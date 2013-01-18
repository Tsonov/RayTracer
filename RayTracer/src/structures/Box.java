package structures;

import java.util.ArrayList;
import java.util.Iterator;

import primitives.Primitive;

public class Box {
	private final Vector3 minPoint;
	private final Vector3 maxPoint;

	public Box(Vector3 min, Vector3 max) {
		minPoint = new Vector3(min);
		maxPoint = new Vector3(max);
	}

	public Box(Box another) {
		this.minPoint = new Vector3(another.minPoint);
		this.maxPoint = new Vector3(another.maxPoint);
	}
	
	public boolean isHit(Ray ray) {
		Vector3 minDist = new Vector3();
		Vector3 maxDist = new Vector3();
		double coeff;

		// This is a intersection check from the Internet
		// Hopefully, it's working.
		for (int i = 0; i < 3; i++) {
			coeff = 1.0 / ray.direction.getCoordinate(i);
			if (coeff >= 0) {
				double minValue = coeff
						* (minPoint.getCoordinate(i) - ray.origin
								.getCoordinate(i));
				double maxValue = coeff
						* (maxPoint.getCoordinate(i) - ray.origin
								.getCoordinate(i));
				minDist.setCoordinate(i, minValue);
				maxDist.setCoordinate(i, maxValue);
			} else {
				double maxValue = coeff
						* (minPoint.getCoordinate(i) - ray.origin
								.getCoordinate(i));
				double minValue = coeff
						* (maxPoint.getCoordinate(i) - ray.origin
								.getCoordinate(i));
				minDist.setCoordinate(i, minValue);
				maxDist.setCoordinate(i, maxValue);
			}
		}
		if (foundHit(minDist, maxDist)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean foundHit(Vector3 min, Vector3 max) {
		return min.getX() > max.getY() || min.getY() > max.getX()
				|| min.getX() > max.getZ() || min.getZ() > max.getX()
				|| min.getY() > max.getZ() || min.getZ() > max.getY();
	}

	public Box combine(Box another) {
		Vector3 minP = new Vector3();
		Vector3 maxP = new Vector3();
		for (int i = 0; i < 3; i++) {
			double min = Math.min(this.minPoint.getCoordinate(i),
					another.minPoint.getCoordinate(i));
			double max = Math.max(this.maxPoint.getCoordinate(i),
					another.maxPoint.getCoordinate(i));
			minP.setCoordinate(i, min);
			maxP.setCoordinate(i, max);
		}

		return new Box(minP, maxP);
	}
	
	public Vector3 getMidPoint() {
		Vector3 result = new Vector3();
		for (int i = 0; i < 3; i++) {
			double value = this.minPoint.getCoordinate(i) + this.maxPoint.getCoordinate(i);
			value /= 2.0;
			result.setCoordinate(i, value);
		}
		return result;
	}
	
	public static Box combineBoundingBoxes(ArrayList<Primitive> primitives) {
		Box combined = new Box(primitives.get(0).getBoundingBox());
		int size = primitives.size();
		for (int i = 1; i < size; i++) {
			combined = combined.combine(primitives.get(i).getBoundingBox());
		}
		return combined;
	}
	
	public static void partition(ArrayList<Primitive> primitives, Vector3 midPoint, int axis,
			ArrayList<Primitive> left, ArrayList<Primitive> right) {
		for (Primitive primitive : primitives) {
			primitive.getBoundingBox().getMidPoint().getCoordinate(axis);
			if(axis < midPoint.getCoordinate(axis)) {
				left.add(primitive);
			} else {
				right.add(primitive);
			}
		}
		
		if(left.size() == primitives.size()) {
			right.add(left.get(left.size() - 1));
			left.remove(left.size() - 1);
		} else if( right.size() == primitives.size()) {
			left.add(right.get(right.size() - 1));
			right.remove(right.size() - 1);
		}
	}
}
