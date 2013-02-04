package main;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;

import lights.Light;
import structures.Color;
import structures.Material;
import structures.Scene;
import structures.Transformations;
import structures.Vector3;
import primitives.*;
import Jama.Matrix;

public class SceneFileReader implements Closeable {
	private Scanner scanner;
	private Vector3[] vertices;
	private int totalVertices = 0;
	private Material currentMaterialProperties;
	private Stack<Matrix> transformationStack;

	/**
	 * Create a new Scene from a given .rayscene file
	 * 
	 * @param filePath
	 *            - the path to the file
	 * @throws IOException
	 *             - when the file is invalid
	 */
	public SceneFileReader(String filePath) throws IOException {
		Path fileLocation = Paths.get(filePath);
		scanner = new Scanner(fileLocation);
	}

	/**
	 * Generate the scene from the loaded .rayscene file
	 * 
	 * @return The scene defined in the file
	 * @throws InvalidSceneFileException
	 *             - when the scene file is corrupted
	 */
	public Scene getSceneInfo() throws InvalidSceneFileException {
		Scene result = new Scene();
		currentMaterialProperties = new Material();
		this.transformationStack = new Stack<>();
		Matrix identity = Transformations.getIdentityMatrix();
		transformationStack.push(identity);

		while (scanner.hasNext()) {
			String command = scanner.next();
			if (command.startsWith("#")) {
				// A comment - ignore the whole line to save time
				scanner.nextLine();
			} else {
				handleCommand(command, result);
			}
		}
		return result;
	}

	private void handleCommand(String command, Scene theScene)
			throws InvalidSceneFileException {
		double red, green, blue;
		switch (command) {
		case "size":
			int width = scanner.nextInt();
			int height = scanner.nextInt();
			theScene.setWidth(width);
			theScene.setHeight(height);
			break;
		case "camera":
			handleCamera(theScene);
			break;
		case "maxverts":
			if (this.vertices != null) {
				throw new IllegalStateException(
						"You can define the number of vertices only once!");
			}
			int maxAmountVertices = scanner.nextInt();
			this.vertices = new Vector3[maxAmountVertices];
			break;
		case "vertex":
			handleNewVertex();
			break;

		case "tri":
			handleNewTriangle(theScene);
			break;

		case "directional":
			double dirLightX = scanner.nextDouble();
			double dirLightY = scanner.nextDouble();
			double dirLightZ = scanner.nextDouble();
			red = scanner.nextDouble();
			green = scanner.nextDouble();
			blue = scanner.nextDouble();
			Light dirLight = new Light(new Vector3(dirLightX, dirLightY,
					dirLightZ), new Color(red, green, blue), true);
			theScene.addNewLightSource(dirLight);
			break;

		case "point":
			double pointLightX = scanner.nextDouble();
			double pointLightY = scanner.nextDouble();
			double pointLightZ = scanner.nextDouble();
			red = scanner.nextDouble();
			green = scanner.nextDouble();
			blue = scanner.nextDouble();
			Light pointLight = new Light(new Vector3(pointLightX, pointLightY,
					pointLightZ), new Color(red, green, blue), false);
			theScene.addNewLightSource(pointLight);
			break;

		case "attenuation":
			double constant = scanner.nextDouble();
			double linear = scanner.nextDouble();
			double quadratic = scanner.nextDouble();
			theScene.setAttenuation(new Vector3(constant, linear, quadratic));
			break;

		case "pushTransform":
			Matrix newTransform = transformationStack.peek().copy();
			transformationStack.push(newTransform);
			break;

		case "popTransform":
			this.transformationStack.pop();
			break;

		case "scale":
			double scaleX = scanner.nextDouble();
			double scaleY = scanner.nextDouble();
			double scaleZ = scanner.nextDouble();
			Matrix scaleMatrix = Transformations.getScaleMatrix(new Vector3(
					scaleX, scaleY, scaleZ));
			applyTransformationToStack(scaleMatrix);
			break;
		case "translate":
			double tranX = scanner.nextDouble();
			double tranY = scanner.nextDouble();
			double tranZ = scanner.nextDouble();
			Matrix translateMatrix = Transformations
					.getTranslateMatrix(new Vector3(tranX, tranY, tranZ));
			applyTransformationToStack(translateMatrix);
			break;
		case "rotate":
			double rotX = scanner.nextDouble();
			double rotY = scanner.nextDouble();
			double rotZ = scanner.nextDouble();
			double angleDegrees = scanner.nextDouble();
			Vector3 axis = new Vector3(rotX, rotY, rotZ);
			Matrix rotationMatrix = Transformations.getRotationMatrix(
					angleDegrees, axis);
			applyTransformationToStack(rotationMatrix);
			break;
		case "ambient":
			red = scanner.nextDouble();
			green = scanner.nextDouble();
			blue = scanner.nextDouble();
			Vector3 newAmbient = new Vector3(red, green, blue);
			this.currentMaterialProperties.setAmbient(newAmbient);
			break;

		case "diffuse":
			red = scanner.nextDouble();
			green = scanner.nextDouble();
			blue = scanner.nextDouble();
			Vector3 newDiffuse = new Vector3(red, green, blue);
			this.currentMaterialProperties.setDiffuse(newDiffuse);
			break;

		case "specular":
			red = scanner.nextDouble();
			green = scanner.nextDouble();
			blue = scanner.nextDouble();
			Vector3 newSpecular = new Vector3(red, green, blue);
			this.currentMaterialProperties.setSpecular(newSpecular);
			break;

		case "shininess":
			double shininess = scanner.nextDouble();
			this.currentMaterialProperties.setShininess(shininess);
			break;

		case "emission":
			red = scanner.nextDouble();
			green = scanner.nextDouble();
			blue = scanner.nextDouble();
			Vector3 newEmission = new Vector3(red, green, blue);
			this.currentMaterialProperties.setEmission(newEmission);
			break;

		case "sphere":
			handleSphere(theScene);
			break;

		case "output":
			String sceneName = scanner.next();
			theScene.setSceneName(sceneName);
			break;
		case "maxdepth":
			int maxDepth = scanner.nextInt();
			theScene.setMaxDepth(maxDepth);
			break;
		default:
			throw new InvalidSceneFileException("Invalid command in file: "
					+ command);
		}
	}

