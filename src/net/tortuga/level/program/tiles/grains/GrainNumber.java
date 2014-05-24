package net.tortuga.level.program.tiles.grains;


import net.tortuga.level.program.tiles.EGrainType;
import net.tortuga.level.program.tiles.ProgTileBase;
import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.level.program.tiles.StoneColors;
import net.tortuga.level.program.tiles.StoneTx;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class GrainNumber extends ProgTileGrain {

	private int number = 0;


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.GRAIN_NUMBER;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.GRAIN_NUMBER;
	}


	@Override
	public EGrainType getGrainType()
	{
		return EGrainType.NUMBER;
	}


	@Override
	public boolean hasExtraNumber()
	{
		return true;
	}


	@Override
	public int getExtraNumber()
	{
		return number;
	}


	@Override
	public void setExtraNumber(int num)
	{
		this.number = num;
	}


	@Override
	public void copyFrom(ProgTileBase other)
	{
		super.copyFrom(other);
		setExtraNumber(((ProgTileGrain) other).getExtraNumber());
	}
}
