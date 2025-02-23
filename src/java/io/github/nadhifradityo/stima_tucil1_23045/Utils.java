package io.github.nadhifradityo.stima_tucil1_23045;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.WeakHashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import io.github.nadhifradityo.stima_tucil1_23045.Board.CompiledPiece;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.BitField;
import io.github.nadhifradityo.stima_tucil1_23045.bitfields.MutableBitField;

public class Utils {
	private Utils() {

	}

	protected static final WeakHashMap<Throwable, String> throwableAsStringCache = new WeakHashMap<>();
	public static String throwableAsString(Throwable throwable) {
		var result = throwableAsStringCache.get(throwable);
		if(result != null) return result;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        result = stringWriter.toString();
		throwableAsStringCache.put(throwable, result);
		return result;
    }

	public static String numberToAlphabet(int n) {
		StringBuilder result = new StringBuilder();
		while(n >= 0) {
			result.insert(0, (char) ('A' + (n % 26)));
			n = (n / 26) - 1;
		}
		return result.toString();
	}

	public static MutableBitField generateRectangularBitField(BitFieldFactory factory, int width, int height) {
		var result = factory.newBitField();
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++)
				result.setValue(i, j, 0, true);
		}
		return result;
	}
	public static MutableBitField generatePyramidalBitField(BitFieldFactory factory, int width, int height) {
		var result = factory.newBitField();
		var depth = Math.min(width, height);
		for(int i = 0; i < depth; i++) {
			for(int j = 0; j < width - i; j++) {
				for(int k = 0; k < height - i; k++)
					result.setValue(j, k, i, true);
			}
		}
		return result;
	}
	public static MutableBitField parseBitFieldTemplate(BitFieldFactory factory, Scanner scanner, int width, int height, int depth) {
		var result = factory.newBitField();
		width = Math.max(0, Math.min(result.getWidth(), width));
		height = Math.max(0, Math.min(result.getHeight(), height));
		depth = Math.max(0, Math.min(result.getDepth(), depth));
		int z = 0;
		int y = 0;
		while(scanner.hasNextLine() && z < depth) {
			String line = scanner.nextLine().trim();
			if(line.startsWith("Z:")) {
				try {
					z = Integer.parseInt(line.substring(2).trim());
					y = 0;
				} catch(NumberFormatException e) {
					z++;
				}
				continue;
			}
			if(y >= height) continue;
			for(int x = 0; x < Math.min(line.length(), width); x++) {
				char character = line.charAt(x);
				if(Character.isWhitespace(character) || character == '.')
					continue;
				result.setValue(x, y, z, true);
			}
			y++;
		}
		return result;
	}
	public static MutableBitField parseBitFieldTemplate(BitFieldFactory factory, String template) {
		return parseBitFieldTemplate(factory, new Scanner(template), Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	protected static final Pattern SPLIT_PIECES_TEMPLATE_PATTERN = Pattern.compile("^\\s*([a-zA-Z0-9])\\1*(?:\\r?\\n\\r?\\1*)*$", Pattern.MULTILINE);
	public static String[] splitPiecesTemplate(String template) {
		var result = new ArrayList<String>();
		var matcher = SPLIT_PIECES_TEMPLATE_PATTERN.matcher(template);
		while(matcher.find())
			result.add(matcher.group(0));
		return result.toArray(n -> new String[n]);
	}

	protected static final ThreadLocal<ByteArrayOutputStream> stringBitField_tempByteArrayOutputStreamLocal = ThreadLocal.withInitial(() -> new ByteArrayOutputStream());
	protected static final ThreadLocal<PrintStream> stringBitField_tempPrintStreamLocal = ThreadLocal.withInitial(() -> new PrintStream(stringBitField_tempByteArrayOutputStreamLocal.get()));
	public static String stringBitField(BitField[] bitFields, char[] chars) {
		var tempByteArrayOutputStream = stringBitField_tempByteArrayOutputStreamLocal.get();
		var tempPrintStream = stringBitField_tempPrintStreamLocal.get();
		outputBitField(bitFields, tempPrintStream, chars);
		tempPrintStream.flush();
		var result = tempByteArrayOutputStream.toString(StandardCharsets.UTF_8);
		tempByteArrayOutputStream.reset();
		return result;
	}
	public static String stringBitField(BitField bitField, char character) {
		var tempBitFields = outputBitField_tempBitFieldsLocal.get();
		var tempChars = outputBitField_tempCharsLocal.get();
		tempBitFields[0] = bitField;
		tempChars[0] = character;
		return stringBitField(tempBitFields, tempChars);
	}
	protected static final ThreadLocal<BitField[]> outputBitField_tempBitFieldsLocal = ThreadLocal.withInitial(() -> new BitField[1]);
	protected static final ThreadLocal<char[]> outputBitField_tempCharsLocal = ThreadLocal.withInitial(() -> new char[1]);
	public static void outputBitField(BitField bitField, PrintStream stream, char character) {
		var tempBitFields = outputBitField_tempBitFieldsLocal.get();
		var tempChars = outputBitField_tempCharsLocal.get();
		tempBitFields[0] = bitField;
		tempChars[0] = character;
		outputBitField(tempBitFields, stream, tempChars);
	}
	public static void outputBitField(BitField[] bitFields, PrintStream stream, char[] chars) {
		try {
			int minX = Stream.of(bitFields).reduce(Integer.MAX_VALUE, (a, b) -> Math.min(a, b.getMinX()), (a, b) -> Math.min(a, b));
			int maxX = Stream.of(bitFields).reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b.getMaxX()), (a, b) -> Math.max(a, b));
			int minY = Stream.of(bitFields).reduce(Integer.MAX_VALUE, (a, b) -> Math.min(a, b.getMinY()), (a, b) -> Math.min(a, b));
			int maxY = Stream.of(bitFields).reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b.getMaxY()), (a, b) -> Math.max(a, b));
			int minZ = Stream.of(bitFields).reduce(Integer.MAX_VALUE, (a, b) -> Math.min(a, b.getMinZ()), (a, b) -> Math.min(a, b));
			int maxZ = Stream.of(bitFields).reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b.getMaxZ()), (a, b) -> Math.max(a, b));
			for(int z = minZ; z <= maxZ; z++) {
				stream.printf("Z: %d\n", z);
				for(int y = minY; y <= maxY; y++) {
					for(int x = minX; x <= maxX; x++) {
						char c = ' ';
						for(int i = 0; i < bitFields.length; i++) {
							var bitField = bitFields[i];
							if(!bitField.getValue(x, y, z)) continue;
							c = chars[i];
							break;
						}
						stream.append(c);
					}
					stream.println();
				}
			}
		} catch(Exception e) {
			throw new Error(e);
		}
	}

	protected static long findClosestValidPlacement(Solver solver, long targetPlacement) {
		var board = solver.getBoard();
		var compiledPieces = board.getCompiledPieces();
		solver.setCurrentPlacement(targetPlacement - 1);
		if(!board.isIntersecting())
			return solver.getBoardPlacement();
		var compiledPieceIndex = compiledPieces.length - 1;
		for(; compiledPieceIndex >= 0; compiledPieceIndex--) {
			board.removeCompiledPiece(compiledPieces[compiledPieceIndex]);
			if(board.isIntersecting()) continue;
			break;
		}
		var closestPlacement = solver.getMaxPlacement();
		var compiledPiece = compiledPieces[compiledPieceIndex];
		for(int j = 0; j < compiledPiece.getCompiledShapes().length; j++) {
			board.putCompiledPiece(compiledPiece, j);
			if(board.isIntersecting()) continue;
			var placement = solver.getBoardPlacement();
			if(Math.abs(targetPlacement - placement) >= Math.abs(targetPlacement - closestPlacement))
				continue;
			closestPlacement = placement;
		}
		return closestPlacement;
	}
	protected static long initialPlacement(Solver solver) {
		var board = solver.getBoard();
		var compiledPieces = board.getCompiledPieces();
		board.reset();
		for(var compiledPiece : compiledPieces)
			board.putCompiledPiece(compiledPiece, 0);
		return solver.getBoardPlacement();
	}
	public static Solver[] defaultSolverBrancher(Solver solver, int branch) {
		var splitEvery = Math.ceilDiv(solver.getMaxPlacement() - solver.getMinPlacement(), branch);
		var start = findClosestValidPlacement(solver, initialPlacement(solver));
		var result = new ArrayList<Solver>(branch);
		for(int i = 0; i < branch; i++) {
			var end = findClosestValidPlacement(solver, start + splitEvery);
			if(end == start) break;
			result.add(i, new Solver(solver.getBoard().clone(), start, end));
			start = end;
		}
		while(result.size() < branch) {
			result.sort((a, b) -> Long.compare(
				b.getMaxPlacement() - b.getMinPlacement(),
				a.getMaxPlacement() - a.getMinPlacement()));
			var splitSuccessful = false;
			for(int i = 0; i < result.size(); i++) {
				var solverToSplit = result.get(i);
				var minPlacement = solverToSplit.getMinPlacement();
				var maxPlacement = solverToSplit.getMaxPlacement();
				var midPoint = (minPlacement + maxPlacement) / 2;
				var splitPoint = findClosestValidPlacement(solver, midPoint);
				if(splitPoint <= minPlacement || splitPoint >= maxPlacement) continue;
				result.remove(i);
				result.add(new Solver(solver.getBoard().clone(), minPlacement, splitPoint));
				result.add(new Solver(solver.getBoard().clone(), splitPoint, maxPlacement));
				splitSuccessful = true;
				break;
			}
			if(!splitSuccessful)
				break;
		}
		result.sort((a, b) -> Long.compare(a.minPlacement, b.minPlacement));
		return result.toArray(n -> new Solver[n]);
	}
}
