package structures;

import java.util.ArrayList;
import java.util.Collection;

import lights.Light;
import primitives.Primitive;

public class Scene {
	private int width;
	private int height;
	private int maxDepth;
	private Camera camera;
	private ArrayList<Primitive> worldObjects;
	private ArrayList<Light> lights;
	private String sceneName;
	private Vector3 attenuation;

	/**
	 * Creates a new scene with default values
	 */
	public Scene() {
		this.width = 640;
		this.height = 480;
		this.maxDepth = 5;
		this.camera = null;
		this.worldObjects = new ArrayList<Primitive>();
		this.lights = new ArrayList<Light>();
		this.sceneName = "Default_scene_name";
		this.attenuation = new Vector3(1, 0, 0);
	}

	/**
	 * Returns a ray for the scene for a given screen sample. The ray starts
	 * from the scene's camera.
	 * 
	 * @param sample
	 *            - the screen sample
	 * @return A ray into the scene that has an origin based on the screen
	 *         sample
	 */
	public Ray getRay(ViewScreenSample sample) {
		return this.camera.getRay(sample);
	}

	/**
	 * Returns the world objects that are in the scene
	 * 
	 * @return A collection of the objects in the scene
	 */
	public Collection<Primitive> getWorldObjects() {
		return this.worldObjects;

	}

	/**
	 * Returns the lights that are in the scene
	 * 
	 * @return A collection of the lights in the scene
	 */
	public Collection<Light> getLights() {
		return this.lights;
	}

	/**
	 * Adds a new object to the scene's definition
	 * 
	 * @param object
	 *            - the new object for the scene
	 */
	public void addWorldObject(Primitive object) {
		this.worldObjects.add(object);
	}

	/**
	 * Adds a new light to the scene's definition
	 * 
	 * @param light
	 *            - the new light
	 */
	public void addNewLightSource(Light light) {
		this.lights.add(light);
	}

	/**
	 * 
	 * @return
	 */
	// public Camera getCamera() {
	// return this.camera;
	// }

	/**
	 * Sets the parameters of the camera that is used to look at the scene
	 * 
	 * @param lookFromX
	 *            - x coordinate of the eye's location
	 * @param lookFromY
	 *            - y coordinate of the eye's location
	 * @param lookFromZ
	 *            - z coordinate of the eye's location
	 * @param lookAtX
	 *            - x coordinate of where the eye is looking at
	 * @param lookAtY
	 *            - y coordinate of where the eye is looking at
	 * @param lookAtZ
	 *            - z coordinate of where the eye is looking at
	 * @param upX
	 *            - x coordinate of the up vector for the eye
	 * @param upY
	 *            - y coordinate of the up vector for the eye
	 * @param upZ
	 *            - z coordinate of the up vector for the eye
	 * @param fieldOfViewY
	 *            - the field of view in the Y (or screen height) direction
	 */
	public void setCameraParameters(double lookFromX, double lookFromY,
			double lookFromZ, double lookAtX, double lookAtY, double lookAtZ,
			double upX, double upY, double upZ, double fieldOfViewY) {
		if (this.width == 0 || this.height == 0) {
			throw new IllegalStateException(
					"Width and height must be set before the camera is specified");
		}
		Vector3 eye = new Vector3(lookFromX, lookFromY, lookFromZ);
		Vector3 center = new Vector3(lookAtX, lookAtY, lookAtZ);
		Vector3 up = new Vector3(upX, upY, upZ);
		this.camera = new Camera(eye, center, up, fieldOfViewY, width, height);
	}

	/**
	 * Returns the attenuation constants for this scene
	 * 
	 * @return The attenuation constants in a vector in format (constant,
	 *         linear, quadratic)
	 */
	public Vector3 getAttenuation() {
		return attenuation;
	}

	/**
	 * Set the attenuation constants for this scene
	 * 
	 * @param attenuation
	 *            - a vector in the format (constant, linear, quadratic) values
	 */
	public void setAttenuation(Vector3 attenuation) {
		this.attenuation = attenuation;
	}

	/**
	 * 
	 * @return The name of this scene
	 */
	public String getSceneName() {
		return sceneName;
	}

	/**
	 * Set the name of this scene
	 * 
	 * @param sceneName
	 *            - the new name for the scene
	 */
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	/**
	 * 
	 * @return The width of the view screen for this scene
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the width of the view screen for this scene
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 
	 * @return The height of the view screen for this scene
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set the height of the view screen for this scene
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 
	 * @return the max depth of a recursive ray tracing for this scene
	 */
	public int getMaxDepth() {
		return maxDepth;
	}

	/**
	 * Set the max depth of a recursive ray tracing for this scene
	 * 
	 * @param maxDepth
	 */
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
}
