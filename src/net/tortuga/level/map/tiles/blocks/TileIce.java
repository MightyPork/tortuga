package net.tortuga.level.map.tiles.blocks;


import net.tortuga.level.map.TurtleMap;
import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MapTx;
import net.tortuga.textures.TxQuad;

import com.porcupine.coord.CoordI;


public class TileIce extends MapTile {

	@Override
	public TxQuad getTexture()
	{
		return MapTx.T_ICE;
	}


	@Override
	public boolean isSlipperly(TurtleMap map, CoordI pos)
	{
		return true;
	}


	@Override
	public boolean isGlassy(TurtleMap map, CoordI pos)
	{
		return true;
	}
}
