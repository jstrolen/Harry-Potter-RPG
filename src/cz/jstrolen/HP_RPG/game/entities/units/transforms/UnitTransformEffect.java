package cz.jstrolen.HP_RPG.game.entities.units.transforms;

/**
 * Created by Josef Stroleny
 */
public class UnitTransformEffect {
    private final EUnitTransform CHANGE;
    private final double VALUE;
    private final double DURATION;

    public UnitTransformEffect(String change, double value, double duration) {
        this.CHANGE = EUnitTransform.valueOf(change);
        this.VALUE = value;
        this.DURATION = duration;
    }

    public EUnitTransform getChange() {
        return CHANGE;
    }

    public double getValue() {
        return VALUE;
    }

    public double getDuration() {
        return DURATION;
    }
}
