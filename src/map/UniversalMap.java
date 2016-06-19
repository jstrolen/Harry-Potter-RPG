package map;

import ai.Intel;
import blocks.Block;
import items.Items;
import support.Input;
import unit.Unit;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class UniversalMap extends Maps {
	private final String NAME;
	private int sizeX;
	private int sizeY;
	private Block[][] blocks;
	private List<Items> items;
	private List<Unit> units;

	public UniversalMap(String name) {
		this.NAME = name;
		load();
	}

	private int load() {
		items = new ArrayList<Items>();
		units = new ArrayList<Unit>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(Input.getFile(Maps.PATH + NAME)));
			String info = br.readLine();
			String[] split = info.split(";");
			sizeX = Integer.parseInt(split[0]);
			sizeY = Integer.parseInt(split[1]);
			blocks = new Block[sizeX][sizeY];
			
			for (int y = 0; y < sizeY; y++) {
				String str = br.readLine();
				String[] pom = str.split(" ");
				for (int x = 0; x < sizeX; x++) {
					String[] teleport = pom[x].split("-");
					if (teleport.length == 1) blocks[x][y] = Block.getBlock(Integer.parseInt(pom[x]), x, y, "", 0, 0);
					else blocks[x][y] = Block.getBlock(Integer.parseInt(teleport[0]), x, y, teleport[1], Integer.parseInt(teleport[2]), Integer.parseInt(teleport[3]));
				}
			}
			int itemsCount = Integer.parseInt(br.readLine());
			for (int i = 0; i < itemsCount; i++) {
				String[] pom = br.readLine().split("-");
				String[] positions = pom[1].split("x");
				items.add(Items.getItems(Integer.parseInt(pom[0]), Integer.parseInt(positions[0]), Integer.parseInt(positions[1]), Integer.parseInt(pom[2])));
			}
			int unitCount = Integer.parseInt(br.readLine());
			for (int i = 0; i < unitCount; i++) {
				String[] pom = br.readLine().split("-");
				String[] positions = pom[1].split("x");
				units.add(Unit.getUnit(Integer.parseInt(pom[0]), Integer.parseInt(positions[0]), Integer.parseInt(positions[1]), 
						Intel.getIntel(Integer.parseInt(pom[2]), pom[3])));
			}
			
			br.close();
		} catch (Exception e) {
			System.out.println("Chyba při zpracování mapy " + NAME + ".");
		}
		return sizeX;
	}

	@Override
	public String toString() {
		return this.NAME;
	}

	@Override
	public Block[][] getBlocks() {
		return blocks;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(sizeX, sizeY);
	}

	@Override
	public List<Items> getItems() {
		return items;
	}

	@Override
	public List<Unit> getUnits() {
		return units;
	}
}
