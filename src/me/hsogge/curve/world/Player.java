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

public class Player extends GameObject implements Comparable<Player> {

	private double vel = 1.2;
	private double turnSpeed = 2.5;
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

	private int score = 0;

	public void win() {
		//score += 1;
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

		ax = (int) Math.round(cosRotated * width / 2 + x);
		ay = (int) Math.round(sinRotated * width / 2 + y);
		bx = (int) Math.round(cosRotated * -width / 2 + x);
		by = (int) Math.round(sinRotated * -width / 2 + y);

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

		polygons.add(new Polygon(new int[] { ax, bx, dx, cx }, new int[] { ay, by, dy, cy }, 4));

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

			if (Keyboard.isKeyDown(KeyEvent.VK_B))
				width = height = 16;
			else
				width = height = 8;
		}

		sin = Math.sin(Math.toRadians(angle));
		cos = Math.cos(Math.toRadians(angle));

		if ((int) (Math.random() * 1.5 * Main.getTickrate()) == 0)
			gapStart = 0;

		if (gapStart < 2 * width)
			gap();
		gapStart += vel;

		if (!stop)
			move();

		if (solid) {
			placePolygon();
		}

		hitbox.width = hitbox.height = width;

		hitbox.x = x-width/2;
		hitbox.y = y-height/2;

		super.tick();

	}

	public void render(Graphics2D g) {

		g.setColor(color);
		if (stop)
			g.drawLine((int) Math.round(x), (int) Math.round(y), (int) Math.round(x + cos * 20),
					(int) Math.round(y + sin * 20));
		g.fillOval((int) Math.round(x - width / 2), (int) Math.round(y - width / 2), width, width);

		for (Polygon polygon : renderPolygons)
			g.fillPolygon(polygon);

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

	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setVel(double vel) {
		this.vel = vel;
	}
	
	public double getVel() {
		return vel;
	}
	
	public void setTurnSpeed(double turnSpeed) {
		this.turnSpeed = turnSpeed;
	}
	
	@Override
	public int compareTo(Player comparestu) {
        int compareage=(comparestu).getScore();
        return compareage-this.score;

    }



}
