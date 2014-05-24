package net.tortuga.gui.widgets.input;


import static net.tortuga.gui.widgets.EColorRole.*;
import net.tortuga.fonts.Fonts;
import net.tortuga.fonts.LoadedFont;
import net.tortuga.gui.widgets.ETheme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.sounds.Effects;
import net.tortuga.textures.Tx;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.Align;
import net.tortuga.util.RenderUtils;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Clcikable button.
 * 
 * @author MightyPork
 */
public class Checkbox extends Widget {

	public boolean checked = false;
	protected int txtDist = 0;


	public Checkbox setChecked(boolean checked)
	{
		setChecked_do(checked);
		return this;
	}


	public void setChecked_do(boolean checked)
	{
		this.checked = checked;
	}


	public boolean isChecked()
	{
		return checked;
	}


	/**
	 * new ckbox
	 * 
	 * @param id widget id
	 * @param text widget text
	 * @param font render font
	 */
	public Checkbox(int id, String text, LoadedFont font) {
		setId(id);
		setText(text);
		setFont(font);
		setTheme(ETheme.WIDGET);
	}


	/**
	 * new ckbox
	 * 
	 * @param id widget id
	 * @param text widget text
	 */
	public Checkbox(int id, String text) {
		this(id, text, Fonts.gui);
	}


	protected Coord getBoxSize()
	{
		return new Coord(58, 28);
	}


	protected Rect getBoxRect()
	{
		Coord size = getBoxSize();
		int imgW = (int) size.x;
		int imgH = (int) size.y;

		Coord left = rect.getCenterLeft().sub(-2, imgH / 2).round();
		return new Rect(left, left.add(imgW, imgH));
	}


	public void renderBase(Coord mouse)
	{
		if (!isVisible()) return;

		Coord txtCenterPos = getBoxRect().getCenterRight().add(txtDist, -font.getHeight() / 2).round();
		font.draw(txtCenterPos, text, getColor(FG, mouse), Align.LEFT);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		renderBase(mouse);

		Rect box = getBoxRect().round();
		TxQuad txq;

		if (checked) {
			txq = Tx.CKBOX_ON;
		} else {
			txq = Tx.CKBOX_OFF;
		}

		RenderUtils.quadTextured(box, txq);
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		if (button != 0) return null;
		if (!isMouseOver(pos)) {
			clicked = false;
			return null;
		}
		if (down == true) {
			clicked = true;
		} else {
			if (clicked) {
				Effects.play("gui.switch");

				clicked = false;
				setChecked(!isChecked());
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
		setMinSize(new Coord(font.getWidth(text) + getBoxSize().x + this.txtDist, Math.max(font.getHeight(), getBoxSize().y)));
		if (minSize.x < oldms.x) minSize.x = oldms.x;
		if (minSize.y < oldms.y) minSize.y = oldms.y;
		rect.setTo(0, 0, minSize.x, minSize.y);
	}

}
