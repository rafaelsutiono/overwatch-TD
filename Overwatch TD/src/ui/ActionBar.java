package ui;

import static main.GameStates.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import helper_methods.Constants.Towers;
import objects.Tower;
import scenes.Playing;

public class ActionBar extends Bar {

	private Playing playing;
	private MyButton bMenu, bPause;

	private MyButton[] towerButtons;
	private Tower selectedTower;
	private Tower displayedTower;
	private MyButton sellTower, upgradeTower;

	private DecimalFormat formatter;

	private int gold = 250; // player starts with 250 gold
	private boolean showTowerCost;
	private int towerCostType;

	private int lives = 1; // player starts with specified lives; 1 for hardcore ohoho

	public ActionBar(int x, int y, int width, int height, Playing playing) { // initialize the action bar
		super(x, y, width, height);
		this.playing = playing;
		formatter = new DecimalFormat("0.0");

		initButtons();
	}

	public void resetEverything() { // reset all the variables and components of the action bar to starting values
		lives = 1;
		towerCostType = 0;
		showTowerCost = false;
		gold = 250;
		selectedTower = null;
		displayedTower = null;
	}

	private void initButtons() { // initialize buttons used in the action bar

		bMenu = new MyButton("Menu", 2, 642, 100, 30);
		bPause = new MyButton("Pause", 2, 682, 100, 30);

		towerButtons = new MyButton[3]; // tower selection buttons

		// tower selection buttons - measurements & pos
		int w = 50;
		int h = 50;
		int xStart = 110;
		int yStart = 650;
		int xOffset = (int) (w * 1.1f);

		for (int i = 0; i < towerButtons.length; i++)
			towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);

