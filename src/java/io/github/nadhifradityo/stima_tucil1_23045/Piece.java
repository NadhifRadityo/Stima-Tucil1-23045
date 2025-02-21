package io.github.nadhifradityo.stima_tucil1_23045;

import io.github.nadhifradityo.stima_tucil1_23045.bitfields.BitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.ImmutableBitField;

public class Piece {
	protected final ImmutableBitField baseShapeField;
	protected final Shape[] shapes;

	protected Piece(ImmutableBitField baseShapeField, Shape[] shapes) {
		this.baseShapeField = baseShapeField;
		this.shapes = shapes;
	}
	public Piece(ImmutableBitField baseShapeField) {
		this(baseShapeField, Shape.generateRotatedShapes(baseShapeField));
	}

	public ImmutableBitField getBaseShapeField() {
		return baseShapeField;
	}
	public Shape[] getShapes() {
		return shapes;
	}

	public static class Shape {
		protected final ImmutableBitField contentField;
		protected final String configurationName;

		public Shape(ImmutableBitField contentField, String configurationName) {
			this.contentField = contentField;
			this.configurationName = configurationName;
		}

		public ImmutableBitField getContentField() {
			return contentField;
		}
		public String getConfigurationName() {
			return configurationName;
		}

		public static Shape[] generateRotatedShapes(BitField baseShapeField) {
			var original = baseShapeField.toMutable();
			var rotateZ90 = original.toRotateZ90();
			var rotateZ180 = original.toRotateZ180();
			var rotateZ270 = original.toRotateZ270();
			var flipXOriginal = original.toFlipX();
			var flipXRotateZ90 = flipXOriginal.toRotateZ90();
			var flipXRotateZ180 = flipXOriginal.toRotateZ180();
			var flipXRotateZ270 = flipXOriginal.toRotateZ270();
			var flipYOriginal = original.toFlipY();
			var flipYRotateZ90 = flipYOriginal.toRotateZ90();
			var flipYRotateZ180 = flipYOriginal.toRotateZ180();
			var flipYRotateZ270 = flipYOriginal.toRotateZ270();
			original.trim();
			rotateZ90.trim();
			rotateZ180.trim();
			rotateZ270.trim();
			flipXOriginal.trim();
			flipXRotateZ90.trim();
			flipXRotateZ180.trim();
			flipXRotateZ270.trim();
			flipYOriginal.trim();
			flipYRotateZ90.trim();
			flipYRotateZ180.trim();
			flipYRotateZ270.trim();
			return new Shape[] {
				new Shape(original.toImmutable(), "Original"),
				new Shape(rotateZ90.toImmutable(), "Rotate Z 90"),
				new Shape(rotateZ180.toImmutable(), "Rotate Z 180"),
				new Shape(rotateZ270.toImmutable(), "Rotate Z 270"),
				new Shape(flipXOriginal.toImmutable(), "Flip X"),
				new Shape(flipXRotateZ90.toImmutable(), "Rotate Z 90 Flip X"),
				new Shape(flipXRotateZ180.toImmutable(), "Rotate Z 180 Flip X"),
				new Shape(flipXRotateZ270.toImmutable(), "Rotate Z 270 Flip X"),
				new Shape(flipYOriginal.toImmutable(), "Flip Y"),
				new Shape(flipYRotateZ90.toImmutable(), "Rotate Z 90 Flip Y"),
				new Shape(flipYRotateZ180.toImmutable(), "Rotate Z 180 Flip Y"),
				new Shape(flipYRotateZ270.toImmutable(), "Rotate Z 270 Flip Y"),
			};
		}
	}
}
