package spells;

import items.Items;

import java.awt.Color;
import java.util.List;

import unit.Unit;
import blocks.Block;

public final class Protego extends Spell {
	public static final int ID = 5;
	private static final double SPEED = 0.5;
	private static final int CAST = 10;
	private static final double LENGTH = 50;
	private static final Color COLOR = Color.BLUE;
	private static final int SIZE = 50;
	private static final boolean WALL = true;
	private static final boolean HITTABLE = true;

	public Protego(Unit caster, double positionX, double positionY, double orientation) {
		super(caster, positionX, positionY, WALL, HITTABLE, orientation, SPEED, CAST, COLOR, LENGTH, SIZE);
	}

	@Override
	public void hitWall(Block[][] blocks, int x, int y) {
		//Nothing
	}

	@Override
	public void hitItems(List<Items> items, Items item) {
		//Nothing
	}

	@Override
	public void hitUnit(List<Unit> units, Unit unit) {
		//Nothing
	}
	
	@Override
	public void hitSpell(List<Spell> spells, Spell spell) {
		//Nothing
	}
	
	@Override
	public String toString() {
		return "Protego";
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	@Override
	public int getId() {
		return Protego.ID;
	}
}
