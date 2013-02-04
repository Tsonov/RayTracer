package main;

import java.io.File;


import javax.swing.filechooser.FileFilter;


public class SceneFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory() == true) {
			return true;
		}
		int indexDot = f.getName().lastIndexOf('.');
		if(indexDot == -1) {
			return false;
		}
		String extension = f.getName().substring(indexDot);
		if(extension.equalsIgnoreCase(".rayScene")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getDescription() {
		return ".rayScene";
	}

}
