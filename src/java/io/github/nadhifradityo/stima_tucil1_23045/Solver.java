package io.github.nadhifradityo.stima_tucil1_23045;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.collections.api.factory.primitive.ObjectIntMaps;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;

import io.github.nadhifradityo.stima_tucil1_23045.bitfields.BitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField;

public class Solver {
	protected final Board board;
	// Counters are representing compiled piece shapes' indices
	protected final ImmutableObjectIntMap<Board.CompiledPiece> fixedCounters;
	protected final MutableObjectIntMap<Board.CompiledPiece> dynamicCounters;
	protected final Board.CompiledPiece[] dynamicPieces;

	protected final List<MutableBitField> intersects;
	protected final MutableBitField tempIntersect1;
	protected final MutableBitField tempIntersect2;

	protected Solver(Board board, ImmutableObjectIntMap<Board.CompiledPiece> fixedCounters, MutableObjectIntMap<Board.CompiledPiece> dynamicCounters) {
		this.board = board;
		this.fixedCounters = fixedCounters;
		this.dynamicCounters = dynamicCounters;
		var dynamicPiecesList = new ArrayList<Board.CompiledPiece>();
		for(var piece : board.getCompiledPieces()) {
			if(fixedCounters.containsKey(piece)) continue;
			dynamicCounters.put(piece, -1);
			dynamicPiecesList.add(piece);
		}
		this.dynamicPieces = dynamicPiecesList.toArray(n -> new Board.CompiledPiece[n]);

		this.intersects = new ArrayList<>();
		this.tempIntersect1 = board.getContentField().toClear();
		this.tempIntersect2 = board.getContentField().toClear();
	}
	public Solver(Board board) {
		this(board, ObjectIntMaps.immutable.empty(), ObjectIntMaps.mutable.empty());
	}

	public Board getBoard() {
		return board;
	}
	public ImmutableObjectIntMap<Board.CompiledPiece> getFixedCounters() {
		return fixedCounters;
	}
	public MutableObjectIntMap<Board.CompiledPiece> getDynamicCounters() {
		return dynamicCounters;
	}
	public Board.CompiledPiece[] getDynamicPieces() {
		return dynamicPieces;
	}
	public List<MutableBitField> getIntersects() {
		return intersects;
	}

	public void reset() {
		for(var intersect : intersects)
			intersect.clear();
		for(var piece : dynamicCounters.keySet())
			dynamicCounters.put(piece, -1);
		var boardContentField = board.getContentField();
		boardContentField.clear();
		for(var entry : fixedCounters.keyValuesView()) {
			var compiledShape = entry.getOne().getCompiledShapes()[entry.getTwo()];
			boardContentField.union(compiledShape.getContentField());
		}
	}
	protected void addField(BitField field) {
		var valueField = tempIntersect1;
		var carryField = tempIntersect2;
		valueField.set(field);
		int intersectIndex = -1;
		while(valueField.count() != 0) {
			if(intersectIndex >= intersects.size())
				intersects.add(board.getContentField().toClear());
			var targetField = intersectIndex >= 0 ? intersects.get(intersectIndex) : board.getContentField();
			carryField.set(targetField);
			carryField.intersect(valueField);
			valueField.subtract(carryField);
			targetField.union(valueField);
			var temp = valueField;
			valueField = carryField;
			carryField = temp;
			intersectIndex++;
		}
	}
	protected void removeField(BitField field) {
		var valueField = tempIntersect1;
		var carryField = tempIntersect2;
		valueField.set(field);
		int intersectIndex = intersects.size() - 1;
		while(valueField.count() != 0 && intersectIndex != -2) {
			var targetField = intersectIndex >= 0 ? intersects.get(intersectIndex) : board.getContentField();
			carryField.set(targetField);
			carryField.intersect(valueField);
			valueField.subtract(carryField);
			targetField.subtract(carryField);
			intersectIndex--;
			if(targetField.count() == 0)
				intersects.remove(targetField);
		}
	}
	public void step(int counterArity) {
		var piece = dynamicPieces[dynamicPieces.length - counterArity];
		var shapes = piece.getCompiledShapes();
		var oldCount = dynamicCounters.get(piece);
		var newCount = Math.floorMod(oldCount + 1, shapes.length);
		dynamicCounters.put(piece, newCount);
		if(oldCount != -1)
			removeField(shapes[oldCount].getContentField());
		addField(shapes[newCount].getContentField());
		if(newCount == 0 && counterArity < dynamicPieces.length)
			step(counterArity + 1);
	}
	public void step() {
		step(1);
	}
	public void unwind(int counterArity) {
		var piece = dynamicPieces[dynamicPieces.length - counterArity];
		var shapes = piece.getCompiledShapes();
		var oldCount = dynamicCounters.get(piece);
		var newCount = oldCount != -1 ? Math.floorMod(oldCount - 1, shapes.length) : shapes.length - 1;
		dynamicCounters.put(piece, newCount);
		if(oldCount != -1)
			removeField(shapes[oldCount].getContentField());
		addField(shapes[newCount].getContentField());
		if(newCount == 0 && counterArity < dynamicPieces.length)
			unwind(counterArity + 1);
	}
	public void unwind() {
		unwind(1);
	}
	public Solver branch() {
		var newFixedCounters = ObjectIntMaps.mutable.<Board.CompiledPiece>empty();
		newFixedCounters.putAll(fixedCounters);
		for(var variableCounter : dynamicCounters.keyValuesView()) {
			if(variableCounter.getTwo() == -1) continue;
			newFixedCounters.put(variableCounter.getOne(), variableCounter.getTwo());
		}
		return new Solver(board.clone(), newFixedCounters.toImmutable(), ObjectIntMaps.mutable.empty());
	}
}
