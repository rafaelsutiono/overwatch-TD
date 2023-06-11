package scenes;

import static helper_methods.Constants.Tiles.GRASS_TILE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enemies.Enemy;
import helper_methods.LevelMaker;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {

	private int[][] lvl;

	private ActionBar actionBar;
	private int mouseX, mouseY;
	private EnemyManager enemyManager;
	private TowerManager towerManager;
	private ProjectileManager projManager;
	private WaveManager waveManager;
	private PathPoint start, end;
	private Tower selectedTower;
	private int goldTick;
	private boolean gamePaused;

	public Playing(Game game) {
		super(game);
		loadDefaultLevel();
		// initiate object managers:
		actionBar = new ActionBar(0, 640, 640, 160, this);
		enemyManager = new EnemyManager(this, start, end);
		towerManager = new TowerManager(this);
		projManager = new ProjectileManager(this);
		waveManager = new WaveManager(this);
	}

	private void loadDefaultLevel() {
		lvl = LevelMaker.GetLevelData(); // level constructor
		ArrayList<PathPoint> points = LevelMaker.GetLevelPathPoints();
		start = points.get(0);
		end = points.get(1);
	}

	public void setLevel(int[][] lvl) {
		this.lvl = lvl;
	}

	public void update() {
		if (!gamePaused) {
			updateTick();
			waveManager.update();

			// gold tick
			goldTick++;
			if (goldTick % (60 * 3) == 0)
				actionBar.addGold(1);

			if (isAllEnemiesDead()) {
				if (isThereMoreWaves()) { // time to next wave timer starts
					waveManager.startWaveTimer();
					if (isWaveTimerOver()) { // next wave starts when timer hits 0
						waveManager.increaseWaveIndex();
						enemyManager.getEnemies().clear();
						waveManager.resetEnemyIndex();

					}
				}
			}

			if (isTimeForNewEnemy()) { // enemy spawner conditions
				if (!waveManager.isWaveTimerOver())
					spawnEnemy();
			}

			enemyManager.update();
			towerManager.update();
			projManager.update();
		}

	}

	private boolean isWaveTimerOver() {

		return waveManager.isWaveTimerOver();
	}

	private boolean isThereMoreWaves() {
		return waveManager.isThereMoreWaves();

	}

	private boolean isAllEnemiesDead() { // check if all enemies have died

		if (waveManager.isThereMoreEnemiesInWave())
			return false;

		for (Enemy e : enemyManager.getEnemies()) // check through all enemies' status (alive or no?)
			if (e.isAlive())
				return false;

		return true;
	}

	private void spawnEnemy() {
		enemyManager.spawnEnemy(waveManager.getNextEnemy());
	}

	private boolean isTimeForNewEnemy() { // check if a new enemy must be spawned in a wave
		if (waveManager.isTimeForNewEnemy()) {
			if (waveManager.isThereMoreEnemiesInWave())
				return true;
		}

		return false;
	}

	public void setSelectedTower(Tower selectedTower) {
		this.selectedTower = selectedTower;
	}

	@Override
	public void render(Graphics g) {

		drawLevel(g);
		actionBar.draw(g);
		enemyManager.draw(g);
		towerManager.draw(g);
		projManager.draw(g);

		drawSelectedTower(g);
		drawHighlight(g);

	}

	private void drawHighlight(Graphics g) { // method to draw a white border around a selected tower
		g.setColor(Color.WHITE);
		g.drawRect(mouseX, mouseY, 32, 32);

	}

	private void drawSelectedTower(Graphics g) {
		if (selectedTower != null)
			g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
	}

	private void drawLevel(Graphics g) { // draw level according to lvl file

		for (int y = 0; y < lvl.length; y++) {
			for (int x = 0; x < lvl[y].length; x++) {
				int id = lvl[y][x];
				if (isAnimation(id)) {
					g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
				} else
					g.drawImage(getSprite(id), x * 32, y * 32, null);
			}
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (y >= 640)
			actionBar.mouseClicked(x, y);
		else { // above y = 640 -> action bar
			if (selectedTower != null) {
				// trying to place a tower
				if (isTileGrass(mouseX, mouseY)) {
					if (getTowerAt(mouseX, mouseY) == null) {
						towerManager.addTower(selectedTower, mouseX, mouseY);

						removeGold(selectedTower.getTowerType());

						selectedTower = null;

					}
				}
			} else {
				// not trying to place a tower
				// checking if a tower exists at x,y
				Tower t = getTowerAt(mouseX, mouseY);
				actionBar.displayTower(t);
			}
		}
	}

	private void removeGold(int towerType) {
		actionBar.payForTower(towerType);

	}

	public void upgradeTower(Tower displayedTower) {
		towerManager.upgradeTower(displayedTower);

	}

	public void removeTower(Tower displayedTower) {
		towerManager.removeTower(displayedTower);
	}

	private Tower getTowerAt(int x, int y) {
		return towerManager.getTowerAt(x, y);
	}

	private boolean isTileGrass(int x, int y) { // grass tile validation for placing towers
		int id = lvl[y / 32][x / 32];
		int tileType = game.getTileManager().getTile(id).getTileType();
		return tileType == GRASS_TILE;
	}

	public void shootEnemy(Tower t, Enemy e) {
		projManager.newProjectile(t, e);

	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	public void keyPressed(KeyEvent e) { // press escape to deselect a selected tower type from the action bar
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			selectedTower = null;
		}
	}

	// mouse input listeners:
	@Override
	public void mouseMoved(int x, int y) {
		if (y >= 640)
			actionBar.mouseMoved(x, y);
		else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640)
			actionBar.mousePressed(x, y);

	}

	@Override
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {

	}

	public void rewardPlayer(int enemyType) {
		actionBar.addGold(helper_methods.Constants.Enemies.GetReward(enemyType));
	}

	public TowerManager getTowerManager() {
		return towerManager;
	}

	public EnemyManager getEnemyManger() {
		return enemyManager;
	}

	public WaveManager getWaveManager() {
		return waveManager;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void removeOneLife() {
		actionBar.removeOneLife();
	}

	public void resetEverything() { // resetter method for replaying the game

		actionBar.resetEverything();

		// managers
		enemyManager.reset();
		towerManager.reset();
		projManager.reset();
		waveManager.reset();

		mouseX = 0;
		mouseY = 0;

		selectedTower = null;
		goldTick = 0;
		gamePaused = false;

	}
	private void drawWinMsg(Graphics g) {
		g.drawString("You beat the game!", 425, 790);
	}

}