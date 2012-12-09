package main;

import java.util.ArrayList;
import java.util.Random;

import structures.Color;
import structures.Ray;
import primitives.Intersectable;

public class RayTracer {
	private ArrayList<Intersectable> objects = new ArrayList<>();

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
			}
		}
		if (minObj == null)
			return new Color(0, 0, 0);
		else {
			if (minObj instanceof primitives.Triangle) {
				Random rand = new Random();
				return new Color(rand.nextInt(255), 0, 0);
			} else
				return new Color(0, 255, 0);
		}
	}
}
