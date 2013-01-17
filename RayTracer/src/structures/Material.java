package structures;

public class Material {
	private Vector3 diffuse;
	private Vector3 ambient;
	private Vector3 specular;
	private Vector3 emission;
	private double shininess;

	public Material() {
		this.setDiffuse(new Vector3(0, 0, 0));
		this.setAmbient(new Vector3(0, 0, 0));
		this.setSpecular(new Vector3(0, 0, 0));
		this.setEmission(new Vector3(0, 0, 0));
		this.shininess = 0;
	}
	
	public Material(Material another) {
		this.setDiffuse(another.diffuse);
		this.setAmbient(another.ambient);
		this.setSpecular(another.specular);
		this.setEmission(another.emission);
		this.setShininess(another.shininess);
	}
	
	public double getShininess() {
		return shininess;
	}

	public void setShininess(double shininess) {
		this.shininess = shininess;
	}
	
	public Vector3 getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Vector3 diffuse) {
		this.diffuse = diffuse;
	}

	public Vector3 getAmbient() {
		return ambient;
	}

	public void setAmbient(Vector3 ambient) {
		this.ambient = ambient;
	}

	public Vector3 getSpecular() {
		return specular;
	}

	public void setSpecular(Vector3 specular) {
		this.specular = specular;
	}

	public Vector3 getEmission() {
		return emission;
	}

	public void setEmission(Vector3 emission) {
		this.emission = emission;
	}
}
