package ai;

import general.Map;

import java.util.Random;

import unit.Unit;

public class Script extends Intel {
	public static final int ID = 0;
	private String name;
	
	public Script(String name) {
		this.name = name;
	}

	@Override
	public synchronized void step(Unit unit, Map map) {
		double speed = unit.getSpeed();
		Random ran = new Random();
		int type = ran.nextInt(4);
		switch (type) {
		case 0:
			unit.move(speed, 0, map);
			break;
		case 1:
			unit.move(0, speed, map);
			break;
		case 2:
			unit.move(-speed, 0, map);
			break;
		case 3:
			unit.move(0, -speed, map);
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
