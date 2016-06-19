package blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Rock extends Block {
	public static final int ID = 2;
	private static final boolean WALL = true;
	private static final boolean HITTABLE = true;
	private static final Color COLOR = Color.GRAY;
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "rock.png");
	private int hot = 0;

	public Rock(double posX, double posY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
	}
	
	@Override
	public Block hit(int type) {
		if (type == Block.DESTROY) {
			return new Sand(getPositionX(), getPositionY());
		}
		else if (type == Block.IGNITE) {
			if (++hot >= 100) return new BurningRock(getPositionX(), getPositionY());
		}
		else if (type == Block.EXTINGUISH) {
			if (hot > 0) hot--;
		}
		return this;
	}

	@Override
	public int getId() {
		return Rock.ID;
	}
}
