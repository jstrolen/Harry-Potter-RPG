package cz.jstrolen.HP_RPG.game.entities.effects;

import cz.jstrolen.HP_RPG.support.Input;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josef Stroleny
 */
class EffectFactory {
    private static final String EFFECTS_PATH = "effects.xml";
    private static final Map<Integer, Effect> EFFECT_TYPES = new HashMap<>();

    static {
        loadEffects();
    }

    private static void loadEffects() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document dom = dBuilder.parse(Input.getFile(EFFECTS_PATH));

            Element doc = dom.getDocumentElement();
            NodeList objects = doc.getElementsByTagName("effect");
            for (int i = 0; i < objects.getLength(); i++) {
                NodeList nodes = objects.item(i).getChildNodes();
                int id = Integer.valueOf(nodes.item(1).getTextContent());
                String name = nodes.item(3).getTextContent();
                String title = nodes.item(5).getTextContent();
                String description = nodes.item(7).getTextContent();

                Effect e = new Effect(id, name, title, description);
                EFFECT_TYPES.put(id, e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Effect getEffect(int id) {
        return EFFECT_TYPES.get(id);
    }
}
