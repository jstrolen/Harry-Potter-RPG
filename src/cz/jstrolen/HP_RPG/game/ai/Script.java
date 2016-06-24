package cz.jstrolen.HP_RPG.game.ai;

import cz.jstrolen.HP_RPG.game.entities.units.Unit;
import cz.jstrolen.HP_RPG.game.maps.World;

import java.util.Random;

public class Script extends AI {
	public static final int ID = 0;
	private final String name;
	
	public Script(String name) {
		this.name = name;
	}

	@Override
	public synchronized void step(Unit unit, World world) {
		double speed = unit.getSpeed();
		Random ran = new Random();
		int type = ran.nextInt(4);
		switch (type) {
		case 0:
			world.move(unit, speed, 0);
			break;
		case 1:
			world.move(unit, 0, speed);
			break;
		case 2:
			world.move(unit, -speed, 0);
			break;
		case 3:
			world.move(unit, 0, -speed);
			break;
		}
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
