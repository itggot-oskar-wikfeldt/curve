package me.hsogge.curve.world;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class GameObject {
	
	BufferedImage texture;
	protected double x = 0;
	protected double y = 0;
	protected int width;
	protected int height;
	protected Ellipse2D.Double hitbox;
	
	public GameObject() {
		
	}
	
	public Ellipse2D.Double getHitbox() {
		return hitbox;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics2D g) {
		g.drawImage(texture, (int) x, (int) y, width, height, null);
	}
	
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getCenterX() {
		return x + width / 2;
	}
	public double getCenterY() {
		return y + height / 2;
	}

	
}
