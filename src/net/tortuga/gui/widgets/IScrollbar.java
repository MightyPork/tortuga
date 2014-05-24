package net.tortuga.gui.widgets;


/**
 * Scrollable element
 * 
 * @author MightyPork
 */
public interface IScrollbar {

	/**
	 * Set scrollbar value
	 * 
	 * @param value value 0-1
	 */
	public void setValue(double value);


	/**
	 * Get scrollbar value
	 * 
	 * @return value 0-1
	 */
	public double getValue();


	/**
	 * Perform scrolling from wheel on another widget
	 * 
	 * @param scrollscroll wheel steps
	 * @return this (widget)
	 */
	public Widget onScrollDelegate(int scroll);

}
