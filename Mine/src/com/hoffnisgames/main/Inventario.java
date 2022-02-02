package com.hoffnisgames.main;

import java.awt.Color;
import java.awt.Graphics;

import com.hoffnisgames.world.Camera;
import com.hoffnisgames.world.FloorTile;
import com.hoffnisgames.world.Tile;
import com.hoffnisgames.world.WallTile;
import com.hoffnisgames.world.World;

public class Inventario {
	
	public int select = 0;
	public boolean ispress = false;
	public boolean placed = false;
	public int mx, my;

	public int inventorysize = 50;
	
	public String[] items = {"grama", "neve", "terra", "areia", "ar", ""};

		public int initialposition = ((Game.WIDTH * Game.SCALE) / 2) - ((items.length*inventorysize) / 2);
		
	public void tick() {
		if(ispress) {
			ispress = false;
			if(mx >= initialposition && mx < initialposition + (inventorysize*items.length));{
				if(my >= Game.HEIGHT*Game.SCALE-inventorysize +1 && my< Game.HEIGHT*Game.SCALE-inventorysize +1 + inventorysize ) {
					select = (int)(mx-initialposition)/inventorysize;
				}
			}
			
		}

		if(placed) {
			placed = false;
			mx = (int)mx/4 + Camera.x;
			my = (int)my/4 + Camera.y;
			
			int tilex = mx/16;
			int tiley = my/16;
			if(World.tiles[tilex+tiley*World.WIDTH].solid == false) {
				if(items[select] == "grama") {
					World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_GRAMA);
				} else if(items[select] == "terra") {
					World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_TERRA);
			} else if(items[select] == "ar") {
				World.tiles[tilex+tiley*World.WIDTH] = new FloorTile(tilex*16, tiley*16, Tile.TILE_AR);
			} else if(items[select] == "areia") {
				World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_AREIA);
			}else if(items[select] == "neve") {
					World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_NEVE);
				
			}
				
				if(World.isFree(Game.player.getX(), Game.player.getY()) ==  false) {
					World.tiles[tilex+tiley*World.WIDTH] = new FloorTile(tilex*16, tiley*16, Tile.TILE_AR);
				}
				
			}
		}
		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < 6; i++) {
			g.setColor(Color.gray);
			g.drawRect(initialposition + (i*inventorysize) +1, Game.HEIGHT*Game.SCALE-inventorysize +1, inventorysize, inventorysize);
			
			if(items[i] == "grama") {
				g.drawImage(Tile.TILE_GRAMA, initialposition + (i*inventorysize) + 10, Game.HEIGHT*Game.SCALE - inventorysize + 10, 32, 32,null );
			} else if(items[i] == "terra") {
				g.drawImage(Tile.TILE_TERRA, initialposition + (i*inventorysize) + 10, Game.HEIGHT*Game.SCALE - inventorysize + 10, 32, 32,null );
			}
			else if(items[i] == "areia") {
				g.drawImage(Tile.TILE_AREIA, initialposition + (i*inventorysize) + 10, Game.HEIGHT*Game.SCALE - inventorysize + 10, 32, 32,null );
			}
			else if(items[i] == "neve") {
				g.drawImage(Tile.TILE_NEVE, initialposition + (i*inventorysize) + 10, Game.HEIGHT*Game.SCALE - inventorysize + 10, 32, 32,null );
			}
			else if(items[i] == "ar") {
				g.drawImage(Tile.TILE_AR, initialposition + (i*inventorysize) + 10, Game.HEIGHT*Game.SCALE - inventorysize + 10, 32, 32,null );
			}
			
			if(select == i) {
				g.setColor(Color.RED);
				g.drawRect(initialposition + (i*inventorysize), Game.HEIGHT*Game.SCALE-inventorysize -1, inventorysize, inventorysize);
			}
		}
	}
}
