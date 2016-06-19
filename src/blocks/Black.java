package blocks;

import support.Input;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Black extends Block {
	public static final int ID = 5;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = false;
	private static final Color COLOR = Color.BLACK;
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "black.png");

	public Black(double posX, double posY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
	}
	
	@Override
	public Block hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return Black.ID;
	}
}
