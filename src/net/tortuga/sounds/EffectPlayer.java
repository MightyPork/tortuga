package net.tortuga.sounds;


import com.porcupine.mutable.MFloat;


public class EffectPlayer {

	/** the track */
	private AudioX track;

	/** base gain for sfx */
	private double baseGain = 1;

	/** base pitch for sfx */
	private double basePitch = 1;

	/** dedicated volume control */
	private MFloat gainMultiplier = null;

	private float lastUpdateComputedGain = 0;


	public EffectPlayer(AudioX track, double basePitch, double baseGain, MFloat gainMultiplier) {
		this.track = track;

		this.baseGain = baseGain;
		this.basePitch = basePitch;

		this.gainMultiplier = gainMultiplier;
	}


	public int play(double pitch, double gain)
	{
		if (track == null) return -1;

		double computedGain = gainMultiplier.get() * gain * baseGain;
		double computedPitch = pitch * basePitch;

		return track.playEffect((float) computedPitch, (float) computedGain, false);

	}


	public int play(double gain)
	{
		if (track == null) return -1;
		return play(1, (float) gain);
	}

}
