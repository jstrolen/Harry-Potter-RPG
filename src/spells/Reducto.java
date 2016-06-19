package spells;

import items.Items;

import java.awt.Color;
import java.util.List;

import unit.Unit;
import blocks.Block;

public final class Reducto extends Spell{
	public static final int ID = 6;
	private static final int DAMAGE = 5;
	private static final double SPEED = 10;
	private static final int CAST = 25;
	private static final double LENGTH = 1000;
	private static final Color COLOR = new Color(150,0,0);
	private static final int SIZE = 10;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = true;

	public Reducto(Unit caster, double positionX, double positionY, double orientation) {
		super(caster, positionX, positionY, WALL, HITTABLE, orientation, SPEED, CAST, COLOR, LENGTH, SIZE);
	}

	@Override
	public void hitWall(Block[][] blocks, int x, int y) {
		blocks[x][y] = blocks[x][y].hit(Block.DESTROY);
		this.setExist(false);
	}

	@Override
	public void hitItems(List<Items> items, Items item) {
		Items it = item.hit(Items.DESTROY);
		if (it == null) items.remove(item);
		else if (it != item) {
			items.set(items.indexOf(item), it);
		}
		this.setExist(false);
	}

	@Override
	public void hitUnit(List<Unit> units, Unit unit) {
		if (unit.injured(DAMAGE)) units.remove(unit);
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
		return "Reducto";
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	@Override
	public int getId() {
		return Reducto.ID;
	}
}
