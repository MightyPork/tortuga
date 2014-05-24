package net.tortuga.gui.panels;


import net.tortuga.gui.screens.Screen;


/**
 * Empty do-nothing panel, used in Screens with no Gui instead of NULL.
 * 
 * @author MightyPork
 */
public class PanelEmpty extends Panel {

	/**
	 * New empty panel
	 * 
	 * @param screen screen
	 */
	public PanelEmpty(Screen screen) {
		super(screen);
	}


	@Override
	public void onCreate()
	{}


	@Override
	public void onClose()
	{}


	@Override
	public boolean hasBackgroundLayer()
	{
		return false;
	}


	@Override
	public void updateGui()
	{}


	@Override
	protected void renderPanel()
	{}


	@Override
	public void onWindowChanged()
	{}
}
