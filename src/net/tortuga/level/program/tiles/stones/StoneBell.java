package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.program.tiles.*;
import net.tortuga.sounds.Effects;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneBell extends ProgTileStone {

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
		return StoneTx.BELL;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.SPECIAL;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc)
	{
		Effects.play("turtle.bell");
		tre.addSleep(1);

	}
}
