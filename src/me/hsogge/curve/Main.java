package me.hsogge.curve;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import me.hsogge.curve.input.Keyboard;
import me.hsogge.curve.world.World;

public class Main implements Runnable {
	
	final int WIDTH = 640;
	final int HEIGHT = 480;
	int width = WIDTH;
	int height = HEIGHT;

	JFrame frame;
	static Canvas canvas;
	BufferStrategy bufferStrategy;
	
	World world;

	public Main() {
		initMain();
		resetGame();
	}
	
	private boolean fullscreen = !true;
	
	private void initMain() {
		frame = new JFrame("curve");
		
		if (fullscreen) {
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			width = Toolkit.getDefaultToolkit().getScreenSize().width;
			height = Toolkit.getDefaultToolkit().getScreenSize().height;
		} else {
			width = WIDTH;
			height = HEIGHT;
		}
		
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(null);
		

		canvas = new Canvas();
		canvas.setBounds(0, 0, width, height);
		canvas.setIgnoreRepaint(true);

		panel.add(canvas);
	
		canvas.addKeyListener(new Keyboard());
	
		world = new World();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height+30);
		frame.setResizable(false);
		frame.setLocationByPlatform(!fullscreen);
		if (fullscreen) frame.setLocation(0, 0);
		frame.setVisible(true);

		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();

		canvas.requestFocus();
	}
	
	static final int TICKRATE = 60;
	static double timePassed = 0;
	static int fps = 0;
	static int tps = 0;
	
	public void toggleFullscreen() {
		fullscreen = !fullscreen;
		frame.dispose();
		initMain();
		
	}
	
	public static int getTickrate() {
		return TICKRATE;
	}
	
	public static int getFPS() {
		return fps;
	}
	public static int getTPS() {
		return tps;
	}
	public static double getTimePassed() {
		return timePassed;
	}

	public static Canvas getCanvas() {
		return canvas;
	}
	private void resetGame() {

	}
	
	
	public void run() {
		final double UPDATE_INTERVAL = 1000000000 / TICKRATE;

		long lastTime = System.nanoTime();
		long startTime = System.nanoTime();
		long timer = System.nanoTime();
		double delta = 0;
		int ticks = 0;
		int frames = 0;

		while (true) {
			long now = System.nanoTime();
			delta += now - lastTime;
			timePassed = (double) (System.nanoTime()-startTime)/1000000000;
			lastTime = now;

			while (delta >= UPDATE_INTERVAL) {
				delta -= UPDATE_INTERVAL;
				update();
				
				if (Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
					System.exit(0);			
				}
				
				if (Keyboard.isKeyPressed(KeyEvent.VK_DELETE)) {
					resetGame();
				}
				
				if (Keyboard.isKeyPressed(KeyEvent.VK_ENTER) && Keyboard.isKeyDown(KeyEvent.VK_ALT)) {
					toggleFullscreen();
				}
				
				ticks++;
			}

			render();
			frames++;

			if (timer + 1000000000 <= System.nanoTime()) {
				timer += 1000000000;
				tps = ticks;
				fps = frames;
				ticks = 0;
				frames = 0;
			}
		}
	}

	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		render(g);
		g.dispose();
		bufferStrategy.show();
	}
	

	protected void update() {
		world.tick();
	}


	protected void render(Graphics2D g) {
		world.render(g);
	}
	
	private static Thread thread;

	public static void main(String[] args) {
		Main ex = new Main();
		thread = new Thread(ex);
		thread.start();
	}

}