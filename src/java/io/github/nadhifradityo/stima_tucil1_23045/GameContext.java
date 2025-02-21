package io.github.nadhifradityo.stima_tucil1_23045;

import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField12x12x12Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField12x12x1Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField16x16x16Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField16x16x1Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField20x20x10Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField20x20x1Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField32x32x1Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField32x32x8Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField8x8x1Impl;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField8x8x8Impl;

public class GameContext {
	protected final Class<? extends MutableBitField> bitFieldKlass;

	public GameContext(Class<? extends MutableBitField> bitFieldKlass) {
		this.bitFieldKlass = bitFieldKlass;
	}
	public GameContext(int width, int height, int depth) {
		this(getBestBitFieldClass(width, height, depth));
	}

	public MutableBitField newBitField() {
		try {
			return bitFieldKlass.getConstructor().newInstance();
		} catch(Throwable e) {
			throw new Error("Error while instantiating BitField class", e);
		}
	}

	public static Class<? extends MutableBitField> getBestBitFieldClass(int width, int height, int depth) {
		if(width < MutableBitField8x8x1Impl.WIDTH && height < MutableBitField8x8x1Impl.HEIGHT && depth < MutableBitField8x8x1Impl.DEPTH)
			return MutableBitField8x8x1Impl.class;
		if(width < MutableBitField8x8x8Impl.WIDTH && height < MutableBitField8x8x8Impl.HEIGHT && depth < MutableBitField8x8x8Impl.DEPTH)
			return MutableBitField8x8x8Impl.class;
		if(width < MutableBitField12x12x1Impl.WIDTH && height < MutableBitField12x12x1Impl.HEIGHT && depth < MutableBitField12x12x1Impl.DEPTH)
			return MutableBitField12x12x1Impl.class;
		if(width < MutableBitField12x12x12Impl.WIDTH && height < MutableBitField12x12x12Impl.HEIGHT && depth < MutableBitField12x12x12Impl.DEPTH)
			return MutableBitField12x12x12Impl.class;
		if(width < MutableBitField16x16x1Impl.WIDTH && height < MutableBitField16x16x1Impl.HEIGHT && depth < MutableBitField16x16x1Impl.DEPTH)
			return MutableBitField16x16x1Impl.class;
		if(width < MutableBitField16x16x16Impl.WIDTH && height < MutableBitField16x16x16Impl.HEIGHT && depth < MutableBitField16x16x16Impl.DEPTH)
			return MutableBitField16x16x16Impl.class;
		if(width < MutableBitField20x20x1Impl.WIDTH && height < MutableBitField20x20x1Impl.HEIGHT && depth < MutableBitField20x20x1Impl.DEPTH)
			return MutableBitField20x20x1Impl.class;
		if(width < MutableBitField20x20x10Impl.WIDTH && height < MutableBitField20x20x10Impl.HEIGHT && depth < MutableBitField20x20x10Impl.DEPTH)
			return MutableBitField20x20x10Impl.class;
		if(width < MutableBitField32x32x1Impl.WIDTH && height < MutableBitField32x32x1Impl.HEIGHT && depth < MutableBitField32x32x1Impl.DEPTH)
			return MutableBitField32x32x1Impl.class;
		if(width < MutableBitField32x32x8Impl.WIDTH && height < MutableBitField32x32x8Impl.HEIGHT && depth < MutableBitField32x32x8Impl.DEPTH)
			return MutableBitField32x32x8Impl.class;
		throw new IllegalArgumentException("No BitField class size available for " + width + "x" + height + "x" + depth);
	}
}
