package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneMathAnd extends ProgTileStone {

	@Override
	public boolean acceptsGrainTop(EGrainType grainType)
	{
		return grainType == EGrainType.NUMBER || grainType == EGrainType.VAR;
	}


	@Override
	public boolean acceptsGrainBottom(EGrainType grainType)
	{
		return grainType == EGrainType.VAR;
	}


	@Override
	public EGrainSlotMode getGrainModeTop()
	{
		return EGrainSlotMode.REQUIRED;
	}


	@Override
	public EGrainSlotMode getGrainModeBottom()
	{
		return EGrainSlotMode.REQUIRED;
	}


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.MATH_AND;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.MATH;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc)
	{
		int valIn;
		int valOut;

		int outVar = bottomGrain.getVariant();

		if (topGrain.getGrainType() == EGrainType.VAR) {
			valIn = tre.getVariable(topGrain.getVariant());
		} else {
			valIn = topGrain.getExtraNumber();
		}

		valOut = tre.getVariable(bottomGrain.getVariant());
		outVar = bottomGrain.getVariant();

		boolean bIn = valIn != 0;
		boolean bOut = valOut != 0;

		tre.setVariable(outVar, (bIn && bOut) ? 1 : 0);

	}
}
