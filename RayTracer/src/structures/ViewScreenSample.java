package structures;

public class ViewScreenSample {
	private final double row;
	private final double column;

	/**
	 * Creates a new screen sample based on a location from the View Screen
	 * 
	 * @param row
	 *            - the row from the screen
	 * @param column
	 *            - the column from the screen
	 */
	public ViewScreenSample(int row, int column) {
		// For now this just returns a sample from the center(not the corner) of
		// each pixel
		this.row = row + 0.5;
		this.column = column + 0.5;
	}

	/**
	 * 
	 * @return the sample's row
	 */
	public double getRow() {
		return this.row;
	}

	/**
	 * 
	 * @return the sample's column
	 */
	public double getColumn() {
		return this.column;
	}
}
