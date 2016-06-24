package cz.jstrolen.HP_RPG.gui.game_state;

import cz.jstrolen.HP_RPG.gui.GuiSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Josef Stroleny
 */
public final class Game extends JFrame implements KeyListener, MouseListener, MouseWheelListener, Runnable {
	private final Thread gameThread;
	private final Display display;
	private boolean running;
	private boolean paused;

	public Game() {
		this.setSize(GuiSettings.SIZE);
		this.setMinimumSize(GuiSettings.SIZE);
		this.setMaximumSize(GuiSettings.SIZE);
		this.setTitle(GuiSettings.TITLE);
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
		final double GAME_HERTZ = GuiSettings.GAME_HERTZ;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = GuiSettings.MAX_UPDATES_BEFORE_RENDER;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime;
		final double TARGET_FPS = GuiSettings.TARGET_FPS;
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
		BufferStrategy bs = display.getBufferStrategy();
		Graphics2D g;
		if (bs == null) {
			display.createBufferStrategy(GuiSettings.BUFFER_STRATEGY);
			bs = display.getBufferStrategy();
			g = (Graphics2D) bs.getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g.setFont(GuiSettings.FONT);
			return;
		}
		g = (Graphics2D) bs.getDrawGraphics();
		display.draw(g);
		bs.show();
		g.dispose();
	}

	public void setPause(boolean pause) {
		this.paused = pause;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		display.getPlayer().scrollSpell(e.getWheelRotation() > 0);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			display.setMouseButtonLeft(true);
		}
		else if (e.getButton() == MouseEvent.BUTTON3) {
			display.setMouseButtonRight(true);
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			display.setMouseButtonMiddle(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			display.setMouseButtonLeft(false);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			display.setMouseButtonRight(false);
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			display.setMouseButtonMiddle(false);
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
