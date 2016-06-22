package cz.jstrolen.HP_RPG.game.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Josef Stroleny
 */
public abstract class AAttributes extends AInfo {
    private final Color COLOR;
    private final BufferedImage IMAGE;

    private final double SIZE_X;
    private final double SIZE_Y;

    public AAttributes(int id, String name, String title, String description, Color color, BufferedImage image, double sizeX, double sizeY) {
        super(id, name, title, description);
        this.COLOR = color;
        this.IMAGE = image;
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
    }

    public double getSizeX() {
        return SIZE_X;
    }

    public double getSizeY() {
        return SIZE_Y;
    }

    public Color getColor() {
        return COLOR;
    }

    public BufferedImage getImage() {
        return IMAGE;
    }
}
