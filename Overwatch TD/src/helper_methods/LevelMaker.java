package helper_methods;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import objects.PathPoint;

public class LevelMaker {

	public static String homePath = System.getProperty("user.home");
	public static String saveFolder = "OWLvl"; // folder for the level
	public static String levelFile = "level.txt"; // level file
	public static String filePath = homePath + File.separator + saveFolder + File.separator + levelFile;
	private static File lvlFile = new File(filePath);

	public static void CreateFolder() { // creates the folder to store the level file if it doesn't exist yet
		File folder = new File(homePath + File.separator + saveFolder);
		if (!folder.exists())
			folder.mkdir();
	}

	public static BufferedImage getSpriteAtlas() { // return buffered img from spriteatlas
		BufferedImage img = null;
		InputStream is = LevelMaker.class.getClassLoader().getResourceAsStream("spriteatlas.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static void CreateLevel(int[] idArr) { // creates a new level file if it doesn't exist yet
		if (lvlFile.exists()) {
			System.out.println("File: " + lvlFile + " already exists!");
			return;
		} else {
			try {
				lvlFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// add start & end points to the new level.
			WriteToFile(idArr, new PathPoint(0, 0), new PathPoint(0, 0));
		}

	}

	private static void WriteToFile(int[] idArr, PathPoint start, PathPoint end) {
		try {
			PrintWriter pw = new PrintWriter(lvlFile);
			for (Integer i : idArr)
				pw.println(i); // to write each element on a new line
			// write x & y coordinates for start & end points:
			pw.println(start.getxCord());
			pw.println(start.getyCord());
			pw.println(end.getxCord());
			pw.println(end.getyCord());

			pw.close(); // close printwriter and flush any remaining data into file
		} catch (FileNotFoundException e) {
			e.printStackTrace(); // error msg
		}

	}

	public static void SaveLevel(int[][] idArr, PathPoint start, PathPoint end) { // save level data, check if file exists before saving
		if (lvlFile.exists()) {
			WriteToFile(Util.TwoDToArrayList(idArr), start, end);
		} else {
			System.out.println("File: " + lvlFile + " does not exists! ");
			return;
		}
	}

	private static ArrayList<Integer> ReadFromFile() { // read level data and return as arraylist
		ArrayList<Integer> list = new ArrayList<>();

		try {
			Scanner sc = new Scanner(lvlFile);

			while (sc.hasNextLine()) {
				list.add(Integer.parseInt(sc.nextLine()));
			}

			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static ArrayList<PathPoint> GetLevelPathPoints() { // read path point data and return as arraylist
		if (lvlFile.exists()) {
			ArrayList<Integer> list = ReadFromFile();
			ArrayList<PathPoint> points = new ArrayList<>();
			points.add(new PathPoint(list.get(400), list.get(401)));
			points.add(new PathPoint(list.get(402), list.get(403)));

			return points;

		} else {
			System.out.println("File: " + lvlFile + " does not exists! ");
			return null;
		}
	}

	public static int[][] GetLevelData() { // reads and converts level data into a 2d array
		if (lvlFile.exists()) {
			ArrayList<Integer> list = ReadFromFile();
			return Util.ArrayListTo2D(list, 20, 20);

		} else {
			System.out.println("File: " + lvlFile + " does not exist! ");
			return null;
		}

	}
}
