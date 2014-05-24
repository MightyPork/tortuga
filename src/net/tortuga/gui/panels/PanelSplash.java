package net.tortuga.gui.panels;


import net.tortuga.LoadingManager;
import net.tortuga.fonts.Fonts;
import net.tortuga.gui.screens.Screen;
import net.tortuga.gui.screens.ScreenMenuMain;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.display.Image;
import net.tortuga.gui.widgets.display.Text;
import net.tortuga.gui.widgets.layout.LayoutV;
import net.tortuga.textures.Tx;

import com.porcupine.color.RGB;


/**
 * Splash panel
 * 
 * @author MightyPork
 */
public class PanelSplash extends PanelGui {

	/**
	 * @param screen
	 */
	public PanelSplash(Screen screen) {
		super(screen);
	}


	@Override
	public void onClose()
	{}


	@Override
	public void onWindowChanged()
	{}


	@Override
	public boolean hasBackgroundLayer()
	{
		return false;
	}


	@Override
	public void onPostInit()
	{
		super.onPostInit();
	}


	@Override
	public void updateGui()
	{
//		while(LoadingManager.hasMoreGroups()) {
//			LoadingManager.loadGroup();
//		}
//		LoadingManager.onResourcesLoaded();
//
//		app.replaceScreen(new ScreenMenuMain());

		if (LoadingManager.hasMoreGroups()) {
			LoadingManager.loadGroup();
		} else {
			LoadingManager.onResourcesLoaded();

			app.replaceScreen(new ScreenMenuMain());
		}
	}


	@Override
	public void initGui()
	{
		LayoutV v = new LayoutV();

		addGui(v);

		v.add(new Image(Tx.LOGO, 0));
		v.add(new Text("Loading...").setColorText(RGB.WHITE).setBlur(new RGB(RGB.BLUE, 0.1), 2).setFont(Fonts.splash_info));

	}


	@Override
	public void actionPerformed(Widget widget)
	{}
}
