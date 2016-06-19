package spells;

import general.ISpellHit;
import general.Item;
import general.Map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import unit.Unit;

public abstract class Spell extends Item implements ISpellHit {	
	public static final int BAR_HEIGHT = 5;
	private static final Random RANDOM = new Random();
	private double orientation;
	private double serviceLife;
	private double speed;
	private int castTime;
	private int castLoading;
	private boolean exist = false;
	private Unit caster;
	
	public Spell(Unit caster, double positionX, double positionY, boolean wall, boolean hittable, double orientation, double speed, int castTime, Color color, double serviceLife, int size) {
		super(positionX, positionY, size, size, wall, hittable, color, null);
		this.caster = caster;
		this.orientation = orientation;
		this.speed = speed;
		this.castTime = castTime;
		this.serviceLife = serviceLife;
	}
	
	@Override
	public void nakresli(Graphics2D g) {
		g.setColor(getColor());
		g.fillOval((int) getPositionX(), (int) getPositionY(), (int) this.getSizeX(), (int) this.getSizeY());
	}
	
	public void loading(Graphics2D g, Unit unit) {
		int startX = (int) (unit.getPositionX());
		int lengthX = (int) (unit.getSizeX());
		int startY = (int) (unit.getPositionY() + unit.getSizeY() + Unit.BAR_HEIGHT);
		
		g.setColor(getColor());
		g.drawRect(startX, startY, lengthX, Spell.BAR_HEIGHT);
		int length = (int) (((double) castLoading / castTime) * lengthX);
		g.fillRect(startX, startY, length, Spell.BAR_HEIGHT);
	}
	
	public boolean reload() {
		castLoading++;
		if (castLoading == castTime) exist = true;
		return exist;
	}
	
	public boolean nextStep(Map map) {
		if (exist) {
			double difX = Math.cos(orientation) * speed;
			double difY = Math.sin(orientation) * speed;
			
			map.tryHit(this, this.getPositionX(), this.getPositionY(), difX , difY, this.getSizeX(), this.getSizeY());
			
			if (this.isWall()) {
				double[] dif = map.tryMove(this, this.getPositionX(), this.getPositionY(), 
						difX, difY, this.getSizeX(), this.getSizeY());
				difX = dif[0];
				difY = dif[1];
			}
			
			setPositionX(this.getPositionX() + difX);
			setPositionY(this.getPositionY() + difY);
			serviceLife -= Math.max(Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2)), 0.1);
			if (serviceLife <= 0) {
				exist = false;
			}
		}
		return exist;
	}
	
	@Override
	public abstract String toString();
	
	public double getOrientation() {
		return orientation;
	}
	
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
	public Unit getCaster() {
		return caster;
	}
	
	public void setCaster(Unit caster) {
		this.caster = caster;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}
	
	public static Spell getSpell(Unit unit, int spellId, double vector) {
		double startX = unit.getPositionX() + unit.getSizeX() / 2;
		double startY = unit.getPositionY() + unit.getSizeY() / 2;
		switch (spellId) {
		case Stupefy.ID:
			return new Stupefy(unit, startX, startY, vector);
		case Reducto.ID:
			return new Reducto(unit, startX, startY, vector);
		case Expelliarmus.ID:
			return new Expelliarmus(unit, startX, startY, vector);
		case Aquamenti.ID:
			return new Aquamenti(unit, startX, startY, vector);
		case Incendio.ID:
			return new Incendio(unit, startX, startY, vector);
		case Protego.ID:
			return new Protego(unit, startX, startY, vector);
		case WingardiumLeviosa.ID:
			return new WingardiumLeviosa(unit, startX, startY, vector);
		case Levicorpus.ID:
			return new Levicorpus(unit, startX, startY, vector);
		case Sectumsempra.ID:
			return new Sectumsempra(unit, startX, startY, vector);
		case Episkey.ID:
			return new Episkey(unit, startX, startY, vector);
		case AvadaKedavra.ID:
			return new AvadaKedavra(unit, startX, startY, vector);
		}
		return null;
	}

	public static Spell getRandomDamagingSpell(Unit unit, double vector) {
		int[] spells = new int[]{Stupefy.ID, Expelliarmus.ID, Sectumsempra.ID, AvadaKedavra.ID, Reducto.ID};
		Spell[] mySpells = unit.getSpells();
		boolean contains = false;
		int spellId = -1;
		do {
			spellId = spells[RANDOM.nextInt(spells.length)];
			for (int i = 0; i < mySpells.length; i++) {
				if (mySpells[i].getId() == spellId) {
					contains = true;
					break;
				}
			}
		} while (!contains);
		return Spell.getSpell(unit, spellId, vector);
	}
	
	public abstract int getId();
}
