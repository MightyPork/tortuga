package net.tortuga.level.map.tiles;


import java.util.LinkedHashMap;
import java.util.Map.Entry;

import net.tortuga.level.map.tiles.blocks.*;
import net.tortuga.level.map.tiles.mechanisms.TileCrate;
import net.tortuga.level.map.tiles.mechanisms.TileElevator;
import net.tortuga.level.map.tiles.mechanisms.TileSwitchOff;
import net.tortuga.level.map.tiles.mechanisms.TileSwitchOn;
import net.tortuga.util.Log;


/**
 * Map tile registry
 * 
 * @author MightyPork
 */
public class MapTileRegistry {

	private static LinkedHashMap<Integer, Class<? extends MapTile>> map = new LinkedHashMap<Integer, Class<? extends MapTile>>();


	/**
	 * Init map tile registry
	 */
	public static void init()
	{
		// colors

		// natural stuff
		register(1, TileCobble.class);
		register(2, TileDirt.class);
		register(3, TilePeebles.class);
		register(4, TileWood.class);
		register(5, TileIce.class);
		register(6, TileLegoRed.class);
		register(7, TileLegoBlue.class);

		// rainbow colors
		register(100, TileWhite.class);

		// pushable, active etc.
		register(200, TileCrate.class);
		register(201, TileElevator.class);
		register(202, TileSwitchOff.class);
		register(203, TileSwitchOn.class);
	}


	/**
	 * Register a map tile
	 * 
	 * @param index index
	 * @param tileClass tile class
	 */
	public static void register(int index, Class<? extends MapTile> tileClass)
	{
		if (map.containsKey(index)) {
			Log.w("Registered map tile " + tileClass.getSimpleName() + " replaces " + map.get(index).getSimpleName() + " at index " + index);
		}

		if (map.containsValue(tileClass)) {
			Log.w("Registering map tile " + tileClass.getSimpleName() + " to index " + index + " as duplicate.");
		}

		map.put(index, tileClass);
	}


	/**
	 * Get map tile index from class
	 * 
	 * @param tileClass tile class
	 * @return the index
	 */
	public static int getTileIndex(Class<? extends MapTile> tileClass)
	{
		if (!map.containsValue(tileClass)) {
			Log.w("Map tile " + tileClass.getSimpleName() + " is not registered.");
		}

		for (Entry<Integer, Class<? extends MapTile>> e : map.entrySet()) {
			if (e.getValue() == tileClass) return e.getKey();
		}

		return -1;
	}


	/**
	 * Convert meta tile to real tile
	 * 
	 * @param tile checked tile
	 * @return output tile for Map
	 */
	private static MapTile processMetaTile(MapTile tile)
	{
		if (tile instanceof MetaTile) {
			return ((MetaTile) tile).createMapTile();
		}

		return tile;
	}


	/**
	 * Get instance of map tile with given index
	 * 
	 * @param index tile index
	 * @return new tile instance
	 */
	public static MapTile getTile(int index)
	{
		if (index == 0) return null;
		if (!map.containsKey(index)) {
			Log.w("There is no map tile registered at index " + index + ".");
		}
		try {
			return processMetaTile(map.get(index).newInstance());
		} catch (Exception e) {
			Log.e("Could not instantiate map tile at " + index + ": " + map.get(index).getSimpleName());
			return null;
		}
	}
}
