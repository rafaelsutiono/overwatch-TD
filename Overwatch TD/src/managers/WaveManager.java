package managers;

import java.util.ArrayList;
import java.util.Arrays;

import events.Wave;
import scenes.Playing;

public class WaveManager {

	private final Playing playing;
	private final ArrayList<Wave> waves = new ArrayList<>();
	private final int enemySpawnTickLimit = 60; // time between enemies spawning
	private int enemySpawnTick = enemySpawnTickLimit;
	private int enemyIndex, waveIndex;
	private final int waveTickLimit = 60 * 5; // time between waves
	private int waveTick = 0;
	private boolean waveStartTimer, waveTickTimerOver;

	public WaveManager(Playing playing) {
		this.playing = playing;
		createWaves();
	}

	public void update() { // tick limit checkers
		if (enemySpawnTick < enemySpawnTickLimit)
			enemySpawnTick++;

		if (waveStartTimer) {
			waveTick++;
			if (waveTick >= waveTickLimit) {
				waveTickTimerOver = true;
			}
		}

	}

	public void increaseWaveIndex() { // increments for every next wave
		waveIndex++;
		waveTick = 0;
		waveTickTimerOver = false;
		waveStartTimer = false;
	}

	public boolean isWaveTimerOver() {

		return waveTickTimerOver;
	}

	public void startWaveTimer() {
		waveStartTimer = true;
	}

	public int getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
	}

	private void createWaves() { // wave maker
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 1, 0, 2, 2, 1, 2, 1, 1, 0, 0, 1, 1, 2, 1, 2, 1, 0, 0, 0, 1, 2, 1, 2, 2, 1, 0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 1, 1, 1, 1, 2, 2, 3, 3, 1, 3, 3, 1, 1, 3, 3, 2, 1, 2, 2, 2, 1, 1, 2, 3, 3, 3, 2, 2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 3, 3, 2, 2, 2, 3, 3, 2, 3, 2, 3, 2, 3, 2, 3))));
	}

	// everything below is self-explanatory la ya
	public ArrayList<Wave> getWaves() {
		return waves;
	}
	public boolean isTimeForNewEnemy() {
		return enemySpawnTick >= enemySpawnTickLimit;
	}

	public boolean isThereMoreEnemiesInWave() {
		return enemyIndex < waves.get(waveIndex).getEnemyList().size();
	}

	public boolean isThereMoreWaves() {
		return waveIndex + 1 < waves.size();
	}

	public void resetEnemyIndex() {
		enemyIndex = 0;
	}

	public int getWaveIndex() {
		return waveIndex;
	}

	public float getTimeLeft() {
		float ticksLeft = waveTickLimit - waveTick;
		return ticksLeft / 60.0f;
	}

	public boolean isWaveTimerStarted() {
		return waveStartTimer;
	}

	public void reset() {
		waves.clear();
		createWaves();
		enemyIndex = 0;
		waveIndex = 0;
		waveStartTimer = false;
		waveTickTimerOver = false;
		waveTick = 0;
		enemySpawnTick = enemySpawnTickLimit;
	}

}
