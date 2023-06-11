package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

public class GameOver extends GameScene implements SceneMethods {

	private MyButton bReplay, bMenu;

	public GameOver(Game game) {
		super(game);
		initButtons();
	}

	private void initButtons() { // create button objects

		// measurements:
		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 300;
		int yOffset = 100;

		// buttons:
		bMenu = new MyButton("Menu", x, y, w, h);
		bReplay = new MyButton("Replay", x, y + yOffset, w, h);

	}

	@Override
	public void render(Graphics g) {
		// game over text:
		g.setFont(new Font("Century Gothic", Font.PLAIN, 50));
		g.setColor(Color.BLACK);
		g.drawString("Game Over!", 170, 80);

		// buttons:
		g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		bMenu.draw(g);
		bReplay.draw(g);
	}

	private void replayGame() { // when replay is clicked...
		// ...reset everything...:
		resetAll();

		// ... and change state to playing:
		SetGameState(PLAYING);

	}

	private void resetAll() {
		game.getPlaying().resetEverything();
	} // method to reset all game-related components and states

	// everything below is self-explanatory la ya
	@Override
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
			resetAll();
		} else if (bReplay.getBounds().contains(x, y))
			replayGame();
	}

	@Override
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bReplay.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if (bReplay.getBounds().contains(x, y))
			bReplay.setMouseOver(true);
	}

	@Override
	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bReplay.getBounds().contains(x, y))
			bReplay.setMousePressed(true);

	}

	@Override
	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bReplay.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {
		// hi im about as useless as the g in lasagna

	}

}
