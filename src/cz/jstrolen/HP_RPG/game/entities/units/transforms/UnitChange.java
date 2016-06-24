package cz.jstrolen.HP_RPG.game.entities.units.transforms;

import cz.jstrolen.HP_RPG.game.entities.units.Unit;

/**
 * Created by Josef Stroleny
 */
public class UnitChange {
    private final UnitTransform TRANSFORM;
    private final double[] remainingTimes;

    public UnitChange(UnitTransform transform) {
        this.TRANSFORM = transform;
        this.remainingTimes = new double[transform.getUnitEffects().size()];
        for (int i = 0; i< remainingTimes.length; i++) {
            remainingTimes[i] = transform.getUnitEffects().get(i).getDuration();
        }
    }

    public boolean tick(Unit u) {
        long lastTick = u.getLastTick();
        long nowTick = System.nanoTime();
        double value;

        for (int i = 0; i < remainingTimes.length; i++) {
            EUnitTransformEffect transform = TRANSFORM.getUnitEffects().get(i).getChange();
            if (remainingTimes[i] < 0) continue;
            value = TRANSFORM.getUnitEffects().get(i).getValue();
            if (remainingTimes[i] == 0) {
                remainingTimes[i] -= 1;
                if (transform == EUnitTransformEffect.INJURE) tickInjure(u, value);
                else if (transform == EUnitTransformEffect.INJURE_PERC) tickInjurePerc(u, value);
                else if (transform == EUnitTransformEffect.HEAL) tickHeal(u, value);
                else if (transform == EUnitTransformEffect.HEAL_PERC) tickHealPerc(u, value);
                else if (transform == EUnitTransformEffect.KILL) kill(u);
                else if (transform == EUnitTransformEffect.SPEED || transform == EUnitTransformEffect.SPEED_PERC) restoreSpeed(u);
                else if (transform == EUnitTransformEffect.CAST_SPEED || transform == EUnitTransformEffect.CAST_SPEED_PERC) restoreCastSpeed(u);
                else if (transform == EUnitTransformEffect.REVERSE) restoreReverse(u);
                continue;
            }

            if (transform == EUnitTransformEffect.INJURE ||transform == EUnitTransformEffect.INJURE_PERC ||
                    transform == EUnitTransformEffect.HEAL ||transform == EUnitTransformEffect.HEAL_PERC) {
                double duration = TRANSFORM.getUnitEffects().get(i).getDuration();
                double valuePerSec = value / duration;
                double diff = (nowTick - lastTick) / 1000000000.0;
                diff = Math.min(diff, remainingTimes[i]);
                value = Math.min(valuePerSec * diff, value);
                remainingTimes[i] -= diff;
                if (remainingTimes[i] == 0) remainingTimes[i] = -1;

                if (transform == EUnitTransformEffect.INJURE) tickInjure(u, value);
                else if (transform == EUnitTransformEffect.INJURE_PERC) tickInjurePerc(u, value);
                else if (transform == EUnitTransformEffect.HEAL) tickHeal(u, value);
                else tickHealPerc(u, value);
            } else if (transform == EUnitTransformEffect.HEALTH_PERC ||transform == EUnitTransformEffect.KILL) {
                remainingTimes[i] = -1;
                if (transform == EUnitTransformEffect.HEALTH_PERC) healthPerc(u, value);
                else kill(u);
            } else {
                remainingTimes[i] -= Math.min((nowTick - lastTick) / 1000000000.0, remainingTimes[i]);
                if (transform == EUnitTransformEffect.SPEED) tickSpeed(u, value);
                else if (transform == EUnitTransformEffect.SPEED_PERC) tickSpeedPerc(u, value);
                else if (transform == EUnitTransformEffect.CAST_SPEED) tickCastSpeed(u, value);
                else if (transform == EUnitTransformEffect.CAST_SPEED_PERC) tickCastSpeedPerc(u, value);
                else if (transform == EUnitTransformEffect.REVERSE) reverse(u);
            }
        }

        boolean end = true;
        for (double remainingTime : remainingTimes) {
            if (remainingTime >= 0) {
                end = false;
                break;
            }
        }

        return end;
    }

    private void tickInjure(Unit u, double value) {
        u.setHealth(u.getHealth() - value);
    }

    private void tickInjurePerc(Unit u, double value) {
        u.setHealth(Math.max(u.getHealth() - u.getAttributes().getHealth() * (value / 100), 0));
    }

    private void tickHeal(Unit u, double value) {
        u.setHealth(Math.min(u.getHealth() + value, u.getAttributes().getHealth()));
    }

    private void tickHealPerc(Unit u, double value) {
        u.setHealth(Math.min(u.getHealth() + u.getAttributes().getHealth() * (value / 100), u.getAttributes().getHealth()));
    }

    private void tickSpeed(Unit u, double value) {
        u.setSpeed(Math.max(value, 0));
    }

    private void tickSpeedPerc(Unit u, double value) {
        u.setSpeed(Math.max(u.getAttributes().getSpeed() * (value / 100), 0));
    }

    private void tickCastSpeed(Unit u, double value) {
        u.setCastSpeed(Math.max(u.getCastSpeed() + value, 0));
    }

    private void tickCastSpeedPerc(Unit u, double value) {
        u.setCastSpeed(Math.max(u.getCastSpeed() + u.getAttributes().getCastSpeed() * (value / 100), 0));
    }

    private void reverse(Unit u) { u.setHanging(true); }

    private void healthPerc(Unit u, double value) {
        u.setHealth(Math.max(u.getAttributes().getHealth() * (value / 100), 0));
    }

    private void kill(Unit u) {
        u.setHealth(0);
    }

    private void restoreSpeed(Unit u) { u.setSpeed(u.getAttributes().getSpeed()); }

    private void restoreCastSpeed(Unit u ) { u.setCastSpeed((u.getAttributes().getCastSpeed())); }

    private void restoreReverse(Unit u) { u.setHanging(false); }
}
