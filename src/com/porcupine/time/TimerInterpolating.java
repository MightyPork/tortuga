package com.porcupine.time;


/**
 * Timer for interpolated timing
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TimerInterpolating {

	private long lastFrame = 0;
	private long nextFrame = 0;
	private long skipped = 0;
	private long lastSkipped = 0;

	private static final long SECOND = 1000000000; // a million nanoseconds
	private long FRAME; // a time of one frame in nanoseconds


	/**
	 * New interpolated timer
	 * 
	 * @param fps target FPS
	 */
	public TimerInterpolating(long fps) {
		FRAME = Math.round(SECOND / fps);

		lastFrame = System.nanoTime();
		nextFrame = System.nanoTime() + FRAME;
	}


	/**
	 * Sync and calculate dropped frames etc.
	 */
	public void sync()
	{
		long time = getTime();
		if (time >= nextFrame) {
			long skippedNow = (long) Math.floor((time - nextFrame) / (double) FRAME) + 1;
			//System.out.println("Skipping: "+skippedNow);
			skipped += skippedNow;
			lastFrame = nextFrame + (1 - skippedNow) * FRAME;
			nextFrame += skippedNow * FRAME;
		}
	}


	/**
	 * Get nanotime
	 * 
	 * @return nanotime
	 */
	public long getTime()
	{
		return System.nanoTime();
	}


	/**
	 * Get fraction of next frame
	 * 
	 * @return fraction
	 */
	public double getFraction()
	{
		if (getSkipped() >= 1) {
			return 1;
		}

		long time = getTime();

		if (time <= nextFrame) {
			return (double) (time - lastFrame) / (double) FRAME;
		}

		return 1;
	}


	/**
	 * Get number of elapsed ticks
	 * 
	 * @return ticks
	 */
	public int getSkipped()
	{
		long change = skipped - lastSkipped;
		lastSkipped = skipped;
		return (int) change;
	}


	/**
	 * Clear timer and start counting new tick.
	 */
	public void startNewFrame()
	{
		//System.out.println("! start new frame !");
		long time = getTime();
		lastFrame = time;
		nextFrame = time + FRAME;
		lastSkipped = skipped;
	}
}
