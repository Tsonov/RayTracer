package main;

import java.awt.Container;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import main.SceneFileReader.InvalidSceneFileException;
import java.awt.image.BufferedImage;

public class RayTracerMain {

	JFrame frmRaytracerSceneSelector;
	private final Action openSceneAction = new OpenSceneAction();
	private final Action exitAction = new ExitAction();
	private final Action action = new HelpSceneFile();
	private JLabel imageLabel;
	private JButton generateButton;
	private File currentDirectory = new File(
			"D:\\Programming\\Repositories\\RayTracer\\RayTracer\\SampleScenes");
	private BufferedImage generatedImage = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				RayTracerMain window;
				try {
					window = new RayTracerMain();
					window.frmRaytracerSceneSelector.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RayTracerMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frmRaytracerSceneSelector = new JFrame();
		frmRaytracerSceneSelector.setResizable(false);
		frmRaytracerSceneSelector.setTitle("RayTracer Scene Selector");
		frmRaytracerSceneSelector.setBounds(100, 100, 802, 474);
		frmRaytracerSceneSelector
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmRaytracerSceneSelector.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpenSceneFile = new JMenuItem("Open scene file");
		mntmOpenSceneFile.setAction(openSceneAction);
		mnFile.add(mntmOpenSceneFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAction(exitAction);
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmSceneFileDescription = new JMenuItem(
				"Scene file description");
		mntmSceneFileDescription.setAction(action);
		mnHelp.add(mntmSceneFileDescription);
	}

	@SuppressWarnings("serial")
	private class OpenSceneAction extends AbstractAction {
		public OpenSceneAction() {
			putValue(NAME, "Open scene");
			putValue(SHORT_DESCRIPTION, "Open a scene description file");
		}

		public void actionPerformed(ActionEvent e) {

			frmRaytracerSceneSelector.getContentPane().removeAll();
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setFileFilter(new SceneFileFilter());
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (currentDirectory != null
					&& currentDirectory.isDirectory() == true) {
				fileChooser.setCurrentDirectory(currentDirectory);
			}

			printProcessing();
			int choice = fileChooser
					.showOpenDialog(RayTracerMain.this.frmRaytracerSceneSelector);
			if (choice == JFileChooser.APPROVE_OPTION) {
				Container contentPane = frmRaytracerSceneSelector
						.getContentPane();
				contentPane.removeAll();
				final File chosenFile = fileChooser.getSelectedFile();
				currentDirectory = chosenFile.getParentFile();
				final RayTracerImageGenerator imageGenerator = new RayTracerImageGenerator();
				try {
					generatedImage = imageGenerator.generateImage(chosenFile
							.getPath());
				} catch (IOException | InvalidSceneFileException e1) {
					frmRaytracerSceneSelector.getContentPane().removeAll();
					frmRaytracerSceneSelector.getContentPane().add(
							new JLabel("Invalid command was found in file"
									+ e1.getMessage()));
				}
				if (imageLabel != null) {
					contentPane.remove(imageLabel);
				}
				JLabel imageLabel = new JLabel(new ImageIcon(generatedImage));
				frmRaytracerSceneSelector.setBounds(100, 100,
						generatedImage.getWidth() + 10,
						generatedImage.getHeight() + 50);
				contentPane.add(imageLabel, BorderLayout.CENTER);
				generateButton = new JButton();
				generateButton.setAction(new GenerateAction(chosenFile));
				contentPane.add(generateButton, BorderLayout.SOUTH);
				contentPane.revalidate();
			} else {
				frmRaytracerSceneSelector.getContentPane().removeAll();
				frmRaytracerSceneSelector.getContentPane().revalidate();
				frmRaytracerSceneSelector.getContentPane().repaint();
			}
		}

		private void printProcessing() {
			JLabel processing = new JLabel(
					"Processing the scene, please wait...");
			frmRaytracerSceneSelector.getContentPane().add(processing,
					BorderLayout.NORTH);
			frmRaytracerSceneSelector.getContentPane().revalidate();
			frmRaytracerSceneSelector.getContentPane().repaint();
		}
	}

	@SuppressWarnings("serial")
	private class ExitAction extends AbstractAction {
		public ExitAction() {
			putValue(NAME, "Exit");
			putValue(SHORT_DESCRIPTION, "Exit the application");
		}

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	@SuppressWarnings("serial")
	private class HelpSceneFile extends AbstractAction {
		public HelpSceneFile() {
			putValue(NAME, "Scene file description");
			putValue(SHORT_DESCRIPTION,
					"Information how to specify a scene file");
		}

		public void actionPerformed(ActionEvent e) {
			JFrame helpFrame = new JFrame();
			JEditorPane edPane = new JEditorPane();
			edPane.setContentType("text/html");
			edPane.setEditable(false);
			edPane.setText("<html><h2>Scene file description: </h2>"
					+ "<p> The input file consists of a sequence of lines, each of which has a command.  The lines have the following form:</p>"
					+ "<p></p><ul><li><b># comments</b>  This is a line of comments.</li>"
					+ "<li><b>Blank line</b> The input file can have blank lines that will be ignored.</li>"
					+ "<li><b>command parameter1 parameter2 ...</b> The first part of the line is always the command.  Based on what the command is, it has "
					+ "certain parameters which will be parsed appropriately.  </li></ul>"
					+ "<p>The various commands are:</p>"
					+ "<h3>General</h3>"
					+ "<ul><li><b>size width height</b>: The size command must be the first"
					+ "command of the file, which controls the image size.</li>"
					+ "<li><b>maxdepth depth</b>: The maximum depth (number of bounces) for a ray(or a default value will be used).</li>"
					+ "<h3>Camera</h3>"
					+ "<p>The camera is specified as follows.  In general, there should be onlyone camera specification in the input file"
					+ "Otherwise the last defined camera will be used.</p>"
					+ "<ul><li><b>camera lookfromx lookfromy lookfromz lookatx lookaty lookatz upx"
					+ " upy upz fov</b> specifies the camera; lookfrom is where the 'eye' is, "
					+ "lookat is the point that the eye is looking at and the up vector shows the orientation of the camera.</ul>"
					+ "<h3>Geometry</h3>"
					+ "<p> The current raytracer supports only triangles and spheres(and their transformed shapes)"
					+ "The shape commands are as follows:</p>"
					+ "<ul><li><b>sphere x y z radius</b> Defines a sphere with a given position in the world "
					+ "and radius. </li>"
					+ "<li><b>maxverts number</b> Defines a maximum number of vertices for"
					+ "later triangle specifications.  It must be set before vertices are defined. </li>"
					+ "<li><b>vertex x y z</b> Defines a vertex at the given location."
					+ "  The vertex is put into a pile, starting to be numbered at 0.  </li>"
					+ "<li><b>tri v1 v2 v3</b> Create a triangle out of the vertices "
					+ "involved (which have previously been specified with the vertex command)."
					+ "The vertices are assumed to be specified in counter-clockwise order. </li></ul> "
					+ "<h3>Transformations</h3> <p> "
					+ "The different transformations that can be applied to an object. The raytracer uses the OpenGL convention "
					+ "where you must push/pop matricies onto the stack to generate the matrix for the current object(which is applied automatically)."
					+ "<ul><li><b>translate x y z</b> A translation by a 3-vector. </li>"
					+ "<li><b>rotate x y z angle</b> A rotation by angle (in degrees) about the given axis.</li>"
					+ "<li><b>scale x y z</b> Scale by the corresponding amount in each axis (a non-uniform scaling). </li>"
					+ "<li><b>pushTransform</b> Push the current modeling transform on the stack as in OpenGL."
					+ "<li><b>popTransform</b>  Pop the current transform from the stack as in OpenGL. The sequence of popTransform and pushTransform can be used "
					+ "if desired before every primitive to reset the transformation.   </li></ul>"
					+ "<h3>Lights</h3>"
					+ "<ul><li><b>directional x y z r g b</b> The direction to a directional light source, and the color, as in OpenGL.</li>"
					+ "<li><b>point x y z r g b</b> The location of a point source and the color, as in OpenGL. </li>"
					+ "<li><b>attenuation const linear quadratic</b> Sets the constant, linear and quadratic attenuations (default 1,0,0) as in OpenGL."
					+ "By default there is no attenuation (the constant term is 1, linear and quadratic are 0).</li>"
					+ "<li><b>ambient r g b</b> The global ambient color to be added for each object (default will be used if not specified). </li></ul>"
					+ "<h3>Materials</h3>"
					+ "<ul><li><b>diffuse r g b</b> specifies the diffuse color of the surface. </li>"
					+ "<li><b>specular r g b</b> specifies the specular color of the surface. </li>"
					+ "<li><b>shininess s</b> specifies the shininess of the surface. </li>"
					+ "<li><b>emission r g b</b> gives the emissive color of the surface. </li>"
					+ "</ul></html>");
			JScrollPane scrollPane = new JScrollPane(edPane);
			helpFrame.getContentPane().add(scrollPane);
			helpFrame.setBounds(200, 200, 800, 640);
			helpFrame.setTitle("Scene file description");
			helpFrame.setVisible(true);
		}
	}

	@SuppressWarnings("serial")
	private class GenerateAction extends AbstractAction {

		private File sceneFile;

		public GenerateAction(File sceneFile) {
			putValue(NAME, "Generate png image");
			putValue(SHORT_DESCRIPTION,
					"Generate a png image in the same folder as the scene file");
			this.sceneFile = sceneFile;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				File output = new File(sceneFile.getPath().substring(0,
						sceneFile.getPath().lastIndexOf('.'))
						+ ".png");
				ImageIO.write(generatedImage, "png", output);
				frmRaytracerSceneSelector.getContentPane().add(
						new JLabel("Successfully exported to a png file!"),
						BorderLayout.NORTH);
				frmRaytracerSceneSelector.getContentPane().revalidate();
				frmRaytracerSceneSelector.getContentPane().repaint();
			} catch (Exception ex) {

			}
		}
	}
}
