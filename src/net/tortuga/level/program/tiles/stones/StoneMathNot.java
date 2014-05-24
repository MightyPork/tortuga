package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneMathNot extends ProgTileStone {

	@Override
	public boolean acceptsGrainTop(EGrainType grainType)
	{
		return false;
	}


	@Override
	public boolean acceptsGrainBottom(EGrainType grainType)
	{
		return grainType == EGrainType.VAR;
	}


	@Override
	public EGrainSlotMode getGrainModeTop()
	{
		return EGrainSlotMode.UNUSED;
	}


	@Override
	public EGrainSlotMode getGrainModeBottom()
	{
		return EGrainSlotMode.REQUIRED;
	}


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.MATH_NOT;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.MATH;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc)
	{
		int valOut;

		int outVar = bottomGrain.getVariant();

		valOut = tre.getVariable(bottomGrain.getVariant());
		outVar = bottomGrain.getVariant();

		boolean bOut = valOut != 0;

		tre.setVariable(outVar, (!bOut) ? 1 : 0);

	}
}
