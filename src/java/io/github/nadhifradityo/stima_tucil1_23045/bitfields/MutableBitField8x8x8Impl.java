
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

public class MutableBitField8x8x8Impl extends BitField8x8x8Impl implements MutableBitField {

	public void setValue(int x, int y, int z, boolean v) {
		assert x >= 0 && x < WIDTH;
		assert y >= 0 && y < HEIGHT;
		assert z >= 0 && z < DEPTH;
		int position = z * (HEIGHT * WIDTH) + y * WIDTH + x;
		if(position < 1024) { this.setValue_0(position, v); return; }
		assert false;
	}
	protected void setValue_0(int position, boolean v) {
		long mask = ~(1L << (63 - position % 64));
		long value = v ? 1L << (63 - position % 64) : 0;
		if(position < 64) { this._0 = (this._0 & mask) | value; return; }
		if(position < 128) { this._1 = (this._1 & mask) | value; return; }
		if(position < 192) { this._2 = (this._2 & mask) | value; return; }
		if(position < 256) { this._3 = (this._3 & mask) | value; return; }
		if(position < 320) { this._4 = (this._4 & mask) | value; return; }
		if(position < 384) { this._5 = (this._5 & mask) | value; return; }
		if(position < 448) { this._6 = (this._6 & mask) | value; return; }
		if(position < 512) { this._7 = (this._7 & mask) | value; return; }
		assert false;
	}

	public void set(BitField that0) {
		if(!(that0 instanceof BitField8x8x8Impl)) {
			MutableBitField.super.set(that0);
			return;
		}
		var that = (BitField8x8x8Impl) that0;
		this.set_0(that);
	}
	protected void set_0(BitField8x8x8Impl that) {
		this._0 = that._0;
		this._1 = that._1;
		this._2 = that._2;
		this._3 = that._3;
		this._4 = that._4;
		this._5 = that._5;
		this._6 = that._6;
		this._7 = that._7;
	}

	public void clear() {
		this.clear_0();
	}
	protected void clear_0() {
		this._0 = 0;
		this._1 = 0;
		this._2 = 0;
		this._3 = 0;
		this._4 = 0;
		this._5 = 0;
		this._6 = 0;
		this._7 = 0;
	}

	public ImmutableBitField8x8x8Impl toImmutable() {
		var that = new ImmutableBitField8x8x8Impl();
		this.toImmutable_0(that);
		return that;
	}
	protected void toImmutable_0(ImmutableBitField8x8x8Impl that) {
		that._0 = this._0;
		that._1 = this._1;
		that._2 = this._2;
		that._3 = this._3;
		that._4 = this._4;
		that._5 = this._5;
		that._6 = this._6;
		that._7 = this._7;
	}

	public MutableBitField8x8x8Impl toMutable() {
		return this.clone();
	}

	public MutableBitField8x8x8Impl clone() {
		var result = new MutableBitField8x8x8Impl();
		result.set(this);
		return result;
	}

