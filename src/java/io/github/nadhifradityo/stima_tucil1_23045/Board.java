package io.github.nadhifradityo.stima_tucil1_23045;

import java.util.ArrayList;
import java.util.List;

import io.github.nadhifradityo.stima_tucil1_23045.bitfields.BitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.ImmutableBitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField;

public class Board {
	protected final ImmutableBitField boundaryField;
	protected final MutableBitField contentField;
	protected final CompiledPiece[] compiledPieces;

	protected Board(ImmutableBitField boundaryField, MutableBitField contentField, CompiledPiece[] compiledPieces) {
		this.boundaryField = boundaryField;
		this.contentField = contentField;
		this.compiledPieces = compiledPieces;
	}
	public Board(ImmutableBitField boundaryField, MutableBitField contentField, Piece[] pieces) {
		this(boundaryField, contentField, generateCompiledPieces(boundaryField, pieces));
	}

	protected static CompiledPiece[] generateCompiledPieces(BitField boundaryField, Piece[] pieces) {
		var compiledPieces = new CompiledPiece[pieces.length];
		for(int i = 0; i < compiledPieces.length; i++)
			compiledPieces[i] = CompiledPiece.generateCompiledPiece(boundaryField, pieces[i]);
		return compiledPieces;
	}

	public ImmutableBitField getBoundaryField() {
		return boundaryField;
	}
	public MutableBitField getContentField() {
		return contentField;
	}
	public CompiledPiece[] getCompiledPieces() {
		return compiledPieces;
	}

	public Board clone() {
		return new Board(boundaryField.clone(), contentField.clone(), compiledPieces);
	}

	public static class CompiledPiece {
		protected final Piece piece;
		protected final CompiledShape[] compiledShapes;

		protected CompiledPiece(Piece piece, CompiledShape[] compiledShapes) {
			this.piece = piece;
			this.compiledShapes = compiledShapes;
		}

		public Piece getPiece() {
			return piece;
		}
		public CompiledShape[] getCompiledShapes() {
			return compiledShapes;
		}

		public static CompiledPiece generateCompiledPiece(BitField boardBoundaryField, Piece piece) {
			var compiledShapes = new ArrayList<CompiledShape>();
			for(var shape : piece.getShapes())
				compiledShapes.addAll(CompiledShape.generateCompiledShapes(boardBoundaryField, shape));
			return new CompiledPiece(piece, compiledShapes.toArray(n -> new CompiledShape[n]));
		}
	}
	public static class CompiledShape {
		protected final Piece.Shape shape;
		protected final ImmutableBitField contentField;
		protected final String configurationName;

		public CompiledShape(Piece.Shape shape, ImmutableBitField contentField, String configurationName) {
			this.shape = shape;
			this.contentField = contentField;
			this.configurationName = configurationName;
		}

		public Piece.Shape getShape() {
			return shape;
		}
		public ImmutableBitField getContentField() {
			return contentField;
		}
		public String getConfigurationName() {
			return configurationName;
		}

		public static List<CompiledShape> generateCompiledShapes(BitField boardBoundaryField, Piece.Shape shape) {
			List<CompiledShape> result = new ArrayList<>();
			var minX = boardBoundaryField.getMinX();
			var maxX = boardBoundaryField.getMaxX();
			var minY = boardBoundaryField.getMinY();
			var maxY = boardBoundaryField.getMaxY();
			var minZ = boardBoundaryField.getMinZ();
			var maxZ = boardBoundaryField.getMaxZ();
			for(int x = minX; x <= maxX; x++) {
				var shiftXField = shape.getContentField().toMutable().toOffsetX(x);
				for(int y = minY; y <= maxY; y++) {
					var shiftXYField = shiftXField.toOffsetY(y);
					for(int z = minZ; z <= maxZ; z++) {
						var shiftXYZField = shiftXYField.toOffsetZ(z);
						if(boardBoundaryField.isIntersecting(shiftXYZField)) continue;
						var configurationName = shape.getConfigurationName() + " (X: " + x + ", Y: " + y + ", Z: " + z + ")";
						var compiledShape = new CompiledShape(shape, shiftXYZField.toImmutable(), configurationName);
						result.add(compiledShape);
					}
				}
			}
			return result;
		}
	}
}
