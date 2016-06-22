package cz.jstrolen.HP_RPG.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Josef Stroleny
 */
public final class Game extends JFrame implements KeyListener, MouseListener, MouseWheelListener, Runnable {
	private Thread gameThread;
	private Display display;
	private boolean running;
	private boolean paused;

	private BufferStrategy bs;
	private Graphics2D g;

	public Game() {
		this.setSize(DrawSettings.SIZE);
		this.setMinimumSize(DrawSettings.SIZE);
		this.setMaximumSize(DrawSettings.SIZE);
		this.setTitle(DrawSettings.TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.add(display = new Display(this));
		this.setFocusable(true);
		this.addKeyListener(this);
		this.pack();
		gameThread = new Thread(this);
		running = true;
		paused = false;

		this.setVisible(true);
		startGame();
	}

	private synchronized void startGame() {
		gameThread.start();
	}

	@Override
	public void run() {
		final double GAME_HERTZ = DrawSettings.GAME_HERTZ;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = DrawSettings.MAX_UPDATES_BEFORE_RENDER;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		final double TARGET_FPS = DrawSettings.TARGET_FPS;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
		double now;

		while (running) {
			now = System.nanoTime();
			int updateCount = 0;
			while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER ) {
				//long nanoStart = System.nanoTime();
				tick();
				//System.out.println("Tick time: " + (System.nanoTime() - nanoStart));
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

			//long nanoStart = System.nanoTime();
			render();
			//System.out.println("Render time: " + (System.nanoTime() - nanoStart));
			lastRenderTime = now;
			while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				Thread.yield();
				try {
					Thread.sleep(1);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				now = System.nanoTime();
			}
		}
	}

	private void tick() {
		if (!paused) display.tick();
	}

	private void render() {
		bs = display.getBufferStrategy();
		if (bs == null) {
			display.createBufferStrategy(DrawSettings.BUFFER_STRATEGY);
			bs = display.getBufferStrategy();
			g = (Graphics2D) bs.getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			return;
		}
		g = (Graphics2D) bs.getDrawGraphics();
		display.draw(g);
		bs.show();
		g.dispose();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		display.getPlayer().scrollSpell(e.getWheelRotation() > 0);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			/*if (spellSelection) {		//TODO
				for (int i = 0; i < spellFields.size(); i++) {
					if (spellFields.get(i).contains(e.getPoint())) {
						//player.setSpell(spellFields.get(i).getId());
						spellSelection = false;
						Display.this.Game.setPaused(spellSelection);
						break;
					}
				}
			}
			else */
			display.setMouseButtonLeft(true);
		}
		else if (e.getButton() == MouseEvent.BUTTON2) {
			//spellSelection = true;
			//Display.this.Game.setPaused(spellSelection); TODO
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			/*if (!spellSelection) {	//TODO
				mouseButton1 = false;
			}*/
			display.setMouseButtonLeft(false);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg) {
		display.getKeys()[arg.getKeyCode()] = true;

		if (arg.getKeyCode() == KeyEvent.VK_P) this.paused = !this.paused;
	}

	@Override
	public void keyReleased(KeyEvent arg) {
		display.getKeys()[arg.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
