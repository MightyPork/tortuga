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
 * @author Ondřej Hruška (MightyPork)
 */
public class FrameTop extends FrameBase {

	public double shadowY = 5;

	public boolean showBorder = true;
	public boolean showShadow = true;


	public FrameTop() {
		setMarginsH(0, 0);
	}


	public FrameTop(Widget child) {
		this();
		add(child);
	}


	public FrameTop(Widget child, int alignH, int alignV) {
		this(child);
		this.alignH = alignH;
		this.alignV = alignV;
	}


	public FrameTop enableBorder(boolean flag)
	{
		showBorder = flag;
		if (!flag) borderWidth = 0;
		return this;
	}


	public FrameTop enableShadow(boolean flag)
	{
		showShadow = flag;
		return this;
	}


	public FrameTop setBorderSize(double width)
	{
		borderWidth = width;
		showBorder = true;
		return this;
	}


	public FrameTop setShadowOffset(double shiftY)
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
			Rect below = rect.getEdgeBottom().growDown_ip(shadowY);

			RenderUtils.quadGradV(below, new RGB(0, 0.3), RGB.TRANSPARENT);
		}

		// BACKGROUND
		RenderUtils.quadTextured(rect, rect, Textures.steel_big_scratched, new RGB(0xd29f8e));//0xb4745e

		if (showBorder) {
			Rect line = rect.getEdgeBottom();

			line.growUp_ip(2);
			RenderUtils.quadRect(line, new RGB(0, 0.2));

			line.growUp_ip(2);
			RenderUtils.quadRect(line, new RGB(0, 0.2));
		}
	}
}
