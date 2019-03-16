package socketgameshared.messaging.messages;

public class AddPointToPathMessage {
	private double x;
	private double y;

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public AddPointToPathMessage(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
