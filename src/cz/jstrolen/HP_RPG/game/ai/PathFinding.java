package cz.jstrolen.HP_RPG.game.ai;

import cz.jstrolen.HP_RPG.game.entities.objects.ObjectFactory;
import cz.jstrolen.HP_RPG.game.entities.objects.AObject;
import cz.jstrolen.HP_RPG.game.entities.objects.Item;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class PathFinding {
	
	public static List<Container> findPath(Unit unit, AObject[][] objects, List<Item> items, List<Unit> units, int[] end) {
		List<Container> moves = new ArrayList<>();
		int endX = end[0] / ObjectFactory.getBlockSize();
		int endY = end[1] / ObjectFactory.getBlockSize();
		int startX = (int) unit.getPositionX() / ObjectFactory.getBlockSize();
		int startY = (int) unit.getPositionY() / ObjectFactory.getBlockSize();
		if (startX == endX && startY == endY) {
			trim(moves, unit, startX, startY);
			return moves;
		}
		Deque<BlockHelp> list = new LinkedList<>();
		
		BlockHelp[][] blHlp = new BlockHelp[objects.length][objects[0].length];
		for (int y = 0; y < objects.length; y++) {
			for (int x = 0; x < objects[0].length; x++) {
				blHlp[y][x] = new BlockHelp(objects[y][x], x, y);
			}
		}
		blHlp[startY][startX].predecessor = blHlp[startY][startX];
		list.addLast(blHlp[startY][startX]);
		BlockHelp pom = list.removeFirst();
		while (pom.object != objects[endY][endX]) {
			try {
				if (tryMove(unit, blHlp, pom, -1, 0)) {
					list.addLast(blHlp[pom.y - 1][pom.x]);
					blHlp[pom.y - 1][pom.x].predecessor = pom;
				}
			} catch (Exception e) {}
			try {
				if (tryMove(unit, blHlp, pom, 1, 0)) {
					list.addLast(blHlp[pom.y + 1][pom.x]);
					blHlp[pom.y + 1][pom.x].predecessor = pom;
				}
			} catch (Exception e) {}
			try {
				if (tryMove(unit, blHlp, pom, 0, -1)) {
					list.addLast(blHlp[pom.y][pom.x - 1]);
					blHlp[pom.y][pom.x - 1].predecessor = pom;
				}
			} catch (Exception e) {}
			try {
				if (tryMove(unit, blHlp, pom, 0, 1)) {
					list.addLast(blHlp[pom.y][pom.x + 1]);
					blHlp[pom.y][pom.x + 1].predecessor = pom;
				}
			} catch (Exception e) {}
			if (list.isEmpty()) {
				trim(moves, unit, startX, startY);
				return moves;
			}
			else pom = list.removeFirst();
		}
		
		while (pom.object != objects[startY][startX]) {
			int x = pom.x;
			int y = pom.y;
			pom = pom.predecessor;
			if (x < pom.x && y == pom.y) moves.add(new Container(EMoves.LEFT, ObjectFactory.getBlockSize()));
			else if (x > pom.x && y == pom.y) moves.add(new Container(EMoves.RIGHT, ObjectFactory.getBlockSize()));
			else if (x == pom.x && y < pom.y) moves.add(new Container(EMoves.UP, ObjectFactory.getBlockSize()));
			else if (x == pom.x && y > pom.y) moves.add(new Container(EMoves.DOWN, ObjectFactory.getBlockSize()));
		}
		
		trim(moves, unit, startX, startY);
		return moves;
	}
	
	private static boolean tryMove(Unit move, BlockHelp[][] blHlp, BlockHelp pom, int x, int y) {
		if (blHlp[pom.y + y][pom.x + x].predecessor != null) return false;
		
		int sizeX = (int) ((move.getAttributes().getSizeX() - 0.01) / ObjectFactory.getBlockSize());
		int sizeY = (int) ((move.getAttributes().getSizeY() - 0.01) / ObjectFactory.getBlockSize());
		
		boolean possible = true;
		for (int i = 0; i <= sizeY; i++) {
			for (int j = 0; j <= sizeX; j++) {
				if (!blHlp[pom.y + y + i][pom.x + x + j].object.getAttributes().isCrossable()) {
					possible = false;
					break;
				}
			}
			if (!possible) break;
		}
		
		return possible;
	}
	
	private static void trim(List<Container> moves, Unit move, int startX, int startY) {
		double difX = move.getPositionX() - startX * ObjectFactory.getBlockSize();
		double difY = move.getPositionY() - startY * ObjectFactory.getBlockSize();
		if (difX != 0.0) moves.add(new Container(EMoves.LEFT, difX));
		if (difY != 0.0) moves.add(new Container(EMoves.UP, difY));
	}
	
	public static double distance(Unit alpha, Unit beta) {
		return Math.sqrt(Math.pow(alpha.getPositionX() - beta.getPositionX(), 2) + Math.pow(alpha.getPositionY() - beta.getPositionY(), 2));
	}
}

class BlockHelp {
	final AObject object;
	BlockHelp predecessor;
	final int x;
	final int y;
	
	public BlockHelp(AObject object, int x, int y) {
		this.object = object;
		predecessor = null;
		this.x = x;
		this.y = y;
	}
}
