package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helper_methods.LevelMaker;
import helper_methods.Util;
import objects.Tower;
import scenes.Playing;

public class TowerManager {

	private Playing playing;
	private BufferedImage[] towerImgs;
	private ArrayList<Tower> towers = new ArrayList<>();
	private int towerAmount = 0;

	public TowerManager(Playing playing) {
		this.playing = playing;
		loadTowerImgs();
	}

	private void loadTowerImgs() { // load tower imgs to action bar
		BufferedImage atlas = LevelMaker.getSpriteAtlas();
		towerImgs = new BufferedImage[3];
		for (int i = 0; i < 3; i++)
			towerImgs[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
	}

	public void addTower(Tower selectedTower, int xPos, int yPos) {
		towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
	}

	public void removeTower(Tower displayedTower) { // to remove a tower when it is sold
		for (int i = 0; i < towers.size(); i++)
			if (towers.get(i).getId() == displayedTower.getId())
				towers.remove(i);
	}

	public void upgradeTower(Tower displayedTower) {
		for (Tower t : towers)
			if (t.getId() == displayedTower.getId())
				t.upgradeTower();
	}

	public void update() {
		for (Tower t : towers) {
			t.update();
			attackEnemyIfClose(t);
		}
	}

	private void attackEnemyIfClose(Tower t) {
		for (Enemy e : playing.getEnemyManger().getEnemies()) {
			if (e.isAlive())
				if (isEnemyInRange(t, e)) {
					if (t.isCooldownOver()) { // fire if enemy is within range and tower is not in cooldown
						playing.shootEnemy(t, e);
						t.resetCooldown();
					}
				} else {
					// ...nothing
				}
		}

	}

	private boolean isEnemyInRange(Tower t, Enemy e) { // is enemy within range?
		int range = Util.GetHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
		return range < t.getRange();
	}

	public void draw(Graphics g) {
		for (Tower t : towers)
			g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
	}

	// getters:
	public Tower getTowerAt(int x, int y) {
		for (Tower t : towers)
			if (t.getX() == x)
				if (t.getY() == y)
					return t;
		return null;
	}

	public BufferedImage[] getTowerImgs() {
		return towerImgs;
	}

	public void reset() {
		towers.clear();
		towerAmount = 0;
	}

}
