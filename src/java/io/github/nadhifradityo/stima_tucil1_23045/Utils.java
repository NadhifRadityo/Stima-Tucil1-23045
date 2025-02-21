package io.github.nadhifradityo.stima_tucil1_23045;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Utils {
	private Utils() {

	}

	public static BitField generateRectangularBitField(GameContext factory, int width, int height) {
		var result = factory.newBitField();
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++)
				result.setValue(i, j, 0, true);
		}
		return result;
	}
	public static BitField generatePyramidalBitField(GameContext factory, int width, int height) {
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
	public static BitField parseBitFieldTemplate(GameContext factory, String template) {
		var result = factory.newBitField();
		int y = 0;
		for(int i = 0; i < template.length(); i++) {
			var character = template.charAt(i);
			if(character == '\r' || character == '\n') {
				char nextCharacter = i + 1 < template.length() ? template.charAt(i + 1) : '\0';
				if(nextCharacter == '\n' || nextCharacter == '\r')
					i++;
				y++;
				continue;
			}
			if(Character.isWhitespace(character))
				continue;
			result.setValue(i, y, 0, true);
		}
		return result;
	}
	protected static final Pattern SPLIT_PIECES_TEMPLATE_PATTERN = Pattern.compile("^\\s*([a-zA-Z0-9])\\1*(?:\\n\\1*)*$", Pattern.MULTILINE);
	public static String[] splitPiecesTemplate(String template) {
		var result = new ArrayList<String>();
		var matcher = SPLIT_PIECES_TEMPLATE_PATTERN.matcher(template);
		while(matcher.find())
			result.add(matcher.group(0));
		if(result.stream().reduce(0, (a, b) -> a + b.length(), (a, b) -> a + b) != template.length())
			System.err.println("Pieces template may be malformed: \n" + template);
		return result.toArray(new String[result.size()]);
	}

	public static void printBitField(BitField bitField) {
		outputBitField(new BitField[] { bitField }, System.out, new char[] { 'X' });
	}
	public static void outputBitField(BitField[] bitFields, OutputStream stream, char[] chars) {
		try {
			StringBuilder sb = new StringBuilder();
			int minX = Stream.of(bitFields).reduce(Integer.MAX_VALUE, (a, b) -> Math.min(a, b.getMinX()), (a, b) -> Math.min(a, b));
			int maxX = Stream.of(bitFields).reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b.getMaxX()), (a, b) -> Math.max(a, b));
			int minY = Stream.of(bitFields).reduce(Integer.MAX_VALUE, (a, b) -> Math.min(a, b.getMinY()), (a, b) -> Math.min(a, b));
			int maxY = Stream.of(bitFields).reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b.getMaxY()), (a, b) -> Math.max(a, b));
			int minZ = Stream.of(bitFields).reduce(Integer.MAX_VALUE, (a, b) -> Math.min(a, b.getMinZ()), (a, b) -> Math.min(a, b));
			int maxZ = Stream.of(bitFields).reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b.getMaxZ()), (a, b) -> Math.max(a, b));
			for(int z = minZ; z <= maxZ; z++) {
				stream.write(("Z: " + z + "\n").getBytes(StandardCharsets.UTF_8));
				for(int y = minY; y <= maxY; y++) {
					for(int x = minX; x <= maxX; x++) {
						char c = ' ';
						for(int i = 0; i < bitFields.length; i++) {
							var bitField = bitFields[i];
							if(!bitField.getValue(x, y, z)) continue;
							c = chars[i];
							break;
						}
						sb.append(c);
					}
					sb.append('\n');
				}
				stream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
				sb.setLength(0);
			}
		} catch(Exception e) {
			throw new Error(e);
		}
	}
}
