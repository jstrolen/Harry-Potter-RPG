package cz.jstrolen.HP_RPG.game.entities.units.transforms;

/**
 * Created by Josef Stroleny
 */
public class UnitTransformEffect {
    private final EUnitTransformEffect CHANGE;
    private final double VALUE;
    private final double DURATION;

    public UnitTransformEffect(String change, double value, double duration) {
        this.CHANGE = EUnitTransformEffect.valueOf(change);
        this.VALUE = value;
        this.DURATION = duration;
    }

    public EUnitTransformEffect getChange() {
        return CHANGE;
    }

    public double getValue() {
        return VALUE;
    }

    public double getDuration() {
        return DURATION;
    }
}
