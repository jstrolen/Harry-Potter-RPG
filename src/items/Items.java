package items;

import general.Item;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Items extends Item {
	public static final String PATH = "items/";
	public static final int DESTROY = 0;
	public static final int EXTINGUISH = 1;
	public static final int IGNITE = 2;
	private int rotation;
	
	public Items(double posX, double posY, double sizeX, double sizeY, boolean wall, boolean hittable, Color color, BufferedImage image, int rotation) {
		super(posX, posY, sizeX, sizeY, wall, hittable, color, image);
		this.rotation = rotation;
		if (rotation % 2 != 0) {
			double pom = this.getSizeX();
			setSizeX(this.getSizeY());
			setSizeY(pom);
		}
	}

	@Override
	public void nakresli(Graphics2D g) {
		g.setColor(getColor());
		double startX = this.getPositionX();
		double startY = this.getPositionY();
		AffineTransform original = g.getTransform();
		AffineTransform my = (AffineTransform) original.clone();

		switch (rotation) {
		case 0:
			my.translate(startX, startY);
			break;
		case 1:
			my.translate(startX, startY + this.getSizeY());
			my.rotate(-Math.PI / 2);
			break;
		case 2:
			my.translate(startX + this.getSizeX(), startY + this.getSizeY());
			my.rotate(Math.PI);
			break;
		case 3:
			my.translate(startX + this.getSizeX(), startY);
			my.rotate(Math.PI / 2);
			break;
		default: 
			my.translate(startX, startY);
			break;
		}
		g.setTransform(my);
		
		double width = this.getSizeX();
		double height = this.getSizeY();
		if (rotation % 2 != 0) {
			double pom = width;
			width = height;
			height = pom;
		}
		
		if (getImage() == null) g.fillRect(0, 0, (int) width, (int) height);
		else g.drawImage(getImage(), 0, 0, (int) width, (int) height, null);
		g.setTransform(original);
	}
	
	public static Items getItems(int id, int x, int y, int rotation) {
		switch (id) {
		case Cabinet.ID:
			return new Cabinet(x, y, rotation);
		case Table.ID:
			return new Table(x, y, rotation);
		case Bed.ID:
			return new Bed(x, y, rotation);
		}
		return null;
	}
	
	public abstract Items hit(int type);
	
	public abstract int getId();
	
	public int getRotation() {
		return rotation;
	}
}
