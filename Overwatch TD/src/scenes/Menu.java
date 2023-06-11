package scenes;

import java.awt.*;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {

	private MyButton bPlaying, bEdit, bQuit;

	public Menu(Game game) {
		super(game);
		initButtons();
	}

	private void initButtons() {
		// measurements:
		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150;
		int yOffset = 100;
		
		// buttons:
		bPlaying = new MyButton("Play", x, y, w, h);
		bEdit = new MyButton("Edit", x, y + yOffset, w, h);
		bQuit = new MyButton("Quit", x, y + yOffset * 2, w, h);

	}

	@Override
	public void render(Graphics g) {
		g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		g.setColor(Color.BLACK);
		g.drawString("Welcome to", 262, 60);

		g.setFont(new Font("Bauhaus 93", Font.PLAIN, 50));
		g.setColor(Color.DARK_GRAY);
		g.drawString("OverwatchTD", 170, 103);

		g.setFont(new Font("Bauhaus 93", Font.PLAIN, 51));
		g.setColor(Color.orange);
		g.drawString("OverwatchTD", 168, 100);
		drawButtons(g); // render buttons

		g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		g.setColor(Color.BLACK);
		g.drawString("made by Rafael Sutiono (L2CC)", 350, 785);
	}

	private void drawButtons(Graphics g) { // draw(g) to render the buttons
		g.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		bPlaying.draw(g);
		bEdit.draw(g);
		bQuit.draw(g);

	}

	@Override
	public void mouseClicked(int x, int y) { // when mouse is clicked (i.e. pressed then released) within button bounds, set scene according to button text

		if (bPlaying.getBounds().contains(x, y))
			SetGameState(PLAYING);
		else if (bEdit.getBounds().contains(x, y))
			SetGameState(EDIT);
		else if (bQuit.getBounds().contains(x, y))
			System.exit(0);
	}

	@Override
	public void mouseMoved(int x, int y) {
		bPlaying.setMouseOver(false);
		bEdit.setMouseOver(false);
		bQuit.setMouseOver(false); // by default, cursor is not within button bounds
		
		// when cursor is over button bounds, setMouseOver = true
		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMouseOver(true);
		else if (bEdit.getBounds().contains(x, y))
			bEdit.setMouseOver(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMouseOver(true);

	}

	@Override
	public void mousePressed(int x, int y) {
		
		// when mouse is pressed while cursor is over button bounds, setMousePressed = true
		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMousePressed(true);
		else if (bEdit.getBounds().contains(x, y))
			bEdit.setMousePressed(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMousePressed(true);

	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() { // booleans set to false when mouse is released
		bPlaying.resetBooleans();
		bEdit.resetBooleans();
		bQuit.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {
		// nothing here lol

	}

}