package me.hsogge.curve.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import me.hsogge.curve.Main;
import me.hsogge.curve.input.Keyboard;

public class Player extends GameObject {

	private double vel = 1.5;
	private double turnSpeed = 2.7;
	private boolean drawing = true;
	private int angle = 0;
	private int width = 10;
	private int leftBind;
	private int rightBind;
	private List<Ellipse2D.Double> circles = new ArrayList<>();

	public Player(String controls) {
		super();
		
		hitbox = new Ellipse2D.Double(0, 0, width, width);
		
		if (controls == "arrows") {
			leftBind = KeyEvent.VK_LEFT;
			rightBind = KeyEvent.VK_RIGHT;
		} else if (controls == "WASD") {
			leftBind = KeyEvent.VK_A;
			rightBind = KeyEvent.VK_D;
		} else {
			System.out.println("invalid control scheme");
		}
	}
	
	private boolean dead = false;
	
	public void kill() {
		if (drawing)
			dead = true;
	}

	private void move() {
		x += Math.cos(Math.toRadians(angle)) * vel;
		y += Math.sin(Math.toRadians(angle)) * vel;
	}

	private double gapStartTime;

	public void tick() {
		dead = false;

		if (Keyboard.isKeyDown(leftBind))
			angle -= turnSpeed;

		if (Keyboard.isKeyDown(rightBind))
			angle += turnSpeed;

		if ((int) (Math.random() * 2 * Main.getTickrate()) == 0)
			gapStartTime = Main.getTimePassed();

		if (Main.getTimePassed() - gapStartTime < 0.3)
			drawing = false;
		else
			drawing = true;

		move();

		if (drawing)
			circles.add(new Ellipse2D.Double(x, y, width, width));
		
		hitbox.x = x;
		hitbox.y = y;

		
		super.tick();
		
		
	}

	public void render(Graphics2D g) {
		if (dead)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLUE);

		g.fill(hitbox);

		for (Ellipse2D.Double circle : circles)
			g.fill(circle);

	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public List<Ellipse2D.Double> getCircles() {
		return circles;
	}
	

}
