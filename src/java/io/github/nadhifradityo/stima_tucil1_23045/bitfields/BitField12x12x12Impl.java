
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

public abstract class BitField12x12x12Impl implements BitField {
	public static final int WIDTH = 12;
	public static final int HEIGHT = 12;
	public static final int DEPTH = 12;
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
	protected long _16;
	protected long _17;
	protected long _18;
	protected long _19;
	protected long _20;
	protected long _21;
	protected long _22;
	protected long _23;
	protected long _24;
	protected long _25;
	protected long _26;

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
		if(position < 1024) return this.getValue_0(position);
		if(position < 2048) return this.getValue_1(position);
		assert false;
		return false;
	}
	protected boolean getValue_0(int position) {
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
	protected boolean getValue_1(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 1088) return (this._16 & mask) != 0;
		if(position < 1152) return (this._17 & mask) != 0;
		if(position < 1216) return (this._18 & mask) != 0;
		if(position < 1280) return (this._19 & mask) != 0;
		if(position < 1344) return (this._20 & mask) != 0;
		if(position < 1408) return (this._21 & mask) != 0;
		if(position < 1472) return (this._22 & mask) != 0;
		if(position < 1536) return (this._23 & mask) != 0;
		if(position < 1600) return (this._24 & mask) != 0;
		if(position < 1664) return (this._25 & mask) != 0;
		if(position < 1728) return (this._26 & mask) != 0;
		assert false;
		return false;
	}

	public abstract ImmutableBitField12x12x12Impl toImmutable();
	public abstract MutableBitField12x12x12Impl toMutable();
	public abstract BitField clone();

	public boolean equals(BitField that0) {
		if(!(that0 instanceof BitField12x12x12Impl))
			return BitField.super.equals(that0);
		var that = (BitField12x12x12Impl) that0;
		if(!this.equals_0(that)) return false;
		if(!this.equals_1(that)) return false;
		return true;
	}
	protected boolean equals_0(BitField12x12x12Impl that) {
		if(this._0 != that._0) return false;
		if(this._1 != that._1) return false;
		if(this._2 != that._2) return false;
		if(this._3 != that._3) return false;
		if(this._4 != that._4) return false;
		if(this._5 != that._5) return false;
		if(this._6 != that._6) return false;
		if(this._7 != that._7) return false;
		if(this._8 != that._8) return false;
		if(this._9 != that._9) return false;
		if(this._10 != that._10) return false;
		if(this._11 != that._11) return false;
		if(this._12 != that._12) return false;
		if(this._13 != that._13) return false;
		if(this._14 != that._14) return false;
		if(this._15 != that._15) return false;
		return true;
	}
	protected boolean equals_1(BitField12x12x12Impl that) {
		if(this._16 != that._16) return false;
		if(this._17 != that._17) return false;
		if(this._18 != that._18) return false;
		if(this._19 != that._19) return false;
		if(this._20 != that._20) return false;
		if(this._21 != that._21) return false;
		if(this._22 != that._22) return false;
		if(this._23 != that._23) return false;
		if(this._24 != that._24) return false;
		if(this._25 != that._25) return false;
		if(this._26 != that._26) return false;
		return true;
	}

	public int count() {
		var result = 0;
		result += this.count_0();
		result += this.count_1();
		return result;
	}
	protected int count_0() {
		var result = 0;
		result += Long.bitCount(this._0);
		result += Long.bitCount(this._1);
		result += Long.bitCount(this._2);
		result += Long.bitCount(this._3);
		result += Long.bitCount(this._4);
		result += Long.bitCount(this._5);
		result += Long.bitCount(this._6);
		result += Long.bitCount(this._7);
		result += Long.bitCount(this._8);
		result += Long.bitCount(this._9);
		result += Long.bitCount(this._10);
		result += Long.bitCount(this._11);
		result += Long.bitCount(this._12);
		result += Long.bitCount(this._13);
		result += Long.bitCount(this._14);
		result += Long.bitCount(this._15);
		return result;
	}
	protected int count_1() {
		var result = 0;
		result += Long.bitCount(this._16);
		result += Long.bitCount(this._17);
		result += Long.bitCount(this._18);
		result += Long.bitCount(this._19);
		result += Long.bitCount(this._20);
		result += Long.bitCount(this._21);
		result += Long.bitCount(this._22);
		result += Long.bitCount(this._23);
		result += Long.bitCount(this._24);
		result += Long.bitCount(this._25);
		result += Long.bitCount(this._26);
		return result;
	}

	public boolean isIntersecting(BitField that0) {
		if(!(that0 instanceof BitField12x12x12Impl))
			return BitField.super.isIntersecting(that0);
		var that = (BitField12x12x12Impl) that0;
		if(this.isIntersecting_0(that)) return true;
		if(this.isIntersecting_1(that)) return true;
		return false;
	}
	protected boolean isIntersecting_0(BitField12x12x12Impl that) {
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
	protected boolean isIntersecting_1(BitField12x12x12Impl that) {
		if((this._16 & that._16) != 0) return true;
		if((this._17 & that._17) != 0) return true;
		if((this._18 & that._18) != 0) return true;
		if((this._19 & that._19) != 0) return true;
		if((this._20 & that._20) != 0) return true;
		if((this._21 & that._21) != 0) return true;
		if((this._22 & that._22) != 0) return true;
		if((this._23 & that._23) != 0) return true;
		if((this._24 & that._24) != 0) return true;
		if((this._25 & that._25) != 0) return true;
		if((this._26 & that._26) != 0) return true;
		return false;
	}
}
