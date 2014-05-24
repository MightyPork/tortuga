package net.tortuga.gui.widgets;


/**
 * Scrollable element
 * 
 * @author MightyPork
 */
public interface IScrollable {

	/**
	 * Get height of the entire content
	 * 
	 * @return content height
	 */
	public double getContentSize();


	/**
	 * When scrollbar is connected
	 * 
	 * @param scrollbar scrollbar
	 */
	public void onScrollbarConnected(IScrollbar scrollbar);


	/**
	 * Get view height (height of the visible area)
	 * 
	 * @return view height
	 */
	public double getViewSize();


	/**
	 * Hook called when scrollbar value changes
	 * 
	 * @param value scrollbar value 0-1
	 */
	public void onScrollbarChange(double value);
}
