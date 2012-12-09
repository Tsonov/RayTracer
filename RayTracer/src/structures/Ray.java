package structures;

public class Ray {
	public final Vector3 origin;
	public final Vector3 direction;
	private double minScalar;
	private double maxScalar;
	
	public Ray(Vector3 from, Vector3 direction) {
		this.origin = from;
		this.direction = direction;
	}
}
