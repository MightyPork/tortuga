package net.tortuga.gui.widgets;


/**
 * Set of 4 numbers for sides (left, right, top, bottom)
 * 
 * @author MightyPork
 */
public class LeftTopRightBottom {

	/** left value */
	public int left;
	/** top value */
	public int top;
	/** right value */
	public int right;
	/** bottom value */
	public int bottom;


	/**
	 * new set of numbers
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public LeftTopRightBottom(int left, int top, int right, int bottom) {
		setTo(left, top, right, bottom);
	}


	/**
	 * new set of numbers
	 * 
	 * @param other
	 */
	public LeftTopRightBottom(LeftTopRightBottom other) {
		setTo(other);
	}


	/**
	 * set numbers to....
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setTo(int left, int top, int right, int bottom)
	{
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}


	/**
	 * set numbers to....
	 * 
	 * @param other other sidenums obj
	 */
	public void setTo(LeftTopRightBottom other)
	{
		this.left = other.left;
		this.top = other.top;
		this.right = other.right;
		this.bottom = other.bottom;
	}


	/**
	 * get copy multiplied by some number.
	 * 
	 * @param mul multiplier
	 * @return copy multiplied
	 */
	public LeftTopRightBottom mul(int mul)
	{
		return new LeftTopRightBottom(left * mul, top * mul, right * mul, bottom * mul);
	}


	/**
	 * Get a copy
	 * 
	 * @return copy
	 */
	public LeftTopRightBottom copy()
	{
		return new LeftTopRightBottom(this);
	}


	/**
	 * Get sum of horizontal numbers
	 * 
	 * @return left + right
	 */
	public int getHorizontal()
	{
		return left + right;
	}


	/**
	 * Get sum of vertical numbers
	 * 
	 * @return top + bottom
	 */
	public int getVetical()
	{
		return top + bottom;
	}
}
