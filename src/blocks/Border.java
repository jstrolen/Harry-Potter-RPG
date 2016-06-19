package blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Border extends Block {
	public static final int ID = 0;
	private static final boolean WALL = true;
	private static final boolean HITTABLE = true;
	private static final Color COLOR = Color.BLACK;
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "border.png");

	public Border(double posX, double posY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
	}
	
	@Override
	public Block hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return Border.ID;
	}
}
