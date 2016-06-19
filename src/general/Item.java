package general;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Item {
	private double positionX;
	private double positionY;
	private boolean hittable;
	private boolean wall;
	private double sizeX;
	private double sizeY;
	private final Color COLOR;
	private final BufferedImage IMAGE;
	
	public Item(double posX, double posY, double sizeX, double sizeY, boolean wall, boolean hittable, Color color, BufferedImage image) {
		this.positionX = posX;
		this.positionY = posY;
		this.COLOR = color;
		this.wall = wall;
		this.IMAGE = image;
		this.hittable = hittable;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public abstract void nakresli(Graphics2D g);
	
	public boolean contains(Rectangle p) {
		int startX = (int) this.getPositionX();
		int endX = (int) (startX + sizeX);
		int startY = (int) this.getPositionY();
		int endY = (int) (startY + sizeY);
		if ((p.x + p.width > startX && p.x <= endX) && (p.y + p.height > startY && p.y <= endY)) return true;
		return false;
	}
	
	public boolean isWall() {
		return wall;
	}
	
	public void setWall(boolean wall) {
		this.wall = wall;
	}
	
	public boolean isHittable() {
		return hittable;
	}
	
	public void setHittable(boolean hittable) {
		this.hittable = hittable;
	}
	
	public void setPositionX(double posX) {
		this.positionX = posX;
	}

	public double getPositionX() {
		return positionX;
	}
	
	public void setPositionY(double posY) {
		this.positionY = posY;
	}
	
	public double getPositionY() {
		return positionY;
	}
	
	public Color getColor() {
		return COLOR;
	}
	
	public BufferedImage getImage() {
		return IMAGE;
	}

	public double getSizeX() {
		return sizeX;
	}

	public void setSizeX(double sizeX) {
		this.sizeX = sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}

	public void setSizeY(double sizeY) {
		this.sizeY = sizeY;
	}
}
