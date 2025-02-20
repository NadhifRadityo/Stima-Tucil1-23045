package io.github.nadhifradityo.stima_tucil1_23045;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BitFieldTest {
	@Test
	public void canSetGetValue() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 3, 3, true);
		assertTrue(bitField.getValue(3, 3, 3));
	}

	@Test
	public void canClear() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 3, 3, true);
		bitField.clear();
		assertFalse(bitField.getValue(3, 3, 3));
	}

	@Test
	public void canOffsetX() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(1, 1, 1, true);
		bitField.offsetX(2);
		assertFalse(bitField.getValue(1, 1, 1));
		assertTrue(bitField.getValue(3, 1, 1));
	}

	@Test
	public void canOffsetY() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(1, 1, 1, true);
		bitField.offsetY(2);
		assertFalse(bitField.getValue(1, 1, 1));
		assertTrue(bitField.getValue(1, 3, 1));
	}

	@Test
	public void canOffsetZ() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(1, 1, 1, true);
		bitField.offsetZ(2);
		assertFalse(bitField.getValue(1, 1, 1));
		assertTrue(bitField.getValue(1, 1, 3));
	}

	@Test
	public void canMinMaxX() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(2, 3, 4, true);
		bitField.setValue(6, 3, 4, true);
		assertEquals(2, bitField.getMinX());
		assertEquals(6, bitField.getMaxX());
	}

	@Test
	public void canMinMaxY() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 2, 4, true);
		bitField.setValue(3, 6, 4, true);
		assertEquals(2, bitField.getMinY());
		assertEquals(6, bitField.getMaxY());
	}

	@Test
	public void canMinMaxZ() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 4, 2, true);
		bitField.setValue(3, 4, 6, true);
		assertEquals(2, bitField.getMinZ());
		assertEquals(6, bitField.getMaxZ());
	}

	@Test
	public void canTrimX() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 3, 3, true);
		bitField.trimX();
		assertEquals(0, bitField.getMinX());
	}

	@Test
	public void canTrimY() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 3, 3, true);
		bitField.trimY();
		assertEquals(0, bitField.getMinY());
	}

	@Test
	public void canTrimZ() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 3, 3, true);
		bitField.trimZ();
		assertEquals(0, bitField.getMinZ());
	}

	@Test
	public void canFlipX() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(2, 3, 4, true);
		bitField.flipX();
		assertFalse(bitField.getValue(2, 3, 4));
		assertTrue(bitField.getValue(5, 3, 4));
	}

	@Test
	public void canFlipY() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 2, 4, true);
		bitField.flipY();
		assertFalse(bitField.getValue(3, 2, 4));
		assertTrue(bitField.getValue(3, 5, 4));
	}

	@Test
	public void canFlipZ() {
		BitField8x8x8 bitField = new BitField8x8x8();
		bitField.setValue(3, 4, 2, true);
		bitField.flipZ();
		assertFalse(bitField.getValue(3, 4, 2));
		assertTrue(bitField.getValue(3, 4, 5));
	}
}
