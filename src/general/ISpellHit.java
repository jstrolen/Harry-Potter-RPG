package general;

import blocks.Block;
import items.Items;
import spells.Spell;
import unit.Unit;

import java.util.List;

public interface ISpellHit {
	public void hitWall(Block[][] blocks, int x, int y);	//rename hitBlock
	public void hitItems(List<Items> items, Items item);
	public void hitUnit(List<Unit> units, Unit unit);
	public void hitSpell(List<Spell> spells, Spell spell);
}
