package cz.jstrolen.HP_RPG.game.entities.objects;

/**
 * Created by Josef Stroleny
 */
public final class Teleport {
	private final String DESTINATION_NAME;
	private final int DESTINATION_X;
	private final int DESTINATION_Y;

	public Teleport(String destinationName, int destinationX, int destinationY) {
		this.DESTINATION_NAME = destinationName;
		this.DESTINATION_X = destinationX;
		this.DESTINATION_Y = destinationY;
	}

	public String getDestinationName() {
		return DESTINATION_NAME;
	}

	public int getDestinationX() { return DESTINATION_X; }

	public int getDestinationY() { return DESTINATION_Y; }
}
