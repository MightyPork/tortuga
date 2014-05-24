package net.tortuga.gui.widgets.menu;


import static net.tortuga.gui.widgets.EColorRole.*;
import net.tortuga.fonts.Fonts;
import net.tortuga.fonts.LoadedFont;
import net.tortuga.gui.widgets.ETheme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.sounds.Effects;
import net.tortuga.textures.Tx;
import net.tortuga.util.Align;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;


/**
 * Clcikable button.
 * 
 * @author MightyPork
 */
public class MenuButton extends Widget {

	/**
	 * new button
	 * 
	 * @param id widget id
	 * @param text widget text
	 * @param font render font
	 */
	public MenuButton(int id, String text, LoadedFont font) {
		setId(id);
		setText(text);
		setFont(font);
		setTheme(ETheme.MENU_BUTTON);
	}


	/**
	 * new button
	 * 
	 * @param id widget id
	 * @param text widget text
	 */
	public MenuButton(int id, String text) {
		setId(id);
		setText(text);
		setFont(Fonts.menu_button);
		setTheme(ETheme.MENU_BUTTON);
		setMinSize(300, 72);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		boolean hover = (isMouseOver(mouse) && isPanelOnTop());

		RGB color = getColor(FG, mouse, hover);
		RGB blur = getColor(SHADOW, mouse, hover);

		/*double yOffset = clicked ? 72D / 256D : hover ? 144D / 256D : 0;

		Rect left = new Rect(rect.x1(), rect.y1(), rect.x1() + 13, rect.y2());
		Rect leftTx = new Rect(0D, 0, 13, 72).div_ip(256);
		leftTx.add_ip(0, yOffset);
		RenderUtils.quadRectTextured(left, leftTx, Textures.buttons);

		left = new Rect(rect.x1() + 13, rect.y1(), rect.x2() - 13, rect.y2());
		leftTx = new Rect(13D, 0, 243, 72).div_ip(256);
		leftTx.add_ip(0, yOffset);
		RenderUtils.quadRectTextured(left, leftTx, Textures.buttons);

		left = new Rect(rect.x2() - 13, rect.y1(), rect.x2(), rect.y2());
		leftTx = new Rect(243, 0, 256, 72).div_ip(256);
		leftTx.add_ip(0, yOffset);
		RenderUtils.quadRectTextured(left, leftTx, Textures.buttons);*/

		//RenderUtils.quadRectBorder(rect, 1, RGB.GREEN, RGB.TRANSPARENT);

		int ofs = clicked ? 1 : hover ? 2 : 0;
		RenderUtils.quadTexturedFrame(rect, Tx.BTN_MENU, ofs);

		font.drawFuzzy(rect.getCenter().sub(0, font.getHeight() / 2).round(), text, Align.CENTER, color, blur, 2, false);
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (button != 0) return null;

		if (!isMouseOver(pos)) {
			if (clicked && !down) clicked = false;
			return null;
		}
		if (down == true) {
			clicked = true;
		} else {
			if (clicked) {
				Effects.play("gui.button.menu");

				clicked = false;

				// mouse down and up
				// click consumed, send event to listener
				return this;
			}
			clicked = false;
		}
		return null;
	}


	@Override
	public void onBlur()
	{
		clicked = false;
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
		minSize.setMax(new Coord(font.getWidth(text) + 40, font.getHeight()));
		rect.setTo(minSize);

	}

}
