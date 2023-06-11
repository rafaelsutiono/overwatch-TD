package main;

import javax.swing.JFrame;

import helper_methods.LevelMaker;
import managers.TileManager;
import scenes.*;

public class Game extends JFrame implements Runnable {

	private GameScreen gameScreen;
	private Thread gameThread;

	private final double FPS_SET = 120.0; // FPS set to 120
	private final double UPS_SET = 60.0; // event updates set to 60/second

	// classes:
	private Render render;
	private Menu menu;
	private Playing playing;
	private Editing editing;
	private GameOver gameOver;

	private TileManager tileManager;

	public Game() {

		LevelMaker.CreateFolder();

		createDefaultLevel();
		initClasses();
		
		// create window:
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false); // so user cannot resize window
		setTitle("Overwatch TD");
		add(gameScreen); // JFrame.add(JPanel) -> adds panel to frame, like sticking a sticky note to a board
		pack(); // sets preferred window size for the user, set to 640x800
		setLocationRelativeTo(null); // window position null -> center
		setVisible(true);

	}

	private void createDefaultLevel() {
		int[] arr = new int[400];
		for (int i = 0; i < arr.length; i++)
			arr[i] = 0;

		LevelMaker.CreateLevel(arr);

	}

	private void initClasses() { // initiate classes
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		playing = new Playing(this);
		editing = new Editing(this);
		gameOver = new GameOver(this);

	}

	private void start() {
		gameThread = new Thread(this) {
		}; // initiates thread

		gameThread.start();
	}

	private void updateGame() {
		switch (GameStates.gameState) {
		case EDIT:
			editing.update();
			break;
		case MENU:
			break;
		case PLAYING:
			playing.update();
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) { // main arg

		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();
		
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET; // duration for each frame to be displayed
		// 1 billion nanoseconds = 1 second, calculation set to 120 FPS
		double timePerUpdate = 1000000000.0 / UPS_SET; // duration for each event update, set as 60 UPS

		long lastFrame = System.nanoTime(); // duration the last frame was displayed
		long lastUpdate = System.nanoTime(); // same but for update
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			// render:
			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now; // if current frame is being displayed longer or equal to the set time per frame, a new frame will be painted and the current frame's time is saved for the next frame to refer to
				frames++;
			}

			// update:
			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}

//			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
//				System.out.println("FPS: " + frames + " | UPS: " + updates);
//				frames = 0;
//				updates = 0;
//				lastTimeCheck = System.currentTimeMillis();
//			} // used to track FPS and UPS

		}

	}

	// getters n setters
	public Render getRender() {
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Editing getEditor() {
		return editing;
	}

	public GameOver getGameOver() {
		return gameOver;
	}

	public TileManager getTileManager() {
		return tileManager;
	}

}