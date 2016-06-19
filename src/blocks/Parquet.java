package blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Parquet extends Block {
	public static final int ID = 10;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = false;
	private static final Color COLOR = new Color(120, 50, 50);
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "parquet.png");

	public Parquet(double posX, double posY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
	}
	
	@Override
	public Block hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return Parquet.ID;
	}
}
