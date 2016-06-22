package cz.jstrolen.HP_RPG.game.ai;

import cz.jstrolen.HP_RPG.game.World;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;

import java.util.ArrayList;
import java.util.List;

public class KillPlayer extends AI {
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
	public void step(Unit Unit, World world) {
		/*double posX = world.getMap().getUnits().get(0).getPositionX(); TODO
		double posY = world.getMap().getUnits().get(0).getPositionY();
		double myX = Unit.getPositionX();
		double myY = Unit.getPositionY();
		double vector = Math.atan2(posY - myY, posX - myX);
		Unit.setSpell(Spell.getRandomDamagingSpell(Unit, vector).getId());
		Unit.cast(vector, world);
		if ((moves.size() / 3 < steps + 5) || (moves.isEmpty() && PathFinding.distance(world.getMap().getUnits().get(0), Unit) > MAX_DISTANE)) {
			moves = PathFinding.findPath(Unit, world.getMap().getBlocks(), world.getMap().getItems(), world.getMap().getUnits(),
					new int[]{(int) world.getMap().getUnits().get(0).getPositionX(), (int) world.getMap().getUnits().get(0).getPositionY()});
			steps = moves.size();
		}
		
		if (!moves.isEmpty() || Math.abs(difX) > 0 || Math.abs(difY) > 0) {
			if (PathFinding.distance(world.getMap().getUnits().get(0), Unit) < MIN_DISTANE) {
				moves.clear();
				difX = 0;
				difY = 0;
			}
			else if (Math.abs(difX) > 0 || Math.abs(difY) > 0) move(Unit, world);
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
		}*/
	}

	private void move(Unit Unit, World world) {
		/*double moveLength = Unit.getSpeed(); TODO
		if (difX > 0) {
			double length = Math.min(difX, moveLength);
			difX -= length;
			Unit.move(length, 0, world);
		}
		else if (difX < 0) {
			double length = Math.max(difX, -moveLength);
			difX -= length;
			Unit.move(length, 0, world);
		}
		else if (difY > 0) {
			double length = Math.min(difY, moveLength);
			difY -= length;
			Unit.move(0, length, world);
		}
		else if (difY < 0) {
			double length = Math.max(difY, -moveLength);
			difY -= length;
			Unit.move(0, length, world);
		}
		if (Math.abs(difX) < EPSILON) difX = 0;
		if (Math.abs(difY) < EPSILON) difY = 0;*/
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
