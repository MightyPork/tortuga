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
import com.porcupine.math.Calc;


public class GrainJumpAbsolute extends ProgTileGrain {

	private int address = 1;


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.GRAIN_JUMP_ABSOLUTE;
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
		tre.jumpAbsolute(address - 1);
	}


	@Override
	public void doCall(TurtleRuntimeEnvironment tre) throws TurtleRuntimeException
	{
		tre.pushAddress();
		tre.jumpAbsolute(address - 1);
	}


	@Override
	public boolean hasExtraNumber()
	{
		return true;
	}


	@Override
	public int getExtraNumber()
	{
		return address;
	}


	@Override
	public void setExtraNumber(int num)
	{
		address = Calc.clampi(num, 1);
	}


	@Override
	public void copyFrom(ProgTileBase other)
	{
		super.copyFrom(other);
		setExtraNumber(((ProgTileGrain) other).getExtraNumber());
	}
}
