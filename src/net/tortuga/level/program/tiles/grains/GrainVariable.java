package net.tortuga.level.program.tiles.grains;


import net.tortuga.level.program.tiles.EGrainType;
import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.level.program.tiles.StoneColors;
import net.tortuga.level.program.tiles.StoneTx;

import com.porcupine.color.RGB;
import com.porcupine.coord.CoordI;


public class GrainVariable extends ProgTileGrain {

	private int number = 0;


	@Override
	public CoordI getOverlayIndex()
	{
		return StoneTx.GRAIN_LETTERS[variant];
	}


	@Override
	public RGB getStoneColor()
	{
		return StoneColors.GRAIN_VAR;
	}


	@Override
	public EGrainType getGrainType()
	{
		return EGrainType.VAR;
	}

}
