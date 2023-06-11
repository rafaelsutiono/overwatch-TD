package scenes;

import static helper_methods.Constants.Tiles.ROAD_TILE;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import helper_methods.LevelMaker;
import main.Game;
import objects.PathPoint;
import objects.Tile;
import ui.Toolbar;

public class Editing extends GameScene implements SceneMethods {

	private int[][] lvl;
	private Tile selectedTile;
	private int mouseX, mouseY;
	private int lastTileX, lastTileY, lastTileId;
	private boolean drawSelect;
	private Toolbar toolbar;
	private PathPoint start, end;

	public Editing(Game game) { // construct the editing class and toolbar
		super(game);
		loadDefaultLevel();
		toolbar = new Toolbar(0, 640, 640, 160, this);
	}

	private void loadDefaultLevel() { // method to load the editor level
		lvl = LevelMaker.GetLevelData();
		ArrayList<PathPoint> points = LevelMaker.GetLevelPathPoints();

		// get start & end points
		start = points.get(0);
		end = points.get(1);
	}

	public void update() {
		updateTick();
	} // update game event

	@Override
	public void render(Graphics g) { // renderer

		drawLevel(g);
		toolbar.draw(g);
		drawSelectedTile(g);
		drawPathPoints(g);

	}

	private void drawPathPoints(Graphics g) { // if start & end points are already there, draw them on the level screen
		if (start != null)
			g.drawImage(toolbar.getStartPathImg(), start.getxCord() * 32, start.getyCord() * 32, 32, 32, null);

		if (end != null)
			g.drawImage(toolbar.getEndPathImg(), end.getxCord() * 32, end.getyCord() * 32, 32, 32, null);

	}

	private void drawLevel(Graphics g) { // method to draw the tiles of the level on the screen using images obtained from getSprite()
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

	private void drawSelectedTile(Graphics g) { // method to draw a selected tile on the screen based on current mouse pos
		if (selectedTile != null && drawSelect) {
			g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
		}
	}

	public void saveLevel() { // save level and set the level as the saved level for when it is played

		LevelMaker.SaveLevel(lvl, start, end);
		game.getPlaying().setLevel(lvl);

	}

	public void setSelectedTile(Tile tile) { // set the selected tile to the given tile and enables drawing the selected tile
		this.selectedTile = tile;
		drawSelect = true;
	}

	private void changeTile(int x, int y) {
		if (selectedTile != null) { // if not null, tile is already selected

			// calculate tile coordinates:
			int tileX = x / 32;
			int tileY = y / 32;

			if (selectedTile.getId() >= 0) { // checks if selected tile has a non-negative ID. If ID is non-negative, the selected tile is not a special tile such as the start/end points
				if (lastTileX == tileX && lastTileY == tileY && lastTileId == selectedTile.getId())
					return; //  checks if current tile coordinates and selected tile ID are the same as previous tile coordinates and ID. Both are equal -> tile has not changed -> no need to update it

				lastTileX = tileX;
				lastTileY = tileY;
				lastTileId = selectedTile.getId(); // update previous tile coordinates and ID with current tile coordinates and ID

				lvl[tileY][tileX] = selectedTile.getId(); // update tile ID in 2D array with specified coordinates, effectively changing the tile at a specific position to the selected tile
			} else {
				int id = lvl[tileY][tileX];
				if (game.getTileManager().getTile(id).getTileType() == ROAD_TILE /* checks if said tile corresponds to a road tile */) {
					if (selectedTile.getId() == -1) // ID == -1 -> start tile
						start = new PathPoint(tileX, tileY);
					else // ID == 0 -> end tile
						end = new PathPoint(tileX, tileY);
				}
			}
		}
	}

	@Override
	public void mouseClicked(int x, int y) { // calls changeTile() if mouse pos is over the toolbar
		if (y >= 640) {
			toolbar.mouseClicked(x, y);
		} else {
			changeTile(mouseX, mouseY);
		}

	}

	@Override
	public void mouseMoved(int x, int y) { // enable drawing tiles and calculate new mouse pos if mouse pos is over the toolbar

		if (y >= 640) {
			toolbar.mouseMoved(x, y);
			drawSelect = false;
		} else {
			drawSelect = true;
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}

	}

	@Override
	public void mousePressed(int x, int y) { // if press occurs on toolbar, delegates event to toolbar. Same thing pretty much for the rest
		if (y >= 640)
			toolbar.mousePressed(x, y);

	}

	@Override
	public void mouseReleased(int x, int y) {
		toolbar.mouseReleased(x, y);

	}

	@Override
	public void mouseDragged(int x, int y) {
		if (y >= 640) {

		} else {
			changeTile(x, y);
		}

	}

	public void keyPressed(KeyEvent e) { // for rotation of tiles: if R is pressed, call rotateSprite()
		if (e.getKeyCode() == KeyEvent.VK_R)
			toolbar.rotateSprite();
	}

}
