package primitives;

import structures.Box;
import structures.Material;
import structures.Vector3;
import Jama.Matrix;

public abstract class Primitive implements Intersectable {
	protected final Matrix transformationToWorldCoordiantes;
	protected Box boundingBox;
	private Material material;

	protected Primitive(Matrix transformation) {
		this.transformationToWorldCoordiantes = transformation;
	}

	protected Primitive(Box boundingBox) {
		this.transformationToWorldCoordiantes = null;
		this.boundingBox = boundingBox;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}

	public Material getMaterial() {
		return this.material;
	}

	public Matrix getTransformation() {
		return this.transformationToWorldCoordiantes;
	}
	
	public Box getBoundingBox() {
		return this.boundingBox;
	}

	public static Vector3 transformVector(Vector3 vector,
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
