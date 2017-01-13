package me.hsogge.curve.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import me.hsogge.curve.Main;

public class World {
	List<Player> players = new ArrayList<>();
	Rectangle worldBounds;

	public World() {
		players.add(new Player("Blue player", "arrows", "blue"));
		players.add(new Player("Green player", "WASD", "green"));
		worldBounds = new Rectangle(0, 0, Main.getCanvas().getWidth(), Main.getCanvas().getHeight());
	}

	private void checkCollision() {
		for (Player player : players) {

			for (int i = 0; i < player.getCircles().size(); i++) {
				Rectangle2D circleBound = player.getCircles().get(i).getBounds2D();

				// checking if the player in the loop is colliding
				if (!player.getDead() && player.getCircles().size() - i > 10)
					if (player.getHitbox().intersects(circleBound))
						player.kill();

				// checking the other players
				for (Player otherPlayer : players) {
					if (otherPlayer.getDead() || otherPlayer == player)
						continue;
					if (otherPlayer.getHitbox().intersects(circleBound))
						otherPlayer.kill();
				}

			}
			
			if (!player.getHitbox().intersects(worldBounds))
				player.kill();
		}
	}
	
	boolean gameOver = false;
	String loser = "";
	
	public void tick() {
		
		if (!gameOver) {
			checkCollision();
		}
		for (Player player : players) {
			if (!gameOver && player.getDead()) {
				gameOver = true;
				loser = player.getName();
			}
			player.tick();
		}
		

		

	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getCanvas().getWidth(), Main.getCanvas().getHeight());
		for (Player player : players)
			player.render(g);
		if (gameOver) {
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER, "  + loser  + " loses!", Main.getCanvas().getWidth()/2, Main.getCanvas().getHeight()/2);
		}
	}
}
