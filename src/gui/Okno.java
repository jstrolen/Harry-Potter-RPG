package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Okno extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	private static final Dimension SIZE = new Dimension(800, 600);
	private static final String TITLE = "HP (alpha)";
	private Panel panel;
	private boolean running;
	private boolean paused;

	public Okno() {
		this.setSize(SIZE);
		this.setTitle(TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.add(panel = new Panel(this));
		this.setFocusable(true);
		this.addKeyListener(this);
		running = true;
		paused = false;
		
		this.setVisible(true);
		run();
	}

	private void run() {
		Thread loop = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		loop.start();
	}
	
	private void gameLoop() {
		final double GAME_HERTZ = 40.0;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = 2;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		final double TARGET_FPS = 30;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;
			if (!paused) {
				while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER ) {
					panel.step();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
				}
				if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}
				
				panel.repaint();
				lastRenderTime = now;
				while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					Thread.yield();
					try {
						Thread.sleep(1);
					} 
					catch(Exception e) {
						System.out.println("Chyba při sleep.");
					} 
					now = System.nanoTime();
				}
			}
		}
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@Override
	public void keyPressed(KeyEvent arg) {
		if (arg.getKeyCode() == KeyEvent.VK_W) panel.move(0, true);
		else if (arg.getKeyCode() == KeyEvent.VK_S) panel.move(1, true);
		if (arg.getKeyCode() == KeyEvent.VK_A) panel.move(2, true);
		else if (arg.getKeyCode() == KeyEvent.VK_D) panel.move(3, true);
		
		if (arg.getKeyCode() == KeyEvent.VK_P) this.paused = !this.paused;
	}

	@Override
	public void keyReleased(KeyEvent arg) {
		if (arg.getKeyCode() == KeyEvent.VK_W) panel.move(0, false);
		else if (arg.getKeyCode() == KeyEvent.VK_S) panel.move(1, false);
		if (arg.getKeyCode() == KeyEvent.VK_A) panel.move(2, false);
		else if (arg.getKeyCode() == KeyEvent.VK_D) panel.move(3, false);	
	}

	@Override
	public void keyTyped(KeyEvent arg) {	
	}
}
