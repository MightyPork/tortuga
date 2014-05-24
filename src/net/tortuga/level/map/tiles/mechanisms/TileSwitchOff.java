package net.tortuga.level.map.tiles.mechanisms;


import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MetaTile;


/**
 * Inactive switch
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TileSwitchOff extends MetaTile {

	@Override
	public MapTile createMapTile()
	{
		return new TileSwitch(false);
	}

}
