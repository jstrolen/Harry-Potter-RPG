package blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import support.Input;

public final class Teleport extends Block {
	public static final int ID = 12;
	private static final boolean WALL = false;
	private static final boolean HITTABLE = false;
	private static final Color COLOR = new Color(220, 100, 40);
	private static final BufferedImage IMAGE = Input.getImage(Block.PATH + "teleport.png");
	private final String DESTINATION_NAME;
	private final int DESTINATION_X;
	private final int DESTINATION_Y;

	public Teleport(double posX, double posY, String destinationName, int destinationX, int destinationY) {
		super(posX, posY, COLOR, WALL, HITTABLE, IMAGE);
		this.DESTINATION_NAME = destinationName;
		this.DESTINATION_X = destinationX;
		this.DESTINATION_Y = destinationY;
	}
	
	@Override
	public Block hit(int type) {
		return this;
	}

	@Override
	public int getId() {
		return Teleport.ID;
	}

	public String getDestinationName() {
		return DESTINATION_NAME;
	}

	public int getDestinationX() {
		return DESTINATION_X;
	}

	public int getDestinationY() {
		return DESTINATION_Y;
	}
}