	public void offsetX(int amount) {
		amount = Math.max(-WIDTH, Math.min(WIDTH, amount));
		if(amount >= 0) {
			while((amount / 4) > 0) {
				this.offsetPX4();
				amount -= 4;
			}
			while((amount / 2) > 0) {
				this.offsetPX2();
				amount -= 2;
			}
			while((amount / 1) > 0) {
				this.offsetPX1();
				amount -= 1;
			}
		} else {
			amount *= -1;
			while((amount / 4) > 0) {
				this.offsetNX4();
				amount -= 4;
			}
			while((amount / 2) > 0) {
				this.offsetNX2();
				amount -= 2;
			}
			while((amount / 1) > 0) {
				this.offsetNX1();
				amount -= 1;
			}
		}
		assert amount == 0;
	}
	protected void offsetPX4() {

		this.offsetPX4_0();
	}
	protected void offsetPX4_0() {
		this._0 = (this._0 & -1085102592571150096L) | ((this._0 & -1085102592571150096L) >> 4);
		this._1 = (this._1 & -1085102592571150096L) | ((this._1 & -1085102592571150096L) >> 4);
		this._2 = (this._2 & -1085102592571150096L) | ((this._2 & -1085102592571150096L) >> 4);
		this._3 = (this._3 & -1085102592571150096L) | ((this._3 & -1085102592571150096L) >> 4);
		this._4 = (this._4 & -1085102592571150096L) | ((this._4 & -1085102592571150096L) >> 4);
		this._5 = (this._5 & -1085102592571150096L) | ((this._5 & -1085102592571150096L) >> 4);
		this._6 = (this._6 & -1085102592571150096L) | ((this._6 & -1085102592571150096L) >> 4);
		this._7 = (this._7 & -1085102592571150096L) | ((this._7 & -1085102592571150096L) >> 4);
		this._0 = this._0 & 1085102592571150095L;
		this._1 = this._1 & 1085102592571150095L;
		this._2 = this._2 & 1085102592571150095L;
		this._3 = this._3 & 1085102592571150095L;
		this._4 = this._4 & 1085102592571150095L;
		this._5 = this._5 & 1085102592571150095L;
		this._6 = this._6 & 1085102592571150095L;
		this._7 = this._7 & 1085102592571150095L;
	}
	protected void offsetPX2() {

		this.offsetPX2_0();
	}
	protected void offsetPX2_0() {
		this._0 = (this._0 & -4557430888798830400L) | ((this._0 & -217020518514230020L) >> 2);
		this._1 = (this._1 & -4557430888798830400L) | ((this._1 & -217020518514230020L) >> 2);
		this._2 = (this._2 & -4557430888798830400L) | ((this._2 & -217020518514230020L) >> 2);
		this._3 = (this._3 & -4557430888798830400L) | ((this._3 & -217020518514230020L) >> 2);
		this._4 = (this._4 & -4557430888798830400L) | ((this._4 & -217020518514230020L) >> 2);
		this._5 = (this._5 & -4557430888798830400L) | ((this._5 & -217020518514230020L) >> 2);
		this._6 = (this._6 & -4557430888798830400L) | ((this._6 & -217020518514230020L) >> 2);
		this._7 = (this._7 & -4557430888798830400L) | ((this._7 & -217020518514230020L) >> 2);
		this._0 = this._0 & 4557430888798830399L;
		this._1 = this._1 & 4557430888798830399L;
		this._2 = this._2 & 4557430888798830399L;
		this._3 = this._3 & 4557430888798830399L;
		this._4 = this._4 & 4557430888798830399L;
		this._5 = this._5 & 4557430888798830399L;
		this._6 = this._6 & 4557430888798830399L;
		this._7 = this._7 & 4557430888798830399L;
	}
	protected void offsetPX1() {

		this.offsetPX1_0();
	}
	protected void offsetPX1_0() {
		this._0 = (this._0 & -9187201950435737472L) | ((this._0 & -72340172838076674L) >> 1);
		this._1 = (this._1 & -9187201950435737472L) | ((this._1 & -72340172838076674L) >> 1);
		this._2 = (this._2 & -9187201950435737472L) | ((this._2 & -72340172838076674L) >> 1);
		this._3 = (this._3 & -9187201950435737472L) | ((this._3 & -72340172838076674L) >> 1);
		this._4 = (this._4 & -9187201950435737472L) | ((this._4 & -72340172838076674L) >> 1);
		this._5 = (this._5 & -9187201950435737472L) | ((this._5 & -72340172838076674L) >> 1);
		this._6 = (this._6 & -9187201950435737472L) | ((this._6 & -72340172838076674L) >> 1);
		this._7 = (this._7 & -9187201950435737472L) | ((this._7 & -72340172838076674L) >> 1);
		this._0 = this._0 & 9187201950435737471L;
		this._1 = this._1 & 9187201950435737471L;
		this._2 = this._2 & 9187201950435737471L;
		this._3 = this._3 & 9187201950435737471L;
		this._4 = this._4 & 9187201950435737471L;
		this._5 = this._5 & 9187201950435737471L;
		this._6 = this._6 & 9187201950435737471L;
		this._7 = this._7 & 9187201950435737471L;
	}
	protected void offsetNX4() {

		this.offsetNX4_0();
	}
	protected void offsetNX4_0() {
		this._0 = (this._0 & 1085102592571150095L) | ((this._0 & 1085102592571150095L) << 4);
		this._1 = (this._1 & 1085102592571150095L) | ((this._1 & 1085102592571150095L) << 4);
		this._2 = (this._2 & 1085102592571150095L) | ((this._2 & 1085102592571150095L) << 4);
		this._3 = (this._3 & 1085102592571150095L) | ((this._3 & 1085102592571150095L) << 4);
		this._4 = (this._4 & 1085102592571150095L) | ((this._4 & 1085102592571150095L) << 4);
		this._5 = (this._5 & 1085102592571150095L) | ((this._5 & 1085102592571150095L) << 4);
		this._6 = (this._6 & 1085102592571150095L) | ((this._6 & 1085102592571150095L) << 4);
		this._7 = (this._7 & 1085102592571150095L) | ((this._7 & 1085102592571150095L) << 4);
		this._0 = this._0 & -1085102592571150096L;
		this._1 = this._1 & -1085102592571150096L;
		this._2 = this._2 & -1085102592571150096L;
		this._3 = this._3 & -1085102592571150096L;
		this._4 = this._4 & -1085102592571150096L;
		this._5 = this._5 & -1085102592571150096L;
		this._6 = this._6 & -1085102592571150096L;
		this._7 = this._7 & -1085102592571150096L;
	}
	protected void offsetNX2() {

		this.offsetNX2_0();
	}
	protected void offsetNX2_0() {
		this._0 = (this._0 & 217020518514230019L) | ((this._0 & 4557430888798830399L) << 2);
		this._1 = (this._1 & 217020518514230019L) | ((this._1 & 4557430888798830399L) << 2);
		this._2 = (this._2 & 217020518514230019L) | ((this._2 & 4557430888798830399L) << 2);
		this._3 = (this._3 & 217020518514230019L) | ((this._3 & 4557430888798830399L) << 2);
		this._4 = (this._4 & 217020518514230019L) | ((this._4 & 4557430888798830399L) << 2);
		this._5 = (this._5 & 217020518514230019L) | ((this._5 & 4557430888798830399L) << 2);
		this._6 = (this._6 & 217020518514230019L) | ((this._6 & 4557430888798830399L) << 2);
		this._7 = (this._7 & 217020518514230019L) | ((this._7 & 4557430888798830399L) << 2);
		this._0 = this._0 & -217020518514230020L;
		this._1 = this._1 & -217020518514230020L;
		this._2 = this._2 & -217020518514230020L;
		this._3 = this._3 & -217020518514230020L;
		this._4 = this._4 & -217020518514230020L;
		this._5 = this._5 & -217020518514230020L;
		this._6 = this._6 & -217020518514230020L;
		this._7 = this._7 & -217020518514230020L;
	}
	protected void offsetNX1() {

		this.offsetNX1_0();
	}
	protected void offsetNX1_0() {
		this._0 = (this._0 & 72340172838076673L) | ((this._0 & 9187201950435737471L) << 1);
		this._1 = (this._1 & 72340172838076673L) | ((this._1 & 9187201950435737471L) << 1);
		this._2 = (this._2 & 72340172838076673L) | ((this._2 & 9187201950435737471L) << 1);
		this._3 = (this._3 & 72340172838076673L) | ((this._3 & 9187201950435737471L) << 1);
		this._4 = (this._4 & 72340172838076673L) | ((this._4 & 9187201950435737471L) << 1);
		this._5 = (this._5 & 72340172838076673L) | ((this._5 & 9187201950435737471L) << 1);
		this._6 = (this._6 & 72340172838076673L) | ((this._6 & 9187201950435737471L) << 1);
		this._7 = (this._7 & 72340172838076673L) | ((this._7 & 9187201950435737471L) << 1);
		this._0 = this._0 & -72340172838076674L;
		this._1 = this._1 & -72340172838076674L;
		this._2 = this._2 & -72340172838076674L;
		this._3 = this._3 & -72340172838076674L;
		this._4 = this._4 & -72340172838076674L;
		this._5 = this._5 & -72340172838076674L;
		this._6 = this._6 & -72340172838076674L;
		this._7 = this._7 & -72340172838076674L;
	}

