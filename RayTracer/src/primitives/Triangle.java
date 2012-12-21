package primitives;

import structures.Ray;
import Jama.Matrix;
import structures.Vector3;

public class Triangle extends Primitive {
	private Vector3[] vertices = new Vector3[3];
	private Vector3 normal;

	public Triangle(Vector3 first, Vector3 second, Vector3 third,
			Matrix transformation) {
		this(new Vector3[] { first, second, third }, transformation);
	}

	public Triangle(Vector3[] vertices, Matrix transformation) {
		super(transformation);
		if (vertices.length != 3) {
			throw new IllegalArgumentException(
					"A triangle needs exactly three vertices to be defined!");
		}
		this.vertices = vertices;
		computeNormal();
	}

	@Override
	public double intersect(Ray theRay) {
		Vector3 transfOrigin = transformVector(theRay.origin, 1);
		Vector3 transfDirection = transformVector(theRay.direction, 0);
		Ray transformedRay = new Ray(transfOrigin, transfDirection);
		return intersectTrans(transformedRay);
	}

	private Vector3 transformVector(Vector3 vector, double homogeneousCoord) {
		double[][] homogenOriginArray = { { vector.getX() },
				{ vector.getY() }, { vector.getZ() },
				{ homogeneousCoord } };
		Matrix homogenOriginMatrix = new Matrix(homogenOriginArray);
		homogenOriginMatrix = this.rayTransformation.times(homogenOriginMatrix);
		Vector3 transformedVector = new Vector3(homogenOriginMatrix.get(0, 0),
				homogenOriginMatrix.get(1, 0), homogenOriginMatrix.get(2, 0));
		return transformedVector;
	}

	private double intersectTrans(Ray transformedRay) {
		// Get the intersection point with the triangle's plane
		double distance = -transformedRay.origin.subtract(vertices[0])
				.dotProduct(normal)
				/ transformedRay.direction.dotProduct(normal);
		Vector3 intersection = transformedRay.origin
				.add(transformedRay.direction.multiply(distance));
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
