package net.tortuga.gui.widgets.input;


import net.tortuga.gui.widgets.IScrollable;
import net.tortuga.textures.Tx;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Clcikable button.
 * 
 * @author MightyPork
 */
public class ScrollbarV extends ScrollbarBase {

	/**
	 * new scrollbar
	 * 
	 * @param width scrollbar width
	 * @param height scrollbar height
	 */
	public ScrollbarV(int width, int height) {
		setMinSize(width, height);
	}


	/**
	 * new scrollbar
	 * 
	 * @param height scrollbar height
	 */
	public ScrollbarV(int height) {
		setMinSize(DEFAULT_WIDTH, height);
	}


	/**
	 * new scrollbar
	 * 
	 * @param height widget height
	 * @param scrollable scrollable object
	 */
	public ScrollbarV(int height, IScrollable scrollable) {
		this(DEFAULT_WIDTH, height, scrollable);
	}


	/**
	 * new scrollbar
	 * 
	 * @param width scrollbar width
	 * @param height scrollbar height
	 * @param scrollable scrollable element
	 */
	public ScrollbarV(int width, int height, IScrollable scrollable) {
		setMinSize(width, height);
		setScrollable(scrollable);
	}


	@Override
	protected double getTrackSize()
	{
		return getSize().y - bdr * 2;
	}


	@Override
	protected Rect getHandleLine()
	{
		double track = getTrackSize();
		double handle = getHandleSize();

		double fromMin = rect.getMin().y + bdr + track - (track - handle) * value - handle;

		Coord min = new Coord(rect.getCenter().x, fromMin);
		Coord max = min.add(0, getHandleSize());

		return new Rect(min, max).round();
	}


	@Override
	protected Rect getRidgeLine()
	{
		return rect.getAxisV().grow_ip(0, -(2 + getHandleSize() / 2));
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		int handleW = 19;
		int trackW = 8;

		Rect track = rect.getAxisV().round().grow_ip(trackW / 2, 0);
		TxQuad txTrack = Tx.SCROLL_V_TRACK;
		Rect handle = getHandleLine().grow_ip(handleW / 2, 0).round();
		TxQuad txHandle;

		Coord handleCenter = getHandleLine().getCenter().round();
		Rect decor = new Rect(handleCenter, handleCenter).grow_ip(handleW / 2D, 25 / 2D).round();
		TxQuad txDecor;

		if (isMouseOver(mouse) || isDragging) {
			txHandle = Tx.SCROLL_V_HANDLE_HOVER;
			txDecor = Tx.SCROLL_V_DOTS_HOVER;
		} else {
			txHandle = Tx.SCROLL_V_HANDLE;
			txDecor = Tx.SCROLL_V_DOTS;
		}

		RGB dye = new RGB(RGB.WHITE, getViewSize() < getContentSize() ? 0.9 : 0.7);

		RenderUtils.quadTexturedStretchV(track, txTrack, 11, dye);

		if (getViewSize() < getContentSize()) {
			RenderUtils.quadTexturedStretchV(handle, txHandle, 6);
			RenderUtils.quadTextured(decor, txDecor);
		}
	}


	@Override
	protected double getDistInDir1(Rect ridge, Coord to)
	{
		return ridge.getMax().y - to.y;
	}


	@Override
	protected double getDistInDir2(Coord from, Coord to)
	{
		return from.y - to.y;
	}
}
