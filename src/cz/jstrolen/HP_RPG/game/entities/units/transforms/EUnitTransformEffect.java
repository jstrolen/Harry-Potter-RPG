package cz.jstrolen.HP_RPG.game.entities.units.transforms;

/**
 * Created by Josef Stroleny
 */
public enum EUnitTransformEffect {
    INJURE("injure"), INJURE_PERC("injure_perc"),
    HEAL("heal"), HEAL_PERC("heal_perc"),
    SPEED("speed"), SPEED_PERC("speed_perc"),
    CAST_SPEED("castSpeed"), CAST_SPEED_PERC("castSpeed_perc"),
    REVERSE("reverse"),
    HEALTH_PERC("health_perc"),
    KILL("kill");

    private final String name;
    EUnitTransformEffect(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && name.equalsIgnoreCase(otherName);
    }

    public String toString() {
        return this.name;
    }
}
