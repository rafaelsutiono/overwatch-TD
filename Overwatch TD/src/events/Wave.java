package events;

import java.util.ArrayList;

public class Wave {
	private ArrayList<Integer> enemyList;

	public Wave(ArrayList<Integer> enemyList) {
		this.enemyList = enemyList;
	} // enemy list for a single wave

	public ArrayList<Integer> getEnemyList() {
		return enemyList;
	} // getter

}
