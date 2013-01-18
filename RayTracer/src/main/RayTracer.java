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
		if(depth == MAX_DEPTH) {
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
					.add(shadowRayDirection.multiply(0.0001));
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
					minIntersectionInfo);
			colorProperties = colorProperties.add(reflectedLightTerm);
			colorProperties = new Vector3(reflectedLightTerm);
		}
		Color result = new Color(colorProperties.getX(),
				colorProperties.getY(), colorProperties.getZ());
		// result = new Color(minIntersectionInfo.Normal.getX() * (-1),
		// minIntersectionInfo.Normal.getY() * (-1),
		// minIntersectionInfo.Normal.getZ() * (-1));
		return result;
	}

	private Vector3 getReflectedLightTerm(Ray originalRay,
			IntersectionInfo intersectionInfo) {
		Vector3 reflectedRayOrigin = intersectionInfo.IntersectionPoint;
		
		Vector3 result = new Vector3();
		return reflectedRayOrigin;
	}

	private Vector3 getLightTerm(IntersectionInfo minIntersectionInfo,
			Material objectMaterial, Light light, Vector3 rayDir) {
		Color lightIntensity = light.getColor();
		Vector3 lightIntensityVector = new Vector3(
				lightIntensity.getRed() / 0xFF,
				lightIntensity.getGreen() / 0xFF,
				lightIntensity.getBlue() / 0xFF);

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

}
