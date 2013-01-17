package main;

import java.util.ArrayList;
import java.util.Collection;

import lights.AbstractLight;

import structures.Color;
import structures.IntersectionInfo;
import structures.Material;
import structures.Ray;
import structures.Vector3;
import primitives.Primitive;

public class RayTracer {
	private final double constAttenuation;
	private final double linearAttenuation;
	private final double quadraticAttenuation;
	private ArrayList<Primitive> objects;
	private ArrayList<AbstractLight> lights;

	public RayTracer() {
		this.objects = new ArrayList<Primitive>();
		this.lights = new ArrayList<AbstractLight>();
		this.constAttenuation = 1;
		this.linearAttenuation = 0;
		this.quadraticAttenuation = 0;
	}

	public RayTracer(Collection<Primitive> objects,
			Collection<AbstractLight> lights, Vector3 sceneAttenuation) {
		this.objects = new ArrayList<Primitive>(objects);
		this.lights = new ArrayList<AbstractLight>(lights);
		this.constAttenuation = sceneAttenuation.getX();
		this.linearAttenuation = sceneAttenuation.getY();
		this.quadraticAttenuation = sceneAttenuation.getZ();
	}

	public void addWorldObject(Primitive object) {
		this.objects.add(object);
	}

	public Color getColor(Ray theRay) {
		double minDistance = Double.POSITIVE_INFINITY;
		Primitive minObj = null;
		IntersectionInfo minIntersectionInfo = null;
		for (Primitive object : objects) {
			IntersectionInfo intersectInfo = object.intersect(theRay);
			double distance = intersectInfo.Distance;
			if (distance < minDistance) {
				minObj = object;
				minDistance = distance;
				minIntersectionInfo = intersectInfo;
			}
		}
		if (minObj == null || minIntersectionInfo == null) {
			return new Color(0, 0, 0);
		}

		Material objectMaterial = minObj.getMaterial();
		// Add the ambient and emission immediately as they are not affected by
		// light sources
		Vector3 colorProperties = objectMaterial.getAmbient().add(
				objectMaterial.getEmission());
		for (AbstractLight light : this.lights) {
			Vector3 shadowRayDirection = light.getCoordinates().subtract(
					minIntersectionInfo.IntersectionPoint);
			shadowRayDirection.normalize();
			Vector3 shadowRayOrigin = minIntersectionInfo.IntersectionPoint
					.add(shadowRayDirection.multiply(0.0001));
			double distanceToLight = minIntersectionInfo.IntersectionPoint.subtract(
					light.getCoordinates()).getLength();
			Ray shadowRay = new Ray(shadowRayOrigin, shadowRayDirection);
			if (isInShadow(shadowRay, distanceToLight)) {
				Vector3 currentLightTerm = getLightTerm(minIntersectionInfo,
						objectMaterial, light);
				colorProperties = colorProperties.add(currentLightTerm);
			}

		}

		Color result = new Color(colorProperties.getX(),
				colorProperties.getY(), colorProperties.getZ());
		result = new Color(minIntersectionInfo.Normal.getX(),
				minIntersectionInfo.Normal.getY(),
				minIntersectionInfo.Normal.getZ());
		return result;
	}

	private boolean isInShadow(Ray ray, double distanceToLightSource) {
		for (Primitive object : objects) {
			IntersectionInfo intersectInfo = object.intersect(ray);
			if (Double.compare(intersectInfo.Distance, distanceToLightSource) < 0)
				return true;
		}
		return false;
	}

	private Vector3 getLightTerm(IntersectionInfo minIntersectionInfo,
			Material objectMaterial, AbstractLight light) {
		// The attenuation term given the distance from the object to the
		// light
		double distance = minIntersectionInfo.IntersectionPoint.subtract(
				light.getCoordinates()).getLength();
		// Calculate the attenuation term based on the distance between the
		// light source and the object
		double attenuationFactor = constAttenuation + linearAttenuation
				* distance + quadraticAttenuation * distance * distance;
		Vector3 lightDirection = light.getCoordinates().subtract(
				minIntersectionInfo.IntersectionPoint);
		Color lightIntensity = light.getColor();
		Vector3 lightIntensityVector = new Vector3(lightIntensity.getRed(),
				lightIntensity.getGreen(), lightIntensity.getBlue());
		lightIntensityVector = lightIntensityVector
				.multiply(1 / attenuationFactor);

		// Calculate the diffuse term based on the location of the object
		// and the light
		double lightDiffuseFactor = Math.max(
				minIntersectionInfo.Normal.dotProduct(lightDirection), 0);
		Vector3 diffusionTerm = objectMaterial.getDiffuse().multiply(
				lightDiffuseFactor);

		Vector3 resultLightTerm = new Vector3(diffusionTerm.getX()
				* lightIntensityVector.getX(), diffusionTerm.getY()
				* lightIntensityVector.getY(), diffusionTerm.getZ()
				* lightIntensityVector.getZ());
		return resultLightTerm;
	}
}
