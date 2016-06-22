package cz.jstrolen.HP_RPG.game.entities.units.transforms;

import cz.jstrolen.HP_RPG.game.entities.AInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by Josef Stroleny
 */
public class UnitTransform extends AInfo {
    private final Set<Integer> EFFECTS;
    private final List<UnitTransformEffect> UNIT_EFFECTS;
    private final Set<Integer> EXCLUDES;

    public UnitTransform(int id, String name, String title, String description, Set<Integer> effects, List<UnitTransformEffect> unitEffects, Set<Integer> excludes) {
        super(id, name, title, description);
        this.EFFECTS = effects;
        this.UNIT_EFFECTS = unitEffects;
        this.EXCLUDES = excludes;
    }

    public List<UnitTransformEffect> getEffects() {
        return UNIT_EFFECTS;
    }

    public Set<Integer> getExcludes() {
        return EXCLUDES;
    }

    public List<UnitTransformEffect> getUNIT_EFFECTS() {
        return UNIT_EFFECTS;
    }
}
