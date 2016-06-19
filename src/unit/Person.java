package unit;

import java.awt.Color;
import java.awt.image.BufferedImage;

import spells.Aquamenti;
import spells.AvadaKedavra;
import spells.Episkey;
import spells.Expelliarmus;
import spells.Incendio;
import spells.Levicorpus;
import spells.Protego;
import spells.Reducto;
import spells.Sectumsempra;
import spells.Stupefy;
import spells.WingardiumLeviosa;
import support.Input;
import ai.Intel;

public final class Person extends Unit {
	public static final int ID = 0;
	private static final double SIZE_X = 25.0;
	private static final double SIZE_Y = 25.0;
	private static final Color COLOR = Color.ORANGE;
	private static final BufferedImage IMAGE = Input.getImage(Unit.PATH + "person.png");
	private static final double SPEED = 5.0;
	private static final int HEALTH = 100;
	private static final boolean WALL = true;
	private static final boolean HITTABLE = true;
	private static final int[] SPELL_IDS = {Expelliarmus.ID, Incendio.ID, Stupefy.ID, Levicorpus.ID, Episkey.ID, Sectumsempra.ID, 
		Reducto.ID, WingardiumLeviosa.ID, Aquamenti.ID, Protego.ID, AvadaKedavra.ID};

	public Person(int positionX, int positionY, Intel intel) {
		super(positionX, positionY, SIZE_X, SIZE_Y, WALL, HITTABLE, COLOR, SPEED, HEALTH, SPELL_IDS, IMAGE, intel);
	}

	@Override
	public int getId() {
		return Person.ID;
	}
}
