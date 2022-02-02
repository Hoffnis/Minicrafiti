package com.hoffnisgames.main;

import com.hoffnisgames.entities.Enemy1;
import com.hoffnisgames.entities.Entity;
import com.hoffnisgames.world.World;

public class EnemySpaw {
	
	public int interval = 60*4;
	public int curtime = 0;
	
	public void tick() {
		curtime++;
		if(curtime == interval) {
			curtime = 0;
			int xInitial = Entity.rand.nextInt((World.WIDTH/2) * 16 -16 - 16) + 16;
			Enemy1 enemy1 = new Enemy1(xInitial,16,16,16,1,Entity.ENEMY1);
			Game.entities.add(enemy1);
		}
	}

}
