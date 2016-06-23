package cz.jstrolen.HP_RPG.game.entities.objects;

import cz.jstrolen.HP_RPG.game.entities.AEntity;
import cz.jstrolen.HP_RPG.game.entities.spells.Spell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josef Stroleny
 */
public class Item extends AObject {
	
	public Item(double posX, double posY, ObjectAttributes objectAttributes) {
		super(posX, posY, objectAttributes);
	}

	@Override
	public AEntity hit(Spell spell) {
		List<Integer> spellEffects = new ArrayList<>(spell.getAttributes().getEffects());
		for (Integer spellEffect : spellEffects) {
			List<Integer> objectTransforms = new ArrayList<>(getAttributes().getTransform());
			for (int j = 0; j < objectTransforms.size(); j++) {
				ObjectTransform objTrans = ObjectFactory.getItemTransforms(objectTransforms.get(j));
				if (objTrans.getTransforms().contains(spellEffect)) {
					getTransforms()[j]++;
					if (getTransforms()[j] >= objTrans.getDurability()) {
						return ObjectFactory.getItem(objTrans.getNewObject(), (int) getPositionX(), (int) getPositionY());
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