	public void offsetY(int amount) {
		amount = Math.max(-HEIGHT, Math.min(HEIGHT, amount));
		if(amount >= 0) {
			while((amount / 4) > 0) {
				this.offsetPY4();
				amount -= 4;
			}
			while((amount / 2) > 0) {
				this.offsetPY2();
				amount -= 2;
			}
			while((amount / 1) > 0) {
				this.offsetPY1();
				amount -= 1;
			}
		} else {
			amount *= -1;
			while((amount / 4) > 0) {
				this.offsetNY4();
				amount -= 4;
			}
			while((amount / 2) > 0) {
				this.offsetNY2();
				amount -= 2;
			}
			while((amount / 1) > 0) {
				this.offsetNY1();
				amount -= 1;
			}
		}
		assert amount == 0;
	}
	protected void offsetPY4() {

		this.offsetPY4_0();
	}
	protected void offsetPY4_0() {
		this._0 = (this._0 & -4294967296L) | ((this._0 & -4294967296L) >> 32);
		this._1 = (this._1 & -4294967296L) | ((this._1 & -4294967296L) >> 32);
		this._2 = (this._2 & -4294967296L) | ((this._2 & -4294967296L) >> 32);
		this._3 = (this._3 & -4294967296L) | ((this._3 & -4294967296L) >> 32);
		this._4 = (this._4 & -4294967296L) | ((this._4 & -4294967296L) >> 32);
		this._5 = (this._5 & -4294967296L) | ((this._5 & -4294967296L) >> 32);
		this._6 = (this._6 & -4294967296L) | ((this._6 & -4294967296L) >> 32);
		this._7 = (this._7 & -4294967296L) | ((this._7 & -4294967296L) >> 32);
		this._0 = this._0 & 4294967295L;
		this._1 = this._1 & 4294967295L;
		this._2 = this._2 & 4294967295L;
		this._3 = this._3 & 4294967295L;
		this._4 = this._4 & 4294967295L;
		this._5 = this._5 & 4294967295L;
		this._6 = this._6 & 4294967295L;
		this._7 = this._7 & 4294967295L;
	}
	protected void offsetPY2() {

		this.offsetPY2_0();
	}
	protected void offsetPY2_0() {
		this._0 = (this._0 & -281474976710656L) | ((this._0 & -65536L) >> 16);
		this._1 = (this._1 & -281474976710656L) | ((this._1 & -65536L) >> 16);
		this._2 = (this._2 & -281474976710656L) | ((this._2 & -65536L) >> 16);
		this._3 = (this._3 & -281474976710656L) | ((this._3 & -65536L) >> 16);
		this._4 = (this._4 & -281474976710656L) | ((this._4 & -65536L) >> 16);
		this._5 = (this._5 & -281474976710656L) | ((this._5 & -65536L) >> 16);
		this._6 = (this._6 & -281474976710656L) | ((this._6 & -65536L) >> 16);
		this._7 = (this._7 & -281474976710656L) | ((this._7 & -65536L) >> 16);
		this._0 = this._0 & 281474976710655L;
		this._1 = this._1 & 281474976710655L;
		this._2 = this._2 & 281474976710655L;
		this._3 = this._3 & 281474976710655L;
		this._4 = this._4 & 281474976710655L;
		this._5 = this._5 & 281474976710655L;
		this._6 = this._6 & 281474976710655L;
		this._7 = this._7 & 281474976710655L;
	}
	protected void offsetPY1() {

		this.offsetPY1_0();
	}
	protected void offsetPY1_0() {
		this._0 = (this._0 & -72057594037927936L) | ((this._0 & -256L) >> 8);
		this._1 = (this._1 & -72057594037927936L) | ((this._1 & -256L) >> 8);
		this._2 = (this._2 & -72057594037927936L) | ((this._2 & -256L) >> 8);
		this._3 = (this._3 & -72057594037927936L) | ((this._3 & -256L) >> 8);
		this._4 = (this._4 & -72057594037927936L) | ((this._4 & -256L) >> 8);
		this._5 = (this._5 & -72057594037927936L) | ((this._5 & -256L) >> 8);
		this._6 = (this._6 & -72057594037927936L) | ((this._6 & -256L) >> 8);
		this._7 = (this._7 & -72057594037927936L) | ((this._7 & -256L) >> 8);
		this._0 = this._0 & 72057594037927935L;
		this._1 = this._1 & 72057594037927935L;
		this._2 = this._2 & 72057594037927935L;
		this._3 = this._3 & 72057594037927935L;
		this._4 = this._4 & 72057594037927935L;
		this._5 = this._5 & 72057594037927935L;
		this._6 = this._6 & 72057594037927935L;
		this._7 = this._7 & 72057594037927935L;
	}
	protected void offsetNY4() {

		this.offsetNY4_0();
	}
	protected void offsetNY4_0() {
		this._0 = (this._0 & 4294967295L) | ((this._0 & 4294967295L) << 32);
		this._1 = (this._1 & 4294967295L) | ((this._1 & 4294967295L) << 32);
		this._2 = (this._2 & 4294967295L) | ((this._2 & 4294967295L) << 32);
		this._3 = (this._3 & 4294967295L) | ((this._3 & 4294967295L) << 32);
		this._4 = (this._4 & 4294967295L) | ((this._4 & 4294967295L) << 32);
		this._5 = (this._5 & 4294967295L) | ((this._5 & 4294967295L) << 32);
		this._6 = (this._6 & 4294967295L) | ((this._6 & 4294967295L) << 32);
		this._7 = (this._7 & 4294967295L) | ((this._7 & 4294967295L) << 32);
		this._0 = this._0 & -4294967296L;
		this._1 = this._1 & -4294967296L;
		this._2 = this._2 & -4294967296L;
		this._3 = this._3 & -4294967296L;
		this._4 = this._4 & -4294967296L;
		this._5 = this._5 & -4294967296L;
		this._6 = this._6 & -4294967296L;
		this._7 = this._7 & -4294967296L;
	}
	protected void offsetNY2() {

		this.offsetNY2_0();
	}
	protected void offsetNY2_0() {
		this._0 = (this._0 & 65535L) | ((this._0 & 281474976710655L) << 16);
		this._1 = (this._1 & 65535L) | ((this._1 & 281474976710655L) << 16);
		this._2 = (this._2 & 65535L) | ((this._2 & 281474976710655L) << 16);
		this._3 = (this._3 & 65535L) | ((this._3 & 281474976710655L) << 16);
		this._4 = (this._4 & 65535L) | ((this._4 & 281474976710655L) << 16);
		this._5 = (this._5 & 65535L) | ((this._5 & 281474976710655L) << 16);
		this._6 = (this._6 & 65535L) | ((this._6 & 281474976710655L) << 16);
		this._7 = (this._7 & 65535L) | ((this._7 & 281474976710655L) << 16);
		this._0 = this._0 & -65536L;
		this._1 = this._1 & -65536L;
		this._2 = this._2 & -65536L;
		this._3 = this._3 & -65536L;
		this._4 = this._4 & -65536L;
		this._5 = this._5 & -65536L;
		this._6 = this._6 & -65536L;
		this._7 = this._7 & -65536L;
	}
	protected void offsetNY1() {

		this.offsetNY1_0();
	}
	protected void offsetNY1_0() {
		this._0 = (this._0 & 255L) | ((this._0 & 72057594037927935L) << 8);
		this._1 = (this._1 & 255L) | ((this._1 & 72057594037927935L) << 8);
		this._2 = (this._2 & 255L) | ((this._2 & 72057594037927935L) << 8);
		this._3 = (this._3 & 255L) | ((this._3 & 72057594037927935L) << 8);
		this._4 = (this._4 & 255L) | ((this._4 & 72057594037927935L) << 8);
		this._5 = (this._5 & 255L) | ((this._5 & 72057594037927935L) << 8);
		this._6 = (this._6 & 255L) | ((this._6 & 72057594037927935L) << 8);
		this._7 = (this._7 & 255L) | ((this._7 & 72057594037927935L) << 8);
		this._0 = this._0 & -256L;
		this._1 = this._1 & -256L;
		this._2 = this._2 & -256L;
		this._3 = this._3 & -256L;
		this._4 = this._4 & -256L;
		this._5 = this._5 & -256L;
		this._6 = this._6 & -256L;
		this._7 = this._7 & -256L;
	}

