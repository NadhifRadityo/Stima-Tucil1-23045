
// Do NOT edit this file. This file was generated.

package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

public abstract class BitField32x32x8Impl implements BitField {
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	public static final int DEPTH = 8;
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
	protected long _27;
	protected long _28;
	protected long _29;
	protected long _30;
	protected long _31;
	protected long _32;
	protected long _33;
	protected long _34;
	protected long _35;
	protected long _36;
	protected long _37;
	protected long _38;
	protected long _39;
	protected long _40;
	protected long _41;
	protected long _42;
	protected long _43;
	protected long _44;
	protected long _45;
	protected long _46;
	protected long _47;
	protected long _48;
	protected long _49;
	protected long _50;
	protected long _51;
	protected long _52;
	protected long _53;
	protected long _54;
	protected long _55;
	protected long _56;
	protected long _57;
	protected long _58;
	protected long _59;
	protected long _60;
	protected long _61;
	protected long _62;
	protected long _63;
	protected long _64;
	protected long _65;
	protected long _66;
	protected long _67;
	protected long _68;
	protected long _69;
	protected long _70;
	protected long _71;
	protected long _72;
	protected long _73;
	protected long _74;
	protected long _75;
	protected long _76;
	protected long _77;
	protected long _78;
	protected long _79;
	protected long _80;
	protected long _81;
	protected long _82;
	protected long _83;
	protected long _84;
	protected long _85;
	protected long _86;
	protected long _87;
	protected long _88;
	protected long _89;
	protected long _90;
	protected long _91;
	protected long _92;
	protected long _93;
	protected long _94;
	protected long _95;
	protected long _96;
	protected long _97;
	protected long _98;
	protected long _99;
	protected long _100;
	protected long _101;
	protected long _102;
	protected long _103;
	protected long _104;
	protected long _105;
	protected long _106;
	protected long _107;
	protected long _108;
	protected long _109;
	protected long _110;
	protected long _111;
	protected long _112;
	protected long _113;
	protected long _114;
	protected long _115;
	protected long _116;
	protected long _117;
	protected long _118;
	protected long _119;
	protected long _120;
	protected long _121;
	protected long _122;
	protected long _123;
	protected long _124;
	protected long _125;
	protected long _126;
	protected long _127;

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
		if(position < 3072) return this.getValue_2(position);
		if(position < 4096) return this.getValue_3(position);
		if(position < 5120) return this.getValue_4(position);
		if(position < 6144) return this.getValue_5(position);
		if(position < 7168) return this.getValue_6(position);
		if(position < 8192) return this.getValue_7(position);
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
		if(position < 1792) return (this._27 & mask) != 0;
		if(position < 1856) return (this._28 & mask) != 0;
		if(position < 1920) return (this._29 & mask) != 0;
		if(position < 1984) return (this._30 & mask) != 0;
		if(position < 2048) return (this._31 & mask) != 0;
		assert false;
		return false;
	}
	protected boolean getValue_2(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 2112) return (this._32 & mask) != 0;
		if(position < 2176) return (this._33 & mask) != 0;
		if(position < 2240) return (this._34 & mask) != 0;
		if(position < 2304) return (this._35 & mask) != 0;
		if(position < 2368) return (this._36 & mask) != 0;
		if(position < 2432) return (this._37 & mask) != 0;
		if(position < 2496) return (this._38 & mask) != 0;
		if(position < 2560) return (this._39 & mask) != 0;
		if(position < 2624) return (this._40 & mask) != 0;
		if(position < 2688) return (this._41 & mask) != 0;
		if(position < 2752) return (this._42 & mask) != 0;
		if(position < 2816) return (this._43 & mask) != 0;
		if(position < 2880) return (this._44 & mask) != 0;
		if(position < 2944) return (this._45 & mask) != 0;
		if(position < 3008) return (this._46 & mask) != 0;
		if(position < 3072) return (this._47 & mask) != 0;
		assert false;
		return false;
	}
	protected boolean getValue_3(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 3136) return (this._48 & mask) != 0;
		if(position < 3200) return (this._49 & mask) != 0;
		if(position < 3264) return (this._50 & mask) != 0;
		if(position < 3328) return (this._51 & mask) != 0;
		if(position < 3392) return (this._52 & mask) != 0;
		if(position < 3456) return (this._53 & mask) != 0;
		if(position < 3520) return (this._54 & mask) != 0;
		if(position < 3584) return (this._55 & mask) != 0;
		if(position < 3648) return (this._56 & mask) != 0;
		if(position < 3712) return (this._57 & mask) != 0;
		if(position < 3776) return (this._58 & mask) != 0;
		if(position < 3840) return (this._59 & mask) != 0;
		if(position < 3904) return (this._60 & mask) != 0;
		if(position < 3968) return (this._61 & mask) != 0;
		if(position < 4032) return (this._62 & mask) != 0;
		if(position < 4096) return (this._63 & mask) != 0;
		assert false;
		return false;
	}
	protected boolean getValue_4(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 4160) return (this._64 & mask) != 0;
		if(position < 4224) return (this._65 & mask) != 0;
		if(position < 4288) return (this._66 & mask) != 0;
		if(position < 4352) return (this._67 & mask) != 0;
		if(position < 4416) return (this._68 & mask) != 0;
		if(position < 4480) return (this._69 & mask) != 0;
		if(position < 4544) return (this._70 & mask) != 0;
		if(position < 4608) return (this._71 & mask) != 0;
		if(position < 4672) return (this._72 & mask) != 0;
		if(position < 4736) return (this._73 & mask) != 0;
		if(position < 4800) return (this._74 & mask) != 0;
		if(position < 4864) return (this._75 & mask) != 0;
		if(position < 4928) return (this._76 & mask) != 0;
		if(position < 4992) return (this._77 & mask) != 0;
		if(position < 5056) return (this._78 & mask) != 0;
		if(position < 5120) return (this._79 & mask) != 0;
		assert false;
		return false;
	}
	protected boolean getValue_5(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 5184) return (this._80 & mask) != 0;
		if(position < 5248) return (this._81 & mask) != 0;
		if(position < 5312) return (this._82 & mask) != 0;
		if(position < 5376) return (this._83 & mask) != 0;
		if(position < 5440) return (this._84 & mask) != 0;
		if(position < 5504) return (this._85 & mask) != 0;
		if(position < 5568) return (this._86 & mask) != 0;
		if(position < 5632) return (this._87 & mask) != 0;
		if(position < 5696) return (this._88 & mask) != 0;
		if(position < 5760) return (this._89 & mask) != 0;
		if(position < 5824) return (this._90 & mask) != 0;
		if(position < 5888) return (this._91 & mask) != 0;
		if(position < 5952) return (this._92 & mask) != 0;
		if(position < 6016) return (this._93 & mask) != 0;
		if(position < 6080) return (this._94 & mask) != 0;
		if(position < 6144) return (this._95 & mask) != 0;
		assert false;
		return false;
	}
	protected boolean getValue_6(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 6208) return (this._96 & mask) != 0;
		if(position < 6272) return (this._97 & mask) != 0;
		if(position < 6336) return (this._98 & mask) != 0;
		if(position < 6400) return (this._99 & mask) != 0;
		if(position < 6464) return (this._100 & mask) != 0;
		if(position < 6528) return (this._101 & mask) != 0;
		if(position < 6592) return (this._102 & mask) != 0;
		if(position < 6656) return (this._103 & mask) != 0;
		if(position < 6720) return (this._104 & mask) != 0;
		if(position < 6784) return (this._105 & mask) != 0;
		if(position < 6848) return (this._106 & mask) != 0;
		if(position < 6912) return (this._107 & mask) != 0;
		if(position < 6976) return (this._108 & mask) != 0;
		if(position < 7040) return (this._109 & mask) != 0;
		if(position < 7104) return (this._110 & mask) != 0;
		if(position < 7168) return (this._111 & mask) != 0;
		assert false;
		return false;
	}
	protected boolean getValue_7(int position) {
		long mask = 1L << (63 - position % 64);
		if(position < 7232) return (this._112 & mask) != 0;
		if(position < 7296) return (this._113 & mask) != 0;
		if(position < 7360) return (this._114 & mask) != 0;
		if(position < 7424) return (this._115 & mask) != 0;
		if(position < 7488) return (this._116 & mask) != 0;
		if(position < 7552) return (this._117 & mask) != 0;
		if(position < 7616) return (this._118 & mask) != 0;
		if(position < 7680) return (this._119 & mask) != 0;
		if(position < 7744) return (this._120 & mask) != 0;
		if(position < 7808) return (this._121 & mask) != 0;
		if(position < 7872) return (this._122 & mask) != 0;
		if(position < 7936) return (this._123 & mask) != 0;
		if(position < 8000) return (this._124 & mask) != 0;
		if(position < 8064) return (this._125 & mask) != 0;
		if(position < 8128) return (this._126 & mask) != 0;
		if(position < 8192) return (this._127 & mask) != 0;
		assert false;
		return false;
	}

	public abstract ImmutableBitField32x32x8Impl toImmutable();
	public abstract MutableBitField32x32x8Impl toMutable();
	public abstract BitField clone();

	public boolean equals(BitField that0) {
		if(!(that0 instanceof BitField32x32x8Impl))
			return BitField.super.equals(that0);
		var that = (BitField32x32x8Impl) that0;
		if(!this.equals_0(that)) return false;
		if(!this.equals_1(that)) return false;
		if(!this.equals_2(that)) return false;
		if(!this.equals_3(that)) return false;
		if(!this.equals_4(that)) return false;
		if(!this.equals_5(that)) return false;
		if(!this.equals_6(that)) return false;
		if(!this.equals_7(that)) return false;
		return true;
	}
	protected boolean equals_0(BitField32x32x8Impl that) {
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
	protected boolean equals_1(BitField32x32x8Impl that) {
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
		if(this._27 != that._27) return false;
		if(this._28 != that._28) return false;
		if(this._29 != that._29) return false;
		if(this._30 != that._30) return false;
		if(this._31 != that._31) return false;
		return true;
	}
	protected boolean equals_2(BitField32x32x8Impl that) {
		if(this._32 != that._32) return false;
		if(this._33 != that._33) return false;
		if(this._34 != that._34) return false;
		if(this._35 != that._35) return false;
		if(this._36 != that._36) return false;
		if(this._37 != that._37) return false;
		if(this._38 != that._38) return false;
		if(this._39 != that._39) return false;
		if(this._40 != that._40) return false;
		if(this._41 != that._41) return false;
		if(this._42 != that._42) return false;
		if(this._43 != that._43) return false;
		if(this._44 != that._44) return false;
		if(this._45 != that._45) return false;
		if(this._46 != that._46) return false;
		if(this._47 != that._47) return false;
		return true;
	}
	protected boolean equals_3(BitField32x32x8Impl that) {
		if(this._48 != that._48) return false;
		if(this._49 != that._49) return false;
		if(this._50 != that._50) return false;
		if(this._51 != that._51) return false;
		if(this._52 != that._52) return false;
		if(this._53 != that._53) return false;
		if(this._54 != that._54) return false;
		if(this._55 != that._55) return false;
		if(this._56 != that._56) return false;
		if(this._57 != that._57) return false;
		if(this._58 != that._58) return false;
		if(this._59 != that._59) return false;
		if(this._60 != that._60) return false;
		if(this._61 != that._61) return false;
		if(this._62 != that._62) return false;
		if(this._63 != that._63) return false;
		return true;
	}
	protected boolean equals_4(BitField32x32x8Impl that) {
		if(this._64 != that._64) return false;
		if(this._65 != that._65) return false;
		if(this._66 != that._66) return false;
		if(this._67 != that._67) return false;
		if(this._68 != that._68) return false;
		if(this._69 != that._69) return false;
		if(this._70 != that._70) return false;
		if(this._71 != that._71) return false;
		if(this._72 != that._72) return false;
		if(this._73 != that._73) return false;
		if(this._74 != that._74) return false;
		if(this._75 != that._75) return false;
		if(this._76 != that._76) return false;
		if(this._77 != that._77) return false;
		if(this._78 != that._78) return false;
		if(this._79 != that._79) return false;
		return true;
	}
	protected boolean equals_5(BitField32x32x8Impl that) {
		if(this._80 != that._80) return false;
		if(this._81 != that._81) return false;
		if(this._82 != that._82) return false;
		if(this._83 != that._83) return false;
		if(this._84 != that._84) return false;
		if(this._85 != that._85) return false;
		if(this._86 != that._86) return false;
		if(this._87 != that._87) return false;
		if(this._88 != that._88) return false;
		if(this._89 != that._89) return false;
		if(this._90 != that._90) return false;
		if(this._91 != that._91) return false;
		if(this._92 != that._92) return false;
		if(this._93 != that._93) return false;
		if(this._94 != that._94) return false;
		if(this._95 != that._95) return false;
		return true;
	}
	protected boolean equals_6(BitField32x32x8Impl that) {
		if(this._96 != that._96) return false;
		if(this._97 != that._97) return false;
		if(this._98 != that._98) return false;
		if(this._99 != that._99) return false;
		if(this._100 != that._100) return false;
		if(this._101 != that._101) return false;
		if(this._102 != that._102) return false;
		if(this._103 != that._103) return false;
		if(this._104 != that._104) return false;
		if(this._105 != that._105) return false;
		if(this._106 != that._106) return false;
		if(this._107 != that._107) return false;
		if(this._108 != that._108) return false;
		if(this._109 != that._109) return false;
		if(this._110 != that._110) return false;
		if(this._111 != that._111) return false;
		return true;
	}
	protected boolean equals_7(BitField32x32x8Impl that) {
		if(this._112 != that._112) return false;
		if(this._113 != that._113) return false;
		if(this._114 != that._114) return false;
		if(this._115 != that._115) return false;
		if(this._116 != that._116) return false;
		if(this._117 != that._117) return false;
		if(this._118 != that._118) return false;
		if(this._119 != that._119) return false;
		if(this._120 != that._120) return false;
		if(this._121 != that._121) return false;
		if(this._122 != that._122) return false;
		if(this._123 != that._123) return false;
		if(this._124 != that._124) return false;
		if(this._125 != that._125) return false;
		if(this._126 != that._126) return false;
		if(this._127 != that._127) return false;
		return true;
	}

	public int count() {
		var result = 0;
		result += this.count_0();
		result += this.count_1();
		result += this.count_2();
		result += this.count_3();
		result += this.count_4();
		result += this.count_5();
		result += this.count_6();
		result += this.count_7();
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
		result += Long.bitCount(this._27);
		result += Long.bitCount(this._28);
		result += Long.bitCount(this._29);
		result += Long.bitCount(this._30);
		result += Long.bitCount(this._31);
		return result;
	}
	protected int count_2() {
		var result = 0;
		result += Long.bitCount(this._32);
		result += Long.bitCount(this._33);
		result += Long.bitCount(this._34);
		result += Long.bitCount(this._35);
		result += Long.bitCount(this._36);
		result += Long.bitCount(this._37);
		result += Long.bitCount(this._38);
		result += Long.bitCount(this._39);
		result += Long.bitCount(this._40);
		result += Long.bitCount(this._41);
		result += Long.bitCount(this._42);
		result += Long.bitCount(this._43);
		result += Long.bitCount(this._44);
		result += Long.bitCount(this._45);
		result += Long.bitCount(this._46);
		result += Long.bitCount(this._47);
		return result;
	}
	protected int count_3() {
		var result = 0;
		result += Long.bitCount(this._48);
		result += Long.bitCount(this._49);
		result += Long.bitCount(this._50);
		result += Long.bitCount(this._51);
		result += Long.bitCount(this._52);
		result += Long.bitCount(this._53);
		result += Long.bitCount(this._54);
		result += Long.bitCount(this._55);
		result += Long.bitCount(this._56);
		result += Long.bitCount(this._57);
		result += Long.bitCount(this._58);
		result += Long.bitCount(this._59);
		result += Long.bitCount(this._60);
		result += Long.bitCount(this._61);
		result += Long.bitCount(this._62);
		result += Long.bitCount(this._63);
		return result;
	}
	protected int count_4() {
		var result = 0;
		result += Long.bitCount(this._64);
		result += Long.bitCount(this._65);
		result += Long.bitCount(this._66);
		result += Long.bitCount(this._67);
		result += Long.bitCount(this._68);
		result += Long.bitCount(this._69);
		result += Long.bitCount(this._70);
		result += Long.bitCount(this._71);
		result += Long.bitCount(this._72);
		result += Long.bitCount(this._73);
		result += Long.bitCount(this._74);
		result += Long.bitCount(this._75);
		result += Long.bitCount(this._76);
		result += Long.bitCount(this._77);
		result += Long.bitCount(this._78);
		result += Long.bitCount(this._79);
		return result;
	}
	protected int count_5() {
		var result = 0;
		result += Long.bitCount(this._80);
		result += Long.bitCount(this._81);
		result += Long.bitCount(this._82);
		result += Long.bitCount(this._83);
		result += Long.bitCount(this._84);
		result += Long.bitCount(this._85);
		result += Long.bitCount(this._86);
		result += Long.bitCount(this._87);
		result += Long.bitCount(this._88);
		result += Long.bitCount(this._89);
		result += Long.bitCount(this._90);
		result += Long.bitCount(this._91);
		result += Long.bitCount(this._92);
		result += Long.bitCount(this._93);
		result += Long.bitCount(this._94);
		result += Long.bitCount(this._95);
		return result;
	}
	protected int count_6() {
		var result = 0;
		result += Long.bitCount(this._96);
		result += Long.bitCount(this._97);
		result += Long.bitCount(this._98);
		result += Long.bitCount(this._99);
		result += Long.bitCount(this._100);
		result += Long.bitCount(this._101);
		result += Long.bitCount(this._102);
		result += Long.bitCount(this._103);
		result += Long.bitCount(this._104);
		result += Long.bitCount(this._105);
		result += Long.bitCount(this._106);
		result += Long.bitCount(this._107);
		result += Long.bitCount(this._108);
		result += Long.bitCount(this._109);
		result += Long.bitCount(this._110);
		result += Long.bitCount(this._111);
		return result;
	}
	protected int count_7() {
		var result = 0;
		result += Long.bitCount(this._112);
		result += Long.bitCount(this._113);
		result += Long.bitCount(this._114);
		result += Long.bitCount(this._115);
		result += Long.bitCount(this._116);
		result += Long.bitCount(this._117);
		result += Long.bitCount(this._118);
		result += Long.bitCount(this._119);
		result += Long.bitCount(this._120);
		result += Long.bitCount(this._121);
		result += Long.bitCount(this._122);
		result += Long.bitCount(this._123);
		result += Long.bitCount(this._124);
		result += Long.bitCount(this._125);
		result += Long.bitCount(this._126);
		result += Long.bitCount(this._127);
		return result;
	}

	public boolean isIntersecting(BitField that0) {
		if(!(that0 instanceof BitField32x32x8Impl))
			return BitField.super.isIntersecting(that0);
		var that = (BitField32x32x8Impl) that0;
		if(this.isIntersecting_0(that)) return true;
		if(this.isIntersecting_1(that)) return true;
		if(this.isIntersecting_2(that)) return true;
		if(this.isIntersecting_3(that)) return true;
		if(this.isIntersecting_4(that)) return true;
		if(this.isIntersecting_5(that)) return true;
		if(this.isIntersecting_6(that)) return true;
		if(this.isIntersecting_7(that)) return true;
		return false;
	}
	protected boolean isIntersecting_0(BitField32x32x8Impl that) {
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
	protected boolean isIntersecting_1(BitField32x32x8Impl that) {
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
		if((this._27 & that._27) != 0) return true;
		if((this._28 & that._28) != 0) return true;
		if((this._29 & that._29) != 0) return true;
		if((this._30 & that._30) != 0) return true;
		if((this._31 & that._31) != 0) return true;
		return false;
	}
	protected boolean isIntersecting_2(BitField32x32x8Impl that) {
		if((this._32 & that._32) != 0) return true;
		if((this._33 & that._33) != 0) return true;
		if((this._34 & that._34) != 0) return true;
		if((this._35 & that._35) != 0) return true;
		if((this._36 & that._36) != 0) return true;
		if((this._37 & that._37) != 0) return true;
		if((this._38 & that._38) != 0) return true;
		if((this._39 & that._39) != 0) return true;
		if((this._40 & that._40) != 0) return true;
		if((this._41 & that._41) != 0) return true;
		if((this._42 & that._42) != 0) return true;
		if((this._43 & that._43) != 0) return true;
		if((this._44 & that._44) != 0) return true;
		if((this._45 & that._45) != 0) return true;
		if((this._46 & that._46) != 0) return true;
		if((this._47 & that._47) != 0) return true;
		return false;
	}
	protected boolean isIntersecting_3(BitField32x32x8Impl that) {
		if((this._48 & that._48) != 0) return true;
		if((this._49 & that._49) != 0) return true;
		if((this._50 & that._50) != 0) return true;
		if((this._51 & that._51) != 0) return true;
		if((this._52 & that._52) != 0) return true;
		if((this._53 & that._53) != 0) return true;
		if((this._54 & that._54) != 0) return true;
		if((this._55 & that._55) != 0) return true;
		if((this._56 & that._56) != 0) return true;
		if((this._57 & that._57) != 0) return true;
		if((this._58 & that._58) != 0) return true;
		if((this._59 & that._59) != 0) return true;
		if((this._60 & that._60) != 0) return true;
		if((this._61 & that._61) != 0) return true;
		if((this._62 & that._62) != 0) return true;
		if((this._63 & that._63) != 0) return true;
		return false;
	}
	protected boolean isIntersecting_4(BitField32x32x8Impl that) {
		if((this._64 & that._64) != 0) return true;
		if((this._65 & that._65) != 0) return true;
		if((this._66 & that._66) != 0) return true;
		if((this._67 & that._67) != 0) return true;
		if((this._68 & that._68) != 0) return true;
		if((this._69 & that._69) != 0) return true;
		if((this._70 & that._70) != 0) return true;
		if((this._71 & that._71) != 0) return true;
		if((this._72 & that._72) != 0) return true;
		if((this._73 & that._73) != 0) return true;
		if((this._74 & that._74) != 0) return true;
		if((this._75 & that._75) != 0) return true;
		if((this._76 & that._76) != 0) return true;
		if((this._77 & that._77) != 0) return true;
		if((this._78 & that._78) != 0) return true;
		if((this._79 & that._79) != 0) return true;
		return false;
	}
	protected boolean isIntersecting_5(BitField32x32x8Impl that) {
		if((this._80 & that._80) != 0) return true;
		if((this._81 & that._81) != 0) return true;
		if((this._82 & that._82) != 0) return true;
		if((this._83 & that._83) != 0) return true;
		if((this._84 & that._84) != 0) return true;
		if((this._85 & that._85) != 0) return true;
		if((this._86 & that._86) != 0) return true;
		if((this._87 & that._87) != 0) return true;
		if((this._88 & that._88) != 0) return true;
		if((this._89 & that._89) != 0) return true;
		if((this._90 & that._90) != 0) return true;
		if((this._91 & that._91) != 0) return true;
		if((this._92 & that._92) != 0) return true;
		if((this._93 & that._93) != 0) return true;
		if((this._94 & that._94) != 0) return true;
		if((this._95 & that._95) != 0) return true;
		return false;
	}
	protected boolean isIntersecting_6(BitField32x32x8Impl that) {
		if((this._96 & that._96) != 0) return true;
		if((this._97 & that._97) != 0) return true;
		if((this._98 & that._98) != 0) return true;
		if((this._99 & that._99) != 0) return true;
		if((this._100 & that._100) != 0) return true;
		if((this._101 & that._101) != 0) return true;
		if((this._102 & that._102) != 0) return true;
		if((this._103 & that._103) != 0) return true;
		if((this._104 & that._104) != 0) return true;
		if((this._105 & that._105) != 0) return true;
		if((this._106 & that._106) != 0) return true;
		if((this._107 & that._107) != 0) return true;
		if((this._108 & that._108) != 0) return true;
		if((this._109 & that._109) != 0) return true;
		if((this._110 & that._110) != 0) return true;
		if((this._111 & that._111) != 0) return true;
		return false;
	}
	protected boolean isIntersecting_7(BitField32x32x8Impl that) {
		if((this._112 & that._112) != 0) return true;
		if((this._113 & that._113) != 0) return true;
		if((this._114 & that._114) != 0) return true;
		if((this._115 & that._115) != 0) return true;
		if((this._116 & that._116) != 0) return true;
		if((this._117 & that._117) != 0) return true;
		if((this._118 & that._118) != 0) return true;
		if((this._119 & that._119) != 0) return true;
		if((this._120 & that._120) != 0) return true;
		if((this._121 & that._121) != 0) return true;
		if((this._122 & that._122) != 0) return true;
		if((this._123 & that._123) != 0) return true;
		if((this._124 & that._124) != 0) return true;
		if((this._125 & that._125) != 0) return true;
		if((this._126 & that._126) != 0) return true;
		if((this._127 & that._127) != 0) return true;
		return false;
	}
}
