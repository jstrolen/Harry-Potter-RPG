package cz.jstrolen.HP_RPG.game.entities.units.transforms;

/**
 * Created by Josef Stroleny
 */
public class UnitChange {
    private final UnitTransform TRANSFORM;
    private double remainingTimes[];

    public UnitChange(UnitTransform transform) {
        this.TRANSFORM = transform;
        this.remainingTimes = new double[transform.getEffects().size()];
        for (int i = 0; i< remainingTimes.length; i++) {
            remainingTimes[i] = transform.getEffects().get(i).getDuration();
        }
    }

    //TODO tick
}
