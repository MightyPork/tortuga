package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneLookInv extends ProgTileStone {

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
		return StoneTx.LOOK_INV;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.TESTS;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc)
	{
		// FIXME implement Look Inv
	}
}
