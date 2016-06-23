package cz.jstrolen.HP_RPG.game.entities;

/**
 * Created by Josef Stroleny
 */
public abstract class AInfo {
    private final int ID;
    private final String NAME;
    private final String TITLE;
    private final String DESCRIPTION;

    protected AInfo(int id, String name, String title, String description) {
        this.ID = id;
        this.NAME = name;
        this.TITLE = title;
        this.DESCRIPTION = description;
    }

    public int getId() {
        return ID;
    }

    public String getName() {
        return NAME;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getDescription() {
        return DESCRIPTION;
    }
}
