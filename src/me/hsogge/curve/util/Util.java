package me.hsogge.curve.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Util {
	public static void drawCenteredString(Graphics g, String text, Font font, int xPos, int yPos) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = xPos - metrics.stringWidth(text) / 2;
	    int y = yPos - metrics.getHeight() / 2 + metrics.getAscent();
	    g.setFont(font);
	    g.drawString(text, x, y);
	}
	

}
