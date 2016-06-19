package ai;

import general.Map;
import unit.Unit;

public abstract class Intel {
	public abstract void step(Unit unit, Map map);
	
	public abstract int getId();
	
	public abstract String getName();
	
	public static Intel getIntel(int id, String name) {
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
