package enemies;

import static helper_methods.Constants.Enemies.SPIDERBOT;

import managers.EnemyManager;
//class for spiderbots
public class Spiderbot extends Enemy {

	public Spiderbot(float x, float y, int ID, EnemyManager em) {
		super(x, y, ID, SPIDERBOT, em);
	}

}
