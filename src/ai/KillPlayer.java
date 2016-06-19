package ai;

import general.Map;

import java.util.ArrayList;
import java.util.List;

import spells.Spell;
import unit.Unit;

public class KillPlayer extends Intel {
	public static final int ID = 1;
	private static final double EPSILON = 0.01;
	private static final int MIN_DISTANE = 100;
	private static final int MAX_DISTANE = 250;
	private String name;
	private List<Container> moves = new ArrayList<Container>();
	private double difX = 0;
	private double difY = 0;
	private int steps = 0;
	
	public KillPlayer(String name) {
		this.name = name;
	}

	@Override
	public void step(Unit unit, Map map) {
		double posX = map.getMap().getUnits().get(0).getPositionX();
		double posY = map.getMap().getUnits().get(0).getPositionY();
		double myX = unit.getPositionX();
		double myY = unit.getPositionY();
		double vector = Math.atan2(posY - myY, posX - myX);
		unit.setSpell(Spell.getRandomDamagingSpell(unit, vector).getId());
		unit.cast(vector, map);
		if ((moves.size() / 3 < steps + 5) || (moves.isEmpty() && PathFinding.distance(map.getMap().getUnits().get(0), unit) > MAX_DISTANE)) { 
			moves = PathFinding.findPath(unit, map.getMap().getBlocks(), map.getMap().getItems(), map.getMap().getUnits(), 
					new int[]{(int)map.getMap().getUnits().get(0).getPositionX(), (int)map.getMap().getUnits().get(0).getPositionY()});
			steps = moves.size();
		}
		
		if (!moves.isEmpty() || Math.abs(difX) > 0 || Math.abs(difY) > 0) {
			if (PathFinding.distance(map.getMap().getUnits().get(0), unit) < MIN_DISTANE) {
				moves.clear();
				difX = 0;
				difY = 0;
			}
			else if (Math.abs(difX) > 0 || Math.abs(difY) > 0) move(unit, map);
			else if (!moves.isEmpty()) {
				Container move = moves.remove(moves.size() - 1);
				if (move.getMove() == EMoves.LEFT) {
					difX -= move.getSize();
				}
				else if (move.getMove() == EMoves.RIGHT) {
					difX += move.getSize();
				}
				else if (move.getMove() == EMoves.UP) {
					difY -= move.getSize();
				}
				else {
					difY += move.getSize();
				}
			}	
		}
	}

	private void move(Unit unit, Map map) {
		double moveLength = unit.getSpeed();
		if (difX > 0) {
			double length = Math.min(difX, moveLength);
			difX -= length;
			unit.move(length, 0, map);
		}
		else if (difX < 0) {
			double length = Math.max(difX, -moveLength);
			difX -= length;
			unit.move(length, 0, map);
		}
		else if (difY > 0) {
			double length = Math.min(difY, moveLength);
			difY -= length;
			unit.move(0, length, map);
		}
		else if (difY < 0) {
			double length = Math.max(difY, -moveLength);
			difY -= length;
			unit.move(0, length, map);
		}
		if (Math.abs(difX) < EPSILON) difX = 0;
		if (Math.abs(difY) < EPSILON) difY = 0;
	}

	@Override
	public int getId() {
		return KillPlayer.ID;
	}

	@Override
	public String getName() {
		return name;
	}
}
