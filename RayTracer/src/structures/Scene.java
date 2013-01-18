package structures;

import java.util.ArrayList;
import java.util.Collection;

import lights.Light;
import primitives.Primitive;

public class Scene {
	private int width;
	private int height;
	private Camera camera;
	private ArrayList<Primitive> worldObjects;
	private String sceneName;
	private Vector3 attenuation;
	private ArrayList<Light> lights;

	public Scene() {
		// TODO
		worldObjects = new ArrayList<>();
		lights = new ArrayList<>();
		sceneName = "default_output_scene_name";
		width = 640;
		height = 480;
		camera = null;
		attenuation = new Vector3(1, 0, 0);
	}

	public Ray getRay(ViewScreenSample sample) {
		return this.camera.getRay(sample);
	}

	public Collection<Primitive> getWorldObjects() {
		return this.worldObjects;

	}
	
	public Collection<Light> getLights() {
		return this.lights;
	}

	public void addWorldObject(Primitive object) {
		this.worldObjects.add(object);
	}

	public void addNewLightSource(Light light) {
		this.lights.add(light);
	}

	public Camera getCamera() {
		return this.camera;
	}

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

	public Vector3 getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(Vector3 attenuation) {
		this.attenuation = attenuation;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
