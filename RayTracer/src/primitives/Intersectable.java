package primitives;

import structures.*;

public interface Intersectable {
	/**
	 * Checks whether a given ray intersects this object and returns information
	 * about the intersection
	 * 
	 * @param theRay
	 *            - the ray that is going into the scene
	 * @return Information about the intersection
	 */
	public IntersectionInfo intersect(Ray theRay);
}
