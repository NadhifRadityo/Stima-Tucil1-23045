package io.github.nadhifradityo.stima_tucil1_23045;

import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanternaExt {
	public static class AnsiLabel extends AbstractComponent<AnsiLabel> {
		private StyledText[] styledLines;
		private Integer labelWidth;
		private TerminalSize labelSize;
		private TextColor foregroundColor;
		private TextColor backgroundColor;
		private final EnumSet<SGR> additionalStyles;

		private static final Pattern ANSI_PATTERN = Pattern.compile("\u001B\\[([0-9;]*)m");

		public AnsiLabel(String text) {
			this.styledLines = null;
			this.labelSize = TerminalSize.ZERO;
			this.labelWidth = 0;
			this.foregroundColor = null;
			this.backgroundColor = null;
			this.additionalStyles = EnumSet.noneOf(SGR.class);
			setText(text);
		}

		public synchronized void setText(String text) {
			this.styledLines = parseAnsiText(text);
			this.labelSize = getBounds(styledLines, labelSize);
			invalidate();
		}

		public synchronized String getText() {
			StringBuilder bob = new StringBuilder();
			for(StyledText styledText : styledLines) {
				if(bob.length() > 0)
					bob.append("\n");
				bob.append(styledText.getText());
			}
			return bob.toString();
		}

		private StyledText[] parseAnsiText(String text) {
			List<StyledText> result = new ArrayList<>();
			TextColor fg = foregroundColor;
			TextColor bg = backgroundColor;
			EnumSet<SGR> styles = EnumSet.copyOf(additionalStyles);
			String[] lines = text.replace("\r", "").split("\n");
			for(String line : lines) {
				Matcher matcher = ANSI_PATTERN.matcher(line);
				List<StyledText.Segment> segments = new ArrayList<>();
				int lastEnd = 0;
				while(matcher.find()) {
					if(matcher.start() > lastEnd)
						segments.add(new StyledText.Segment(line.substring(lastEnd, matcher.start()), fg, bg, styles.toArray(n -> new SGR[n])));
					String[] codes = matcher.group(1).split(";");
					int i = 0;
					while (i < codes.length) {
						int value = Integer.parseInt(codes[i]);
						switch(value) {
							case 0:
								fg = foregroundColor;
								bg = backgroundColor;
								styles.clear();
								styles.addAll(additionalStyles);
								break;
							case 1:
								styles.add(SGR.BOLD);
								break;
							case 4:
								styles.add(SGR.UNDERLINE);
								break;
							case 7:
								styles.add(SGR.REVERSE);
								break;
							case 30, 31, 32, 33, 34, 35, 36, 37:
								fg = TextColor.ANSI.values()[value - 30];
								break;
							case 40, 41, 42, 43, 44, 45, 46, 47:
								bg = TextColor.ANSI.values()[value - 40];
								break;
							case 90, 91, 92, 93, 94, 95, 96, 97:
								fg = TextColor.ANSI.values()[value - 90 + 8];
								break;
							case 100, 101, 102, 103, 104, 105, 106, 107:
								bg = TextColor.ANSI.values()[value - 100 + 8];
								break;
							case 38:
								if(i + 1 < codes.length) {
									int type = Integer.parseInt(codes[++i]);
									if(type == 5 && i + 1 < codes.length) {
										fg = new TextColor.Indexed(Integer.parseInt(codes[++i]));
									} else if(type == 2 && i + 3 < codes.length) {
										fg = new TextColor.RGB(
											Integer.parseInt(codes[++i]),
											Integer.parseInt(codes[++i]),
											Integer.parseInt(codes[++i])
										);
									}
								}
								break;
							case 48:
								if(i + 1 < codes.length) {
									int type = Integer.parseInt(codes[++i]);
									if(type == 5 && i + 1 < codes.length) {
										bg = new TextColor.Indexed(Integer.parseInt(codes[++i]));
									} else if(type == 2 && i + 3 < codes.length) {
										bg = new TextColor.RGB(
											Integer.parseInt(codes[++i]),
											Integer.parseInt(codes[++i]),
											Integer.parseInt(codes[++i])
										);
									}
								}
								break;
						}
						i++;
					}
					lastEnd = matcher.end();
				}
				if(lastEnd < line.length())
					segments.add(new StyledText.Segment(line.substring(lastEnd), fg, bg, styles.toArray(n -> new SGR[n])));
				result.add(new StyledText(segments));
			}
			return result.toArray(n -> new StyledText[n]);
		}

		private TerminalSize getBounds(StyledText[] lines, TerminalSize currentBounds) {
			if(currentBounds == null)
				currentBounds = TerminalSize.ZERO;
			currentBounds = currentBounds.withRows(lines.length);
			if(labelWidth == null || labelWidth == 0) {
				int preferredWidth = 0;
				for(StyledText line : lines) {
					int lineWidth = TerminalTextUtils.getColumnWidth(line.getText());
					if(preferredWidth < lineWidth)
						preferredWidth = lineWidth;
				}
				currentBounds = currentBounds.withColumns(preferredWidth);
			} else {
				List<StyledText> wordWrapped = getWordWrappedText(labelWidth, lines);
				currentBounds = currentBounds.withColumns(labelWidth).withRows(wordWrapped.size());
			}
			return currentBounds;
		}

		public synchronized AnsiLabel setForegroundColor(TextColor foregroundColor) {
			this.foregroundColor = foregroundColor;
			return this;
		}
		public TextColor getForegroundColor() {
			return foregroundColor;
		}

		public synchronized AnsiLabel setBackgroundColor(TextColor backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}
		public TextColor getBackgroundColor() {
			return backgroundColor;
		}

		public synchronized AnsiLabel addStyle(SGR sgr) {
			additionalStyles.add(sgr);
			return this;
		}
		public synchronized AnsiLabel removeStyle(SGR sgr) {
			additionalStyles.remove(sgr);
			return this;
		}

		public synchronized AnsiLabel setLabelWidth(Integer labelWidth) {
			this.labelWidth = labelWidth;
			return this;
		}
		public Integer getLabelWidth() {
			return labelWidth;
		}

		@Override
		protected ComponentRenderer<AnsiLabel> createDefaultRenderer() {
			return new ComponentRenderer<AnsiLabel>() {
				@Override
				public TerminalSize getPreferredSize(AnsiLabel label) {
					return labelSize;
				}

				@Override
				public void drawComponent(TextGUIGraphics graphics, AnsiLabel component) {
					ThemeDefinition themeDefinition = component.getThemeDefinition();
					graphics.applyThemeStyle(themeDefinition.getNormal());
					if(foregroundColor != null)
						graphics.setForegroundColor(foregroundColor);
					if(backgroundColor != null)
						graphics.setBackgroundColor(backgroundColor);
					SGR[] lastModifiers = additionalStyles.toArray(n -> new SGR[n]);
					graphics.enableModifiers(lastModifiers);
					StyledText[] linesToDraw;
					if(component.getLabelWidth() == null)
						linesToDraw = component.styledLines;
					else
						linesToDraw = getWordWrappedText(graphics.getSize().getColumns(), component.styledLines).toArray(n -> new StyledText[n]);
					for(int row = 0; row < Math.min(graphics.getSize().getRows(), linesToDraw.length); row++) {
						StyledText line = linesToDraw[row];
						if(graphics.getSize().getColumns() < labelSize.getColumns()) {
							int availableColumns = graphics.getSize().getColumns();
							line = fitStyledText(line, 0, availableColumns);
						}
						int col = 0;
						for(StyledText.Segment segment : line.getSegments()) {
							if(segment.foreground != null)
								graphics.setForegroundColor(segment.foreground);
							else
								graphics.setForegroundColor(themeDefinition.getNormal().getForeground());
							if(segment.background != null)
								graphics.setBackgroundColor(segment.background);
							else
								graphics.setBackgroundColor(themeDefinition.getNormal().getBackground());
							graphics.disableModifiers(lastModifiers);
							graphics.enableModifiers(segment.styles);
							lastModifiers = segment.styles;
							graphics.putString(col, row, segment.text);
							col += segment.text.length();
						}
					}
				}
			};
		}

		public static List<StyledText> getWordWrappedText(int maxWidth, StyledText... styledLines) {
			if(maxWidth <= 0)
				return Arrays.asList(styledLines);
			List<StyledText> result = new ArrayList<>();
			LinkedList<StyledText> linesToBeWrapped = new LinkedList<>(Arrays.asList(styledLines));
			while(!linesToBeWrapped.isEmpty()) {
				StyledText styledText = linesToBeWrapped.removeFirst();
				List<StyledText.Segment> currentSegments = new ArrayList<>();
				StringBuilder currentLine = new StringBuilder();
				int currentWidth = 0;
				for(StyledText.Segment segment : styledText.getSegments()) {
					String segmentText = segment.text;
					int segmentWidth = TerminalTextUtils.getColumnWidth(segmentText);
					if (currentWidth + segmentWidth <= maxWidth) {
						currentSegments.add(segment);
						currentLine.append(segmentText);
						currentWidth += segmentWidth;
					} else {
						int remainingWidth = maxWidth - currentWidth;
						int breakIndex = TerminalTextUtils.getStringCharacterIndex(segmentText, remainingWidth);
						while(breakIndex > 0 &&
							!Character.isSpaceChar(segmentText.charAt(breakIndex)) &&
							!TerminalTextUtils.isCharCJK(segmentText.charAt(breakIndex)))
							breakIndex--;
						if(breakIndex <= 0)
							breakIndex = Math.max(remainingWidth, 1);
						String firstPart = segmentText.substring(0, breakIndex);
						String secondPart = segmentText.substring(breakIndex).stripLeading();
						currentSegments.add(new StyledText.Segment(firstPart, segment.foreground, segment.background, segment.styles));
						result.add(new StyledText(currentSegments));
						currentSegments = new ArrayList<>();
						currentLine = new StringBuilder();
						currentWidth = 0;
						if(!secondPart.isEmpty()) {
							linesToBeWrapped.addFirst(new StyledText(
								Collections.singletonList(new StyledText.Segment(secondPart, segment.foreground, segment.background, segment.styles))
							));
						}
					}
				}
				if(!currentSegments.isEmpty())
					result.add(new StyledText(currentSegments));
			}
			return result;
		}
		public static StyledText fitStyledText(StyledText styledText, int fromColumn, int availableColumnSpace) {
			if(availableColumnSpace <= 0)
				return new StyledText(Collections.emptyList());
			List<StyledText.Segment> fittedSegments = new ArrayList<>();
			int column = 0;
			boolean trimmed = false;
			for(StyledText.Segment segment : styledText.getSegments()) {
				StringBuilder segmentText = new StringBuilder();
				String rawText = segment.text;
				for (int i = 0; i < rawText.length(); i++) {
					char c = rawText.charAt(i);
					int charWidth = TerminalTextUtils.isCharCJK(c) ? 2 : 1;
					if(column < fromColumn) {
						column += charWidth;
						continue;
					}
					if(availableColumnSpace - charWidth < 0) {
						trimmed = true;
						break;
					}
					segmentText.append(c);
					availableColumnSpace -= charWidth;
				}
				if(segmentText.length() > 0)
					fittedSegments.add(new StyledText.Segment(segmentText.toString(), segment.foreground, segment.background, segment.styles));
				if(trimmed)
					break;
			}
			return new StyledText(fittedSegments);
		}		

		private static class StyledText {
			private final List<Segment> segments;

			public StyledText(List<Segment> segments) {
				this.segments = segments;
			}

			public List<Segment> getSegments() {
				return segments;
			}

			public String getText() {
				StringBuilder sb = new StringBuilder();
				for(Segment segment : segments)
					sb.append(segment.text);
				return sb.toString();
			}

			private static class Segment {
				final String text;
				final TextColor foreground;
				final TextColor background;
				final SGR[] styles;

				Segment(String text, TextColor foreground, TextColor background, SGR[] styles) {
					this.text = text;
					this.foreground = foreground;
					this.background = background;
					this.styles = styles;
				}
			}
		}
	}
}
