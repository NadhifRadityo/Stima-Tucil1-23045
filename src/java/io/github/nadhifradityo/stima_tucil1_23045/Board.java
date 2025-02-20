package io.github.nadhifradityo.stima_tucil1_23045;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
	protected final BitField boundaryField;
	protected final BitField contentField;
	protected final Piece[] pieces;
	protected final Map<Piece, CompiledPiece> compiledPieces;

	protected Board(BitField boundaryField, BitField contentField, Piece[] pieces, Map<Piece, CompiledPiece> compiledPieces) {
		this.boundaryField = boundaryField;
		this.contentField = contentField;
		this.pieces = pieces;
		this.compiledPieces = compiledPieces;
	}
	public Board(BitField boundaryField, BitField contentField, Piece[] pieces) {
		this(boundaryField, contentField, pieces, generateCompiledPieces(boundaryField, pieces));
	}

	protected static Map<Piece, CompiledPiece> generateCompiledPieces(BitField boundaryField, Piece[] pieces) {
		var compiledPieces = new HashMap<Piece, CompiledPiece>();
		for(var piece : pieces)
			compiledPieces.put(piece, CompiledPiece.generateCompiledPiece(boundaryField, piece));
		return compiledPieces;
	}

	public BitField getBoundaryField() {
		return boundaryField;
	}
	public BitField getContentField() {
		return contentField;
	}
	public Piece[] getPieces() {
		return pieces;
	}
	public Map<Piece, CompiledPiece> getCompiledPieces() {
		return compiledPieces;
	}

	public Board clone() {
		return new Board(boundaryField, contentField, pieces, compiledPieces);
	}

	public static class CompiledPiece {
		protected final Piece piece;
		protected final Map<Piece.Shape, CompiledShape[]> compiledShapes;

		protected CompiledPiece(Piece piece, Map<Piece.Shape, CompiledShape[]> compiledShapes) {
			this.piece = piece;
			this.compiledShapes = compiledShapes;
		}

		public Piece getPiece() {
			return piece;
		}
		public Map<Piece.Shape, CompiledShape[]> getCompiledShapes() {
			return compiledShapes;
		}

		public static CompiledPiece generateCompiledPiece(BitField boardBoundaryField, Piece piece) {
			var compiledShapes = new HashMap<Piece.Shape, CompiledShape[]>();
			for(var shape : piece.getShapes())
				compiledShapes.put(shape, CompiledShape.generateCompiledShapes(boardBoundaryField, shape));
			return new CompiledPiece(piece, compiledShapes);
		}
	}
	public static class CompiledShape {
		protected final BitField contentField;
		protected final String configurationName;

		public CompiledShape(BitField contentField, String configurationName) {
			this.contentField = contentField;
			this.configurationName = configurationName;
		}

		public BitField getContentField() {
			return contentField;
		}
		public String getConfigurationName() {
			return configurationName;
		}

		public static CompiledShape[] generateCompiledShapes(BitField boardBoundaryField, Piece.Shape shape) {
			List<CompiledShape> result = new ArrayList<>();
			int minX = boardBoundaryField.getMinX();
			int maxX = boardBoundaryField.getMaxX();
			int minY = boardBoundaryField.getMinY();
			int maxY = boardBoundaryField.getMaxY();
			int minZ = boardBoundaryField.getMinZ();
			int maxZ = boardBoundaryField.getMaxZ();
			for(int x = minX; x <= maxX; x++) {
				BitField shiftXField = boardBoundaryField.clone();
				shiftXField.offsetX(x);
				for(int y = minY; y <= maxY; y++) {
					BitField shiftYField = shiftXField.clone();
					shiftYField.offsetY(y);
					for(int z = minZ; z <= maxZ; z++) {
						BitField shiftZField = shiftYField.clone();
						shiftZField.offsetZ(z);
						if(boardBoundaryField.isIntersecting(shiftZField)) continue;
						var compiledShape = new CompiledShape(shiftZField, shape.getConfigurationName() + " (X: " + x + ", Y: " + y + ", Z: " + z + ")");
						result.add(compiledShape);
					}
				}
			}
			return result.toArray(new CompiledShape[result.size()]);
		}
	}
}
