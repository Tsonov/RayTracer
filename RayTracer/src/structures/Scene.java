package structures;

import java.util.ArrayList;
import java.util.Collection;

import primitives.Intersectable;

public class Scene {
	private int width;
	private int height;
	private Camera camera;
	private ArrayList<Intersectable> worldObjects;

	public Scene() {
		// TODO
		worldObjects = new ArrayList<>();
	}

	public Ray getRay(ViewScreenSample sample) {
		return this.camera.getRay(sample);
	}
	
	public Collection<Intersectable> getWorldObjects() {
		return this.worldObjects;

	}

	public void addWorldObject(Intersectable object) {
		this.worldObjects.add(object);
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
