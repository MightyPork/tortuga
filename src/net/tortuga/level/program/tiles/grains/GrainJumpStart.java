package net.tortuga.level.program.tiles.grains;


import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.TurtleRuntimeException;
import net.tortuga.level.program.tiles.EGrainType;
import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.level.program.tiles.StoneColors;
import net.tortuga.level.program.tiles.StoneTx;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class GrainJumpStart extends ProgTileGrain {

	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.GRAIN_JUMP_START;
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
		tre.jumpStart();
	}

}
