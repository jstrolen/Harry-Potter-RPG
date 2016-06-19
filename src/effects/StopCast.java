package effects;

import java.awt.Graphics2D;

import spells.Spell;
import unit.Unit;

public final class StopCast extends UnitEffect {
	public static final int ID = 3;

	public StopCast(int counter, Spell spell, Unit unit) {
		super(counter, spell, unit);
	}
	
	@Override
	public void nakresli(Graphics2D g, double x, double y, double sizeX,
			double sizeY) {
		//Nothing
	}

	@Override
	public void apply() {
		getUnit().setCanCastNow(false);
	}

	@Override
	public int getId() {
		return StopCast.ID;
	}
}
