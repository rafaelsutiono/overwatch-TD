package helper_methods;

import static helper_methods.Constants.Direction.*;
import static helper_methods.Constants.Tiles.*;

import java.util.ArrayList;

import objects.PathPoint;

public class Util {

	public static int[][] GetRoadDirArr(int[][] lvlTypeArr, PathPoint start, PathPoint end) { // enemy pathfinder
		int[][] roadDirArr = new int[lvlTypeArr.length][lvlTypeArr[0].length]; // road at y,x

		PathPoint currTile = start; // main variable for pathfinding
		int lastDir = -1; // 0 left, 1 up, 2 right, 3 down

		while (!IsCurrSameAsEnd(currTile, end)) { // loops as long as currTile != end
			PathPoint prevTile = currTile; // when loop starts, prevtile will become the current tile
			currTile = GetNextRoadTile(prevTile, lastDir, lvlTypeArr); // to get the direction of the next tile
			lastDir = GetDirFromPrevToCurr(prevTile, currTile); // to figure out the direction from previous tile to the next tile
			roadDirArr[prevTile.getyCord()][prevTile.getxCord()] = lastDir; // put prev tile's coordinates in
		}
		roadDirArr[end.getyCord()][end.getxCord()] = lastDir; // executes when we get the endpoint
		return roadDirArr;
	}

	private static int GetDirFromPrevToCurr(PathPoint prevTile, PathPoint currTile) { // direction getter
		// up or down:
		if (prevTile.getxCord() == currTile.getxCord()) {
			if (prevTile.getyCord() > currTile.getyCord())
				return UP;
			else
				return DOWN;
		} else {
			// left or right:
			if (prevTile.getxCord() > currTile.getxCord())
				return LEFT;
			else
				return RIGHT;
		}

	}

	private static PathPoint GetNextRoadTile(PathPoint prevTile, int lastDir, int[][] lvlTypeArr) { // to find the next tile and try different directions until it finds a direction that has a road

		int testDir = lastDir;
		PathPoint testTile = GetTileInDir(prevTile, testDir, lastDir);

		while (!IsTileRoad(testTile, lvlTypeArr)) { // if it returns true, then a road tile has been found
			testDir++;
			testDir %= 4; // go down to 0 again if it reaches 4
			testTile = GetTileInDir(prevTile, testDir, lastDir);
		}

		return testTile;
	}

	private static boolean IsTileRoad(PathPoint testTile, int[][] lvlTypeArr) {
		if (testTile != null)
			// checks if it is within bounds:
			if (testTile.getyCord() >= 0)
				if (testTile.getyCord() < lvlTypeArr.length)
					if (testTile.getxCord() >= 0)
						if (testTile.getxCord() < lvlTypeArr[0].length)
							if (lvlTypeArr[testTile.getyCord()][testTile.getxCord()] == ROAD_TILE) // is it a road tile or no?
								return true;

		return false;
	}

	private static PathPoint GetTileInDir(PathPoint prevTile, int testDir, int lastDir) { // to make sure tiles are coming from correct directions

		switch (testDir) {
		case LEFT:
			if (lastDir != RIGHT)
				return new PathPoint(prevTile.getxCord() - 1, prevTile.getyCord());
		case UP:
			if (lastDir != DOWN)
				return new PathPoint(prevTile.getxCord(), prevTile.getyCord() - 1);
		case RIGHT:
			if (lastDir != LEFT)
				return new PathPoint(prevTile.getxCord() + 1, prevTile.getyCord());
		case DOWN:
			if (lastDir != UP)
				return new PathPoint(prevTile.getxCord(), prevTile.getyCord() + 1);
		}

		return null;
	}

	private static boolean IsCurrSameAsEnd(PathPoint currTile, PathPoint end) { // to check if current tile isn't the end point
		if (currTile.getxCord() == end.getxCord())
			if (currTile.getyCord() == end.getyCord())
				return true;
		return false;
	}

	public static int[][] ArrayListTo2D(ArrayList<Integer> list, int ySize, int xSize) { // takes in an array list (int type) with a set size (20x20) that converts it into a 2d array through the use of a nested loop
		int[][] newArr = new int[ySize][xSize];

		for (int j = 0; j < newArr.length; j++)
			for (int i = 0; i < newArr[j].length; i++) {
				int index = j * ySize + i;
				newArr[j][i] = list.get(index);
			}

		return newArr;

	}

	public static int[] TwoDToArrayList(int[][] twoArr) { // essentially the reverse of the method above
		int[] oneArr = new int[twoArr.length * twoArr[0].length];

		for (int j = 0; j < twoArr.length; j++)
			for (int i = 0; i < twoArr[j].length; i++) {
				int index = j * twoArr.length + i;
				oneArr[index] = twoArr[j][i];
			}

		return oneArr;
	}

	public static int GetHypoDistance(float x1, float y1, float x2, float y2) { // using pythagoras theorem to calculate when an enemy is in range

		float xDiff = Math.abs(x1 - x2);
		float yDiff = Math.abs(y1 - y2);

		return (int) Math.hypot(xDiff, yDiff);

	}

}
