package net.tortuga.level.program.tiles.stones;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.program.tiles.*;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class StoneLabel extends ProgTileStone {

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
		return StoneTx.LABEL;
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.GRAIN_LABELS;
	}


	@Override
	public CoordI getSubOverlayIndex()
	{
		return StoneTx.LABEL_FILL;
	}


	@Override
	public RGB getSubOverlayColor()
	{
		return StoneColors.LABEL_COLORS[variant];
	}


	@Override
	public boolean isSingleInstance()
	{
		return true;
	}


	@Override
	public void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc)
	{}
}
