package main;

import java.util.ArrayList;
import java.util.Collection;

import lights.Light;

import structures.Color;
import structures.IntersectionInfo;
import structures.Material;
import structures.Ray;
import structures.Vector3;
import primitives.Primitive;
import primitives.Sphere;

public class RayTracer {
	private final double constAttenuation;
	private final double linearAttenuation;
	private final double quadraticAttenuation;
	private final int MAX_DEPTH = 5;
	private ArrayList<Primitive> objects;
	private ArrayList<Light> lights;

	// for debug
	// private double debugX = 150;
	// private double debugY = 200;
	// private double debugZ = 90;

	public RayTracer() {
		this.objects = new ArrayList<Primitive>();
		this.lights = new ArrayList<Light>();
		this.constAttenuation = 1;
		this.linearAttenuation = 0;
		this.quadraticAttenuation = 0;
	}

	public RayTracer(Collection<Primitive> objects, Collection<Light> lights,
			Vector3 sceneAttenuation) {
		this.objects = new ArrayList<Primitive>(objects);
		this.lights = new ArrayList<Light>(lights);
		this.constAttenuation = sceneAttenuation.getX();
		this.linearAttenuation = sceneAttenuation.getY();
		this.quadraticAttenuation = sceneAttenuation.getZ();
	}

	public void addWorldObject(Primitive object) {
		this.objects.add(object);
	}

	public Color getColor(Ray theRay) {
		return this.getColor(theRay, 0);
	}

	public Color getColor(Ray theRay, int depth) {
		if (depth == MAX_DEPTH) {
			return new Color(0, 0, 0);
		}
		double minDistance = Double.POSITIVE_INFINITY;
		Primitive minObj = null;
		IntersectionInfo minIntersectionInfo = null;
		for (Primitive object : objects) {
			IntersectionInfo intersectInfo = object.intersect(theRay);
			double distance = intersectInfo.Distance;
			if (Double.compare(minDistance, distance) > 0) {
				minObj = object;
				minDistance = distance;
				minIntersectionInfo = intersectInfo;
			}
		}
		if (minObj == null || minIntersectionInfo == null) {
			return new Color(0, 0, 0);
		}

		// DEBUG
		// if (depth == 1) {
		// return new Color(minIntersectionInfo.IntersectionPoint.getX(),
		// minIntersectionInfo.IntersectionPoint.getY(),
		// minIntersectionInfo.IntersectionPoint.getZ());
		// // return new Color(minIntersectionInfo.Normal.getX(),
		// // minIntersectionInfo.Normal.getY(),
		// // minIntersectionInfo.Normal.getZ());
		// } else {
		// // Vector3 reflectedRayDirection = getReflectedDirection(
		// // theRay.direction, minIntersectionInfo.Normal);
		// // Vector3 reflectedRayOrigin =
		// // minIntersectionInfo.IntersectionPoint
		// // .add(reflectedRayDirection.multiply(0.001));
		// // Ray reflectedRay = new Ray(reflectedRayOrigin,
		// // reflectedRayDirection);
		// // Color reflection = this.getColor(reflectedRay, depth + 1);
		// // return reflection;
		// }
		Material objectMaterial = minObj.getMaterial();
		// Add the ambient and emission terms immediately as they are not
		// affected by light sources
		Vector3 colorProperties = objectMaterial.getAmbient().add(
				objectMaterial.getEmission());

		// Loop through all the lights to find out if the object is affected
		for (Light light : this.lights) {
			Vector3 shadowRayDirection = light.getCoordinates().subtract(
					minIntersectionInfo.IntersectionPoint);
			// shadowRayDirection.normalize();
			Vector3 shadowRayOrigin = minIntersectionInfo.IntersectionPoint
					.add(shadowRayDirection.multiply(0.001));
			double distanceToLight = minIntersectionInfo.IntersectionPoint
					.subtract(light.getCoordinates()).getLength();
			Ray shadowRay = new Ray(shadowRayOrigin, shadowRayDirection);
			if (isInShadow(shadowRay, distanceToLight) == false) {
				Vector3 currentLightTerm = getLightTerm(minIntersectionInfo,
						objectMaterial, light, theRay.direction);
				colorProperties = colorProperties.add(currentLightTerm);
			}
		}

		if (objectMaterial.getSpecular().isZero() == false) {
			Vector3 reflectedLightTerm = getReflectedLightTerm(theRay,
					minIntersectionInfo, depth);
			reflectedLightTerm = reflectedLightTerm
					.multiplyByVector(objectMaterial.getSpecular());
			colorProperties = colorProperties.add(reflectedLightTerm);
		}
		Color result = new Color(colorProperties.getX(),
				colorProperties.getY(), colorProperties.getZ());
		//Debug
//		result = new Color(minIntersectionInfo.Normal.getX(),
//				minIntersectionInfo.Normal.getY(),
//				minIntersectionInfo.Normal.getZ());
		return result;
	}

