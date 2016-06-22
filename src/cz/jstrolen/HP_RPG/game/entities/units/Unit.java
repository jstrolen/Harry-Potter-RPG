package cz.jstrolen.HP_RPG.game.entities.units;

import cz.jstrolen.HP_RPG.game.ai.AI;
import cz.jstrolen.HP_RPG.game.entities.AEntity;
import cz.jstrolen.HP_RPG.game.entities.units.transforms.UnitChange;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josef Stroleny
 */
public class Unit extends AEntity implements Runnable {
	private final UnitAttributes unitAttributes;
	private double speed;
	private double health;
	private List<UnitChange> effects;
	private AI ai;
	
	public Unit(double positionX, double positionY, UnitAttributes unitAttributes) {
		super(positionX, positionY, unitAttributes.getSizeX(), unitAttributes.getSizeY());
		this.unitAttributes = unitAttributes;
		this.speed = unitAttributes.getSpeed();
		this.health = unitAttributes.getHealth();
		effects = new ArrayList<>();
	}

	@Override
	public void run() {
		/* TODO
		restore();
		for (int i = 0; i < effects.size(); i++) {
			if (effects.get(i).nextStep()) {
				effects.remove(i);
				i--;
			}
			else {
				effects.get(i).apply();
			}
		}
		if (!isCanCastNow()) stopCast(World.getInstance());
		
		if (ai != null) ai.tick(this, World.getInstance());
		*/
	}

	@Override
	public void draw(Graphics2D g) {
		if (unitAttributes.getImage() == null) {
			g.setColor(unitAttributes.getColor());
			g.fillRect((int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY());
		}
		else {
			g.drawImage(unitAttributes.getImage(), (int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY(), null);
		}

		/* TODO
		int startX = (int) getPositionX();
		int lengthX = (int) unitAttributes.getSIZE_X();
		int startY = (int) (getPositionY() + unitAttributes.getSIZE_Y());
		g.setColor(HEALTH);
		g.drawRect(startX, startY, lengthX, Unit.BAR_HEIGHT);
		int length = (int) (((double) health / MAX_HEALTH) * lengthX);
		g.fillRect(startX, startY, length, Unit.BAR_HEIGHT);
		*/
	}

	/* TODO
	public void cast(double vector, World map) {
		if (!canCastNow || !this.isCanCast()) return;
		if (levitating != null) {
			levitating = null;
			return;
		}
		if (actual == null) {
			actual = Spell.getSpell(this, spellId, vector);
			map.addCasting(actual);
		}

		boolean exist = false;
		if (actual != null ) exist = actual.reload();

		if (exist) {
			actual.setPositionX(this.getPositionX() + this.getSIZE_X() / 2 - actual.getSIZE_X() / 2);
			actual.setPositionY(this.getPositionY() + this.getSIZE_Y() / 2 - actual.getSIZE_Y() / 2);
			actual.setOrientation(vector);
			map.removeCasting(actual);
			map.newSpell(actual);
			actual = null;
		}
	}
	*/

	/* TODO
	public void move(double difX, double difY, World map) {
		if (levitating == null) {
			double[] position = map.tryMove(this, this.getPositionX(), this.getPositionY(), difX, difY, this.getSIZE_X(), this.getSIZE_Y());
			setPositionX(this.getPositionX() + position[0]);
			setPositionY(this.getPositionY() + position[1]);
		}
		else {
			double[] position = map.tryMove(levitating, levitating.getPositionX(), levitating.getPositionY(), difX, difY, levitating.getSIZE_X(), levitating.getSIZE_Y());
			levitating.setPositionX(levitating.getPositionX() + position[0]);
			levitating.setPositionY(levitating.getPositionY() + position[1]);
		}
	}
	*/

	/* TODO
	public void changeSpell(int i) {
		if (i > 0) spellNumber++;
		else spellNumber--;
		
		if (spellNumber >= this.getSpellCount()) spellNumber = 0;
		else if (spellNumber < 0) spellNumber = this.getSpellCount() - 1;
		
		spellId = SPELL_IDS[spellNumber];
	}
	*/

	public double getSpeed() { return speed; }

	public void setSpeed(double speed) { this.speed = speed; }

	public double getHealth() { return health; }

	public void setHealth(double health) { this.health = health; }

	public AI getAi() {
		return ai;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}

	public UnitAttributes getAttributes() { return unitAttributes; }
}
