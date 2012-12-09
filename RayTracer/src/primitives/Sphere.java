package primitives;

import structures.*;

public class Sphere implements Intersectable {
	private double radius;
	private Vector3 center;

	public Sphere(Vector3 theCenter, double radius) {
		this.radius = radius;
		this.center = theCenter;
	}

	@Override
	public double intersect(Ray theRay) {
		Vector3 vecToCenter = theRay.origin.subtract(this.center);
		// Form a quadratic parametric equation ax^2+bx+c=0 and solve for the
		// distance along the direction
		double a = theRay.direction.dotProduct(theRay.direction);
		double b = vecToCenter.dotProduct(theRay.direction);
		double c = vecToCenter.dotProduct(vecToCenter) - this.radius
				* this.radius;
		double discriminant = b * b - a * c;
		double result = Double.POSITIVE_INFINITY;
		if (discriminant == 0) {
			// Tangent
			result = -b/a;
		} else if(discriminant > 0){
			discriminant = Math.sqrt(discriminant);
			double firstRoot = (-b - discriminant) / a;
			double secondRoot = (-b + discriminant) / a;
			// Note that firstRoot is always smaller than secondRoot
			// At least one root must be positive if the object is in front of
			// the camera
			if (secondRoot > 0) {
				if (firstRoot > 0) {
					// Two intersections, pick the smaller one;
					result = firstRoot;
				} else {
					//One intersection point(camera in object), return the positive root
					result = secondRoot;
				}
			}
		}
		return result;
	}
}
