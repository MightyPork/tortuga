package net.tortuga.gui.panels.dialogs;


import net.tortuga.gui.panels.PanelGui;
import net.tortuga.gui.screens.Screen;
import net.tortuga.gui.widgets.Theme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.display.Text;
import net.tortuga.gui.widgets.input.Button;
import net.tortuga.gui.widgets.layout.LayoutH;
import net.tortuga.gui.widgets.layout.LayoutV;
import net.tortuga.gui.widgets.layout.frame.FrameWindow;
import net.tortuga.sounds.Effects;
import net.tortuga.util.Align;

import org.lwjgl.input.Keyboard;

import com.porcupine.color.RGB;


/**
 * Main menu panel
 * 
 * @author MightyPork
 */
public class PanelDialogModal extends PanelGui {

	/**
	 * Dialog click handler
	 * 
	 * @author MightyPork
	 */
	public interface IDialogListener {

		/**
		 * on dialog closed by button press
		 * 
		 * @param dialogId id of the dualog used
		 * @param button button index, or -1 on ESC
		 */
		public void onDialogButton(int dialogId, int button);
	}

	private String text;
	private String title = null;
	private String[] buttons;
	private IDialogListener listener;
	private boolean cancellable = true;
	private int id = -1;

	private int enterIndex = -1;


	public PanelDialogModal setEnterButton(int index)
	{
		enterIndex = index;
		return this;
	}


	/**
	 * Create modal dialog
	 * 
	 * @param screen parent screen
	 * @param listener onButton listenner (handles user input)
	 * @param id id
	 * @param cancellable can be cancelled by ESC (listener gets -1)
	 * @param text shown text
	 * @param buttons button texts, IDs 0,1,2...
	 */
	public PanelDialogModal(Screen screen, IDialogListener listener, int id, boolean cancellable, String text, String... buttons) {
		super(screen);
		this.text = text;
		this.buttons = buttons;
		this.listener = listener;
		this.cancellable = cancellable;
		this.id = id;
	}


	public PanelDialogModal setTitle(String title)
	{
		this.title = title;
		return this;
	}


	@Override
	public void initGui()
	{
		FrameWindow frame = Theme.mkWindow();

		LayoutV v = new LayoutV(Align.CENTER, Align.CENTER);
		v.setMinWidth(400);
		v.setMinWidth(200);
		if (title != null) v.add(Theme.mkTitle(title));
		v.add(new Text(text));

		LayoutH h = new LayoutH(Align.CENTER, Align.CENTER);
		int i = 0;
		for (String s : buttons) {
			Button btn = new Button(i++, s);

			btn.setMarginsH(10, 10);
			h.add(btn);
		}
		v.add(h);

		frame.add(v);

		addGui(frame);

		Effects.play("gui.popup");
	}


	@Override
	public boolean hasBackgroundLayer()
	{
		return true;
	}


	@Override
	public RGB getBackgroundColor()
	{
		return new RGB(0, 0.4);
	}


	@Override
	public void actionPerformed(Widget widget)
	{
		if (widget instanceof Button) {
			closePanel();
			if (listener != null) listener.onDialogButton(id, widget.getId());
		}
	}


	@Override
	public void onKey(int key, char c, boolean down)
	{
		super.onKey(key, c, down);

		if (cancellable && down && key == Keyboard.KEY_ESCAPE) {
			closePanel();
			if (listener != null) listener.onDialogButton(id, -1);
			return;
		}

		if ((enterIndex != -1 || buttons.length == 1) && down && (key == Keyboard.KEY_RETURN || key == Keyboard.KEY_NUMPADENTER)) {
			closePanel();

			if (listener != null) {
				if (enterIndex != -1) {
					listener.onDialogButton(id, enterIndex);
				} else if (buttons.length == 1) {
					listener.onDialogButton(id, 0);
				}
			}
			return;
		}
	}
}
