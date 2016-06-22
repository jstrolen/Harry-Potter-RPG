package cz.jstrolen.HP_RPG.support;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Josef Stroleny
 */
public class Input {
	private Input() {}

	public static File getFile(String s) {
		try {
			return new File(Input.class.getClassLoader().getResource(s).toURI());
		} catch (Exception e) {
			System.out.println("Chyba při načítání souboru " + s + ".");
			return null;
		}
	}
	
	public static BufferedImage getImage(String s) {
		try {
			return ImageIO.read(Input.class.getClassLoader().getResource(s));
		} catch (Exception e) {
			System.out.println("Chyba při načítání obrázku " + s + ".");
			return null;
		}
	}

	public static BufferedImage crop(BufferedImage original, int tileSize, int imageWidth, int imageHeight,
									  int imageX, int imageY) {
		return original.getSubimage(tileSize * imageX, tileSize * imageY, imageWidth, imageHeight);
	}
}
