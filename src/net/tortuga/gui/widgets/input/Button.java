package net.tortuga.gui.widgets.input;


import static net.tortuga.gui.widgets.EColorRole.*;
import net.tortuga.fonts.Fonts;
import net.tortuga.fonts.LoadedFont;
import net.tortuga.gui.widgets.ETheme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.input.Function;
import net.tortuga.sounds.Effects;
import net.tortuga.textures.Tx;
import net.tortuga.util.Align;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;


/**
 * Clickable button.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Button extends Widget {

	private Function<Boolean> onClickHandler;

	public boolean lastRenderHover = false;

	public double borderWidth = 2;
	public boolean showBorder = true;
	public double paddingX = 12;//10
	public double paddingY = 5;//4

	public String tooltipText = "";
	public RGB tooltipColor = RGB.WHITE;


	public void addOnClickHandler(Function<Boolean> handler)
	{
		onClickHandler = handler;
	}


	public Button setPadding(double x, double y)
	{
		paddingX = x;
		paddingY = y;
		return this;
	}


	public Button setBorderSize(double width)
	{
		borderWidth = width;
		showBorder = true;
		return this;
	}


	public Button enableBorder(boolean flag)
	{
		showBorder = flag;
		if (!flag) borderWidth = 0;
		return this;
	}


	/**
	 * new button
	 * 
	 * @param id widget id
	 * @param text widget text
	 * @param font render font
	 */
	public Button(int id, String text, LoadedFont font) {
		setId(id);
		setText(text);
		setFont(font);
		setTheme(ETheme.BUTTON);
		setMinSize(110, 0);
	}


	/**
	 * new button
	 * 
	 * @param id widget id
	 * @param text widget text
	 */
	public Button(int id, String text) {
		this(id, text, Fonts.gui);
	}


	/**
	 * new button
	 * 
	 * @param id widget id
	 */
	public Button(int id) {
		setId(id);
	}


	/**
	 * Set tooltip
	 * 
	 * @param text text
	 * @param color render color
	 * @return this
	 */
	public Button setTooltip(String text, RGB color)
	{
		tooltipColor = color;
		tooltipText = text;
		return this;
	}


	public Button setTooltip(String text)
	{
		tooltipColor = RGB.WHITE.copy();
		tooltipText = text;
		return this;
	}

	/** Borders - L,R,T,B */
	public boolean bdrs[] = { true, true, true, true };

	private long mouseEnterTime = 0;


	protected void renderBase(Coord mouse)
	{
		if (!isVisible()) return;

		boolean onTop = isPanelOnTop();

		boolean hover = isMouseOver(mouse);

		int ofs = selected || clicked ? 1 : hover ? 2 : 0;
		RenderUtils.quadTexturedFrame(rect.round(), Tx.BTN_SMALL, ofs, enabled ? RGB.WHITE : new RGB(RGB.WHITE, 0.3));

		if (enabled && hover && onTop && tooltipText.length() > 0) {
			if (System.currentTimeMillis() - mouseEnterTime > 1000) {
				renderTooltip(tooltipText, tooltipColor);
			}
		} else {
			mouseEnterTime = System.currentTimeMillis();
		}

		lastRenderHover = hover;

		//RenderUtils.quadBorder(rect.grow(1, 1), 1, RGB.ORANGE, null);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;
		renderBase(mouse);
		boolean hover = isMouseOver(mouse);

		Coord txtCenterPos = rect.getCenter().sub(3, font.getHeight() / 2 + 2).round();
		font.drawFuzzy(txtCenterPos, text, Align.CENTER, getColor(FG, mouse, hover), getColor(SHADOW, mouse, hover), 2, false);
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (selected || !enabled) return null;
		if (button != 0) return null;
		if (!isMouseOver(pos)) {
			clicked = false;
			return null;
		}
		if (down == true) {
			clicked = true;
		} else {
			if (clicked) {
				Effects.play("gui.button.dialog");

				clicked = false;

				if (onClickHandler != null) return onClickHandler.run() ? this : null;

				return this;
			}
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
		Coord oldms = getMinSize().copy();
		setMinSize(new Coord(font.getWidth(text) + borderWidth * 2 + paddingX * 2, font.getHeight() + borderWidth * 2 + paddingY * 2));
		if (minSize.x < oldms.x) minSize.x = oldms.x;
		if (minSize.y < oldms.y) minSize.y = oldms.y;
		rect.setTo(0, 0, minSize.x, minSize.y);
	}

}