		// sell & upgrade buttons:
		sellTower = new MyButton("Sell", 420, 702, 80, 25);
		upgradeTower = new MyButton("Upgrade", 545, 702, 80, 25);

	}

	public void removeOneLife() { // method to remove lives and check if it has reached 0
		lives--;
		if (lives <= 0)
			SetGameState(GAME_OVER);
	}

	private void drawButtons(Graphics g) { // draw menu, pause, tower buttons
		bMenu.draw(g);
		bPause.draw(g);

		for (MyButton b : towerButtons) {
			g.setColor(Color.gray);
			g.fillRect(b.x, b.y, b.width, b.height);
			g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
			drawButtonFeedback(g, b);
		}
	}

	public void draw(Graphics g) {

		// bg:
		g.setColor(new Color(255, 213, 160));
		g.fillRect(x, y, width, height);

		// buttons:
		drawButtons(g);

		// displayed tower:
		drawDisplayedTower(g);

		// wave info:
		drawWaveInfo(g);

		// gold info:
		drawGoldAmount(g);

		// draw tower cost:
		if (showTowerCost)
			drawTowerCost(g);

		// game paused text:
		if (playing.isGamePaused()) {
			g.setColor(Color.black);
			g.drawString("Game is Paused!", 110, 790);
		}

		// lives:
		g.setColor(Color.black);
		g.drawString("Lives: " + lives, 110, 750);

	}

	private void drawTowerCost(Graphics g) { // draw tower cost button
		g.setColor(Color.gray);
		g.fillRect(280, 650, 120, 50);
		g.setColor(Color.black);
		g.drawRect(280, 650, 120, 50);

		g.drawString("" + getTowerCostName(), 285, 670);
		g.drawString("Cost: " + getTowerCostCost() + "g", 285, 695);

		// if player lacks gold for the selected tower:
		if (isTowerCostMoreThanCurrentGold()) {
			g.setColor(Color.RED);
			g.drawString("lol u too broke", 270, 725);

		}

	}

	private boolean isTowerCostMoreThanCurrentGold() {
		return getTowerCostCost() > gold; // checker if player is too broke to afford a tower
	}

	private String getTowerCostName() { // tower name when buying
		return helper_methods.Constants.Towers.GetName(towerCostType);
	}

	private int getTowerCostCost() { // tower cost when buying
		return helper_methods.Constants.Towers.GetTowerCost(towerCostType);
	}

	private void drawGoldAmount(Graphics g) { // gold info
		g.drawString("Gold: " + gold + "g", 110, 725);

	}

	private void drawWaveInfo(Graphics g) { // wave info
		g.setColor(Color.black);
		g.setFont(new Font("CenturyGothic", Font.BOLD, 20));
		drawWaveTimerInfo(g);
		drawEnemiesLeftInfo(g);
		drawWavesLeftInfo(g);

	}

	private void drawWavesLeftInfo(Graphics g) { // wave counter
		int current = playing.getWaveManager().getWaveIndex();
		int size = playing.getWaveManager().getWaves().size();
		g.drawString("Wave " + (current + 1) + " / " + size, 425, 770);

	}

	private void drawEnemiesLeftInfo(Graphics g) { // enemies left counter
		int remaining = playing.getEnemyManger().getAmountOfAliveEnemies();
		g.drawString("Enemies Left: " + remaining, 425, 790);
	}

	private void drawWaveTimerInfo(Graphics g) { // wave timer before next wave comes in
		if (playing.getWaveManager().isWaveTimerStarted()) {

			float timeLeft = playing.getWaveManager().getTimeLeft();
			String formattedText = formatter.format(timeLeft);
			g.drawString("Next wave in: " + formattedText, 425, 750);
		}
	}

	private void drawDisplayedTower(Graphics g) {
		// draw tower in game:
		if (displayedTower != null) {
			g.setColor(Color.gray);
			g.fillRect(410, 645, 220, 85);
			g.setColor(Color.black);
			g.drawRect(410, 645, 220, 85);
			g.drawRect(420, 650, 50, 50);
			g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 420, 650, 50, 50, null);
			g.setFont(new Font("LucidaSans", Font.BOLD, 15));
			g.drawString("" + Towers.GetName(displayedTower.getTowerType()), 480, 660);
			g.drawString("ID: " + displayedTower.getId(), 480, 675);
			g.drawString("Tier: " + displayedTower.getTier(), 560, 660);
			drawDisplayedTowerBorder(g);
			drawDisplayedTowerRange(g); // range circle

			// sell button:
			sellTower.draw(g);
			drawButtonFeedback(g, sellTower);

			// upgrade Button:
			if (displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
				upgradeTower.draw(g);
				drawButtonFeedback(g, upgradeTower);
			}

			if (sellTower.isMouseOver()) {
				g.setColor(Color.red);
				g.drawString("Sell for: " + getSellAmount(displayedTower) + "g", 480, 695);
			} else if (upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)) {
				g.setColor(Color.blue);
				g.drawString("Upgrade for: " + getUpgradeAmount(displayedTower) + "g", 480, 695);
			}

		}

	}

	private int getUpgradeAmount(Tower displayedTower) { // upgrade multiplier for towers
		return (int) (helper_methods.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) * 0.7f);
	}

	private int getSellAmount(Tower displayedTower) {
		int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
		upgradeCost *= 0.5f;

		return helper_methods.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) / 2 + upgradeCost;
	}

	private void drawDisplayedTowerRange(Graphics g) { // drawing an oval to display a tower's range
		g.setColor(Color.white);
		g.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2, displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2, (int) displayedTower.getRange() * 2,
				(int) displayedTower.getRange() * 2);

	}

	private void drawDisplayedTowerBorder(Graphics g) { // tower's square bounds

		g.setColor(Color.CYAN);
		g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);

	}

	public void displayTower(Tower t) {
		displayedTower = t;
	} // helper to check if a displayed tower exists at x,y in PLAYING

	private void sellTowerClicked() {
		playing.removeTower(displayedTower);
		gold += helper_methods.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) / 2;

		int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
		upgradeCost *= 0.5f;
		gold += upgradeCost; // formula to calculate gold that player receives when selling a tower

		displayedTower = null;

	}

	private void upgradeTowerClicked() { // subtract gold when player upgrades tower
		playing.upgradeTower(displayedTower);
		gold -= getUpgradeAmount(displayedTower);

	}

	private void togglePause() {
		playing.setGamePaused(!playing.isGamePaused());

		if (playing.isGamePaused())
			bPause.setText("Unpause");
		else
			bPause.setText("Pause");

	}

	public void mouseClicked(int x, int y) { // mouse click actions: sell tower, upgrade tower, placing tower
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);
		else if (bPause.getBounds().contains(x, y))
			togglePause();
		else {

			if (displayedTower != null) {
				if (sellTower.getBounds().contains(x, y)) {
					sellTowerClicked();

					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
					upgradeTowerClicked();
					return;
				}
			}

			for (MyButton b : towerButtons) {
				if (b.getBounds().contains(x, y)) {
					if (!isGoldEnoughForTower(b.getId()))
						return;

					selectedTower = new Tower(0, 0, -1, b.getId());
					playing.setSelectedTower(selectedTower);
					return;
				}
			}
		}

	}

	private boolean isGoldEnoughForTower(int towerType) {

		return gold >= helper_methods.Constants.Towers.GetTowerCost(towerType);
	}

	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bPause.setMouseOver(false);
		showTowerCost = false;
		sellTower.setMouseOver(false);
		upgradeTower.setMouseOver(false);

		for (MyButton b : towerButtons)
			b.setMouseOver(false); // default to false

		// menu, pause & tower buttons:
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if (bPause.getBounds().contains(x, y))
			bPause.setMouseOver(true);
		else {

			if (displayedTower != null) {
				if (sellTower.getBounds().contains(x, y)) {
					sellTower.setMouseOver(true);
					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
					upgradeTower.setMouseOver(true);
					return;
				}
			}

			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMouseOver(true);
					showTowerCost = true;
					towerCostType = b.getId();
					return;
				}
		}
	}

	public void mousePressed(int x, int y) { // same for mouse pressed
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bPause.getBounds().contains(x, y))
			bPause.setMousePressed(true);
		else {

			if (displayedTower != null) {
				if (sellTower.getBounds().contains(x, y)) {
					sellTower.setMousePressed(true);
					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
					upgradeTower.setMousePressed(true);
					return;
				}
			}

			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
		}

	}

	public void mouseReleased(int x, int y) { // reset setMousePressed() to false when released
		bMenu.resetBooleans();
		bPause.resetBooleans();
		for (MyButton b : towerButtons)
			b.resetBooleans();
		sellTower.resetBooleans();
		upgradeTower.resetBooleans();

	}

	public void payForTower(int towerType) { // effectively subtract gold when buying tower
		this.gold -= helper_methods.Constants.Towers.GetTowerCost(towerType);

	}

	public void addGold(int getReward) {
		this.gold += getReward;
	} // to effectively reward player when enemy is killed

	public int getLives() {
		return lives;
	}

}
