package net.tortuga.gui.widgets.input;


import static net.tortuga.gui.widgets.EColorRole.*;
import net.tortuga.fonts.FontManager;
import net.tortuga.fonts.LoadedFont;
import net.tortuga.gui.widgets.ETheme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.util.Align;
import net.tortuga.util.RenderUtils;

import org.lwjgl.input.Keyboard;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.util.StringUtils;


/**
 * Clcikable button.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TextInput extends Widget {

	public static final String CHARS_ALL = FontManager.Glyphs.all;
	public static final String CHARS_ALNUM = FontManager.Glyphs.alnum;
	public static final String CHARS_BASIC = FontManager.Glyphs.basic;
	public static final String CHARS_EMAIL = FontManager.Glyphs.alnum_nospace + ".@+-_";
	public static final String CHARS_FILENAME = FontManager.Glyphs.alnum + ".,+-'!_&%#@()[]{}";
	public static final String CHARS_NUMBERS = "0123456789";
	public static final String CHARS_NUMBERS_FLOAT = "0123456789.-";
	public static final String CHARS_USERNAME = FontManager.Glyphs.basic;

	public String allowedChars = CHARS_BASIC;

	public double borderWidth = 2;
	public double paddingX = 6;
	public double paddingY = 4;

	public boolean passwordMode = false;


	/**
	 * new input
	 * 
	 * @param id widget id
	 * @param text widget text
	 * @param font render font
	 */
	public TextInput(int id, String text, LoadedFont font) {
		setId(id);
		setText(text);
		setFont(font);
		setTheme(ETheme.WIDGET);
	}


	/**
	 * new input
	 * 
	 * @param id widget id
	 * @param text widget text
	 */
	public TextInput(int id, String text) {
		setId(id);
		setText(text);
		setTheme(ETheme.WIDGET);
	}


	@Override
	public void calcChildSizes()
	{
		Coord oldms = getMinSize().copy();
		// + borderWidth * 2 + paddingX * 2
		setMinSize(new Coord(oldms.x, font.getHeight() + borderWidth * 2 + paddingY * 2));
		if (minSize.x < oldms.x) minSize.x = oldms.x;
		if (minSize.y < oldms.y) minSize.y = oldms.y;
		rect.setTo(0, 0, minSize.x, minSize.y);
	}


	private String getShownText(String text)
	{
		if (!passwordMode) return text;
		return StringUtils.passwordify(text);
	}


	@Override
	public void onBlur()
	{}


	@Override
	public void onFocus()
	{
		super.onFocus();
	}


	@Override
	public Widget onKey(int key, char chr, boolean down)
	{
		if (focused && enabled && down && chr != 0) {
			if (key == Keyboard.KEY_BACK || key == Keyboard.KEY_DELETE) {
				if (text.length() > 0) {
					text = text.substring(0, text.length() - 1);
					return this;
				}
			}
			if (allowedChars == null || allowedChars.contains("" + chr)) {
				if (font.getWidth(getShownText(text + chr) + "|") <= getMinSize().x - borderWidth * 2 - paddingX * 2) {
					text += chr;
					return this;
				}
			}
		}

		return null;
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (down) {
			if (isMouseOver(pos)) {
				return this;
			}
		}
		return null;
	}


	@Override
	public Widget onScroll(Coord pos, int scroll)
	{
		return null;
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		boolean hover = isMouseOver(mouse) || focused;

		RGB cBdr = getColor(BDR, mouse, hover);
		RGB cBg = getColor(BG, mouse, hover);
		RGB cFg = getColor(FG, mouse, hover);

		RenderUtils.quadBorder(rect, borderWidth, cBdr, cBg);

		boolean cursor = focused && enabled && System.currentTimeMillis() % 800 < 400;

		Coord txtPos = rect.getMin().add(paddingX + borderWidth, paddingY + borderWidth - 2).round_ip();
		String shown = getShownText(text);
		font.draw(txtPos, shown + (cursor ? "|" : ""), cFg, Align.LEFT);
	}


	/**
	 * Set chars allowed in this text input.
	 * 
	 * @param allowed allowed chars
	 * @return this
	 */
	public TextInput setAllowedChars(String allowed)
	{
		this.allowedChars = allowed;
		return this;
	}


	public TextInput setBorderSize(double width)
	{
		borderWidth = width;
		return this;
	}


	public TextInput setPadding(double x, double y)
	{
		paddingX = x;
		paddingY = y;
		return this;
	}


	public TextInput setPasswordMode(boolean state)
	{
		passwordMode = state;
		return this;
	}

}
