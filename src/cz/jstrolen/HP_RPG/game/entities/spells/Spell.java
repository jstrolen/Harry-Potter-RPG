package cz.jstrolen.HP_RPG.game.entities.spells;

import cz.jstrolen.HP_RPG.game.entities.AEntity;
import cz.jstrolen.HP_RPG.game.entities.units.Unit;

import java.awt.*;

/**
 * Created by Josef Stroleny
 */
public class Spell extends AEntity {
	private final SpellAttributes spellAttributes;

	private double orientation;
	private Unit caster;
	private double remainingDistance;

	
	public Spell(Unit caster, double positionX, double positionY, double orientation, SpellAttributes spellAttributes) {
		super(positionX, positionY, spellAttributes.getSizeX(), spellAttributes.getSizeY());
		this.caster = caster;
		this.orientation = orientation;
		this.spellAttributes = spellAttributes;
		this.remainingDistance = spellAttributes.getDistanceVitality();
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (spellAttributes.getImage() == null) {
			g.setColor(spellAttributes.getColor());
			g.fillOval((int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY());
		} else {
			g.drawImage(spellAttributes.getImage(), (int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY(), null);
		}
	}

	/* TODO
	public void loading(Graphics2D g, Unit Unit) {
		int startX = (int) (Unit.getPositionX());
		int lengthX = (int) (Unit.getSIZE_X());
		int startY = (int) (Unit.getPositionY() + Unit.getSIZE_Y() + Unit.BAR_HEIGHT);
		
		g.setColor(getColor());
		g.drawRect(startX, startY, lengthX, Spell.BAR_HEIGHT);
		int length = (int) (((double) castLoading / castTime) * lengthX);
		g.fillRect(startX, startY, length, Spell.BAR_HEIGHT);
	}
	*/

	public boolean move() {
		double difX = Math.cos(orientation) * spellAttributes.getSpeed();
		double difY = Math.sin(orientation) * spellAttributes.getSpeed();

		setPositionX(this.getPositionX() + difX);
		setPositionY(this.getPositionY() + difY);
		remainingDistance -= Math.max(Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2)), 0.1);

		return remainingDistance > 0;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public Unit getCaster() {
		return caster;
	}
}
