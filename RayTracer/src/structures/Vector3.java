package structures;

public class Vector3 {
	public final int X_AXIS = 0;
	public final int Y_AXIS = 1;
	public final int Z_AXIS = 2;
	private static final Vector3 ZERO_VEC = new Vector3(0.0, 0.0, 0.0);
	private double x;
	private double y;
	private double z;

	/**
	 * Creates a new Vector with default values
	 */
	public Vector3() {
		this(0.0f, 0.0f, 0.0f);
	}

	/**
	 * Creates a new Vector with given coordinates
	 * 
	 * @param x
	 *            coordinate of the vector
	 * @param y
	 *            coordinate of the vector
	 * @param z
	 *            coordinate of the vector
	 */
	public Vector3(double x, double y, double z) {
		setX(x);
		setY(y);
		setZ(z);
	}

	/**
	 * Creates a new Vector from an existing one
	 * 
	 * @param another
	 *            - the existing Vector
	 */
	public Vector3(Vector3 another) {
		this(another.getX(), another.getY(), another.getZ());
	}

	/**
	 * Converts an RGB color into a Vector
	 * 
	 * @param color
	 *            - the color with RGB specified in the range [0, 255]
	 */
	public Vector3(Color color) {
		this(((double) color.getRed()) / 0xFF,
				((double) color.getGreen()) / 0xFF,
				((double) color.getBlue()) / 0xFF);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Vector3))
			return false;
		Vector3 otherVec = (Vector3) other;
		boolean result = ((Double) this.x).equals(otherVec.x)
				&& ((Double) this.y).equals(otherVec.y)
				&& ((Double) this.z).equals(otherVec.z);
		return result;
	}

	/**
	 * Adds this vector to another vector
	 * 
	 * @param other
	 *            - the second vector
	 * @return The result of the addition
	 */
	public Vector3 add(Vector3 other) {
		Vector3 result = new Vector3();
		result.setX(this.x + other.x);
		result.setY(this.y + other.y);
		result.setZ(this.z + other.z);
		return result;
	}

	/**
	 * Subtracts another vector from this vector
	 * 
	 * @param other
	 *            - the second vector
	 * @return The result of the subtraction
	 */
	public Vector3 subtract(Vector3 other) {
		Vector3 result = new Vector3();
		result.setX(this.x - other.x);
		result.setY(this.y - other.y);
		result.setZ(this.z - other.z);
		return result;
	}

	/**
	 * Calculates the cross product between this vector and another vector
	 * 
	 * @param other
	 *            - the second vector
	 * @return The cross product vector
	 */
	public Vector3 crossProduct(Vector3 other) {
		Vector3 result = new Vector3();
		result.setX(this.y * other.z - this.z * other.y);
		result.setY(this.z * other.x - this.x * other.z);
		result.setZ(this.x * other.y - this.y * other.x);
		return result;
	}

	/**
	 * Calculates the dot product between this vector and another vector
	 * 
	 * @param other
	 *            - the second vector
	 * @return The dot product value
	 */
	public double dotProduct(Vector3 other) {
		double result = this.x * other.x + this.y * other.y + this.z * other.z;
		return result;
	}

	/**
	 * Multiplies this vector by a scalar coordinate by coordinate
	 * 
	 * @param scalar
	 *            - the multiplier
	 * @return The resulting multiplied vector
	 */
	public Vector3 multiply(double scalar) {
		Vector3 result = new Vector3();
		result.setX(x * scalar);
		result.setY(y * scalar);
		result.setZ(z * scalar);
		return result;
	}

	/**
	 * Multiply this vector by another vector coordinate by coordinate
	 * 
	 * @param another
	 *            - the second vector
	 * @return The resulting multiplied vector
	 */
	public Vector3 multiplyByVector(Vector3 another) {
		Vector3 result = new Vector3();
		result.setX(x * another.x);
		result.setY(y * another.y);
		result.setZ(z * another.z);
		return result;
	}

	/**
	 * Normalizes this vector so that its length is 1
	 */
	public void normalize() {
		if (this.equals(ZERO_VEC))
			return; // zero vector is already normalized, avoid division by zero
		double length = getLength();
		this.setX(x / length);
		this.setY(y / length);
		this.setZ(z / length);
	}

	/**
	 * Checks whether this vector is the Zero vector
	 * 
	 * @return true if the vector is the Zero vector, false otherwise
	 */
	public boolean isZero() {
		return this.equals(ZERO_VEC);
	}

	/**
	 * Gets a coordinate based on the axis
	 * 
	 * @param axis
	 *            - x, y or z axis
	 * @return The x, y or z coordinate of this vector
	 */
	public double getCoordinate(int axis) {
		switch (axis) {
		case X_AXIS:
			return this.getX();
		case Y_AXIS:
			return this.getY();
		case Z_AXIS:
			return this.getZ();
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Sets the coordinate based on the axis
	 * 
	 * @param axis
	 *            - the x, y or z axis
	 * @param value
	 *            - the value to give to the coordinate
	 */
	public void setCoordinate(int axis, double value) {
		switch (axis) {
		case X_AXIS:
			this.setX(value);
			break;
		case Y_AXIS:
			this.setY(value);
			break;
		case Z_AXIS:
			this.setZ(value);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Calculates the standard length of this vector
	 * 
	 * @return The length of the vector
	 */
	public double getLength() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * 
	 * @return The X value of this vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the X value of this vector
	 * 
	 * @param x
	 *            - the new value
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * @return The Y value of this vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the Y value of this vector
	 * 
	 * @param y
	 *            - the new value
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * 
	 * @return The Z value of this vector
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Sets the Z value of this vector
	 * 
	 * @param z
	 *            - the new value
	 */
	public void setZ(double z) {
		this.z = z;
	}
}
