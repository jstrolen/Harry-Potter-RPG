package cz.jstrolen.HP_RPG.game.maps;

import cz.jstrolen.HP_RPG.game.GameSettings;
import cz.jstrolen.HP_RPG.game.entities.AEntity;
import cz.jstrolen.HP_RPG.game.entities.objects.Block;
import cz.jstrolen.HP_RPG.game.entities.objects.Item;
import cz.jstrolen.HP_RPG.game.entities.objects.ObjectFactory;
import cz.jstrolen.HP_RPG.game.entities.spells.Spell;
import cz.jstrolen.HP_RPG.game.entities.spells.SpellFactory;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static cz.jstrolen.HP_RPG.game.GameSettings.DRAW_DISTANCE;

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
			if (!map.isInMap(map.getSpells().get(i))) {
				map.getSpells().remove(i);
				i--;
				continue;
			}
			boolean hit = spellTryHit(map.getSpells().get(i));
			if (!distanceLeft || hit) {
				map.getSpells().remove(i);
				i--;
			}
		}

		for (int i = 0; i < map.getUnits().size(); i++) {
			if (map.getUnits().get(i).tick() == null) map.getUnits().remove(i--);
		}

		for (int i = 0; i < map.getUnits().size(); i++) {
			map.getUnits().get(i).run(this);
		}

		/* TODO
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
		*/
	}

	public void draw(Graphics2D g) {
		drawBlocks(g);
		drawItems(g);
		drawUnits(g);
		drawSpells(g);
		drawBars(g);
	}

	private void drawBlocks(Graphics2D g) {
		int drawDistance = GameSettings.DRAW_DISTANCE;
		int blockSize = ObjectFactory.getBlockSize();
		int startX = (int) Math.max((map.getUnits().get(0).getPositionX() - drawDistance) / blockSize, 0);
		int endX = (int) Math.min((map.getUnits().get(0).getPositionX() + drawDistance) / blockSize, map.getSize().width - 1);
		int startY = (int) Math.max((map.getUnits().get(0).getPositionY() - drawDistance) / blockSize, 0);
		int endY = (int) Math.min((map.getUnits().get(0).getPositionY() + drawDistance) / blockSize, map.getSize().width - 1);
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (Math.sqrt(Math.pow(map.getBlocks()[y][x].getPositionX() - map.getUnits().get(0).getPositionX(), 2) +		//TODO sqrt speed
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
	}

	private void drawBars(Graphics2D g) {
		if (GameSettings.DRAW_HEALTH_BAR) {
			g.setColor(GameSettings.HEALTH_BAR_COLOR);
			for (int i = 0; i < map.getUnits().size(); i++) {
				if (Math.sqrt(Math.pow(map.getUnits().get(i).getPositionX() - map.getUnits().get(0).getPositionX(), 2) +
						Math.pow(map.getUnits().get(i).getPositionY() - map.getUnits().get(0).getPositionY(), 2)) > DRAW_DISTANCE) continue;
				map.getUnits().get(i).drawHealthBar(g);
			}
		}

		if (GameSettings.DRAW_SPELL_BAR) {
			g.setColor(GameSettings.SPELL_BAR_COLOR);
			for (int i = 0; i < map.getUnits().size(); i++) {
				if (Math.sqrt(Math.pow(map.getUnits().get(i).getPositionX() - map.getUnits().get(0).getPositionX(), 2) +
						Math.pow(map.getUnits().get(i).getPositionY() - map.getUnits().get(0).getPositionY(), 2)) > DRAW_DISTANCE) continue;
				map.getUnits().get(i).drawSpellBar(g);
			}
		}
	}

	private void drawSpells(Graphics2D g) {
		for (int i = 0; i < map.getSpells().size(); i++) {
			map.getSpells().get(i).draw(g);
		}
	}

	public void move(AEntity entity, double diffX, double diffY) {
		double positionX = entity.getPositionX();
		double positionY = entity.getPositionY();
		entity.setPositionX(positionX + diffX);
		entity.setPositionY(positionY + diffY);
		if (tryIntersection(entity, false, true, false) == null && map.isInMap(entity)) return;

		double newX = positionX, newY = positionY;
		//X
		for (int i = 1; i < GameSettings.INTERSECTION_STEPS; i++) {
			newX = positionX + diffX / i;
			entity.setPositionX(newX);
			entity.setPositionY(newY);
			if (tryIntersection(entity, false, true, false) == null && map.isInMap(entity)) break;
			else newX = positionX;
		}
		//Y
		for (int i = 1; i < GameSettings.INTERSECTION_STEPS; i++) {
			newY = positionY + diffY / i;
			entity.setPositionX(newX);
			entity.setPositionY(newY);
			if (tryIntersection(entity, false, true, false) == null && map.isInMap(entity)) break;
			else newY = positionY;
		}
		entity.setPositionX(newX);
		entity.setPositionY(newY);
	}

	private boolean spellTryHit(Spell spell) { //TODO
		AEntity intersect = tryIntersection(spell, true, false, false);
		if (intersect == null) return false;
		AEntity newEntity = intersect.hit(spell);
		if (newEntity != intersect) changeEntity(intersect, newEntity);
		return true;
	}

	private void changeEntity(AEntity entity, AEntity newEntity) {
		if (entity instanceof Block) { map.changeBlock((Block) entity, (Block) newEntity); }
		else if (entity instanceof Item) {
			if (newEntity == null) map.getItems().remove(entity);
			else map.getItems().set(map.getItems().indexOf(entity), (Item) newEntity);
		}
		else if (entity instanceof Unit) {
			if (newEntity == null) map.getUnits().remove(entity);
		}
	}

	private AEntity tryIntersection(AEntity entity, boolean detectHittable, boolean detectNotCrossable, boolean detectNotFlyable) {
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
				if (entity instanceof Spell) if (((Spell) entity).getCaster() == unit) continue;
				return unit;
			}
		}
		return null;
	}

	public void createNewSpell(Unit caster, double orientation, boolean self) {
		Spell spell = SpellFactory.getSpell(caster, orientation, self);
		if (spell != null && spell.getCreateInstance()) map.getSpells().add(spell);
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
