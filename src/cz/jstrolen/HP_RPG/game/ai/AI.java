package cz.jstrolen.HP_RPG.game.ai;

import cz.jstrolen.HP_RPG.game.World;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;

public abstract class AI {
	public abstract void step(Unit Unit, World world);
	
	public abstract int getId();
	
	public abstract String getName();
	
	public static AI getIntel(int id, String name) {
		switch (id) {
		case Script.ID:
			return new Script(name);
		case KillPlayer.ID:
			return new KillPlayer(name);
		default:
			return null;
		}
	}
}
