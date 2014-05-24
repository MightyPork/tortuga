package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneSleep extends ProgTileStone {

	@Override
	public boolean acceptsGrainTop(EGrainType grainType)
	{
		return grainType == EGrainType.NUMBER || grainType == EGrainType.VAR;
	}


	@Override
	public boolean acceptsGrainBottom(EGrainType grainType)
	{
		return false;
	}


	@Override
	public EGrainSlotMode getGrainModeTop()
	{
		return EGrainSlotMode.OPTIONAL;
	}


	@Override
	public EGrainSlotMode getGrainModeBottom()
	{
		return EGrainSlotMode.UNUSED;
	}


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.SLEEP;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.SPECIAL;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc)
	{
		int secs = 1;

		if (topGrain.getGrainType() == EGrainType.NUMBER) secs = topGrain.getExtraNumber();
		if (topGrain.getGrainType() == EGrainType.VAR) secs = tre.getVariable(topGrain.getVariant());

		tre.addSleep(secs);

	}
}
