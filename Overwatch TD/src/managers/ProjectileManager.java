package managers;

import static helper_methods.Constants.Projectiles.*;
import static helper_methods.Constants.Towers.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helper_methods.LevelMaker;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

public class ProjectileManager {

	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private BufferedImage[] proj_imgs, explo_imgs;
	private int proj_id = 0;

	public ProjectileManager(Playing playing) {
		this.playing = playing;
		importImgs();

	}

	private void importImgs() {
		BufferedImage atlas = LevelMaker.getSpriteAtlas();
		proj_imgs = new BufferedImage[3];

		for (int i = 0; i < 3; i++)
			proj_imgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
		importExplosion(atlas);
	}

	private void importExplosion(BufferedImage atlas) {
		explo_imgs = new BufferedImage[7];

		for (int i = 0; i < 7; i++)
			explo_imgs[i] = atlas.getSubimage(i * 32, 32 * 2, 32, 32);

	}

	public void newProjectile(Tower t, Enemy e) {
		int type = getProjType(t);

		int xDist = (int) (t.getX() - e.getX()); // x distance = tower X - enemy X
		int yDist = (int) (t.getY() - e.getY()); // y distance = tower Y - enemy Y
		int totDist = Math.abs(xDist) + Math.abs(yDist);

		// formula to calculate x & y speed using xPer, which is the x-distance over the actual distance
		float xPer = (float) Math.abs(xDist) / totDist;

		float xSpeed = xPer * helper_methods.Constants.Projectiles.GetSpeed(type);
		float ySpeed = helper_methods.Constants.Projectiles.GetSpeed(type) - xSpeed;

		if (t.getX() > e.getX())
			xSpeed *= -1; // x gets translated to the left instead of the right if tower's X is above the enemy X
		if (t.getY() > e.getY())
			ySpeed *= -1; // y gets translated downwards instead of upwards if tower's Y is above the enemy Y

		float rotate = 0;

		if (type == ARROW) {
			float arcValue = (float) Math.atan(yDist / (float) xDist); // formula to calculate angle of rotation using arc-tan value
			rotate = (float) Math.toDegrees(arcValue); // rotate according to arc-tan value converted to deg

			if (xDist < 0) // negative x-distance value -> + 180 deg
				rotate += 180;
		}

		for (Projectile p : projectiles)
			if (!p.isActive())
				if (p.getProjectileType() == type) {
					p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate);
					return;
				}

		projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type));

	}

	public void update() { // checker: projectile hitting enemies
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.move();
				if (isProjHittingEnemy(p)) {
					p.setActive(false);
					if (p.getProjectileType() == BOMB) {
						explosions.add(new Explosion(p.getPos()));
						explodeOnEnemies(p); // AOE dmg for bombs
					}
				} else if (isProjOutsideBounds(p)) { // make projectile inactive
					p.setActive(false);
				}
			}

		for (Explosion e : explosions)
			if (e.getIndex() < 7)
				e.update();

	}

	private void explodeOnEnemies(Projectile p) { // bomb AOE
		for (Enemy e : playing.getEnemyManger().getEnemies()) {
			if (e.isAlive()) {
				float radius = 40.0f;

				float xDist = Math.abs(p.getPos().x - e.getX());
				float yDist = Math.abs(p.getPos().y - e.getY());

				float realDist = (float) Math.hypot(xDist, yDist);

				if (realDist <= radius) // enemies within the radius will take AOE dmg
					e.hurt(p.getDmg());
			}

		}

	}

	private boolean isProjHittingEnemy(Projectile p) { // check if a projectile hits an enemy
		for (Enemy e : playing.getEnemyManger().getEnemies()) {
			if (e.isAlive())
				if (e.getBounds().contains(p.getPos())) {
					e.hurt(p.getDmg());
					if (p.getProjectileType() == ICE) // mei applies slow
						e.slow();

					return true;
				}
		}
		return false;
	}

	private boolean isProjOutsideBounds(Projectile p) { // check if a projectile is outside bounds
		if (p.getPos().x >= 0)
			if (p.getPos().x <= 640)
				if (p.getPos().y >= 0)
					if (p.getPos().y <= 800)
						return false;
		return true;
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		for (Projectile p : projectiles)
			if (p.isActive()) {
				if (p.getProjectileType() == ARROW) {
					g2d.translate(p.getPos().x, p.getPos().y); // pinpoint the center of the arrow
					g2d.rotate(Math.toRadians(p.getRotation())); // rotate img
					g2d.drawImage(proj_imgs[p.getProjectileType()], -16, -16, null); // draw the image; -16, -16 so it is rotated on its center
					g2d.rotate(-Math.toRadians(p.getRotation()));
					g2d.translate(-p.getPos().x, -p.getPos().y); // backtrace to get back to default values
				} else {
					g2d.drawImage(proj_imgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
				}
			}

		drawExplosions(g2d);

	}

	private void drawExplosions(Graphics2D g2d) { // draw different explosion stages based on index
		for (Explosion e : explosions)
			if (e.getIndex() < 7)
				g2d.drawImage(explo_imgs[e.getIndex()], (int) e.getPos().x - 16, (int) e.getPos().y - 16, null);
	}

	private int getProjType(Tower t) { // get projectile type based on tower
		switch (t.getTowerType()) {
		case HANZO:
			return ARROW;
		case BASTION:
			return BOMB;
		case MEI:
			return ICE;
		}
		return 0;
	}

	public class Explosion {

		private Point2D.Float pos;
		private int exploTick, exploIndex;

		public Explosion(Point2D.Float pos) {
			this.pos = pos;
		}

		public void update() { // explosion index updater
			exploTick++;
			if (exploTick >= 6) {
				exploTick = 0;
				exploIndex++;
			}
		}

		public int getIndex() {
			return exploIndex;
		}

		public Point2D.Float getPos() {
			return pos;
		}
	}

	public void reset() {
		projectiles.clear();
		explosions.clear();

		proj_id = 0;
	}

}
