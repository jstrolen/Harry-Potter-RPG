package cz.jstrolen.HP_RPG.game.entities.units.transforms;

import cz.jstrolen.HP_RPG.game.entities.AInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by Josef Stroleny
 */
public class UnitTransform extends AInfo {
    private final Set<Integer> STARTER_EFFECTS_ID;
    private final List<UnitTransformEffect> UNIT_EFFECTS;
    private final Set<Integer> EXCLUDED_UNITS;

    public UnitTransform(int id, String name, String title, String description, Set<Integer> starterEffectIds, List<UnitTransformEffect> unitEffects,
                         Set<Integer> excludedUnits) {
        super(id, name, title, description);
        this.STARTER_EFFECTS_ID = starterEffectIds;
        this.UNIT_EFFECTS = unitEffects;
        this.EXCLUDED_UNITS = excludedUnits;
    }

    public Set<Integer> getStarterEffectsIds() {
        return STARTER_EFFECTS_ID;
    }

    public Set<Integer> getExcludedUnits() {
        return EXCLUDED_UNITS;
    }

    public List<UnitTransformEffect> getUnitEffects() {
        return UNIT_EFFECTS;
    }
}
