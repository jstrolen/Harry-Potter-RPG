package map;

import blocks.Block;
import items.Items;
import unit.Unit;

import java.awt.*;
import java.util.List;

public abstract class Maps {
	public static final String PATH = "map/";

	public abstract Block[][] getBlocks();

	public abstract Dimension getSize();

	public abstract String toString();

	public abstract List<Items> getItems();

	public abstract List<Unit> getUnits();

	public static Maps getMaps(String name) {
		return new UniversalMap(name);
	}
}
