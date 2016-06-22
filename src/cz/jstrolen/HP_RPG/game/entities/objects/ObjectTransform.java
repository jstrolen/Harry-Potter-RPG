package cz.jstrolen.HP_RPG.game.entities.objects;

import cz.jstrolen.HP_RPG.game.entities.AInfo;

import java.util.Set;

/**
 * Created by Josef Stroleny
 */
public class ObjectTransform extends AInfo {
    private final int NEW_OBJECT;
    private final int DURABILITY;
    private final Set<Integer> TRANSFORMS;
    private final Set<Integer> ANTITRANSFORMS;

    public ObjectTransform(int id, String name, String title, String description,
                           int newObject, int durability, Set<Integer> transforms, Set<Integer> antitransforms) {
        super(id, name, title, description);
        this.NEW_OBJECT = newObject;
        this.DURABILITY = durability;
        this.TRANSFORMS = transforms;
        this.ANTITRANSFORMS = antitransforms;
    }

    public int getNewObject() {
        return NEW_OBJECT;
    }

    public int getDurability() {
        return DURABILITY;
    }

    public Set<Integer> getTransforms() {
        return TRANSFORMS;
    }

    public Set<Integer> getAntitransforms() {
        return ANTITRANSFORMS;
    }
}
