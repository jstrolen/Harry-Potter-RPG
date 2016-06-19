package items;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Table extends Items {
	public static final int ID = 1;
	private static final double SIZE_X = 60;
	private static final double SIZE_Y = 25;
	private static final Color COLOR = new Color(120, 30, 30);
	private static final BufferedImage IMAGE = Input.getImage(Items.PATH + "table.png");
	private static final boolean WALL = true;
	private static final boolean HITTABLE = false;

	public Table(double posX, double posY, int rotation) {
		super(posX, posY, SIZE_X, SIZE_Y, WALL, HITTABLE, COLOR, IMAGE, rotation);
	}

	@Override
	public Items hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return Table.ID;
	}
}
