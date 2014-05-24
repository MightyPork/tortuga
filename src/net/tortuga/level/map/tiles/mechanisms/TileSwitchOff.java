package net.tortuga.level.map.tiles.mechanisms;


import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MetaTile;


/**
 * Inactive switch
 * 
 * @author MightyPork
 */
public class TileSwitchOff extends MetaTile {

	@Override
	public MapTile createMapTile()
	{
		return new TileSwitch(false);
	}

}
