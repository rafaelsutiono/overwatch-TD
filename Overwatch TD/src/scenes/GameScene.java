package scenes;

import java.awt.image.BufferedImage;

import main.Game;

public class GameScene {

	protected Game game;
	protected int animationIndex; // keeps track of current animation frame index
	protected int ANIMATION_SPEED = 25;
	protected int tick;

	public GameScene(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	protected boolean isAnimation(int spriteID) {
		return game.getTileManager().isSpriteAnimation(spriteID); // return true if sprite associated with the ID is an animated sprite
	}

	protected void updateTick() {
		tick++;
		if (tick >= ANIMATION_SPEED) { // when tick reaches animation speed, reset tick to 0 and increment animationIndex. Goes back to 0 when it reaches an index of 4
			tick = 0;
			animationIndex++;
			if (animationIndex >= 4)
				animationIndex = 0;
		}
	}

	protected BufferedImage getSprite(int spriteID) {
		return game.getTileManager().getSprite(spriteID); // receives spriteID and returns the sprite image associated with said ID
	}

	protected BufferedImage getSprite(int spriteID, int animationIndex) {
		return game.getTileManager().getAniSprite(spriteID, animationIndex); // same thing but for animated sprites
	}

}
