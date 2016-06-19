package spells;

import items.Items;

import java.awt.Color;
import java.util.List;

import unit.Unit;
import blocks.Block;

public final class Incendio extends Spell {
	public static final int ID = 4;
	private static final int DAMAGE = 1;
	private static final double SPEED = 7;
	private static final int CAST = 1;
	private static final double LENGTH = 200;
	private static final Color COLOR = Color.ORANGE;
	private static final int SIZE = 6;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = false;
	private static final double DISPERSION = 2;

	public Incendio(Unit caster, double positionX, double positionY, double orientation) {
		super(caster, positionX, positionY, WALL, HITTABLE, orientation + (Math.random() / DISPERSION) - (1 / (2 * DISPERSION)), 
				SPEED, CAST, COLOR, LENGTH, SIZE);
	}

	@Override
	public void hitWall(Block[][] blocks, int x, int y) {
		blocks[x][y] = blocks[x][y].hit(Block.IGNITE);
		this.setExist(false);
	}

	@Override
	public void hitItems(List<Items> items, Items item) {
		Items it = item.hit(Items.IGNITE);
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
		//Nothing
	}
	
	@Override
	public String toString() {
		return "Incendio";
	}
	
	@Override
	public void setOrientation(double vector) {
		super.setOrientation(vector + (Math.random() / DISPERSION) - (1 / (2 * DISPERSION)));
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	@Override
	public int getId() {
		return Incendio.ID;
	}
}
