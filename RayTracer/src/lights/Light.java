package lights;

import structures.Color;
import structures.Vector3;

public class Light {
	public final boolean IsDirectional;
	private Vector3 coordinates;
	private Color color;

	/**
	 * Creates a new light source
	 * 
	 * @param coordinates
	 *            - the location of the light
	 * @param intensity
	 *            - the intensity of the light in RGB
	 * @param isLightDirectional
	 *            - the type of the light (directional or point)
	 */
	public Light(Vector3 coordinates, Color intensity,
			boolean isLightDirectional) {
		this.coordinates = coordinates;
		this.color = intensity;
		this.IsDirectional = isLightDirectional;
	}

	/**
	 * 
	 * @return The intensity of this light source
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * 
	 * @return The location of the light source or the direction for directional lights
	 */
	public Vector3 getCoordinates() {
		return this.coordinates;
	}
}
