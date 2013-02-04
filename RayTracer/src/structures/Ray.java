package structures;

public class Ray {
	public final Vector3 origin;
	public final Vector3 direction;
	
	/**
	 * Constructs a new Ray based on a origin and direction
	 * @param from - the point where the ray starts
	 * @param direction - the direction of the ray
	 */
	public Ray(Vector3 from, Vector3 direction) {
		this.origin = from;
		this.direction = direction;
	}
}
