package net.tortuga.gui.widgets.display;


import net.tortuga.gui.widgets.Theme;
import net.tortuga.gui.widgets.Widget;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;


/**
 * Passive text label
 * 
 * @author MightyPork
 */
public class TextDouble extends Widget {

	private String textLeft = "";
	private String textRight = "";

	/** Text color left */
	public RGB colorLeft = Theme.TEXT.copy();
	/** Text color right */
	public RGB colorRight = Theme.TEXT.copy();


	/**
	 * new text
	 * 
	 * @param width min width
	 */
	public TextDouble(int width) {
		setMargins(2, 0, 2, 0);
		setMinWidth(width);
	}


	public TextDouble setTextLeft(String text, RGB color)
	{
		textLeft = text;
		colorLeft = color;
		return this;
	}


	public TextDouble setTextRight(String text, RGB color)
	{
		textRight = text;
		colorRight = color;
		return this;
	}


	@Override
	public void calcChildSizes()
	{
		Coord oldms = getMinSize().copy();
		setMinSize(new Coord(oldms.x, font.getHeight()));
		if (minSize.x < oldms.x) minSize.x = oldms.x;
		if (minSize.y < oldms.y) minSize.y = oldms.y;
		rect.setTo(0, 0, minSize.x, minSize.y);
	}


	@Override
	public void onBlur()
	{}


	@Override
	public Widget onKey(int key, char chr, boolean down)
	{
		return null;
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
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		Coord leftP;
		leftP = rect.getCenter();
		leftP.x = rect.getMin().x;

		Coord rightP;

		rightP = rect.getCenter();
		rightP.x = rect.getMax().x;

		leftP.sub_ip(2, font.getHeight() / 2).round_ip();
		font.draw(leftP, textLeft, colorLeft, -1);

		rightP.sub_ip(2, font.getHeight() / 2).round_ip();
		font.draw(rightP, textRight, colorRight, 1);
	}


	public void eraseTexts()
	{
		textLeft = "";
		textRight = "";
	}


	public TextDouble setTexts(String left, String right)
	{
		this.textLeft = left;
		this.textRight = right;
		return this;
	}


	public TextDouble setColors(RGB left, RGB right)
	{
		this.colorLeft = left;
		this.colorRight = right;
		return this;
	}
}
