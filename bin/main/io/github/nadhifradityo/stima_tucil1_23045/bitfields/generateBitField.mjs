import fs from "fs/promises"
import fs0 from "fs"
import path from "path"
import url from "url"

const __dirname = path.dirname(url.fileURLToPath(import.meta.url));

/**
 * @template {T}
 * @param {T[]} a
 * @param {number} n
 * @returns {T[][]}
 */
const splitEvery = (a, n) => {
	const result = [];
	for(let i = 0; i < a.length; i += n)
		result.push(a.slice(i, i + n));
	return result;
};

/**
 * @param {BigInt} x
 * @returns {BigInt}
 */
const toSigned64 = x => (x & 0x7FFFFFFFFFFFFFFFn) - (x & 0x8000000000000000n);

/**
 * @param {RegExp} regex
 * @param {string} string
 * @param {(g: RegExpExecArray) => string} replacer
 * @returns {string}
 */
const regexReplace = (regex, string, replacer) => {
	let lastIndex = 0;
	let result = "";
	let matcher;
	while((matcher = regex.exec(string)) != null) {
		const replace = replacer(matcher);
		result += string.slice(lastIndex, matcher.index);
		result += replace;
		lastIndex = matcher.index + matcher[0].length;
	}
	result += string.slice(lastIndex);
	return result;
};

/**
 * @typedef {Object} GeneratorOpts
 * @property {string} storeType
 * @property {number} storeSize
 * @property {number} width
 * @property {number} height
 * @property {number} depth
 * @property {number} batch
 */

/**
 * @param {GeneratorOpts} opts
 */
