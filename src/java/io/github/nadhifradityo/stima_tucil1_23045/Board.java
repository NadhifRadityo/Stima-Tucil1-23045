package io.github.nadhifradityo.stima_tucil1_23045;

public class Board {
	protected BitField boundaryField;
	protected BitField contentField;

	public Board(BitField boundaryField) {
		this.boundaryField = boundaryField;
		this.contentField = new BitField();
	}
}
