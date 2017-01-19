package me.hsogge.curve.world.item;

import me.hsogge.curve.Assets;
import me.hsogge.curve.world.Player;
import me.hsogge.curve.world.World;

public class Speed extends Item {

<<<<<<< HEAD
	public Speed(World world) {
		super(Assets.SPEED, world);
=======
	public Speed() {
		super(Assets.SPEED);
>>>>>>> origin/master
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void pickup(Player player) {
		for (Player loopPlayer : world.getPlayers()) {
			loopPlayer.setVel(loopPlayer.getVel()*2);
		}
		super.pickup(player);
	}

}
