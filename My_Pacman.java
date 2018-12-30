package GUI;

public class My_Pacman{
/**
 * A class that represents a "robot" with a location, orientation and ability to move (at defined speed)
 * @return getX, getY, getZ, getSpeed, getEatR,getAngle
 */
	private double x;
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	private double y;
	private double z;
	private double speed;
	private double eatR;
	private double angle;
	
	public My_Pacman() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.speed = 1;
		this.eatR = 1;
		this.angle = 0;
	}

	public My_Pacman(double x, double y, double z, double speed, double eatR, double angle) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.speed = speed;
		this.eatR = eatR;
		this.angle = angle;
	}

	public My_Pacman(My_Pacman pacman) {
		this.x = pacman.x;
		this.y = pacman.y;
		this.z = pacman.z;
		this.speed = pacman.speed;
		this.eatR = pacman.eatR;
		this.angle = pacman.angle;
	}


	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getEatR() {
		return eatR;
	}

	public void setEatR(double eatR) {
		this.eatR = eatR;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

}
