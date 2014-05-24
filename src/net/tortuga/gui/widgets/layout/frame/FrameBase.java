package net.tortuga.gui.widgets.layout.frame;


import static org.lwjgl.opengl.GL11.*;
import net.tortuga.gui.widgets.GuiRoot;
import net.tortuga.gui.widgets.IWidget;
import net.tortuga.gui.widgets.LeftTopRightBottom;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.layout.Gap;
import net.tortuga.util.Align;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Vec;


/**
 * Frame for widgets (with shadow and border + background)
 * 
 * @author MightyPork
 */
public abstract class FrameBase extends Widget {

	/** Horizontal align */
	public int alignH = Align.CENTER;
	/** Vertical align */
	public int alignV = Align.CENTER;

	/** Border width */
	public double borderWidth = 3;

	/** Frame child */
	public Widget child = new Gap(0, 0);

	/** Padding */
	public LeftTopRightBottom padding = new LeftTopRightBottom(5, 5, 5, 5);


	/**
	 * Set the primary and only child.
	 */
	@Override
	public final void add(Widget child)
	{
		this.child = child;
	}


	@Override
	public final void calcChildSizes()
	{
		child.setGuiRoot(guiRoot);

		child.calcChildSizes();

		//@formatter:off
		setMinSize( (int)(Math.max(minSize.x, child.getSize().x + padding.getHorizontal() + borderWidth * 2)),
					(int)(Math.max(minSize.y, child.getSize().y + padding.getVetical() + borderWidth * 2)));
		//@formatter:on

		rect.setTo(0, 0, minSize.x, minSize.y);

		//Coord lbPos = rect.getCenter();
		Coord childMove = new Coord();
		Coord sizeC = child.getSize();

		double bdrL = padding.left + borderWidth;
		double bdrT = padding.top + borderWidth;
		double bdrR = padding.right + borderWidth;
		double bdrB = padding.bottom + borderWidth;

		switch (alignH) {
			case Align.LEFT:
				childMove.x = bdrL;
				break;
			case Align.RIGHT:
				childMove.x = rect.getSize().x - bdrR - sizeC.x;
				break;
			case Align.CENTER:
				childMove.x = rect.getSize().x / 2 - sizeC.x / 2;
				break;
		}

		switch (alignV) {
			case Align.TOP:
				childMove.y = rect.getSize().y - bdrT - sizeC.y;
				break;
			case Align.BOTTOM:
				childMove.y = bdrB;
				break;
			case Align.CENTER:
				childMove.y = rect.getSize().y / 2 - sizeC.y / 2;
				break;
		}

		child.rect.add_ip(new Vec(childMove));
	}


	@Override
	public final Widget onKey(int key, char chr, boolean down)
	{
		if (!isVisible() || !isEnabled()) return null;

		if (!child.isEnabled() || !child.isVisible()) return null;
		return child.onKey(key, chr, down);
	}


	@Override
	public final Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (!isVisible() || !isEnabled()) return null;

		Coord pos_r = pos.sub(rect.getMin());

		if (!child.isEnabled() || !child.isVisible()) return null;
		return child.onMouseButton(pos_r, button, down);
	}


	@Override
	public final Widget onScroll(Coord pos, int scroll)
	{
		if (!isVisible() || !isEnabled()) return null;

		Coord pos_r = pos.sub(rect.getMin());
		if (!child.isEnabled() || !child.isVisible()) return null;
		return child.onScroll(pos_r, scroll);
	}


	@Override
	public final void handleStaticInputs(Coord pos)
	{
		if (!isVisible() || !isEnabled()) return;

		Coord pos_r = pos.sub(rect.getMin());

		if (!child.isEnabled() || !child.isVisible()) return;
		child.handleStaticInputs(pos_r);
	}


	@Override
	public final void render(Coord mouse)
	{
		if (!isVisible()) return;

		renderFrame(mouse);

		// CHILD
		glPushMatrix();
		glPushAttrib(GL_ENABLE_BIT);

		glTranslated(rect.x1(), rect.y1(), 2);
		child.render(mouse.sub(rect.getMin()));

		glPopAttrib();
		glPopMatrix();

		//RenderUtils.quadBorder(rect.grow(1, 1), 1, RGB.YELLOW, null);
	}


	/**
	 * Render frame decorations (not child)
	 * 
	 * @param mouse
	 */
	protected abstract void renderFrame(Coord mouse);


	/**
	 * Set horizontal align
	 * 
	 * @param align align
	 * @return this
	 */
	public final FrameBase setAlignH(int align)
	{
		alignH = align;
		return this;
	}


	/**
	 * Set vertical align
	 * 
	 * @param align align
	 * @return this
	 */
	public final FrameBase setAlignV(int align)
	{
		alignV = align;
		return this;
	}


	@Override
	public final IWidget setGuiRoot(GuiRoot guiContainer)
	{
		super.setGuiRoot(guiContainer);
		child.setGuiRoot(guiContainer);
		return this;
	}


	/**
	 * Set padding (inside distance from border)
	 * 
	 * @param left left padding
	 * @param right right padding
	 * @param top top padding
	 * @param bottom bottom padding
	 * @return this
	 */
	public final FrameBase setPadding(int left, int right, int top, int bottom)
	{
		padding.setTo(left, top, right, bottom);
		return this;
	}


	/**
	 * Set horizontal padding (inside distance from border)
	 * 
	 * @param left left padding
	 * @param right right padding
	 * @return this
	 */
	public final FrameBase setPaddingH(int left, int right)
	{
		padding.left = left;
		padding.right = right;
		return this;
	}


	/**
	 * Set vertical padding (inside distance from border)
	 * 
	 * @param top top padding
	 * @param bottom bottom padding
	 * @return this
	 */
	public final FrameBase setPaddingV(int top, int bottom)
	{
		padding.top = top;
		padding.bottom = bottom;
		return this;
	}
}
