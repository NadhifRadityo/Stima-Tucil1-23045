package io.github.nadhifradityo.stima_tucil1_23045;

import org.eclipse.collections.api.factory.primitive.ObjectIntMaps;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;

public class Solver {
	protected final Board board;
	// Counters are representing compiled piece shapes' indices
	protected final ImmutableObjectIntMap<Board.CompiledPiece> fixedCounters;
	protected final MutableObjectIntMap<Board.CompiledPiece> dynamicCounters;

	protected Solver(Board board, ImmutableObjectIntMap<Board.CompiledPiece> fixedCounters, MutableObjectIntMap<Board.CompiledPiece> dynamicCounters) {
		this.board = board;
		this.fixedCounters = fixedCounters;
		this.dynamicCounters = dynamicCounters;
		for(var piece : board.getCompiledPieces()) {
			if(fixedCounters.containsKey(piece)) continue;
			dynamicCounters.put(piece, 0);
		}
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

	public void step(int counterArity) {
		
	}
	public void step() {
		this.step(1);
	}
	public Solver branch() {
		var newFixedCounters = ObjectIntMaps.mutable.<Board.CompiledPiece>empty();
		newFixedCounters.putAll(fixedCounters);
		for(var variableCounter : dynamicCounters.keyValuesView()) {
			if(variableCounter.getTwo() == 0) continue;
			newFixedCounters.put(variableCounter.getOne(), variableCounter.getTwo());
		}
		return new Solver(board, newFixedCounters.toImmutable(), ObjectIntMaps.mutable.empty());
	}
}
