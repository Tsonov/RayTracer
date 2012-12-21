package structures;

import Jama.Matrix;

public final class Transformations {

	public static Matrix getTranslateMatrix(Vector3 translateVector) {
		double[][] translateArray = { { 1, 0, 0, translateVector.getX() },
				{ 0, 1, 0, translateVector.getY() },
				{ 0, 0, 1, translateVector.getZ() }, { 0, 0, 0, 1 } };
		Matrix result = new Matrix(translateArray);
		return result;
	}

	public static Matrix getScaleMatrix(Vector3 scaleVector) {
		double[][] array = { { scaleVector.getX(), 0, 0, 0 },
				{ 0, scaleVector.getY(), 0, 0 },
				{ 0, 0, scaleVector.getZ(), 0 }, { 0, 0, 0, 1 } };
		Matrix result = new Matrix(array);
		return result;
	}

	public static Matrix getRotationMatrix(double degrees, Vector3 axis) {
		double radians = degrees * Math.PI / 180;
		axis.normalize();
		double x = axis.getX();
		double y = axis.getY();
		double z = axis.getZ();
		double cos = Math.cos(radians);

		double[][] matrixArray = { { cos, 0, 0 }, { 0, cos, 0 },
				{ 0, 0, cos } };
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
