package structures;

public class IntersectionInfo {
	public final double Distance;
	public final Vector3 IntersectionPoint;
	public final Vector3 Normal;

	public IntersectionInfo(double distance, Vector3 intersectionPoint,
			Vector3 normal) {
		Distance = distance;
		IntersectionPoint = intersectionPoint;
		Normal = normal;
	}

}