	private Vector3 getReflectedLightTerm(Ray originalRay,
			IntersectionInfo intersectionInfo, int depth) {
		Vector3 reflectedRayDirection = getReflectedDirection(
				originalRay.direction, intersectionInfo.Normal);
		Vector3 reflectedRayOrigin = intersectionInfo.IntersectionPoint
				.add(reflectedRayDirection.multiply(0.001));
		Ray reflectedRay = new Ray(reflectedRayOrigin, reflectedRayDirection);
		Color reflection = this.getColor(reflectedRay, depth + 1);
		Vector3 result = new Vector3(reflection);
		return result;
	}

	private Vector3 getLightTerm(IntersectionInfo minIntersectionInfo,
			Material objectMaterial, Light light, Vector3 rayDir) {
		Color lightIntensity = light.getColor();
		Vector3 lightIntensityVector = new Vector3(lightIntensity);

		// If the light is a point, we have to add an attenuation factor
		if (light.IsDirectional == false) {
			double distance = minIntersectionInfo.IntersectionPoint.subtract(
					light.getCoordinates()).getLength();
			double attenuationFactor = constAttenuation + linearAttenuation
					* distance + quadraticAttenuation * distance * distance;
			lightIntensityVector = lightIntensityVector
					.multiply(1 / attenuationFactor);
		}

		// Calculate the diffuse term based on the location of the object
		// and the light
		Vector3 lightDirection = light.getCoordinates().subtract(
				minIntersectionInfo.IntersectionPoint);
		// debugX = lightDirection.getX();
		// debugY = lightDirection.getY();
		// debugZ = lightDirection.getZ();
		lightDirection.normalize();
		double lightDiffuseFactor = Math.max(
				minIntersectionInfo.Normal.dotProduct(lightDirection), 0);
		Vector3 diffusionTerm = objectMaterial.getDiffuse().multiply(
				lightDiffuseFactor);

		// Calculate the specular term based on the location of the object and
		// the reflection properties
		// But only if the light is visible ( i.e. the diffuse factor is not 0 )
		Vector3 halfAngle = lightDirection.subtract(rayDir);
		halfAngle.normalize();
		double specularFactor = Math.max(
				halfAngle.dotProduct(minIntersectionInfo.Normal), 0);
		specularFactor = Math
				.pow(specularFactor, objectMaterial.getShininess());
		Vector3 specularTerm = objectMaterial.getSpecular().multiply(
				specularFactor);

		Vector3 resultLightTerm = diffusionTerm.add(specularTerm)
				.multiplyByVector(lightIntensityVector);
		return resultLightTerm;
	}

	private boolean isInShadow(Ray ray, double distanceToLightSource) {
		for (Primitive object : objects) {
			IntersectionInfo intersectInfo = object.intersect(ray);
			if (intersectInfo.Distance > 0
					&& Double.compare(intersectInfo.Distance,
							distanceToLightSource) < 0)
				return true;
		}
		return false;
	}

	private Vector3 getReflectedDirection(Vector3 incomingDirection,
			Vector3 reflectionAxis) {
		Vector3 result = new Vector3(incomingDirection);
		Vector3 reflectionModifier = reflectionAxis.multiply(reflectionAxis
				.dotProduct(incomingDirection) * 2);
		result = result.subtract(reflectionModifier);
		return result;
	}
}
