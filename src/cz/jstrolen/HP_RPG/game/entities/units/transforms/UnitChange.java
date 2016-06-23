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
        long nowTick = System.currentTimeMillis();
        double value;

        for (int i = 0; i < remainingTimes.length; i++) {
            EUnitTransform transform = TRANSFORM.getUnitEffects().get(i).getChange();
            if (remainingTimes[i] <= 0) {
                if (transform == EUnitTransform.SPEED || transform == EUnitTransform.SPEED_PERC) restoreSpeed(u);
                else if (transform == EUnitTransform.CAST_SPEED || transform == EUnitTransform.CAST_SPEED_PERC) restoreCastSpeed(u);
                else if (transform == EUnitTransform.REVERSE) restoreReverse(u);
                continue;
            }
            value = TRANSFORM.getUnitEffects().get(i).getValue();

            if (transform == EUnitTransform.INJURE ||transform == EUnitTransform.INJURE_PERC ||
                    transform == EUnitTransform.HEAL ||transform == EUnitTransform.HEAL_PERC) {
                double duration = TRANSFORM.getUnitEffects().get(i).getDuration();
                if (duration > 0) {
                    double valuePerSec = value / duration;
                    double diff = (nowTick - lastTick) / 1000.0;
                    diff = Math.max(diff, remainingTimes[i]);
                    value = Math.min(valuePerSec * diff, value);
                    remainingTimes[i] -= diff;
                }
                if (transform == EUnitTransform.INJURE) tickInjure(u, value);
                else if (transform == EUnitTransform.INJURE_PERC) tickInjurePerc(u, value);
                else if (transform == EUnitTransform.HEAL) tickHeal(u, value);
                else tickHealPerc(u, value);
            } else if (transform == EUnitTransform.HEALTH_PERC ||transform == EUnitTransform.KILL) {
                remainingTimes[i] = 0;
                if (transform == EUnitTransform.HEALTH_PERC) healthPerc(u, value);
                else kill(u);
            } else {
                remainingTimes[i] -= (nowTick - lastTick) / 1000.0;
                if (transform == EUnitTransform.SPEED) tickSpeed(u, value);
                else if (transform == EUnitTransform.SPEED_PERC) tickSpeedPerc(u, value);
                else if (transform == EUnitTransform.CAST_SPEED) tickCastSpeed(u, value);
                else if (transform == EUnitTransform.CAST_SPEED_PERC) tickCastSpeedPerc(u, value);
                else if (transform == EUnitTransform.REVERSE) reverse(u);
            }
        }

        boolean end = true;
        for (double remainingTime : remainingTimes) {
            if (remainingTime > 0) {
                end = false;
                break;
            }
        }

        u.setLastTick(nowTick);
        return end;
    }

    private void tickInjure(Unit u, double value) {
        u.setHealth(u.getHealth() - value);
    }

    private void tickInjurePerc(Unit u, double value) {
        u.setHealth(u.getHealth() - u.getAttributes().getHealth() / value);
    }

    private void tickHeal(Unit u, double value) {
        u.setHealth(Math.max(u.getHealth() + value, u.getAttributes().getHealth()));
    }

    private void tickHealPerc(Unit u, double value) {
        u.setHealth(Math.max(u.getHealth() + u.getAttributes().getHealth() / value, u.getAttributes().getHealth()));
    }

    private void tickSpeed(Unit u, double value) {
        u.setSpeed(u.getSpeed() + value);
    }

    private void tickSpeedPerc(Unit u, double value) {
        u.setSpeed(u.getSpeed() + u.getAttributes().getSpeed() / value);
    }

    private void tickCastSpeed(Unit u, double value) {
        u.setCastSpeed(u.getCastSpeed() + value);
    }

    private void tickCastSpeedPerc(Unit u, double value) {
        u.setCastSpeed(u.getCastSpeed() + u.getAttributes().getCastSpeed() / value);
    }

    private void reverse(Unit u) {
        //TODO
    }

    private void healthPerc(Unit u, double value) {
        u.setHealth(u.getAttributes().getHealth() / value);
    }

    private void kill(Unit u) {
        u.setHealth(0);
    }

    private void restoreSpeed(Unit u) { u.setSpeed(u.getAttributes().getSpeed()); }

    private void restoreCastSpeed(Unit u ) { u.setCastSpeed((u.getAttributes().getCastSpeed())); }

    private void restoreReverse(Unit u) {
        //TODO
    }
}
