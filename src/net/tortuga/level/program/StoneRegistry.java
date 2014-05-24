package net.tortuga.level.program;


import java.util.LinkedHashMap;
import java.util.Map.Entry;

import net.tortuga.level.program.tiles.ProgTileStone;
import net.tortuga.level.program.tiles.stones.*;
import net.tortuga.util.Log;


/**
 * Stone program tile registry
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class StoneRegistry {

	private static LinkedHashMap<Integer, Class<? extends ProgTileStone>> map = new LinkedHashMap<Integer, Class<? extends ProgTileStone>>();


	/**
	 * Init stone registry
	 */
	public static void init()
	{
		// movement and rotation
		register(1, StoneGoForward.class);
		register(2, StoneGoBackward.class);
		register(3, StoneTurnLeft.class);
		register(4, StoneTurnRight.class);

		// world manipulation and conditional jumps
		register(101, StoneBoxTake.class);
		register(102, StoneBoxPlace.class);
		register(103, StoneLookFront.class);
		register(104, StoneLookDown.class);
		register(105, StoneLookInv.class);

		// labels and jumps
		register(201, StoneLabel.class);
		register(202, StoneGoto.class);
		register(203, StoneCall.class);
		register(204, StoneReturn.class);

		// special operations
		register(301, StoneBell.class);
		register(302, StoneSleep.class);

		// comparations
		register(401, StoneCompare.class);
		register(402, StoneCompareResultFull.class);
		register(403, StoneCompareResultSimple.class);

		// math operations
		register(501, StoneMathSet.class);
		register(502, StoneMathAdd.class);
		register(503, StoneMathSub.class);
		register(504, StoneMathMul.class);
		register(505, StoneMathDiv.class);
		register(506, StoneMathMod.class);
		register(507, StoneMathInc.class);
		register(508, StoneMathDecJz.class);
		register(509, StoneMathDecJnz.class);
		register(510, StoneMathAnd.class);
		register(511, StoneMathOr.class);
		register(512, StoneMathXor.class);
		register(513, StoneMathNot.class);
	}


	/**
	 * Register a program stone tile
	 * 
	 * @param index index (<1000)
	 * @param stoneClass stone class
	 */
	public static void register(int index, Class<? extends ProgTileStone> stoneClass)
	{
		if (map.containsKey(index)) {
			Log.w("Registered program tile " + stoneClass.getSimpleName() + " replaces " + map.get(index).getSimpleName() + " at index " + index);
		}

		if (map.containsValue(stoneClass)) {
			Log.w("Registering program tile " + stoneClass.getSimpleName() + " to index " + index + " as duplicate.");
		}

		map.put(index, stoneClass);
	}


	/**
	 * Get stone index from class
	 * 
	 * @param stoneClass stone class
	 * @return the index
	 */
	public static int getStoneIndex(Class<? extends ProgTileStone> stoneClass)
	{
		if (!map.containsValue(stoneClass)) {
			Log.w("Program tile " + stoneClass.getSimpleName() + " is not registered.");
		}

		for (Entry<Integer, Class<? extends ProgTileStone>> e : map.entrySet()) {
			if (e.getValue() == stoneClass) return e.getKey();
		}

		return -1;
	}


	/**
	 * Get instance of stone with given index
	 * 
	 * @param index stone index
	 * @return new stone instance
	 */
	public static ProgTileStone getStone(int index)
	{
		if (!map.containsKey(index)) {
			Log.w("There is no program tile registered at index " + index + ".");
		}
		try {
			return map.get(index).newInstance();
		} catch (Exception e) {
			Log.e("Could not instantiate program tile at " + index + ": " + map.get(index).getSimpleName());
			return null;
		}
	}
}
