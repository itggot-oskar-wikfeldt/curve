package me.hsogge.curve.world;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class GameObject {
	
	BufferedImage texture;
	protected double x;
	protected double y;
	protected Ellipse2D.Double hitbox;
	
	public GameObject() {
		
	}
	
	public Ellipse2D.Double getHitbox() {
		return hitbox;
	}
	
	
	
	public void tick() {
		
	}
	
	public void render(Graphics2D g) {
		
	}
	
	
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
}
