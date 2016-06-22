package cz.jstrolen.HP_RPG.game.entities.units.transforms;

import cz.jstrolen.HP_RPG.game.entities.AInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by Josef Stroleny
 */
public class UnitTransform extends AInfo {
    private final List<UnitTransformEffect> EFFECTS;
    private final Set<Integer> EXCLUDES;

    public UnitTransform(int id, String name, String title, String description, List<UnitTransformEffect> effects, Set<Integer> excludes) {
        super(id, name, title, description);
        this.EFFECTS = effects;
        this.EXCLUDES = excludes;
    }

    public List<UnitTransformEffect> getEffects() {
        return EFFECTS;
    }

    public Set<Integer> getExcludes() {
        return EXCLUDES;
    }
}
