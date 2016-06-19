package spells;

import items.Items;

import java.awt.Color;
import java.util.List;

import unit.Unit;
import blocks.Block;
import effects.Heal;

public final class Episkey extends Spell {
	public static final int ID = 0;
	private static final double SPEED = 15;
	private static final int CAST = 25;
	private static final double LENGTH = 1000;
	private static final Color COLOR = Color.PINK;
	private static final int SIZE = 7;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = true;
	private static final int TIME = 30;
	private static final int HOT = 1;

	public Episkey(Unit caster, double positionX, double positionY, double orientation) {
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
		unit.unitHitted(new Heal(TIME, this, unit, HOT));
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
		return "Episkey";
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	@Override
	public int getId() {
		return Episkey.ID;
	}
}
