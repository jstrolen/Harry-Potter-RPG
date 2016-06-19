package effects;

import java.awt.Graphics2D;

import spells.Spell;
import unit.Unit;

public abstract class UnitEffect {
	private int counter;
	private Spell spell;
	private Unit unit;
	
	public UnitEffect(int counter, Spell spell, Unit unit) {
		this.counter = counter;
		this.spell = spell;
		this.unit = unit;
	}
	
	public boolean nextStep() {
		counter--;
		return (counter < 0);
	}
	
	public abstract void apply();
	
	public abstract void nakresli(Graphics2D g, double x, double y, double sizeX, double sizeY);

	public Spell getSpell() {
		return spell;
	}

	public void setSpell(Spell spell) {
		this.spell = spell;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public abstract int getId();
}
