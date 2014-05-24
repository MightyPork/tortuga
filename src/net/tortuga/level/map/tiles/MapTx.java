package net.tortuga.level.map.tiles;


import net.tortuga.textures.Textures;
import net.tortuga.textures.TxQuad;


/**
 * Textures for map tiles
 * 
 * @author MightyPork
 */
@SuppressWarnings("javadoc")
// irrelevant
public class MapTx {

	// nothing
	public static TxQuad T_NONE = null; // XXX temporary

	// tiles
	public static TxQuad T_WHITE;
	public static TxQuad T_WOOD;
	public static TxQuad T_ICE;
	public static TxQuad T_COBBLE;
	public static TxQuad T_DIRT;
	public static TxQuad T_LEGO_RED;
	public static TxQuad T_LEGO_BLUE;
	public static TxQuad T_CRATE;
	public static TxQuad T_PEEBLES;

	// layers
	public static TxQuad L_GOAL;
	public static TxQuad L_GOAL_LOCK;
	public static TxQuad L_ELEV_BASE;
	public static TxQuad L_ELEV_BASE_OVER;
	public static TxQuad L_ELEV_PLATFORM;
	public static TxQuad L_SWITCH_OFF;
	public static TxQuad L_SWITCH_ON;

	// shadows
	public static TxQuad SH_SIDE_SIDE;
	public static TxQuad SH_SIDE_BOTTOM;
	public static TxQuad SH_TOP_SIDE;
	public static TxQuad SH_TOP_CORNER;
	public static TxQuad SH_ABOVE_INNER;
	public static TxQuad SH_ABOVE_LEFT;
	public static TxQuad SH_ABOVE_RIGHT;
	public static TxQuad SH_ABOVE_BOTH;


	/**
	 * Load
	 */
	public static void init()
	{
		// tiles

		// tiles: row1
		T_WHITE = TxQuad.fromSize(Textures.map_tiles, 0, 0, 64, 96);
		T_WOOD = TxQuad.fromSize(Textures.map_tiles, 64, 0, 64, 96);
		T_ICE = TxQuad.fromSize(Textures.map_tiles, 64 * 2, 0, 64, 96);
		T_COBBLE = TxQuad.fromSize(Textures.map_tiles, 64 * 3, 0, 64, 96);
		T_DIRT = TxQuad.fromSize(Textures.map_tiles, 64 * 4, 0, 64, 96);
		T_LEGO_RED = TxQuad.fromSize(Textures.map_tiles, 64 * 5, 0, 64, 96);
		T_LEGO_BLUE = TxQuad.fromSize(Textures.map_tiles, 64 * 6, 0, 64, 96);
		T_CRATE = TxQuad.fromSize(Textures.map_tiles, 64 * 7, 0, 64, 96);
		// tiles: row2
		T_PEEBLES = TxQuad.fromSize(Textures.map_tiles, 0, 96, 64, 96);

		// 64x64 nothing
		T_NONE = TxQuad.fromSize(Textures.map_tiles, 0, 64 * 7, 64, 64);

		// labels - non-block tiles
		L_GOAL = TxQuad.fromSize(Textures.map_tiles, 64 * 7, 64 * 7, 64, 64);
		L_GOAL_LOCK = TxQuad.fromSize(Textures.map_tiles, 64 * 7, 64 * 6, 64, 64);
		L_ELEV_BASE = TxQuad.fromSize(Textures.map_tiles, 64 * 6, 64 * 7, 64, 64);
		L_ELEV_BASE_OVER = TxQuad.fromSize(Textures.map_tiles, 64 * 5, 64 * 7, 64, 64);
		L_ELEV_PLATFORM = TxQuad.fromSize(Textures.map_tiles, 64 * 4, 64 * 7, 64, 64);
		L_SWITCH_OFF = TxQuad.fromSize(Textures.map_tiles, 64 * 3, 64 * 7, 64, 64);
		L_SWITCH_ON = TxQuad.fromSize(Textures.map_tiles, 64 * 2, 64 * 7, 64, 64);

		// shading of tiles
		SH_SIDE_SIDE = TxQuad.fromSize(Textures.map_shading, 0, 0, 64, 32);
		SH_SIDE_BOTTOM = TxQuad.fromSize(Textures.map_shading, 64, 0, 64, 32);
		SH_TOP_CORNER = TxQuad.fromSize(Textures.map_shading, 0, 32, 64, 64);
		SH_TOP_SIDE = TxQuad.fromSize(Textures.map_shading, 64, 32, 64, 64);

		SH_ABOVE_INNER = TxQuad.fromSize(Textures.map_shading, 0, 96, 64, 32);
		SH_ABOVE_LEFT = TxQuad.fromSize(Textures.map_shading, 64, 96, 64, 32);
		SH_ABOVE_RIGHT = TxQuad.fromSize(Textures.map_shading, 0, 128, 64, 32);
		SH_ABOVE_BOTH = TxQuad.fromSize(Textures.map_shading, 64, 128, 64, 32);
	}

}
