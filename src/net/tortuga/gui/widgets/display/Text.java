package net.tortuga.gui.widgets.display;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.tortuga.fonts.LoadedFont;
import net.tortuga.gui.widgets.Theme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.input.Function;
import net.tortuga.util.Align;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;


/**
 * Passive text label
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Text extends Widget {

	private Function<Boolean> onClickHandler;

	private int align = Align.CENTER;

	private CoordI padding = new CoordI(0, 0);

	private String[] linesRev = null;

	/** Text color */
	public RGB textColor = Theme.TEXT.copy();

	/** Blur color */
	public RGB blurColor = new RGB(0xffffff, 0.1);

	/** Blur size */
	public int blurSize = 0;

	/** Blur smooth flag */
	public boolean blurSmooth = true;


	/**
	 * Set text padding
	 * 
	 * @param x padding x
	 * @param y padding y
	 * @return this
	 */
	public Text setPadding(int x, int y)
	{
		padding.setTo(x, y);
		return this;
	}


	/**
	 * Assign on clock handler
	 * 
	 * @param handler handler
	 * @return this
	 */
	public Text addOnClickHandler(Function<Boolean> handler)
	{
		onClickHandler = handler;
		return this;
	}


	/**
	 * Set text blur
	 * 
	 * @param color blur color
	 * @param size blur size
	 * @param smooth render smooth blur (all iterations / only the outmost)
	 * @return this
	 */
	public Text setBlur(RGB color, int size, boolean smooth)
	{
		this.blurColor.setTo(color);
		this.blurSize = size;
		this.blurSmooth = smooth;
		return this;
	}


	/**
	 * Set text blur (smooth=true)
	 * 
	 * @param color blur color
	 * @param size blur size
	 * @return this
	 */
	public Text setBlur(RGB color, int size)
	{
		this.blurColor.setTo(color);
		this.blurSize = size;
		this.blurSmooth = true;
		return this;
	}


	/**
	 * new text
	 * 
	 * @param text widget text
	 * @param font render font
	 */
	public Text(String text, LoadedFont font) {
		setText(text);
		setMargins(2, 0, 2, 0);
		setFont(font);
	}


	/**
	 * new text
	 * 
	 * @param text widget text
	 */
	public Text(String text) {
		setText(text);
		setMargins(2, 0, 2, 0);
	}


	@Override
	public void calcChildSizes()
	{
		Coord oldms = getMinSize().copy();

		double max = 0;
		for (String s : linesRev) {
			max = Math.max(max, font.getWidth(s));
		}

		setMinSize(new Coord(max, font.getHeight() * linesRev.length));

		if (minSize.x < oldms.x) minSize.x = oldms.x;
		if (minSize.y < oldms.y) minSize.y = oldms.y;
		rect.setTo(0, 0, minSize.x + padding.x * 2, minSize.y + padding.y * 2);
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
		if (isMouseOver(pos) && down) clicked = true;
		if (!down) {
			if (clicked && isMouseOver(pos)) {
				clicked = false;
				if (onClickHandler != null) return onClickHandler.run(this) ? this : null;
			}
			clicked = false;
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

		int i = 0;
		for (String s : linesRev) {
			Coord txtPos = rect.getCenter();

			switch (align) {
				case Align.CENTER:
					txtPos = rect.getCenter();
					break;

				case Align.LEFT:
					txtPos = rect.getCenter();
					txtPos.x = rect.getMin().x + padding.x;
					break;

				case Align.RIGHT:
					txtPos = rect.getCenter();
					txtPos.x = rect.getMax().x - padding.x;
					break;
			}

			txtPos.sub_ip(2, font.getHeight() * linesRev.length / 2).add_ip(0, font.getHeight() * i).round_ip();

			if (blurSize == 0) {
				font.draw(txtPos, s, textColor, align);
			} else {
				font.drawFuzzy(txtPos, text, align, textColor, blurColor, blurSize, blurSmooth);
			}

			i++;

		}

		// debug white rect showing the bounds.
//		RenderUtils.setColor(new RGB(1,1,1,0.2));
//		RenderUtils.quadAbsCoord(rect.x1(), rect.y1(), rect.x2(), rect.y2());
//		RenderUtils.setColor(new RGB(1,1,1));
	}


	/**
	 * Set text color
	 * 
	 * @param color color
	 * @return this
	 */
	public Text setColorText(RGB color)
	{
		textColor = color;
		return this;
	}


	@Override
	public Text setText(String text)
	{
		super.setText(text);

		linesRev = text.split("\n");
		List<String> l = Arrays.asList(linesRev);
		Collections.reverse(l);

		return this;
	}


	/**
	 * Set how the text should be aligned if minWidth is higher than text's
	 * width.
	 * 
	 * @param align -1 left, 0 center, 1 right; Use constant from Align class.
	 * @return this
	 */
	public Text setTextAlign(int align)
	{
		this.align = align;
		return this;
	}

}
