package effects;

import java.awt.Graphics2D;
import java.util.List;

import spells.Spell;
import unit.Unit;

public final class Wound extends UnitEffect {
	public static final int ID = 4;
	private List<Unit> units;
	private int damage;

	public Wound(int counter, Spell spell, Unit unit, List<Unit> units, int damage) {
		super(counter, spell, unit);
		this.units = units;
		this.damage = damage;
	}
	
	@Override
	public void nakresli(Graphics2D g, double x, double y, double sizeX,
			double sizeY) {
		//Nothing
	}

	@Override
	public void apply() {
		if (getUnit().injured(damage)) units.remove(getUnit());
	}

	@Override
	public int getId() {
		return Wound.ID;
	}
}
