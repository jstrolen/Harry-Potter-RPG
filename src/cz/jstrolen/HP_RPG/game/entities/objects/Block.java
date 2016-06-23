package cz.jstrolen.HP_RPG.game.entities.objects;

import cz.jstrolen.HP_RPG.game.entities.AEntity;
import cz.jstrolen.HP_RPG.game.entities.spells.Spell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josef Stroleny
 */
public class Block extends AObject {
    private final int MAP_POSITION_X;
    private final int MAP_POSITION_Y;

    public Block(int mapPositionX, int mapPositionY, ObjectAttributes objectAttributes) {
        super(mapPositionX * objectAttributes.getSizeX(), mapPositionY * objectAttributes.getSizeY(), objectAttributes);
        MAP_POSITION_X = mapPositionX;
        MAP_POSITION_Y = mapPositionY;
    }

    @Override
    public AEntity hit(Spell spell) {
        List<Integer> spellEffects = new ArrayList<>(spell.getAttributes().getEffects());
        for (Integer spellEffect : spellEffects) {
            List<Integer> objectTransforms = new ArrayList<>(getAttributes().getTransform());
            for (int j = 0; j < objectTransforms.size(); j++) {
                ObjectTransform objTrans = ObjectFactory.getBlockTransforms(objectTransforms.get(j));
                if (objTrans.getTransforms().contains(spellEffect)) {
                    getTransforms()[j]++;
                    if (getTransforms()[j] >= objTrans.getDurability()) {
                        return ObjectFactory.getBlock(objTrans.getNewObject(), MAP_POSITION_X, MAP_POSITION_Y);
                    }
                } else {
                    if (objTrans.getAntitransforms().contains(spellEffect)) {
                        if (getTransforms()[j] > 0) getTransforms()[j]--;
                    }
                }
            }
        }
        return this;
    }
}
