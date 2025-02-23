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

	public static Solver[] defaultSolverBrancher(Solver solver, int branch) {
		long splitEvery = Math.ceilDiv(solver.getMaxPlacement() - solver.getMinPlacement(), branch);
		var result = new Solver[branch];
		for(int i = 0; i < branch; i++) {
			var start = i * splitEvery;
			var end = Math.min(solver.getMaxPlacement(), (i + 1) * splitEvery);
			result[i] = new Solver(solver.getBoard().clone(), start, end);
		}
		return result;
	}
}