const generateSource = async opts => {
	const {
		type,
		width,
		height,
		depth,
		batch
	} = opts;
	const storeType = "long";
	const storeSize = 64;
	const slots = Math.ceil((width * height * depth) / storeSize);
	const slotBatch = Math.ceil(slots / batch);

	/**
	 * @param {number} x
	 * @param {number} y
	 * @param {number} z
	 * @returns {number}
	 */
	const positionAt = (x, y, z) => {
		if(x < 0 || x >= width) throw new Error("X index out of range: " + x);
		if(y < 0 || y >= height) throw new Error("Y index out of range: " + y);
		if(z < 0 || z >= depth) throw new Error("Z index out of range: " + z);
		return z * (height * width) + y * width + x;
	};
	/**
	 * @param {number} p
	 * @returns {number}
	 */
	const positionLoc = p => Math.floor(p / storeSize);
	/**
	 * @param {number} p
	 * @returns {number}
	 */
	const positionOffset = p => (storeSize - 1) - p % storeSize;

	/**
	 * @param {number[]} fromPositions
	 * @param {number[]} toPositions
	 * @returns {string[]}
	 */
	const generateSourceMover = (fromPositions, toPositions, vResult = l => `this._${l}`, vFrom = l => `_${l}`, vTarget = l => `_${l}`) => {
		/** @type {[number, number][]} */
		const flos = fromPositions.map(p => [positionLoc(p), positionOffset(p)]);
		/** @type {[number, number][]} */
		const tlos = toPositions.map(p => [positionLoc(p), positionOffset(p)]);
		// group consecutive bit shifts
		const glos = new Array(fromPositions.length).fill().reduce((/** @type {[number, number, number, number, number][]} */ a, _, i) => {
			const [l1, o1] = flos[i];
			const [l2, o2] = tlos[i];
			const group = a.find(([pl1, po1, pl2, po2, l]) => l1 == pl1 && o1 == po1 + l && l2 == pl2 && o2 == po2 + l);
			if(group == null) {
				a.push([l1, o1, l2, o2, 1]);
				return a;
			}
			group[4]++;
			return a;
		}, []).filter(([l1, o1, l2, o2, l]) => l1 != l2 || o1 != o2 || l != 0);
		// group islands of bit shifts
		const iglos = glos.reduce((/** @type {[number, BigInt, number, BigInt, number][]} */ a, [l1, o1, l2, o2, l]) => {
			const lm = toSigned64((1n << BigInt(l)) - 1n);
			const m1 = toSigned64((lm << BigInt(o1)) & 0xFFFFFFFFFFFFFFFFn);
			const m2 = toSigned64((lm << BigInt(o2)) & 0xFFFFFFFFFFFFFFFFn);
			const group = a.find(([pl1, pm1, pl2, pm2, ps]) => l1 == pl1 && l2 == pl2 && o1 - o2 == ps);
			if(group == null) {
				a.push([l1, m1, l2, m2, o1 - o2]);
				return a;
			}
			group[1] |= m1;
			group[3] |= m2;
			return a;
		}, []).filter(([l1, m1, l2, m2, s]) => l1 != l2 || m1 != 0 || m2 != 0 || s != 0);
		// https://graphics.stanford.edu/~seander/bithacks.html
		// https://stackoverflow.com/questions/14547087/extracting-bits-with-a-single-multiplication
		// const cancglos = new Array(fromPositions.length).fill().reduce((/** @type {[number, number[], number, number][]} */ a, _, i) => {
		// 	const [l1, o1] = flos[i];
		// 	const [l2, o2] = tlos[i];
		// 	const group = a.find(([pl1, po1s, pl2, po2]) => l1 == pl1 && l2 == pl2 && o2 == po2 + 1);
		// 	if(group == null) {
		// 		a.push([l1, [o1], l2, o2]);
		// 		return a;
		// 	}
		// 	group[1].push(o1);
		// 	group[1].sort((a, b) => a - b);
		// 	group[3] = o2;
		// 	return a;
		// }, []);
		// const cglos = new Array(fromPositions.length).fill().reduce((/** @type {[number, number, number, number, number][]} */ a, _, i) => {
		// 	const [l1, o1] = flos[i];
		// 	const [l2, o2] = tlos[i];
		// }, []);
		return iglos.map(([l1, m1, l2, m2, s]) => {
			const nm2 = toSigned64(m2 ^ 0xFFFFFFFFFFFFFFFFn);
			const tss = s != 0 ? s > 0 ? `>> ${s}` : `<< ${-s}` : null;
			const ts = m1 != 0n ? m1 != -1n ?
				tss != null ? `(${vTarget(l1)} & ${m1}L) ${tss}` : `${vTarget(l1)} & ${m1}L` :
				tss != null ? `${vTarget(l1)} ${tss}` : `${vTarget(l1)}` :
				null;
			const fs = nm2 != 0n ? nm2 != -1n ?
				ts != null ? `(${vFrom(l2)} & ${nm2}L) | (${ts})` : `${vFrom(l2)} & ${nm2}L` :
				ts != null ? `${vFrom(l2)} | (${ts})` : null :
				ts != null ? ts : "0";
			if(fs == null) return null;
			return `${vResult(l2)} = ${fs};`;
		}).filter(l => l != null);
	};
	/**
	 * @param {number[]} positions
	 * @param {boolean[]} values
	 * @returns {string[]}
	 */
	const generateSourceSetter = (positions, values, vResult = l => `this._${l}`, vFrom = l => `_${l}`) => {
		/** @type {[number, number][]} */
		const flos = positions.map(p => [positionLoc(p), positionOffset(p)]);
		// group consecutive bit shifts
		const iglos = new Array(positions.length).fill().reduce((/** @type {[number, BigInt, BigInt][]} */ a, _, i) => {
			const [l1, o1] = flos[i];
			const value = values[i];
			const tv1 = toSigned64(value ? (1n << BigInt(o1)) : 0n);
			const fv1 = toSigned64(!value ? (1n << BigInt(o1)) : 0n);
			const group = a.find(([pl1, ptv1, pfv1]) => l1 == pl1);
			if(group == null) {
				a.push([l1, tv1, fv1]);
				return a;
			}
			group[1] |= tv1;
			group[2] |= fv1;
			return a;
		}, []).filter(([l, tv, fv]) => tv != 0 || fv != 0);
		return iglos.map(([l1, tv1, fv1]) => {
			const nfv1 = toSigned64(fv1 ^ 0xFFFFFFFFFFFFFFFFn);
			const stv1 = tv1 != 0n ? tv1 != -1n ? `${tv1}L` : `${-1n}L` : null;
			if(stv1 == `${-1n}L`)
				return `${vResult(l1)} = ${stv1};`
			const sfv1 = nfv1 != 0n ? nfv1 != -1n ?
				stv1 != null ? `(${vFrom(l1)} & ${nfv1}L) | ${stv1}` : `${vFrom(l1)} & ${nfv1}L` :
				stv1 != null ? `${vFrom(l1)} | ${stv1}` : null :
				stv1 != null ? stv1 : "0";
			if(sfv1 == null) return null;
			return `${vResult(l1)} = ${sfv1};`
		}).filter(l => l != null);
	};

	/**
	 * @param {string} name
	 * @param {number} amountX
	 * @param {number} amountY
	 * @param {number} amountZ
	 * @returns {string}
	 */
	const generateSourceOffsetXYZ = (name, amountX, amountY, amountZ) => {
		const fromPositions = [];
		const toPositions = [];
		for(let x = 0; x < width; x++) {
			const tx = x + amountX;
			if(tx < 0 || tx >= width) continue;
			for(let y = 0; y < height; y++) {
				const ty = y + amountY;
				if(ty < 0 || ty >= height) continue;
				for(let z = 0; z < depth; z++) {
					const tz = z + amountZ;
					if(tz < 0 || tz >= depth) continue;
					fromPositions.push(positionAt(x, y, z));
					toPositions.push(positionAt(tx, ty, tz));
				}
			}
		}
		const unsetPositions = [];
		for(let x = 0; x < width; x++) {
			for(let y = 0; y < height; y++) {
				for(let z = 0; z < depth; z++) {
					const p = positionAt(x, y, z);
					if(toPositions.includes(p)) continue;
					unsetPositions.push(p);
				}
			}
		}
		const overwrittenVars = new Set();
		const requiredVars = new Set();
		const moves = [];
		moves.push(...generateSourceMover(
			fromPositions,
			toPositions,
			l => { overwrittenVars.add(l); return `this._${l}`; },
			l => `this._${l}`,
			(l, la) => { if(!overwrittenVars.has(l) || l == la) return `this._${l}`; requiredVars.add(l); return `INJECT_${l}`; }
		).map(m => `		${m}`));
		moves.push(...generateSourceSetter(
			unsetPositions,
			unsetPositions.map(() => false),
			l => `this._${l}`,
			l => `this._${l}`
		).map(m => `		${m}`));
		if(requiredVars.size > 0) {
			const alt_overwrittenVars = new Set();
			const alt_requiredVars = new Set();
			const alt_moves = [];
			alt_moves.push(...generateSourceMover(
				fromPositions.toReversed(),
				toPositions.toReversed(),
				l => { alt_overwrittenVars.add(l); return `this._${l}`; },
				l => `this._${l}`,
				l => { if(!alt_overwrittenVars.has(l)) return `this._${l}`; alt_requiredVars.add(l); return `INJECT_${l}`; }
			).map(m => `		${m}`));
			alt_moves.push(...generateSourceSetter(
				unsetPositions.toReversed(),
				unsetPositions.toReversed().map(() => false),
				l => `this._${l}`,
				l => `this._${l}`
			).map(m => `		${m}`));
			if(alt_requiredVars.size < requiredVars.size) {
				overwrittenVars.clear();
				for(const v of alt_overwrittenVars)
					overwrittenVars.add(v);
				requiredVars.clear();
				for(const v of alt_requiredVars)
					requiredVars.add(v);
				moves.splice(0);
				moves.push(...alt_moves);
			}
		}
		const movesBatched = splitEvery(moves, 4 * batch);
		const requiredBatchVars = movesBatched.map(b => {
			const vars = new Set();
			b.forEach((_, i) => {
				b[i] = regexReplace(/INJECT_([0-9]+)/gm, b[i], g => {
					vars.add(g[1]);
					return `_${g[1]}`;
				});
			});
			return vars;
		});
		return (
`
	protected void ${name}() {
${[...requiredVars].map(i => `		long _${i} = this._${i};`).join("\n")}
${movesBatched.map((_, i) => `
		this.${name}_${i}(${[...requiredBatchVars[i]].map(j => `_${j}`).join(", ")});
`.slice(1, -1)).join("\n")}
	}
${movesBatched.map((m, i) => `
	protected void ${name}_${i}(${[...requiredBatchVars[i]].map(j => `long _${j}`).join(", ")}) {
${m.join("\n")}
	}
`.slice(1, -1)).join("\n")}
`).slice(1, -1);
	};

	/**
	 * @param {string} name
	 * @returns {string}
	 */
	const generateSourceRotateXYZ = (name) => {
		const fromPositions = [];
		const toPositions = [];
		for(let x = 0; x < width; x++) {
			for(let y = 0; y < height; y++) {
				for(let z = 0; z < depth; z++) {
					let tp = null;
					if(name == "toRotateX90")
						tp = [x, z, height - 1 - y];
					if(name == "toRotateX180")
						tp = [x, height - 1 - y, depth - 1 - z];
					if(name == "toRotateX270")
						tp = [x, depth - 1 - z, y];
					if(name == "toRotateY90")
						tp = [depth - 1 - z, y, x];
					if(name == "toRotateY180")
						tp = [width - 1 - x, y, depth - 1 - z];
					if(name == "toRotateY270")
						tp = [z, y, width - 1 - x];
					if(name == "toRotateZ90")
						tp = [height - 1 - y, x, z];
					if(name == "toRotateZ180")
						tp = [width - 1 - x, height - 1 - y, z];
					if(name == "toRotateZ270")
						tp = [y, width - 1 - x, z];
					const [tx, ty, tz] = tp;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= depth) continue;
					fromPositions.push(positionAt(x, y, z));
					toPositions.push(positionAt(tx, ty, tz));
				}
			}
		}
		const moves = [];
		moves.push(...generateSourceMover(
			fromPositions,
			toPositions,
			l => `that._${l}`,
			l => `this._${l}`,
			l => `this._${l}`
		).map(m => `		${m}`));
		const movesBatched = splitEvery(moves, 4 * batch);
		return (
`
	public ${Class} ${name}() {
		var that = new ${Class}();
${movesBatched.map((_, i) => `
		this.${name}_${i}(that);
`.slice(1, -1)).join("\n")}
		return that;
	}
${movesBatched.map((m, i) => `
	protected void ${name}_${i}(${Class} that) {
${m.join("\n")}
	}
`.slice(1, -1)).join("\n")}
`).slice(1, -1);
	};

	if(type == "abstract") {
		const Class = `BitField${width}x${height}x${depth}Impl`;
		const ClassMutable = `MutableBitField${width}x${height}x${depth}Impl`;
		const ClassImmutable = `ImmutableBitField${width}x${height}x${depth}Impl`;
		const source = `
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

public abstract class ${Class} implements BitField {
	public static final int WIDTH = ${width};
	public static final int HEIGHT = ${height};
	public static final int DEPTH = ${depth};
${new Array(slots).fill().map((_, i) => i).map(i => `	protected ${storeType} _${i};`).join("\n")}

	public int getWidth() {
		return WIDTH;
	}
	public int getHeight() {
		return HEIGHT;
	}
	public int getDepth() {
		return DEPTH;
	}

	public boolean getValue(int x, int y, int z) {
		assert x >= 0 && x < WIDTH;
		assert y >= 0 && y < HEIGHT;
		assert z >= 0 && z < DEPTH;
		int position = z * (HEIGHT * WIDTH) + y * WIDTH + x;
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		if(position < ${(i + 1) * batch * storeSize}) return this.getValue_${i}(position);`).join("\n")}
		assert false;
		return false;
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected boolean getValue_${i}(int position) {
		long mask = 1L << (${storeSize - 1} - position % ${storeSize});
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		if(position < ${(j + 1) * storeSize}) return (this._${j} & mask) != 0;`).join("\n")}
		assert false;
		return false;
	}
`.slice(1, -1)).join("\n")}

	public abstract ${ClassImmutable} toImmutable();
	public abstract ${ClassMutable} toMutable();
	public abstract BitField clone();

	public boolean isIntersecting(BitField that0) {
		if(!(that0 instanceof ${Class}))
			return BitField.super.isIntersecting(that0);
		var that = (${Class}) that0;
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		if(this.isIntersecting_${i}(that)) return true;`).join("\n")}
		return false;
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected boolean isIntersecting_${i}(${Class} that) {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		if((this._${j} & that._${j}) != 0) return true;`).join("\n")}
		return false;
	}
