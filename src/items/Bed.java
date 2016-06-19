package items;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Bed extends Items {
	public static final int ID = 2;
	private static final double SIZE_X = 35;
	private static final double SIZE_Y = 70;
	private static final Color COLOR = new Color(90, 10, 10);
	private static final BufferedImage IMAGE = Input.getImage(Items.PATH + "bed.png");
	private static final boolean WALL = false;
	private static final boolean HITTABLE = false;

	public Bed(double posX, double posY, int rotation) {
		super(posX, posY, SIZE_X, SIZE_Y, WALL, HITTABLE, COLOR, IMAGE, rotation);
	}

	@Override
	public Items hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return Bed.ID;
	}
}
