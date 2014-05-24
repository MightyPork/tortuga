package net.tortuga.gui.widgets.layout;


import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.Collections;

import net.tortuga.gui.widgets.GuiRoot;
import net.tortuga.gui.widgets.IWidget;
import net.tortuga.gui.widgets.Widget;

import com.porcupine.coord.Coord;


/**
 * Base for layouts.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class LayoutBase extends Widget {

	/** layout children. */
	public ArrayList<Widget> children = new ArrayList<Widget>();


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		ArrayList<Widget> renderlist = new ArrayList<Widget>(children);
		Collections.sort(renderlist);

		glPushMatrix();
		glTranslated(rect.x1(), rect.y1(), 4);
		for (Widget child : renderlist) {
			glPushMatrix();
			glPushAttrib(GL_ENABLE_BIT);
			glTranslated(0, 0, 0.1 * child.renderIndex);
			Coord pos_r = mouse.sub(rect.getMin());
			child.render(pos_r);
			glPopAttrib();
			glPopMatrix();
		}
		glPopMatrix();

		//RenderUtils.quadBorder(rect, 1, RGB.PURPLE, null);

	}


	@Override
	public void handleStaticInputs(Coord pos)
	{
		if (!isVisible() || !isEnabled()) return;

		Coord pos_r = pos.sub(rect.getMin());
		for (Widget child : children) {
			if (!child.isEnabled() || !child.isVisible()) continue;
			child.handleStaticInputs(pos_r);
		}
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (!isVisible() || !isEnabled()) return null;

		Coord pos_r = pos.sub(rect.getMin());
		for (Widget child : children) {
			if (!child.isEnabled() || !child.isVisible()) continue;
			Widget consumer = child.onMouseButton(pos_r, button, down);
			if (consumer != null) return consumer;
		}
		return null;
	}


	@Override
	public Widget onScroll(Coord pos, int scroll)
	{
		if (!isVisible() || !isEnabled()) return null;

		Coord pos_r = pos.sub(rect.getMin());
		for (Widget child : children) {
			if (!child.isEnabled() || !child.isVisible()) continue;
			Widget consumer = child.onScroll(pos_r, scroll);
			if (consumer != null) return consumer;
		}
		return null;
	}


	@Override
	public Widget onKey(int key, char chr, boolean down)
	{
		if (!isVisible() || !isEnabled()) return null;

		for (Widget child : children) {
			if (!child.isEnabled() || !child.isVisible()) continue;
			Widget consumer = child.onKey(key, chr, down);
			if (consumer != null) return consumer;
		}
		return null;
	}


	@Override
	public abstract void calcChildSizes();


	@Override
	public void add(Widget child)
	{
		children.add(child);
	}


	@Override
	public void removeAll()
	{
		children.clear();
	}


	@Override
	public IWidget setGuiRoot(GuiRoot guiContainer)
	{
		super.setGuiRoot(guiContainer);
		for (Widget child : children) {
			child.setGuiRoot(guiContainer);
		}

		calcChildSizes();

		return this;
	}

}
