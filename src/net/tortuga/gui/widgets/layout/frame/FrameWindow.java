package net.tortuga.gui.widgets.layout.frame;


import static org.lwjgl.opengl.GL11.*;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.textures.Textures;
import net.tortuga.textures.Tx;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Frame for widgets (with shadow and border + background)
 * 
 * @author MightyPork
 */
public class FrameWindow extends FrameBase {

	public boolean showScrews = true;

	public double shadowX = 10;
	public double shadowY = 10;

	public boolean showBorder = true;
	public boolean showShadow = true;

	private int[] screws = new int[4];


	public FrameWindow(Widget child) {
		this();
		add(child);
	}


	public FrameWindow(Widget child, int alignH, int alignV) {
		this();
		add(child);
		this.alignH = alignH;
		this.alignV = alignV;
	}


	public FrameWindow(int alignH, int alignV) {
		this();
		this.alignH = alignH;
		this.alignV = alignV;
	}


	public FrameWindow() {
		screws[0] = rand.nextInt(360);
		screws[1] = rand.nextInt(360);
		screws[2] = rand.nextInt(360);
		screws[3] = rand.nextInt(360);
	}


	public FrameWindow enableBorder(boolean flag)
	{
		showBorder = flag;
		if (!flag) borderWidth = 0;
		return this;
	}


	public FrameWindow enableShadow(boolean flag)
	{
		showShadow = flag;
		return this;
	}


	public FrameWindow enableScrews(boolean flag)
	{
		showScrews = flag;
		return this;
	}


	public FrameWindow setBorderSize(double width)
	{
		borderWidth = width;
		showBorder = true;
		return this;
	}


	public FrameWindow setShadowOffset(double shiftX, double shiftY)
	{
		shadowX = shiftX;
		shadowY = shiftY;
		showShadow = true;
		return this;
	}


	@Override
	public void renderFrame(Coord mouse)
	{
		// SHADOW
		if (showShadow) {
			RenderUtils.quadRect(rect.add(shadowX, -shadowY).grow(-1, -1), new RGB(0, 0.1));
			RenderUtils.quadRect(rect.add(shadowX, -shadowY).grow(0, 0), new RGB(0, 0.1));
			RenderUtils.quadRect(rect.add(shadowX, -shadowY).grow(1, 1), new RGB(0, 0.1));
		}

		// BACKGROUND
		RenderUtils.quadTextured(rect, rect, Textures.steel_big_lt);

		if (showBorder) {
			RenderUtils.quadBorder(rect, 4, new RGB(0, 0.2), null);
			RenderUtils.quadBorder(rect, 2, new RGB(0, 0.3), null);
		}

		// SCREWS
		if (showScrews) {
			int screw = 15;

			Rect screwRect = new Rect().grow_ip(8, 8);

			glPushMatrix();
			RenderUtils.translate(rect.getLeftBottom().add(screw, screw));
			glRotated(screws[0], 0, 0, 1);
			RenderUtils.quadTextured(screwRect, Tx.SCREW);
			glPopMatrix();

			glPushMatrix();
			RenderUtils.translate(rect.getLeftTop().add(screw, -screw));
			glRotated(screws[1], 0, 0, 1);
			RenderUtils.quadTextured(screwRect, Tx.SCREW);
			glPopMatrix();

			glPushMatrix();
			RenderUtils.translate(rect.getRightTop().sub(screw, screw));
			glRotated(screws[2], 0, 0, 1);
			RenderUtils.quadTextured(screwRect, Tx.SCREW);
			glPopMatrix();

			glPushMatrix();
			RenderUtils.translate(rect.getRightBottom().add(-screw, screw));
			glRotated(screws[3], 0, 0, 1);
			RenderUtils.quadTextured(screwRect, Tx.SCREW);
			glPopMatrix();
		}
	}
}
