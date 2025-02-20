package io.github.nadhifradityo.stima_tucil1_23045;

// BitField shall only interacts with instances of same type.
// Interfacing with different implementations is technically allowed,
// but may lead to unoptimized result.
public abstract class BitField {
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getDepth();
	public abstract boolean getValue(int x, int y, int z);
	public abstract void setValue(int x, int y, int z, boolean v);
	public void set(BitField that) {
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
	public void clear() {
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
	public abstract BitField clone();

	public void offsetX(int amount) {
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
	public void offsetY(int amount) {
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
	public void offsetZ(int amount) {
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
	public int getMinX() {
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
	public int getMaxX() {
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
	public int getMinY() {
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
	public int getMaxY() {
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
	public int getMinZ() {
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
	public int getMaxZ() {
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
	public void trimX() {
		var minX = this.getMinX();
		this.offsetX(-minX);
	}
	public void trimY() {
		var minY = this.getMinY();
		this.offsetY(-minY);
	}
	public void trimZ() {
		var minZ = this.getMinZ();
		this.offsetZ(-minZ);
	}
	public void trim() {
		this.trimX();
		this.trimY();
		this.trimZ();
	}

	public boolean isIntersecting(BitField that) {
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
	public void complement() {
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
	public void union(BitField that) {
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
	public void intersect(BitField that) {
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
	public void exclusive(BitField that) {
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
	public void subtract(BitField that) {
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

	public void flipX() {
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
	public void flipY() {
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
	public void flipZ() {
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
	public BitField toRotateX90() {
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
	public BitField toRotateX180() {
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
	public BitField toRotateX270() {
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
	public BitField toRotateY90() {
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
	public BitField toRotateY180() {
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
	public BitField toRotateY270() {
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
	public BitField toRotateZ90() {
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
	public BitField toRotateZ180() {
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
	public BitField toRotateZ270() {
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
