package net.tortuga.gui.widgets.layout.frame;


import net.tortuga.gui.widgets.Widget;
import net.tortuga.textures.Textures;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Frame for widgets (with shadow and border + background)
 * 
 * @author MightyPork
 */
public class FrameBottom extends FrameBase {

	public double shadowY = 5;

	public boolean showBorder = true;
	public boolean showShadow = true;


	public FrameBottom() {
		setMarginsH(0, 0);
	}


	public FrameBottom(Widget child) {
		this();
		add(child);
	}


	public FrameBottom(Widget child, int alignH, int alignV) {
		this(child);
		this.alignH = alignH;
		this.alignV = alignV;
	}


	public FrameBottom enableBorder(boolean flag)
	{
		showBorder = flag;
		if (!flag) borderWidth = 0;
		return this;
	}


	public FrameBottom enableShadow(boolean flag)
	{
		showShadow = flag;
		return this;
	}


	public FrameBottom setBorderSize(double width)
	{
		borderWidth = width;
		showBorder = true;
		return this;
	}


	public FrameBottom setShadowOffset(double shiftY)
	{
		shadowY = shiftY;
		showShadow = true;
		return this;
	}


	@Override
	public void renderFrame(Coord mouse)
	{
		// SHADOW
		if (showShadow) {
			Rect below = rect.getEdgeTop().growUp_ip(shadowY);

			RenderUtils.quadGradV(below, RGB.TRANSPARENT, new RGB(0, 0.3));
		}

		// BACKGROUND
		RenderUtils.quadTextured(rect, rect, Textures.steel_big_scratched, new RGB(0xd29f8e));//0xb4745e

		if (showBorder) {
			Rect line = rect.getEdgeTop();

			line.growDown_ip(2);
			RenderUtils.quadRect(line, new RGB(0, 0.2));

			line.growDown_ip(2);
			RenderUtils.quadRect(line, new RGB(0, 0.2));
		}
	}
}
