package net.tortuga.level.program.tiles;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.TurtleRuntimeException;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public abstract class ProgTileStone extends ProgTileBase {

	public abstract boolean acceptsGrainTop(EGrainType grainType);


	public abstract boolean acceptsGrainBottom(EGrainType grainType);


	public abstract EGrainSlotMode getGrainModeTop();


	public abstract EGrainSlotMode getGrainModeBottom();


	public ProgTileStone() {}


	@Override
	public abstract CoordI getOverlayIndex();


	@Override
	public EProgTileType getProgTileType()
	{
		return EProgTileType.STONE;
	}


	@Override
	public abstract RGB getStoneColor();


	@Override
	public boolean isSingleInstance()
	{
		return false;
	}


	@Override
	public RGB getSubOverlayColor()
	{
		return RGB.WHITE;
	}


	@Override
	public CoordI getSubOverlayIndex()
	{
		return null;
	}


	/**
	 * Execute this command stone (in case of jump grain, perform the jump)
	 * 
	 * @param topGrain top grain argument
	 * @param bottomGrain bottom grain argument
	 * @param tre turtle runtime environment
	 * @param tc turtle controller with world access
	 * @throws TurtleRuntimeException
	 */
	public abstract void execute(ProgTileGrain topGrain, ProgTileGrain bottomGrain, TurtleRuntimeEnvironment tre, TurtleController tc) throws TurtleRuntimeException;

}
