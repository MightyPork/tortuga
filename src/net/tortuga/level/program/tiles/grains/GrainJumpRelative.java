package net.tortuga.level.program.tiles.grains;


import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.TurtleRuntimeException;
import net.tortuga.level.program.tiles.EGrainType;
import net.tortuga.level.program.tiles.ProgTileBase;
import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.level.program.tiles.StoneColors;
import net.tortuga.level.program.tiles.StoneTx;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class GrainJumpRelative extends ProgTileGrain {

	private int increment = 0;


	@Override
	public CoordI getOverlayIndex()
	{
		return increment >= 0 ? StoneTx.GRAIN_JUMP_RELATIVE_FORTH : StoneTx.GRAIN_JUMP_RELATIVE_BACK;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.GRAIN_LABELS;
	}


	@Override
	public EGrainType getGrainType()
	{
		return EGrainType.LABEL;
	}


	@Override
	public void doJump(TurtleRuntimeEnvironment tre) throws TurtleRuntimeException
	{
		tre.jumpRelative(increment);
	}


	@Override
	public void doCall(TurtleRuntimeEnvironment tre) throws TurtleRuntimeException
	{
		tre.pushAddress();
		tre.jumpRelative(-1 + increment);
	}


	@Override
	public boolean hasExtraNumber()
	{
		return true;
	}


	@Override
	public int getExtraNumber()
	{
		return increment;
	}


	@Override
	public void setExtraNumber(int num)
	{
		increment = num;
	}


	@Override
	public void copyFrom(ProgTileBase other)
	{
		super.copyFrom(other);
		setExtraNumber(((ProgTileGrain) other).getExtraNumber());
	}

}
