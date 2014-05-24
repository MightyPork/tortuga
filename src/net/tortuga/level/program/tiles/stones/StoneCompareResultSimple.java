package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.TurtleRuntimeException;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneCompareResultSimple extends ProgTileStone {

	@Override
	public boolean acceptsGrainTop(EGrainType grainType)
	{
		return grainType == EGrainType.LABEL;
	}


	@Override
	public boolean acceptsGrainBottom(EGrainType grainType)
	{
		return grainType == EGrainType.LABEL;
	}


	@Override
	public EGrainSlotMode getGrainModeTop()
	{
		return EGrainSlotMode.OPTIONAL;
	}


	@Override
	public EGrainSlotMode getGrainModeBottom()
	{
		return EGrainSlotMode.OPTIONAL;
	}


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.COMPARE_RESULT_EQUAL_UNEQUAL;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.TESTS;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc) throws TurtleRuntimeException
	{
		if (!(tre.lastStone.getStone() instanceof StoneCompare)) {
			throw new TurtleRuntimeException("Result block not after compare.");
		}

		if (tre.compareTop == tre.compareBottom) {
			if (topGrain != null) topGrain.doJump(tre);
			return;
		}

		if (tre.compareTop != tre.compareBottom) {
			if (bottomGrain != null) bottomGrain.doJump(tre);
			return;
		}

	}
}
