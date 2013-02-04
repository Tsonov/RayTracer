package structures;

import Jama.Matrix;

public final class Transformations {

	/**
	 * Gets the identity 4x4 matrix
	 * 
	 * @return The identity matrix
	 */
	public static Matrix getIdentityMatrix() {
		double[][] identityArray = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 },
				{ 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(identityArray);
	}

	/**
	 * Gets a transformation 4x4 matrix for a translation by a vector
	 * 
	 * @param translateVector
	 *            - the vector of translation
	 * @return A transformation matrix corresponding to the translation
	 */
	public static Matrix getTranslateMatrix(Vector3 translateVector) {
		double[][] translateArray = { { 1, 0, 0, translateVector.getX() },
				{ 0, 1, 0, translateVector.getY() },
				{ 0, 0, 1, translateVector.getZ() }, { 0, 0, 0, 1 } };
		Matrix result = new Matrix(translateArray);
		return result;
	}

	/**
	 * Gets a transformation 4x4 matrix for a scale operation
	 * 
	 * @param scaleVector
	 *            - the x, y, z scaling factors
	 * @return A transformation matrix corresponding to the scale operation
	 */
	public static Matrix getScaleMatrix(Vector3 scaleVector) {
		double[][] array = { { scaleVector.getX(), 0, 0, 0 },
				{ 0, scaleVector.getY(), 0, 0 },
				{ 0, 0, scaleVector.getZ(), 0 }, { 0, 0, 0, 1 } };
		Matrix result = new Matrix(array);
		return result;
	}

	/**
	 * Gets a transformation 4x4 matrix for a rotation around an axis
	 * 
	 * @param degrees
	 *            - the angle of rotation
	 * @param axis
	 *            - the axis for rotation
	 * @return A transformation matrix corresponding to the rotation
	 */
	public static Matrix getRotationMatrix(double degrees, Vector3 axis) {
		double radians = degrees * Math.PI / 180;
		axis.normalize();
		double x = axis.getX();
		double y = axis.getY();
		double z = axis.getZ();
		double cos = Math.cos(radians);

		double[][] matrixArray = { { cos, 0, 0 }, { 0, cos, 0 }, { 0, 0, cos } };
		Matrix rotationMatrix = new Matrix(matrixArray);

		double[][] componentAlongAxisArray = { { x * x, x * y, x * z },
				{ x * y, y * y, y * z }, { x * z, y * z, z * z } };
		Matrix componentAlongAxis = new Matrix(componentAlongAxisArray);
		componentAlongAxis.timesEquals(1 - cos);
		rotationMatrix.plusEquals(componentAlongAxis);

		double[][] rotateCompArray = { { 0, -z, y }, { z, 0, -x }, { -y, x, 0 } };
		Matrix rotateComponent = new Matrix(rotateCompArray);
		double sin = Math.sin(radians);
		rotateComponent = rotateComponent.times(sin);
		rotationMatrix.plusEquals(rotateComponent);

		// Transform to a 4x4 matrix for homogeneous coordinates
		// The important part is the 1 for the homogeneous coordinate
		// All else will be replaced
		double[][] resultArray = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 1 } };
		Matrix result = new Matrix(resultArray);
		result.setMatrix(0, 2, 0, 2, rotationMatrix);
		return result;
	}
}
