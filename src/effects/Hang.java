package effects;

import java.awt.Graphics2D;

import spells.Spell;
import unit.Unit;

public final class Hang extends UnitEffect {
	public static final int ID = 0;

	public Hang(int counter, Spell spell, Unit unit) {
		super(counter, spell, unit);
	}
	
	@Override
	public void nakresli(Graphics2D g, double x, double y, double sizeX,
			double sizeY) {
		//Nothing
	}

	@Override
	public void apply() {
		getUnit().setHang(true);
	}

	@Override
	public int getId() {
		return Hang.ID;
	}
}
