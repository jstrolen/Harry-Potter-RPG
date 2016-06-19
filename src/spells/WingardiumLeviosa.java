package spells;

import items.Items;

import java.awt.Color;
import java.util.List;

import unit.Unit;
import blocks.Block;

public final class WingardiumLeviosa extends Spell {
	public static final int ID = 9;
	private static final double SPEED = 20;
	private static final int CAST = 20;
	private static final double LENGTH = 1000;
	private static final Color COLOR = Color.BLUE;
	private static final int SIZE = 5;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = true;

	public WingardiumLeviosa(Unit caster, double positionX, double positionY, double orientation) {
		super(caster, positionX, positionY, WALL, HITTABLE, orientation, SPEED, CAST, COLOR, LENGTH, SIZE);
	}

	@Override
	public void hitWall(Block[][] blocks, int x, int y) {
		this.setExist(false);
	}

	@Override
	public void hitItems(List<Items> items, Items item) {
		this.getCaster().setLevitating(item);
		this.setExist(false);
	}

	@Override
	public void hitUnit(List<Unit> units, Unit unit) {
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
		return "Wingardium Leviosa";
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	@Override
	public int getId() {
		return WingardiumLeviosa.ID;
	}
}
