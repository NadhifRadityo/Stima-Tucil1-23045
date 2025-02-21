package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

// BitField shall only interacts with instances of same type.
// Interfacing with different implementations is technically allowed,
// but may lead to unoptimized result.
public interface MutableBitField extends BitField {
	public void setValue(int x, int y, int z, boolean v);
	public default MutableBitField toSetValue(int x, int y, int z, boolean v) {
		var result = this.clone();
		result.setValue(x, y, z, v);
		return result;
	}
	public default void set(BitField that) {
		var width = Math.min(this.getWidth(), that.getWidth());
		var height = Math.min(this.getHeight(), that.getHeight());
		var depth = Math.min(this.getDepth(), that.getDepth());
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					if(!that.getValue(x, y, z)) continue;
					this.setValue(x, y, z, true);
				}
			}
		}
	}
	public default void clear() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					this.setValue(x, y, z, false);
				}
			}
		}
	}
	public default MutableBitField toClear() {
		var result = this.clone();
		result.clear();
		return result;
	}
	public abstract MutableBitField clone();

	public default void offsetX(int amount) {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		if(amount >= 0) {
			for(int x = width - 1; x >= 0; x--) {
				for(int y = 0; y < height; y++) {
					for(int z = 0; z < depth; z++) {
						int newX = x + amount;
						if(newX >= 0 && newX < width)
							this.setValue(newX, y, z, this.getValue(x, y, z));
						this.setValue(x, y, z, false);
					}
				}
			}
		} else {
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					for(int z = 0; z < depth; z++) {
						int newX = x + amount;
						if(newX >= 0 && newX < width)
							this.setValue(newX, y, z, this.getValue(x, y, z));
						this.setValue(x, y, z, false);
					}
				}
			}
		}
	}
	public default void offsetY(int amount) {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		if(amount >= 0) {
			for(int y = height - 1; y >= 0; y--) {
				for(int x = 0; x < width; x++) {
					for(int z = 0; z < depth; z++) {
						int newY = y + amount;
						if(newY >= 0 && newY < height)
							this.setValue(x, newY, z, this.getValue(x, y, z));
						this.setValue(x, y, z, false);
					}
				}
			}
		} else {
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					for(int z = 0; z < depth; z++) {
						int newY = y + amount;
						if(newY >= 0 && newY < height)
							this.setValue(x, newY, z, this.getValue(x, y, z));
						this.setValue(x, y, z, false);
					}
				}
			}
		}
	}
	public default void offsetZ(int amount) {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		if(amount >= 0) {
			for(int z = depth - 1; z >= 0; z--) {
				for(int x = 0; x < width; x++) {
					for(int y = 0; y < height; y++) {
						int newZ = z + amount;
						if(newZ >= 0 && newZ < depth)
							this.setValue(x, y, newZ, this.getValue(x, y, z));
						this.setValue(x, y, z, false);
					}
				}
			}
		} else {
			for(int z = 0; z < depth; z++) {
				for(int x = 0; x < width; x++) {
					for(int y = 0; y < height; y++) {
						int newZ = z + amount;
						if(newZ >= 0 && newZ < depth)
							this.setValue(x, y, newZ, this.getValue(x, y, z));
						this.setValue(x, y, z, false);
					}
				}
			}
		}
	}
	public default MutableBitField toOffsetX(int amount) {
		var result = this.clone();
		result.offsetX(amount);
		return result;
	}
	public default MutableBitField toOffsetY(int amount) {
		var result = this.clone();
		result.offsetY(amount);
		return result;
	}
	public default MutableBitField toOffsetZ(int amount) {
		var result = this.clone();
		result.offsetZ(amount);
		return result;
	}

	public default void trimX() {
		var minX = this.getMinX();
		this.offsetX(-minX);
	}
	public default void trimY() {
		var minY = this.getMinY();
		this.offsetY(-minY);
	}
	public default void trimZ() {
		var minZ = this.getMinZ();
		this.offsetZ(-minZ);
	}
	public default void trim() {
		this.trimX();
		this.trimY();
		this.trimZ();
	}
	public default MutableBitField toTrimX() {
		var result = this.clone();
		result.trimX();
		return result;
	}
	public default MutableBitField toTrimY() {
		var result = this.clone();
		result.trimY();
		return result;
	}
	public default MutableBitField toTrimZ() {
		var result = this.clone();
		result.trimZ();
		return result;
	}
	public default MutableBitField toTrim() {
		var result = this.clone();
		result.trim();
		return result;
	}

	public default void complement() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++)
					this.setValue(x, y, z, !this.getValue(x, y, z));
			}
		}
	}
	public default void union(BitField that) {
		var width = Math.min(this.getWidth(), that.getWidth());
		var height = Math.min(this.getHeight(), that.getHeight());
		var depth = Math.min(this.getDepth(), that.getDepth());
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					if(!that.getValue(x, y, z)) continue;
					this.setValue(x, y, z, true);
				}
			}
		}
	}
	public default void intersect(BitField that) {
		var width = Math.min(this.getWidth(), that.getWidth());
		var height = Math.min(this.getHeight(), that.getHeight());
		var depth = Math.min(this.getDepth(), that.getDepth());
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					if(that.getValue(x, y, z)) continue;
					this.setValue(x, y, z, false);
				}
			}
		}
	}
	public default void exclusive(BitField that) {
		var width = Math.min(this.getWidth(), that.getWidth());
		var height = Math.min(this.getHeight(), that.getHeight());
		var depth = Math.min(this.getDepth(), that.getDepth());
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var v = this.getValue(x, y, z) ^ that.getValue(x, y, z);
					this.setValue(x, y, z, v);
				}
			}
		}
	}
	public default void subtract(BitField that) {
		var width = Math.min(this.getWidth(), that.getWidth());
		var height = Math.min(this.getHeight(), that.getHeight());
		var depth = Math.min(this.getDepth(), that.getDepth());
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					if(!that.getValue(x, y, z)) continue;
					this.setValue(x, y, z, false);
				}
			}
		}
	}
	public default MutableBitField toComplement() {
		var result = this.clone();
		result.complement();
		return result;
	}
	public default MutableBitField toUnion(BitField that) {
		var result = this.clone();
		result.union(that);
		return result;
	}
	public default MutableBitField toIntersect(BitField that) {
		var result = this.clone();
		result.intersect(that);
		return result;
	}
	public default MutableBitField toExclusive(BitField that) {
		var result = this.clone();
		result.exclusive(that);
		return result;
	}
	public default MutableBitField toSubtract(BitField that) {
		var result = this.clone();
		result.subtract(that);
		return result;
	}

	public default void flipX() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width / 2; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					boolean temp = this.getValue(x, y, z);
					this.setValue(x, y, z, this.getValue(width - 1 - x, y, z));
					this.setValue(width - 1 - x, y, z, temp);
				}
			}
		}
	}
	public default void flipY() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int y = 0; y < height / 2; y++) {
			for(int x = 0; x < width; x++) {
				for(int z = 0; z < depth; z++) {
					boolean temp = this.getValue(x, y, z);
					this.setValue(x, y, z, this.getValue(x, height - 1 - y, z));
					this.setValue(x, height - 1 - y, z, temp);
				}
			}
		}
	}
	public default void flipZ() {
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int z = 0; z < depth / 2; z++) {
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					boolean temp = this.getValue(x, y, z);
					this.setValue(x, y, z, this.getValue(x, y, depth - 1 - z));
					this.setValue(x, y, depth - 1 - z, temp);
				}
			}
		}
	}
	public default MutableBitField toFlipX() {
		var result = this.clone();
		result.flipX();
		return result;
	}
	public default MutableBitField toFlipY() {
		var result = this.clone();
		result.flipY();
		return result;
	}
	public default MutableBitField toFlipZ() {
		var result = this.clone();
		result.flipZ();
		return result;
	}

	public default MutableBitField toRotateX90() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = x;
					var ty = z;
					var tz = height - 1 - y;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
	public default MutableBitField toRotateX180() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = x;
					var ty = height - 1 - y;
					var tz = depth - 1 - z;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
	public default MutableBitField toRotateX270() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = x;
					var ty = depth - 1 - z;
					var tz = y;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
	public default MutableBitField toRotateY90() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = depth - 1 - z;
					var ty = y;
					var tz = x;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
	public default MutableBitField toRotateY180() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = width - 1 - x;
					var ty = y;
					var tz = depth - 1 - z;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
	public default MutableBitField toRotateY270() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = z;
					var ty = y;
					var tz = width - 1 - x;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
	public default MutableBitField toRotateZ90() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = height - 1 - y;
					var ty = x;
					var tz = z;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
	public default MutableBitField toRotateZ180() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = width - 1 - x;
					var ty = height - 1 - y;
					var tz = z;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
	public default MutableBitField toRotateZ270() {
		var rotated = this.clone();
		rotated.clear();
		var width = this.getWidth();
		var height = this.getHeight();
		var depth = this.getDepth();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < depth; z++) {
					var tx = y;
					var ty = width - 1 - x;
					var tz = z;
					if(tx < 0 || tx >= width) continue;
					if(ty < 0 || ty >= height) continue;
					if(tz < 0 || tz >= width) continue;
					boolean v = this.getValue(x, y, z);
					if(!v) continue;
					rotated.setValue(tx, ty, tz, v);
				}
			}
		}
		return rotated;
	}
}
