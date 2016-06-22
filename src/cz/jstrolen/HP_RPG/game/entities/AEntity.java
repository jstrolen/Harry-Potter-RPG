package cz.jstrolen.HP_RPG.game.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Josef Stroleny
 */
public abstract class AEntity {
    private double positionX;
    private double positionY;
    private double sizeX;
    private double sizeY;

    public AEntity(double posX, double posY, double sizeX, double sizeY) {
        this.positionX = posX;
        this.positionY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(positionX, positionY, sizeX, sizeY);
    }

    public abstract void draw(Graphics2D g);

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
