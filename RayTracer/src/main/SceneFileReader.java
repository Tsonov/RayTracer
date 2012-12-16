package main;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import structures.Scene;
import structures.Vector3;
import primitives.*;

public class SceneFileReader implements Closeable {
	private Scanner scanner;
	private Vector3[] vertices;
	private int totalVertices = 0;

	public SceneFileReader(String filePath) throws IOException {
		Path fileLocation = Paths.get(filePath);
		scanner = new Scanner(fileLocation);
	}

	public Scene getSceneInfo() throws Exception {
		Scene result = new Scene();
		while (scanner.hasNext()) {
			String command = scanner.next();
			if (command.startsWith("#")) {
				// A comment - ignore the whole line to save time
				scanner.nextLine();
				continue;
			}
			handleCommand(command, result);
		}
		return result;
	}

	private void handleCommand(String command, Scene theScene)
			throws InvalidSceneFileException {
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
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			break;

		case "point":
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			break;

		case "attenuation":
			
			break;

		case "ambient":
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			break;

		case "diffuse":
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			break;

		case "specular":
			scanner.nextDouble();
			scanner.nextDouble();
			scanner.nextDouble();
			break;

		case "shininess":

			break;

		case "emission":
			break;
		default:
			throw new InvalidSceneFileException("Invalid command in file");
		}
	}

	private void handleNewTriangle(Scene theScene) throws InvalidSceneFileException {
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
		Triangle newTriangle = new Triangle(vertices[firstVertexIndex],
				vertices[secondVertexIndex], vertices[thirdVertexIndex]);
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
