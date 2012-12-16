package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import structures.*;
import primitives.*;

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
		// // Output image
		// int width = 256;
		// int height = 192;

		// Camera camera = new Camera(new Vector3(0, 0, 4), new Vector3(0, 0,
		// 0), new Vector3(0, 1, 0), 30, width, height);
		// Camera camera = new Camera(new Vector3(0, -3, 3), new Vector3(0, 0,
		// 0),
		// new Vector3(0, 1, 0), 30, width, height);
		// Camera camera = new Camera(new Vector3(-4, 0, 1),
		// new Vector3(0, 0, 1), new Vector3(0, 0, 1), 45, width, height);
		// Camera camera = new Camera(new Vector3(-4, -4, 4),
		// new Vector3(1, 0, 0), new Vector3(0, 1, 0), 30, width, height);
		// scene 2
		// Camera camera = new Camera(new Vector3(-2, -2, -2),
		// // new Vector3(0, 0, 0), new Vector3(-1, -1, 2), 60, width, height);
		// Camera camera = new Camera(new Vector3(-2, -2, 2),
		// new Vector3(0, 0, 0), new Vector3(1, 1, 2), 60, width, height);
		// Camera camera = new Camera(new Vector3(2, 2, 2),
		// // new Vector3(0, 0, 0), new Vector3(-1, -1, 2), 60, width, height);
		// int max = width * height;
		// RayTracer tracer = new RayTracer();
		// Vector3[] vertices = new Vector3[] { new Vector3(-1, -1, -1),
		// new Vector3(+1, -1, -1), new Vector3(+1, +1, -1),
		// new Vector3(-1, +1, -1), new Vector3(-1, -1, +1),
		// new Vector3(+1, -1, +1), new Vector3(+1, +1, +1),
		// new Vector3(-1, +1, +1), };
		// Color red = new Color(255, 0, 0);
		// Color blue = new Color(0, 0, 255);
		// Color yellow = new Color(255, 255, 0);
		// Color cyan = new Color(0, 255, 255);
		// Color purple = new Color(255, 0, 255);
		// Color otherCol = new Color(120, 120, 50);
		// tracer.addWorldObject(new Triangle(vertices[0], vertices[1],
		// vertices[5], blue));
		// tracer.addWorldObject(new Triangle(vertices[0], vertices[5],
		// vertices[4], blue));
		// tracer.addWorldObject(new Triangle(vertices[3], vertices[7],
		// vertices[6], red));
		// tracer.addWorldObject(new Triangle(vertices[3], vertices[6],
		// vertices[2], red));
		// tracer.addWorldObject(new Triangle(vertices[1], vertices[2],
		// vertices[6], yellow));
		// tracer.addWorldObject(new Triangle(vertices[1], vertices[6],
		// vertices[5], yellow));
		// tracer.addWorldObject(new Triangle(vertices[0], vertices[7],
		// vertices[3], cyan));
		// tracer.addWorldObject(new Triangle(vertices[0], vertices[4],
		// vertices[7], cyan));
		// tracer.addWorldObject(new Triangle(vertices[0], vertices[3],
		// vertices[2], purple));
		// tracer.addWorldObject(new Triangle(vertices[0], vertices[2],
		// vertices[1], purple));
		// tracer.addWorldObject(new Triangle(vertices[4], vertices[5],
		// vertices[6], otherCol));
		// tracer.addWorldObject(new Triangle(vertices[4], vertices[6],
		// vertices[7], otherCol));
		//
		// tracer.addWorldObject(new Sphere(new Vector3(1, 0, 0), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-0.5, 1, -0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(0.5, 1, 0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(0, 0, 1), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-0.5, -0.5, 1), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(0.5, 0.5, 1), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-1, -0.5, -0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-1, -0.5, +0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-1, +0.5, +0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-1, +0.5, -0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-0.5, -1, -0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-0.5, -1, +0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(+0.5, -1, +0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(+0.5, -1, -0.5), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(0, -1, 0), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-0.5, -0.5, -1), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-0.5, 0, -1), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(-0.5, +0.5, -1), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(+0.5, -0.5, -1), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(+0.5, 0, -1), 0.15));
		// tracer.addWorldObject(new Sphere(new Vector3(+0.5, 0.5, -1), 0.15));
		// I go pixel by pixel from the image to request a sample

		SceneFileReader reader = new SceneFileReader(
				"D:\\University and Study\\Online\\edX\\Computer graphics\\"
						+ "Homework 3\\test scenes\\testscenes\\scene1.test");
		Scene scene = reader.getSceneInfo();
		int width = scene.getWidth();
		int height = scene.getHeight();
		int max = width * height;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		RayTracer tracer = new RayTracer(scene.getWorldObjects());
		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {
				ViewScreenSample screenPoint = new ViewScreenSample(row, column);
				Ray ray = scene.getRay(screenPoint);
				Color theColor = tracer.getColor(ray);
				image.setRGB(column, row, theColor.getRGB());
				System.out.println((column + 1) * (row + 1) * 100
						/ (double) max);
			}
		}
		printImage(image);
	}

	private static void printImage(BufferedImage image) {
		// Graphics2D drawer = image.createGraphics();
		try {
			ImageIO.write(image, "png", new File("testscene.png"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Success");
	}

}