`.slice(1, -1)).join("\n")}
}
`;
		const outputFile = path.join(__dirname, `${Class}.java`);
		console.log(`Generated ${outputFile}`);
		await fs.writeFile(outputFile, source, "utf-8");
		return;
	}
	if(type == "mutable") {
		const offsetSteps = [4,2,1];
		const Class = `MutableBitField${width}x${height}x${depth}Impl`;
		const ClassImmutable = `ImmutableBitField${width}x${height}x${depth}Impl`;
		const ClassAbstract = `BitField${width}x${height}x${depth}Impl`;
		const source = `
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

public class ${Class} extends ${ClassAbstract} implements MutableBitField {

	public void setValue(int x, int y, int z, boolean v) {
		assert x >= 0 && x < WIDTH;
		assert y >= 0 && y < HEIGHT;
		assert z >= 0 && z < DEPTH;
		int position = z * (HEIGHT * WIDTH) + y * WIDTH + x;
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		if(position < ${(i + 1) * batch * storeSize}) { this.setValue_${i}(position, v); return; }`).join("\n")}
		assert false;
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void setValue_${i}(int position, boolean v) {
		long mask = ~(1L << (${storeSize - 1} - position % ${storeSize}));
		long value = v ? 1L << (${storeSize - 1} - position % ${storeSize}) : 0;
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		if(position < ${(j + 1) * storeSize}) { this._${j} = (this._${j} & mask) | value; return; }`).join("\n")}
		assert false;
	}
`.slice(1, -1)).join("\n")}

	public void set(BitField that0) {
		if(!(that0 instanceof ${ClassAbstract})) {
			MutableBitField.super.set(that0);
			return;
		}
		var that = (${ClassAbstract}) that0;
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.set_${i}(that);`).join("\n")}
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void set_${i}(${ClassAbstract} that) {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		this._${j} = that._${j};`).join("\n")}
	}
