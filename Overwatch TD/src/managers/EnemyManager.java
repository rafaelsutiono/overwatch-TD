package managers;

import static helper_methods.Constants.Direction.*;
import static helper_methods.Constants.Enemies.*;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Flybot;
import enemies.Enemy;
import enemies.Darkbot;
import enemies.Bot;
import enemies.Spiderbot;
import helper_methods.LevelMaker;
import helper_methods.Util;
import objects.PathPoint;
import scenes.Playing;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[] enemyImgs;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private PathPoint start, end;
	private int HPbarWidth = 20;
	private BufferedImage slowEffect;
	private int[][] roadDirArr;

	public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
		this.playing = playing;
		enemyImgs = new BufferedImage[4]; // store an img for every enemy
		this.start = start; // start point
		this.end = end; // end point

		loadEffectImg();
		loadEnemyImgs();
		loadRoadDirArr();

	}

	// loaders:
	private void loadRoadDirArr() {
		roadDirArr = Util.GetRoadDirArr(playing.getGame().getTileManager().getTypeArr(), start, end);

	}

	private void loadEffectImg() {
		slowEffect = LevelMaker.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);
	}

	private void loadEnemyImgs() {
		BufferedImage atlas = LevelMaker.getSpriteAtlas();
		for (int i = 0; i < 4; i++)
			enemyImgs[i] = atlas.getSubimage(i * 32, 32, 32, 32);

	}


	public void update() {
		for (Enemy e : enemies)
			if (e.isAlive()) {
				updateEnemyMove(e);
			}

	}

	private void updateEnemyMove(Enemy e) { // method to update enemy movement
		PathPoint currTile = getEnemyTile(e);
		int dir = roadDirArr[currTile.getyCord()][currTile.getxCord()];

		e.move(GetSpeed(e.getEnemyType()), dir); // movement speed

		PathPoint newTile = getEnemyTile(e);

		if (!isTilesTheSame(currTile, newTile)) {
			if (isTilesTheSame(newTile, end)) { // kill enemy and remove one life from player when enemy reaches end
				e.kill();
				playing.removeOneLife();
				return;
			}
			int newDir = roadDirArr[newTile.getyCord()][newTile.getxCord()];
			if (newDir != dir) { // direction setter
				e.setPos(newTile.getxCord() * 32, newTile.getyCord() * 32);
				e.setLastDir(newDir);
			}
		}

	}

	private PathPoint getEnemyTile(Enemy e) { // to get path points for the enemy to go to

		switch (e.getLastDir()) {
		case LEFT:
			return new PathPoint((int) ((e.getX() + 31) / 32), (int) (e.getY() / 32));
		case UP:
			return new PathPoint((int) (e.getX() / 32), (int) ((e.getY() + 31) / 32));
		case RIGHT:
		case DOWN:
			return new PathPoint((int) (e.getX() / 32), (int) (e.getY() / 32));

		}

		return new PathPoint((int) (e.getX() / 32), (int) (e.getY() / 32));
	}

	private boolean isTilesTheSame(PathPoint currTile, PathPoint newTile) { // check if the next tile is the same, meaning bounds have been reached
		if (currTile.getxCord() == newTile.getxCord())
			if (currTile.getyCord() == newTile.getyCord())
				return true;
		return false;
	}
	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);
	}

	public void addEnemy(int enemyType) {

		int x = start.getxCord() * 32;
		int y = start.getyCord() * 32;

		switch (enemyType) {
		case BOT:
			enemies.add(new Bot(x, y, 0, this));
			break;
		case FLYBOT:
			enemies.add(new Flybot(x, y, 0, this));
			break;
		case DARKBOT:
			enemies.add(new Darkbot(x, y, 0, this));
			break;
		case SPIDERBOT:
			enemies.add(new Spiderbot(x, y, 0, this));
			break;
		}

	}

	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			if (e.isAlive()) {
				drawEnemy(e, g);
				drawHealthBar(e, g);
				drawEffects(e, g);
			}
		}
	}

	private void drawEffects(Enemy e, Graphics g) { // for slow effect
		if (e.isSlowed())
			g.drawImage(slowEffect, (int) e.getX(), (int) e.getY(), null);

	}

	private void drawHealthBar(Enemy e, Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) e.getX() + 16 - (getNewBarWidth(e) / 2), (int) e.getY() - 10, getNewBarWidth(e), 3);

	}

	private int getNewBarWidth(Enemy e) { // get new bar width after an enemy takes dmg
		return (int) (HPbarWidth * e.getHealthBarFloat());
	}

	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public int getAmountOfAliveEnemies() { // increment for every enemy alive
		int size = 0;
		for (Enemy e : enemies)
			if (e.isAlive())
				size++;

		return size;
	}

	public void rewardPlayer(int enemyType) {
		playing.rewardPlayer(enemyType);
	}

	public void reset() {
		enemies.clear();
	}

}
