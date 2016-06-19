package spells;

import items.Items;

import java.awt.Color;
import java.util.List;

import unit.Unit;
import blocks.Block;

public class AvadaKedavra extends Spell {
	public static final int ID = 10;
	private static final double SPEED = 25;
	private static final int CAST = 45;
	private static final double LENGTH = 750;
	private static final Color COLOR = new Color(0, 150, 0);
	private static final int SIZE = 10;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = true;
	private static final int DAMAGE = Integer.MAX_VALUE;

	public AvadaKedavra(Unit caster, double positionX, double positionY, double orientation) {
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
		if (unit.injured(DAMAGE)) units.remove(unit);
		this.setExist(false);
	}
	
	@Override
	public void hitSpell(List<Spell> spells, Spell spell) {
		spell.setExist(false);
	}
	
	@Override
	public String toString() {
		return "Avada Kedavra";
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	@Override
	public int getId() {
		return AvadaKedavra.ID;
	}
}
