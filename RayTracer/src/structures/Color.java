package structures;

public class Color {
	private final int red;
	private final int green;
	private final int blue;

	/**
	 * Constructs an RGB color
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public Color(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/**
	 * Construcs an RGB color by fraction values. Color is dumped, meaning that
	 * it's normalized to BLACK or WHITE for fractions outside the [0, 1] range.
	 * 
	 * @param redPercent
	 * @param greenPercent
	 * @param bluePercent
	 */
	public Color(double redPercent, double greenPercent, double bluePercent) {
		if (Double.compare(redPercent, 1) > 0)
			redPercent = 1;
		if (redPercent < 0)
			redPercent = 0;
		if (Double.compare(greenPercent, 1) > 0)
			greenPercent = 1;
		if (greenPercent < 0)
			greenPercent = 0;
		if (Double.compare(bluePercent, 1) > 0)
			bluePercent = 1;
		if (bluePercent < 0)
			bluePercent = 0;
		this.red = (int) (redPercent * 0xFF);
		this.green = (int) (greenPercent * 0xFF);
		this.blue = (int) (bluePercent * 0xFF);
	}

	/**
	 * Calculate the RGB sum value of this color
	 * 
	 * @return The RGB sum value
	 */
	public int getRGB() {
		int result = getBlue();
		result += getGreen() << 8;
		result += getRed() << 16;
		return result;
	}

	/**
	 * 
	 * @return The red term of this color
	 */
	public int getRed() {
		return red;
	}

	/**
	 * 
	 * @return The green term of this color
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * 
	 * @return The blue term of this color
	 */

	public int getBlue() {
		return blue;
	}
}
