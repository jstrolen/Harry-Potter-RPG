package blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Water extends Block {
	public static final int ID = 8;
	private static final boolean WALL = true;
	private static final boolean HITTABLE = false;
	private static final Color COLOR = Color.BLUE;
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "water.png");

	public Water(double posX, double posY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
	}
	
	@Override
	public Block hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return Water.ID;
	}
}
