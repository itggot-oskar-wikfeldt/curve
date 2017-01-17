package me.hsogge.curve.world.item;

import java.awt.image.BufferedImage;

import me.hsogge.curve.Main;
import me.hsogge.curve.world.GameObject;
import me.hsogge.curve.world.Player;

public class Item extends GameObject {

	private String type;

	public Item(BufferedImage texture) {
		
		setX((int) (Math.random() * Main.getCanvas().getWidth()));
		setY((int) (Math.random() * Main.getCanvas().getHeight()));

		width = texture.getWidth();
		height = texture.getHeight();
		this.texture = texture;
	}
	
	public void pickup(Player player) {
		
	}

	public String getType() {
		return type;
	}
}
