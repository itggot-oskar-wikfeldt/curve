package me.hsogge.curve.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.hsogge.curve.Main;
import me.hsogge.curve.comp.HUD;
import me.hsogge.curve.world.item.Item;
import me.hsogge.curve.world.item.Speed;
public class World {
	List<Player> players = new ArrayList<>();
	List<Item> items = new ArrayList<>();
	List<Player> alivePlayers = new ArrayList<>();
	Rectangle worldBounds;
	int numOfPlayers = 2;
	double startTime = 0;
	HUD hud = new HUD(this);

	public World() {
		for (int i = 0; i < numOfPlayers; i++) {
			players.add(new Player("arrows", i));
		}
		

	
		worldBounds = new Rectangle(0, 0, Main.getCanvas().getWidth(), Main.getCanvas().getHeight());
		newGame();
		items.add(new Speed(this));
	}
	
	

	private void checkCollision() {
		for (Player player : players) {

			for (int i = 0; i < player.getPolygons().size(); i++) {

				Polygon polygon = player.getPolygons().get(i);

				// checking if the player in the loop is colliding
				if (!player.getDead() && player.getPolygons().size() - i > 20)
					if (polygon.intersects(player.getHitbox().getFrame()))
						player.kill();

				// checking the other players
				for (Player otherPlayer : players) {
					if (otherPlayer.getDead() || otherPlayer == player)
						continue;
					if (polygon.intersects(otherPlayer.getHitbox().getFrame()))
						otherPlayer.kill();
				}

			}

			if (player.getHitbox().getMinX() < worldBounds.getMinX()
					|| player.getHitbox().getMaxX() > worldBounds.getMaxX()
					|| player.getHitbox().getMinY() < worldBounds.getMinY()
					|| player.getHitbox().getMaxY() > worldBounds.getMaxY())
				player.kill();
			
			for (Item item : items) {
				if (item.getHitbox().intersects(player.getHitbox().getFrame())) {
					item.pickup(player);
				}
			}

		}
	}

	private void newGame() {
		for (Player player : players)
			player.init();
		items.clear();
		if (!(winner == null))
			winner.win();
		startTime = Main.getTimePassed();
		gameOver = false;
		alivePlayers.clear();
		alivePlayers.addAll(players);
		Collections.sort(players);
	}
	
	public void playerDied(Player player) {
		for (Player loopPlayer : players) {
			
			if (loopPlayer.getDead()) {
				continue;
			}
			loopPlayer.setScore(loopPlayer.getScore()+1);
		}
	}

	public boolean stopPlayers = false;

	boolean gameOver = false;
	boolean tie;
	Player winner;
	double gameOverTime = 0;

	public void tick() {

		for (Player player : players) {
			if (player.getDead()) {
				if (alivePlayers.contains(player)) {
					alivePlayers.remove(player);
					playerDied(player);
				}
			} else {
				if (!alivePlayers.contains(player))
					alivePlayers.add(player);
				if (Main.getTimePassed() - startTime < 6) {
					player.gap();
					if (Main.getTimePassed() - startTime < 3)
						player.stop();
					else
						player.go();
				} else
					player.fill();
				player.tick();
			}			
		}

		checkCollision();
		for (Player player : alivePlayers) {
			for (Item item : items) {
				if (item.getHitbox().intersects(player.getHitbox().getFrame())) {
					item.pickup(player);
					//items.remove(item);
				}	
			}
		}
		
		for (Item item : items) 
			item.tick();

		if (alivePlayers.size() <= 1 && !gameOver) {
			gameOverTime = Main.getTimePassed();
			gameOver = true;
			if (alivePlayers.size() < 1)
				tie = true;
			else {
				tie = false;
				winner = alivePlayers.get(0);
			}
		}

		if (gameOver && Main.getTimePassed() - gameOverTime > 5)
			newGame();
		
		System.out.println(items.size());
		
		

	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getCanvas().getWidth(), Main.getCanvas().getHeight());

		for (Player player : players)
			player.render(g);

		for (Item item : items)
			item.render(g);

		g.setColor(Color.WHITE);
		hud.render(g);
		if (gameOver)
			hud.drawResult(winner, tie, g);

	}
	
	public List<Player> getPlayers() {
		return players;
	}

}
