package objects;

import java.awt.geom.Point2D;

public class Projectile { // projectile base class

	private Point2D.Float pos;
	private int id, projectileType, dmg;
	private float xSpeed, ySpeed, rotation;
	private boolean active = true;

	public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg, float rotation, int id, int projectileType) { // projectile attributes
		pos = new Point2D.Float(x, y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.dmg = dmg;
		this.rotation = rotation;
		this.id = id;
		this.projectileType = projectileType;
	}

	public void reuse(int x, int y, float xSpeed, float ySpeed, int dmg, float rotate) { // method to reuse inactive projectiles
		pos = new Point2D.Float(x, y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.dmg = dmg;
		this.rotation = rotate;
		active = true;
	}

	public void move() { // method for moving projectiles
		pos.x += xSpeed;
		pos.y += ySpeed;
	}

	public Point2D.Float getPos() {
		return pos;
	}

	public void setPos(Point2D.Float pos) {
		this.pos = pos;
	}

	public int getId() {
		return id;
	}

	public int getProjectileType() {
		return projectileType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getDmg() {
		return dmg;
	}

	public float getRotation() {
		return rotation;
	}

}
