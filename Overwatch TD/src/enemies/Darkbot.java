package enemies;

import static helper_methods.Constants.Enemies.DARKBOT;

import managers.EnemyManager;
//class for darkbots
public class Darkbot extends Enemy {

	public Darkbot(float x, float y, int ID, EnemyManager em) {
		super(x, y, ID, DARKBOT, em);
		
	}

}
