package me.hsogge.curve.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import me.hsogge.curve.Main;

public class World {
	List<Player> players = new ArrayList<>();
	
	public World() {
		players.add(new Player("arrows", "blue"));
		players.add(new Player("WASD", "green"));
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
					if (!otherPlayer.getDead() || otherPlayer == player)
						continue;
					if (otherPlayer.getHitbox().intersects(circleBound))
						otherPlayer.kill();
				}
				
			}
		}
	}
	
	public void tick() {
		
		for (Player player : players)
			player.tick();
		checkCollision();
		
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getCanvas().getWidth(), Main.getCanvas().getHeight());
		for (Player player : players)
			player.render(g);
	}
}
