package net.tortuga.level.program.tiles;


import net.tortuga.annotations.Unimplemented;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.TurtleRuntimeException;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public abstract class ProgTileGrain extends ProgTileBase {

	public ProgTileGrain() {}


	@Override
	public abstract CoordI getOverlayIndex();


	@Override
	public final EProgTileType getProgTileType()
	{
		return EProgTileType.GRAIN;
	}


	@Override
	public abstract RGB getStoneColor();


	public abstract EGrainType getGrainType();


	@Override
	public boolean isSingleInstance()
	{
		return false;
	}


	@Override
	public RGB getSubOverlayColor()
	{
		return RGB.WHITE;
	}


	@Override
	public CoordI getSubOverlayIndex()
	{
		return null;
	}


	/**
	 * Jump to target label or address
	 * 
	 * @param tre turtle runtime environment
	 * @throws TurtleRuntimeException
	 */
	@Unimplemented
	public void doJump(TurtleRuntimeEnvironment tre) throws TurtleRuntimeException
	{}


	public boolean hasExtraNumber()
	{
		return false;
	}


	public int getExtraNumber()
	{
		return 0;
	}


	@Unimplemented
	public void setExtraNumber(int num)
	{}


	@Unimplemented
	public void doCall(TurtleRuntimeEnvironment tre) throws TurtleRuntimeException
	{}

}
