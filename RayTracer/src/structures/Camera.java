package structures;

public final class Camera {
	private final Vector3 eye;
	private final Vector3 u;
	private final Vector3 v;
	private final Vector3 w;
	private final double fieldViewY;
	private final double widthViewScreen;
	private final double heightViewScreen;

	/**
	 * Construct a new camera that can send view rays
	 * 
	 * @param eye
	 *            - the eye location
	 * @param center
	 *            - where the eye is looking at
	 * @param up
	 *            - the up vector for the eye's orientation
	 * @param fieldViewY
	 *            - the field of view in the Y (height) direction
	 * @param width
	 *            - the width of the View Screen
	 * @param height
	 *            - the height of the View Screen
	 */
	public Camera(Vector3 eye, Vector3 center, Vector3 up, double fieldViewY,
			int width, int height) {
		this.eye = eye;
		this.fieldViewY = fieldViewY * Math.PI / 180; // store in radians for
														// math functions
		// Construct the camera coordinate system here
		Vector3 w = eye.subtract(center);
		w.normalize();
		up = getProperUp(up, w);
		Vector3 u = up.crossProduct(w);
		u.normalize();
		Vector3 v = w.crossProduct(u);
		v.normalize();
		this.u = u;
		this.v = v;
		this.w = w;
		this.widthViewScreen = width;
		this.heightViewScreen = height;
	}

	/**
	 * Starts a Ray from this camera for a screen sample
	 * 
	 * @param sample
	 *            - the sample from the view screen
	 * @return The ray from the camera to the sample
	 */
	public Ray getRay(ViewScreenSample sample) {
		Vector3 direction = new Vector3();
		double alphaCoordinate = getColumnCoordinate(sample);
		direction = direction.add(u.multiply(alphaCoordinate));
		double betaCoordinate = getRowCoordinate(sample);
		direction = direction.add(v.multiply(betaCoordinate));
		// The screen is -1 units in camera coordinates away from the camera
		// A convention similar to OpenGL's one
		direction = direction.add(w.multiply(-1));
		direction.normalize();
		Ray result = new Ray(eye, direction);
		return result;
	}
	
	private Vector3 getProperUp(Vector3 up, Vector3 viewAxisVector) {
		Vector3 planeVec = up.crossProduct(viewAxisVector);
		Vector3 orthogonalUp = viewAxisVector.crossProduct(planeVec);
		orthogonalUp.normalize();
		return orthogonalUp;
	}

	private double getColumnCoordinate(ViewScreenSample sample) {
		// double result = Math.tan(fieldViewX / 2);
		double result = Math.tan(fieldViewY / 2);
		result *= (sample.getColumn() - (widthViewScreen / 2))
				/ (heightViewScreen / 2);
		return result;
	}

	private double getRowCoordinate(ViewScreenSample sample) {
		double result = Math.tan(fieldViewY / 2);
		result *= ((heightViewScreen / 2) - sample.getRow())
				/ (heightViewScreen / 2);
		return result;
	}
}
