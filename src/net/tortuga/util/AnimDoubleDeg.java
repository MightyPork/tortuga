package net.tortuga.util;


import com.porcupine.math.Calc;


/**
 * Double which supports delta timing
 * 
 * @author MightyPork
 */
public class AnimDoubleDeg extends AnimDouble {

	/**
	 * new AnimDoubleDeg
	 * 
	 * @param d value
	 */
	public AnimDoubleDeg(double d) {
		super(d);
	}


	/**
	 * Get value at delta time<br>
	 * <b>render(delta) MUST BE CALLED FIRST!</b>
	 * 
	 * @return the value
	 */
	@Override
	public double delta()
	{
		return Calc.interpolateDeg(start, end, animTime / time);
	}
}
