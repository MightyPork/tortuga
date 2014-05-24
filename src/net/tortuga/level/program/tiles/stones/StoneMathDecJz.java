package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.TurtleRuntimeException;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneMathDecJz extends ProgTileStone {

	@Override
	public boolean acceptsGrainTop(EGrainType grainType)
	{
		return grainType == EGrainType.LABEL;
	}


	@Override
	public boolean acceptsGrainBottom(EGrainType grainType)
	{
		return grainType == EGrainType.VAR;
	}


	@Override
	public EGrainSlotMode getGrainModeTop()
	{
		return EGrainSlotMode.OPTIONAL;
	}


	@Override
	public EGrainSlotMode getGrainModeBottom()
	{
		return EGrainSlotMode.REQUIRED;
	}


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.MATH_DEC_JZ;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.MATH;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc) throws TurtleRuntimeException
	{
		int valOut;

		int outVar = bottomGrain.getVariant();

		valOut = tre.getVariable(bottomGrain.getVariant());
		outVar = bottomGrain.getVariant();

		tre.setVariable(outVar, valOut - 1);

		if (valOut - 1 == 0) {
			if (topGrain != null) {
				topGrain.doJump(tre);
			}
		}

	}
}
