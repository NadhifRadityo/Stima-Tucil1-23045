package io.github.nadhifradityo.stima_tucil1_23045;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.primitive.CharLists;
import org.eclipse.collections.api.factory.primitive.ObjectIntMaps;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.github.nadhifradityo.stima_tucil1_23045.bitfields.BitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField;

public class AppCLI {

	public static void main(String... args) throws Exception {
		Options options = new Options();
		options.addRequiredOption("i", "input", true, "Input case file");
		options.addOption("b", "branch", true, "Branches allowed for solver");
		options.addOption("t", "thread", true, "How many threads should be used");
		options.addOption("s", "solutions", true, "Solutions to generate");
		options.addOption("u", "no-interactive", false, "Force no interactive console");
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		String cmdInput;
		int cmdBranch;
		int cmdThread;
		int cmdSolutions;
		boolean cmdNoInteractive;
		try {
			CommandLine cmd = parser.parse(options, args);
			cmdInput = cmd.getOptionValue("input");
			cmdBranch = Integer.parseInt(cmd.getOptionValue("branch", "8"));
			cmdThread = Integer.parseInt(cmd.getOptionValue("thread", "2"));
			cmdSolutions = Integer.parseInt(cmd.getOptionValue("solutions", "1"));
			cmdNoInteractive = cmd.hasOption("no-interactive");
		} catch(ParseException e) {
			System.out.println("Error: " + e.getMessage());
			formatter.printHelp("CLIExample", options);
			return;
		}
		var instance = new AppCLI(cmdInput, cmdBranch, cmdThread, cmdSolutions, cmdNoInteractive);
		instance.prepareConsole();
		instance.prepare();
		instance.execute();
	}

	protected static final boolean isConsoleInteractive = System.console() != null && System.out.getClass().getName().equals("java.io.PrintStream");
	protected static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	protected final String cmdInput;
	protected final int cmdBranch;
	protected final int cmdThread;
	protected final int cmdSolutions;
	protected final boolean cmdNoInteractive;

	public AppCLI(String cmdInput, int cmdBranch, int cmdThread, int cmdSolutions, boolean cmdNoInteractive) {
		this.cmdInput = cmdInput;
		this.cmdBranch = cmdBranch;
		this.cmdThread = cmdThread;
		this.cmdSolutions = cmdSolutions;
		this.cmdNoInteractive = cmdNoInteractive;
	}

	protected Terminal terminal;
	protected Screen screen;

	public void prepareConsole() throws Exception {
		if(this.cmdNoInteractive || !isConsoleInteractive) return;
		this.terminal = new DefaultTerminalFactory().createHeadlessTerminal();
		this.screen = new TerminalScreen(this.terminal);
	}

	protected int boardWidth;
	protected int boardHeight;
	protected int boardDepth;
	protected int piecesCount;
	protected String boardType;
	protected BitFieldFactory fieldFactory;
	protected MutableBitField boardBoundaryField;
	protected Piece[] pieces;
	protected Board board;
	protected Solver baseSolver;
	protected Solver[] solvers;

