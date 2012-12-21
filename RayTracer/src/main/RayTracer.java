package main;

import java.util.ArrayList;
import java.util.Collection;

import structures.Color;
import structures.Material;
import structures.Ray;
import structures.Vector3;
import primitives.Primitive;

public class RayTracer {
	private ArrayList<Primitive> objects;

	public RayTracer() {
		this.objects = new ArrayList<Primitive>();
	}

	public RayTracer(Collection<Primitive> objects) {
		this.objects = new ArrayList<Primitive>(objects);
	}

	public void addWorldObject(Primitive object) {
		this.objects.add(object);
	}

	public Color getColor(Ray theRay) {
		double minDistance = Double.POSITIVE_INFINITY;
		Primitive minObj = null;
		for (Primitive object : objects) {
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
		Material objectMaterial = minObj.getMaterial();
		Vector3 colorProperties = objectMaterial.getAmbient().add(
				objectMaterial.getEmission());
		Color result = new Color(colorProperties.getX(),
				colorProperties.getY(), colorProperties.getZ());
		return result;
	}
}