`.slice(1, -1)).join("\n")}

	public void clear() {
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.clear_${i}();`).join("\n")}
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void clear_${i}() {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		this._${j} = 0;`).join("\n")}
	}
`.slice(1, -1)).join("\n")}

	public ${ClassImmutable} toImmutable() {
		var that = new ${ClassImmutable}();
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.toImmutable_${i}(that);`).join("\n")}
		return that;
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void toImmutable_${i}(${ClassImmutable} that) {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		that._${j} = this._${j};`).join("\n")}
	}
`.slice(1, -1)).join("\n")}

	public ${Class} toMutable() {
		return this.clone();
	}

	public ${Class} clone() {
		var result = new ${Class}();
		result.set(this);
		return result;
	}

	public void offsetX(int amount) {
		amount = Math.max(-WIDTH, Math.min(WIDTH, amount));
		if(amount >= 0) {
${offsetSteps.map(step => `
			while((amount / ${step}) > 0) {
				this.offsetPX${step}();
				amount -= ${step};
			}
`.slice(1, -1)).join("\n")}
		} else {
			amount *= -1;
${offsetSteps.map(step => `
			while((amount / ${step}) > 0) {
				this.offsetNX${step}();
				amount -= ${step};
			}
`.slice(1, -1)).join("\n")}
		}
		assert amount == 0;
	}
${offsetSteps.map(step => `
${generateSourceOffsetXYZ(`offsetPX${step}`, step, 0, 0)}
`.slice(1, -1)).join("\n")}
${offsetSteps.map(step => `
${generateSourceOffsetXYZ(`offsetNX${step}`, -step, 0, 0)}
`.slice(1, -1)).join("\n")}

	public void offsetY(int amount) {
		amount = Math.max(-HEIGHT, Math.min(HEIGHT, amount));
		if(amount >= 0) {
${offsetSteps.map(step => `
			while((amount / ${step}) > 0) {
				this.offsetPY${step}();
				amount -= ${step};
			}