	public void offsetZ(int amount) {
		amount = Math.max(-DEPTH, Math.min(DEPTH, amount));
		if(amount >= 0) {
			while((amount / 4) > 0) {
				this.offsetPZ4();
				amount -= 4;
			}
			while((amount / 2) > 0) {
				this.offsetPZ2();
				amount -= 2;
			}
			while((amount / 1) > 0) {
				this.offsetPZ1();
				amount -= 1;
			}
		} else {
			amount *= -1;
			while((amount / 4) > 0) {
				this.offsetNZ4();
				amount -= 4;
			}
			while((amount / 2) > 0) {
				this.offsetNZ2();
				amount -= 2;
			}
			while((amount / 1) > 0) {
				this.offsetNZ1();
				amount -= 1;
			}
		}
		assert amount == 0;
	}
	protected void offsetPZ4() {

		this.offsetPZ4_0();
	}
	protected void offsetPZ4_0() {
		this._4 = this._0;
		this._5 = this._1;
		this._6 = this._2;
		this._7 = this._3;
		this._0 = 0;
		this._1 = 0;
		this._2 = 0;
		this._3 = 0;
	}
	protected void offsetPZ2() {

		this.offsetPZ2_0();
	}
	protected void offsetPZ2_0() {
		this._7 = this._5;
		this._6 = this._4;
		this._5 = this._3;
		this._4 = this._2;
		this._3 = this._1;
		this._2 = this._0;
		this._1 = 0;
		this._0 = 0;
	}
	protected void offsetPZ1() {

		this.offsetPZ1_0();
	}
	protected void offsetPZ1_0() {
		this._7 = this._6;
		this._6 = this._5;
		this._5 = this._4;
		this._4 = this._3;
		this._3 = this._2;
		this._2 = this._1;
		this._1 = this._0;
		this._0 = 0;
	}
	protected void offsetNZ4() {

		this.offsetNZ4_0();
	}
	protected void offsetNZ4_0() {
		this._0 = this._4;
		this._1 = this._5;
		this._2 = this._6;
		this._3 = this._7;
		this._4 = 0;
		this._5 = 0;
		this._6 = 0;
		this._7 = 0;
	}
	protected void offsetNZ2() {

		this.offsetNZ2_0();
	}
	protected void offsetNZ2_0() {
		this._0 = this._2;
		this._1 = this._3;
		this._2 = this._4;
		this._3 = this._5;
		this._4 = this._6;
		this._5 = this._7;
		this._6 = 0;
		this._7 = 0;
	}
	protected void offsetNZ1() {

		this.offsetNZ1_0();
	}
	protected void offsetNZ1_0() {
		this._0 = this._1;
		this._1 = this._2;
		this._2 = this._3;
		this._3 = this._4;
		this._4 = this._5;
		this._5 = this._6;
		this._6 = this._7;
		this._7 = 0;
	}

