package structures;

public class Material {
	private Vector3 diffuse;
	private Vector3 ambient;
	private Vector3 specular;
	private Vector3 emission;
	private double shininess;

	/**
	 * Constructs a new Material with default properties
	 */
	public Material() {
		this.setDiffuse(new Vector3(0, 0, 0));
		this.setAmbient(new Vector3(0, 0, 0));
		this.setSpecular(new Vector3(0, 0, 0));
		this.setEmission(new Vector3(0, 0, 0));
		this.shininess = 0;
	}

	/**
	 * Constructs a new Material from an existing one
	 * 
	 * @param another
	 */
	public Material(Material another) {
		this.setDiffuse(another.diffuse);
		this.setAmbient(another.ambient);
		this.setSpecular(another.specular);
		this.setEmission(another.emission);
		this.setShininess(another.shininess);
	}

	/**
	 * Returns a value that indicates the power of a reflected light ray on this
	 * material
	 * 
	 * @return The shininess of the material
	 */
	public double getShininess() {
		return shininess;
	}

	/**
	 * Set the shininess that indicates the power of a reflected light ray on
	 * this material
	 * 
	 * @param shininess
	 *            - the value of the shininess
	 */
	public void setShininess(double shininess) {
		this.shininess = shininess;
	}

	/**
	 * Returns a value that indicates how much light does this material disperse
	 * 
	 * @return The diffuse value of the material
	 */
	public Vector3 getDiffuse() {
		return diffuse;
	}

	/**
	 * Set the diffuse that indicates how much light does this material disperse
	 * 
	 * @param diffuse
	 */
	public void setDiffuse(Vector3 diffuse) {
		this.diffuse = diffuse;
	}

	/**
	 * Returns the ambient term for this material that indicates its base color
	 * property
	 * 
	 * @return The value of the ambient term
	 */
	public Vector3 getAmbient() {
		return ambient;
	}

	/**
	 * Set the ambient term for this material that indicates its base color
	 * property
	 * 
	 * @param ambient
	 */
	public void setAmbient(Vector3 ambient) {
		this.ambient = ambient;
	}

	/**
	 * Returns a value that indicates how reflective this material is
	 * 
	 * @return The value of the specular term
	 */
	public Vector3 getSpecular() {
		return specular;
	}

	/**
	 * Set the specular term that indicates how reflective this material is
	 * 
	 * @param specular
	 */
	public void setSpecular(Vector3 specular) {
		this.specular = specular;
	}

	/**
	 * Returns the emission of the material
	 * 
	 * @return The emission value
	 */
	public Vector3 getEmission() {
		return emission;
	}

	/**
	 * Set the emission of this material
	 * 
	 * @param emission
	 */
	public void setEmission(Vector3 emission) {
		this.emission = emission;
	}
}
