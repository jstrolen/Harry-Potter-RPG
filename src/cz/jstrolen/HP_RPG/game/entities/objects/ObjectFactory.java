package cz.jstrolen.HP_RPG.game.entities.objects;

import cz.jstrolen.HP_RPG.support.Input;
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
public class ObjectFactory {
	private static final String BLOCK_PATH = "blocks.xml";
	private static final String ITEM_PATH = "items.xml";

	private static final String BLOCK_TRANSFORM_PATH = "blockTransform.xml";
	private static final String ITEM_TRANSFORM_PATH = "itemTransform.xml";

	private static final Map<Integer, ObjectAttributes> BLOCK_TYPES = new HashMap<>();
	private static final Map<Integer, ObjectAttributes> ITEM_TYPES = new HashMap<>();

	private static final Map<Integer, ObjectTransform> BLOCK_TRANSFORMS = new HashMap<>();
	private static final Map<Integer, ObjectTransform> ITEM_TRANSFORMS = new HashMap<>();


	private static int BLOCK_SIZE;

	static {
		loadObjects(true);
		loadObjects(false);
		loadTransforms(true);
		loadTransforms(false);
	}

	private static void loadObjects(boolean blocks) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document dom = null;
			if (blocks) dom = dBuilder.parse(Input.getFile(BLOCK_PATH));
			else dom = dBuilder.parse(Input.getFile(ITEM_PATH));

			Element doc = dom.getDocumentElement();
			if (blocks) BLOCK_SIZE = Integer.parseInt(doc.getElementsByTagName("blockSize").item(0).getTextContent());

			Node sprite = doc.getElementsByTagName("sprite").item(0);
			String imagePath = sprite.getChildNodes().item(1).getFirstChild().getTextContent();
			BufferedImage image = Input.getImage(imagePath);
			int tileSize = Integer.valueOf(sprite.getChildNodes().item(3).getFirstChild().getTextContent());

			NodeList objects = doc.getElementsByTagName("objectList").item(0).getChildNodes();
			for (int i = 1; i < objects.getLength(); i += 2) {
				NodeList nodes = objects.item(i).getChildNodes();
				int id = Integer.valueOf(nodes.item(1).getTextContent());
				String name = nodes.item(3).getTextContent();
				String title = nodes.item(5).getTextContent();
				String description = nodes.item(7).getTextContent();
				boolean hittable = nodes.item(9).getTextContent().equalsIgnoreCase("true");
				boolean crossable = nodes.item(11).getTextContent().equalsIgnoreCase("true");
				boolean flyable = nodes.item(13).getTextContent().equalsIgnoreCase("true");

				int index = 15;
				double sizeX, sizeY;
				if (blocks) sizeX = sizeY = BLOCK_SIZE;
				else {
					sizeX = Double.valueOf(nodes.item(15).getChildNodes().item(1).getTextContent());
					sizeY = Double.valueOf(nodes.item(15).getChildNodes().item(3).getTextContent());
					index = 17;
				}

				int colorRed = Integer.valueOf(nodes.item(index).getChildNodes().item(1).getTextContent());
				int colorGreen = Integer.valueOf(nodes.item(index).getChildNodes().item(3).getTextContent());
				int colorBlue = Integer.valueOf(nodes.item(index).getChildNodes().item(5).getTextContent());
				int imageWidth = Integer.valueOf(nodes.item(index + 2).getChildNodes().item(1).getTextContent());
				int imageHeight = Integer.valueOf(nodes.item(index + 2).getChildNodes().item(3).getTextContent());
				int imageX = Integer.valueOf(nodes.item(index + 4).getChildNodes().item(1).getTextContent());
				int imageY = Integer.valueOf(nodes.item(index + 4).getChildNodes().item(3).getTextContent());

				ObjectAttributes oa = new ObjectAttributes(id, name, title, description,
						new Color(colorRed, colorGreen, colorBlue),
						Input.crop(image, tileSize, imageWidth, imageHeight, imageX, imageY),
						sizeX, sizeY, hittable, crossable, flyable);
				if (blocks) BLOCK_TYPES.put(id, oa);
				else ITEM_TYPES.put(id, oa);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void loadTransforms(boolean blocks) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document dom = null;
			if (blocks) dom = dBuilder.parse(Input.getFile(BLOCK_TRANSFORM_PATH));
			else dom = dBuilder.parse(Input.getFile(ITEM_TRANSFORM_PATH));

			Element doc = dom.getDocumentElement();
			NodeList transforms = doc.getElementsByTagName("transforms").item(0).getChildNodes();
			for (int i = 1; i < transforms.getLength(); i += 2) {
				NodeList nodes = transforms.item(i).getChildNodes();
				int id = Integer.valueOf(nodes.item(1).getTextContent());
				String name = nodes.item(3).getTextContent();
				String title = nodes.item(5).getTextContent();
				String description = nodes.item(7).getTextContent();

				int sourceId = Integer.valueOf(nodes.item(9).getTextContent());
				int targetId = Integer.valueOf(nodes.item(11).getTextContent());
				int durability = Integer.valueOf(nodes.item(13).getTextContent());

				Set<Integer> transformTypes = new HashSet<>();
				NodeList transformList = nodes.item(15).getChildNodes();
				for (int j = 1; j < transformList.getLength(); j += 2) {
					transformTypes.add(Integer.valueOf(transformList.item(j).getTextContent()));
				}

				Set<Integer> antitransformTypes = new HashSet<>();
				NodeList antitransformList = nodes.item(17).getChildNodes();
				for (int j = 1; j < antitransformList.getLength(); j += 2) {
					antitransformTypes.add(Integer.valueOf(antitransformList.item(j).getTextContent()));
				}

				ObjectTransform t = new ObjectTransform(id, name, title, description, sourceId, targetId, durability, transformTypes, antitransformTypes);
				if (blocks) {
					BLOCK_TRANSFORMS.put(id, t);
					BLOCK_TYPES.get(sourceId).getTransform().add(id);
				}
				else {
					ITEM_TRANSFORMS.put(id, t);
					ITEM_TYPES.get(sourceId).getTransform().add(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static Block getBlock(int id, int x, int y) { return BLOCK_TYPES.containsKey(id) ? new Block(x, y, BLOCK_TYPES.get(id)) : null; }

	public static Item getItem(int id, int x, int y) { return ITEM_TYPES.containsKey(id) ? new Item(x, y, ITEM_TYPES.get(id)) : null; }

	public static ObjectTransform getBlockTransforms(int id) { return BLOCK_TRANSFORMS.get(id); }

	public static ObjectTransform getItemTransforms(int id) { return ITEM_TRANSFORMS.get(id); }

	public static int getBlockSize() { return BLOCK_SIZE; }
}
