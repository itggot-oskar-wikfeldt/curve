package me.hsogge.curve.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import me.hsogge.curve.Main;
import me.hsogge.curve.input.Keyboard;

public class Player extends GameObject {

	private double vel = 1.5;
	private double turnSpeed = 2.7;
	private boolean solid = false;
	private boolean stop = false;
	private double angle = Math.random() * 360;
	private int leftBind;
	private int rightBind;
	private Color color;
	private String name;
	private List<Polygon> polygons = new ArrayList<>();

	public Player(String controls, int playerIndex) {

		if (playerIndex == 0) {

			leftBind = KeyEvent.VK_LEFT;
			rightBind = KeyEvent.VK_RIGHT;

			color = Color.BLUE;
			name = "Blue player";

		} else if (playerIndex == 1) {

			leftBind = KeyEvent.VK_A;
			rightBind = KeyEvent.VK_D;

			color = Color.GREEN;
			name = "Green player";

		} else if (playerIndex == 2) {

			leftBind = KeyEvent.VK_N;
			rightBind = KeyEvent.VK_M;

			color = Color.RED;
			name = "Red player";

		} else {
			System.out.println("too many players");
		}

		init();
	}

	public void init() {

		dead = false;

		polygons.clear();
		renderPolygons.clear();

		setX(Math.random() * Main.getCanvas().getWidth());
		setY(Math.random() * Main.getCanvas().getHeight());

		width = height = 8;

		hitbox = new Ellipse2D.Double(0, 0, width, width);

	}

	private boolean dead = false;

	public void kill() {
		if (solid && !dead)
			dead = true;
	}

	private void move() {
		x += cos * vel;
		y += sin * vel;
	}

	private int ax, bx, cx, dx, ay, by, cy, dy, oldCx, oldDx, oldCy, oldDy;
	private int oldTick;

	List<Polygon> renderPolygons = new ArrayList<>();

	int firstPoly = 0;

	private void placePolygon() {
		double sinRotated = Math.sin(Math.toRadians(angle + 90));
		double cosRotated = Math.cos(Math.toRadians(angle + 90));

		oldCx = ax;
		oldCy = ay;
		oldDx = bx;
		oldDy = by;

		ax = (int) Math.round(cosRotated * width / 2 + getCenterX());
		ay = (int) Math.round(sinRotated * width / 2 + getCenterY());
		bx = (int) Math.round(cosRotated * -width / 2 + getCenterX());
		by = (int) Math.round(sinRotated * -width / 2 + getCenterY());

		boolean newPoly = false;

		if (Main.getTotalTicks() - 1 == oldTick) {
			cx = oldCx;
			cy = oldCy;
			dx = oldDx;
			dy = oldDy;
		} else {
			cx = ax;
			cy = ay;
			dx = bx;
			dy = by;
			firstPoly = polygons.size();
			newPoly = true;
		}

		oldTick = Main.getTotalTicks();

		int[] xPoints = { ax, bx, dx, cx };
		int[] yPoints = { ay, by, dy, cy };

		polygons.add(new Polygon(xPoints, yPoints, 4));

		if (solid) {
			Polygon renderPolygon = new Polygon(
					new int[] { polygons.get(firstPoly).xpoints[3], polygons.get(firstPoly).ypoints[2] },
					new int[] { polygons.get(firstPoly).ypoints[3], polygons.get(firstPoly).ypoints[2] }, 2);

			for (int i = firstPoly; i < polygons.size(); i++) {
				Polygon polygon = polygons.get(i);
				renderPolygon.addPoint(polygon.xpoints[3], polygon.ypoints[3]);
				renderPolygon.addPoint(polygon.xpoints[0], polygon.ypoints[0]);
			}

			for (int i = polygons.size(); i > firstPoly; i--) {
				Polygon polygon = polygons.get(i - 1);
				renderPolygon.addPoint(polygon.xpoints[1], polygon.ypoints[1]);
				renderPolygon.addPoint(polygon.xpoints[2], polygon.ypoints[2]);
			}

			if (newPoly)
				renderPolygons.add(renderPolygon);
			else
				renderPolygons.set(renderPolygons.size() - 1, renderPolygon);
		}

	}

	public void gap() {
		solid = false;
	}

	public void stop() {
		stop = true;
	}

	public void fill() {
		solid = true;
	}

	public void go() {
		stop = false;
	}

	private double gapStart;

	private double sin;
	private double cos;

	public void tick() {

		if (!stop) {
			if (Keyboard.isKeyDown(leftBind))
				angle -= turnSpeed;

			if (Keyboard.isKeyDown(rightBind))
				angle += turnSpeed;
		}

		sin = Math.sin(Math.toRadians(angle));
		cos = Math.cos(Math.toRadians(angle));

		if ((int) (Math.random() * 2 * Main.getTickrate()) == 0)
			gapStart = 0;

		if (gapStart < 2 * width)
			gap();
		gapStart += vel;

		if (!stop)
			move();

		if (solid) {
			placePolygon();
		}

		hitbox.x = x;
		hitbox.y = y;

		super.tick();

	}

	public void render(Graphics2D g) {

		g.setColor(color);
		if (stop)
			g.drawLine((int) getCenterX(), (int) getCenterY(), (int) (getCenterX() + cos * 20),
					(int) (getCenterY() + sin * 20));
		g.fill(hitbox);

		for (Polygon polygon : renderPolygons) {
			g.fillPolygon(polygon);

		}

	}

	public void setWidth(int width) {
		this.width = width;
	}

	public List<Polygon> getPolygons() {
		return polygons;
	}

	public boolean getDead() {
		return dead;
	}

	public String getName() {
		return name;
	}

}
