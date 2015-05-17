package gui;
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PersonFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		
		if (f.isDirectory()) {
			return true;			
		}
		
		String name = f.getName();
		String ext = getFileExtension(name);
		
		if (ext == null) {
			return false;
		}
		
		if (ext.equals("per")) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Person database files (*.per)";
	}
	
	private String getFileExtension(String name) {
		int index = name.lastIndexOf(".");
		if(index == -1) {
			return null;
		}
		if (index == name.length()-1) {
			return null;
		}
		return name.substring(index+1, name.length());
	}

}
