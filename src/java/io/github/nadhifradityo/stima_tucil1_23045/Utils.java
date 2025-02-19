package io.github.nadhifradityo.stima_tucil1_23045;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Utils {
	private Utils() {

	}

	public static BitField generateRectangularBitField(int width, int height) {
		var result = new BitField();
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++)
				result.set(i, j, 0, true);
		}
		return result;
	}
	public static BitField generatePyramidalBitField(int width, int height) {
		var result = new BitField();
		var depth = Math.min(width, height);
		for(int i = 0; i < depth; i++) {
			for(int j = 0; j < width - i; j++) {
				for(int k = 0; k < height - i; k++)
					result.set(j, k, i, true);
			}
		}
		return result;
	}
	public static BitField parseBitFieldTemplate(String template) {
		var result = new BitField();
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
			result.set(i, y, 0, true);
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
			System.err.println("Some pieces template may be malformed: \n" + template);
		return result.toArray(new String[result.size()]);
	}
}
