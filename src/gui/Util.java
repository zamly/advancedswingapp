package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class Util {
	
	public static ImageIcon createIcon(String path) {
		URL url = System.class.getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: "+path);
		}
		ImageIcon icon = new ImageIcon(url);
		
		return icon;
	}

	public static Font createFont(String path) {
		URL url = System.class.getResource(path);
		if (url == null) {
			System.err.println("Unable to load Font: "+path);
		}
		
		//ImageIcon icon = new ImageIcon(url);
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException | IOException e) {
			System.err.println("Bad format in Font file/unable to read file.");
		}
		
		return font;
	}
}
