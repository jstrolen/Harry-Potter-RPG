package cz.jstrolen.HP_RPG.game.entities.objects;

import cz.jstrolen.HP_RPG.game.entities.AAttributes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Josef Stroleny
 */
public class ObjectAttributes extends AAttributes {
    private final boolean HITTABLE;
    private final boolean CROSSABLE;
    private final boolean FLYABLE;
    private final Set<ObjectTransform> TRANSFORMS;

    public ObjectAttributes(int id, String name, String title, String description, Color color, BufferedImage image, double sizeX, double sizeY,
                            boolean hittable, boolean crossable, boolean flyable) {
        super(id, name, title, description, color, image, sizeX, sizeY);
        this.HITTABLE = hittable;
        this.CROSSABLE = crossable;
        this.FLYABLE = flyable;
        this.TRANSFORMS = new HashSet<>();
    }

    public boolean isHittable() {
        return HITTABLE;
    }

    public boolean isCrossable() {
        return CROSSABLE;
    }

    public boolean isFlyable() {
        return FLYABLE;
    }

    public Set<ObjectTransform> getTransform() { return TRANSFORMS; }
}
