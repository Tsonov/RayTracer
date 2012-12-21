package primitives;

import structures.Material;
import Jama.Matrix;

public abstract class Primitive implements Intersectable {
	protected final Matrix rayTransformation;
	private Material material;

	protected Primitive(Matrix transformation) {
		this.rayTransformation = transformation.inverse();
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Material getMaterial() {
		return this.material;
	}
	
	public Matrix getTransformation() {
		return this.rayTransformation;
	}
}
