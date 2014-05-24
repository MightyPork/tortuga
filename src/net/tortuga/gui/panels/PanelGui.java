package net.tortuga.gui.panels;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tortuga.annotations.Unimplemented;
import net.tortuga.gui.screens.Screen;
import net.tortuga.gui.widgets.GuiRoot;
import net.tortuga.gui.widgets.GuiRoot.EventListener;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.layout.LayoutV;
import net.tortuga.util.Align;

import com.porcupine.coord.Coord;
import com.porcupine.math.Calc;


public abstract class PanelGui extends Panel implements EventListener {

	protected List<GuiRoot> guis = new ArrayList<GuiRoot>();
	private LayoutV v;


	public PanelGui(Screen screen) {
		super(screen);
	}


	public GuiRoot addGui(Widget layout, int alignH, int alignV)
	{
		GuiRoot gui = new GuiRoot(this, layout);
		gui.setAlign(alignH, alignV);
		guis.add(gui);
		return gui;
	}


	public GuiRoot addGui(Widget layout)
	{
		GuiRoot gui = new GuiRoot(this, layout);
		gui.setAlign(Align.CENTER, Align.CENTER);
		guis.add(gui);
		return gui;
	}


	@Override
	public final void onCreate()
	{
		initGui();

		for (GuiRoot gui : guis) {
			gui.setParentPanel(this);
			gui.updatePositions();
		}

		Collections.sort(guis);
		onPostInit();
	}


	/**
	 * Called after panel is fully created
	 */
	@Unimplemented
	public void onPostInit()
	{}


	@Override
	@Unimplemented
	public void onClose()
	{}


	public final void updateWidgetPositions()
	{
		for (GuiRoot gui : guis) {
			gui.updatePositions();
		}
	}


//	public final GuiRoot getRootWidget() {
//		return gui;
//	}

	public abstract void initGui();


	@Override
	public void onWindowChanged()
	{
		for (GuiRoot gui : guis) {
			gui.updatePositions();
		}
	}


	@Override
	public boolean hasBackgroundLayer()
	{
		return true;
	}


	@Override
	@Unimplemented
	public void updateGui()
	{}


	@Override
	protected void renderPanel()
	{
		//glPushMatrix();
		for (GuiRoot gui : guis) {
			gui.render();
			//glTranslated(0, 0, 10);
		}
		//glPopMatrix();
	}


	@Override
	public abstract void actionPerformed(Widget widget);


	@Override
	public void onMouseButton(int button, boolean down, int wheelDelta, Coord pos, Coord deltaPos)
	{
		for (GuiRoot gui : guis) {
			if (button != -1) gui.onMouseButton(button, down);
			if (wheelDelta != 0) gui.onScroll(Calc.clampi(wheelDelta, -1, 1));
		}
	}


	@Override
	public void onKey(int key, char c, boolean down)
	{
		for (GuiRoot gui : guis) {
			gui.onKeyDown(key, c, down);
		}
	}


	@Override
	public void handleStaticInputs()
	{
		for (GuiRoot gui : guis) {
			gui.handleStaticInputs();
		}
	}

}
