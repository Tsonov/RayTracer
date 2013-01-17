package primitives;

import structures.*;

public interface Intersectable {
	public IntersectionInfo intersect(Ray theRay);
}
