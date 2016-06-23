package cz.jstrolen.HP_RPG.game.entities;

import cz.jstrolen.HP_RPG.game.entities.spells.Spell;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Josef Stroleny
 */
public abstract class AEntity {
    private final Rectangle2D BOUNDS;
    private double positionX;
    private double positionY;
    private double sizeX;
    private double sizeY;

    protected AEntity(double posX, double posY, double sizeX, double sizeY) {
        this.positionX = posX;
        this.positionY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        BOUNDS = new Rectangle2D.Double(posX, posY, sizeX, sizeY);
    }

    public Rectangle2D getBounds() {
        return BOUNDS;
    }

    public abstract AEntity hit(Spell spell);

    public abstract void draw(Graphics2D g);

    public void setPositionX(double posX) {
        this.positionX = posX;
        this.BOUNDS.setRect(positionX, positionY, sizeX, sizeY);
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionY(double posY) {
        this.positionY = posY;
        this.BOUNDS.setRect(positionX, positionY, sizeX, sizeY);
    }

    public double getPositionY() {
        return positionY;
    }

    public double getSizeX() {
        return sizeX;
    }

    protected void setSizeX(double sizeX) {
        this.sizeX = sizeX;
        this.BOUNDS.setRect(positionX, positionY, sizeX, sizeY);
    }

    public double getSizeY() {
        return sizeY;
    }

    protected void setSizeY(double sizeY) {
        this.sizeY = sizeY;
        this.BOUNDS.setRect(positionX, positionY, sizeX, sizeY);
    }
}
