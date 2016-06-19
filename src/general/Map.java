package general;

import blocks.Block;
import blocks.Teleport;
import items.Items;
import map.Maps;
import spells.Spell;
import unit.Unit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
	private static final Map singleton = new Map();
	private static final int DRAW_DISTANCE = 1000;
	private static final int STEPS = 5;
	
	private Maps map;
	private List<Spell> spells = new ArrayList<Spell>();
	private List<Spell> casting = new ArrayList<Spell>();
	private Block teleport = null;
	
	private Map() {}
	
	public void nakresli(Graphics2D g) {
		int startX = (int) Math.max(((map.getUnits().get(0).getPositionX() - DRAW_DISTANCE) / Block.BLOCK_SIZE), 0);
		int endX = (int) Math.min(((map.getUnits().get(0).getPositionX() + DRAW_DISTANCE) / Block.BLOCK_SIZE), map.getSize().width - 1);
		int startY = (int) Math.max(((map.getUnits().get(0).getPositionY() - DRAW_DISTANCE) / Block.BLOCK_SIZE), 0);
		int endY = (int) Math.min(((map.getUnits().get(0).getPositionY() + DRAW_DISTANCE) / Block.BLOCK_SIZE), map.getSize().height - 1);
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (Math.sqrt(Math.pow(map.getBlocks()[x][y].getPositionX() * Block.BLOCK_SIZE - map.getUnits().get(0).getPositionX(), 2) + 
						Math.pow(map.getBlocks()[x][y].getPositionY() * Block.BLOCK_SIZE - map.getUnits().get(0).getPositionY(), 2)) > DRAW_DISTANCE) continue;
				map.getBlocks()[x][y].nakresli(g);
			}
		}
		
		for (int i = 0; i < map.getItems().size(); i++) {
			if (Math.sqrt(Math.pow((map.getItems().get(i).getPositionX() - map.getUnits().get(0).getPositionX()), 2) + 
					Math.pow((map.getItems().get(i).getPositionY() - map.getUnits().get(0).getPositionY()), 2)) > DRAW_DISTANCE) continue;
			map.getItems().get(i).nakresli(g);
		}
		
		for (int i = 0; i < map.getUnits().size(); i++) {
			if (Math.sqrt(Math.pow(map.getUnits().get(i).getPositionX() - map.getUnits().get(0).getPositionX(), 2) + 
					Math.pow(map.getUnits().get(i).getPositionY() - map.getUnits().get(0).getPositionY(), 2)) > DRAW_DISTANCE) continue;
			map.getUnits().get(i).nakresli(g);
		}
		
		for (int i = 0; i < spells.size(); i++) {
			spells.get(i).nakresli(g);
		}
		
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
	}
	
	public void run() {
		for (int i = 0; i < spells.size(); i++) {
			if (!spells.get(i).nextStep(this)) {
				spells.remove(i);
				i--;
			}
		}
		List<Thread> threads = new ArrayList<>(map.getUnits().size());
		for (int i = 0; i < map.getUnits().size(); i++) {
			Thread t = new Thread(map.getUnits().get(i));
			t.start();
			threads.add(t);
		}
		for (int i = 0; i < threads.size(); i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public double[] tryMove(Item item, double posX, double posY, double difX, double difY, double sizeX, double sizeY) {
		double diferences[] = new double[2];
		int type = 0;
		double px = difX / Map.STEPS;
		double py = difY / Map.STEPS;
		diferences[0] = 0.0;
		diferences[1] = 0.0;
		for (int i = 0; i < Map.STEPS; i++) {
			if (type == 0 || type == 1){
				double pomX = diferences[0] + px;
				if (!move(item, posX + pomX, posY + diferences[1], sizeX, sizeY)) {
					if (type == 0) type = 2;
					else type = 3;
				}
				else {
					diferences[0] = pomX;
				}
			}
			if (type == 0 || type == 2) {
				double pomY = diferences[1] + py;
				if (!move(item, posX + diferences[0], posY + pomY, sizeX, sizeY)) {
					if (type == 0) type = 1;
					else type = 3;
				}
				else {
					diferences[1] = pomY;
				}
			}
			if (type >= 3) break;
		}
		if (teleport != null) {
			String name = ((Teleport) teleport).getDestinationName();
			int desX = ((Teleport) teleport).getDestinationX();
			int desY = ((Teleport) teleport).getDestinationY();
			Unit myUnit = map.getUnits().get(0);
			myUnit.setPositionX(desX);
			myUnit.setPositionY(desY);
			Maps newMap = Maps.getMaps(name);
			if (newMap.getUnits().size() > 0) newMap.getUnits().set(0, myUnit);
			else newMap.getUnits().add(myUnit);
			this.map = newMap;
			teleport = null;
			return new double[]{0.0, 0.0};
		}
		else return diferences;
	}
	
	private boolean move(Item item, double x, double y, double sizeX, double sizeY) {
		Rectangle r = new Rectangle((int) x, (int) y, (int) sizeX, (int) sizeY);
		
		if (x < 0 || y < 0) return false;
		if (x + sizeX >= map.getSize().width * Block.BLOCK_SIZE || y + sizeY >= map.getSize().height * Block.BLOCK_SIZE) return false;
		int startX = (int) (x / Block.BLOCK_SIZE);
		int endX = (int) ((x + sizeX) / Block.BLOCK_SIZE);
		int startY = (int) (y / Block.BLOCK_SIZE);
		int endY = (int) ((y + sizeY) / Block.BLOCK_SIZE);
		for (int pomX = startX; pomX <= endX; pomX++) {
			for (int pomY = startY; pomY <= endY; pomY++) {
				if (map.getBlocks()[pomX][pomY].isWall() && map.getBlocks()[pomX][pomY].contains(r)) return false;
				else if ((map.getBlocks()[pomX][pomY] instanceof Teleport) && (item == map.getUnits().get(0)) && map.getBlocks()[pomX][pomY].contains(r)) {
					teleport = map.getBlocks()[pomX][pomY];
					return false;
				}
			}
		}
		
		for (int i = 0; i < map.getItems().size(); i++) {
			if (item == map.getItems().get(i)) continue;
			if (map.getItems().get(i).isWall() && map.getItems().get(i).contains(r)) return false;
		}
		
		for (int i = 0; i < map.getUnits().size(); i++) {
			if (item == map.getUnits().get(i)) continue;
			try {
				if (map.getUnits().get(i).isWall() && map.getUnits().get(i).contains(r)) return false;
			} catch (NullPointerException e) {
				//Unit doesn't exist
			}
		}
		
		return true;
	}
	
	public double[] tryHit(Spell spell, double posX, double posY, double difX, double difY, double sizeX, double sizeY) {
		double diferences[] = new double[2];
		Item item = null;
		double px = difX / Map.STEPS;
		double py = difY / Map.STEPS;
		diferences[0] = 0;
		diferences[1] = 0;
		for (int i = 0; i < Map.STEPS; i++) {
			double pomX = diferences[0] + px;
			if ((item = hit(spell, posX + pomX, posY + diferences[1], sizeX, sizeY)) != null) {
				if (item instanceof Block) spell.hitWall(map.getBlocks(), (int) item.getPositionX(), (int) item.getPositionY());
				else if (item instanceof Items) spell.hitItems(map.getItems(), (Items) item);
				else if (item instanceof Unit) spell.hitUnit(map.getUnits(), (Unit) item);
				else if (item instanceof Spell) spell.hitSpell(spells, (Spell) item);
				return diferences;
			}
			else {
				diferences[0] = pomX;
			}
			double pomY = diferences[1] + py;
			if ((item = hit(spell, posX + diferences[0], posY + pomY, sizeX, sizeY)) != null) {
				if (item instanceof Block) spell.hitWall(map.getBlocks(), (int)item.getPositionX(), (int)item.getPositionY());
				else if (item instanceof Items) spell.hitItems(map.getItems(), (Items) item);
				else if (item instanceof Unit) spell.hitUnit(map.getUnits(), (Unit) item);
				else if (item instanceof Spell) spell.hitSpell(spells, (Spell) item);
				return diferences;
			}
			else {
				diferences[1] = pomY;
			}
		}
		return diferences;
	}
	
	private Item hit(Spell spell, double x, double y, double sizeX, double sizeY) {
		Rectangle r = new Rectangle((int) x, (int) y, (int) sizeX, (int) sizeY);
		
		if (x < 0 || y < 0) return null;
		if (x + sizeX >= map.getSize().width * Block.BLOCK_SIZE || y + sizeY >= map.getSize().height * Block.BLOCK_SIZE) return null;
		int startX = (int) (x / Block.BLOCK_SIZE);
		int endX = (int) ((x + sizeX) / Block.BLOCK_SIZE);
		int startY = (int) (y / Block.BLOCK_SIZE);
		int endY = (int) ((y + sizeY) / Block.BLOCK_SIZE);
		for (int pomX = startX; pomX <= endX; pomX++) {
			for (int pomY = startY; pomY <= endY; pomY++) {
				if (map.getBlocks()[pomX][pomY].isHittable() && map.getBlocks()[pomX][pomY].contains(r)) return map.getBlocks()[pomX][pomY];
			}
		}
		
		for (int i = 0; i < map.getItems().size(); i++) {
			if (map.getItems().get(i).isHittable() && map.getItems().get(i).contains(r)) return map.getItems().get(i);
		}
		
		for (int i = 0; i < map.getUnits().size(); i++) {
			if (map.getUnits().get(i) == spell.getCaster()) continue;
			if (map.getUnits().get(i).isWall() && map.getUnits().get(i).contains(r)) return map.getUnits().get(i);
		}
		
		for (int i = 0; i < spells.size(); i++) {
			if (spells.get(i) == spell) continue;
			if (spells.get(i).isHittable() && spells.get(i).contains(r)) return spells.get(i);
		}
		
		return null;
	}
	
	public int getSizeX() {
		return map.getSize().width;
	}
	
	public int getSizeY() {
		return map.getSize().height;
	}
	
	public Unit getPlayer() {
		return map.getUnits().get(0);
	}
	
	public void addCasting(Spell s) {
		casting.add(s);
	}
	
	public void removeCasting(Spell s) {
		casting.remove(s);
	}
	
	public void newSpell(Spell spell) {
		spells.add(spell);
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
	
	public Maps getMap() {
		return this.map;
	}
	
	public void setMap(Maps newMap) {
		this.map = newMap;
	}
	
	public static Map getInstance() {
		return singleton;
	}
}
