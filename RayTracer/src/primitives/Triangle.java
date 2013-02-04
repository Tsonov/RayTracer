package primitives;

import structures.IntersectionInfo;
import structures.Ray;
import Jama.Matrix;
import structures.Vector3;

public class Triangle extends Primitive {
	private Vector3[] vertices = new Vector3[3];
	private Vector3 normal;

	/**
	 * Create a triangle from three vertices in clock-wise direction.
	 * 
	 * @param first
	 *            - the first vertex
	 * @param second
	 *            - the second vertex
	 * @param third
	 *            - the third vertex
	 * @param transformation
	 *            - the transformation for this triangle
	 */
	public Triangle(Vector3 first, Vector3 second, Vector3 third,
			Matrix transformation) {
		this(new Vector3[] { first, second, third }, transformation);
	}

	/**
	 * Create a triangle from three vertices in clock-wise direction.
	 * 
	 * @param vertices
	 *            - the array with the vertices
	 * @param transformation
	 *            - the transformation for this triangle
	 */
	public Triangle(Vector3[] vertices, Matrix transformation) {
		super(transformation);
		if (vertices.length != 3) {
			throw new IllegalArgumentException(
					"A triangle needs exactly three vertices to be defined!");
		}
		// this.vertices = vertices;
		for (int i = 0; i < vertices.length; i++) {
			this.vertices[i] = transformVector(vertices[i], 1,
					this.transformationToWorldCoordiantes);
		}
		computeNormal();
	}

	@Override
	public IntersectionInfo intersect(Ray theRay) {
		return intersectTrans(theRay);
	}

	private IntersectionInfo intersectTrans(Ray theRay) {
		// Get the intersection point with the triangle's plane
		double distance = -theRay.origin.subtract(vertices[0]).dotProduct(
				normal)
				/ theRay.direction.dotProduct(normal);

		// If distance is negative, then the ray hits the triangle from behind
		if (Double.compare(distance, 0) < 0) {
			return new IntersectionInfo(Double.POSITIVE_INFINITY, null, null);
		}
		Vector3 intersectionPoint = theRay.origin.add(theRay.direction
				.multiply(distance));
		// A counter-clockwise based check to see whether the intersection
		// point is within the triangle
		boolean isInside = isToTheLeftFromLine(1, 0, intersectionPoint)
				&& isToTheLeftFromLine(2, 1, intersectionPoint)
				&& isToTheLeftFromLine(0, 2, intersectionPoint);
		if (isInside) {
			return new IntersectionInfo(distance, intersectionPoint,
					new Vector3(this.normal));
		} else {
			return new IntersectionInfo(Double.POSITIVE_INFINITY, null, null);
		}
	}

	private boolean isToTheLeftFromLine(int firstVertexIndex,
			int secondVertexIndex, Vector3 intersection) {
		Vector3 vectorParallelNormal = vertices[firstVertexIndex].subtract(
				vertices[secondVertexIndex]).crossProduct(
				intersection.subtract(vertices[secondVertexIndex]));
		double dot = vectorParallelNormal.dotProduct(normal);
		boolean result = dot >= 0;
		return result;
	}

	private void computeNormal() {
		Vector3 first = this.vertices[1].subtract(this.vertices[0]);
		Vector3 second = this.vertices[2].subtract(this.vertices[0]);
		this.normal = first.crossProduct(second);
		// this.normal = transformVector(normal, 0,
		// this.transformationToWorldCoordiantes.inverse().transpose());
		this.normal.normalize();
	}
}
