package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneCompare extends ProgTileStone {

	@Override
	public boolean acceptsGrainTop(EGrainType grainType)
	{
		return grainType == EGrainType.NUMBER || grainType == EGrainType.VAR;
	}


	@Override
	public boolean acceptsGrainBottom(EGrainType grainType)
	{
		return grainType == EGrainType.NUMBER || grainType == EGrainType.VAR;
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
		return StoneTx.COMPARE;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.TESTS;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc)
	{
		int val1;

		if (topGrain.getGrainType() == EGrainType.VAR) {
			val1 = tre.getVariable(topGrain.getVariant());
		} else {
			val1 = topGrain.getExtraNumber();
		}

		int val2;

		if (bottomGrain.getGrainType() == EGrainType.VAR) {
			val2 = tre.getVariable(bottomGrain.getVariant());
		} else {
			val2 = bottomGrain.getExtraNumber();
		}

		tre.compareTop = val1;
		tre.compareBottom = val2;

	}
}
