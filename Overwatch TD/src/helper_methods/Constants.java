package helper_methods;

public class Constants {

	public static class Projectiles { // projectile IDs
		public static final int ARROW = 0;
		public static final int ICE = 1;
		public static final int BOMB = 2;

		public static float GetSpeed(int type) {
			switch (type) {
			case ARROW:
				return 8f;
			case BOMB:
				return 4f;
			case ICE:
				return 6f;
			}
			return 0f;
		}
	}

	public static class Towers { // tower IDs
		public static final int BASTION = 0;
		public static final int HANZO = 1;
		public static final int MEI = 2;

		public static int GetTowerCost(int towerType) {
			switch (towerType) {
			case BASTION:
				return 65;
			case HANZO:
				return 35;
			case MEI:
				return 50;
			}
			return 0;
		}

		public static String GetName(int towerType) {
			switch (towerType) {
			case BASTION:
				return "Bastion";
			case HANZO:
				return "Hanzo";
			case MEI:
				return "Mei";
			}
			return "";
		}

		public static int GetStartDmg(int towerType) {
			switch (towerType) {
			case BASTION:
				return 15;
			case HANZO:
				return 5;
			case MEI:
				return 0;
			}

			return 0;
		}

		public static float GetDefaultRange(int towerType) {
			switch (towerType) {
			case BASTION:
				return 75;
			case HANZO:
				return 120;
			case MEI:
				return 100;
			}

			return 0;
		}

		public static float GetDefaultCooldown(int towerType) {
			switch (towerType) {
			case BASTION:
				return 120;
			case HANZO:
				return 35;
			case MEI:
				return 50;
			}

			return 0;
		}
	}

	public static class Direction { // direction IDs
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class Enemies { // enemy IDs

		public static final int BOT = 0;
		public static final int FLYBOT = 1;
		public static final int DARKBOT = 2;
		public static final int SPIDERBOT = 3;

		public static int GetReward(int enemyType) { // gold earned for enemy killed
			switch (enemyType) {
			case BOT:
				return 5;
			case FLYBOT:
				return 5;
			case DARKBOT:
				return 25;
			case SPIDERBOT:
				return 10;
			}
			return 0;
		}

		public static float GetSpeed(int enemyType) {
			switch (enemyType) {
			case BOT:
				return 0.5f;
			case FLYBOT:
				return 0.7f;
			case DARKBOT:
				return 0.45f;
			case SPIDERBOT:
				return 1f;
			}
			return 0;
		}

		public static int GetStartHealth(int enemyType) {
			switch (enemyType) {
			case BOT:
				return 85;
			case FLYBOT:
				return 100;
			case DARKBOT:
				return 400;
			case SPIDERBOT:
				return 125;
			}
			return 0;
		}
	}

	public static class Tiles { // tile IDs
		public static final int WATER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
	}

}
