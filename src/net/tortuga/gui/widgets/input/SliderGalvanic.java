package net.tortuga.gui.widgets.input;


import net.tortuga.textures.Tx;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Clcikable button.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class SliderGalvanic extends Slider {

	/**
	 * new scrollbar
	 * 
	 * @param width scrollbar width
	 * @param value slider value
	 */
	public SliderGalvanic(int width, double value) {
		super(width, (1 + value) / 2D);
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
		Rect fill = new Rect(rect.getCenter().sub(-3, fillH / 2), handle.add(0, fillH / 2)).round();

		TxQuad trackUVs = Tx.SLIDER_TRACK;
		TxQuad fillUVs = Tx.SLIDER_FILL2;
		TxQuad txHandle = Tx.SLIDER_HANDLE;

		RenderUtils.quadTexturedStretchH(track, trackUVs, 7);
		RenderUtils.quadTexturedStretchH(fill, fillUVs, 7, new RGB(0x00b19a));

		// draw handle
		Rect handleRect = new Rect(handle.sub(12, 12), handle.add(12, 12)).round();
		RenderUtils.quadTextured(handleRect, txHandle);
	}


	@Override
	public void setValue(double value)
	{
		super.setValue((1 + value) / 2D);
	}


	@Override
	public double getValue()
	{
		return (-0.5D + super.getValue()) * 2;
	}
}
