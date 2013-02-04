package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import main.SceneFileReader.InvalidSceneFileException;
import structures.*;

public class RayTracerImageGenerator {
	private double progress = 0;

	/**
	 */
	public BufferedImage generateImage(String scenePath) throws IOException,
			InvalidSceneFileException {
		SceneFileReader reader = null;
		Scene scene;
		try {
			reader = new SceneFileReader(scenePath);
			scene = reader.getSceneInfo();
		} catch (Exception e) {
			throw e;
		} finally {
			reader.close();
		}
		int width = scene.getWidth();
		int height = scene.getHeight();
		int max = width * height;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		RayTracer tracer = new RayTracer(scene.getWorldObjects(),
				scene.getLights(), scene.getAttenuation(), scene.getMaxDepth());

		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {
				ViewScreenSample screenPoint = new ViewScreenSample(row, column);
				Ray ray = scene.getRay(screenPoint);
				Color theColor = tracer.getColor(ray);
				image.setRGB(column, row, theColor.getRGB());
			}
			progress = (column + 1) * height * 100 / (double) max;
		}

		return image;
	}

	public double getProgress() {
		return this.progress;
	}

}
