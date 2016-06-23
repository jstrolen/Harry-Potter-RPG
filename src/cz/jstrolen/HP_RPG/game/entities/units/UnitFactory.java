package cz.jstrolen.HP_RPG.game.entities.units;

import cz.jstrolen.HP_RPG.game.entities.units.transforms.UnitChange;
import cz.jstrolen.HP_RPG.game.entities.units.transforms.UnitTransform;
import cz.jstrolen.HP_RPG.game.entities.units.transforms.UnitTransformEffect;
import cz.jstrolen.HP_RPG.support.Input;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Josef Stroleny
 */
public class UnitFactory {
    private static final String UNIT_PATH = "units.xml";
    private static final String UNIT_TRANSFORM_PATH = "unitTransform.xml";
    private static final Map<Integer, UnitAttributes> UNIT_TYPES = new HashMap<>();
    private static final Map<Integer, UnitTransform> UNIT_TRANSFORMS = new HashMap<>();

    static{
        loadUnits();
        loadTransforms();
    }

    private static void loadUnits() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document dom = dBuilder.parse(Input.getFile(UNIT_PATH));

            Element doc = dom.getDocumentElement();
            Node sprite = doc.getElementsByTagName("sprite").item(0);
            String imagePath = sprite.getChildNodes().item(1).getFirstChild().getTextContent();
            BufferedImage image = Input.getImage(imagePath);
            int tileSize = Integer.valueOf(sprite.getChildNodes().item(3).getFirstChild().getTextContent());

            NodeList objects = doc.getElementsByTagName("unitList").item(0).getChildNodes();
            for (int i = 1; i < objects.getLength(); i += 2) {
                NodeList nodes = objects.item(i).getChildNodes();
                int id = Integer.valueOf(nodes.item(1).getTextContent());
                String name = nodes.item(3).getTextContent();
                String title = nodes.item(5).getTextContent();
                String description = nodes.item(7).getTextContent();
                double speed = Double.valueOf(nodes.item(9).getTextContent());
                double health = Double.valueOf(nodes.item(11).getTextContent());
                double castSpeed = Double.valueOf(nodes.item(13).getTextContent());
                Set<Integer> spells = new HashSet<>();
                NodeList spellList = nodes.item(15).getChildNodes();
                for (int j = 1; j < spellList.getLength(); j += 2) {
                    spells.add(Integer.valueOf(spellList.item(j).getTextContent()));
                }
                double objectWidth = Double.valueOf(nodes.item(17).getChildNodes().item(1).getTextContent());
                double objectHeight = Double.valueOf(nodes.item(17).getChildNodes().item(3).getTextContent());
                int colorRed = Integer.valueOf(nodes.item(19).getChildNodes().item(1).getTextContent());
                int colorGreen = Integer.valueOf(nodes.item(19).getChildNodes().item(3).getTextContent());
                int colorBlue = Integer.valueOf(nodes.item(19).getChildNodes().item(5).getTextContent());
                int imageWidth = Integer.valueOf(nodes.item(21).getChildNodes().item(1).getTextContent());
                int imageHeight = Integer.valueOf(nodes.item(21).getChildNodes().item(3).getTextContent());
                int imageX = Integer.valueOf(nodes.item(23).getChildNodes().item(1).getTextContent());
                int imageY = Integer.valueOf(nodes.item(23).getChildNodes().item(3).getTextContent());


                UnitAttributes ua = new UnitAttributes(id, name, title, description,
                        new Color(colorRed, colorGreen, colorBlue),
                        Input.crop(image, tileSize, imageWidth, imageHeight, imageX, imageY),
                        objectWidth, objectHeight, speed, health, castSpeed, spells);
                UNIT_TYPES.put(id, ua);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void loadTransforms() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document dom = dBuilder.parse(Input.getFile(UNIT_TRANSFORM_PATH));

            Element doc = dom.getDocumentElement();
            NodeList transforms = doc.getElementsByTagName("transforms").item(0).getChildNodes();
            for (int i = 1; i < transforms.getLength(); i += 2) {
                NodeList nodes = transforms.item(i).getChildNodes();
                int id = Integer.valueOf(nodes.item(1).getTextContent());
                String name = nodes.item(3).getTextContent();
                String title = nodes.item(5).getTextContent();
                String description = nodes.item(7).getTextContent();

                Set<Integer> effects = new HashSet<>();
                NodeList effectList = nodes.item(9).getChildNodes();
                for (int j = 1; j < effectList.getLength(); j += 2) {
                    effects.add(Integer.valueOf(effectList.item(j).getTextContent()));
                }

                Set<Integer> excludes = new HashSet<>();
                NodeList excludeList = nodes.item(11).getChildNodes();
                for (int j = 1; j < excludeList.getLength(); j += 2) {
                    excludes.add(Integer.valueOf(excludeList.item(j).getTextContent()));
                }

                List<UnitTransformEffect> transformTypes = new ArrayList<>();
                NodeList transformList = nodes.item(13).getChildNodes();
                for (int j = 1; j < transformList.getLength(); j += 2) {
                    double value = Double.valueOf(transformList.item(j).getAttributes().item(0).getTextContent());
                    double duration = Double.valueOf(transformList.item(j).getAttributes().item(1).getTextContent());
                    String text = transformList.item(j).getTextContent();
                    transformTypes.add(new UnitTransformEffect(text, value, duration));
                }

                UnitTransform ut = new UnitTransform(id, name, title, description, effects, transformTypes, excludes);
                UNIT_TRANSFORMS.put(id, ut);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Unit getUnit(int id, int x, int y) {
        return new Unit(x, y, UNIT_TYPES.get(id));
    }

    public static UnitChange getUnitTransform(int unitId, int transformId) {
        if (UNIT_TRANSFORMS.get(transformId).getExcludes().contains(unitId)) return null;
        return new UnitChange(UNIT_TRANSFORMS.get(transformId));
    }

    public static List<UnitChange> getAllUnitChanges(int effect) {
        return UNIT_TRANSFORMS.values().stream().filter(ut -> ut.getEffects().contains(effect)).map(UnitChange::new).collect(Collectors.toList());
    }
}
