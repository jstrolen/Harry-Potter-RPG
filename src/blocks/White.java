package blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class White extends Block {
	public static final int ID = 4;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = false;
	private static final Color COLOR = Color.WHITE;
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "white.png");

	public White(double posX, double posY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
	}
	
	@Override
	public Block hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return White.ID;
	}
}
