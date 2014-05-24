package net.tortuga.gui.widgets.composite;


/**
 * Drag'n'drop stone
 * 
 * @author MightyPork
 */
public interface IDragSlot {

	/**
	 * Get stone in slot
	 * 
	 * @return the stone
	 */
	public IDragStone getStoneInSlot();


	/**
	 * Get if mouse is over this slot
	 * 
	 * @return is over
	 */
	public boolean isMouseOver();


	/**
	 * Get if slot has stone available for building
	 * 
	 * @return has stone
	 */
	public boolean hasStone();


	/**
	 * Grab stone from slot
	 * 
	 * @return stone
	 */
	public IDragStone grabStone();


	/**
	 * Get if this slot can accept given stone (is empty, stone is of right type
	 * etc.)
	 * 
	 * @param stone stone to check
	 * @return can be accepted
	 */
	public boolean acceptsStone(IDragStone stone);


	/**
	 * Put stone to this slot.
	 * 
	 * @param stone stone
	 * @return true on success
	 */
	public boolean putStone(IDragStone stone);


	/**
	 * Return dragged stone back to this slot
	 * 
	 * @param stone the dragged stone
	 */
	public void returnStone(IDragStone stone);


	/**
	 * Called when a stone was placed into program - eg. disable taking of more
	 * stones etc.
	 * 
	 * @param stone
	 */
	public void onStoneCreated(DragStone stone);


	/**
	 * Called when a stone was removed from program
	 * 
	 * @param stone
	 */
	public void onStoneDestroyed(DragStone stone);


	public boolean isShopSlot();


	public void deleteStone();
}
