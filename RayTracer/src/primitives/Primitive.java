package primitives;

import structures.Material;
import structures.Vector3;
import Jama.Matrix;

public abstract class Primitive implements Intersectable {
	protected final Matrix transformationToWorldCoordiantes;
	private Material material;

	/**
	 * Sets the tranformation for this primitive
	 * 
	 * @param transformation
	 *            - the transformation for this primitive
	 */
	protected Primitive(Matrix transformation) {
		this.transformationToWorldCoordiantes = transformation;
	}

	/**
	 * Sets the material properties for this primitive
	 * 
	 * @param material
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * Returns the material properties of this primitive
	 * 
	 * @return The material properties
	 */
	public Material getMaterial() {
		return this.material;
	}

	/**
	 * Returns the affine transformation for this primitive
	 * 
	 * @return The matrix of the transformation
	 */
	public Matrix getTransformation() {
		return this.transformationToWorldCoordiantes;
	}

	/**
	 * Transforms a 3-dimensional vector into homogeneous coordinates, applies a
	 * transformation to them. Does not dehomogenize the vector, but drops the
	 * homogeneous coordinate instead.
	 * 
	 * @param vector
	 *            - the vector to transform
	 * @param homogeneousCoord
	 *            - the homogeneous coordinate to apply to the vector(1 for
	 *            points, 0 for directional vectors)
	 * @param transformation
	 *            - the transformation to apply to the vector, must be a 4x4
	 *            matrix
	 * @return
	 * 
	 */
	protected static Vector3 transformVector(Vector3 vector,
			double homogeneousCoord, Matrix transformation) {
		double[][] homogenOriginArray = { { vector.getX() }, { vector.getY() },
				{ vector.getZ() }, { homogeneousCoord } };
		Matrix homogenOriginMatrix = new Matrix(homogenOriginArray);
		homogenOriginMatrix = transformation.times(homogenOriginMatrix);
		Vector3 transformedVector = new Vector3(homogenOriginMatrix.get(0, 0),
				homogenOriginMatrix.get(1, 0), homogenOriginMatrix.get(2, 0));
		return transformedVector;
	}
}