	public void prepare() throws Exception {
		Scanner scanner;
		if(cmdInput.equals("-")) {
			System.err.println("Enter case text (Ctrl+D to signal EOF on Linux/macOS, Ctrl+Z on Windows):");
			scanner = new Scanner(System.in);
		} else
			scanner = new Scanner(new File(cmdInput));
		this.boardWidth = scanner.nextInt();
		this.boardHeight = scanner.nextInt();
		this.boardDepth = 1;
		this.piecesCount = scanner.nextInt();
		this.boardType = scanner.next().toUpperCase(Locale.ROOT);
		if(this.boardType.matches("-?\\d+")) {
			this.boardDepth = this.piecesCount;
			this.piecesCount = Integer.parseInt(this.boardType);
			this.boardType = scanner.next().toUpperCase(Locale.ROOT);
		}
		// BitField must at least can store up to +1 the board size. This is needed because
		// we want to store the boundary, which is on the edges.
		this.fieldFactory = new BitFieldFactory(this.boardWidth + 1, this.boardHeight + 1, this.boardDepth > 1 ? this.boardDepth + 1 : 1);
		if(this.boardType.equals("DEFAULT"))
			this.boardBoundaryField = Utils.generateRectangularBitField(this.fieldFactory, this.boardWidth, this.boardHeight);
		else if(this.boardType.equals("PYRAMID"))
			this.boardBoundaryField = Utils.generatePyramidalBitField(this.fieldFactory, this.boardWidth, this.boardHeight);
		else if(this.boardType.equals("CUSTOM"))
			this.boardBoundaryField = Utils.parseBitFieldTemplate(this.fieldFactory, scanner, this.boardWidth, this.boardHeight, this.boardDepth);
		else
			throw new Error("Unknown board type: " + this.boardType);
		this.boardBoundaryField.offsetX(1);
		this.boardBoundaryField.offsetY(1);
		if(this.boardDepth > 1)
			this.boardBoundaryField.offsetZ(1);
		this.boardBoundaryField.complement();
		scanner.useDelimiter("\\A");
		String scannerRest = scanner.hasNext() ? scanner.next() : "";
		String[] piecesTemplate = Utils.splitPiecesTemplate(scannerRest);
		this.pieces = new Piece[piecesTemplate.length];
		for(int i = 0; i < piecesTemplate.length; i++) {
			var pieceContentField = Utils.parseBitFieldTemplate(this.fieldFactory, piecesTemplate[i]);
			this.pieces[i] = new Piece(pieceContentField.toImmutable());
		}
		this.board = new Board(this.boardBoundaryField.toImmutable(), this.boardBoundaryField.toClear(), this.pieces);
		this.baseSolver = new Solver(this.board);
		this.solvers = Utils.defaultSolverBrancher(this.baseSolver, cmdBranch);
	}

