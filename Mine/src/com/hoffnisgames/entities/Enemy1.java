package com.hoffnisgames.entities;

import java.awt.image.BufferedImage;

import com.hoffnisgames.main.Game;
import com.hoffnisgames.world.FloorTile;
import com.hoffnisgames.world.Tile;
import com.hoffnisgames.world.WallTile;
import com.hoffnisgames.world.World;

public class Enemy1 extends Entity {
	
	public boolean rigth = true, left = false;
	public int ivida = 10;
	public int dir = 1;

	public Enemy1(double x, double y, int width, int heigth, double speed, BufferedImage sprite) {
		super(x, y, width, heigth, speed, sprite);
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		
		
		
		
		if(World.isFree((int)x, (int)(y+1))) {
			y+=1;
		}
		
		if(dir == 1) {
			if(World.isFree((int)(x+speed), (int)y)) {
				x+=speed;
			} else {
				int xnext = (int)((x+speed)/16) +1;
				int ynext = (int)(y / 16);
				if(World.tiles[xnext+ynext*World.WIDTH] instanceof WallTile && World.tiles[xnext+ynext*World.WIDTH].solid == false) {
					World.tiles[xnext+ynext*World.WIDTH] = new FloorTile((xnext)*16, ynext*16, Tile.TILE_AR);
				}
				
				dir = -1;
			}
		} else if(dir == -1) {
			if(World.isFree((int)(x-speed), (int)y)) {
				x-=speed;
			} else {
				int xnext = (int)((x-speed)/16);
				int ynext = (int)(y / 16);
				if(World.tiles[xnext+ynext*World.WIDTH] instanceof WallTile && World.tiles[xnext+ynext*World.WIDTH].solid == false) {
					World.tiles[xnext+ynext*World.WIDTH] = new FloorTile((xnext)*16, ynext*16, Tile.TILE_AR);
				}
				dir = 1;
			}
		}
		
		if(ivida == 0) {
			Game.entities.remove(this);
			return;
		}
	/*	
		if(rigth) {
			if(World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			if(World.isFree((int)x+16, (int)y+1)) {
				rigth = false;
				left = true;	
			}
			}else {
				rigth = false;
				left = true;
			}
		}
		
		
		if(left){
			if(World.isFree((int)(x-speed), (int)y)) {
			x-=speed;
			if(World.isFree((int)x-16, (int)y+1)) {
				rigth = true;
				left = false;
			}
		}else {
			rigth = true;
			left = false;
		}
			}*/
		
	}
}
