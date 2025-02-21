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
		for(int offset = -3; offset <= 3; offset++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(3, 3, 3, true);
			bitField.offsetX(offset);
			assertTrue(bitField.getValue(3 + offset, 3, 3));
			if(offset != 0)
				assertFalse(bitField.getValue(3, 3, 3));
		}
	}

	@Test
	public void canOffsetY() {
		for(int offset = -3; offset <= 3; offset++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(3, 3, 3, true);
			bitField.offsetY(offset);
			assertTrue(bitField.getValue(3, 3 + offset, 3));
			if(offset != 0)
				assertFalse(bitField.getValue(3, 3, 3));
		}
	}

	@Test
	public void canOffsetZ() {
		for(int offset = -3; offset <= 3; offset++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(3, 3, 3, true);
			bitField.offsetZ(offset);
			assertTrue(bitField.getValue(3, 3, 3 + offset));
			if(offset != 0)
				assertFalse(bitField.getValue(3, 3, 3));
		}
	}

	@Test
	public void canMinMaxX() {
		for(int x = 0; x < BitField8x8x8.WIDTH; x++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(x, 3, 3, true);
			assertEquals(x, bitField.getMinX());
			assertEquals(x, bitField.getMaxX());
		}
	}

	@Test
	public void canMinMaxY() {
		for(int y = 0; y < BitField8x8x8.HEIGHT; y++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(3, y, 3, true);
			assertEquals(y, bitField.getMinY());
			assertEquals(y, bitField.getMaxY());
		}
	}

	@Test
	public void canMinMaxZ() {
		for(int z = 0; z < BitField8x8x8.DEPTH; z++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(3, 3, z, true);
			assertEquals(z, bitField.getMinZ());
			assertEquals(z, bitField.getMaxZ());
		}
	}

	@Test
	public void canFlipX() {
		for(int x = 0; x < BitField8x8x8.WIDTH; x++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(x, 3, 3, true);
			bitField.flipX();
			assertTrue(bitField.getValue(BitField8x8x8.WIDTH - 1 - x, 3, 3));
			assertFalse(bitField.getValue(x, 3, 3));
		}
	}

	@Test
	public void canFlipY() {
		for(int y = 0; y < BitField8x8x8.HEIGHT; y++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(3, y, 3, true);
			bitField.flipY();
			assertTrue(bitField.getValue(3, BitField8x8x8.HEIGHT - 1 - y, 3));
			assertFalse(bitField.getValue(3, y, 3));
		}
	}

	@Test
	public void canFlipZ() {
		for(int z = 0; z < BitField8x8x8.DEPTH; z++) {
			BitField8x8x8 bitField = new BitField8x8x8();
			bitField.setValue(3, 3, z, true);
			bitField.flipZ();
			assertTrue(bitField.getValue(3, 3, BitField8x8x8.DEPTH - 1 - z));
			assertFalse(bitField.getValue(3, 3, z));
		}
	}
}
