package me.hsogge.curve.world;

import me.hsogge.curve.Assets;
import me.hsogge.curve.Main;

public class Item extends GameObject {

	private String type;
	
	public void pickup(Player player) {
		
	}

	public Item() {
		
		setX((int) (Math.random() * Main.getCanvas().getWidth()));
		setY((int) (Math.random() * Main.getCanvas().getHeight()));
		
		int rand = (int) (Math.random() * 3);

		if (rand == 0) {
			type = "speed";
			texture = Assets.SPEED;
		} else {
			texture = Assets.SPEED;
		}
		width = texture.getWidth();
		height = texture.getHeight();
	}

	public String getType() {
		return type;
	}
}
