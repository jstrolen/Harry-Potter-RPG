package cz.jstrolen.HP_RPG.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;

/**
 * Created by Josef Stroleny
 */
public final class SpellField {
	private static final int FONT_INSETS = 3;
	private final String TEXT;
	private final int ID;
	private final int DRAW_X;
	private final int DRAW_Y;
	private final int POINT_X;
	private final int POINT_Y;
	private int sizeX;
	private int sizeY;
	
	public SpellField(String text, int id, int drawX, int drawY, int pointX, int pointY) {
		this.TEXT = text;
		this.ID = id;
		this.DRAW_X = drawX;
		this.DRAW_Y = drawY;
		this.POINT_X = pointX;
		this.POINT_Y = pointY;
	}
	
	public void draw(Graphics2D g) {
		g.setFont(DrawSettings.FONT);
		Font font = g.getFont();
		FontRenderContext frc = g.getFontRenderContext();
		String spellText = TEXT;
		sizeX = (int) font.getStringBounds(spellText, frc).getWidth();
		sizeY = (int) font.getStringBounds(spellText, frc).getHeight();	
		
		g.setColor(DrawSettings.TEXT_COLOR);
		g.fillRect(DRAW_X - sizeX / 2 - FONT_INSETS, DRAW_Y - sizeY / 2 - FONT_INSETS, sizeX + 5 * FONT_INSETS, sizeY + 5 * FONT_INSETS);
		g.setColor(DrawSettings.INFO_TEXT);
		g.drawString(spellText, DRAW_X - sizeX / 2, DRAW_Y  + sizeY / 2);
	}

	public boolean contains(Point p) {
		return p.x >= POINT_X - sizeX / 2 - FONT_INSETS && p.x <= POINT_X + sizeX / 2 + 4 * FONT_INSETS &&
				p.y >= POINT_Y - sizeY / 2 - FONT_INSETS && p.y <= POINT_Y + sizeY / 2 + 4 * FONT_INSETS;
	}
	
	public int getId() {
		return ID;
	}
}
