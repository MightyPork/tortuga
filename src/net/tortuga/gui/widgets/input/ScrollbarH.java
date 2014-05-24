package net.tortuga.gui.widgets.input;


import net.tortuga.gui.widgets.IScrollable;
import net.tortuga.textures.Tx;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Horizontal scrollbar
 * 
 * @author MightyPork
 */
public class ScrollbarH extends ScrollbarBase {

	/**
	 * new scrollbar
	 * 
	 * @param width scrollbar width
	 * @param height scrollbar height
	 */
	public ScrollbarH(int width, int height) {
		setMinSize(width, height);
	}


	/**
	 * new scrollbar
	 * 
	 * @param width scrollbar width
	 */
	public ScrollbarH(int width) {
		setMinSize(width, DEFAULT_WIDTH);
	}


	/**
	 * new scrollbar
	 * 
	 * @param width widget width
	 * @param scrollable scrollable object
	 */
	public ScrollbarH(int width, IScrollable scrollable) {
		this(width, DEFAULT_WIDTH, scrollable);
	}


	/**
	 * new scrollbar
	 * 
	 * @param width scrollbar width
	 * @param height scrollbar height
	 * @param scrollable scrollable element
	 */
	public ScrollbarH(int width, int height, IScrollable scrollable) {
		setMinSize(width, height);
		setScrollable(scrollable);
	}


	@Override
	protected double getTrackSize()
	{
		return getSize().x - bdr * 2;
	}


	@Override
	protected Rect getHandleLine()
	{
		double track = getTrackSize();
		double handle = getHandleSize();

		double fromMin = rect.getMin().x + bdr + (track - handle) * value;

		Coord min = new Coord(fromMin, rect.getCenter().y);
		Coord max = min.add(getHandleSize(), 0);

		return new Rect(min, max).round();
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		int handleH = 19;
		int trackH = 8;

		Rect track = rect.getAxisH().grow_ip(0, trackH / 2).round();
		TxQuad txTrack = Tx.SCROLL_H_TRACK;

		Rect handle = getHandleLine().grow_ip(0, handleH / 2).round();
		TxQuad txHandle;

		Coord handleCenter = getHandleLine().getCenter().round();
		Rect decor = new Rect(handleCenter, handleCenter).grow_ip(25D / 2D, handleH / 2D).round();
		TxQuad txDecor;

		if (isMouseOver(mouse) || isDragging) {
			txHandle = Tx.SCROLL_H_HANDLE_HOVER;
			txDecor = Tx.SCROLL_H_DOTS_HOVER;
		} else {
			txHandle = Tx.SCROLL_H_HANDLE;
			txDecor = Tx.SCROLL_H_DOTS;
		}

		RGB dye = new RGB(RGB.WHITE, getViewSize() < getContentSize() ? 0.9 : 0.7);

		RenderUtils.quadTexturedStretchH(track, txTrack, 11, dye);

		if (getViewSize() < getContentSize()) {
			RenderUtils.quadTexturedStretchH(handle, txHandle, 6);
			RenderUtils.quadTextured(decor, txDecor);
		}
	}


	@Override
	protected double getDistInDir1(Rect ridge, Coord to)
	{
		return to.x - ridge.getMin().x;
	}


	@Override
	protected double getDistInDir2(Coord from, Coord to)
	{
		return to.x - from.x;
	}


	@Override
	protected Rect getRidgeLine()
	{
		return rect.getAxisH().grow_ip(-(2 + getHandleSize() / 2), 0);
	}
}
