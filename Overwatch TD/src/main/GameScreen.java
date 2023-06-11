package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

public class GameScreen extends JPanel {

	private Game game;
	private Dimension windowSize;

	private MyMouseListener myMouseListener;
	private KeyboardListener keyboardListener;

	public GameScreen(Game game) {
		this.game = game;

		setPanelSize();

	}

	public void initInputs() { // initiate input listeners
		myMouseListener = new MyMouseListener(game);
		keyboardListener = new KeyboardListener(game);

		addMouseListener(myMouseListener);
		addMouseMotionListener(myMouseListener);
		addKeyListener(keyboardListener);

		requestFocus(); // to have the focus of all inputs given
	}

	private void setPanelSize() { // set window size to 640x800
		windowSize = new Dimension(640, 800);
		setMinimumSize(windowSize);
		setPreferredSize(windowSize);
		setMaximumSize(windowSize);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // calls JPanel, handles graphic calculations n stuff that don't need to be messed with
		game.getRender().render(g);

	}

}