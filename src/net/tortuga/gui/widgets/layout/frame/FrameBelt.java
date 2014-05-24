package net.tortuga.gui.widgets.layout.frame;


import net.tortuga.gui.widgets.Widget;
import net.tortuga.textures.Textures;
import net.tortuga.util.RenderUtils;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Frame for widgets (with shadow and border + background)
 * 
 * @author MightyPork
 */
public class FrameBelt extends FrameBase {

	public FrameBelt() {
		setMarginsH(0, 0);
		setPaddingV(36 + 5, 36 + 5);
		setMarginsV(0, 0);
	}


	public FrameBelt(Widget child) {
		this();
		add(child);
	}


	public FrameBelt(Widget child, int alignH, int alignV) {
		this(child);
		this.alignH = alignH;
		this.alignV = alignV;
	}


	@Override
	public void renderFrame(Coord mouse)
	{
		// BACKGROUND
		Rect r = rect.grow(0, -36);
		RenderUtils.quadTextured(r, new Rect(0, 0, r.getSize().x, r.getSize().y), Textures.steel_brushed);

		Rect belt1 = rect.getEdgeTop().growDown_ip(50).add_ip(0, 11);
		Rect belt1tx = new Rect(0, 0, belt1.getSize().x, 50);
		RenderUtils.quadTextured(belt1, belt1tx, Textures.steel_rivet_belts);

		Rect belt2 = rect.getEdgeBottom().growUp_ip(50).sub_ip(0, 11);
		Rect belt2t = new Rect(0, 256 - 50, (int) rect.getSize().x, 256);
		RenderUtils.quadTextured(belt2, belt2t, Textures.steel_rivet_belts);
	}
}
