package net.tortuga.gui.widgets.composite;


import com.porcupine.coord.Coord;


public class DragStoneExtended implements IDragStone {

	private DragStone top;
	private DragStone middle;
	private DragStone bottom;


	public DragStoneExtended(DragStone top, DragStone middle, DragStone bottom) {
		this.middle = middle;
		this.top = top;
		this.bottom = bottom;
	}


	public DragStone getMiddleStone()
	{
		return middle;
	}


	public DragStone getTopStone()
	{
		return top;
	}


	public DragStone getBottomStone()
	{
		return bottom;
	}


	@Override
	public void render(Coord pos)
	{
		if (top != null) top.render(pos.add(0, 64));
		middle.render(pos);
		if (bottom != null) bottom.render(pos.sub(0, 64));
	}


	@Override
	public IDragStone copy()
	{
		return new DragStoneExtended(top != null ? top.copy() : null, middle != null ? middle.copy() : null, bottom != null ? bottom.copy() : null);
	}

}
