package net.tortuga.level.map.tiles.blocks;


import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MapTx;
import net.tortuga.textures.TxQuad;


public class TileCobble extends MapTile {

	@Override
	public TxQuad getTexture()
	{
		return MapTx.T_COBBLE;
	}

}
