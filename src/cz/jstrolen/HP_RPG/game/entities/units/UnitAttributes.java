package cz.jstrolen.HP_RPG.game.entities.units;

import cz.jstrolen.HP_RPG.game.entities.AAttributes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Created by Josef Stroleny
 */
public class UnitAttributes extends AAttributes {
    private final double SPEED;
    private final double HEALTH;
    private final double CAST_SPEED;
    private final Set<Integer> SPELLS;

    public UnitAttributes(int id, String name, String title, String description, Color color, BufferedImage image, double sizeX, double sizeY,
                          double speed, double health, double castSpeed, Set<Integer> spells) {
        super(id, name, title, description, color, image, sizeX, sizeY);
        this.SPEED = speed;
        this.HEALTH = health;
        this.CAST_SPEED = castSpeed;
        this.SPELLS = spells;
    }

    public double getSpeed() { return SPEED; }

    public double getHealth() { return HEALTH; }

    public double getCastSpeed() { return CAST_SPEED; }

    public Set<Integer> getSpells() { return SPELLS; }
}
