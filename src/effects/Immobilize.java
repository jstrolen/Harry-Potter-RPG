package effects;

import java.awt.Graphics2D;

import spells.Spell;
import unit.Unit;

public final class Immobilize extends UnitEffect {
	public static final int ID = 2;

	public Immobilize(int counter, Spell spell, Unit unit) {
		super(counter, spell, unit);
	}
	
	@Override
	public void nakresli(Graphics2D g, double x, double y, double sizeX,
			double sizeY) {
		//Nothing
	}

	@Override
	public void apply() {
		getUnit().setSpeed(0);
	}

	@Override
	public int getId() {
		return Heal.ID;
	}
}
