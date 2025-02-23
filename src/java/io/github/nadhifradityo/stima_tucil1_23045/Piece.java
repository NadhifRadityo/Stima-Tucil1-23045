package io.github.nadhifradityo.stima_tucil1_23045;

import java.util.ArrayList;

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
		return this.baseShapeField;
	}
	public Shape[] getShapes() {
		return this.shapes;
	}

	public static class Shape {
		protected final ImmutableBitField contentField;
		protected final String configurationName;

		public Shape(ImmutableBitField contentField, String configurationName) {
			this.contentField = contentField;
			this.configurationName = configurationName;
		}

		public ImmutableBitField getContentField() {
			return this.contentField;
		}
		public String getConfigurationName() {
			return this.configurationName;
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
			var candidates = new ArrayList<Shape>();
			candidates.add(new Shape(original.toImmutable(), "Original"));
			candidates.add(new Shape(rotateZ90.toImmutable(), "Rotate Z 90"));
			candidates.add(new Shape(rotateZ180.toImmutable(), "Rotate Z 180"));
			candidates.add(new Shape(rotateZ270.toImmutable(), "Rotate Z 270"));
			candidates.add(new Shape(flipXOriginal.toImmutable(), "Flip X"));
			candidates.add(new Shape(flipXRotateZ90.toImmutable(), "Rotate Z 90 Flip X"));
			candidates.add(new Shape(flipXRotateZ180.toImmutable(), "Rotate Z 180 Flip X"));
			candidates.add(new Shape(flipXRotateZ270.toImmutable(), "Rotate Z 270 Flip X"));
			candidates.add(new Shape(flipYOriginal.toImmutable(), "Flip Y"));
			candidates.add(new Shape(flipYRotateZ90.toImmutable(), "Rotate Z 90 Flip Y"));
			candidates.add(new Shape(flipYRotateZ180.toImmutable(), "Rotate Z 180 Flip Y"));
			candidates.add(new Shape(flipYRotateZ270.toImmutable(), "Rotate Z 270 Flip Y"));
			for(int i = candidates.size() - 1; i >= 0; i--) {
				var shape = candidates.get(i);
				var duplicated = false;
				for(int j = i - 1; j >= 0; j--) {
					var shape2 = candidates.get(j);
					if(!shape.getContentField().equals(shape2.getContentField()))
						continue;
					duplicated = true;
					break;
				}
				if(!duplicated)
					continue;
				candidates.remove(i);
			}
			return candidates.toArray(n -> new Shape[n]);
		}
	}
}
