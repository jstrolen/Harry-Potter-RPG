package cz.jstrolen.HP_RPG.gui;

import cz.jstrolen.HP_RPG.game.Settings;
import cz.jstrolen.HP_RPG.game.World;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;
import cz.jstrolen.HP_RPG.game.maps.Map;
import cz.jstrolen.HP_RPG.game.support.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by Josef Stroleny
 */
public final class Display extends Canvas {
	private static final World WORLD = World.getInstance();
	static {
		WORLD.setMap(new Map("Surrey", "maps/Surrey.map"));
	}
	private final Game Game;
	private Unit player = WORLD.getPlayer();
	private boolean[] keys = new boolean[256];
	private boolean mouseButton1 = false;
	private boolean spellSelection = false;
	private List<SpellField> spellFields;

	public Display(Game game) {
		this.Game = game;
		Display.WORLD.getPlayer().setAi(null);

		/*TODO
		this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				player.changeSpell(e.getWheelRotation());
			}
		});
		*/

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/*if (e.getButton() == MouseEvent.BUTTON1) {
					if (!spellSelection) {
						mouseButton1 = false;
					}
				}*/
			}

			@Override
			public void mousePressed(MouseEvent e) {
				/*if (e.getButton() == MouseEvent.BUTTON1) {
					if (spellSelection) {
						for (int i = 0; i < spellFields.size(); i++) {
							if (spellFields.get(i).contains(e.getPoint())) {
								//player.setSpell(spellFields.get(i).getId()); TODO
								spellSelection = false;
								Display.this.Game.setPaused(spellSelection);
								break;
							}
						}
					}
					else mouseButton1 = true;
					repaint();
				}
				else if (e.getButton() == MouseEvent.BUTTON2) {
					spellSelection = true;
					//Display.this.Game.setPaused(spellSelection); TODO
					repaint();
				}*/
			}
		});
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = Input.getImage("images/wand.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "wand");
		this.setCursor(c);
	}

	public void tick() {
		playerTick();
		WORLD.tick();
	}

	public void draw(Graphics2D g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		g.translate(-player.getPositionX() + (double) this.getWidth() / 2 - player.getSizeX() / 2,
				-player.getPositionY() + (double) this.getHeight() / 2 - player.getSizeY() / 2);
		WORLD.draw(g);

		/* TODO
		g.setFont(Display.FONT);
		Font font = g.getFont();
		FontRenderContext frc = g.getFontRenderContext();

		int startX = (int) (player.getPositionX() - (double) this.getWidth() / 2 + player.getAttributes().getSIZE_X() / 2);
		int startY = (int) (player.getPositionY() - (double) this.getHeight() / 2 + player.getAttributes().getSIZE_Y() / 2);
		
		String spellText = player.getActualSpell().toString();
		int spellY = startY + (int) (font.getStringBounds(spellText, frc).getHeight());
		
		String mapText = WORLD.toString();
		int mapY = spellY + (int) (font.getStringBounds(spellText, frc).getHeight());
		
		int maxX = (int) Math.max(font.getStringBounds(spellText, frc).getWidth(), font.getStringBounds(mapText, frc).getWidth()) * 2;
		int maxY = (int) (font.getStringBounds(spellText, frc).getHeight() + 2 * font.getStringBounds(spellText, frc).getHeight());
		
		g.setColor(INFO_BOX);
		g.fillRect(startX, startY, maxX, maxY);
		g.setColor(INFO_TEXT);
		g.drawString(spellText , startX, spellY);
		g.drawString(mapText, startX, mapY);
		
		if (spellSelection) {
			drawSpells(g);
		}
		*/
	}

	/* TODO
	private void drawSpells(Graphics2D g) {
		spellFields = new ArrayList<SpellField>();
		int spellCount = player.getSpellCount();
		Spell[] spells = player.getSpells();
		
		int d = Math.min(getSize().width, getSize().height) / 4;
		
		int startX = (int) (player.getPositionX() + player.getSIZE_X() / 2);
		int startY = (int) (player.getPositionY() + player.getSIZE_Y() / 2);
		g.setColor(Color.BLACK);
		for (int i = 0; i < spellCount; i++) {
			int myX = (int) (d * Math.cos(((double) i / spellCount) * Math.PI * 2));
			int myY = (int) (d * Math.sin(((double) i / spellCount) * Math.PI * 2));
			SpellField f = new SpellField(spells[i].toString(), spells[i].getId(), startX + myX, startY + myY, this.getSize().width / 2 + myX, this.getSize().height / 2 + myY);
			spellFields.add(f);
			f.paint(g);
		}
	}
	*/

	public void playerTick() {
		/*if (this.mouseButton1) {
			try {
				Point pom = Display.this.getMousePosition();
				double vector = Math.atan2(pom.y - this.getHeight() / 2, pom.x - this.getWidth() / 2);
				player.cast(vector, WORLD);
			} catch (Exception e) {
				//Mouse out of panel
				player.stopCast(WORLD);
			}
		}
		else {
			player.stopCast(WORLD);
		}*/
		
		double diffX = 0.0;
		double diffY = 0.0;
		
		if (keys[KeyEvent.VK_W]) diffY -= player.getSpeed();
		else if (keys[KeyEvent.VK_S]) diffY += player.getSpeed();
		if (keys[KeyEvent.VK_A]) diffX -= player.getSpeed();
		else if (keys[KeyEvent.VK_D]) diffX += player.getSpeed();
		
		if (diffX != 0 && diffY != 0) {
			diffX /= Math.sqrt(Settings.SQRT_2);
			diffY /= Math.sqrt(Settings.SQRT_2);
			
			diffX = Math.round(diffX);
			diffY = Math.round(diffY);
		}

		WORLD.move(player, diffX, diffY);
	}

	public boolean[] getKeys() { return keys; }
}