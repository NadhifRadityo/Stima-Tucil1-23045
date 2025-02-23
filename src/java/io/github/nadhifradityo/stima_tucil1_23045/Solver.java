package io.github.nadhifradityo.stima_tucil1_23045;

import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;

public class Solver {
	protected final Board board;
	protected final long minPlacement;
	protected final long maxPlacement;
	protected long currentPlacement;
	protected boolean completed;
	protected int currentCompiledPieceIndex;
	protected long[] multiplierCache;
	protected ProgressTracker progressTracker;

	protected Solver(Board board, long minPlacement, long maxPlacement) {
		this.board = board;
		this.minPlacement = Math.max(0, minPlacement);
		this.maxPlacement = Math.min(computeMaxPlacement(board), maxPlacement);
		this.setCurrentPlacement(this.minPlacement);
		var compiledPieces = this.board.getCompiledPieces();
		this.multiplierCache = new long[compiledPieces.length];
		this.multiplierCache[compiledPieces.length - 1] = 1;
		for(int i = compiledPieces.length - 1; i >= 1; i--)
			this.multiplierCache[i - 1] = this.multiplierCache[i] * (compiledPieces[i].getCompiledShapes().length + 1);
		this.progressTracker = new ProgressTracker(minPlacement, maxPlacement);
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
	public void setCurrentPlacement(long placement) {
		this.currentPlacement = placement;
		this.setBoardPlacement(placement);
		var compiledPieces = this.board.getCompiledPieces();
		for(int i = compiledPieces.length - 1; i >= 0; i--) {
            var compiledPiece = compiledPieces[i];
            var compiledPiecePlacement = this.board.getCompiledPiecePlacement(compiledPiece);
            if(compiledPiecePlacement != -1) break;
            this.currentCompiledPieceIndex = i;
        }
	}
	public boolean isCompleted() {
		return this.completed;
	}
	public ProgressTracker getProgressTracker() {
		return progressTracker;
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
		this.board.reset();
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
			synchronized(this.progressTracker) {
				this.progressTracker.reportProgress(this.currentPlacement);
			}
			this.currentCompiledPieceIndex--;
			return;
		}
		var intersecting = this.board.putCompiledPiece(compiledPiece, newCompiledPiecePlacement);
		this.currentPlacement += (newCompiledPiecePlacement - oldCompiledPiecePlacement) * multiplier;
		synchronized(this.progressTracker) {
			this.progressTracker.reportProgress(this.currentPlacement);
		}
		if(this.currentPlacement < this.minPlacement || this.currentPlacement >= this.maxPlacement) {
			this.completed = true;
			return;
		}
		if(intersecting) {
			synchronized(this.progressTracker) {
				this.progressTracker.reportProgressRange(this.currentPlacement, this.currentPlacement + multiplier);
			}
			return;
		}
		if(this.currentCompiledPieceIndex + 1 < compiledPieces.length)
			this.currentCompiledPieceIndex++;
		return;
	}

	public class ProgressTracker {
		protected final long min;
		protected final long max;
		protected final MutableLongList progressRanges;

		public ProgressTracker(long min, long max) {
			if(min > max)
				throw new IllegalArgumentException("Min cannot be greater than Max.");
			this.min = min;
			this.max = max;
			this.progressRanges = new LongArrayList();
		}

		public void reportProgress(long p) {
			p = Math.max(this.min, Math.min(this.max, p));
			var index = findInsertionIndex(p);
			if(index >= 0) return;
			index = -index - 1;
			var mergedLeft = false;
			var mergedRight = false;
			if(index > 0 && progressRanges.get(index - 1) + 1 == p) {
				progressRanges.set(index - 1, p);
				mergedLeft = true;
			}
			if(index < progressRanges.size() && progressRanges.get(index) - 1 == p) {
				progressRanges.set(index, p);
				mergedRight = true;
			}
			if(mergedLeft && mergedRight) {
				progressRanges.set(index - 1, progressRanges.get(index));
				progressRanges.removeAtIndex(index);
			} else if(!mergedLeft && !mergedRight) {
				progressRanges.addAtIndex(index, p);
				progressRanges.addAtIndex(index + 1, p);
			}
		}

		public void reportProgressRange(long start, long end) {
			if(start > end) return;
			start = Math.max(this.min, Math.min(this.max, start));
			end = Math.max(this.min, Math.min(this.max, end));
			if(end - start <= 1) {
				this.reportProgress(end);
				return;
			}
			var index = findInsertionIndex(start);
			if(index < 0) index = -index - 1;
			var mergeStart = start;
			var mergeEnd = end;
			while(index < progressRanges.size() && progressRanges.get(index) <= mergeEnd + 1) {
				mergeStart = Math.min(mergeStart, progressRanges.get(index));
				mergeEnd = Math.max(mergeEnd, progressRanges.get(index + 1));
				progressRanges.removeAtIndex(index);
				progressRanges.removeAtIndex(index);
			}
			progressRanges.addAtIndex(index, mergeStart);
			progressRanges.addAtIndex(index + 1, mergeEnd);
		}
		protected int findInsertionIndex(long p) {
			var low = 0;
			var high = progressRanges.size() / 2 - 1;
			while(low <= high) {
				var mid = (low + high) / 2;
				var start = progressRanges.get(2 * mid);
				var end = progressRanges.get(2 * mid + 1);
				if(p >= start && p <= end) return 2 * mid;
				if(p < start) high = mid - 1;
				else low = mid + 1;
			}
			return -2 * low - 1;
		}

		public double getOverallProgress() {
			var covered = 0L;
			var total = this.max - this.min + 1;
			for(int i = 0; i < progressRanges.size(); i += 2)
				covered += progressRanges.get(i + 1) - progressRanges.get(i) + 1;
			return (double) covered / total;
		}

		protected static final int BAR_LENGTH = 50;
		public String getProgressBar() {
			var bar = new StringBuilder();
			var rangeSize = this.max - this.min + 1;
			var unitSize = (double) rangeSize / BAR_LENGTH;
			for(int i = 0; i < BAR_LENGTH; i++) {
				var segmentStart = (long) (this.min + i * unitSize);
				var segmentEnd = (long) (segmentStart + unitSize - 1);
				var fillRatio = getFillRatio(segmentStart, segmentEnd);
				bar.append(getShadeCharacter(fillRatio));
			}
			return "[" + bar.toString() + "]";
		}
		protected double getFillRatio(long start, long end) {
			var covered = 0L;
			var total = end - start + 1;
			for(int i = 0; i < progressRanges.size(); i += 2) {
				var rangeStart = progressRanges.get(i);
				var rangeEnd = progressRanges.get(i + 1);
				if(rangeEnd < start) continue;
				if(rangeStart > end) break;
				var overlapStart = Math.max(rangeStart, start);
				var overlapEnd = Math.min(rangeEnd, end);
				covered += (overlapEnd - overlapStart + 1);
			}
			return (double) covered / total;
		}
		protected static char getShadeCharacter(double fillRatio) {
			if(fillRatio >= 1.0) return '█';
			if(fillRatio >= 0.75) return '▓';
			if(fillRatio >= 0.50) return '▒';
			if(fillRatio >= 0.25) return '░';
			return ' ';
		}
	}
}
