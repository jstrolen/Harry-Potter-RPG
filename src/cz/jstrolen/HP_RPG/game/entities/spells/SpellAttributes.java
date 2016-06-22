package cz.jstrolen.HP_RPG.game.entities.spells;

import cz.jstrolen.HP_RPG.game.entities.AAttributes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Created by Josef Stroleny
 */
public class SpellAttributes extends AAttributes {
    private final double DISTANCE_VITALITY;
    private final double SPEED;
    private final double CAST_TIME;
    private final double DISPERSION;
    private final Set<Integer> EFFECTS;

    public SpellAttributes(int id, String name, String title, String description, Color color, BufferedImage image, double sizeX, double sizeY,
                           double distanceVitality, double speed, double castTime, double dispersion, Set<Integer> effects) {
        super(id, name, title, description, color, image, sizeX, sizeY);
        this.DISTANCE_VITALITY = distanceVitality;
        this.SPEED = speed;
        this.CAST_TIME = castTime;
        this.DISPERSION = dispersion;
        this.EFFECTS = effects;
    }

    public double getDistanceVitality() {
        return DISTANCE_VITALITY;
    }

    public double getSpeed() {
        return SPEED;
    }

    public double getCastTime() {
        return CAST_TIME;
    }

    public double getDispersion() {
        return DISPERSION;
    }

    public Set<Integer> getEffects() { return EFFECTS; }
}
