package net.tortuga.gui.widgets.input;


import java.util.HashSet;

import net.tortuga.fonts.LoadedFont;
import net.tortuga.textures.Tx;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Radio button.
 * 
 * @author MightyPork
 */
public class RadioButton extends Checkbox {

	/**
	 * Radio group.
	 * 
	 * @author MightyPork
	 */
	public static class RadioGroup extends HashSet<RadioButton> {
	}


	/**
	 * Build new radiobutton group.
	 * 
	 * @return group list.
	 */
	public static RadioGroup newGroup()
	{
		return new RadioGroup();
	}

	private RadioGroup group = null;


	/**
	 * Assign radio button group
	 * 
	 * @param groupList group list
	 * @return this
	 */
	public RadioButton setGroup(RadioGroup groupList)
	{
		this.group = groupList;
		group.add(this);
		return this;
	}


	/**
	 * Radio button
	 * 
	 * @param id widget ID
	 * @param text
	 */
	public RadioButton(int id, String text) {
		super(id, text);
		txtDist = 5;
	}


	/**
	 * Radio button
	 * 
	 * @param id widget id
	 * @param text widget text
	 * @param font render font
	 */
	public RadioButton(int id, String text, LoadedFont font) {
		super(id, text, font);
		txtDist = 5;
	}


	@Override
	public Checkbox setChecked(boolean checked)
	{
		if (checked == false) return this; // no unchecking here.
		for (RadioButton rb : group)
			rb.setChecked_do(false);
		setChecked_do(true);
		return this;
	}


	@Override
	protected Coord getBoxSize()
	{
		return new Coord(22, 22);
	}


	@Override
	public void render(Coord mouse)
	{
		if (!isVisible()) return;

		renderBase(mouse);

		Rect box = getBoxRect();
		TxQuad txq;

		if (checked) {
			txq = Tx.RADIO_ON;
		} else {
			txq = Tx.RADIO_OFF;
		}

		RenderUtils.quadTextured(box, txq);
	}
}
