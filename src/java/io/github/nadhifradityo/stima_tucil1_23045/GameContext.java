package io.github.nadhifradityo.stima_tucil1_23045;

public class GameContext {
	protected final Class<? extends BitField> bitFieldKlass;

	public GameContext(Class<? extends BitField> bitFieldKlass) {
		this.bitFieldKlass = bitFieldKlass;
	}
	public GameContext(int width, int height, int depth) {
		this(getBestBitFieldClass(width, height, depth));
	}

	public BitField newBitField() {
		try {
			return bitFieldKlass.getConstructor().newInstance();
		} catch(Throwable e) {
			throw new Error("Error while instantiating BitField class", e);
		}
	}

	public static Class<? extends BitField> getBestBitFieldClass(int width, int height, int depth) {
		if(width < BitField8x8x1.WIDTH && height < BitField8x8x1.HEIGHT && depth < BitField8x8x1.DEPTH)
			return BitField8x8x1.class;
		if(width < BitField8x8x8.WIDTH && height < BitField8x8x8.HEIGHT && depth < BitField8x8x8.DEPTH)
			return BitField8x8x8.class;
		if(width < BitField12x12x1.WIDTH && height < BitField12x12x1.HEIGHT && depth < BitField12x12x1.DEPTH)
			return BitField12x12x1.class;
		if(width < BitField12x12x12.WIDTH && height < BitField12x12x12.HEIGHT && depth < BitField12x12x12.DEPTH)
			return BitField12x12x12.class;
		if(width < BitField16x16x1.WIDTH && height < BitField16x16x1.HEIGHT && depth < BitField16x16x1.DEPTH)
			return BitField16x16x1.class;
		if(width < BitField16x16x16.WIDTH && height < BitField16x16x16.HEIGHT && depth < BitField16x16x16.DEPTH)
			return BitField16x16x16.class;
		if(width < BitField20x20x1.WIDTH && height < BitField20x20x1.HEIGHT && depth < BitField20x20x1.DEPTH)
			return BitField20x20x1.class;
		if(width < BitField20x20x10.WIDTH && height < BitField20x20x10.HEIGHT && depth < BitField20x20x10.DEPTH)
			return BitField20x20x10.class;
		if(width < BitField32x32x1.WIDTH && height < BitField32x32x1.HEIGHT && depth < BitField32x32x1.DEPTH)
			return BitField32x32x1.class;
		if(width < BitField32x32x8.WIDTH && height < BitField32x32x8.HEIGHT && depth < BitField32x32x8.DEPTH)
			return BitField32x32x8.class;
		throw new IllegalArgumentException("No BitField class size available for " + width + "x" + height + "x" + depth);
	}
}
