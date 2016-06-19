package gui;
import general.Map;
import map.Maps;
import spells.Spell;
import support.Input;
import unit.Person;
import unit.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;

public final class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final Color INFO_TEXT = Color.BLACK;
	public static final Color INFO_BOX = Color.YELLOW;
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 12);
	private static final Map map = Map.getInstance();
	static {
		map.setMap(Maps.getMaps("Surrey"));
	}
	private final Okno OKNO;
	private Unit player = map.getPlayer();
	private boolean[] move = new boolean[4];
	private boolean mouseButton1 = false;
	private boolean spellSelection = false;
	private List<SpellField> spellFields;

	public Panel(Okno okno) {
		this.OKNO = okno;
		Panel.map.getPlayer().setIntel(null);
		this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				player.changeSpell(e.getWheelRotation());
			}
		});

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (!spellSelection) {
						mouseButton1 = false;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (spellSelection) {
						for (int i = 0; i < spellFields.size(); i++) {
							if (spellFields.get(i).contains(e.getPoint())) {
								player.setSpell(spellFields.get(i).getId());
								spellSelection = false;
								Panel.this.OKNO.setPaused(spellSelection);
								break;
							}
						}
					}
					else mouseButton1 = true;
					repaint();
				}
				else if (e.getButton() == MouseEvent.BUTTON2) {
					spellSelection = true;
					//Panel.this.OKNO.setPaused(spellSelection);
					repaint();
				}
			}
		});
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = Input.getImage("wand.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "wand");
		this.setCursor(c);
	}
	
	@Override
	public void paint(Graphics g0) {
		Graphics2D g = (Graphics2D) g0;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.translate(-player.getPositionX() + (double) this.getWidth() / 2 - player.getSizeX() / 2, 
				-player.getPositionY() + (double) this.getHeight() / 2 - player.getSizeY() / 2);
		map.nakresli(g);
		
		g.setFont(Panel.FONT);
		Font font = g.getFont();
		FontRenderContext frc = g.getFontRenderContext();
		
		int startX = (int) (player.getPositionX() - (double) this.getWidth() / 2 + player.getSizeX() / 2);
		int startY = (int) (player.getPositionY() - (double) this.getHeight() / 2 + player.getSizeY() / 2);
		
		String spellText = ((Person) player).getActualSpell().toString();
		int spellY = startY + (int) (font.getStringBounds(spellText, frc).getHeight());
		
		String mapText = map.toString();
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
	}
	
	private void drawSpells(Graphics2D g) {
		spellFields = new ArrayList<SpellField>();
		int spellCount = player.getSpellCount();
		Spell[] spells = player.getSpells();
		
		int d = Math.min(getSize().width, getSize().height) / 4;
		
		int startX = (int) (player.getPositionX() + player.getSizeX() / 2);
		int startY = (int) (player.getPositionY() + player.getSizeY() / 2);
		g.setColor(Color.BLACK);
		for (int i = 0; i < spellCount; i++) {
			int myX = (int) (d * Math.cos(((double) i / spellCount) * Math.PI * 2));
			int myY = (int) (d * Math.sin(((double) i / spellCount) * Math.PI * 2));
			SpellField f = new SpellField(spells[i].toString(), spells[i].getId(), startX + myX, startY + myY, this.getSize().width / 2 + myX, this.getSize().height / 2 + myY);
			spellFields.add(f);
			f.paint(g);
		}
	}

	public void playerRun() {
		if (this.mouseButton1) {
			try {
				Point pom = Panel.this.getMousePosition();
				double vector = Math.atan2(pom.y - this.getHeight() / 2, pom.x - this.getWidth() / 2);
				player.cast(vector, map);
			} catch (Exception e) {
				//Mouse out of panel
				player.stopCast(map);
			}
		}
		else {
			player.stopCast(map);
		}
		
		double difX = 0.0;
		double difY = 0.0;
		
		if (move[0]) difY -= player.getSpeed();
		else if (move[1]) difY += player.getSpeed();
		if (move[2]) difX -= player.getSpeed();
		else if (move[3]) difX += player.getSpeed();
		
		if (difX != 0 && difY != 0) {
			difX /= Math.sqrt(2.0);
			difY /= Math.sqrt(2.0);
			
			difX = Math.round(difX);
			difY = Math.round(difY);
		}
		
		player.move(difX, difY, map);
	}
	
	public void move(int direction, boolean type) {
		move[direction] = type;
	}

	public void step() {
		playerRun();
		map.run();
	}
}