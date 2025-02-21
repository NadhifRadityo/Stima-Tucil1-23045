
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045;

public class BitField20x20x1 extends BitField {
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	public static final int DEPTH = 1;
	protected long _0;
	protected long _1;
	protected long _2;
	protected long _3;
	protected long _4;
	protected long _5;
	protected long _6;

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
		if(position < 128) return (this._1 & mask) != 0;
		if(position < 192) return (this._2 & mask) != 0;
		if(position < 256) return (this._3 & mask) != 0;
		if(position < 320) return (this._4 & mask) != 0;
		if(position < 384) return (this._5 & mask) != 0;
		if(position < 448) return (this._6 & mask) != 0;
		assert false;
		return false;
	}

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
		assert false;
	}

	public void set(BitField that0) {
		if(!(that0 instanceof BitField20x20x1)) {
			super.set(that0);
			return;
		}
		var that = (BitField20x20x1) that0;
		this.set_0(that);
	}
	protected void set_0(BitField20x20x1 that) {
		this._0 = that._0;
		this._1 = that._1;
		this._2 = that._2;
		this._3 = that._3;
		this._4 = that._4;
		this._5 = that._5;
		this._6 = that._6;
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
	}

	public BitField20x20x1 clone() {
		var result = new BitField20x20x1();
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
		long _2 = this._2;
		long _1 = this._1;
		long _5 = this._5;
		long _0 = this._0;
		this.offsetPX4_0(_2, _1, _5, _0);
	}
	protected void offsetPX4_0(long _2, long _1, long _5, long _0) {
		this._6 = (this._6 & -1152640029630136321L) | ((this._6 & -4503599627370496L) >> 4);
		this._5 = (this._5 & -1152905011916701681L) | ((this._5 & -263883042324736L) >> 4);
		this._4 = (this._4 & -72056563244793856L) | ((this._4 & 1152905011916701680L) >> 4);
		this._4 = (this._4 & 1152921504606846975L) | ((this._3 & 15L) << 60);
		this._3 = (this._3 & -1085367445771718656L) | ((this._3 & -1080864941362053136L) >> 4);
		this._2 = (this._2 & -1148699375929651456L) | ((this._2 & -67554058835128336L) >> 4);
		this._1 = (this._1 & -1152657621564522256L) | ((this._1 & -4222128677195536L) >> 4);
		this._0 = (this._0 & -1152905011916701681L) | ((this._0 & -263883042324736L) >> 4);
		this._3 = (this._3 & 1152921504606846975L) | ((_2 & 15L) << 60);
		this._2 = (this._2 & 1152921504606846975L) | ((_1 & 15L) << 60);
		this._6 = (this._6 & 1152921504606846975L) | ((_5 & 15L) << 60);
		this._1 = (this._1 & 1152921504606846975L) | ((_0 & 15L) << 60);
		this._5 = this._5 & 1152905011916701680L;
		this._4 = this._4 & -1080864941362053121L;
		this._3 = this._3 & -67554058835128321L;
		this._2 = this._2 & -4222128677195521L;
		this._1 = this._1 & -263883042324721L;
		this._0 = this._0 & 1152905011916701680L;
	}
	protected void offsetPX2() {
		long _3 = this._3;
		long _2 = this._2;
		long _1 = this._1;
		long _5 = this._5;
		long _0 = this._0;
		this.offsetPX2_0(_3, _2, _1, _5, _0);
	}
	protected void offsetPX2_0(long _3, long _2, long _1, long _5, long _0) {
		this._6 = (this._6 & -4611404543450677249L) | ((this._6 & -1125899906842624L) >> 2);
		this._5 = (this._5 & -4611672824275271668L) | ((this._5 & -52776608464948L) >> 2);
		this._4 = (this._4 & -3746994065337745408L) | ((this._4 & -3458767812358569988L) >> 2);
		this._3 = (this._3 & -4557642771359285248L) | ((this._3 & -216172988272410628L) >> 2);
		this._2 = (this._2 & -4608308315485631488L) | ((this._2 & -13510811767025668L) >> 2);
		this._1 = (this._1 & -4611474911993528128L) | ((this._1 & -844425735439108L) >> 2);
		this._0 = (this._0 & -4611672824275271668L) | ((this._0 & -52776608464948L) >> 2);
		this._4 = (this._4 & 4611686018427387903L) | ((_3 & 3L) << 62);
		this._3 = (this._3 & 4611686018427387903L) | ((_2 & 3L) << 62);
		this._2 = (this._2 & 4611686018427387903L) | ((_1 & 3L) << 62);
		this._6 = (this._6 & 4611686018427387903L) | ((_5 & 3L) << 62);
		this._1 = (this._1 & 4611686018427387903L) | ((_0 & 3L) << 62);
		this._5 = this._5 & 4611672824275271667L;
		this._4 = this._4 & -864691953089642497L;
		this._3 = this._3 & -54043247068102657L;
		this._2 = this._2 & -3377702941756417L;
		this._1 = this._1 & -211106433859777L;
		this._0 = this._0 & 4611672824275271667L;
	}
	protected void offsetPX1() {
		long _3 = this._3;
		long _2 = this._2;
		long _1 = this._1;
		long _5 = this._5;
		long _0 = this._0;
		this.offsetPX1_0(_3, _2, _1, _5, _0);
	}
	protected void offsetPX1_0(long _3, long _2, long _1, long _5, long _0) {
		this._6 = (this._6 & -9223090561878065153L) | ((this._6 & -562949953421312L) >> 1);
		this._5 = (this._5 & -9223363240753364984L) | ((this._5 & -17592202821650L) >> 1);
		this._4 = (this._4 & -8646910734795014144L) | ((this._4 & -1152922604119523330L) >> 1);
		this._3 = (this._3 & -9187343205476040704L) | ((this._3 & -72057662757470210L) >> 1);
		this._2 = (this._2 & -9221120234893604864L) | ((this._2 & -4503603922341890L) >> 1);
		this._1 = (this._1 & -9223231299232202624L) | ((this._1 & -281475245146370L) >> 1);
		this._0 = (this._0 & -9223363240753364984L) | ((this._0 & -17592202821650L) >> 1);
		this._4 = (this._4 & 9223372036854775807L) | ((_3 & 1L) << 63);
		this._3 = (this._3 & 9223372036854775807L) | ((_2 & 1L) << 63);
		this._2 = (this._2 & 9223372036854775807L) | ((_1 & 1L) << 63);
		this._6 = (this._6 & 9223372036854775807L) | ((_5 & 1L) << 63);
		this._1 = (this._1 & 9223372036854775807L) | ((_0 & 1L) << 63);
		this._5 = this._5 & 9223363240753364983L;
		this._4 = this._4 & -576461302059761665L;
		this._3 = this._3 & -36028831378735105L;
		this._2 = this._2 & -2251801961170945L;
		this._1 = this._1 & -140737622573185L;
		this._0 = this._0 & 9223363240753364983L;
	}
	protected void offsetNX4() {
		long _2 = this._2;
		long _3 = this._3;
		long _4 = this._4;
		this.offsetNX4_0(_2, _3, _4);
	}
	protected void offsetNX4_0(long _2, long _3, long _4) {
		this._0 = (this._0 & 263883042324735L) | ((this._0 & 1152905011916701680L) << 4);
		this._0 = (this._0 & -16L) | ((this._1 & -1152921504606846976L) >> 60);
		this._1 = (this._1 & 4222128677195535L) | ((this._1 & 1152657621564522255L) << 4);
		this._2 = (this._2 & 67554058835128335L) | ((this._2 & 1148699375929651455L) << 4);
		this._3 = (this._3 & 1080864941362053135L) | ((this._3 & 1085367445771718655L) << 4);
		this._4 = (this._4 & -1152905011916701681L) | ((this._4 & 72056563244793855L) << 4);
		this._5 = (this._5 & 263883042324735L) | ((this._5 & 1152905011916701680L) << 4);
		this._5 = (this._5 & -16L) | ((this._6 & -1152921504606846976L) >> 60);
		this._1 = (this._1 & -16L) | ((_2 & -1152921504606846976L) >> 60);
		this._6 = (this._6 & 4503599627370495L) | ((this._6 & 1152640029630136320L) << 4);
		this._2 = (this._2 & -16L) | ((_3 & -1152921504606846976L) >> 60);
		this._3 = (this._3 & -16L) | ((_4 & -1152921504606846976L) >> 60);
		this._0 = this._0 & -263883042324721L;
		this._1 = this._1 & -4222128677195521L;
		this._2 = this._2 & -67554058835128321L;
		this._3 = this._3 & -1080864941362053121L;
		this._4 = this._4 & 1152905011916701680L;
		this._5 = this._5 & -263883042324721L;
		this._6 = this._6 & -4222124650659841L;
	}
	protected void offsetNX2() {
		long _1 = this._1;
		long _2 = this._2;
		long _3 = this._3;
		long _4 = this._4;
		this.offsetNX2_0(_1, _2, _3, _4);
	}
	protected void offsetNX2_0(long _1, long _2, long _3, long _4) {
		this._0 = (this._0 & 52776608464947L) | ((this._0 & 4611672824275271667L) << 2);
		this._1 = (this._1 & 844425735439107L) | ((this._1 & 4611474911993528127L) << 2);
		this._2 = (this._2 & 13510811767025667L) | ((this._2 & 4608308315485631487L) << 2);
		this._3 = (this._3 & 216172988272410627L) | ((this._3 & 4557642771359285247L) << 2);
		this._4 = (this._4 & 3458767812358569987L) | ((this._4 & 3746994065337745407L) << 2);
		this._5 = (this._5 & 52776608464947L) | ((this._5 & 4611672824275271667L) << 2);
		this._0 = (this._0 & -4L) | ((_1 & -4611686018427387904L) >> 62);
		this._5 = (this._5 & -4L) | ((this._6 & -4611686018427387904L) >> 62);
		this._6 = (this._6 & 1125899906842623L) | ((this._6 & 4611404543450677248L) << 2);
		this._1 = (this._1 & -4L) | ((_2 & -4611686018427387904L) >> 62);
		this._2 = (this._2 & -4L) | ((_3 & -4611686018427387904L) >> 62);
		this._3 = (this._3 & -4L) | ((_4 & -4611686018427387904L) >> 62);
		this._0 = this._0 & -52776608464945L;
		this._1 = this._1 & -844425735439105L;
		this._2 = this._2 & -13510811767025665L;
		this._3 = this._3 & -216172988272410625L;
		this._4 = this._4 & -3458767812358569988L;
		this._5 = this._5 & -52776608464945L;
		this._6 = this._6 & -844424930131969L;
	}
	protected void offsetNX1() {
		long _1 = this._1;
		long _2 = this._2;
		long _3 = this._3;
		long _4 = this._4;
		this.offsetNX1_0(_1, _2, _3, _4);
	}
	protected void offsetNX1_0(long _1, long _2, long _3, long _4) {
		this._0 = (this._0 & 17592202821649L) | ((this._0 & 9223363240753364983L) << 1);
		this._1 = (this._1 & 281475245146369L) | ((this._1 & 9223231299232202623L) << 1);
		this._2 = (this._2 & 4503603922341889L) | ((this._2 & 9221120234893604863L) << 1);
		this._3 = (this._3 & 72057662757470209L) | ((this._3 & 9187343205476040703L) << 1);
		this._4 = (this._4 & 1152922604119523329L) | ((this._4 & 8646910734795014143L) << 1);
		this._5 = (this._5 & 17592202821649L) | ((this._5 & 9223363240753364983L) << 1);
		this._0 = (this._0 & -2L) | ((_1 & -9223372036854775808L) >> 63);
		this._5 = (this._5 & -2L) | ((this._6 & -9223372036854775808L) >> 63);
		this._6 = (this._6 & 562949953421311L) | ((this._6 & 9223090561878065152L) << 1);
		this._1 = (this._1 & -2L) | ((_2 & -9223372036854775808L) >> 63);
		this._2 = (this._2 & -2L) | ((_3 & -9223372036854775808L) >> 63);
		this._3 = (this._3 & -2L) | ((_4 & -9223372036854775808L) >> 63);
		this._0 = this._0 & -17592202821649L;
		this._1 = this._1 & -281475245146369L;
		this._2 = this._2 & -4503603922341889L;
		this._3 = this._3 & -72057662757470209L;
		this._4 = this._4 & -1152922604119523330L;
		this._5 = this._5 & -17592202821649L;
		this._6 = this._6 & -281474976710657L;
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
		long _3 = this._3;
		this.offsetPY4_0(_3);
	}
	protected void offsetPY4_0(long _3) {
		this._6 = (this._6 & 281474976710655L) | ((this._4 & 65535L) << 48);
		this._5 = (this._5 & -281474976710656L) | ((this._4 & -65536L) >> 16);
		this._4 = (this._4 & -281474976710656L) | ((this._3 & -65536L) >> 16);
		this._4 = (this._4 & 281474976710655L) | ((this._2 & 65535L) << 48);
		this._3 = (this._3 & -281474976710656L) | ((this._2 & -65536L) >> 16);
		this._3 = (this._3 & 281474976710655L) | ((this._1 & 65535L) << 48);
		this._2 = (this._2 & -281474976710656L) | ((this._1 & -65536L) >> 16);
		this._2 = (this._2 & 281474976710655L) | ((this._0 & 65535L) << 48);
		this._1 = (this._1 & -281474976710656L) | ((this._0 & -65536L) >> 16);
		this._5 = (this._5 & 281474976710655L) | ((_3 & 65535L) << 48);
		this._1 = this._1 & 281474976710655L;
		this._0 = 0;
	}
	protected void offsetPY2() {

		this.offsetPY2_0();
	}
	protected void offsetPY2_0() {
		this._6 = (this._6 & 281474976710655L) | ((this._5 & 1099494850560L) << 24);
		this._5 = (this._5 & -16777216L) | ((this._5 & -1099511627776L) >> 40);
		this._5 = (this._5 & 16777215L) | ((this._4 & 1099511627775L) << 24);
		this._4 = (this._4 & -16777216L) | ((this._4 & -1099511627776L) >> 40);
		this._4 = (this._4 & 16777215L) | ((this._3 & 1099511627775L) << 24);
		this._3 = (this._3 & -16777216L) | ((this._3 & -1099511627776L) >> 40);
		this._3 = (this._3 & 16777215L) | ((this._2 & 1099511627775L) << 24);
		this._2 = (this._2 & -16777216L) | ((this._2 & -1099511627776L) >> 40);
		this._2 = (this._2 & 16777215L) | ((this._1 & 1099511627775L) << 24);
		this._1 = (this._1 & -16777216L) | ((this._1 & -1099511627776L) >> 40);
		this._1 = (this._1 & 16777215L) | ((this._0 & 1099511627775L) << 24);
		this._0 = (this._0 & -16777216L) | ((this._0 & -1099511627776L) >> 40);
		this._0 = this._0 & 16777215L;
	}
	protected void offsetPY1() {

		this.offsetPY1_0();
	}
	protected void offsetPY1_0() {
		this._6 = (this._6 & 281474976710655L) | ((this._5 & 1048560L) << 44);
		this._5 = (this._5 & -17592186044416L) | ((this._5 & -1048576L) >> 20);
		this._5 = (this._5 & 17592186044415L) | ((this._4 & 1048575L) << 44);
		this._4 = (this._4 & -17592186044416L) | ((this._4 & -1048576L) >> 20);
		this._4 = (this._4 & 17592186044415L) | ((this._3 & 1048575L) << 44);
		this._3 = (this._3 & -17592186044416L) | ((this._3 & -1048576L) >> 20);
		this._3 = (this._3 & 17592186044415L) | ((this._2 & 1048575L) << 44);
		this._2 = (this._2 & -17592186044416L) | ((this._2 & -1048576L) >> 20);
		this._2 = (this._2 & 17592186044415L) | ((this._1 & 1048575L) << 44);
		this._1 = (this._1 & -17592186044416L) | ((this._1 & -1048576L) >> 20);
		this._1 = (this._1 & 17592186044415L) | ((this._0 & 1048575L) << 44);
		this._0 = (this._0 & -17592186044416L) | ((this._0 & -1048576L) >> 20);
		this._0 = this._0 & 17592186044415L;
	}
	protected void offsetNY4() {

		this.offsetNY4_0();
	}
	protected void offsetNY4_0() {
		this._0 = (this._0 & 65535L) | ((this._1 & 281474976710655L) << 16);
		this._0 = (this._0 & -65536L) | ((this._2 & -281474976710656L) >> 48);
		this._1 = (this._1 & 65535L) | ((this._2 & 281474976710655L) << 16);
		this._1 = (this._1 & -65536L) | ((this._3 & -281474976710656L) >> 48);
		this._2 = (this._2 & 65535L) | ((this._3 & 281474976710655L) << 16);
		this._2 = (this._2 & -65536L) | ((this._4 & -281474976710656L) >> 48);
		this._3 = (this._3 & 65535L) | ((this._4 & 281474976710655L) << 16);
		this._3 = (this._3 & -65536L) | ((this._5 & -281474976710656L) >> 48);
		this._4 = (this._4 & 65535L) | ((this._5 & 281474976710655L) << 16);
		this._4 = (this._4 & -65536L) | ((this._6 & -281474976710656L) >> 48);
		this._5 = 0;
		this._6 = this._6 & 281474976710655L;
	}
	protected void offsetNY2() {

		this.offsetNY2_0();
	}
	protected void offsetNY2_0() {
		this._0 = (this._0 & 1099511627775L) | ((this._0 & 16777215L) << 40);
		this._0 = (this._0 & -1099511627776L) | ((this._1 & -16777216L) >> 24);
		this._1 = (this._1 & 1099511627775L) | ((this._1 & 16777215L) << 40);
		this._1 = (this._1 & -1099511627776L) | ((this._2 & -16777216L) >> 24);
		this._2 = (this._2 & 1099511627775L) | ((this._2 & 16777215L) << 40);
		this._2 = (this._2 & -1099511627776L) | ((this._3 & -16777216L) >> 24);
		this._3 = (this._3 & 1099511627775L) | ((this._3 & 16777215L) << 40);
		this._3 = (this._3 & -1099511627776L) | ((this._4 & -16777216L) >> 24);
		this._4 = (this._4 & 1099511627775L) | ((this._4 & 16777215L) << 40);
		this._4 = (this._4 & -1099511627776L) | ((this._5 & -16777216L) >> 24);
		this._5 = (this._5 & 1099511627775L) | ((this._5 & 16777215L) << 40);
		this._5 = (this._5 & -1099494850561L) | ((this._6 & -281474976710656L) >> 24);
		this._5 = this._5 & -16777216L;
		this._6 = this._6 & 281474976710655L;
	}
	protected void offsetNY1() {

		this.offsetNY1_0();
	}
	protected void offsetNY1_0() {
		this._0 = (this._0 & 1048575L) | ((this._0 & 17592186044415L) << 20);
		this._0 = (this._0 & -1048576L) | ((this._1 & -17592186044416L) >> 44);
		this._1 = (this._1 & 1048575L) | ((this._1 & 17592186044415L) << 20);
		this._1 = (this._1 & -1048576L) | ((this._2 & -17592186044416L) >> 44);
		this._2 = (this._2 & 1048575L) | ((this._2 & 17592186044415L) << 20);
		this._2 = (this._2 & -1048576L) | ((this._3 & -17592186044416L) >> 44);
		this._3 = (this._3 & 1048575L) | ((this._3 & 17592186044415L) << 20);
		this._3 = (this._3 & -1048576L) | ((this._4 & -17592186044416L) >> 44);
		this._4 = (this._4 & 1048575L) | ((this._4 & 17592186044415L) << 20);
		this._4 = (this._4 & -1048576L) | ((this._5 & -17592186044416L) >> 44);
		this._5 = (this._5 & 1048575L) | ((this._5 & 17592186044415L) << 20);
		this._5 = (this._5 & -1048561L) | ((this._6 & -281474976710656L) >> 44);
		this._5 = this._5 & -16L;
		this._6 = this._6 & 281474976710655L;
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
		this._0 = 0;
		this._1 = 0;
		this._2 = 0;
		this._3 = 0;
		this._4 = 0;
		this._5 = 0;
		this._6 = this._6 & 281474976710655L;
	}
	protected void offsetPZ2() {

		this.offsetPZ2_0();
	}
	protected void offsetPZ2_0() {
		this._0 = 0;
		this._1 = 0;
		this._2 = 0;
		this._3 = 0;
		this._4 = 0;
		this._5 = 0;
		this._6 = this._6 & 281474976710655L;
	}
	protected void offsetPZ1() {

		this.offsetPZ1_0();
	}
	protected void offsetPZ1_0() {
		this._0 = 0;
		this._1 = 0;
		this._2 = 0;
		this._3 = 0;
		this._4 = 0;
		this._5 = 0;
		this._6 = this._6 & 281474976710655L;
	}
	protected void offsetNZ4() {

		this.offsetNZ4_0();
	}
	protected void offsetNZ4_0() {
		this._0 = 0;
		this._1 = 0;
		this._2 = 0;
		this._3 = 0;
		this._4 = 0;
		this._5 = 0;
		this._6 = this._6 & 281474976710655L;
	}
	protected void offsetNZ2() {

		this.offsetNZ2_0();
	}
	protected void offsetNZ2_0() {
		this._0 = 0;
		this._1 = 0;
		this._2 = 0;
		this._3 = 0;
		this._4 = 0;
		this._5 = 0;
		this._6 = this._6 & 281474976710655L;
	}
	protected void offsetNZ1() {

		this.offsetNZ1_0();
	}
	protected void offsetNZ1_0() {
		this._0 = 0;
		this._1 = 0;
		this._2 = 0;
		this._3 = 0;
		this._4 = 0;
		this._5 = 0;
		this._6 = this._6 & 281474976710655L;
	}

	public boolean isIntersecting(BitField that0) {
		if(!(that0 instanceof BitField20x20x1))
			return super.isIntersecting(that0);
		var that = (BitField20x20x1) that0;
		if(this.isIntersecting_0(that)) return true;
		return false;
	}
	protected boolean isIntersecting_0(BitField20x20x1 that) {
		if((this._0 & that._0) != 0) return true;
		if((this._1 & that._1) != 0) return true;
		if((this._2 & that._2) != 0) return true;
		if((this._3 & that._3) != 0) return true;
		if((this._4 & that._4) != 0) return true;
		if((this._5 & that._5) != 0) return true;
		if((this._6 & that._6) != 0) return true;
		return false;
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
	}

	public void union(BitField that0) {
		if(!(that0 instanceof BitField20x20x1)) {
			super.union(that0);
			return;
		}
		var that = (BitField20x20x1) that0;
		this.union_0(that);
	}
	protected void union_0(BitField20x20x1 that) {
		this._0 |= that._0;
		this._1 |= that._1;
		this._2 |= that._2;
		this._3 |= that._3;
		this._4 |= that._4;
		this._5 |= that._5;
		this._6 |= that._6;
	}

	public void intersect(BitField that0) {
		if(!(that0 instanceof BitField20x20x1)) {
			super.union(that0);
			return;
		}
		var that = (BitField20x20x1) that0;
		this.intersect_0(that);
	}
	protected void intersect_0(BitField20x20x1 that) {
		this._0 &= that._0;
		this._1 &= that._1;
		this._2 &= that._2;
		this._3 &= that._3;
		this._4 &= that._4;
		this._5 &= that._5;
		this._6 &= that._6;
	}

	public void exclusive(BitField that0) {
		if(!(that0 instanceof BitField20x20x1)) {
			super.union(that0);
			return;
		}
		var that = (BitField20x20x1) that0;
		this.exclusive_0(that);
	}
	protected void exclusive_0(BitField20x20x1 that) {
		this._0 ^= that._0;
		this._1 ^= that._1;
		this._2 ^= that._2;
		this._3 ^= that._3;
		this._4 ^= that._4;
		this._5 ^= that._5;
		this._6 ^= that._6;
	}

	public void subtract(BitField that0) {
		if(!(that0 instanceof BitField20x20x1)) {
			super.union(that0);
			return;
		}
		var that = (BitField20x20x1) that0;
		this.subtract_0(that);
	}
	protected void subtract_0(BitField20x20x1 that) {
		this._0 &= ~that._0;
		this._1 &= ~that._1;
		this._2 &= ~that._2;
		this._3 &= ~that._3;
		this._4 &= ~that._4;
		this._5 &= ~that._5;
		this._6 &= ~that._6;
	}


}
