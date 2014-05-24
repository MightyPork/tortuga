package net.tortuga.gui.widgets.input;


import static net.tortuga.gui.widgets.EColorRole.*;
import net.tortuga.fonts.LoadedFont;
import net.tortuga.util.Align;

import org.lwjgl.opengl.GL11;

import com.porcupine.coord.Coord;


/**
 * Vertical (left tab) button
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ButtonVertical extends Button {

	/**
	 * new button v
	 * 
	 * @param id widget id
	 * @param text widget text
	 * @param font render font
	 */
	public ButtonVertical(int id, String text, LoadedFont font) {
		super(id, text, font);
	}


	/**
	 * new button v
	 * 
	 * @param id widget id
	 * @param text widget text
	 */
	public ButtonVertical(int id, String text) {
		super(id, text);
		setMinSize(0, 110);
	}


	/**
	 * new button v
	 * 
	 * @param id widget id
	 */
	public ButtonVertical(int id) {
		super(id);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;
		renderBase(mouse);

		GL11.glPushMatrix();

		Coord txtCenterPos = rect.getCenter().add(font.getHeight() / 2 + 2, -3);

		GL11.glTranslated(txtCenterPos.x, txtCenterPos.y, 0);

		GL11.glRotated(90, 0, 0, 1);

		font.draw(Coord.ZERO, text, getColor(FG, mouse), Align.CENTER);

		GL11.glPopMatrix();
	}


	@Override
	public void calcChildSizes()
	{
		Coord oldms = getMinSize().copy();
		double w = font.getHeight() + borderWidth * 2 + paddingX * 2;
		double h = font.getWidth(text) + borderWidth * 2 + paddingY * 2;
		setMinSize(new Coord(w, h));
		if (minSize.x < oldms.x) minSize.x = oldms.x;
		if (minSize.y < oldms.y) minSize.y = oldms.y;
		rect.setTo(0, 0, minSize.x, minSize.y);
	}

}
