package objects;

import java.awt.image.BufferedImage;

public class Tile {
	// class for tiles in the game, both static & animated
	private BufferedImage[] sprite;
	private int id, tileType;

	public Tile(BufferedImage sprite, int id, int tileType) { // static sprites
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	public Tile(BufferedImage[] sprite, int id, int tileType) { // animated sprites (water only for now)
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;
	}

	public int getTileType() {
		return tileType;
	}

	public BufferedImage getSprite(int animationIndex) {
		return sprite[animationIndex];
	}

	public BufferedImage getSprite() {
		return sprite[0];
	}

	public boolean isAnimation() {
		return sprite.length > 1;
	}

	public int getId() {
		return id;
	}

}
