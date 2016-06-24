package cz.jstrolen.HP_RPG.gui;

import java.awt.*;

/**
 * Created by Josef Stroleny
 */
public class GuiSettings {
    public static final int BUFFER_STRATEGY = 3;

    public static final double GAME_HERTZ = 40;
    public static final int MAX_UPDATES_BEFORE_RENDER = 2;
    public static final double TARGET_FPS = 30;

    public static final String TITLE = "HP Game";
    public static final Dimension SIZE = new Dimension(800, 600);

    public static final Font FONT = new Font("Sans-Serif", Font.BOLD, 12);
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color TEXT_BACKGROUND_COLOR = Color.WHITE;

    public static final int INFO_BOX_WIDTH = 150;
    public static final int INFO_BOX_HEIGHT = 40;
    public static final int TEXT_TOP_SPACE = 15;
    public static final int TEXT_LEFT_SPACE = 10;

    public static final String CURSOR_PATH = "images/wand.png";
    public static final String CURSOR_NAME = "Wand";
}
