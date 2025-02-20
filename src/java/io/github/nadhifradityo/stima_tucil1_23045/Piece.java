package io.github.nadhifradityo.stima_tucil1_23045;

public class Piece {
	protected final BitField baseShapeField;
	protected final Shape[] shapes;

	protected Piece(BitField baseShapeField, Shape[] shapes) {
		this.baseShapeField = baseShapeField;
		this.shapes = shapes;
	}
	public Piece(BitField baseShapeField) {
		this(baseShapeField, Shape.generateRotatedShapes(baseShapeField));
	}

	public BitField getBaseShapeField() {
		return baseShapeField;
	}
	public Shape[] getShapes() {
		return shapes;
	}

	public static class Shape {
		protected final BitField contentField;
		protected final String configurationName;

		public Shape(BitField contentField, String configurationName) {
			this.contentField = contentField;
			this.configurationName = configurationName;
		}

		public BitField getContentField() {
			return contentField;
		}
		public String getConfigurationName() {
			return configurationName;
		}

		public static Shape[] generateRotatedShapes(BitField baseShapeField) {
			var original = baseShapeField.clone();
			var rotateZ90 = original.toRotateZ90();
			var rotateZ180 = original.toRotateZ180();
			var rotateZ270 = original.toRotateZ270();
			var flipXOriginal = original.clone();
			flipXOriginal.flipX();
			var flipXRotateZ90 = flipXOriginal.toRotateZ90();
			var flipXRotateZ180 = flipXOriginal.toRotateZ180();
			var flipXRotateZ270 = flipXOriginal.toRotateZ270();
			var flipYOriginal = original.clone();
			flipYOriginal.flipY();
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
				new Shape(original, "Original"),
				new Shape(rotateZ90, "Rotate Z 90"),
				new Shape(rotateZ180, "Rotate Z 180"),
				new Shape(rotateZ270, "Rotate Z 270"),
				new Shape(flipXOriginal, "Flip X"),
				new Shape(flipXRotateZ90, "Rotate Z 90 Flip X"),
				new Shape(flipXRotateZ180, "Rotate Z 180 Flip X"),
				new Shape(flipXRotateZ270, "Rotate Z 270 Flip X"),
				new Shape(flipYOriginal, "Flip Y"),
				new Shape(flipYRotateZ90, "Rotate Z 90 Flip Y"),
				new Shape(flipYRotateZ180, "Rotate Z 180 Flip Y"),
				new Shape(flipYRotateZ270, "Rotate Z 270 Flip Y"),
			};
		}
	}
}