`.slice(1, -1)).join("\n")}
		} else {
			amount *= -1;
${offsetSteps.map(step => `
			while((amount / ${step}) > 0) {
				this.offsetNY${step}();
				amount -= ${step};
			}
`.slice(1, -1)).join("\n")}
		}
		assert amount == 0;
	}
${offsetSteps.map(step => `
${generateSourceOffsetXYZ(`offsetPY${step}`, 0, step, 0)}
`.slice(1, -1)).join("\n")}
${offsetSteps.map(step => `
${generateSourceOffsetXYZ(`offsetNY${step}`, 0, -step, 0)}
`.slice(1, -1)).join("\n")}

	public void offsetZ(int amount) {
		amount = Math.max(-DEPTH, Math.min(DEPTH, amount));
		if(amount >= 0) {
${offsetSteps.map(step => `
			while((amount / ${step}) > 0) {
				this.offsetPZ${step}();
				amount -= ${step};
			}
`.slice(1, -1)).join("\n")}
		} else {
			amount *= -1;
${offsetSteps.map(step => `
			while((amount / ${step}) > 0) {
				this.offsetNZ${step}();
				amount -= ${step};
			}
`.slice(1, -1)).join("\n")}
		}
		assert amount == 0;
	}
${offsetSteps.map(step => `
${generateSourceOffsetXYZ(`offsetPZ${step}`, 0, 0, step)}
`.slice(1, -1)).join("\n")}
${offsetSteps.map(step => `
${generateSourceOffsetXYZ(`offsetNZ${step}`, 0, 0, -step)}
`.slice(1, -1)).join("\n")}

	public void complement() {
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.complement_${i}();`).join("\n")}
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void complement_${i}() {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		this._${j} = ~this._${j};`).join("\n")}
	}
`.slice(1, -1)).join("\n")}

	public void union(BitField that0) {
		if(!(that0 instanceof ${ClassAbstract})) {
			MutableBitField.super.union(that0);
			return;
		}
		var that = (${ClassAbstract}) that0;
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.union_${i}(that);`).join("\n")}
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void union_${i}(${ClassAbstract} that) {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		this._${j} |= that._${j};`).join("\n")}
	}
