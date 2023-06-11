package enemies;

import static helper_methods.Constants.Enemies.BOT;

import managers.EnemyManager;
//class for bots
public class Bot extends Enemy {

	public Bot(float x, float y, int ID, EnemyManager em) {
		super(x, y, ID, BOT,em);
	}

}
