package net.tortuga.gui.widgets.composite;


import net.tortuga.level.program.tiles.EProgTileType;
import net.tortuga.level.program.tiles.ProgTileBase;
import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.level.program.tiles.ProgTileStone;

import com.porcupine.coord.Coord;


public class DragStone implements IDragStone {

	private ProgTileBase stone;


	public DragStone(ProgTileBase stone) {
		this.stone = stone;
	}


	public boolean isStone()
	{
		return getType() == EProgTileType.STONE;
	}


	public boolean isGrain()
	{
		return getType() == EProgTileType.GRAIN;
	}


	public EProgTileType getType()
	{
		return stone.getProgTileType();
	}


	public ProgTileStone getSquareStone()
	{
		if (getType() == EProgTileType.STONE) {
			return (ProgTileStone) stone;
		} else {
			return null;
		}
	}


	public ProgTileGrain getHexagonStone()
	{
		if (getType() == EProgTileType.GRAIN) {
			return (ProgTileGrain) stone;
		} else {
			return null;
		}
	}


	@Override
	public void render(Coord pos)
	{
		stone.render(pos);
	}


	@Override
	public DragStone copy()
	{
		return new DragStone(stone.copy());
	}


	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof DragStone)) return false;
		return ((DragStone) obj).stone.equals(stone);
	}


	public ProgTileBase getStoneBase()
	{
		return stone;
	}

}