	public void execute() throws Exception {
		if(this.cmdNoInteractive || !isConsoleInteractive) {
			this.executeNonInteractive();
			return;
		}
	}
	protected volatile boolean shouldStop = false;
	protected void executeNonInteractive() throws Exception {
		{
			System.out.println(String.format("Board width: %d", this.boardWidth));
			System.out.println(String.format("Board height: %d", this.boardHeight));
			System.out.println(String.format("Board depth: %d", this.boardDepth));
			System.out.println(String.format("Board type: %s", this.boardType));
			System.out.println(String.format("Board shape:"));
			System.out.println(Utils.stringBitField(this.boardBoundaryField.toComplement(), 'X'));
			System.out.println();
			var compiledPieces = this.board.getCompiledPieces();
			for(int i = 0; i < compiledPieces.length; i++) {
				var id = Utils.numberToAlphabet(i);
				var compiledPiece = compiledPieces[i];
				var piece = compiledPiece.getPiece();
				System.out.println(String.format("Piece %s: %d shapes, %d compiled shapes", id, piece.getShapes().length, compiledPiece.getCompiledShapes().length));
				System.out.println(Utils.stringBitField(piece.getBaseShapeField(), id.charAt(id.length() - 1)));
				System.out.println();
			}
			for(int i = 0; i < this.solvers.length; i++) {
				var solver = this.solvers[i];
				System.out.println(String.format("Solver %d: %d permutations", i, solver.getMaxPlacement() - solver.getMinPlacement()));
			}
		}
		var printLock = new Object();
		var solutions = Collections.synchronizedList(new ArrayList<Solution>());
		var futures = new Future[this.cmdThread];
		var taskTimes = new long[this.cmdThread];
		var splitEvery = Math.ceilDiv(this.solvers.length, this.cmdThread);
		for(int _i = 0; _i < this.cmdThread; _i++) {
			var i = _i;
			var start = i * splitEvery;
			var end = Math.min(this.solvers.length, (i + 1) * splitEvery);
			futures[i] = executorService.submit(new Callable<Void>() {
				public Void call() throws Exception {
					var repeat = 0;
					var allFinished = false;
					var timeStart = System.currentTimeMillis();
					while(!AppCLI.this.shouldStop && !allFinished && repeat < 65535) {
						if(repeat % 8192 == 0)
							Thread.sleep(1);
						repeat++;
						allFinished = true;
						for(int j = start; j < end; j++) {
							var solver = AppCLI.this.solvers[j];
							if(solver.isCompleted()) continue;
							allFinished = false;
							solver.step();
							if(!solver.isSolved()) continue;
							var duration = (taskTimes[i] + System.currentTimeMillis() - timeStart) / (end - start);
							solutions.add(new Solution(solver, duration));
							if(solutions.size() < AppCLI.this.cmdSolutions) continue;
							synchronized(printLock) {
								AppCLI.this.shouldStop = true;
								printLock.notify();
							}
							break;
						}
					}
					taskTimes[i] += System.currentTimeMillis() - timeStart;
					if(!AppCLI.this.shouldStop && !allFinished)
						futures[i] = executorService.submit(this);
					return null;
				}
			});
		}
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			for(int i = 0; i < futures.length; i++) {
				if(futures[i].isDone()) continue;
				futures[i].cancel(true);
			}
			synchronized(printLock) {
				this.shouldStop = true;
				printLock.notify();
			}
		}));
		try {
			var tempSolutionCounts = ObjectIntMaps.mutable.empty();
			Runnable printSolverStats = () -> {
				synchronized(solutions) {
					for(var solver : tempSolutionCounts.keySet())
						tempSolutionCounts.put(solver, 0);
					for(var solution : solutions)
						tempSolutionCounts.addToValue(solution.solver, 1);
				}
				for(int i = 0; i < this.solvers.length; i++) {
					var solver = this.solvers[i];
					var minPlacement = solver.getMinPlacement();
					var maxPlacement = solver.getMaxPlacement();
					var placement = !solver.isCompleted() ? solver.getCurrentPlacement() : solver.getMaxPlacement();
					var progress = placement - minPlacement;
					var maxProgress = maxPlacement - minPlacement;
					var solutionsCount = tempSolutionCounts.get(solver);
					System.out.println(String.format("Solver %d: %d/%d permutations (%.2f%%), %d solutions", i, progress, maxProgress, (float) progress * 100 / (float) maxProgress, solutionsCount));
				}
			};
			while(!this.shouldStop) {
				printSolverStats.run();
				boolean allDone = true;
				for(int i = 0; i < futures.length; i++) {
					if(futures[i].isDone()) continue;
					allDone = false;
					break;
				}
				if(allDone) {
					synchronized(printLock) {
						this.shouldStop = true;
						printLock.notify();
					}
					break;
				}
				synchronized(printLock) {
					printLock.wait(1000);
				}
			}
			printSolverStats.run();
		} finally {
			synchronized(printLock) {
				this.shouldStop = true;
				printLock.notify();
			}
			for(int i = 0; i < futures.length; i++) {
				if(futures[i].isDone()) continue;
				futures[i].cancel(true);
			}
		}
		System.out.println();
		for(int i = 0; i < solutions.size(); i++) {
			var solution = solutions.get(i);
			System.out.println(String.format("Solution: #%d (Took %d ms)", i, solution.duration));
			System.out.println(Utils.stringBitField(solution.bitFields, solution.chars));
			System.out.println();
		}
		if(solutions.size() == 0)
			System.out.println(String.format("No solution found"));
		else {
			var longestDuration = solutions.stream().reduce(1L, (a, s) -> Math.max(a, s.duration), (a, b) -> Math.max(a, b));
			var msPerSolution = (float) longestDuration / solutions.size();
			var solutionsPerSecond = 1000 / msPerSolution;
			System.out.println(String.format("Speed: %.2f ms/solution <==> %.2f solutions/second", msPerSolution, solutionsPerSecond));
		}
		executorService.shutdown();
		try {
			if(!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
				System.out.println("Forcing shutdown...");
				executorService.shutdownNow();
			}
		} catch(InterruptedException e) {
			executorService.shutdownNow();
		}
		System.out.println("All tasks finished. Exiting program.");
	}

	protected static class Solution {
		public final Solver solver;
		public final BitField[] bitFields;
		public final char[] chars;
		public final long duration;

		public Solution(Solver solver, long duration) {
			var compiledPieces = solver.getBoard().getCompiledPieces();
			var bitFields = Lists.mutable.<BitField>empty();
			var chars = CharLists.mutable.empty();
			for(int j = 0; j < compiledPieces.length; j++) {
				var id = Utils.numberToAlphabet(j);
				var compiledPiece = compiledPieces[j];
				var compiledPiecePlacement = solver.getBoard().getCompiledPiecePlacement(compiledPiece);
				if(compiledPiecePlacement == -1) continue;
				bitFields.add(compiledPiece.getCompiledShapes()[compiledPiecePlacement].getContentField());
				chars.add(id.charAt(id.length() - 1));
			}
			this.solver = solver;
			this.bitFields = bitFields.toArray(n -> new BitField[n]);
			this.chars = chars.toArray();
			this.duration = duration;
		}
	}
}
