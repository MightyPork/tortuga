package net.tortuga.gui.widgets.layout;


import net.tortuga.App;
import net.tortuga.gui.widgets.Widget;

import com.porcupine.coord.Coord;


/**
 * Space
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class AmbRenderer extends Widget {

	public AmbRenderer() {
		setMargins(0, 0, 0, 0);
		setMinSize(0, 0);
	}


	@Override
	public void render(Coord mouse)
	{
		App.weatherAnimation.render(App.currentDelta);
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		return null;
	}


	@Override
	public Widget onScroll(Coord pos, int scroll)
	{
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
		rect.setTo(0, 0, 0, 0);
	}

}
