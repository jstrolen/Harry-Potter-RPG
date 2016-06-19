package items;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Cabinet extends Items {
	public static final int ID = 0;
	private static final double SIZE_X = 55;
	private static final double SIZE_Y = 35;
	private static final Color COLOR = new Color(100, 10, 10);
	private static final BufferedImage IMAGE = Input.getImage(Items.PATH + "cabinet.png");
	private static final boolean WALL = true;
	private static final boolean HITTABLE = true;
	private int hot = 0;

	public Cabinet(double posX, double posY, int rotation) {
		super(posX, posY, SIZE_X, SIZE_Y, WALL, HITTABLE, COLOR, IMAGE, rotation);
	}
	
	@Override
	public Items hit(int type) {
		if (type == Cabinet.DESTROY) {
			return null;
		}
		else if (type == Cabinet.IGNITE) {
			if (++hot >= 100) return null;
		}
		else if (type == Cabinet.EXTINGUISH) {
			if (hot > 0) hot--;
		}
		return this;
	}

	@Override
	public int getId() {
		return Cabinet.ID;
	}
}
