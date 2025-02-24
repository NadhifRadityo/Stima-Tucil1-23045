package io.github.nadhifradityo.stima_tucil1_23045;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilsTest {
	@Test
	public void canGenerateRectangularBitField() {
		var gameContext =  new BitFieldFactory(4, 4, 4);
		var bitField = Utils.generateRectangularBitField(gameContext, 4, 4);
		var result = Utils.stringBitField(bitField, 'X').replace("\r\n", "\n");
		assertEquals("""
			Z: 0\n\
			XXXX\n\
			XXXX\n\
			XXXX\n\
			XXXX\n\
			""", result);
	}
	@Test
	public void canGeneratePyramidalBitField() {
		var gameContext =  new BitFieldFactory(4, 4, 4);
		var bitField = Utils.generatePyramidalBitField(gameContext, 4, 4);
		var result = Utils.stringBitField(bitField, 'X').replace("\r\n", "\n");
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
			""", result);
	}
}
