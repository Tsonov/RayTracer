package lights;

import structures.Color;
import structures.Vector3;

public class Light {
	public final boolean IsDirectional;
	private Vector3 coordinates;
	private Color color;

	public Light(Vector3 coordinates, Color color, boolean isLightDirectional) {
		this.coordinates = coordinates;
		this.color = color;
		this.IsDirectional = isLightDirectional;
	}

	public Color getColor() {
		return this.color;
	}

	public Vector3 getCoordinates() {
		return this.coordinates;
	}
}
