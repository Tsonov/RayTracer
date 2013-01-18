package structures;

public class Vector3 {
	private static final Vector3 ZERO_VEC = new Vector3(0.0, 0.0, 0.0);
	private double x;
	private double y;
	private double z;

	public Vector3() {
		this(0.0f, 0.0f, 0.0f);
	}

	public Vector3(double x, double y, double z) {
		setX(x);
		setY(y);
		setZ(z);
	}

	public Vector3(Vector3 another) {
		this(another.getX(), another.getY(), another.getZ());
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

	public Vector3 add(Vector3 other) {
		Vector3 result = new Vector3();
		result.setX(this.x + other.x);
		result.setY(this.y + other.y);
		result.setZ(this.z + other.z);
		return result;
	}

	public Vector3 subtract(Vector3 other) {
		Vector3 result = new Vector3();
		result.setX(this.x - other.x);
		result.setY(this.y - other.y);
		result.setZ(this.z - other.z);
		return result;
	}

	public Vector3 crossProduct(Vector3 other) {
		Vector3 result = new Vector3();
		result.setX(this.y * other.z - this.z * other.y);
		result.setY(this.z * other.x - this.x * other.z);
		result.setZ(this.x * other.y - this.y * other.x);
		return result;
	}

	public double dotProduct(Vector3 other) {
		double result = this.x * other.x + this.y * other.y + this.z * other.z;
		return result;
	}

	public Vector3 multiply(double scalar) {
		Vector3 result = new Vector3();
		result.setX(x * scalar);
		result.setY(y * scalar);
		result.setZ(z * scalar);
		return result;
	}

	public Vector3 multiplyByVector(Vector3 another) {
		Vector3 result = new Vector3();
		result.setX(x * another.x);
		result.setY(y * another.y);
		result.setZ(z * another.z);
		return result;
	}

	public void normalize() {
		if (this.equals(ZERO_VEC))
			return; // zero vector is already normalized, avoid division by zero
		double length = getLength();
		this.setX(x / length);
		this.setY(y / length);
		this.setZ(z / length);
	}

	public boolean isZero() {
		return this.equals(ZERO_VEC);
	}
	
	public double getLength() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
}
