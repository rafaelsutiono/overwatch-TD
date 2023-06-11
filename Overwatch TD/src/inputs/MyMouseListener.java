package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Game;
import main.GameStates;

public class MyMouseListener implements MouseListener, MouseMotionListener {

	private Game game;

	public MyMouseListener(Game game) {
		this.game = game;
	}
	
	// all methods below are listeners that switch out with different game scenes. This includes listeners for when the mouse is:
	// dragged, moved, clicked, pressed, released
	// dragged -> user presses the mouse button and moves the mouse before releasing the button. Releasing the mouse button after a mouseDragged() will not result in a mouseClicked()
	// moved -> mouse moves without being clicked, pressed or dragged
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseDragged(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseDragged(e.getX(), e.getY());
			break;
		case EDIT:
			game.getEditor().mouseDragged(e.getX(), e.getY());
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseMoved(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseMoved(e.getX(), e.getY());
			break;
		case EDIT:
			game.getEditor().mouseMoved(e.getX(), e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mouseMoved(e.getX(), e.getY());
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (GameStates.gameState) {
			case MENU:
				game.getMenu().mouseClicked(e.getX(), e.getY());
				break;
			case PLAYING:
				game.getPlaying().mouseClicked(e.getX(), e.getY());
				break;
			case EDIT:
				game.getEditor().mouseClicked(e.getX(), e.getY());
				break;
			case GAME_OVER:
				game.getGameOver().mouseClicked(e.getX(), e.getY());
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mousePressed(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mousePressed(e.getX(), e.getY());
			break;
		case EDIT:
			game.getEditor().mousePressed(e.getX(), e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mousePressed(e.getX(), e.getY());
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseReleased(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseReleased(e.getX(), e.getY());
			break;
		case EDIT:
			game.getEditor().mouseReleased(e.getX(), e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mouseReleased(e.getX(), e.getY());
			break;

		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// hello

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// me useless
	}

}
