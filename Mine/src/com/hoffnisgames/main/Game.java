package com.hoffnisgames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;




import com.hoffnisgames.entities.Entity;

import com.hoffnisgames.entities.Player;

import com.hoffnisgames.graficos.Spritesheet;
import com.hoffnisgames.graficos.UI;

import com.hoffnisgames.world.World;


public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 4;
	private int x = 0;
	private boolean showGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	
	public static Spritesheet spritesheet;
	public static Player player;
	public static Random rand;
	public UI ui;
	public static World world;
	public static BufferedImage minimapa;
	public static int[] mapixel;
	public static double score = 0;
	public static Inventario inventario;
	public static EnemySpaw enemyspaw;
	
	

	

	
public Game() {
	

	addKeyListener(this);
	addMouseListener(this);
	this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
	initFrame();
	
	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	spritesheet = new Spritesheet("/spritesheet.png");
	entities = new ArrayList<Entity>();
	player = new Player(16, 16, 16, 16, 1, Entity.PLAYER_RIGHT[0]);
	entities.add(player);
	world = new World();
	inventario = new Inventario();
	enemyspaw = new EnemySpaw();

	ui = new UI();

	



	

}

public void initFrame() {
	
	frame = new JFrame("Hoffnis");
	frame.add(this);
	frame.setResizable(false);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
}

public synchronized void start() {
	thread = new Thread(this);
	isRunning = true;
	thread.start();
}

public synchronized void stop() {
	isRunning = false;
	try {
		thread.join();
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	}
	
	
}
	
public static void main(String args[]) {
	Game game = new Game();
	game.start();
	
	
}

public void tick() {
	



	for(int i = 0; i < entities.size(); i++) {
		Entity e = entities.get(i);
		e.tick();
	}
	
	ui.tick();
	inventario.tick();
	enemyspaw.tick();

	
	

}



public void render() {
	BufferStrategy bs = this.getBufferStrategy();
	if(bs == null) {
		this.createBufferStrategy(3);
		return;
	}
	
	Graphics g = image.getGraphics();
	g.setColor(new Color(3,170,250));
	g.fillRect(0, 0, WIDTH, HEIGHT);
	
	//Graphics2D g2= (Graphics2D) g;
	world.render(g);

	Collections.sort(entities, Entity.nodeSorter);
	for(int i = 0; i < entities.size(); i++) {
		Entity e = entities.get(i);
		e.render(g);
	}
	
	
	
	g.dispose();
	g = bs.getDrawGraphics();
	g.drawImage(image,0,0,WIDTH*SCALE, HEIGHT*SCALE, null);
	ui.render(g);
	inventario.render(g);
	bs.show();
}
	public void run() {
		long LastTime = System.nanoTime();
		double amountOfTicks= 60.0;
		double ns = 1000000000 / amountOfTicks;
		int frames = 0;
		double timer = System.currentTimeMillis();
		double delta= 0;
		requestFocus();
	
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - LastTime) / ns;
			LastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
			}
		}
				
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = true;
	
		
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			
			player.left = true;
		
		}
		
		
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		}
		
		
	}

	
	public void keyReleased(KeyEvent e) {
		
if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = false;
	
		
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			
			player.left = false;
		
		}
		
	}


	public void keyTyped(KeyEvent e) {
	
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton() == MouseEvent.BUTTON3) {
		
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			inventario.ispress = true;
			inventario.mx = e.getX();
			inventario.my = e.getY();
			
			player.atak = true;
	
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			 inventario.placed = true;
				
			 inventario.mx = e.getX();
			 inventario.my = e.getY();
		 }
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
