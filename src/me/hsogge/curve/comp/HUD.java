package me.hsogge.curve.comp;

import java.awt.Font;
import java.awt.Graphics2D;

import me.hsogge.curve.Main;
import me.hsogge.curve.util.Util;
import me.hsogge.curve.world.Player;
import me.hsogge.curve.world.World;

public class HUD {
	World world;
	public HUD(World world) {
		this.world = world;
	}
	

	Font fontLarge = new Font("Arial", Font.BOLD, 24);
	Font fontSmall = new Font("Arial", Font.PLAIN, 12);
	
	public void drawResult(Player winner, boolean tie, Graphics2D g) {
		
		String string = tie ? "Tie" : winner.getName() + " wins";
		Util.drawCenteredString(g, string, fontLarge, Main.getCanvas().getWidth() / 2,
				Main.getCanvas().getHeight() / 2);
	}
	
	public void render(Graphics2D g) {
		g.setFont(fontSmall);
		g.drawString("del: reset game", 0, 10);
		g.drawString("esc: quit", 0, 20);
		
		//g.drawString("TPS: " + Main.getTPS(), 300, 10);
		//g.drawString("FPS: " + Main.getFPS(), 300, 20);
		
		for (int i = 0; i < world.getPlayers().size(); i++) {
			Player player = world.getPlayers().get(i);
			String string = player.getName() + ": " + player.getScore();
			g.drawString(string, 0, 50+i*20);
		}

	}
}
