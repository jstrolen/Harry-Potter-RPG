package cz.jstrolen.HP_RPG.game.entities.objects;

import cz.jstrolen.HP_RPG.game.entities.AEntity;

import java.awt.*;

/**
 * Created by Josef Stroleny
 */
public abstract class AObject extends AEntity {
	private final ObjectAttributes objectAttributes;
	private final int[] TRANSFORMS;
	private int rotation;
	private Teleport teleport;
	
	AObject(double positionX, double positionY, ObjectAttributes objectAttributes) {
		super(positionX, positionY, objectAttributes.getSizeX(), objectAttributes.getSizeY());
		this.objectAttributes = objectAttributes;
		this.rotation = 0;
		this.TRANSFORMS = new int[objectAttributes.getTransform().size()];
	}

	@Override
	public void draw(Graphics2D g) {
		if (objectAttributes.getImage() == null) {
			g.setColor(objectAttributes.getColor());
			g.fillOval((int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY());
		} else {
			switch (rotation % 4) {
				case 0:
					g.drawImage(objectAttributes.getImage(), (int) getPositionX(), (int) getPositionY(),
							(int) getSizeX(), (int) getSizeY(), null);
					break;
				case 1:
					g.drawImage(objectAttributes.getImage(), (int) (getPositionX() + getSizeX()), (int) getPositionY(),
							(int) -getSizeX(), (int) getSizeY(), null);
					break;
				case 2:
					g.drawImage(objectAttributes.getImage(), (int) (getPositionX() + getSizeX()), (int) (getPositionY() + getSizeY()),
							(int) -getSizeX(), (int) -getSizeY(), null);
					break;
				case 3:
					g.drawImage(objectAttributes.getImage(), (int) getPositionX(), (int) (getPositionY() + getSizeY()),
							(int) getSizeX(), (int) -getSizeY(), null);
					break;
			}
		}
	}

	public ObjectAttributes getAttributes() {
		return objectAttributes;
	}

	public void setRotation(int rotation) {
		rotation = rotation % 4;
		if (this.rotation % 2 == 0 && rotation % 2 != 0) {
			double pom = getSizeX();
			this.setSizeX(getSizeY());
			this.setSizeY(pom);
		}
		this.rotation = rotation;
	}

	public int getRotation() { return rotation; }

	public void setTeleport(Teleport teleport) { this.teleport = teleport; }

	public Teleport getTeleport() { return teleport; }

	int[] getTransforms() {
		return TRANSFORMS;
	}
}
