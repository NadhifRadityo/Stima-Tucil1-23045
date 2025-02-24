package io.github.nadhifradityo.stima_tucil1_23045;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.primitive.CharLists;
import org.eclipse.collections.api.factory.primitive.ObjectIntMaps;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.MouseCaptureMode;
import com.googlecode.lanterna.terminal.Terminal;

import io.github.nadhifradityo.stima_tucil1_23045.Board.CompiledShape;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.BitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField;
import jnafilechooser.api.JnaFileChooser;

public class AppCLI {

	public static void main(String... args) throws Exception {
		Options options = new Options();
		options.addOption("i", "input", true, "Input case file");
		options.addOption("b", "branch", true, "Branches allowed for solver. Default 8");
		options.addOption("t", "thread", true, "How many threads should be used. Default 2");
		options.addOption("s", "solutions", true, "Solutions to generate. Default 100");
		options.addOption("o", "output", true, "Output file");
		options.addOption("u", "no-interactive", false, "Force no interactive console");
		options.addOption("c", "virtual-console", false, "Use virtual console");
		options.addOption("h", "help", false, "Shows help message");
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		String cmdInput;
		int cmdBranch;
		int cmdThread;
		int cmdSolutions;
		String cmdOutput;
		boolean cmdNoInteractive;
		boolean cmdVirtualConsole;
		try {
			CommandLine cmd = parser.parse(options, args);
			if(cmd.hasOption("help")) {
				formatter.printHelp("", options);
				System.exit(0);
			}
			cmdInput = cmd.getOptionValue("input");
			if(cmdInput == null) {
				var fileChooser = new JnaFileChooser();
				fileChooser.addFilter("Text Files", "txt");
				System.err.println("Input file not specified, opening file chooser...");
				if(fileChooser.showOpenDialog(null))
					cmdInput = fileChooser.getSelectedFile().getAbsolutePath();
				else {
					if(isAwtHeadless() || (cmd.hasOption("no-interactive") && !isConsoleInteractive))
						System.err.println("Open file aborted");
					else
						JOptionPane.showMessageDialog(null, "Open file aborted", "Error", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
			cmdBranch = Integer.parseInt(cmd.getOptionValue("branch", "8"));
			cmdThread = Integer.parseInt(cmd.getOptionValue("thread", "2"));
			cmdSolutions = Integer.parseInt(cmd.getOptionValue("solutions", "100"));
			cmdOutput = cmd.getOptionValue("output");
			cmdNoInteractive = cmd.hasOption("no-interactive");
			cmdVirtualConsole = cmd.hasOption("virtual-console");
		} catch(ParseException e) {
			System.out.println("Error: " + e.getMessage());
			formatter.printHelp("", options);
			return;
		}
		var instance = new AppCLI(cmdInput, cmdBranch, cmdThread, cmdSolutions, cmdOutput, cmdNoInteractive, cmdVirtualConsole);
		try {
			instance.prepare();
			instance.prepareConsole();
			instance.execute();
		} catch(Exception e) {
			instance.alertOrPrintErr(Utils.throwableAsString(e));
		}
	}

	protected static boolean isAwtHeadless() {
		try {
			Class<?> klass = Class.forName("java.awt.GraphicsEnvironment");
			Method method = klass.getDeclaredMethod("isHeadless");
			return (Boolean) method.invoke(null);
		} catch(Exception ignore) {
			return true;
		}
	}
	protected void alertOrPrintErr(String message) {
		if(isAwtHeadless() || (this.cmdNoInteractive && !isConsoleInteractive))
			this.logStream.println(message);
		else
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	protected static final boolean isConsoleInteractive = System.console() != null && System.out.getClass().getName().equals("java.io.PrintStream");
	protected static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	protected final String cmdInput;
	protected final int cmdBranch;
	protected final int cmdThread;
	protected final int cmdSolutions;
	protected String cmdOutput;
	protected final boolean cmdNoInteractive;
	protected final boolean cmdVirtualConsole;

	public AppCLI(String cmdInput, int cmdBranch, int cmdThread, int cmdSolutions, String cmdOutput, boolean cmdNoInteractive, boolean cmdVirtualConsole) {
		this.cmdInput = cmdInput;
		this.cmdBranch = cmdBranch;
		this.cmdThread = cmdThread;
		this.cmdSolutions = cmdSolutions;
		this.cmdOutput = cmdOutput;
		this.cmdNoInteractive = cmdNoInteractive;
		this.cmdVirtualConsole = cmdVirtualConsole;
	}

	protected PrintStream logStream;
	protected PrintStream outputStream;
	protected Terminal terminal;
	protected Screen screen;

	public void prepareConsole() throws Exception {
		if(this.cmdNoInteractive || (!isConsoleInteractive && isAwtHeadless())) {
			if(this.cmdVirtualConsole) {
				alertOrPrintErr("Virtual console is only available in interactive mode");
				System.exit(1);
				return;
			}
			if(this.cmdOutput != null) {
				this.logStream = System.out;
				this.outputStream = new PrintStream(new File(this.cmdOutput));
			} else {
				this.logStream = System.err;
				this.outputStream = System.out;
			}
			return;
		}
		if(this.cmdVirtualConsole && isAwtHeadless()) {
			alertOrPrintErr("Virtual console is not available in headless environment");
			System.exit(1);
			return;
		}
		this.logStream = System.err;
		this.outputStream = System.out;
		var terminalFactory = new DefaultTerminalFactory();
		terminalFactory.setMouseCaptureMode(MouseCaptureMode.CLICK_RELEASE_DRAG_MOVE);
		try {
			this.terminal = this.cmdVirtualConsole ? terminalFactory.createTerminalEmulator() : terminalFactory.createHeadlessTerminal();
		} catch(Throwable e) {
			if(e.getMessage().contains("To start java on Windows, use javaw!") && !this.cmdVirtualConsole && !isAwtHeadless())
				this.terminal = terminalFactory.createTerminalEmulator();
			else
				throw e;
		}
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
		this.boardHeight = scanner.nextInt();
		this.boardWidth = scanner.nextInt();
		this.boardDepth = 1;
		this.piecesCount = scanner.nextInt();
		this.boardType = scanner.next().toUpperCase(Locale.ROOT);
		if(this.boardType.matches("-?\\d+")) {
			this.boardDepth = this.piecesCount;
			this.piecesCount = Integer.parseInt(this.boardType);
			this.boardType = scanner.next().toUpperCase(Locale.ROOT);
		}
		// BitField must at least can store up to +2 the board size. This is needed because
		// we want to store the boundary, which is on the edges.
		this.fieldFactory = new BitFieldFactory(this.boardWidth + 2, this.boardHeight + 2, this.boardDepth > 1 ? this.boardDepth + 2 : 1);
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

	protected volatile boolean shouldStop;
	protected Object printLock;
	protected List<Solution> solutions;
	protected Future<Void>[] futures;
	protected long[] taskTimes;
	protected void stopSolvers() {
		for(int i = 0; i < this.futures.length; i++) {
			if(this.futures[i].isDone()) continue;
			this.futures[i].cancel(true);
		}
		synchronized(this.printLock) {
			this.shouldStop = true;
			this.printLock.notify();
		}
	}
	protected void runSolvers() {
		this.shouldStop = false;
		this.printLock = new Object();
		this.solutions = Collections.synchronizedList(new ArrayList<Solution>());
		this.futures = new Future[this.cmdThread];
		this.taskTimes = new long[this.cmdThread];
		var splitEvery = Math.ceilDiv(this.solvers.length, this.cmdThread);
		for(int _i = 0; _i < this.cmdThread; _i++) {
			var i = _i;
			var startIndex = i * splitEvery;
			var endIndex = Math.min(this.solvers.length, (i + 1) * splitEvery);
			this.futures[i] = executorService.submit(new Callable<Void>() {
				public Void call() throws Exception {
					var repeat = 0;
					var allFinished = false;
					var timeStart = System.nanoTime();
					while(!AppCLI.this.shouldStop && !allFinished && repeat < 65535) {
						if(repeat % 8192 == 0)
							Thread.sleep(1);
						repeat++;
						allFinished = true;
						for(int j = startIndex; j < endIndex; j++) {
							var solver = AppCLI.this.solvers[j];
							if(solver.isCompleted()) continue;
							allFinished = false;
							solver.step();
							if(!solver.isSolved()) continue;
							var duration = AppCLI.this.taskTimes[i] + System.nanoTime() - timeStart;
							// var durationInterleaved = Math.ceilDiv(duration, (end - start) * (repeat - 1) + j);
							var durationInterleaved = Math.ceilDiv(duration, (endIndex - startIndex) * Math.max(1, repeat / 32 - 1) + j);
							AppCLI.this.solutions.add(new Solution(solver, duration, durationInterleaved));
							if(AppCLI.this.solutions.size() < AppCLI.this.cmdSolutions) continue;
							AppCLI.this.stopSolvers();
							break;
						}
					}
					AppCLI.this.taskTimes[i] += System.nanoTime() - timeStart;
					if(!AppCLI.this.shouldStop && !allFinished)
						AppCLI.this.futures[i] = executorService.submit(this);
					return null;
				}
			});
		}
	}

	public void execute() throws Exception {
		if(this.cmdNoInteractive || (!isConsoleInteractive && isAwtHeadless())) {
			this.executeNonInteractive();
			return;
		}
		this.executeInteractive();
	}
	protected void executeNonInteractive() throws Exception {
		this.logStream.println(String.format("Board width: %d", this.boardWidth));
		this.logStream.println(String.format("Board height: %d", this.boardHeight));
		this.logStream.println(String.format("Board depth: %d", this.boardDepth));
		this.logStream.println(String.format("Board type: %s", this.boardType));
		this.logStream.println(String.format("Board shape:"));
		this.logStream.println(Utils.stringBitField(this.boardBoundaryField.toComplement(), 'X'));
		this.logStream.println();
		var compiledPieces = this.board.getCompiledPieces();
		for(int i = 0; i < compiledPieces.length; i++) {
			var id = Utils.numberToAlphabet(i);
			var compiledPiece = compiledPieces[i];
			var piece = compiledPiece.getPiece();
			this.logStream.println(String.format("Piece %s: %d shapes, %d compiled shapes", id, piece.getShapes().length, compiledPiece.getCompiledShapes().length));
			this.logStream.println(Utils.stringBitField(piece.getBaseShapeField(), id.charAt(id.length() - 1)));
			this.logStream.println();
		}
		for(int i = 0; i < this.solvers.length; i++) {
			var solver = this.solvers[i];
			this.logStream.println(String.format("Solver %d: %d permutations", i, solver.getMaxPlacement() - solver.getMinPlacement()));
		}
		this.runSolvers();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			this.stopSolvers();
		}));
		try {
			var tempSolutionCounts = ObjectIntMaps.mutable.empty();
			Runnable printSolverStats = () -> {
				synchronized(this.solutions) {
					for(var solver : tempSolutionCounts.keySet())
						tempSolutionCounts.put(solver, 0);
					for(var solution : this.solutions)
						tempSolutionCounts.addToValue(solution.solver, 1);
				}
				for(int i = 0; i < this.solvers.length; i++) {
					var solver = this.solvers[i];
					var solutionsCount = tempSolutionCounts.get(solver);
					var progressTracker = solver.getProgressTracker();
					String progressBar;
					double progressPercent;
					synchronized(progressTracker) {
						progressBar = progressTracker.getProgressBar(50);
						progressPercent = progressTracker.getOverallProgress() * 100;
					}
					this.logStream.println(String.format("Solver #%d: %4d solutions, %s (%-6.2f%%)", i, solutionsCount, progressBar, progressPercent));
				}
			};
			while(!this.shouldStop) {
				printSolverStats.run();
				boolean allDone = true;
				for(int i = 0; i < this.futures.length; i++) {
					if(this.futures[i].isDone()) continue;
					allDone = false;
					break;
				}
				if(allDone) {
					this.stopSolvers();
					break;
				}
				synchronized(this.printLock) {
					this.printLock.wait(1000);
				}
			}
			printSolverStats.run();
		} finally {
			this.stopSolvers();
		}
		this.logStream.println();
		for(int i = 0; i < this.solutions.size(); i++) {
			var solution = this.solutions.get(i);
			var solverIndex = -1;
			for(int j = 0; j < this.solvers.length; j++) {
				if(this.solvers[j] != solution.solver) continue;
				solverIndex = j;
				break;
			}
			this.outputStream.println(String.format("Solution: #%d (from Solver #%d)", i, solverIndex));
			this.outputStream.println(String.format("Duration: %.2f ms (%.2f ms interleaved not compensated)", (double) solution.durationInterleaved / 1000000, (double) solution.duration / 1000000));
			this.outputStream.println(String.format("Placement ID: %d", solution.placement));
			this.outputStream.println(Utils.stringBitField(solution.bitFields, solution.chars));
			this.outputStream.println();
		}
		if(this.solutions.size() == 0)
			this.outputStream.println(String.format("No solution found"));
		else {
			var longestDuration = this.solutions.stream().reduce(1L, (a, s) -> Math.max(a, s.duration), (a, b) -> Math.max(a, b));
			var msPerSolution = (double) longestDuration / 1000000 / this.solutions.size();
			var solutionsPerSecond = 1000 / msPerSolution;
			this.outputStream.println(String.format("Speed: %.2f ms/solution <==> %.2f solutions/second", msPerSolution, solutionsPerSecond));
		}
		executorService.shutdown();
		try {
			if(!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
				this.logStream.println("Forcing shutdown...");
				executorService.shutdownNow();
			}
		} catch(InterruptedException e) {
			executorService.shutdownNow();
		}
		this.logStream.println("All tasks finished. Exiting program.");
	}
	protected void executeInteractive() throws Exception {
		screen.startScreen();
		var resizeListeners = new ArrayList<Runnable>();
		var updaters = new ArrayList<Runnable>();
		var gui = new MultiWindowTextGUI(screen);
		gui.setBlockingIO(false);

		// Informational Window
		var infoWindow = new BasicWindow("Board Information");
		infoWindow.setHints(Arrays.asList(Window.Hint.FIXED_POSITION, Window.Hint.FIXED_SIZE));
		infoWindow.setPosition(TerminalPosition.OFFSET_1x1);
		infoWindow.setFixedSize(TerminalSize.ZERO);
		resizeListeners.add(() -> {
			var newSize = screen.getTerminalSize();
			infoWindow.setFixedSize(new TerminalSize(
				(int) ((newSize.getColumns() - 6) * 1.0),
				(int) Math.min(20, (newSize.getRows() - 5) * 0.5)
			));
		});
		gui.addWindow(infoWindow);

		var infoPanel = new Panel();
		infoPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL).setSpacing(2));
		infoWindow.setComponent(infoPanel);

		var infoBoardPanel = new Panel();
		infoBoardPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		infoPanel.addComponent(infoBoardPanel);
		var infoBoardSizeLabel = new Label(String.format("Size: %dx%dx%d", this.boardWidth, this.boardHeight, this.boardDepth));
		infoBoardPanel.addComponent(infoBoardSizeLabel);
		var infoBoardTypeLabel = new Label(String.format("Type: %s", this.boardType));
		infoBoardPanel.addComponent(infoBoardTypeLabel);
		var infoBoardSelectorsList = new RadioBoxList<String>();
		infoBoardSelectorsList.addItem("Pieces");
		infoBoardSelectorsList.addItem("Solutions");
		infoBoardPanel.addComponent(infoBoardSelectorsList);

		var infoBoardCompiledPiecesPanel = new Panel();
		infoBoardCompiledPiecesPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		var compiledPiecesLabel = new Label("Compiled Pieces:");
		infoBoardCompiledPiecesPanel.addComponent(compiledPiecesLabel);
		var compiledPiecesList = new RadioBoxList<String>();
		infoBoardCompiledPiecesPanel.addComponent(compiledPiecesList);

		var infoBoardCompiledShapesPanel = new Panel();
		infoBoardCompiledShapesPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		var compiledShapesLabel = new Label("Compiled Shapes:");
		infoBoardCompiledShapesPanel.addComponent(compiledShapesLabel);
		var compiledShapesList = new RadioBoxList<String>();
		infoBoardCompiledShapesPanel.addComponent(compiledShapesList);

		var infoBoardSolutionsPanel = new Panel();
		infoBoardSolutionsPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		var solutionsSpeedLabel = new Label("Speed:");
		infoBoardSolutionsPanel.addComponent(solutionsSpeedLabel);
		var solutionsLabel = new Label("Solutions:");
		infoBoardSolutionsPanel.addComponent(solutionsLabel);
		var solutionsList = new RadioBoxList<String>();
		infoBoardSolutionsPanel.addComponent(solutionsList);

		var infoBoardShapeFieldPanel = new Panel();
		infoBoardShapeFieldPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		var compiledShapeFieldLabel = new LanternaExt.AnsiLabel("");
		infoBoardShapeFieldPanel.addComponent(compiledShapeFieldLabel);

		infoBoardSelectorsList.addListener((selectedIndex, previousSelection) -> {
			compiledPiecesList.clearSelection();
			compiledShapesList.clearSelection();
			solutionsList.clearSelection();
			infoPanel.removeComponent(infoBoardCompiledPiecesPanel);
			infoPanel.removeComponent(infoBoardSolutionsPanel);
			if(selectedIndex == 0)
				infoPanel.addComponent(infoBoardCompiledPiecesPanel);
			if(selectedIndex == 1)
				infoPanel.addComponent(infoBoardSolutionsPanel);
		});
		infoBoardSelectorsList.setCheckedItemIndex(0);

		var shapes = new ArrayList<Piece.Shape>();
		var compiledShapes = new ArrayList<Board.CompiledShape>();
		compiledPiecesList.addItem("");
		compiledPiecesList.addItem("Board Shape");
		for(int i = 0; i < this.board.getCompiledPieces().length; i++) {
			var compiledPiece = this.board.getCompiledPieces()[i];
			var id = Utils.numberToAlphabet(i);
			for(var shape : compiledPiece.getPiece().getShapes()) {
				shapes.add(shape);
				compiledPiecesList.addItem(String.format("%s %s", id, shape.getConfigurationName()));
			}
		}
		compiledPiecesList.addListener((selectedIndex, previousSelection) -> {
			infoPanel.removeComponent(infoBoardCompiledShapesPanel);
			infoPanel.removeComponent(infoBoardShapeFieldPanel);
			if(selectedIndex == -1 || selectedIndex == 0) return;
			if(selectedIndex == 1) {
				infoPanel.addComponent(infoBoardShapeFieldPanel);
				compiledShapeFieldLabel.setText(Utils.stringBitField(this.board.getBoundaryField().withComplement(), 'X'));
				return;
			}
			infoPanel.addComponent(infoBoardCompiledShapesPanel);
			compiledShapesList.clearItems();
			compiledShapesList.addItem("");
			compiledShapes.clear();
			var shape = shapes.get(selectedIndex - 2);
			for(var compiledPiece : this.board.getCompiledPieces()) {
				for(var compiledShape : compiledPiece.getCompiledShapes()) {
					if(compiledShape.shape != shape) continue;
					compiledShapes.add(compiledShape);
					compiledShapesList.addItem(compiledShape.getConfigurationName());
				}
			}
		});
		compiledShapesList.addListener((selectedIndex, previousSelection) -> {
			infoPanel.removeComponent(infoBoardShapeFieldPanel);
			if(selectedIndex == -1 || selectedIndex == 0) return;
			infoPanel.addComponent(infoBoardShapeFieldPanel);
			var compiledShape = compiledShapes.get(selectedIndex - 1);
			var pieceIndex = -1;
			var compiledPieces_ = this.board.getCompiledPieces();
			for(int i = 0; i < compiledPieces_.length; i++) {
				for(var shape : compiledPieces_[i].getCompiledShapes()) {
					if(shape != compiledShape) continue;
					pieceIndex = i;
					break;
				}
				if(pieceIndex != -1)
					break;
			}
			var id = Utils.numberToAlphabet(pieceIndex);
			var character = id.length() > 0 ? id.charAt(id.length() - 1) : 'X';
			var ansiStyle = Utils.getAnsiColorFromSeed("" + pieceIndex);
			compiledShapeFieldLabel.setText(
				ansiStyle +
				Utils.stringBitField(compiledShape.getContentField(), character, false, false, true)
			);
		});

		updaters.add(() -> {
			var solutionsCount = 0;
			var longestDuration = 1L;
			synchronized(this.solutions) {
				solutionsCount = this.solutions.size();
				longestDuration = this.solutions.stream().reduce(1L, (a, s) -> Math.max(a, s.duration), (a, b) -> Math.max(a, b));
			}
			var msPerSolution = (double) longestDuration / 1000000 / this.solutions.size();
			var solutionsPerSecond = 1000 / msPerSolution;
			solutionsLabel.setText(String.format("Solutions: (%d available)", solutionsCount));
			solutionsSpeedLabel.setText(String.format("Speed: %.2f ms/solution <==> %.2f solutions/second", msPerSolution, solutionsPerSecond));
		});
		updaters.add(() -> {
			synchronized(this.solutions) {
				var itemCount = solutionsList.getItemCount() - 1;
				var itemAdded = this.solutions.size() - itemCount;
				for(int i = 0; i < itemAdded; i++) {
					var solution = this.solutions.get(itemCount + i);
					var solverIndex = -1;
					for(int j = 0; j < this.solvers.length; j++) {
						if(this.solvers[j] != solution.solver) continue;
						solverIndex = j;
						break;
					}
					solutionsList.addItem(String.format("Solution #%d (from Solver #%d)", itemCount + i, solverIndex));
				}
			}
		});

		solutionsList.addItem("");
		solutionsList.addListener((selectedIndex, previousSelection) -> {
			infoPanel.removeComponent(infoBoardShapeFieldPanel);
			if(selectedIndex == -1 || selectedIndex == 0) return;
			infoPanel.addComponent(infoBoardShapeFieldPanel);
			var solution = this.solutions.get(selectedIndex - 1);
			var bitFields = solution.bitFields;
			var chars = solution.chars;
			var ansiStyles = new String[bitFields.length];
			for(int i = 0; i < bitFields.length; i++)
				ansiStyles[i] = Utils.getAnsiColorFromSeed("" + i);
			compiledShapeFieldLabel.setText(
				String.format("Duration: %.2f ms (%.2f ms interleaved not compensated)\n", (double) solution.durationInterleaved / 1000000, (double) solution.duration / 1000000) +
				String.format("Placement ID: %d\n", solution.placement) +
				Utils.stringBitField(bitFields, chars, ansiStyles)
			);
		});

		this.runSolvers();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			this.stopSolvers();
		}));

		// Solver Windows
		var solverWindows = new ArrayList<BasicWindow>();
		int[] solverWindowsScroll = new int[1];
		var infoScrollSolverUpButton = new Button("Scroll Up");
		infoBoardPanel.addComponent(infoScrollSolverUpButton);
		infoScrollSolverUpButton.addListener((button) -> {
			solverWindowsScroll[0] += 10;
			for(var resizeListener : resizeListeners)
				resizeListener.run();
			for(var solverWindow : solverWindows)
				solverWindow.invalidate();
		});
		var infoScrollSolverDownButton = new Button("Scroll Down");
		infoBoardPanel.addComponent(infoScrollSolverDownButton);
		infoScrollSolverDownButton.addListener((button) -> {
			solverWindowsScroll[0] -= 10;
			for(var resizeListener : resizeListeners)
				resizeListener.run();
			for(var solverWindow : solverWindows)
				solverWindow.invalidate();
		});

		var tempSolutionCounts = ObjectIntMaps.mutable.empty();
		updaters.add(() -> {
			synchronized(this.solutions) {
				for(var solver : tempSolutionCounts.keySet())
					tempSolutionCounts.put(solver, 0);
				for(var solution : this.solutions)
					tempSolutionCounts.addToValue(solution.solver, 1);
			}
		});
		for(int _i = 0; _i < this.solvers.length; _i++) {
			var i = _i;
			var solver = this.solvers[i];
			var solverWindow = new BasicWindow(String.format("Solver #%d", i));
			solverWindow.setHints(Arrays.asList(Window.Hint.FIXED_POSITION, Window.Hint.FIXED_SIZE));
			solverWindow.setPosition(TerminalPosition.OFFSET_1x1);
			solverWindow.setFixedSize(TerminalSize.ZERO);
			resizeListeners.add(() -> {
				var width = 35;
				var height = 10;
				var newSize = screen.getTerminalSize();
				var columns = newSize.getColumns() / (width + 6);
				var infoWindowBottom = infoWindow.getPosition().getRow() + infoWindow.getSize().getRows() + 5;
				var centerOffset = (newSize.getColumns() - columns * (width + 6)) / 2;
				solverWindow.setPosition(new TerminalPosition(
					(int) centerOffset + (i % columns) * (width + 6),
					(int) solverWindowsScroll[0] + infoWindowBottom + (i / columns) * (height + 5)
				));
				solverWindow.setFixedSize(new TerminalSize(
					(int) width,
					(int) height
				));
			});
			gui.addWindow(solverWindow);

			var solverPanel = new Panel();
			solverPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			solverWindow.setComponent(solverPanel);
			var solverProgressLabel = new Label("Progress:");
			solverPanel.addComponent(solverProgressLabel);
			var solutionsCountLabel = new Label("Solutions:");
			solverPanel.addComponent(solutionsCountLabel);
			var placementStateLabel = new Label("Placement:");
			solverPanel.addComponent(placementStateLabel);
			var boardStateLabel = new LanternaExt.AnsiLabel("");
			solverPanel.addComponent(boardStateLabel);

			solverWindows.add(solverWindow);
			var tempSolver = new Solver(this.board.clone());
			var titleState = new int[1];
			updaters.add(() -> {
				if(solver.isCompleted()) {
					if(titleState[0] != 1) {
						solverWindow.setTitle(String.format("Solver #%d (Done)", i));
						titleState[0] = 1;
					}
				} else if(solver.isSolved()) {
					if(titleState[0] != 2) {
						solverWindow.setTitle(String.format("Solver #%d (Solved)", i));
						titleState[0] = 2;
					}
				} else {
					if(titleState[0] != 0) {
						solverWindow.setTitle(String.format("Solver #%d", i));
						titleState[0] = 0;
					}
				}
				var progressTracker = solver.getProgressTracker();
				String progressBar;
				double progressPercent;
				synchronized(progressTracker) {
					progressBar = progressTracker.getProgressBar(Math.max(6, solverPanel.getSize().getColumns() - 6));
					progressPercent = progressTracker.getOverallProgress() * 100;
				}
				solverProgressLabel.setText(String.format("Progress: %s (%-6.2f%%)", progressBar, progressPercent));
				solutionsCountLabel.setText(String.format("Solutions: %d", tempSolutionCounts.get(solver)));
				placementStateLabel.setText(String.format("Placement: %d/%d", solver.getCurrentPlacement(), solver.getMaxPlacement() - solver.getMinPlacement()));
				tempSolver.setCurrentPlacement(solver.getCurrentPlacement());
				var compiledPieces_ = solver.getBoard().getCompiledPieces();
				var compiledShapes_ = Lists.mutable.<CompiledShape>empty();
				var chars = CharLists.mutable.empty();
				for(int j = 0; j < compiledPieces_.length; j++) {
					var id = Utils.numberToAlphabet(j);
					var compiledPiece = compiledPieces_[j];
					var compiledPiecePlacement = solver.getBoard().getCompiledPiecePlacement(compiledPiece);
					if(compiledPiecePlacement == -1) continue;
					compiledShapes_.add(compiledPiece.getCompiledShapes()[compiledPiecePlacement]);
					chars.add(id.charAt(id.length() - 1));
				}
				var bitFields = compiledShapes_.stream().map(s -> s.getContentField()).toArray(n -> new BitField[n]);
				var ansiStyles = new String[bitFields.length];
				for(int j = 0; j < bitFields.length; j++)
					ansiStyles[j] = Utils.getAnsiColorFromSeed("" + j);
				boardStateLabel.setText(Utils.stringBitField(bitFields, chars.toArray(), ansiStyles, false, false, true));
			});
		}

		var saveSolutionsButton = new Button("Save Solutions");
		infoBoardPanel.addComponent(saveSolutionsButton);
		var saveTaskRef = new Object[1];
		saveSolutionsButton.addListener((button) -> {
			if(saveTaskRef[0] != null) return;
			saveTaskRef[0] = executorService.submit(() -> {
				var fileChooser = new JnaFileChooser();
				if(this.cmdOutput != null) {
					var defaultOutputFile = new File(this.cmdOutput);
					fileChooser.setCurrentDirectory(defaultOutputFile.getParentFile().getAbsolutePath());
					fileChooser.setDefaultFileName(defaultOutputFile.getName());
				}
				fileChooser.addFilter("Text Files", "txt");
				if(fileChooser.showSaveDialog(null)) {
					var selectedFile = fileChooser.getSelectedFile();
					if(!selectedFile.getName().endsWith(".txt"))
						selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
					this.cmdOutput = selectedFile.getAbsolutePath();
					try(var outputStream = new PrintStream(selectedFile)) {
						synchronized(this.solutions) {
							for(int i = 0; i < this.solutions.size(); i++) {
								var solution = this.solutions.get(i);
								var solverIndex = -1;
								for(int j = 0; j < this.solvers.length; j++) {
									if(this.solvers[j] != solution.solver) continue;
									solverIndex = j;
									break;
								}
								outputStream.println(String.format("Solution: #%d (from Solver #%d)", i, solverIndex));
								outputStream.println(String.format("Duration: %.2f ms (%.2f ms interleaved not compensated)", (double) solution.durationInterleaved / 1000000, (double) solution.duration / 1000000));
								outputStream.println(String.format("Placement ID: %d", solution.placement));
								outputStream.println(Utils.stringBitField(solution.bitFields, solution.chars));
								outputStream.println();
							}
							if(this.solutions.size() == 0)
								outputStream.println(String.format("No solution found"));
						}
					} catch(Throwable e) {
						e.printStackTrace(this.logStream);
					}
				}
				saveTaskRef[0] = null;
				return null;
			});
		});

		for(var resizeListener : resizeListeners)
			resizeListener.run();
		gui.setActiveWindow(infoWindow);
		gui.moveToTop(infoWindow);
		var escaped = false;
		while(!escaped) {
			for(var solverUpdater : updaters)
				solverUpdater.run();
			screen.clear();
			if(screen.doResizeIfNecessary() != null) {
				for(var resizeListener : resizeListeners)
					resizeListener.run();
				infoWindow.invalidate();
				for(var solverWindow : solverWindows)
					solverWindow.invalidate();
			}
			gui.updateScreen();
			while(true) {
				var input = screen.pollInput();
				if(input == null) break;
				var keyType = input.getKeyType();
				if(keyType == KeyType.EOF) {
					escaped = true;
					break;
				}
				if(keyType == KeyType.Escape)
					escaped = true;
				gui.handleInput(input);
			}
			if(escaped)
				break;
			while(gui.getGUIThread().processEventsAndUpdate());
			synchronized(this.printLock) {
				this.printLock.wait(15);
			}
		}
		screen.stopScreen();
		executorService.shutdown();
		try {
			if(!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
				this.logStream.println("Forcing shutdown...");
				executorService.shutdownNow();
			}
		} catch(InterruptedException e) {
			executorService.shutdownNow();
		}
		this.logStream.println("All tasks finished. Exiting program.");
	}

	protected static class Solution {
		public final Solver solver;
		public final long placement;
		public final CompiledShape[] compiledShapes;
		public final BitField[] bitFields;
		public final char[] chars;
		public final long duration;
		public final long durationInterleaved;

		public Solution(Solver solver, long duration, long durationInterleaved) {
			var compiledPieces = solver.getBoard().getCompiledPieces();
			var compiledShapes = Lists.mutable.<CompiledShape>empty();
			var chars = CharLists.mutable.empty();
			for(int j = 0; j < compiledPieces.length; j++) {
				var id = Utils.numberToAlphabet(j);
				var compiledPiece = compiledPieces[j];
				var compiledPiecePlacement = solver.getBoard().getCompiledPiecePlacement(compiledPiece);
				if(compiledPiecePlacement == -1) continue;
				compiledShapes.add(compiledPiece.getCompiledShapes()[compiledPiecePlacement]);
				chars.add(id.charAt(id.length() - 1));
			}
			this.solver = solver;
			this.placement = solver.getCurrentPlacement();
			this.compiledShapes = compiledShapes.toArray(n -> new CompiledShape[n]);
			this.bitFields = compiledShapes.stream().map(s -> s.getContentField()).toArray(n -> new BitField[n]);
			this.chars = chars.toArray();
			this.duration = duration;
			this.durationInterleaved = durationInterleaved;
		}
	}
}
