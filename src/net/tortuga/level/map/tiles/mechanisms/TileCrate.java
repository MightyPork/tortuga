package net.tortuga.level.map.tiles.mechanisms;


import net.tortuga.level.map.TurtleMap;
import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MapTx;
import net.tortuga.textures.TxQuad;

import com.porcupine.coord.CoordI;


public class TileCrate extends MapTile {

	@Override
	public TxQuad getTexture()
	{
		return MapTx.T_CRATE;
	}


	@Override
	public boolean canBePushed(TurtleMap map, CoordI tilePos)
	{
		return true;
	}
}
