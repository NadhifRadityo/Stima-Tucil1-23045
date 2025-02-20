package io.github.nadhifradityo.stima_tucil1_23045;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class UtilsTest {
	@Test
	public void canGenerateRectangularBitField() {
		var gameContext =  new GameContext(4, 4, 4);
		var bitField = Utils.generateRectangularBitField(gameContext, 4, 4);
		var stream = new ByteArrayOutputStream();
		Utils.outputBitField(new BitField[] { bitField }, stream, new char[] { 'X' });
		assertEquals("""
			Z: 0\n\
			XXXX\n\
			XXXX\n\
			XXXX\n\
			XXXX\n\
			""", stream.toString(StandardCharsets.UTF_8));
	}
	@Test
	public void canGeneratePyramidalBitField() {
		var gameContext =  new GameContext(4, 4, 4);
		var bitField = Utils.generatePyramidalBitField(gameContext, 4, 4);
		var stream = new ByteArrayOutputStream();
		Utils.outputBitField(new BitField[] { bitField }, stream, new char[] { 'X' });
		assertEquals("""
			Z: 0\n\
			XXXX\n\
			XXXX\n\
			XXXX\n\
			XXXX\n\
			Z: 1\n\
			XXX \n\
			XXX \n\
			XXX \n\
			    \n\
			Z: 2\n\
			XX  \n\
			XX  \n\
			    \n\
			    \n\
			Z: 3\n\
			X   \n\
			    \n\
			    \n\
			    \n\
			""", stream.toString(StandardCharsets.UTF_8));
	}
}
