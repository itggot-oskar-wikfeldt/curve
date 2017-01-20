package me.hsogge.curve.world.item;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import me.hsogge.curve.Main;
import me.hsogge.curve.world.GameObject;
import me.hsogge.curve.world.Player;
import me.hsogge.curve.world.World;

public class Item extends GameObject {

	protected World world;

	public Item(BufferedImage texture, World world) {
		
		setX((int) (Math.random() * Main.getCanvas().getWidth()));
		setY((int) (Math.random() * Main.getCanvas().getHeight()));

		width = texture.getWidth();
		height = texture.getHeight();
		this.texture = texture;
		this.world = world;
		
		hitbox = new Ellipse2D.Double(x-width/2, y-height/2, width, height);
	}
	
	public void pickup(Player player) {
		
	}
	
	

}
