package blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class GrayBlocks extends Block {
	public static final int ID = 9;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = false;
	private static final Color COLOR = Color.LIGHT_GRAY;
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "grayBlocks.png");

	public GrayBlocks(double posX, double posY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
	}
	
	@Override
	public Block hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return GrayBlocks.ID;
	}
}
