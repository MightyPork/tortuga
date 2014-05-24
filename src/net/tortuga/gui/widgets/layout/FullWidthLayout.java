package net.tortuga.gui.widgets.layout;


import static org.lwjgl.opengl.GL11.*;
import net.tortuga.App;
import net.tortuga.gui.widgets.GuiRoot;
import net.tortuga.gui.widgets.IRefreshable;
import net.tortuga.gui.widgets.IWidget;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.util.Align;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Vec;


/**
 * Frame for widgets (with shadow and border + background)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class FullWidthLayout extends Widget implements IRefreshable {

	public int alignH = Align.CENTER;
	public int alignV = Align.CENTER;

	private Widget child = new Gap(0, 0);


	public FullWidthLayout(Widget child) {
		this();
		add(child);
	}


	public FullWidthLayout(Widget child, int alignH, int alignV) {
		this();
		add(child);
		this.alignH = alignH;
		this.alignV = alignV;
	}


	public FullWidthLayout(int alignH, int alignV) {
		this();
		this.alignH = alignH;
		this.alignV = alignV;
	}


	public FullWidthLayout() {
		setMargins(0, 0, 0, 0);
	}


	/**
	 * Set the primary and only child.
	 */
	@Override
	public void add(Widget child)
	{
		this.child = child;
		setMinWidth(App.inst.getSize().xi());
		child.setMinWidth(App.inst.getSize().xi() - child.getMargins().getHorizontal());
	}


	@Override
	public void calcChildSizes()
	{
		child.calcChildSizes();

		rect.setTo(0, 0, minSize.x, child.getSize().y);

		//Coord lbPos = rect.getCenter();
		Coord childMove = new Coord();
		Coord sizeC = child.getSize();

		switch (alignH) {
			case Align.LEFT:
				childMove.x = 0;
				break;
			case Align.RIGHT:
				childMove.x = rect.getSize().x - sizeC.x;
				break;
			case Align.CENTER:
				childMove.x = rect.getSize().x / 2 - sizeC.x / 2;
				break;
		}

//		switch (alignV) {
//			case Align.TOP:
//				childMove.y = rect.getSize().y - sizeC.y;
//				break;
//			case Align.BOTTOM:
//				childMove.y = 0;
//				break;
//			case Align.CENTER:
//				childMove.y = rect.getSize().y / 2 - sizeC.y / 2;
//				break;
//		}

		child.rect.add_ip(new Vec(childMove));
	}


	@Override
	public Widget onKey(int key, char chr, boolean down)
	{
		if (!isVisible() || !isEnabled()) return null;

		if (!child.isEnabled() || !child.isVisible()) return null;
		return child.onKey(key, chr, down);
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (!isVisible() || !isEnabled()) return null;

		Coord pos_r = pos.sub(rect.getMin());

		if (!child.isEnabled() || !child.isVisible()) return null;
		return child.onMouseButton(pos_r, button, down);
	}


	@Override
	public Widget onScroll(Coord pos, int scroll)
	{
		if (!isVisible() || !isEnabled()) return null;

		Coord pos_r = pos.sub(rect.getMin());
		if (!child.isEnabled() || !child.isVisible()) return null;
		return child.onScroll(pos_r, scroll);
	}


	@Override
	public void handleStaticInputs(Coord pos)
	{
		if (!isVisible() || !isEnabled()) return;

		Coord pos_r = pos.sub(rect.getMin());

		if (!child.isEnabled() || !child.isVisible()) return;
		child.handleStaticInputs(pos_r);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		glPushMatrix();
		glPushAttrib(GL_ENABLE_BIT);

		glTranslated(rect.x1(), rect.y1(), 2);
		child.render(mouse.sub(rect.getMin()));

		glPopAttrib();
		glPopMatrix();
	}


	public FullWidthLayout setAlignH(int align)
	{
		alignH = align;
		return this;
	}


	public FullWidthLayout setAlignV(int align)
	{
		alignV = align;
		return this;
	}


	@Override
	public IWidget setGuiRoot(GuiRoot guiContainer)
	{
		super.setGuiRoot(guiContainer);
		child.setGuiRoot(guiContainer);

		guiContainer.scheduleRefresh(this);
		return this;
	}


	@Override
	public void refresh()
	{
		setMinWidth(App.inst.getSize().xi());
		child.setMinWidth(App.inst.getSize().xi() - child.getMargins().getHorizontal());
		calcChildSizes();
	}
}
