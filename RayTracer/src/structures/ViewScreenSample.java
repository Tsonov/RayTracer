package structures;

public class ViewScreenSample {
	private final double row;
	private final double column;

	public ViewScreenSample(int row, int column) {
		//For now this just returns a sample from the center(not the corner) of each pixel
		this.row = row + 0.5;
		this.column = column + 0.5;
	}

	public double getRow() {
		return this.row;
	}

	public double getColumn() {
		return this.column;
	}
}
