package cz.jstrolen.HP_RPG.gui.game_state;

import cz.jstrolen.HP_RPG.game.GameSettings;
import cz.jstrolen.HP_RPG.game.entities.spells.SpellFactory;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;
import cz.jstrolen.HP_RPG.game.maps.Map;
import cz.jstrolen.HP_RPG.game.maps.World;
import cz.jstrolen.HP_RPG.gui.GuiSettings;
import cz.jstrolen.HP_RPG.support.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

/**
 * Created by Josef Stroleny
 */
public final class Display extends Canvas {
	private static final World world = World.getInstance();

	private final Game game;
	static {
		world.setMap(new Map("Surrey", "maps/Surrey.map"));
	}
	private final Unit player = world.getPlayer();
	private final boolean[] keys = new boolean[256];
	private boolean mouseButtonLeft = false;
	private boolean mouseButtonRight = false;
	private boolean mouseButtonMiddle = false;
	private boolean spellSelectionWindow = false;

	public Display(Game game) {
		this.game = game;
		this.addMouseListener(game);
		this.addMouseWheelListener(game);
		this.addKeyListener(game);
		Display.world.getPlayer().setAi(null);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = Input.getImage(GuiSettings.CURSOR_PATH);
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), GuiSettings.CURSOR_NAME);
		this.setCursor(c);
	}

	public void tick() {
		playerTick();
		world.tick();
	}

	public void draw(Graphics2D g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());

		AffineTransform trans = g.getTransform();
		g.translate(-player.getPositionX() + (double) this.getWidth() / 2 - player.getSizeX() / 2,
				-player.getPositionY() + (double) this.getHeight() / 2 - player.getSizeY() / 2);
		world.draw(g);
		g.setTransform(trans);

		drawInfo(g);
	}

	private void drawInfo(Graphics2D g) {
		String spellText = SpellFactory.getSpellAttributes(player.getActualSpell()).getTitle();
		String mapText = world.getMap().getName();

		g.setColor(GuiSettings.TEXT_BACKGROUND_COLOR);
		g.fillRect(0, 0, GuiSettings.INFO_BOX_WIDTH, GuiSettings.INFO_BOX_HEIGHT);
		g.setColor(GuiSettings.TEXT_COLOR);
		g.drawString(spellText, GuiSettings.TEXT_LEFT_SPACE, GuiSettings.TEXT_TOP_SPACE);
		g.drawString(mapText, GuiSettings.TEXT_LEFT_SPACE, 2 * GuiSettings.TEXT_TOP_SPACE);
	}

	private void playerTick() {
		if (mouseButtonLeft) {
			try {
				int cast = player.cast();
				for (int i = 0; i < cast ;i++) {
					Point pom = Display.this.getMousePosition();
					double orientation = Math.atan2(pom.y - this.getHeight() / 2, pom.x - this.getWidth() / 2);
					world.createNewSpell(player, orientation, false);
				}
			} catch (Exception e) { //Mouse out of panel
				player.stopCast();
			}
		} else if (mouseButtonRight) {
			try {
				int cast = player.cast();
				for (int i = 0; i < cast ;i++) {
					world.createNewSpell(player, 0, true);
				}
			} catch (Exception e) { //Mouse out of panel
				player.stopCast();
			}
		} else {
			player.stopCast();
		}
		
		double diffX = 0.0;
		double diffY = 0.0;
		
		if (keys[KeyEvent.VK_W]) diffY -= player.getSpeed();
		else if (keys[KeyEvent.VK_S]) diffY += player.getSpeed();
		if (keys[KeyEvent.VK_A]) diffX -= player.getSpeed();
		else if (keys[KeyEvent.VK_D]) diffX += player.getSpeed();
		
		if (diffX != 0 && diffY != 0) {
			diffX /= Math.sqrt(GameSettings.SQRT_2);
			diffY /= Math.sqrt(GameSettings.SQRT_2);
			
			diffX = Math.round(diffX);
			diffY = Math.round(diffY);
		}

		world.move(player, diffX, diffY);
	}

	public boolean[] getKeys() { return keys; }

	public Unit getPlayer() { return player; }

	public void setMouseButtonLeft(boolean mouseButtonLeft) {
		this.mouseButtonLeft = mouseButtonLeft;
	}

	public void setMouseButtonRight(boolean mouseButtonRight) {
		this.mouseButtonRight = mouseButtonRight;
	}

	public void setMouseButtonMiddle(boolean mouseButtonMiddle) {
		this.mouseButtonMiddle = mouseButtonMiddle;
		spellSelectionWindow = !spellSelectionWindow;
		game.setPause(spellSelectionWindow);
	}
}