package net.tortuga.gui.widgets.menu;


import static net.tortuga.gui.widgets.EColorRole.*;
import net.tortuga.fonts.Fonts;
import net.tortuga.fonts.LoadedFont;
import net.tortuga.gui.widgets.ETheme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.util.Align;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;


/**
 * Passive label
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class MenuTitle extends Widget {

	/**
	 * Gui label
	 * 
	 * @param text widget text
	 * @param font render font
	 */
	public MenuTitle(String text, LoadedFont font) {
		setText(text);
		setFont(font);
		setTheme(ETheme.MENU_TITLE);
	}


	/**
	 * Gui label menu_title
	 * 
	 * @param text widget text
	 */
	public MenuTitle(String text) {
		this(text, Fonts.menu_title);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		RGB color = getColor(FG, mouse);
		RGB blur = getColor(SHADOW, mouse);

		font.drawFuzzy(rect.getCenterDown(), text, Align.CENTER, color, blur, 1);

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
		//FontManager.setFullscreenDoubleSize(true);
		setMinSize(font.getWidth(text), font.getHeight());
		rect.setTo(0, 0, minSize.x, minSize.y);
		//FontManager.setFullscreenDoubleSize(false);
	}

}
