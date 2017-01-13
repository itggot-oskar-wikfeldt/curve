package me.hsogge.curve;

import java.awt.image.BufferedImage;

import me.hsogge.curve.util.Loader;

public class Assets {
	
	// World
	public static final BufferedImage TILE_GREEN = Loader.loadImage("/tiles/tile_green.png");
	public static final BufferedImage TILE_BLUE = Loader.loadImage("/tiles/tile_blue.png");
	public static final BufferedImage TILE_YELLOW = Loader.loadImage("/tiles/tile_yellow.png");
	public static final BufferedImage[] TILE_DOOR = {Loader.loadImage("/tiles/door_closed.png"), Loader.loadImage("/tiles/door_open.png"), Loader.loadImage("/tiles/door_open_right.png")};
	public static final BufferedImage GRADIENT = Loader.loadImage("/gradient.png");
	
	// Entities
	public static final BufferedImage[][] ENTITY_PLAYER = new BufferedImage[2][4];
	
	public static final BufferedImage TEXTURE_PLAYERHAND = Loader.loadImage("/entities/player/hand.png");
	public static final BufferedImage TEXTURE_ZOMBIEHAND = Loader.loadImage("/entities/enemy/hand.png");
	public static final BufferedImage[][] ENTITY_ZOMBIE = new BufferedImage[2][4];
	
	// Items
	public static final BufferedImage ITEM_SWORD = Loader.loadImage("/items/sword.png");
	public static final BufferedImage ITEM_HAMMER = Loader.loadImage("/items/hammer.png");
	
	// Misc
	public static final BufferedImage CURSOR = Loader.loadImage("/cursor.png");
	public static final BufferedImage TEXTURE_TRANSPARENT = Loader.loadImage("/transparentcursor.png");
	public static final BufferedImage HOTBAR = Loader.loadImage("/hotbar.png");
	public static final BufferedImage SELECTED = Loader.loadImage("/selected.png");
	
		
	
	
	
	
	static {
		
		for (int i = 0; i < 4; i++) {
			ENTITY_PLAYER[0][i] = Loader.loadImage("/entities/player/standing/" + i + ".png");
			ENTITY_PLAYER[1][i] = Loader.loadImage("/entities/player/crouching/" + i + ".png");
			
			ENTITY_ZOMBIE[0][i] = Loader.loadImage("/entities/enemy/standing/" + i + ".png");
			ENTITY_ZOMBIE[1][i] = Loader.loadImage("/entities/enemy/crouching/" + i + ".png");
		}
	}

	


	
}

