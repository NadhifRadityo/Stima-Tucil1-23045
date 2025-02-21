package io.github.nadhifradityo.stima_tucil1_23045.bitfields;

public interface ImmutableBitField extends BitField {
	public default ImmutableBitField withSetValue(int x, int y, int z, boolean v) {
		var result = this.toMutable();
		result.setValue(x, y, z, v);
		return result.toImmutable();
	}
	public default ImmutableBitField withClear() {
		var result = this.toMutable();
		result.clear();
		return result.toImmutable();
	}
	public abstract ImmutableBitField clone();

	public default ImmutableBitField withOffsetX(int amount) {
		var result = this.toMutable();
		result.offsetX(amount);
		return result.toImmutable();
	}
	public default ImmutableBitField withOffsetY(int amount) {
		var result = this.toMutable();
		result.offsetY(amount);
		return result.toImmutable();
	}
	public default ImmutableBitField withOffsetZ(int amount) {
		var result = this.toMutable();
		result.offsetZ(amount);
		return result.toImmutable();
	}

	public default ImmutableBitField withTrimX() {
		var result = this.toMutable();
		result.trimX();
		return result.toImmutable();
	}
	public default ImmutableBitField withTrimY() {
		var result = this.toMutable();
		result.trimY();
		return result.toImmutable();
	}
	public default ImmutableBitField withTrimZ() {
		var result = this.toMutable();
		result.trimZ();
		return result.toImmutable();
	}
	public default ImmutableBitField withTrim() {
		var result = this.toMutable();
		result.trim();
		return result.toImmutable();
	}

	public default ImmutableBitField withComplement() {
		var result = this.toMutable();
		result.complement();
		return result.toImmutable();
	}
	public default ImmutableBitField withUnion(BitField that) {
		var result = this.toMutable();
		result.union(that);
		return result.toImmutable();
	}
	public default ImmutableBitField withIntersect(BitField that) {
		var result = this.toMutable();
		result.intersect(that);
		return result.toImmutable();
	}
	public default ImmutableBitField withExclusive(BitField that) {
		var result = this.toMutable();
		result.exclusive(that);
		return result.toImmutable();
	}
	public default ImmutableBitField withSubtract(BitField that) {
		var result = this.toMutable();
		result.subtract(that);
		return result.toImmutable();
	}

	public default ImmutableBitField withFlipX() {
		var result = this.toMutable();
		result.flipX();
		return result.toImmutable();
	}
	public default ImmutableBitField withFlipY() {
		var result = this.toMutable();
		result.flipY();
		return result.toImmutable();
	}
	public default ImmutableBitField withFlipZ() {
		var result = this.toMutable();
		result.flipZ();
		return result.toImmutable();
	}

	public default ImmutableBitField withRotateX90() {
		var result = this.toMutable();
		result.toRotateX90();
		return result.toImmutable();
	}
	public default ImmutableBitField withRotateX180() {
		var result = this.toMutable();
		result.toRotateX180();
		return result.toImmutable();
	}
	public default ImmutableBitField withRotateX270() {
		var result = this.toMutable();
		result.toRotateX270();
		return result.toImmutable();
	}
	public default ImmutableBitField withRotateY90() {
		var result = this.toMutable();
		result.toRotateY90();
		return result.toImmutable();
	}
	public default ImmutableBitField withRotateY180() {
		var result = this.toMutable();
		result.toRotateY180();
		return result.toImmutable();
	}
	public default ImmutableBitField withRotateY270() {
		var result = this.toMutable();
		result.toRotateY270();
		return result.toImmutable();
	}
	public default ImmutableBitField withRotateZ90() {
		var result = this.toMutable();
		result.toRotateZ90();
		return result.toImmutable();
	}
	public default ImmutableBitField withRotateZ180() {
		var result = this.toMutable();
		result.toRotateZ180();
		return result.toImmutable();
	}
	public default ImmutableBitField withRotateZ270() {
		var result = this.toMutable();
		result.toRotateZ270();
		return result.toImmutable();
	}
}
