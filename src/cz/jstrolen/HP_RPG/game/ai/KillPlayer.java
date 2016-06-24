package cz.jstrolen.HP_RPG.game.ai;

import cz.jstrolen.HP_RPG.game.entities.units.Unit;
import cz.jstrolen.HP_RPG.game.maps.World;

import java.util.ArrayList;
import java.util.List;

public class KillPlayer extends AI {
	public static final int ID = 1;
	private static final double EPSILON = 0.01;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 250;
	private final String name;
	private List<Container> moves = new ArrayList<>();
	private double difX = 0;
	private double difY = 0;
	private int steps = 0;
	
	public KillPlayer(String name) {
		this.name = name;
	}

	@Override
	public void step(Unit unit, World world) {
		double posX = world.getPlayer().getPositionX();
		double posY = world.getPlayer().getPositionY();
		double myX = unit.getPositionX();
		double myY = unit.getPositionY();
		double vector = Math.atan2(posY - myY, posX - myX);
		unit.setSpell(1);
		world.createNewSpell(unit, vector, false);
		if ((moves.size() / 3 < steps + 5) || (moves.isEmpty() && PathFinding.distance(world.getMap().getUnits().get(0), unit) > MAX_DISTANCE)) {
			moves = PathFinding.findPath(unit, world.getMap().getBlocks(), world.getMap().getItems(), world.getMap().getUnits(),
					new int[]{(int) world.getMap().getUnits().get(0).getPositionX(), (int) world.getMap().getUnits().get(0).getPositionY()});
			steps = moves.size();
		}
		
		if (!moves.isEmpty() || Math.abs(difX) > 0 || Math.abs(difY) > 0) {
			if (PathFinding.distance(world.getMap().getUnits().get(0), unit) < MIN_DISTANCE) {
				moves.clear();
				difX = 0;
				difY = 0;
			}
			else if (Math.abs(difX) > 0 || Math.abs(difY) > 0) move(unit, world);
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

	private void move(Unit unit, World world) {
		double moveLength = unit.getSpeed();
		if (difX > 0) {
			double length = Math.min(difX, moveLength);
			difX -= length;
			world.move(unit, length, 0);
		}
		else if (difX < 0) {
			double length = Math.max(difX, -moveLength);
			difX -= length;
			world.move(unit, length, 0);
		}
		else if (difY > 0) {
			double length = Math.min(difY, moveLength);
			difY -= length;
			world.move(unit, 0, length);
		}
		else if (difY < 0) {
			double length = Math.max(difY, -moveLength);
			difY -= length;
			world.move(unit, 0, length);
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
