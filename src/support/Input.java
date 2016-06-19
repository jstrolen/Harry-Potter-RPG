package support;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Input {
	private Input() {};
	private static String pathImages = "images/";
	private static String pathFiles = "files/";

	public static File getFile(String s) {
		try {
			return new File(Input.class.getClassLoader().getResource(/*"../../" + */pathFiles + s).toURI());
		} catch (Exception e) {
			System.out.println("Chyba při načítání souboru " + s + ".");
			return null;
		}
	}
	
	public static BufferedImage getImage(String s) {
		try {
			return ImageIO.read(Input.class.getClassLoader().getResource(pathImages + s));
		} catch (Exception e) {
			System.out.println("Chyba při načítání obrázku " + s + ".");
			return null;
		}
	}
}
