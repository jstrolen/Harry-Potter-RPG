package ai;

public class Container {
	private final EMoves move;
	private final double size;
	private final double speed;
	
	public Container(EMoves move, double size) {
		this(move, size, Double.MAX_VALUE);
	}
	
	public Container(EMoves move, double size, double speed) {
		this.move = move;
		this.size = size;
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}

	public double getSize() {
		return size;
	}

	public EMoves getMove() {
		return move;
	}
}
