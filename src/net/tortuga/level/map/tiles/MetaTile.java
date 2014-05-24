package net.tortuga.level.map.tiles;


import net.tortuga.textures.TxQuad;


/**
 * Meta tile used to build map tiles with special configurations. Is used in
 * MapTileRegistry with separate ID.
 * 
 * @author MightyPork
 */
public abstract class MetaTile extends MapTile {

	@Override
	public TxQuad getTexture()
	{
		return null;
	}


	/**
	 * Create the right map tile
	 * 
	 * @return the wrapped tile
	 */
	public abstract MapTile createMapTile();

}
