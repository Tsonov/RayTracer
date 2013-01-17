package lights;

import structures.Color;
import structures.Vector3;

public class AbstractLight {
	private Vector3 coordinates;
	private Color color;

	public AbstractLight(Vector3 coordinates, Color color) {
		this.coordinates = coordinates;
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public Vector3 getCoordinates() {
		return this.coordinates;
	}
}
