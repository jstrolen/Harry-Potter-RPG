package cz.jstrolen.HP_RPG.game.entities.spells;

import cz.jstrolen.HP_RPG.game.entities.units.Unit;
import cz.jstrolen.HP_RPG.game.support.Input;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Josef Stroleny
 */
public class SpellFactory {
    private static final String SPELL_PATH = "spells.xml";
    private static final Map<Integer, SpellAttributes> SPELL_TYPES = new HashMap<>();

    static {
        loadSpells();
    }

    private static void loadSpells() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document dom = dBuilder.parse(Input.getFile(SPELL_PATH));

            Element doc = dom.getDocumentElement();
            Node sprite = doc.getElementsByTagName("sprite").item(0);
            String imagePath = sprite.getChildNodes().item(1).getFirstChild().getTextContent();
            BufferedImage image = Input.getImage(imagePath);
            int tileSize = Integer.valueOf(sprite.getChildNodes().item(3).getFirstChild().getTextContent());

            NodeList objects = doc.getElementsByTagName("spellList").item(0).getChildNodes();
            for (int i = 1; i < objects.getLength(); i += 2) {
                NodeList nodes = objects.item(i).getChildNodes();
                int id = Integer.valueOf(nodes.item(1).getTextContent());
                String name = nodes.item(3).getTextContent();
                String title = nodes.item(5).getTextContent();
                String description = nodes.item(7).getTextContent();

                double distanceVitality = Double.valueOf(nodes.item(9).getTextContent());
                double speed = Double.valueOf(nodes.item(11).getTextContent());
                double castTime = Double.valueOf(nodes.item(13).getTextContent());
                double dispersion = Double.valueOf(nodes.item(15).getTextContent());
                Set<Integer> effects = new HashSet<>();
                NodeList effectList = nodes.item(17).getChildNodes();
                for (int j = 1; j < effectList.getLength(); j += 2) {
                    effects.add(Integer.valueOf(effectList.item(j).getTextContent()));
                }

                double objectWidth = Double.valueOf(nodes.item(19).getChildNodes().item(1).getTextContent());
                double objectHeight = Double.valueOf(nodes.item(19).getChildNodes().item(3).getTextContent());
                int colorRed = Integer.valueOf(nodes.item(21).getChildNodes().item(1).getTextContent());
                int colorGreen = Integer.valueOf(nodes.item(21).getChildNodes().item(3).getTextContent());
                int colorBlue = Integer.valueOf(nodes.item(21).getChildNodes().item(5).getTextContent());
                int imageWidth = Integer.valueOf(nodes.item(23).getChildNodes().item(1).getTextContent());
                int imageHeight = Integer.valueOf(nodes.item(23).getChildNodes().item(3).getTextContent());
                int imageX = Integer.valueOf(nodes.item(25).getChildNodes().item(1).getTextContent());
                int imageY = Integer.valueOf(nodes.item(25).getChildNodes().item(3).getTextContent());

                SpellAttributes sa = new SpellAttributes(id, name, title, description,
                        new Color(colorRed, colorGreen, colorBlue),
                        Input.crop(image, tileSize, imageWidth, imageHeight, imageX, imageY),
                        objectWidth, objectHeight, distanceVitality, speed, castTime, dispersion, effects);
                SPELL_TYPES.put(id, sa);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Spell getSpell(Unit caster, int id, double orientation) {
        double startX = caster.getPositionX() + caster.getSizeX() / 2;
        double startY = caster.getPositionY() + caster.getSizeY() / 2;
        SpellAttributes attributes = SPELL_TYPES.get(id);
        if (attributes.getDispersion() > 0) orientation += ((2 * Math.random()) - 1) * attributes.getDispersion();

        return new Spell(caster, startX, startY, orientation, attributes);
    }
}
