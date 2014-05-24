package net.tortuga.util;


import com.porcupine.math.Calc;


/**
 * Double which supports delta timing
 * 
 * @author MightyPork
 */
public class AnimDouble {

	/** target double */
	public double end = 0;

	/** last tick double */
	public double start = 0;

	/** how long the transition should last */
	public double time = 1;

	/** current anim time */
	public double animTime = 0;


	/**
	 * new DeltaDouble
	 * 
	 * @param d value
	 */
	public AnimDouble(double d) {
		this.start = this.end = d;
	}


	/**
	 * Get start double
	 * 
	 * @return number
	 */
	public double getValue()
	{
		return start;
	}


	/**
	 * Get how much of the animation is already finished
	 * 
	 * @return completion ratio
	 */
	public double getRatio()
	{
		if (time == 0) return 1;
		return animTime / time;
	}


	/**
	 * Set to a value (both last and future)
	 * 
	 * @param d
	 * @return this
	 */
	public AnimDouble setTo(double d)
	{
		start = end = d;
		animTime = 0;
		return this;
	}


	/**
	 * Copy other
	 * 
	 * @param d
	 */
	public void setTo(AnimDouble d)
	{
		this.start = d.start;
		this.end = d.end;
		this.time = d.time;
		this.animTime = d.animTime;
	}


	/**
	 * Increment animation on render
	 * 
	 * @param delta delta
	 */
	public void update(double delta)
	{
		animTime = Calc.clampd(animTime + delta, 0, time);
		if (isFinished()) {
			time = 0;
			animTime = 0;
			start = end;
		}
	}


	/**
	 * Get value at delta time<br>
	 * <b>render(delta) MUST BE CALLED FIRST!</b>
	 * 
	 * @return the value
	 */
	public double delta()
	{
		if (time == 0) return end;
		return Calc.interpolate(start, end, animTime / time);
	}


	/**
	 * Get if animation is finished
	 * 
	 * @return is finished
	 */
	public boolean isFinished()
	{
		return animTime >= time;
	}


	/**
	 * Add, start animating.
	 * 
	 * @param num num
	 * @param time animation time (secs)
	 */
	public void addValue(double num, double time)
	{
		start = end;
		end += num;
		this.time = time;
		animTime = 0;
	}


	/**
	 * Animate between two states in time
	 * 
	 * @param from intial state
	 * @param to target state
	 * @param time animation time (secs)
	 */
	public void anim(double from, double to, double time)
	{
		setTo(from);
		addValue(to - from, time);
	}


	/**
	 * Animate 0 to 1
	 * 
	 * @param time animation time (secs)
	 */
	public void animIn(double time)
	{
		setTo(0);
		addValue(1, time);
	}


	/**
	 * Animate 1 to 0
	 * 
	 * @param time animation time (secs)
	 */
	public void animOut(double time)
	{
		setTo(1);
		addValue(-1, time);
	}


	/**
	 * Make a copy
	 * 
	 * @return copy
	 */
	public AnimDouble copy()
	{
		return new AnimDouble(end);
	}


	@Override
	public String toString()
	{
		return end + "";
	}


	/**
	 * Set to zero and stop animation
	 * 
	 * @return this
	 */
	public AnimDouble clear()
	{
		start = end = 0;
		time = 1;
		animTime = 0;
		return this;
	}


	/**
	 * Stop animation, keep current value
	 */
	public void stopAnimation()
	{
		start = end = delta();
		animTime = 0;
		time = 1;
	}
}
