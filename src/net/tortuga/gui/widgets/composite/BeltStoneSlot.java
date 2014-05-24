package net.tortuga.gui.widgets.composite;


public class BeltStoneSlot implements IDragSlot {

	public DragStone stone = null;
	private PgmBeltCell cell;


	/**
	 * Belt cell grain slot
	 * 
	 * @param cell the cell
	 * @param index index: 0 top, 1 bottom
	 */
	public BeltStoneSlot(PgmBeltCell cell) {
		this.cell = cell;
	}


	@Override
	public IDragStone getStoneInSlot()
	{
		return stone;
	}


	@Override
	public boolean isMouseOver()
	{
		return cell.isMouseOverStone();
	}


	@Override
	public boolean hasStone()
	{
		return stone != null;
	}


	@Override
	public IDragStone grabStone()
	{
		DragStone topGrain = cell.topGrain.stone;
		DragStone bottomGrain = cell.bottomGrain.stone;

		DragStoneExtended st = new DragStoneExtended(topGrain, stone, bottomGrain);

		stone = null;
		cell.topGrain.stone = null;
		cell.bottomGrain.stone = null;
		return st;
	}


	@Override
	public boolean acceptsStone(IDragStone stone)
	{
		if (stone instanceof DragStone) return ((DragStone) stone).isStone();

		return true;
	}


	@Override
	public boolean putStone(IDragStone stone)
	{
		if (acceptsStone(stone)) {
			returnStone(stone);

			return true;
		}

		return false;
	}


	@Override
	public void returnStone(IDragStone stone)
	{
		if (hasStone()) return;
		if (stone instanceof DragStone) {
			this.stone = (DragStone) stone;
		} else {
			DragStoneExtended ext = (DragStoneExtended) stone;

			cell.topGrain.stone = ext.getTopStone();
			cell.bottomGrain.stone = ext.getBottomStone();
			this.stone = ext.getMiddleStone();
		}
	}


	@Override
	public void deleteStone()
	{
		stone = null;
		cell.topGrain.stone = null;
		cell.bottomGrain.stone = null;
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

}
