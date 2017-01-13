package me.hsogge.curve.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Loader {
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Loader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ImageIcon loadImageIcon(String path) {
		return new ImageIcon(Loader.class.getResource(path));

	}
}
