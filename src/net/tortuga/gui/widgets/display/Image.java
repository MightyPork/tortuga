package net.tortuga.gui.widgets.display;


import net.tortuga.gui.widgets.Widget;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;


/**
 * Image from texture
 * 
 * @author MightyPork
 */
public class Image extends Widget {

	private TxQuad texture = null;

	/** Tint color */
	public RGB colorTint = RGB.WHITE.copy();


	/**
	 * Set color tint
	 * 
	 * @param tint color tint
	 * @return this
	 */
	public Image setColor(RGB tint)
	{
		colorTint.setTo(tint);
		return this;
	}

	public String tooltipText = "";
	public RGB tooltipColor = RGB.WHITE;
	private long mouseEnterTime = 0;


	/**
	 * new icon
	 * 
	 * @param texture texture (px)
	 * @param textureRect texture rectangle (px)
	 * @param margin image margins
	 */
	public Image(TxQuad texture, int margin) {
		this.texture = texture;
		setMargins(margin, margin, margin, margin);
		setMinSize(texture.size);
	}


	/**
	 * Set tooltip
	 * 
	 * @param text text
	 * @param color render color
	 * @return this
	 */
	public Image setTooltip(String text, RGB color)
	{
		tooltipColor = color;
		tooltipText = text;
		return this;
	}


	public Image setTooltip(String text)
	{
		tooltipColor = RGB.WHITE.copy();
		tooltipText = text;
		return this;
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		RenderUtils.quadTextured(rect, texture, colorTint);

		boolean onTop = isPanelOnTop();
		boolean hover = isMouseOver(mouse);

		if (enabled && hover && onTop && tooltipText.length() > 0) {
			if (System.currentTimeMillis() - mouseEnterTime > 1000) {
				renderTooltip(tooltipText, tooltipColor);
			}
		} else {
			mouseEnterTime = System.currentTimeMillis();
		}
	}


	@Override
	public void calcChildSizes()
	{
		rect.setTo(0, 0, minSize.x, minSize.y);
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

}
