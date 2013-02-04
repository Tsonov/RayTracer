package structures;

public class IntersectionInfo {
	public final double Distance;
	public final Vector3 IntersectionPoint;
	public final Vector3 Normal;

	/**
	 * Create a new object that holds information about a ray intersection
	 * @param distance - the distance from the ray's origin
	 * @param intersectionPoint - the point of intersection
	 * @param normal - the normal at the point of intersection
	 */
	public IntersectionInfo(double distance, Vector3 intersectionPoint,
			Vector3 normal) {
		Distance = distance;
		IntersectionPoint = intersectionPoint;
		Normal = normal;
	}

}
