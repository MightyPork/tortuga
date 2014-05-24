package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.TurtleRuntimeException;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneReturn extends ProgTileStone {

	@Override
	public boolean acceptsGrainTop(EGrainType grainType)
	{
		return false;
	}


	@Override
	public boolean acceptsGrainBottom(EGrainType grainType)
	{
		return false;
	}


	@Override
	public EGrainSlotMode getGrainModeTop()
	{
		return EGrainSlotMode.UNUSED;
	}


	@Override
	public EGrainSlotMode getGrainModeBottom()
	{
		return EGrainSlotMode.UNUSED;
	}


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.RETURN;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.JUMPS;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc) throws TurtleRuntimeException
	{
		tre.popAddress();
	}
}
