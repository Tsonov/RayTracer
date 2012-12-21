package structures;

public class Color {
	private final int PURE_RED = 0xFF0000;
	private final int PURE_GREEN = 0xFF00;
	private final int PURE_BLUE = 0xFF;
	private final int WHITE = 0xFFFFFF;
	private final int BLACK = 0x0;
	private final int red;
	private final int green;
	private final int blue;

	public Color(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public Color(double redPercent, double greenPercent, double bluePercent) {
		this.red = (int) (redPercent * 0xFF);
		this.green = (int) (greenPercent * 0xFF);
		this.blue = (int) (bluePercent * 0xFF);
	}

	public int getRGB() {
		int result = getBlue();
		result += getGreen() << 8;
		result += getRed() << 16;
		return result;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}
}
