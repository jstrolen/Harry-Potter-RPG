package spells;

import items.Items;

import java.awt.Color;
import java.util.List;

import unit.Unit;
import blocks.Block;
import effects.Wound;

public final class Sectumsempra extends Spell {
	public static final int ID = 7;
	private static final double SPEED = 15;
	private static final int CAST = 35;
	private static final double LENGTH = 1000;
	private static final Color COLOR = Color.GREEN;
	private static final int SIZE = 7;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = true;
	private static final int TIME = 50;
	private static final int DOT = 1;

	public Sectumsempra(Unit caster, double positionX, double positionY, double orientation) {
		super(caster, positionX, positionY, WALL, HITTABLE, orientation, SPEED, CAST, COLOR, LENGTH, SIZE);
	}

	@Override
	public void hitWall(Block[][] blocks, int x, int y) {
		this.setExist(false);
	}

	@Override
	public void hitItems(List<Items> items, Items item) {
		this.setExist(false);
	}

	@Override
	public void hitUnit(List<Unit> units, Unit unit) {
		unit.unitHitted(new Wound(TIME, this, unit, units, DOT));
		this.setExist(false);
	}
	
	@Override
	public void hitSpell(List<Spell> spells, Spell spell) {
		if (spell instanceof Protego) {
			this.setOrientation(this.getOrientation() + Math.PI);
			this.setCaster(spell.getCaster());
			spell.setExist(false);
		}
	}
	
	@Override
	public String toString() {
		return "Sectumsempra";
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	@Override
	public int getId() {
		return Sectumsempra.ID;
	}
}
