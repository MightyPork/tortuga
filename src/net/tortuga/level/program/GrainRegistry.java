package net.tortuga.level.program;


import java.util.LinkedHashMap;
import java.util.Map.Entry;

import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.level.program.tiles.grains.*;
import net.tortuga.util.Log;


/**
 * Grain program tile registry
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class GrainRegistry {

	private static LinkedHashMap<Integer, Class<? extends ProgTileGrain>> map = new LinkedHashMap<Integer, Class<? extends ProgTileGrain>>();


	/**
	 * Init grain registry
	 */
	public static void init()
	{
		// Values & variables
		register(1001, GrainNumber.class);
		register(1002, GrainVariable.class);

		// Jumps
		register(1101, GrainJumpLabel.class);
		register(1102, GrainJumpStart.class);
		register(1103, GrainJumpEnd.class);
		register(1104, GrainJumpSkipOne.class);
		register(1105, GrainJumpAbsolute.class);
		register(1106, GrainJumpRelative.class);
	}


	/**
	 * Register an attribute tile
	 * 
	 * @param index index (>1000)
	 * @param grainClass attribute class
	 */
	public static void register(int index, Class<? extends ProgTileGrain> grainClass)
	{
		if (map.containsKey(index)) {
			Log.w("Registered attribute tile " + grainClass.getSimpleName() + " replaces " + map.get(index).getSimpleName() + " at index " + index);
		}

		if (map.containsValue(grainClass)) {
			Log.w("Registering attribute tile " + grainClass.getSimpleName() + " to index " + index + " as duplicate.");
		}

		map.put(index, grainClass);
	}


	/**
	 * Get grain index from class
	 * 
	 * @param grainClass grain class
	 * @return the index
	 */
	public static int getGrainIndex(Class<? extends ProgTileGrain> grainClass)
	{
		if (!map.containsValue(grainClass)) {
			Log.w("Attribute tile " + grainClass.getSimpleName() + " is not registered.");
		}

		for (Entry<Integer, Class<? extends ProgTileGrain>> e : map.entrySet()) {
			if (e.getValue() == grainClass) return e.getKey();
		}

		return -1;
	}


	/**
	 * Get instance of grain with given index
	 * 
	 * @param index grain index
	 * @return new grain instance
	 */
	public static ProgTileGrain getGrain(int index)
	{
		if (!map.containsKey(index)) {
			Log.w("There is no attribute tile registered at index " + index + ".");
		}
		try {
			return map.get(index).newInstance();
		} catch (Exception e) {
			Log.e("Could not instantiate attribute tile at " + index + ": " + map.get(index).getSimpleName());
			return null;
		}
	}
}
