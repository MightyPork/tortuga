package net.tortuga.gui.widgets.input;


import net.tortuga.gui.panels.PanelGui;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.textures.Tx;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;
import com.porcupine.math.Calc;


/**
 * Clcikable button.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Slider extends Widget {

	protected double value = 0;
	private int handleH = 24;
	private int paddingH = 0;
	private int ingapH = paddingH + handleH / 2 - 4;
	protected int trackH = 14;
	protected int fillH = 10;


	/**
	 * new scrollbar
	 * 
	 * @param width scrollbar width
	 * @param value slider value
	 */
	public Slider(int width, double value) {
		setMinSize(width, handleH + 4);
		setValue(value);
	}


	private double getGrooveWidth()
	{
		return getSize().x - paddingH * 2 - ingapH * 2;
	}


	protected Coord getHandlePos()
	{
		double GW = getGrooveWidth();
		return new Coord(rect.getMin().x + paddingH + ingapH + (GW) * value, rect.getCenter().y);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		Coord handle = getHandlePos();

		// rect center left and right
		Coord cL = rect.getCenterLeft();
		Coord cR = rect.getCenterRight();

		// prepare textured rects
		Rect track = new Rect(cL.sub(0, trackH / 2), cR.add(0, trackH / 2)).round();
		Rect fill = new Rect(cL.sub(-3, fillH / 2), handle.add(0, fillH / 2)).round();

		TxQuad trackUVs = Tx.SLIDER_TRACK;
		TxQuad fillUVs = Tx.SLIDER_FILL;
		TxQuad txHandle = Tx.SLIDER_HANDLE;

		RenderUtils.quadTexturedStretchH(track, trackUVs, 7);
		RenderUtils.quadTexturedStretchH(fill, fillUVs, 7, new RGB(0x00b19a));

		// draw handle
		Rect handleRect = new Rect(handle.sub(12, 12), handle.add(12, 12)).round();
		RenderUtils.quadTextured(handleRect, txHandle);
	}

	private boolean isDragging = false;
	private Coord posDragStart;
	private double valueDragStart = 0;


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (button != 0) return null;
		if (!isDragging && !isMouseOver(pos)) {
			return null;
		}

		if (down == true) {
			if (pos.distTo(getHandlePos()) > handleH / 2) {
				Coord cL = rect.getCenterLeft();
				Coord cR = rect.getCenterRight();
				Rect ridge = new Rect(cL.add(7, 0), cR.sub(7, 0));

				double abspos = pos.x - ridge.x1();
				double rsize = ridge.getSize().x;

				double vlast = value;
				value = Calc.clampd(abspos / rsize, 0, 1);
				if (value != vlast) {
					((PanelGui) getPanel()).actionPerformed(this);
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


	@Override
	public void handleStaticInputs(Coord pos)
	{
		if (isDragging) {
			double vlast = value;
			value = valueDragStart - (1 / getGrooveWidth()) * (posDragStart.x - pos.x);
			value = Calc.clampd(value, 0, 1);
			if (value != vlast) {
				((PanelGui) getPanel()).actionPerformed(this);
			}
		}
	}


	@Override
	public void onBlur()
	{}


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


	public Widget onScrollDelegate(int scroll)
	{
		double vlast = value;
		value = Calc.clampd(value + scroll * 0.1, 0, 1);
		if (value != vlast) ((PanelGui) getPanel()).actionPerformed(this);
		return this;
	}


	public double getValue()
	{
		return value;
	}


	public void setValue(double value)
	{
		this.value = Calc.clampd(value, 0, 1);
	}

}
