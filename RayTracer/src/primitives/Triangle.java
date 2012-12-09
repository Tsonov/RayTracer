package primitives;

import structures.Ray;
import structures.Vector3;
import structures.Color;

public class Triangle implements Intersectable {
	private Vector3[] vertices = new Vector3[3];
	private Vector3 normal;
	public Color testColor;
	
	public Triangle(Vector3 first, Vector3 second, Vector3 third) {
		this(new Vector3[] { first, second, third });
	}

	public Triangle(Vector3[] vertices) {
		if (vertices.length != 3) {
			throw new IllegalArgumentException(
					"A triangle needs exactly three vertices to be defined!");
		}
		this.vertices = vertices;
		computeNormal();
	}

	public Triangle(Vector3 first, Vector3 second, Vector3 third, Color theColor) {
		this(first, second, third);
		this.testColor = theColor;
	}

	@Override
	public double intersect(Ray theRay) {
		// Get the intersection point with the triangle's plane
		double distance = -theRay.origin.subtract(vertices[0]).dotProduct(
				normal)
				/ theRay.direction.dotProduct(normal);
		Vector3 intersection = theRay.origin.add(theRay.direction
				.multiply(distance));
		// A counter-clockwise based check to see whether the intersection
		// point is within the triangle
		boolean isInside = isToTheLeftFromLine(1, 0, intersection)
				&& isToTheLeftFromLine(2, 1, intersection)
				&& isToTheLeftFromLine(0, 2, intersection);
		if (isInside) {
			return distance;
		} else {
			return Double.POSITIVE_INFINITY;
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
	}
}
