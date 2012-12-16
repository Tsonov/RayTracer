package main;

import java.util.ArrayList;
import java.util.Collection;

import structures.Color;
import structures.Ray;
import primitives.Intersectable;

public class RayTracer {
	private ArrayList<Intersectable> objects;

	public RayTracer() {
		this.objects = new ArrayList<Intersectable>();
	}

	public RayTracer(Collection<Intersectable> objects) {
		this.objects = new ArrayList<Intersectable>(objects);
	}

	public void addWorldObject(Intersectable object) {
		this.objects.add(object);
	}

	public Color getColor(Ray theRay) {
		double minDistance = Double.POSITIVE_INFINITY;
		Intersectable minObj = null;
		for (Intersectable object : objects) {
			double distance = object.intersect(theRay);
			if (distance < minDistance) {
				minObj = object;
				minDistance = distance;
			}
		}
		if (minObj == null) {
			return new Color(0, 0, 0);
		}
		// else {
		// if (minObj instanceof primitives.Triangle) {
		// return ((primitives.Triangle) minObj).testColor;
		// } else
		return new Color(0, 255, 0);
	}
}
