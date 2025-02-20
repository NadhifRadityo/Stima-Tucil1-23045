
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045;

public class BitField32x32x1 implements BitField {
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	public static final int DEPTH = 1;
	protected long _0;
	protected long _1;
	protected long _2;
	protected long _3;
	protected long _4;
	protected long _5;
	protected long _6;
	protected long _7;
	protected long _8;
	protected long _9;
	protected long _10;
	protected long _11;
	protected long _12;
	protected long _13;
	protected long _14;
	protected long _15;

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
		if(position < 1024) return this.getValue0(position);
		assert false;
		return false;
	}
	protected boolean getValue0(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 64) return (this._0 & mask) != 0;
		if(position < 128) return (this._1 & mask) != 0;
		if(position < 192) return (this._2 & mask) != 0;
		if(position < 256) return (this._3 & mask) != 0;
		if(position < 320) return (this._4 & mask) != 0;
		if(position < 384) return (this._5 & mask) != 0;
		if(position < 448) return (this._6 & mask) != 0;
		if(position < 512) return (this._7 & mask) != 0;
		if(position < 576) return (this._8 & mask) != 0;
		if(position < 640) return (this._9 & mask) != 0;
		if(position < 704) return (this._10 & mask) != 0;
		if(position < 768) return (this._11 & mask) != 0;
		if(position < 832) return (this._12 & mask) != 0;
		if(position < 896) return (this._13 & mask) != 0;
		if(position < 960) return (this._14 & mask) != 0;
		if(position < 1024) return (this._15 & mask) != 0;
		assert false;
		return false;
	}

	public void setValue(int x, int y, int z, boolean v) {
		assert x >= 0 && x < WIDTH;
		assert y >= 0 && y < HEIGHT;
		assert z >= 0 && z < DEPTH;
		int position = z * (HEIGHT * WIDTH) + y * WIDTH + x;
		if(position < 1024) { this.setValue0(position, v); return; }
		assert false;
	}
	protected void setValue0(int position, boolean v) {
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
		if(position < 576) { this._8 = (this._8 & mask) | value; return; }
		if(position < 640) { this._9 = (this._9 & mask) | value; return; }
		if(position < 704) { this._10 = (this._10 & mask) | value; return; }
		if(position < 768) { this._11 = (this._11 & mask) | value; return; }
		if(position < 832) { this._12 = (this._12 & mask) | value; return; }
		if(position < 896) { this._13 = (this._13 & mask) | value; return; }
		if(position < 960) { this._14 = (this._14 & mask) | value; return; }
		if(position < 1024) { this._15 = (this._15 & mask) | value; return; }
		assert false;
	}

	public void set(BitField that0) {
		if(!(that0 instanceof BitField32x32x1)) {
			BitField.super.set(that0);
			return;
		}
		var that = (BitField32x32x1) that0;
		this.set0(that);
	}
	protected void set0(BitField32x32x1 that) {
		this._0 = that._0;
		this._1 = that._1;
		this._2 = that._2;
		this._3 = that._3;
		this._4 = that._4;
		this._5 = that._5;
		this._6 = that._6;
		this._7 = that._7;
		this._8 = that._8;
		this._9 = that._9;
		this._10 = that._10;
		this._11 = that._11;
		this._12 = that._12;
		this._13 = that._13;
		this._14 = that._14;
		this._15 = that._15;
	}

	public BitField32x32x1 clone() {
		var result = new BitField32x32x1();
		result.set(this);
		return result;
	}

	public void offsetX(int amount) {
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
		this._0 = (this._0 & -1152921500580315136L) | (this._0 & -64424509456L >> 4);
		this._1 = (this._1 & -1152921500580315136L) | (this._1 & -64424509456L >> 4);
		this._2 = (this._2 & -1152921500580315136L) | (this._2 & -64424509456L >> 4);
		this._3 = (this._3 & -1152921500580315136L) | (this._3 & -64424509456L >> 4);
		this._4 = (this._4 & -1152921500580315136L) | (this._4 & -64424509456L >> 4);
		this._5 = (this._5 & -1152921500580315136L) | (this._5 & -64424509456L >> 4);
		this._6 = (this._6 & -1152921500580315136L) | (this._6 & -64424509456L >> 4);
		this._7 = (this._7 & -1152921500580315136L) | (this._7 & -64424509456L >> 4);
		this._8 = (this._8 & -1152921500580315136L) | (this._8 & -64424509456L >> 4);
		this._9 = (this._9 & -1152921500580315136L) | (this._9 & -64424509456L >> 4);
		this._10 = (this._10 & -1152921500580315136L) | (this._10 & -64424509456L >> 4);
		this._11 = (this._11 & -1152921500580315136L) | (this._11 & -64424509456L >> 4);
		this._12 = (this._12 & -1152921500580315136L) | (this._12 & -64424509456L >> 4);
		this._13 = (this._13 & -1152921500580315136L) | (this._13 & -64424509456L >> 4);
		this._14 = (this._14 & -1152921500580315136L) | (this._14 & -64424509456L >> 4);
		this._15 = (this._15 & -1152921500580315136L) | (this._15 & -64424509456L >> 4);
	}
	protected void offsetPX2() {
		this.offsetPX2_0();
	}
	protected void offsetPX2_0() {
		this._0 = (this._0 & -4611686015206162432L) | (this._0 & -12884901892L >> 2);
		this._1 = (this._1 & -4611686015206162432L) | (this._1 & -12884901892L >> 2);
		this._2 = (this._2 & -4611686015206162432L) | (this._2 & -12884901892L >> 2);
		this._3 = (this._3 & -4611686015206162432L) | (this._3 & -12884901892L >> 2);
		this._4 = (this._4 & -4611686015206162432L) | (this._4 & -12884901892L >> 2);
		this._5 = (this._5 & -4611686015206162432L) | (this._5 & -12884901892L >> 2);
		this._6 = (this._6 & -4611686015206162432L) | (this._6 & -12884901892L >> 2);
		this._7 = (this._7 & -4611686015206162432L) | (this._7 & -12884901892L >> 2);
		this._8 = (this._8 & -4611686015206162432L) | (this._8 & -12884901892L >> 2);
		this._9 = (this._9 & -4611686015206162432L) | (this._9 & -12884901892L >> 2);
		this._10 = (this._10 & -4611686015206162432L) | (this._10 & -12884901892L >> 2);
		this._11 = (this._11 & -4611686015206162432L) | (this._11 & -12884901892L >> 2);
		this._12 = (this._12 & -4611686015206162432L) | (this._12 & -12884901892L >> 2);
		this._13 = (this._13 & -4611686015206162432L) | (this._13 & -12884901892L >> 2);
		this._14 = (this._14 & -4611686015206162432L) | (this._14 & -12884901892L >> 2);
		this._15 = (this._15 & -4611686015206162432L) | (this._15 & -12884901892L >> 2);
	}
	protected void offsetPX1() {
		this.offsetPX1_0();
	}
	protected void offsetPX1_0() {
		this._0 = (this._0 & -9223372034707292160L) | (this._0 & -4294967298L >> 1);
		this._1 = (this._1 & -9223372034707292160L) | (this._1 & -4294967298L >> 1);
		this._2 = (this._2 & -9223372034707292160L) | (this._2 & -4294967298L >> 1);
		this._3 = (this._3 & -9223372034707292160L) | (this._3 & -4294967298L >> 1);
		this._4 = (this._4 & -9223372034707292160L) | (this._4 & -4294967298L >> 1);
		this._5 = (this._5 & -9223372034707292160L) | (this._5 & -4294967298L >> 1);
		this._6 = (this._6 & -9223372034707292160L) | (this._6 & -4294967298L >> 1);
		this._7 = (this._7 & -9223372034707292160L) | (this._7 & -4294967298L >> 1);
		this._8 = (this._8 & -9223372034707292160L) | (this._8 & -4294967298L >> 1);
		this._9 = (this._9 & -9223372034707292160L) | (this._9 & -4294967298L >> 1);
		this._10 = (this._10 & -9223372034707292160L) | (this._10 & -4294967298L >> 1);
		this._11 = (this._11 & -9223372034707292160L) | (this._11 & -4294967298L >> 1);
		this._12 = (this._12 & -9223372034707292160L) | (this._12 & -4294967298L >> 1);
		this._13 = (this._13 & -9223372034707292160L) | (this._13 & -4294967298L >> 1);
		this._14 = (this._14 & -9223372034707292160L) | (this._14 & -4294967298L >> 1);
		this._15 = (this._15 & -9223372034707292160L) | (this._15 & -4294967298L >> 1);
	}
	protected void offsetNX4() {
		this.offsetNX4_0();
	}
	protected void offsetNX4_0() {
		this._0 = (this._0 & 64424509455L) | (this._0 & 1152921500580315135L << 4);
		this._1 = (this._1 & 64424509455L) | (this._1 & 1152921500580315135L << 4);
		this._2 = (this._2 & 64424509455L) | (this._2 & 1152921500580315135L << 4);
		this._3 = (this._3 & 64424509455L) | (this._3 & 1152921500580315135L << 4);
		this._4 = (this._4 & 64424509455L) | (this._4 & 1152921500580315135L << 4);
		this._5 = (this._5 & 64424509455L) | (this._5 & 1152921500580315135L << 4);
		this._6 = (this._6 & 64424509455L) | (this._6 & 1152921500580315135L << 4);
		this._7 = (this._7 & 64424509455L) | (this._7 & 1152921500580315135L << 4);
		this._8 = (this._8 & 64424509455L) | (this._8 & 1152921500580315135L << 4);
		this._9 = (this._9 & 64424509455L) | (this._9 & 1152921500580315135L << 4);
		this._10 = (this._10 & 64424509455L) | (this._10 & 1152921500580315135L << 4);
		this._11 = (this._11 & 64424509455L) | (this._11 & 1152921500580315135L << 4);
		this._12 = (this._12 & 64424509455L) | (this._12 & 1152921500580315135L << 4);
		this._13 = (this._13 & 64424509455L) | (this._13 & 1152921500580315135L << 4);
		this._14 = (this._14 & 64424509455L) | (this._14 & 1152921500580315135L << 4);
		this._15 = (this._15 & 64424509455L) | (this._15 & 1152921500580315135L << 4);
	}
	protected void offsetNX2() {
		this.offsetNX2_0();
	}
	protected void offsetNX2_0() {
		this._0 = (this._0 & 12884901891L) | (this._0 & 4611686015206162431L << 2);
		this._1 = (this._1 & 12884901891L) | (this._1 & 4611686015206162431L << 2);
		this._2 = (this._2 & 12884901891L) | (this._2 & 4611686015206162431L << 2);
		this._3 = (this._3 & 12884901891L) | (this._3 & 4611686015206162431L << 2);
		this._4 = (this._4 & 12884901891L) | (this._4 & 4611686015206162431L << 2);
		this._5 = (this._5 & 12884901891L) | (this._5 & 4611686015206162431L << 2);
		this._6 = (this._6 & 12884901891L) | (this._6 & 4611686015206162431L << 2);
		this._7 = (this._7 & 12884901891L) | (this._7 & 4611686015206162431L << 2);
		this._8 = (this._8 & 12884901891L) | (this._8 & 4611686015206162431L << 2);
		this._9 = (this._9 & 12884901891L) | (this._9 & 4611686015206162431L << 2);
		this._10 = (this._10 & 12884901891L) | (this._10 & 4611686015206162431L << 2);
		this._11 = (this._11 & 12884901891L) | (this._11 & 4611686015206162431L << 2);
		this._12 = (this._12 & 12884901891L) | (this._12 & 4611686015206162431L << 2);
		this._13 = (this._13 & 12884901891L) | (this._13 & 4611686015206162431L << 2);
		this._14 = (this._14 & 12884901891L) | (this._14 & 4611686015206162431L << 2);
		this._15 = (this._15 & 12884901891L) | (this._15 & 4611686015206162431L << 2);
	}
	protected void offsetNX1() {
		this.offsetNX1_0();
	}
	protected void offsetNX1_0() {
		this._0 = (this._0 & 4294967297L) | (this._0 & 9223372034707292159L << 1);
		this._1 = (this._1 & 4294967297L) | (this._1 & 9223372034707292159L << 1);
		this._2 = (this._2 & 4294967297L) | (this._2 & 9223372034707292159L << 1);
		this._3 = (this._3 & 4294967297L) | (this._3 & 9223372034707292159L << 1);
		this._4 = (this._4 & 4294967297L) | (this._4 & 9223372034707292159L << 1);
		this._5 = (this._5 & 4294967297L) | (this._5 & 9223372034707292159L << 1);
		this._6 = (this._6 & 4294967297L) | (this._6 & 9223372034707292159L << 1);
		this._7 = (this._7 & 4294967297L) | (this._7 & 9223372034707292159L << 1);
		this._8 = (this._8 & 4294967297L) | (this._8 & 9223372034707292159L << 1);
		this._9 = (this._9 & 4294967297L) | (this._9 & 9223372034707292159L << 1);
		this._10 = (this._10 & 4294967297L) | (this._10 & 9223372034707292159L << 1);
		this._11 = (this._11 & 4294967297L) | (this._11 & 9223372034707292159L << 1);
		this._12 = (this._12 & 4294967297L) | (this._12 & 9223372034707292159L << 1);
		this._13 = (this._13 & 4294967297L) | (this._13 & 9223372034707292159L << 1);
		this._14 = (this._14 & 4294967297L) | (this._14 & 9223372034707292159L << 1);
		this._15 = (this._15 & 4294967297L) | (this._15 & 9223372034707292159L << 1);
	}

	public void offsetY(int amount) {
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
		this._2 = this._0;
		this._3 = this._1;
		this._4 = this._2;
		this._5 = this._3;
		this._6 = this._4;
		this._7 = this._5;
		this._8 = this._6;
		this._9 = this._7;
		this._10 = this._8;
		this._11 = this._9;
		this._12 = this._10;
		this._13 = this._11;
		this._14 = this._12;
		this._15 = this._13;
	}
	protected void offsetPY2() {
		this.offsetPY2_0();
	}
	protected void offsetPY2_0() {
		this._1 = this._0;
		this._2 = this._1;
		this._3 = this._2;
		this._4 = this._3;
		this._5 = this._4;
		this._6 = this._5;
		this._7 = this._6;
		this._8 = this._7;
		this._9 = this._8;
		this._10 = this._9;
		this._11 = this._10;
		this._12 = this._11;
		this._13 = this._12;
		this._14 = this._13;
		this._15 = this._14;
	}
	protected void offsetPY1() {
		this.offsetPY1_0();
	}
	protected void offsetPY1_0() {
		this._0 = (this._0 & -4294967296L) | (this._0 & -4294967296L >> 32);
		this._1 = (this._1 & 4294967295L) | (this._0 & 4294967295L << 32);
		this._1 = (this._1 & -4294967296L) | (this._1 & -4294967296L >> 32);
		this._2 = (this._2 & 4294967295L) | (this._1 & 4294967295L << 32);
		this._2 = (this._2 & -4294967296L) | (this._2 & -4294967296L >> 32);
		this._3 = (this._3 & 4294967295L) | (this._2 & 4294967295L << 32);
		this._3 = (this._3 & -4294967296L) | (this._3 & -4294967296L >> 32);
		this._4 = (this._4 & 4294967295L) | (this._3 & 4294967295L << 32);
		this._4 = (this._4 & -4294967296L) | (this._4 & -4294967296L >> 32);
		this._5 = (this._5 & 4294967295L) | (this._4 & 4294967295L << 32);
		this._5 = (this._5 & -4294967296L) | (this._5 & -4294967296L >> 32);
		this._6 = (this._6 & 4294967295L) | (this._5 & 4294967295L << 32);
		this._6 = (this._6 & -4294967296L) | (this._6 & -4294967296L >> 32);
		this._7 = (this._7 & 4294967295L) | (this._6 & 4294967295L << 32);
		this._7 = (this._7 & -4294967296L) | (this._7 & -4294967296L >> 32);
		this._8 = (this._8 & 4294967295L) | (this._7 & 4294967295L << 32);
		this._8 = (this._8 & -4294967296L) | (this._8 & -4294967296L >> 32);
		this._9 = (this._9 & 4294967295L) | (this._8 & 4294967295L << 32);
		this._9 = (this._9 & -4294967296L) | (this._9 & -4294967296L >> 32);
		this._10 = (this._10 & 4294967295L) | (this._9 & 4294967295L << 32);
		this._10 = (this._10 & -4294967296L) | (this._10 & -4294967296L >> 32);
		this._11 = (this._11 & 4294967295L) | (this._10 & 4294967295L << 32);
		this._11 = (this._11 & -4294967296L) | (this._11 & -4294967296L >> 32);
		this._12 = (this._12 & 4294967295L) | (this._11 & 4294967295L << 32);
		this._12 = (this._12 & -4294967296L) | (this._12 & -4294967296L >> 32);
		this._13 = (this._13 & 4294967295L) | (this._12 & 4294967295L << 32);
		this._13 = (this._13 & -4294967296L) | (this._13 & -4294967296L >> 32);
		this._14 = (this._14 & 4294967295L) | (this._13 & 4294967295L << 32);
		this._14 = (this._14 & -4294967296L) | (this._14 & -4294967296L >> 32);
		this._15 = (this._15 & 4294967295L) | (this._14 & 4294967295L << 32);
		this._15 = (this._15 & -4294967296L) | (this._15 & -4294967296L >> 32);
	}
	protected void offsetNY4() {
		this.offsetNY4_0();
	}
	protected void offsetNY4_0() {
		this._0 = this._2;
		this._1 = this._3;
		this._2 = this._4;
		this._3 = this._5;
		this._4 = this._6;
		this._5 = this._7;
		this._6 = this._8;
		this._7 = this._9;
		this._8 = this._10;
		this._9 = this._11;
		this._10 = this._12;
		this._11 = this._13;
		this._12 = this._14;
		this._13 = this._15;
	}
	protected void offsetNY2() {
		this.offsetNY2_0();
	}
	protected void offsetNY2_0() {
		this._0 = this._1;
		this._1 = this._2;
		this._2 = this._3;
		this._3 = this._4;
		this._4 = this._5;
		this._5 = this._6;
		this._6 = this._7;
		this._7 = this._8;
		this._8 = this._9;
		this._9 = this._10;
		this._10 = this._11;
		this._11 = this._12;
		this._12 = this._13;
		this._13 = this._14;
		this._14 = this._15;
	}
	protected void offsetNY1() {
		this.offsetNY1_0();
	}
	protected void offsetNY1_0() {
		this._0 = (this._0 & 4294967295L) | (this._0 & 4294967295L << 32);
		this._0 = (this._0 & -4294967296L) | (this._1 & -4294967296L >> 32);
		this._1 = (this._1 & 4294967295L) | (this._1 & 4294967295L << 32);
		this._1 = (this._1 & -4294967296L) | (this._2 & -4294967296L >> 32);
		this._2 = (this._2 & 4294967295L) | (this._2 & 4294967295L << 32);
		this._2 = (this._2 & -4294967296L) | (this._3 & -4294967296L >> 32);
		this._3 = (this._3 & 4294967295L) | (this._3 & 4294967295L << 32);
		this._3 = (this._3 & -4294967296L) | (this._4 & -4294967296L >> 32);
		this._4 = (this._4 & 4294967295L) | (this._4 & 4294967295L << 32);
		this._4 = (this._4 & -4294967296L) | (this._5 & -4294967296L >> 32);
		this._5 = (this._5 & 4294967295L) | (this._5 & 4294967295L << 32);
		this._5 = (this._5 & -4294967296L) | (this._6 & -4294967296L >> 32);
		this._6 = (this._6 & 4294967295L) | (this._6 & 4294967295L << 32);
		this._6 = (this._6 & -4294967296L) | (this._7 & -4294967296L >> 32);
		this._7 = (this._7 & 4294967295L) | (this._7 & 4294967295L << 32);
		this._7 = (this._7 & -4294967296L) | (this._8 & -4294967296L >> 32);
		this._8 = (this._8 & 4294967295L) | (this._8 & 4294967295L << 32);
		this._8 = (this._8 & -4294967296L) | (this._9 & -4294967296L >> 32);
		this._9 = (this._9 & 4294967295L) | (this._9 & 4294967295L << 32);
		this._9 = (this._9 & -4294967296L) | (this._10 & -4294967296L >> 32);
		this._10 = (this._10 & 4294967295L) | (this._10 & 4294967295L << 32);
		this._10 = (this._10 & -4294967296L) | (this._11 & -4294967296L >> 32);
		this._11 = (this._11 & 4294967295L) | (this._11 & 4294967295L << 32);
		this._11 = (this._11 & -4294967296L) | (this._12 & -4294967296L >> 32);
		this._12 = (this._12 & 4294967295L) | (this._12 & 4294967295L << 32);
		this._12 = (this._12 & -4294967296L) | (this._13 & -4294967296L >> 32);
		this._13 = (this._13 & 4294967295L) | (this._13 & 4294967295L << 32);
		this._13 = (this._13 & -4294967296L) | (this._14 & -4294967296L >> 32);
		this._14 = (this._14 & 4294967295L) | (this._14 & 4294967295L << 32);
		this._14 = (this._14 & -4294967296L) | (this._15 & -4294967296L >> 32);
		this._15 = (this._15 & 4294967295L) | (this._15 & 4294967295L << 32);
	}

	public void offsetZ(int amount) {
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

	}

	protected void offsetPZ2() {

	}

	protected void offsetPZ1() {

	}

	protected void offsetNZ4() {

	}

	protected void offsetNZ2() {

	}

	protected void offsetNZ1() {

	}


	public boolean isIntersecting(BitField that0) {
		if(!(that0 instanceof BitField32x32x1))
			return BitField.super.isIntersecting(that0);
		var that = (BitField32x32x1) that0;
		if(this.isIntersecting0(that)) return true;
		return false;
	}
	protected boolean isIntersecting0(BitField32x32x1 that) {
		if((this._0 & that._0) != 0) return true;
		if((this._1 & that._1) != 0) return true;
		if((this._2 & that._2) != 0) return true;
		if((this._3 & that._3) != 0) return true;
		if((this._4 & that._4) != 0) return true;
		if((this._5 & that._5) != 0) return true;
		if((this._6 & that._6) != 0) return true;
		if((this._7 & that._7) != 0) return true;
		if((this._8 & that._8) != 0) return true;
		if((this._9 & that._9) != 0) return true;
		if((this._10 & that._10) != 0) return true;
		if((this._11 & that._11) != 0) return true;
		if((this._12 & that._12) != 0) return true;
		if((this._13 & that._13) != 0) return true;
		if((this._14 & that._14) != 0) return true;
		if((this._15 & that._15) != 0) return true;
		return false;
	}

	public void complement() {
		this.complement0();
	}
	protected void complement0() {
		this._0 = ~this._0;
		this._1 = ~this._1;
		this._2 = ~this._2;
		this._3 = ~this._3;
		this._4 = ~this._4;
		this._5 = ~this._5;
		this._6 = ~this._6;
		this._7 = ~this._7;
		this._8 = ~this._8;
		this._9 = ~this._9;
		this._10 = ~this._10;
		this._11 = ~this._11;
		this._12 = ~this._12;
		this._13 = ~this._13;
		this._14 = ~this._14;
		this._15 = ~this._15;
	}

	public void union(BitField that0) {
		if(!(that0 instanceof BitField32x32x1)) {
			BitField.super.union(that0);
			return;
		}
		var that = (BitField32x32x1) that0;
		this.union0(that);
	}
	protected void union0(BitField32x32x1 that) {
		this._0 |= that._0;
		this._1 |= that._1;
		this._2 |= that._2;
		this._3 |= that._3;
		this._4 |= that._4;
		this._5 |= that._5;
		this._6 |= that._6;
		this._7 |= that._7;
		this._8 |= that._8;
		this._9 |= that._9;
		this._10 |= that._10;
		this._11 |= that._11;
		this._12 |= that._12;
		this._13 |= that._13;
		this._14 |= that._14;
		this._15 |= that._15;
	}

	public void intersect(BitField that0) {
		if(!(that0 instanceof BitField32x32x1)) {
			BitField.super.union(that0);
			return;
		}
		var that = (BitField32x32x1) that0;
		this.intersect0(that);
	}
	protected void intersect0(BitField32x32x1 that) {
		this._0 &= that._0;
		this._1 &= that._1;
		this._2 &= that._2;
		this._3 &= that._3;
		this._4 &= that._4;
		this._5 &= that._5;
		this._6 &= that._6;
		this._7 &= that._7;
		this._8 &= that._8;
		this._9 &= that._9;
		this._10 &= that._10;
		this._11 &= that._11;
		this._12 &= that._12;
		this._13 &= that._13;
		this._14 &= that._14;
		this._15 &= that._15;
	}

	public void exclusive(BitField that0) {
		if(!(that0 instanceof BitField32x32x1)) {
			BitField.super.union(that0);
			return;
		}
		var that = (BitField32x32x1) that0;
		this.exclusive0(that);
	}
	protected void exclusive0(BitField32x32x1 that) {
		this._0 ^= that._0;
		this._1 ^= that._1;
		this._2 ^= that._2;
		this._3 ^= that._3;
		this._4 ^= that._4;
		this._5 ^= that._5;
		this._6 ^= that._6;
		this._7 ^= that._7;
		this._8 ^= that._8;
		this._9 ^= that._9;
		this._10 ^= that._10;
		this._11 ^= that._11;
		this._12 ^= that._12;
		this._13 ^= that._13;
		this._14 ^= that._14;
		this._15 ^= that._15;
	}

	public void subtract(BitField that0) {
		if(!(that0 instanceof BitField32x32x1)) {
			BitField.super.union(that0);
			return;
		}
		var that = (BitField32x32x1) that0;
		this.subtract0(that);
	}
	protected void subtract0(BitField32x32x1 that) {
		this._0 &= ~that._0;
		this._1 &= ~that._1;
		this._2 &= ~that._2;
		this._3 &= ~that._3;
		this._4 &= ~that._4;
		this._5 &= ~that._5;
		this._6 &= ~that._6;
		this._7 &= ~that._7;
		this._8 &= ~that._8;
		this._9 &= ~that._9;
		this._10 &= ~that._10;
		this._11 &= ~that._11;
		this._12 &= ~that._12;
		this._13 &= ~that._13;
		this._14 &= ~that._14;
		this._15 &= ~that._15;
	}


}
