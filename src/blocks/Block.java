package blocks;

import general.Item;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Block extends Item {
	public static final String PATH = "block/";
	public static final int BLOCK_SIZE = 25;
	public static final int DESTROY = 0;
	public static final int EXTINGUISH = 1;
	public static final int IGNITE = 2;
	
	public Block(double posX, double posY, Color color, boolean wall, boolean hittable, BufferedImage image) {
		super(posX, posY, Block.BLOCK_SIZE, Block.BLOCK_SIZE, wall, hittable, color, image);
	}
	
	@Override
	public void nakresli(Graphics2D g) {
		g.setColor(getColor());
		if (getImage() == null) g.fillRect((int) (getPositionX() * Block.BLOCK_SIZE), 
				(int) (getPositionY() * Block.BLOCK_SIZE), Block.BLOCK_SIZE, Block.BLOCK_SIZE);
		else g.drawImage(getImage(), (int) (getPositionX() * Block.BLOCK_SIZE), 
				(int) (getPositionY() * Block.BLOCK_SIZE), Block.BLOCK_SIZE, Block.BLOCK_SIZE, null);
	}
	
	@Override
	public boolean contains(Rectangle p) {
		int startX = (int) (this.getPositionX() * Block.BLOCK_SIZE);
		int endX = (int) (startX + this.getSizeX());
		int startY = (int) (this.getPositionY() * Block.BLOCK_SIZE);
		int endY = (int) (startY + this.getSizeY());
		if ((p.x + p.width > startX && p.x <= endX) && (p.y + p.height > startY && p.y <= endY)) return true;
		return false;
	}
	
	public static Block getBlock(int id, int x, int y, String destination, int destX, int destY) {
		switch (id) {
		case Border.ID:
			return new Border(x, y);
		case BurningRock.ID:
			return new BurningRock(x, y);
		case Rock.ID:
			return new Rock(x, y);
		case Sand.ID:
			return new Sand(x, y);
		case White.ID:
			return new White(x, y);
		case Black.ID:
			return new Black(x, y);
		case Grass.ID:
			return new Grass(x, y);
		case Hedge.ID:
			return new Hedge(x, y);
		case Water.ID:
			return new Water(x, y);
		case GrayBlocks.ID:
			return new GrayBlocks(x, y);
		case Parquet.ID:
			return new Parquet(x, y);
		case BrownWall.ID:
			return new BrownWall(x, y);
		case Teleport.ID:
			return new Teleport(x, y, destination, destX, destY);
		}
		return null;
	}
	
	public abstract Block hit(int type);
	
	public abstract int getId();
}
