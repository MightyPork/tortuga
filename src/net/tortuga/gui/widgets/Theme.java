package net.tortuga.gui.widgets;


import net.tortuga.fonts.Fonts;
import net.tortuga.gui.widgets.display.Text;
import net.tortuga.gui.widgets.layout.frame.FrameWindow;

import com.porcupine.color.RGB;


public class Theme {

	public static final RGB TITLE = new RGB(0xffffff, 1);
	public static final RGB TITLE_BLUR = new RGB(0x000000, 0.1);
	public static final RGB TEXT = new RGB(0x101010, 1);


	public static Text mkTitle(String text)
	{
		Text tx = new Text(text);
		tx.setColorText(TITLE);
		tx.setBlur(TITLE_BLUR, 2, true);
		tx.setFont(Fonts.gui_title);
		tx.setMargins(0, 5, 0, 1);

		return tx;
	}


	public static FrameWindow mkWindow()
	{
		FrameWindow frame = new FrameWindow();
		frame.setPadding(20, 20, 25, 25);
		frame.enableShadow(true);
		return frame;
	}
}
