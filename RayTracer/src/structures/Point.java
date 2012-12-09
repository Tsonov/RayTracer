package structures;

/**
 * Point in the 3d space
 * @author Tsonov
 *
 */
public class Point {
	private static final Point ZERO_POINT = new Point(0.0, 0.0, 0.0); //Store the Zero point for checks
	private double x;
	private double y;
	private double z;
	
	public Point() {
		this(0.0f, 0.0f, 0.0f);
	}
	
	public Point(double x, double y, double z) {
		setX(x);
		setY(y);
		setZ(z);
	}
	
//	public Point add(Point other) {
//		double newX = this.getX() + other.getX();
//		double newY = this.getY() + other.getY();
//		double newZ = this.getZ() + other.getZ();
//		return new Point(newX, newY, newZ);
//	}
	
//	public Point subtract(Point other) {
//		Point result = this.add
//	}
	
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
