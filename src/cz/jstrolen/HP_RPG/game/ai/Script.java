package cz.jstrolen.HP_RPG.game.ai;

import cz.jstrolen.HP_RPG.game.World;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;

public class Script extends AI {
	public static final int ID = 0;
	private String name;
	
	public Script(String name) {
		this.name = name;
	}

	@Override
	public synchronized void step(Unit Unit, World world) {
		/*double speed = Unit.getSpeed(); TODO
		Random ran = new Random();
		int type = ran.nextInt(4);
		switch (type) {
		case 0:
			Unit.move(speed, 0, world);
			break;
		case 1:
			Unit.move(0, speed, world);
			break;
		case 2:
			Unit.move(-speed, 0, world);
			break;
		case 3:
			Unit.move(0, -speed, world);
			break;
		} */
	}

	@Override
	public int getId() {
		return Script.ID;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
