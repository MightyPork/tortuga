package net.tortuga.level.map.tiles.mechanisms;


import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MetaTile;


/**
 * Active switch
 * 
 * @author MightyPork
 */
public class TileSwitchOn extends MetaTile {

	@Override
	public MapTile createMapTile()
	{
		return new TileSwitch(true);
	}

}
