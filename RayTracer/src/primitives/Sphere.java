package primitives;

import structures.*;
import Jama.Matrix;

public class Sphere extends Primitive {
	private double radius;
	private Vector3 center;

	public Sphere(Vector3 theCenter, double radius, Matrix transformation) {
		super(transformation);
		this.radius = radius;
		this.center = theCenter;
	}

	@Override
	public IntersectionInfo intersect(Ray theRay) {
		Matrix inverseTransformation = this.transformationToWorldCoordiantes.inverse();
		Vector3 transfOrigin = transformVector(theRay.origin, 1, inverseTransformation);
		Vector3 transfDirection = transformVector(theRay.direction, 0, inverseTransformation);
		Ray transformedRay = new Ray(transfOrigin, transfDirection);
		return intersectTrans(transformedRay);
	}


	public IntersectionInfo intersectTrans(Ray theRay) {
		Vector3 vecToCenter = theRay.origin.subtract(this.center);
		// Form a quadratic parametric equation a*x^2+b*x+c=0 and solve for the
		// distance along the direction
		double a = theRay.direction.dotProduct(theRay.direction);
		double b = vecToCenter.dotProduct(theRay.direction);
		double c = vecToCenter.dotProduct(vecToCenter) - this.radius
				* this.radius;
		double discriminant = b * b - a * c;
		double resultDistance = Double.POSITIVE_INFINITY;
		if (discriminant == 0) {
			// Tangent
			resultDistance = -b / a;
		} else if (discriminant > 0) {
			discriminant = Math.sqrt(discriminant);
			double firstRoot = (-b - discriminant) / a;
			double secondRoot = (-b + discriminant) / a;
			// Note that firstRoot is always smaller than secondRoot
			// At least one root must be positive if the object is in front of
			// the camera
			if (secondRoot > 0) {
				if (firstRoot > 0) {
					// Two intersections, pick the smaller one;
					resultDistance = firstRoot;
				} else {
					// One intersection point(camera in object), return the
					// positive root
					resultDistance = secondRoot;
				}
			}
		}
		//If the distance is still Infinity, we don't have an intersection
		if (Double.compare(resultDistance, Double.POSITIVE_INFINITY) == 0) {
			return new IntersectionInfo(Double.POSITIVE_INFINITY, null, null);
		}
		
		Vector3 intersectionPoint = theRay.origin.add(theRay.direction
				.multiply(resultDistance));
		intersectionPoint = transformVector(intersectionPoint, 1, this.transformationToWorldCoordiantes);
		intersectionPoint = transformVector(intersectionPoint, 1, this.transformationToWorldCoordiantes.inverse());
		Vector3 normal = intersectionPoint.subtract(this.center);
		normal.normalize();
		Matrix transformationForNormal = this.transformationToWorldCoordiantes.inverse().transpose();
		normal = transformVector(normal, 0, transformationForNormal);
		normal.normalize();
		//this.transformIntersectionObjectsToWorld(intersectionPoint, normal);
		return new IntersectionInfo(resultDistance, intersectionPoint, normal);
	}
}
