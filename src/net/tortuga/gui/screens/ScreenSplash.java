package net.tortuga.gui.screens;


import net.tortuga.gui.panels.PanelSplash;
import net.tortuga.sounds.Loops;
import net.tortuga.textures.Textures;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Rect;


/**
 * Splash screen
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ScreenSplash extends Screen {

	@Override
	public void initScreen()
	{
		Loops.playIntro();

		setRootPanel(new PanelSplash(this));
	}


	@Override
	public void render2D(double delta)
	{
		Rect quad = new Rect(0, 0, app.getSize().x, app.getSize().y);
		Rect quad2 = new Rect(0, 0, app.getSize().x, app.getSize().y);

		RenderUtils.quadTextured(quad, quad2, Textures.steel_small_dk, new RGB(0x999999));
	}


	@Override
	public boolean isWeatherEnabled()
	{
		return false;
	}

}
