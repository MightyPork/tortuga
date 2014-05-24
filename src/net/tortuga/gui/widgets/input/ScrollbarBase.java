package net.tortuga.gui.widgets.input;


import net.tortuga.gui.widgets.IScrollable;
import net.tortuga.gui.widgets.IScrollbar;
import net.tortuga.gui.widgets.Widget;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;
import com.porcupine.math.Calc;


/**
 * Clcikable button.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class ScrollbarBase extends Widget implements IScrollbar {

	public static final int DEFAULT_WIDTH = 14;

	protected int bdr = 2;

	protected double value = 0;

	protected IScrollable scrollable;


	/**
	 * Set scrollable element
	 * 
	 * @param scrollable scrollable element
	 * @return this
	 */
	public ScrollbarBase setScrollable(IScrollable scrollable)
	{
		this.scrollable = scrollable;
		scrollable.onScrollbarConnected(this);
		return this;
	}


	protected double getContentSize()
	{
		if (scrollable == null) return 100;
		return scrollable.getContentSize();
	}


	protected double getViewSize()
	{
		if (scrollable == null) return 30;
		return scrollable.getViewSize();
	}


	protected abstract double getTrackSize();


	protected double getHandleSize()
	{
		double hs = (getViewSize() / getContentSize()) * getTrackSize();
		if (hs < 41) hs = 41;
		if (hs > getTrackSize()) hs = getTrackSize();
		return hs;
	}


	protected abstract Rect getHandleLine();


	protected abstract Rect getRidgeLine();


	@Override
	public abstract void render(Coord mouse);

	protected boolean isDragging = false;
	protected Coord posDragStart;
	protected double valueDragStart = 0;


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (button != 0) return null;
		if (!isDragging && !isMouseOver(pos)) {
			return null;
		}
		if (getContentSize() <= getViewSize()) return null;
		if (down == true) {
			double size = getHandleSize();

			if (pos.distTo(getHandleLine().getCenter()) > size / 2) {
				Rect ridge = getRidgeLine();

				double abspos = getDistInDir1(ridge, pos);
				double rsize = ridge.getSize().size();

				double vlast = value;
				value = Calc.clampd(abspos / rsize, 0, 1);

				if (value != vlast && scrollable != null) {
					scrollable.onScrollbarChange(value);
				}
			}

			isDragging = true;
			posDragStart = pos;
			valueDragStart = value;
		} else {
			// up.
			isDragging = false;
		}
		return null;
	}


	protected abstract double getDistInDir1(Rect ridge, Coord to);


	protected abstract double getDistInDir2(Coord from, Coord to);


	@Override
	public void handleStaticInputs(Coord pos)
	{
		if (isDragging) {
			double vlast = value;
			value = valueDragStart + (1 / (getTrackSize() - getHandleSize())) * getDistInDir2(posDragStart, pos);
			value = Calc.clampd(value, 0, 1);
			if (value != vlast && scrollable != null) {
				scrollable.onScrollbarChange(value);
			}
		}
	}


	@Override
	public Widget onScroll(Coord pos, int scroll)
	{
		if (isMouseOver(pos)) {
			return onScrollDelegate(scroll);
		}
		return null;
	}


	@Override
	public Widget onKey(int key, char chr, boolean down)
	{
		return null;
	}


	@Override
	public void calcChildSizes()
	{
		rect.setTo(0, 0, minSize.x, minSize.y);
	}


	/**
	 * Handler fro scroll wheel
	 * 
	 * @param scroll scroll steps
	 * @return this
	 */
	@Override
	public Widget onScrollDelegate(int scroll)
	{
		if (getContentSize() <= getViewSize()) return null;
		double vlast = value;
		value = Calc.clampd(value - scroll * (getViewSize() / getContentSize()) * 0.2, 0, 1);
		if (value != vlast && scrollable != null) scrollable.onScrollbarChange(value);
		return this;
	}


	/**
	 * Get scrollbar value
	 * 
	 * @return value
	 */
	@Override
	public double getValue()
	{
		return value;
	}


	/**
	 * Set scrollbar value
	 * 
	 * @param value value
	 */
	@Override
	public void setValue(double value)
	{
		this.value = Calc.clampd(value, 0, 1);
	}

}