`.slice(1, -1)).join("\n")}

	public void intersect(BitField that0) {
		if(!(that0 instanceof ${ClassAbstract})) {
			MutableBitField.super.union(that0);
			return;
		}
		var that = (${ClassAbstract}) that0;
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.intersect_${i}(that);`).join("\n")}
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void intersect_${i}(${ClassAbstract} that) {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		this._${j} &= that._${j};`).join("\n")}
	}
`.slice(1, -1)).join("\n")}

	public void exclusive(BitField that0) {
		if(!(that0 instanceof ${ClassAbstract})) {
			MutableBitField.super.union(that0);
			return;
		}
		var that = (${ClassAbstract}) that0;
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.exclusive_${i}(that);`).join("\n")}
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void exclusive_${i}(${ClassAbstract} that) {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		this._${j} ^= that._${j};`).join("\n")}
	}
`.slice(1, -1)).join("\n")}

	public void subtract(BitField that0) {
		if(!(that0 instanceof ${ClassAbstract})) {
			MutableBitField.super.union(that0);
			return;
		}
		var that = (${ClassAbstract}) that0;
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.subtract_${i}(that);`).join("\n")}
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void subtract_${i}(${ClassAbstract} that) {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		this._${j} &= ~that._${j};`).join("\n")}
	}
`.slice(1, -1)).join("\n")}
}
`;
		const outputFile = path.join(__dirname, `${Class}.java`);
		console.log(`Generated ${outputFile}`);
		await fs.writeFile(outputFile, source, "utf-8");
		return;
	}
	if(type == "immutable") {
		const Class = `ImmutableBitField${width}x${height}x${depth}Impl`;
		const ClassMutable = `MutableBitField${width}x${height}x${depth}Impl`;
		const ClassAbstract = `BitField${width}x${height}x${depth}Impl`;
		const source = `
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

public class ${Class} extends ${ClassAbstract} implements ImmutableBitField {

	public ${Class} toImmutable() {
		return this.clone();
	}

	public ${ClassMutable} toMutable() {
		var that = new ${ClassMutable}();
		that.set(this);
		return that;
	}

	public ${Class} clone() {
		var that = new ${Class}();
${new Array(slotBatch).fill().map((_, i) => i).map(i => `		this.clone_${i}(that);`).join("\n")}
		return that;
	}
${new Array(slotBatch).fill().map((_, i) => i).map(i => `
	protected void clone_${i}(${Class} that) {
${new Array(batch).fill().map((_, j) => i * batch + j).filter(j => j < slots).map(j => `		that._${j} = this._${j};`).join("\n")}
	}
`.slice(1, -1)).join("\n")}
}
`;
		const outputFile = path.join(__dirname, `${Class}.java`);
		console.log(`Generated ${outputFile}`);
		await fs.writeFile(outputFile, source, "utf-8");
		return;
	}
	throw new Error(`Invalid type: ${type}`);
}

const sizes = [
	[8, 8, 1],
	[8, 8, 8],
	[12, 12, 1],
	[12, 12, 12],
	[16, 16, 1],
	[16, 16, 16],
	[20, 20, 1],
	[20, 20, 10],
	[32, 32, 1],
	[32, 32, 8],
];
for(const [width, height, depth] of sizes) {
	await generateSource({
		type: "abstract",
		width: width,
		height: height,
		depth: depth,
		batch: 16
	});
	await generateSource({
		type: "mutable",
		width: width,
		height: height,
		depth: depth,
		batch: 16
	});
	await generateSource({
		type: "immutable",
		width: width,
		height: height,
		depth: depth,
		batch: 16
	});
}
