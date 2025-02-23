
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

public abstract class BitField8x8x1Impl implements BitField {
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	public static final int DEPTH = 1;
	protected long _0;

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
		assert false;
		return false;
	}
	protected boolean getValue_0(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 64) return (this._0 & mask) != 0;
		assert false;
		return false;
	}

	public abstract ImmutableBitField8x8x1Impl toImmutable();
	public abstract MutableBitField8x8x1Impl toMutable();
	public abstract BitField clone();

	public boolean equals(BitField that0) {
		if(!(that0 instanceof BitField8x8x1Impl))
			return BitField.super.equals(that0);
		var that = (BitField8x8x1Impl) that0;
		if(!this.equals_0(that)) return false;
		return true;
	}
	protected boolean equals_0(BitField8x8x1Impl that) {
		if(this._0 != that._0) return false;
		return true;
	}

	public int count() {
		var result = 0;
		result += this.count_0();
		return result;
	}
	protected int count_0() {
		var result = 0;
		result += Long.bitCount(this._0);
		return result;
	}

	public boolean isIntersecting(BitField that0) {
		if(!(that0 instanceof BitField8x8x1Impl))
			return BitField.super.isIntersecting(that0);
		var that = (BitField8x8x1Impl) that0;
		if(this.isIntersecting_0(that)) return true;
		return false;
	}
	protected boolean isIntersecting_0(BitField8x8x1Impl that) {
		if((this._0 & that._0) != 0) return true;
		return false;
	}
}