	private void applyTransformationToStack(Matrix transformation) {
		Matrix currentTransformMatrix = transformationStack.pop();
		currentTransformMatrix = currentTransformMatrix.times(transformation);
		transformationStack.push(currentTransformMatrix);
	}

	private void handleSphere(Scene theScene) {
		double centX = scanner.nextDouble();
		double centY = scanner.nextDouble();
		double centZ = scanner.nextDouble();
		double radius = scanner.nextDouble();
		Matrix transform = transformationStack.peek();
		Sphere newSphere = new Sphere(new Vector3(centX, centY, centZ), radius,
				transform);
		newSphere.setMaterial(new Material(currentMaterialProperties));
		theScene.addWorldObject(newSphere);
	}

	private void handleNewTriangle(Scene theScene)
			throws InvalidSceneFileException {
		int firstVertexIndex = scanner.nextInt();
		int secondVertexIndex = scanner.nextInt();
		int thirdVertexIndex = scanner.nextInt();
		if (firstVertexIndex < 0 || firstVertexIndex >= this.vertices.length
				|| secondVertexIndex < 0
				|| secondVertexIndex >= this.vertices.length
				|| thirdVertexIndex < 0
				|| thirdVertexIndex >= this.vertices.length) {
			throw new InvalidSceneFileException(
					"Invalid vertex index for a triangle detected");
		}

		Matrix transform = transformationStack.peek();
		Triangle newTriangle = new Triangle(vertices[firstVertexIndex],
				vertices[secondVertexIndex], vertices[thirdVertexIndex],
				transform);
		newTriangle.setMaterial(new Material(currentMaterialProperties));
		theScene.addWorldObject(newTriangle);
	}

	private void handleNewVertex() {
		if (this.vertices == null) {
			throw new IllegalStateException(
					"Maximum number of available vertices must be defined before any vertices are added!");
		}
		if (this.totalVertices == this.vertices.length) {
			throw new IllegalStateException(
					"Maximum number of available vertices exceeded!");
		}
		double x = scanner.nextDouble();
		double y = scanner.nextDouble();
		double z = scanner.nextDouble();
		Vector3 vertex = new Vector3(x, y, z);
		this.vertices[this.totalVertices] = vertex;
		this.totalVertices++;
	}

	private void handleCamera(Scene theScene) {
		double lookFromX = scanner.nextDouble();
		double lookFromY = scanner.nextDouble();
		double lookFromZ = scanner.nextDouble();
		double lookAtX = scanner.nextDouble();
		double lookAtY = scanner.nextDouble();
		double lookAtZ = scanner.nextDouble();
		double upX = scanner.nextDouble();
		double upY = scanner.nextDouble();
		double upZ = scanner.nextDouble();
		double fieldViewY = scanner.nextDouble();
		theScene.setCameraParameters(lookFromX, lookFromY, lookFromZ, lookAtX,
				lookAtY, lookAtZ, upX, upY, upZ, fieldViewY);
	}

	@Override
	public void close() throws IOException {
		scanner.close();
	}

	@SuppressWarnings("serial")
	public class InvalidSceneFileException extends Exception {

		public final Exception InnerException;

		public InvalidSceneFileException(Exception exception) {
			this.InnerException = exception;
		}

		public InvalidSceneFileException(String errorMessage) {
			super(errorMessage);
			this.InnerException = null;
		}
	}

}
