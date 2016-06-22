package cz.jstrolen.HP_RPG.game.entities;

/**
 * Created by Josef Stroleny
 */
public abstract class AInfo {
    private final int ID;
    private final String NAME;
    private final String TITLE;
    private final String DESCRIPTION;

    public AInfo(int id, String name, String title, String description) {
        this.ID = id;
        this.NAME = name;
        this.TITLE = title;
        this.DESCRIPTION = description;
    }


}
