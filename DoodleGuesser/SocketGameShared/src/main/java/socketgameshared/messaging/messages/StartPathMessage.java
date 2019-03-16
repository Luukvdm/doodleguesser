package socketgameshared.messaging.messages;

public class StartPathMessage {
	private double x;
	private double y;

	private double r;
	private double g;
	private double b;

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getColorR() {
		return this.r;
	}

	public double getColorG() {
		return this.g;
	}

	public double getColorB() {
		return this.b;
	}

	public StartPathMessage(double x, double y) {
		this.x = x;
		this.y = y;
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}

	public StartPathMessage(double x, double y, double r, double g, double b) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.g = g;
		this.b = b;
	}
}
