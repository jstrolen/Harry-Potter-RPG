package unit;

import ai.Intel;
import effects.UnitEffect;
import general.IUnitEffects;
import general.Item;
import general.Map;
import items.Items;
import spells.Spell;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Unit extends Item implements IUnitEffects, Runnable {
	public static final String PATH = "unit/";
	public static final int BAR_HEIGHT = 5;
	public static final Color HEALTH = Color.RED;
	
	private final double NORMAL_SPEED;
	private final int MAX_HEALTH;
	private double speed;
	private int health;
	private int[] SPELL_IDS;
	private Spell actual = null;
	private Items levitating = null;
	private boolean hang = false;
	private boolean canCastNow;
	private int spellNumber;
	private int spellId;
	private List<UnitEffect> effects;
	private Intel intel;
	
	public Unit(double positionX, double positionY, double sizeX, double sizeY, 
			boolean wall, boolean hittable, Color color, double speed, int health, 
			int[] spellIds, BufferedImage image, Intel intel) {
		super(positionX, positionY, sizeX, sizeY, wall, hittable, color, image);
		this.NORMAL_SPEED = speed;
		this.speed = speed;
		this.MAX_HEALTH = health;
		this.health = health;
		this.canCastNow = (spellIds.length > 0);
		SPELL_IDS = spellIds;
		spellNumber = -1;
		spellId = -1;
		if (spellIds.length > 0) {
			spellNumber = 0;
			spellId = spellIds[0];
		}
		effects = new ArrayList<UnitEffect>();
		this.setIntel(intel);
	}
	
	@Override
	public void run() {
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
		if (!isCanCastNow()) stopCast(Map.getInstance());
		
		if (intel != null) intel.step(this, Map.getInstance());
	}
	
	public static Unit getUnit(int id, int x, int y, Intel intel) {
		switch (id) {
		case Person.ID:
			return new Person(x, y, intel);
		}
		return null;
	}
	
	private void restore() {
		this.setHang(false);
		this.setCanCastNow(this.isCanCast());
		this.setSpeed(this.getNormalSpeed());
	}

	@Override
	public void nakresli(Graphics2D g) {
		g.setColor(getColor());
		if (getImage() == null) {
			g.fillRect((int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY());
		}
		else {
			if (hang) g.drawImage(getImage(), (int) (getPositionX() + getSizeX()), (int) (getPositionY() + getSizeY()), 
					(int) -getSizeX(), (int) -getSizeY(), null);
			else g.drawImage(getImage(), (int) getPositionX(), (int) getPositionY(), (int) getSizeX(), (int) getSizeY(), null);
		}
		for (int i = 0; i < effects.size(); i++) {
			effects.get(i).nakresli(g, this.getPositionX(), this.getPositionY(), this.getSizeX(), this.getSizeY());
		}
		
		int startX = (int) getPositionX();
		int lengthX = (int) this.getSizeX();
		int startY = (int) (getPositionY() + getSizeY());
		g.setColor(HEALTH);
		g.drawRect(startX, startY, lengthX, Unit.BAR_HEIGHT);
		int length = (int) (((double) health / MAX_HEALTH) * lengthX);
		g.fillRect(startX, startY, length, Unit.BAR_HEIGHT);
	}
	
	public void cast(double vector, Map map) {
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
			actual.setPositionX(this.getPositionX() + this.getSizeX() / 2 - actual.getSizeX() / 2);
			actual.setPositionY(this.getPositionY() + this.getSizeY() / 2 - actual.getSizeY() / 2);
			actual.setOrientation(vector);
			map.removeCasting(actual);
			map.newSpell(actual);
			actual = null;
		}
	}
	
	public void stopCast(Map map) {
		map.removeCasting(actual);
		actual = null;
	}

	public void move(double difX, double difY, Map map) {
		if (levitating == null) {
			double[] position = map.tryMove(this, this.getPositionX(), this.getPositionY(), difX, difY, this.getSizeX(), this.getSizeY());
			setPositionX(this.getPositionX() + position[0]);
			setPositionY(this.getPositionY() + position[1]);
		}
		else {
			double[] position = map.tryMove(levitating, levitating.getPositionX(), levitating.getPositionY(), difX, difY, levitating.getSizeX(), levitating.getSizeY());
			levitating.setPositionX(levitating.getPositionX() + position[0]);
			levitating.setPositionY(levitating.getPositionY() + position[1]);
		}
	}
	
	public boolean injured(int amount) {
		this.health -= amount;
		return (this.health <= 0);
	}
	
	public void unitHitted(UnitEffect effect) {
		effects.add(effect);
	}
	
	public void changeSpell(int i) {
		if (i > 0) spellNumber++;
		else spellNumber--;
		
		if (spellNumber >= this.getSpellCount()) spellNumber = 0;
		else if (spellNumber < 0) spellNumber = this.getSpellCount() - 1;
		
		spellId = SPELL_IDS[spellNumber];
	}
	
	public Spell getActualSpell() {
		return Spell.getSpell(this, spellId, 0);
	}
	
	public Spell[] getSpells() {
		Spell[] spells = new Spell[getSpellCount()];
		for (int i = 0; i < spells.length; i++) {
			spells[i] = Spell.getSpell(this, SPELL_IDS[i], 0);
		}
		return spells;
	}
	
	public void setSpell(int spellID) {
		for (int i = 0; i < this.getSpellCount(); i++) {
			if (SPELL_IDS[i] == spellID) {
				this.spellId = spellID;
				spellNumber = i;
				return;
			}
		}
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isHang() {
		return hang;
	}

	public void setHang(boolean hang) {
		this.hang = hang;
	}
	
	public Items getLevitating() {
		return levitating;
	}

	public void setLevitating(Items levitating) {
		this.levitating = levitating;
	}
	
	public int getSpellCount() {
		return SPELL_IDS.length;
	}

	public double getNormalSpeed() {
		return NORMAL_SPEED;
	}

	public boolean isCanCast() {
		return (SPELL_IDS.length > 0);
	}

	public boolean isCanCastNow() {
		return canCastNow;
	}

	public void setCanCastNow(boolean canCastNow) {
		this.canCastNow = canCastNow;
	}
	
	public int getMaxHealth() {
		return MAX_HEALTH;
	}
	
	public abstract int getId();

	public Intel getIntel() {
		return intel;
	}

	public void setIntel(Intel intel) {
		this.intel = intel;
	}
	
	public Spell getSpell() {
		return this.actual;
	}
}
