package primitives;

import structures.IntersectionInfo;
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
		// this.vertices = vertices;
		for (int i = 0; i < vertices.length; i++) {
			this.vertices[i] = transformVector(vertices[i], 1,
					this.transformationToWorldCoordiantes);
		}
		computeNormal();
	}

	@Override
	public IntersectionInfo intersect(Ray theRay) {
		// Ray transfRay = new Ray(transformVector(theRay.origin, 1,
		// this.transformationToWorldCoordiantes.inverse()),
		// transformVector(theRay.direction, 0,
		// this.transformationToWorldCoordiantes.inverse()));
		return intersectTrans(theRay);
	}

	private IntersectionInfo intersectTrans(Ray theRay) {
		// Get the intersection point with the triangle's plane
		double distance = -theRay.origin.subtract(vertices[0]).dotProduct(
				normal)
				/ theRay.direction.dotProduct(normal);
		if (distance < 0) {
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
		// Vector3 normal = vertices[1].subtract(vertices[0]);
		// normal = normal.crossProduct(vertices[2].subtract(vertices[0]));
		// normal.normalize();
		//
		// double distance = -(theRay.origin.subtract(vertices[0])
		// .dotProduct(normal));
		// distance /= theRay.direction.dotProduct(normal);
		// if (distance < 0) {
		// return new IntersectionInfo(Double.POSITIVE_INFINITY, null, null);
		// }
		//
		// Vector3 intersectionPoint = theRay.origin.add(theRay.direction
		// .multiply(distance));
		// boolean firstCheck = vertices[1].subtract(vertices[0])
		// .crossProduct(intersectionPoint.subtract(vertices[0]))
		// .dotProduct(normal) >= 0;
		// boolean secondCheck = vertices[2].subtract(vertices[1])
		// .crossProduct(intersectionPoint).subtract(vertices[1])
		// .dotProduct(normal) >= 0;
		// boolean thirdCheck = vertices[0].subtract(vertices[2])
		// .crossProduct(intersectionPoint.subtract(vertices[2]))
		// .dotProduct(normal) >= 0;
		// if (firstCheck && secondCheck && thirdCheck) {
		// intersectionPoint = transformVector(intersectionPoint, 1,
		// this.transformationToWorldCoordiantes);
		// normal = transformVector(normal, 0,
		// this.transformationToWorldCoordiantes.inverse().transpose());
		// return new IntersectionInfo(distance, intersectionPoint, normal);
		// } else {
		// return new IntersectionInfo(Double.POSITIVE_INFINITY, null, null);
		// }
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
