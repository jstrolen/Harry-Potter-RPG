package effects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import spells.Spell;
import unit.Unit;

public final class Heal extends UnitEffect {
	public static final int ID = 1;
	private int heal;

	public Heal(int counter, Spell spell, Unit unit, int heal) {
		super(counter, spell, unit);
		this.heal = heal;
	}
	
	@Override
	public void nakresli(Graphics2D g, double x, double y, double sizeX,
			double sizeY) {
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif", Font.BOLD, 22));
		g.drawString("+", (int) x, (int) y);
	}

	@Override
	public void apply() {
		getUnit().setHealth(Math.min(getUnit().getHealth() + heal, getUnit().getMaxHealth()));
	}

	@Override
	public int getId() {
		return Heal.ID;
	}

}
