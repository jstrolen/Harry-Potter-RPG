package cz.jstrolen.HP_RPG.game.maps;

import cz.jstrolen.HP_RPG.game.entities.objects.Block;
import cz.jstrolen.HP_RPG.game.entities.objects.Item;
import cz.jstrolen.HP_RPG.game.entities.objects.ObjectFactory;
import cz.jstrolen.HP_RPG.game.entities.objects.Teleport;
import cz.jstrolen.HP_RPG.game.entities.spells.Spell;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;
import cz.jstrolen.HP_RPG.game.entities.units.UnitFactory;
import cz.jstrolen.HP_RPG.support.Input;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josef Stroleny
 */
public class Map {
	private final String NAME;
	private int sizeX;
	private int sizeY;
	private Block[][] blocks;
	private List<Item> items;
	private List<Unit> units;
	private List<Spell> spells;

	public Map(String name, String path) {
		this.NAME = name;
		load(path);
	}

	private void load(String path) {
		items = new ArrayList<>();
		units = new ArrayList<>();
		spells = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(Input.getFile(path)));
			String dimension = br.readLine();
			String[] size = dimension.split("x");
			sizeX = Integer.parseInt(size[0]);
			sizeY = Integer.parseInt(size[1]);
			blocks = new Block[sizeY][sizeX];
			
			for (int y = 0; y < sizeY; y++) {
				String line = br.readLine();
				String[] blocks = line.split(" ");
				for (int x = 0; x < sizeX; x++) {
					String[] params = blocks[x].split("-");
					if (params.length == 1) this.blocks[y][x] = ObjectFactory.getBlock(Integer.parseInt(blocks[x]), x, y);
					else {
						Teleport tel = new Teleport(params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]));
						this.blocks[y][x] = ObjectFactory.getBlock(Integer.parseInt(params[0]), x, y);
						this.blocks[y][x].setTeleport(tel);
					}
				}
			}
			int itemsCount = Integer.parseInt(br.readLine());
			for (int i = 0; i < itemsCount; i++) {
				String[] params = br.readLine().split("-");
				String[] positions = params[1].split("x");
				Item item = ObjectFactory.getItem(Integer.parseInt(params[0]), Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
				item.setRotation(Integer.parseInt(params[2]));
				items.add(item);

			}
			int unitCount = Integer.parseInt(br.readLine());
			for (int i = 0; i < unitCount; i++) {
				String[] params = br.readLine().split("-");
				String[] positions = params[1].split("x");
				units.add(UnitFactory.getUnit(Integer.parseInt(params[0]), Integer.parseInt(positions[0]), Integer.parseInt(positions[1])));
			}
			
			br.close();
		} catch (Exception e) {
			System.out.println("Chyba při zpracování mapy " + NAME + ".");
		}
	}

	public Block getBlockAtLocation(double posX, double posY) {
		return blocks[(int) posY / ObjectFactory.getBlockSize()][(int) posX / ObjectFactory.getBlockSize()];
	}

	public void changeBlock(Block originalBlock, Block newBlock) {
		int positionX = (int) (originalBlock.getPositionX() / ObjectFactory.getBlockSize());
		int positionY = (int) (originalBlock.getPositionY() / ObjectFactory.getBlockSize());
		blocks[positionY][positionX] = newBlock;
	}

	public String getName() {
		return this.NAME;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public Dimension getSize() {
		return new Dimension(sizeX, sizeY);
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public List<Spell> getSpells() {
		return spells;
	}
}
