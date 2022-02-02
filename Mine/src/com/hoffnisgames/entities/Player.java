package com.hoffnisgames.entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.hoffnisgames.main.Game;
import com.hoffnisgames.world.Camera;
import com.hoffnisgames.world.World;

public class Player extends Entity{


	public boolean right, left;
	private double grav = 2;
	public int dir = 1;
	public boolean jump = false;
	public boolean jumping = false;
	public int pulo = 44;
	public int jumpframes = 0;
	private int anima = 0;
	private int maxAnima = 6;
	private int maxsprite = 4;
	private int cursprite = 0;
	public double life = 100;
	private boolean moved = false;
	public BufferedImage ATAQUER;
	public BufferedImage ATAQUEL;
	public boolean atak = false;
	public boolean isatak = false;
	public int atakf = 0;
	public int atakm = 8;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	
		ATAQUER = Game.spritesheet.getSprite(80, 0, 16, 16);
		ATAQUEL = Game.spritesheet.getSprite(96, 0, 16, 16);
	}
	public void tick() {
	
		depth = 2;
	if(World.isFree((int)x, (int)(y+grav)) && jumping == false) {
		y+=grav;
		
		
		}
	
	
	if(right && World.isFree((int)(x+speed), (int)y)) {
		x+=speed;
		dir=1;
		moved = true;
		
	} else if(left && World.isFree((int)(x-speed), (int)y)) {
		x-=speed;
		dir=0;
		moved = true;
	} else {
		moved=false;
	}
	
	if(jump) {
		if(!World.isFree(this.getX(), this.getY()+1)){
			jumping = true;
		}else {
			jump = false;
		}
	}
	
	if(jumping) {
		if(World.isFree(this.getX(), this.getY()-2)) {
			y-=2;
			jumpframes+=2;
			if(jumpframes == pulo) {
				jumping = false;
				jump = false;
				jumpframes = 0;
			}
		}else {
			jumping = false;
			jump = false;
			jumpframes = 0;
		}
	}
	
	if(atak) {
		if(isatak == false) {
		atak = false;
		isatak = true;
	}
	}
	
	if(isatak) {
		atakf++;
		if(atakf == atakm) {
			atakf=0;
			isatak = false;
		}
		
	}
	
	collisionEnemy();
	
	 if(Game.player.life <= 0) {
		 World.restartGame(null);
	 }
	Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
	Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
	
	}
	
	public void collisionEnemy() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy1) {
				if(Entity.rand.nextInt(100) < 30) {
					if(Entity.isCollinding(this, e)) {
						life-=1;
					if(isatak) {
						((Enemy1) e).ivida --;
						
						if(dir == 1) {
						((Enemy1)e).x +=10;
						} else {
							((Enemy1)e).x -=20;
						}
					
					}
					
					
		
			
					}
				}
			}
		}
	}
	
	
	public void render(Graphics g) {
		
		if(moved) {
		anima++;
		if(anima == maxAnima) {
			cursprite++;
			anima = 0;
			if(cursprite == maxsprite) {
				cursprite = 0;
			}
		}
		} else {
			cursprite = 0;
		}
		
		if(dir == 1) {
			sprite = Entity.PLAYER_RIGHT[cursprite];
			if(isatak) {
				g.drawImage(ATAQUER, this.getX()+8 - Camera.x, this.getY() - Camera.y, null);
			}
		}
		else if (dir == 0) {
			sprite = Entity.PLAYER_LEFT[cursprite];
			if(isatak) {
				g.drawImage(ATAQUEL, this.getX()-8 - Camera.x, this.getY() - Camera.y, null);
			}
		}
		
		super.render(g);
	}
	
	
	
	
	
	


}
