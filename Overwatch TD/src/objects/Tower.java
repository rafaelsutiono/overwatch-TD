package objects;

import static helper_methods.Constants.Towers.*;

public class Tower {

	private int x, y, id, towerType, cdTick, dmg;
	private float range, cooldown;
	private int tier;

	public Tower(int x, int y, int id, int towerType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.towerType = towerType;
		tier = 1;
		setDefaultDmg();
		setDefaultRange();
		setDefaultCooldown();
	}

	public void update() {
		cdTick++;
	} // used for tower firing cooldowns

	public void upgradeTower() {
		this.tier++;

		switch (towerType) { // upgrade tower stats
		case HANZO:
			dmg += 3;
			range += 20;
			cooldown -= 8;
			break;
		case BASTION:
			dmg += 6;
			range += 35;
			cooldown -= 5;
			break;
		case MEI:
			range += 20;
			cooldown -= 20;
			break;
		}
	}

	public boolean isCooldownOver() { // for when firing cooldown is over

		return cdTick >= cooldown;
	}

	public void resetCooldown() {
		cdTick = 0;
	} // to effectively reset a tower's firing cooldown

	// getters n setters:
	private void setDefaultCooldown() {
		cooldown = helper_methods.Constants.Towers.GetDefaultCooldown(towerType);

	}

	private void setDefaultRange() {
		range = helper_methods.Constants.Towers.GetDefaultRange(towerType);

	}

	private void setDefaultDmg() {
		dmg = helper_methods.Constants.Towers.GetStartDmg(towerType);

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTowerType() {
		return towerType;
	}

	public void setTowerType(int towerType) {
		this.towerType = towerType;
	}

	public int getDmg() {
		return dmg;
	}

	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}

	public int getTier() {
		return tier;
	}

}
