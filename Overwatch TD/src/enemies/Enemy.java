package enemies;

import static helper_methods.Constants.Direction.*;

import java.awt.Rectangle;

import managers.EnemyManager;

public abstract class Enemy {
	protected EnemyManager enemyManager;
	protected float x, y;
	protected Rectangle bounds;
	protected int health;
	protected int maxHealth;
	protected int ID;
	protected int enemyType;
	protected int lastDir;
	protected boolean alive = true;
	protected int slowTickLimit = 120;
	protected int slowTick = slowTickLimit;

	public Enemy(float x, float y, int ID, int enemyType, EnemyManager enemyManager) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.enemyType = enemyType;
		this.enemyManager = enemyManager;
		bounds = new Rectangle((int) x, (int) y, 32, 32);
		lastDir = -1; // -1 -> no direction; enemy coords don't change when lastDir == 1
		setStartHealth();
	}

	private void setStartHealth() { // sets initial n max health of enemy based on its type,
		health = helper_methods.Constants.Enemies.GetStartHealth(enemyType);
		maxHealth = health;
	}

	public void hurt(int dmg) { //  reduces the enemy's health by the specified dmg
		this.health -= dmg;
		if (health <= 0) { // health <= 0 -> enemy alive = false -> rewards player based on enemy type killed
			alive = false;
			enemyManager.rewardPlayer(enemyType);
		}

	}

	public void kill() { // to unalive enemy if it reaches the end of a path
		alive = false;
		health = 0;
	}

	public void slow() {
		slowTick = 0; // apply slow on enemy; ticks for duration
	}

	public void move(float speed, int dir) {
		lastDir = dir;

		if (slowTick < slowTickLimit) { // adjusts speed of enemy when slowed until its duration ends
			slowTick++;
			speed *= 0.5f;
		}

		switch (dir) { // moves enemy in a specified direction
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
			break;
		}

		updateHitbox();
	}

	private void updateHitbox() { // updates enemy's bounding rectangle
		bounds.x = (int) x;
		bounds.y = (int) y;
	}

	public void setPos(int x, int y) { // for updating enemy positions
		this.x = x;
		this.y = y;
	}

	// getters n setters:
	public float getHealthBarFloat() {
		return health / (float) maxHealth;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getHealth() {
		return health;
	}

	public int getID() {
		return ID;
	}

	public int getEnemyType() {
		return enemyType;
	}

	public int getLastDir() {
		return lastDir;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isSlowed() {
		return slowTick < slowTickLimit;
	}

	public void setLastDir(int newDir) {
		this.lastDir = newDir;
	}

}
