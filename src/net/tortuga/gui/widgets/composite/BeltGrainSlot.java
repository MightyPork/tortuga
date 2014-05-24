package net.tortuga.gui.widgets.composite;


public class BeltGrainSlot implements IDragSlot {

	public DragStone stone = null;
	private PgmBeltCell cell;
	private int index;


	/**
	 * Belt cell grain slot
	 * 
	 * @param cell the cell
	 * @param index index: 0 top, 1 bottom
	 */
	public BeltGrainSlot(PgmBeltCell cell, int index) {
		this.cell = cell;
		this.index = index;
	}


	@Override
	public IDragStone getStoneInSlot()
	{
		return stone;
	}


	@Override
	public boolean isMouseOver()
	{
		return cell.isMouseOverGrain(index);
	}


	@Override
	public boolean hasStone()
	{
		return stone != null;
	}


	@Override
	public IDragStone grabStone()
	{
		DragStone st = stone;
		stone = null;
		return st;
	}


	@Override
	public boolean acceptsStone(IDragStone stone)
	{
		if (!(stone instanceof DragStone)) return false;
		if (!((DragStone) stone).isGrain()) return false;

		return cell.doesGrainSlotAcceptStone((DragStone) stone, index);
	}


	@Override
	public boolean putStone(IDragStone stone)
	{
		if (acceptsStone(stone)) {
			this.stone = (DragStone) stone;
			return true;
		}

		return false;
	}


	@Override
	public void returnStone(IDragStone stone)
	{
		if (hasStone()) return;
		this.stone = (DragStone) stone;
	}


	@Override
	public void onStoneCreated(DragStone stone)
	{}


	@Override
	public void onStoneDestroyed(DragStone stone)
	{}


	@Override
	public boolean isShopSlot()
	{
		return false;
	}


	@Override
	public void deleteStone()
	{
		stone = null;
	}

}
