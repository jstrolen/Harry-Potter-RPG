package cz.jstrolen.HP_RPG.game.entities.units;

import cz.jstrolen.HP_RPG.game.GameSettings;
import cz.jstrolen.HP_RPG.game.ai.AI;
import cz.jstrolen.HP_RPG.game.entities.AEntity;
import cz.jstrolen.HP_RPG.game.entities.spells.Spell;
import cz.jstrolen.HP_RPG.game.entities.spells.SpellFactory;
import cz.jstrolen.HP_RPG.game.entities.units.transforms.UnitChange;
import cz.jstrolen.HP_RPG.game.maps.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josef Stroleny
 */
public class Unit extends AEntity {
	private final UnitAttributes unitAttributes;
	private double speed;
	private double health;
	private List<UnitChange> effects;
	private AI ai;
	private double castSpeed;
	private boolean hanging;

	private long lastTick;

	private int actualSpell;
	private double castTime;

	public Unit(double positionX, double positionY, UnitAttributes unitAttributes) {
		super(positionX, positionY, unitAttributes.getSizeX(), unitAttributes.getSizeY());
		this.unitAttributes = unitAttributes;
		this.speed = unitAttributes.getSpeed();
		this.health = unitAttributes.getHealth();
		this.castSpeed = unitAttributes.getCastSpeed();
		this.effects = new ArrayList<>();
		this.actualSpell = unitAttributes.getSpells().isEmpty() ? -1 : 0;
		this.lastTick = System.currentTimeMillis();
		this.effects = new ArrayList<>();
		this.hanging = false;
	}

	public void run(World world) {
		//if (ai != null) ai.step(this, world);
	}

	public AEntity tick() {
		for (int i = 0; i < effects.size(); i++) {
			boolean delete = effects.get(i).tick(this);
			if (delete) effects.remove(i--);
		}
		lastTick = System.nanoTime();
		return this.getHealth() >  0 ? this : null;
	}

	@Override
	public void draw(Graphics2D g) {
		if (unitAttributes.getImage() == null) {
			g.setColor(unitAttributes.getColor());
			g.fillRect((int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY());
		}
		else {
			if (!hanging) g.drawImage(unitAttributes.getImage(), (int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY(), null);
			else g.drawImage(unitAttributes.getImage(), (int) (getPositionX() + getSizeX()), (int) (getPositionY() + getSizeY()),
					(int) -getSizeX(), (int) -getSizeY(), null);
		}
	}

	public void drawHealthBar(Graphics2D g) {
		int startX = (int) getPositionX();
		int sizeX = (int) getSizeX();
		int startY = (int) (getPositionY() + getSizeY()) + GameSettings.HEALTH_BAR_SPACE;
		g.drawRect(startX, startY, sizeX, GameSettings.HEALTH_BAR_HEIGHT);
		int length = (int) ((health / unitAttributes.getHealth()) * sizeX);
		g.fillRect(startX, startY, length, GameSettings.HEALTH_BAR_HEIGHT);
	}

	public void drawSpellBar(Graphics2D g) {
		int startX = (int) getPositionX();
		int sizeX = (int) getSizeX();
		int startY = (int) (getPositionY() + getSizeY()) + GameSettings.SPELL_BAR_SPACE;
		g.drawRect(startX, startY, sizeX, GameSettings.SPELL_BAR_HEIGHT);
		int length = (int) ((castTime / SpellFactory.getSpellAttributes(actualSpell).getCastTime()) * sizeX);
		g.fillRect(startX, startY, length, GameSettings.SPELL_BAR_HEIGHT);
	}

	@Override
	public AEntity hit(Spell spell) {
		List<Integer> spellEffects = new ArrayList<>(spell.getAttributes().getEffects());
		for (Integer spellEffect : spellEffects) {
			effects.addAll(UnitFactory.getAllUnitChanges(spellEffect));
		}
		return this.getHealth() >  0 ? this : null;
	}

	public int cast() {
		castTime += castSpeed;
		double spellCast = SpellFactory.getSpellAttributes(actualSpell).getCastTime();
		int spells = 0;
		while (castTime > spellCast) {
			spells++;
			castTime -= spellCast;
		}
		return spells;
	}

	public void stopCast() {
		castTime = 0;
	}

	public void scrollSpell(boolean up) {
		stopCast();
		List<Integer> spellList = new ArrayList<>(unitAttributes.getSpells());
		int index = 0; {
			for (int i = 0; i < spellList.size(); i++) {
				if (spellList.get(i).equals(actualSpell)) {
					index = i;
					break;
				}
			}
		}
		index = up ? index + 1 : index - 1;
		if (index < 0) index = spellList.size() - 1;
		else if (index >= spellList.size()) index = 0;
		actualSpell = index;
	}

	public int getActualSpell() {
		return actualSpell;
	}

	public void setSpell(int newSpell) {
		actualSpell = unitAttributes.getSpells().contains(newSpell) ? newSpell : actualSpell;
	}

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

	public double getCastTime() {
		return castTime;
	}

	public void setCastTime(double castTime) {
		this.castTime = castTime;
	}

	public double getCastSpeed() {
		return castSpeed;
	}

	public void setCastSpeed(double castSpeed) {
		this.castSpeed = castSpeed;
	}

	public long getLastTick() {
		return lastTick;
	}

	public void setLastTick(long lastTick) { this.lastTick = lastTick; }

	public boolean isHanging() { return this.hanging; }

	public void setHanging(boolean hanging) { this.hanging = hanging; }
}
