package io.github.nadhifradityo.stima_tucil1_23045;

public class Solver {
	protected final Board board;
	protected final long minPlacement;
	protected final long maxPlacement;
	protected long currentPlacement;
	protected boolean completed;
	protected int currentCompiledPieceIndex;
	protected long[] multiplierCache;

	protected Solver(Board board, long minPlacement, long maxPlacement) {
		this.board = board;
		this.minPlacement = Math.max(0, minPlacement);
		this.maxPlacement = Math.min(computeMaxPlacement(board), maxPlacement);
		this.setCurrentPlacemet(this.minPlacement);
		var compiledPieces = this.board.getCompiledPieces();
		this.multiplierCache = new long[compiledPieces.length];
		this.multiplierCache[0] = 1;
		for(int i = compiledPieces.length - 1; i >= 1; i--)
			this.multiplierCache[i - 1] = this.multiplierCache[i] * (compiledPieces[i].getCompiledShapes().length + 1);
	}
	public Solver(Board board) {
		this(board, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	protected static long computeMaxPlacement(Board board) {
		long result = 1;
		for(var compiledPiece : board.getCompiledPieces())
			result *= compiledPiece.getCompiledShapes().length + 1;
		return result;
	}

	public Board getBoard() {
		return this.board;
	}
	public long getMinPlacement() {
		return this.minPlacement;
	}
	public long getMaxPlacement() {
		return this.maxPlacement;
	}
	public long getCurrentPlacement() {
		return this.currentPlacement;
	}
	public void setCurrentPlacemet(long placement) {
		this.currentPlacement = placement;
		this.setBoardPlacement(placement);
		var compiledPieces = this.board.getCompiledPieces();
		for(int i = 0; i < compiledPieces.length; i++) {
            var compiledPiece = compiledPieces[i];
            var compiledPiecePlacement = this.board.getCompiledPiecePlacement(compiledPiece);
            this.currentCompiledPieceIndex = i;
            if(compiledPiecePlacement != -1) break;
        }
	}
	public boolean isCompleted() {
		return this.completed;
	}

	protected long getBoardPlacement() {
		var compiledPieces = this.board.getCompiledPieces();
		long placement = 0;
		long multiplier = 1;
		for(int i = compiledPieces.length - 1; i >= 0; i--) {
			var compiledPiece = compiledPieces[i];
			var compiledPiecePlacement = this.board.getCompiledPiecePlacement(compiledPiece);
			placement += (compiledPiecePlacement + 1) * multiplier;
			multiplier *= (compiledPiece.getCompiledShapes().length + 1);
		}
		return placement;
	}
	protected void setBoardPlacement(long placement) {
		var compiledPieces = this.board.getCompiledPieces();
		for(int i = compiledPieces.length - 1; i >= 0; i--) {
			var compiledPiece = compiledPieces[i];
			var shapesCount = compiledPiece.getCompiledShapes().length + 1;
			var compiledPiecePlacement = (int) (placement % shapesCount);
			if(compiledPiecePlacement > 0)
				this.board.putCompiledPiece(compiledPiece, compiledPiecePlacement - 1);
			else
				this.board.removeCompiledPiece(compiledPiece);
			placement /= shapesCount;
		}
	}

	public boolean isSolved() {
		return this.board.isSolved();
	}
	public void step() {
		if(this.completed) return;
		var compiledPieces = this.board.getCompiledPieces();
		var compiledPiece = compiledPieces[this.currentCompiledPieceIndex];
		var compiledShapes = compiledPiece.getCompiledShapes();
		var oldCompiledPiecePlacement = this.board.getCompiledPiecePlacement(compiledPiece);
		var newCompiledPiecePlacement = oldCompiledPiecePlacement + 1;
		var multiplier = this.multiplierCache[this.currentCompiledPieceIndex];
		if(newCompiledPiecePlacement >= compiledShapes.length) {
			if(this.currentCompiledPieceIndex <= 0) {
				this.completed = true;
				return;
			}
			this.board.removeCompiledPiece(compiledPiece);
			this.currentPlacement -= (oldCompiledPiecePlacement + 1) * multiplier;
			if(this.currentPlacement < this.minPlacement || this.currentPlacement >= this.maxPlacement) {
				this.completed = true;
				return;
			}
			this.currentCompiledPieceIndex--;
			return;
		}
		var intersecting = this.board.putCompiledPiece(compiledPiece, newCompiledPiecePlacement);
		this.currentPlacement += (newCompiledPiecePlacement - oldCompiledPiecePlacement) * multiplier;
		if(this.currentPlacement < this.minPlacement || this.currentPlacement >= this.maxPlacement) {
			this.completed = true;
			return;
		}
		if(intersecting)
			return;
		if(this.currentCompiledPieceIndex + 1 < compiledPieces.length)
			this.currentCompiledPieceIndex++;
		return;
	}
}
