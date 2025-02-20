package io.github.nadhifradityo.stima_tucil1_23045;

// BitField shall only interacts with instances of same type.
// Interfacing with different implementations is technically allowed,
// but may lead to unoptimized result.
public interface BitField {
	int getWidth();
	int getHeight();
	int getDepth();
	boolean getValue(int x, int y, int z);
	void setValue(int x, int y, int z, boolean v);
	default void set(BitField that) {
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
	BitField clone();

	void offsetX(int amount);
	void offsetY(int amount);
	void offsetZ(int amount);
	default boolean isIntersecting(BitField that) {
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
	default void complement() {
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
	default void union(BitField that) {
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
	default void intersect(BitField that) {
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
	default void exclusive(BitField that) {
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
	default void subtract(BitField that) {
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
}
