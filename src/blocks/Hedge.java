package blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Hedge extends Block {
	public static final int ID = 7;
	private static final boolean WALL = true;
	private static final boolean HITTABLE = true;
	private static final Color COLOR = new Color(50, 150, 50);
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "hedge.png");
	private int hot = 0;

	public Hedge(double posX, double posY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
	}
	
	@Override
	public Block hit(int type) {
		if (type == Block.DESTROY) {
			return new Grass(getPositionX(), getPositionY());
		}
		else if (type == Block.EXTINGUISH) {
			if (hot > 0) hot--;
		}
		else if (type == Block.IGNITE) {
			if (++hot >= 100) return new Sand(getPositionX(), getPositionY());
		}
		return this;
	}

	@Override
	public int getId() {
		return Hedge.ID;
	}
}
