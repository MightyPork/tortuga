package net.tortuga.gui.panels;


import net.tortuga.App;
import net.tortuga.gui.panels.dialogs.PanelDialogModal;
import net.tortuga.gui.panels.dialogs.PanelDialogModal.IDialogListener;
import net.tortuga.gui.screens.Screen;
import net.tortuga.gui.screens.ScreenGame;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.display.Image;
import net.tortuga.gui.widgets.layout.Gap;
import net.tortuga.gui.widgets.layout.LayoutV;
import net.tortuga.gui.widgets.menu.MenuButton;
import net.tortuga.textures.Tx;


/**
 * Main menu panel
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class PanelMenu extends PanelGui {

	private static final int EXIT = 0;
	private static final int PLAY = 1;
	private static final int CONFIG = 3;
	private static final int USERS = 4;
	private static final int LEVELS = 5;


//	private LayoutH top;
//	private FrameWindow frame;

	public PanelMenu(Screen screen) {
		super(screen);
	}


	@Override
	public void initGui()
	{
		LayoutV v = new LayoutV();

		v.add(new Image(Tx.TITLE, 0));
		//v.add(new MenuTitle("Tortuga"));
		v.add(new Gap(0, 10));
		v.add(new MenuButton(LEVELS, "Show Game Screen"));
		//v.add(new Gap(0, 15));
		v.add(new MenuButton(CONFIG, "Settings"));
		v.add(new MenuButton(EXIT, "Quit Game"));

		addGui(v);

//		top = new LayoutH(Align.CENTER, Align.CENTER);
//		top.setMargins(0, 0, 0, 0);
//		
//		frame = new FrameWindow(top);
//		frame.setMinHeight(80);
//		frame.setMarginsH(50, 50);
//		
//		addGui(new FullWidthLayout(frame), Align.CENTER, Align.TOP);

	}


//	@Override
//	public void onWindowChanged() {
//		super.onWindowChanged();
//		
//		top.removeAll();
//		int width = (int) (app.getSize().x - frame.getMargins().getHorizontal() - frame.paddingLeft - frame.paddingRight);
//		
//		IWidgetFactory maker = new IWidgetFactory() {			
//			@Override
//			public Widget getWidget() {
//				return new Button(-1, "Hello!");
//			}
//		};
//		
//		Widget b = maker.getWidget();
//		b.calcChildSizes();
//		int one = b.getSize().xi()+b.getMargins().left;
//				
//		int canFit = width / one;
//		
//		for(int i=0; i<canFit; i++) {
//			top.add((Widget) maker.getWidget().setGuiRoot(top.guiRoot));
//		}
//		
//		updateWidgetPositions();
//	}

	@Override
	public void onPostInit()
	{}


	@Override
	public boolean hasBackgroundLayer()
	{
		return false;
	}


	@Override
	public void actionPerformed(Widget widget)
	{
		if (!widget.isEnabled()) return;

		switch (widget.id) {
			case EXIT:
				PanelDialogModal p;

				IDialogListener listener = new IDialogListener() {

					@Override
					public void onDialogButton(int dialogId, int button)
					{
						if (button == 0) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {}
							App.inst.exit();
						}
					}
				};

				p = new PanelDialogModal(screen, listener, -1, true, "Do you really want to quit?", "Yes", "No").setEnterButton(0);

				openPanel(p);
				break;

			case PLAY:
				break;

			case LEVELS:
				app.replaceScreen(new ScreenGame());
				break;

			case CONFIG:
				openPanel(new PanelConfig(screen, 0));
				break;

			case USERS:
				break;
		}
	}
}
