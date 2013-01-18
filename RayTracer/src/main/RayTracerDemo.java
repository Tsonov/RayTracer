package main;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import structures.*;

public class RayTracerDemo {

	static final int BLUE = 0xFF;
	static final int RED = 0xFF0000;
	static final int GREEN = 0xFF00;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Scanner scanner = new Scanner("test 1 2\n ");
		// System.out.println(scanner.nextLine());
		// System.out.println(scanner.next());
		// scanner.close();
		// Process input
		// Get size
		// Get name
		// Get objects
		// Sampler -> get point
		// Camera -> from point get ray
		// RayTracer -> from ray get color (check objects)
		// Give color to image
		// Output image
		// I go pixel by pixel from the image to request a sample

		SceneFileReader reader = new SceneFileReader(
				"D:\\University and Study\\Online\\edX\\Computer graphics\\"
						+ "Homework 3\\hw3-submissionscenes\\hw3-submissionscenes\\scene6.test");
		Scene scene = reader.getSceneInfo();
		reader.close();
		int width = scene.getWidth();
		int height = scene.getHeight();
		int max = width * height;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		RayTracer tracer = new RayTracer(scene.getWorldObjects(),
				scene.getLights(), scene.getAttenuation());

		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {
				ViewScreenSample screenPoint = new ViewScreenSample(row, column);
				Ray ray = scene.getRay(screenPoint);
				Color theColor = tracer.getColor(ray);
				image.setRGB(column, row, theColor.getRGB());
			}
			double percent = (column + 1) * height * 100 / (double) max;
			System.out.println(String.format("%.2f", percent));
		}
		printImage(image);
	}

	private static void printImage(BufferedImage image) {
		// Graphics2D drawer = image.createGraphics();
		try {
			ImageIO.write(image, "png", new File("Outputs\\scene6.png"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Success");
	}

}
