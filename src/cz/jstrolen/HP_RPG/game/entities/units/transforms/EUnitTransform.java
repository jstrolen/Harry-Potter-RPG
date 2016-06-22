package cz.jstrolen.HP_RPG.game.entities.units.transforms;

/**
 * Created by Josef Stroleny
 */
public enum EUnitTransform {
    INJURE("injure"), INJURE_PERC("injure_perc"),
    HEALTH("health"), HEALTH_PERC("health_perc"),
    SPEED("speed"), SPEED_PERC("speed_perc"),
    CAST_SPEED("castSpeed"), CAST_SPEED_PERC("castSpeed_perc"),
    REVERSE("reverse"),
    KILL("kill");

    private final String name;
    EUnitTransform(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equalsIgnoreCase(otherName);
    }

    public String toString() {
        return this.name;
    }
}
