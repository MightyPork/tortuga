package net.tortuga.gui.widgets.input;


import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.HSV;
import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Button with icon
 * 
 * @author MightyPork
 */
public class ButtonIcon extends Button {

	private TxQuad txq = null;

	private RGB tint = RGB.WHITE;


	/**
	 * Set color tint
	 * 
	 * @param tint color tint
	 * @return this
	 */
	public ButtonIcon setColor(RGB tint)
	{
		tint.setTo(tint);
		return this;
	}


	/**
	 * new button
	 * 
	 * @param id widget id
	 * @param txquad texture quad
	 */
	public ButtonIcon(int id, TxQuad txquad, RGB tint) {
		super(id);
		setId(id);
		this.tint = tint;
		this.txq = txquad;
		setPadding(8, 8);
		setMargins(3, 3, 3, 3);
		setMinSize(0, 0);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;
		renderBase(mouse);

		Rect iconRect = new Rect(rect.getCenter(), rect.getCenter()).grow_ip(txq.size.x / 2, txq.size.y / 2).round_ip();

		HSV color = tint.toHSV();

		if (clicked || selected) {
			color.v *= 0.8;
		} else if (isMouseOver(mouse)) {
			color.v *= 1.2;
		}

		color.norm();

		RenderUtils.quadTextured(iconRect, txq, new RGB(color.toRGB(), enabled ? 1 : 0.3));
	}


	@Override
	public void calcChildSizes()
	{
		Coord oldms = getMinSize().copy();
		setMinSize(new Coord(txq.size.x + borderWidth * 2 + paddingX * 2, txq.size.y + borderWidth * 2 + paddingY * 2));
		if (minSize.x < oldms.x) minSize.x = oldms.x;
		if (minSize.y < oldms.y) minSize.y = oldms.y;
		rect.setTo(0, 0, minSize.x, minSize.y);
	}

}
