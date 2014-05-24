package net.tortuga.gui.screens;


import net.tortuga.gui.panels.PanelMenu;
import net.tortuga.sounds.Loops;
import net.tortuga.textures.Textures;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Rect;


/**
 * main menu screen
 * 
 * @author MightyPork
 */
public class ScreenMenuMain extends Screen {

	@Override
	public void initScreen()
	{
		Loops.playMenu();

		setRootPanel(new PanelMenu(this));
	}


	@Override
	public void render2D(double delta)
	{
		Rect quad = new Rect(app.getSize());
		Rect quadT = new Rect(app.getSize());

		RenderUtils.quadTextured(quad, quadT, Textures.steel_big_dk, new RGB(0xdddddd));
	}
}
