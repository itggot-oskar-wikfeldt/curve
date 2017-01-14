package me.hsogge.curve.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import me.hsogge.curve.Main;
import me.hsogge.curve.util.Util;

public class World {
	List<Player> players = new ArrayList<>();
	List<Item> items = new ArrayList<>();
	List<Player> alivePlayers = new ArrayList<>();
	Rectangle worldBounds;
	double startTime = 0;

	public World() {
		players.add(new Player("Blue player", "arrows", "blue"));
		players.add(new Player("Green player", "WASD", "green"));
		worldBounds = new Rectangle(0, 0, Main.getCanvas().getWidth(), Main.getCanvas().getHeight());
		newGame();
	}

	private void checkCollision() {
		for (Player player : players) {

			for (int i = 0; i < player.getPolygons().size(); i++) {
				Rectangle2D polygonBound = player.getPolygons().get(i).getBounds2D();

				// checking if the player in the loop is colliding
				if (!player.getDead() && player.getPolygons().size() - i > 10)
					if (player.getHitbox().intersects(polygonBound))
						player.kill();

				// checking the other players
				for (Player otherPlayer : players) {
					if (otherPlayer.getDead() || otherPlayer == player)
						continue;
					if (otherPlayer.getHitbox().intersects(polygonBound))
						otherPlayer.kill();
				}

			}

			if (!player.getHitbox().intersects(worldBounds))
				player.kill();
		}
	}

	private void newGame() {
		for (Player player : players)
			player.init();
		items.clear();
		startTime = Main.getTimePassed();
		System.out.println(startTime);
		gameOver = false;
		alivePlayers.clear();
		alivePlayers.addAll(players);
	}

	public boolean stopPlayers = false;

	boolean gameOver = false;
	boolean tie;
	String winner = "asd";
	double gameOverTime = 0;

	public void tick() {

		for (Player player : players) {
			if (Main.getTimePassed() - startTime < 6) {
				player.gap();
				if (Main.getTimePassed() - startTime < 3)
					player.stop();
				else
					player.go();
			} else
				player.fill();

			if (player.getDead())
				alivePlayers.remove(player);
			else {
				if (!alivePlayers.contains(player))
					alivePlayers.add(player);
				player.tick();
			}
		}

		checkCollision();

		if (alivePlayers.size() <= 1 && !gameOver) {
			gameOverTime = Main.getTimePassed();
			gameOver = true;
			if (alivePlayers.size() < 1)
				tie = true;
			else {
				tie = false;
				winner = alivePlayers.get(0).getName();
			}
		}

		if (gameOver && Main.getTimePassed() - gameOverTime > 5)
			newGame();

	}

	Font fontLarge = new Font("Arial", Font.PLAIN, 24);
	Font fontSmall = new Font("Serif", Font.PLAIN, 12);

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getCanvas().getWidth(), Main.getCanvas().getHeight());

		for (Player player : players)
			player.render(g);

		for (Item item : items)
			item.render(g);

		g.setColor(Color.WHITE);
		g.setFont(fontSmall);
		g.drawString("del: reset game", 0, 10);
		g.drawString("esc: quit", 0, 20);
		if (gameOver) {
			String string = tie ? "Tie!" : winner + " wins!";
			Util.drawCenteredString(g, string, fontLarge, Main.getCanvas().getWidth() / 2,
					Main.getCanvas().getHeight() / 2);
		}
	}
}
