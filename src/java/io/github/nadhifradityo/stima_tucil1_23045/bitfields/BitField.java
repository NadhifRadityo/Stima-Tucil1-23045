package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

// BitField shall only interacts with instances of same type.
// Interfacing with different implementations is technically allowed,
// but may lead to unoptimized result.
public interface BitField {
	public int getWidth();
	public int getHeight();
	public int getDepth();
	public boolean getValue(int x, int y, int z);
	public ImmutableBitField toImmutable();
	public MutableBitField toMutable();
	public BitField clone();

	public default int getMinX() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		var minX = Integer.MAX_VALUE;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					if(!this.getValue(x, y, z)) continue;
					minX = Math.min(minX, x);
				}
			}
		}
		return minX < width ? minX : 0;
	}
	public default int getMaxX() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		var maxX = Integer.MIN_VALUE;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					if(!this.getValue(x, y, z)) continue;
					maxX = Math.max(maxX, x);
				}
			}
		}
		return maxX >= 0 ? maxX : -1;
	}
	public default int getMinY() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		var minY = Integer.MAX_VALUE;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				for(int z = 0; z < depth; z++) {
					if(!this.getValue(x, y, z)) continue;
					minY = Math.min(minY, y);
				}
			}
		}
		return minY < height ? minY : 0;
	}
	public default int getMaxY() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		var maxY = Integer.MIN_VALUE;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				for(int z = 0; z < depth; z++) {
					if(!this.getValue(x, y, z)) continue;
					maxY = Math.max(maxY, y);
				}
			}
		}
		return maxY >= 0 ? maxY : -1;
	}
	public default int getMinZ() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		var minZ = Integer.MAX_VALUE;
		for(int z = 0; z < depth; z++) {
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					if(!this.getValue(x, y, z)) continue;
					minZ = Math.min(minZ, z);
				}
			}
		}
		return minZ < depth ? minZ : 0;
	}
	public default int getMaxZ() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		var maxZ = Integer.MIN_VALUE;
		for(int z = 0; z < depth; z++) {
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					if(!this.getValue(x, y, z)) continue;
					maxZ = Math.max(maxZ, z);
				}
			}
		}
		return maxZ >= 0 ? maxZ : -1;
	}

	public default boolean isIntersecting(BitField that) {
		var width = Math.min(this.getWidth(), that.getWidth());
		var height = Math.min(this.getHeight(), that.getHeight());
		var depth = Math.min(this.getDepth(), that.getDepth());
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					if(!that.getValue(x, y, z)) continue;
					if(!this.getValue(x, y, z)) continue;
					return true;
				}
			}
		}
		return false;
	}
}
