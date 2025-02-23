package io.github.nadhifradityo.stima_tucil1_23045;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.collections.api.factory.primitive.ObjectIntMaps;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;

import io.github.nadhifradityo.stima_tucil1_23045.bitfields.BitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.ImmutableBitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField;

public class Board {
	protected final ImmutableBitField boundaryField;
	protected final MutableBitField contentField;
	protected final CompiledPiece[] compiledPieces;

	protected final MutableObjectIntMap<CompiledPiece> compiledPiecePlacements;
	protected final List<MutableBitField> intersects;
	protected final MutableBitField tempIntersect1;
	protected final MutableBitField tempIntersect2;

	protected Board(ImmutableBitField boundaryField, MutableBitField contentField, CompiledPiece[] compiledPieces) {
		this.boundaryField = boundaryField;
		this.contentField = contentField;
		this.compiledPieces = compiledPieces;

		this.compiledPiecePlacements = ObjectIntMaps.mutable.empty();
		for(var compiledPiece : compiledPieces)
			this.compiledPiecePlacements.put(compiledPiece, -1);
		this.intersects = new ArrayList<>();
		this.tempIntersect1 = contentField.toClear();
		this.tempIntersect2 = contentField.toClear();
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
		return this.boundaryField;
	}
	public MutableBitField getContentField() {
		return this.contentField;
	}
	public CompiledPiece[] getCompiledPieces() {
		return this.compiledPieces;
	}
	public MutableObjectIntMap<CompiledPiece> getCompiledPiecePlacements() {
		return this.compiledPiecePlacements;
	}
	public List<MutableBitField> getIntersects() {
		return this.intersects;
	}

	public void reset() {
		this.contentField.clear();
		for(var intersect : this.intersects)
			intersect.clear();
		for(var compiledPiece : this.compiledPieces)
			this.removeCompiledPiece(compiledPiece);
	}
	public boolean isIntersecting() {
		for(var intersect : this.intersects) {
			if(intersect.count() == 0) continue;
			return true;
		}
		return false;
	}
	public boolean isSolved() {
		if(this.isIntersecting())
			return false;
		var checkField = this.tempIntersect1;
		checkField.set(this.contentField);
		checkField.complement();
		return checkField.equals(this.boundaryField);
	}
	public int getCompiledPiecePlacement(CompiledPiece piece) {
		return this.compiledPiecePlacements.get(piece);
	}
	public boolean putCompiledPiece(CompiledPiece piece, int placement) {
		this.removeCompiledPiece(piece);
		this.compiledPiecePlacements.put(piece, placement);
		if(placement == -1) return false;
		var shape = piece.getCompiledShapes()[placement];
		var valueField = this.tempIntersect1;
		var carryField = this.tempIntersect2;
		valueField.set(shape.getContentField());
		int intersectIndex = -1;
		while(valueField.count() != 0) {
			if(intersectIndex >= this.intersects.size())
				this.intersects.add(this.contentField.toClear());
			var targetField = intersectIndex >= 0 ? this.intersects.get(intersectIndex) : this.contentField;
			carryField.set(targetField);
			carryField.intersect(valueField);
			valueField.subtract(carryField);
			targetField.union(valueField);
			var temp = valueField;
			valueField = carryField;
			carryField = temp;
			intersectIndex++;
		}
		return intersectIndex > 0;
	}
	public void removeCompiledPiece(CompiledPiece piece) {
		var placement = this.compiledPiecePlacements.getAndPut(piece, -1, -1);
		if(placement == -1) return;
		var shape = piece.getCompiledShapes()[placement];
		var valueField = this.tempIntersect1;
		var carryField = this.tempIntersect2;
		valueField.set(shape.getContentField());
		int intersectIndex = this.intersects.size() - 1;
		while(valueField.count() != 0 && intersectIndex != -2) {
			var targetField = intersectIndex >= 0 ? this.intersects.get(intersectIndex) : this.contentField;
			carryField.set(targetField);
			carryField.intersect(valueField);
			valueField.subtract(carryField);
			targetField.subtract(carryField);
			intersectIndex--;
			// if(targetField.count() == 0)
			// 	this.intersects.remove(targetField);
		}
	}

	public Board clone() {
		return new Board(this.boundaryField, this.contentField.clone(), this.compiledPieces);
	}

	public static class CompiledPiece {
		protected final Piece piece;
		protected final CompiledShape[] compiledShapes;

		protected CompiledPiece(Piece piece, CompiledShape[] compiledShapes) {
			this.piece = piece;
			this.compiledShapes = compiledShapes;
		}

		public Piece getPiece() {
			return this.piece;
		}
		public CompiledShape[] getCompiledShapes() {
			return this.compiledShapes;
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
			return this.shape;
		}
		public ImmutableBitField getContentField() {
			return this.contentField;
		}
		public String getConfigurationName() {
			return this.configurationName;
		}

		public static List<CompiledShape> generateCompiledShapes(BitField boardBoundaryField, Piece.Shape shape) {
			List<CompiledShape> result = new ArrayList<>();
			var boardTargetField = boardBoundaryField.toMutable();
			boardTargetField.complement();
			var minX = boardTargetField.getMinX();
			var maxX = boardTargetField.getMaxX();
			var minY = boardTargetField.getMinY();
			var maxY = boardTargetField.getMaxY();
			var minZ = boardTargetField.getMinZ();
			var maxZ = boardTargetField.getMaxZ();
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
