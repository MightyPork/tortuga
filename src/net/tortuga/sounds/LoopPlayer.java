package net.tortuga.sounds;


import net.tortuga.util.AnimDouble;

import org.lwjgl.openal.AL10;

import com.porcupine.mutable.MFloat;


public class LoopPlayer {

	private int sourceID = -1;

	/** the track */
	private AudioX track;

	/** animator for fade in and fade out */
	private AnimDouble fadeAnim = new AnimDouble(0);

	/** max gain for track */
	private float fullGain = 1;

	/** dedicated volume control */
	private MFloat gainMultiplier = null;

	private float lastUpdateComputedGain = 0;

	/** flag that track is paused */
	private boolean paused = true;


	public LoopPlayer(AudioX track, float pitch, float fullGain, MFloat gainMultiplier) {
		this.track = track;

		this.fullGain = fullGain;
		fadeAnim.setTo(0);
		this.gainMultiplier = gainMultiplier;

		if (track != null) {
			sourceID = track.playAsEffectLoop(1, 0);
			track.pauseLoop();
		}

		paused = true;
	}


	public void pause()
	{
		if (track == null) return;
		if (paused) {
			//System.out.println("Can't pause, loop is already paused.");
			return;
		}

		track.pauseLoop();
		paused = true;
	}


	public boolean isPaused()
	{
		return paused;
	}


	public void resume()
	{
		if (track == null) return;
		if (!paused) {
			//System.out.println("Can't resume, loop is already playing.");
			return; // playing
		}

		sourceID = track.resumeLoop();
		paused = false;
	}


	public void update(double delta)
	{
		if (track == null) return;
		if (paused) {
			return;
		}
		fadeAnim.update(delta);

		float computedGain = (float) (gainMultiplier.get() * fullGain * fadeAnim.delta());
		if (!paused && computedGain != lastUpdateComputedGain) {
			AL10.alSourcef(sourceID, AL10.AL_GAIN, computedGain);
			lastUpdateComputedGain = computedGain;
		}

		if (computedGain == 0 && !paused) pause();
	}


//	public void setVolume(double volume) {
//		fadeAnim.setTo(volume);
//	}

	public void fadeIn(double secs)
	{
		if (track == null) return;
		resume();
		fadeAnim.stopAnimation();
		fadeAnim.addValue(1 - fadeAnim.delta(), secs * (1 - fadeAnim.delta()));
	}


	public void fadeOut(double secs)
	{
		if (track == null) return;
		fadeAnim.stopAnimation();
		fadeAnim.addValue(-fadeAnim.delta(), secs * (fadeAnim.delta()));
	}

}
