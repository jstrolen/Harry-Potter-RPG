package cz.jstrolen.HP_RPG.game;

import cz.jstrolen.HP_RPG.game.entities.AEntity;
import cz.jstrolen.HP_RPG.game.entities.objects.Block;
import cz.jstrolen.HP_RPG.game.entities.objects.Item;
import cz.jstrolen.HP_RPG.game.entities.objects.ObjectFactory;
import cz.jstrolen.HP_RPG.game.entities.spells.Spell;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;
import cz.jstrolen.HP_RPG.game.maps.Map;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static cz.jstrolen.HP_RPG.game.Settings.DRAW_DISTANCE;

/**
 * Created by Josef Stroleny
 */
public class World {
	private static final World singleton = new World();
	private Map map;
	
	private World() {}

	public void tick() {
		for (int i = 0; i < map.getSpells().size(); i++) {
			boolean distanceLeft = map.getSpells().get(i).move();
			boolean hit = tryHit(map.getSpells().get(i));
			if (!distanceLeft || hit) {
				map.getSpells().remove(i);
				i--;
			}
		}

		List<Thread> threads = new ArrayList<>(map.getUnits().size());
		for (int i = 0; i < map.getUnits().size(); i++) {
			Thread t = new Thread(map.getUnits().get(i));
			threads.add(t);
			t.start();
		}
		for (int i = 0; i < threads.size(); i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void draw(Graphics2D g) {
		drawBlocks(g);
		drawItems(g);
		drawUnits(g);
		drawSpells(g);
	}

	private void drawBlocks(Graphics2D g) {
		int drawDistance = Settings.DRAW_DISTANCE;
		int blockSize = ObjectFactory.getBlockSize();
		int startX = (int) Math.max((map.getUnits().get(0).getPositionX() - drawDistance) / blockSize, 0);
		int endX = (int) Math.min((map.getUnits().get(0).getPositionX() + drawDistance) / blockSize, map.getSize().width - 1);
		int startY = (int) Math.max((map.getUnits().get(0).getPositionY() - drawDistance) / blockSize, 0);
		int endY = (int) Math.min((map.getUnits().get(0).getPositionY() + drawDistance) / blockSize, map.getSize().width - 1);
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (Math.sqrt(Math.pow(map.getBlocks()[y][x].getPositionX() - map.getUnits().get(0).getPositionX(), 2) +
						Math.pow(map.getBlocks()[y][x].getPositionY() - map.getUnits().get(0).getPositionY(), 2)) > DRAW_DISTANCE) continue;
				map.getBlocks()[y][x].draw(g);
			}
		}
	}

	private void drawItems(Graphics2D g) {
		for (int i = 0; i < map.getItems().size(); i++) {
			if (Math.sqrt(Math.pow((map.getItems().get(i).getPositionX() - map.getUnits().get(0).getPositionX()), 2) +
					Math.pow((map.getItems().get(i).getPositionY() - map.getUnits().get(0).getPositionY()), 2)) > DRAW_DISTANCE) continue;
			map.getItems().get(i).draw(g);
		}
	}

	private void drawUnits(Graphics2D g) {
		for (int i = 0; i < map.getUnits().size(); i++) {
			if (Math.sqrt(Math.pow(map.getUnits().get(i).getPositionX() - map.getUnits().get(0).getPositionX(), 2) +
					Math.pow(map.getUnits().get(i).getPositionY() - map.getUnits().get(0).getPositionY(), 2)) > DRAW_DISTANCE) continue;
			map.getUnits().get(i).draw(g);
		}

		/* TODO
		for (int i = 0; i < casting.size(); i++) {
			if (!map.getUnits().contains(casting.get(i).getCaster())) {
				casting.remove(i);
				i--;
				continue;
			}
			if (Math.sqrt(Math.pow(casting.get(i).getPositionX() - map.getUnits().get(0).getPositionX(), 2) +
					Math.pow(casting.get(i).getPositionY() - map.getUnits().get(0).getPositionY(), 2)) > DRAW_DISTANCE) continue;
			casting.get(i).loading(g, casting.get(i).getCaster());
		}
		*/
	}

	private void drawSpells(Graphics2D g) {
		for (int i = 0; i < map.getSpells().size(); i++) {
			map.getSpells().get(i).draw(g);
		}
	}

	public void move(AEntity entity, double diffX, double diffY) {
		/*if (levitating == null) {//TODO*/
		double positionX = entity.getPositionX();
		double positionY = entity.getPositionY();
		entity.setPositionX(positionX + diffX);
		entity.setPositionY(positionY + diffY);
		if (tryIntersection(entity, false, true, false) == null) return;
		entity.setPositionX(positionX);
		entity.setPositionY(positionY);
		/*}
		else {
			double[] position = map.tryMove(levitating, levitating.getPositionX(), levitating.getPositionY(), difX, difY, levitating.getSIZE_X(), levitating.getSIZE_Y());
			levitating.setPositionX(levitating.getPositionX() + position[0]);
			levitating.setPositionY(levitating.getPositionY() + position[1]);
		}*/
	}

	public boolean tryHit(Spell spell) { //TODO
		AEntity intersect = tryIntersection(spell, true, false, false);
		if (intersect != null) return true;
		return false;
	}

	public AEntity tryIntersection(AEntity entity, boolean detectHittable, boolean detectNotCrossable, boolean detectNotFlyable) {
		Rectangle2D bound = entity.getBounds();
		Block block;
		int startX = (int) (entity.getPositionX() / ObjectFactory.getBlockSize());
		int endX = (int) ((entity.getPositionX() + entity.getSizeX()) / ObjectFactory.getBlockSize());
		int startY = (int) (entity.getPositionY() / ObjectFactory.getBlockSize());
		int endY = (int) ((entity.getPositionY() + entity.getSizeY()) / ObjectFactory.getBlockSize());
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				block = map.getBlocks()[y][x];
				if ((!detectHittable || block.getAttributes().isHittable()) &&
						(!detectNotCrossable || !block.getAttributes().isCrossable()) &&
						(!detectNotFlyable || !block.getAttributes().isFlyable()) &&
						block.getBounds().intersects(bound)) {
					return block;
				}
			}
		}
		for (int i = 0; i < map.getItems().size(); i++) {
			Item item = map.getItems().get(i);
			if ((!detectHittable || item.getAttributes().isHittable()) &&
					(!detectNotCrossable || !item.getAttributes().isCrossable()) &&
					(!detectNotFlyable || !item.getAttributes().isFlyable()) &&
					item.getBounds().intersects(bound)) {
				return item;
			}
		}
		for (int i = 0; i < map.getUnits().size(); i++) {
			Unit unit = map.getUnits().get(i);
			if (unit != entity && unit.getBounds().intersects(bound)) {
				return unit;
			}
		}
		return null;
	}
	
	public int getWidth() {
		return map.getSize().width;
	}
	
	public int getHeight() {
		return map.getSize().height;
	}
	
	public Unit getPlayer() {
		return map.getUnits().get(0);
	}
	
	public Map getMap() {
		return this.map;
	}
	
	public void setMap(Map newMap) {
		this.map = newMap;
	}
	
	public static World getInstance() {
		return singleton;
	}
}