	public void complement() {
		this.complement_0();
	}
	protected void complement_0() {
		this._0 = ~this._0;
		this._1 = ~this._1;
		this._2 = ~this._2;
		this._3 = ~this._3;
		this._4 = ~this._4;
		this._5 = ~this._5;
		this._6 = ~this._6;
		this._7 = ~this._7;
	}

	public void union(BitField that0) {
		if(!(that0 instanceof BitField8x8x8Impl)) {
			MutableBitField.super.union(that0);
			return;
		}
		var that = (BitField8x8x8Impl) that0;
		this.union_0(that);
	}
	protected void union_0(BitField8x8x8Impl that) {
		this._0 |= that._0;
		this._1 |= that._1;
		this._2 |= that._2;
		this._3 |= that._3;
		this._4 |= that._4;
		this._5 |= that._5;
		this._6 |= that._6;
		this._7 |= that._7;
	}

	public void intersect(BitField that0) {
		if(!(that0 instanceof BitField8x8x8Impl)) {
			MutableBitField.super.union(that0);
			return;
		}
		var that = (BitField8x8x8Impl) that0;
		this.intersect_0(that);
	}
	protected void intersect_0(BitField8x8x8Impl that) {
		this._0 &= that._0;
		this._1 &= that._1;
		this._2 &= that._2;
		this._3 &= that._3;
		this._4 &= that._4;
		this._5 &= that._5;
		this._6 &= that._6;
		this._7 &= that._7;
	}

	public void exclusive(BitField that0) {
		if(!(that0 instanceof BitField8x8x8Impl)) {
			MutableBitField.super.union(that0);
			return;
		}
		var that = (BitField8x8x8Impl) that0;
		this.exclusive_0(that);
	}
	protected void exclusive_0(BitField8x8x8Impl that) {
		this._0 ^= that._0;
		this._1 ^= that._1;
		this._2 ^= that._2;
		this._3 ^= that._3;
		this._4 ^= that._4;
		this._5 ^= that._5;
		this._6 ^= that._6;
		this._7 ^= that._7;
	}

	public void subtract(BitField that0) {
		if(!(that0 instanceof BitField8x8x8Impl)) {
			MutableBitField.super.union(that0);
			return;
		}
		var that = (BitField8x8x8Impl) that0;
		this.subtract_0(that);
	}
	protected void subtract_0(BitField8x8x8Impl that) {
		this._0 &= ~that._0;
		this._1 &= ~that._1;
		this._2 &= ~that._2;
		this._3 &= ~that._3;
		this._4 &= ~that._4;
		this._5 &= ~that._5;
		this._6 &= ~that._6;
		this._7 &= ~that._7;
	}
}
