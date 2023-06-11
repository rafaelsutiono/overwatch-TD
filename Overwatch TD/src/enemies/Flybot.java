package enemies;

import static helper_methods.Constants.Enemies.FLYBOT;

import managers.EnemyManager;
// class for flybots
public class Flybot extends Enemy {

	public Flybot(float x, float y, int ID, EnemyManager em) {
		super(x, y, ID, FLYBOT,em);
		
	}

}
