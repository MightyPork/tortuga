package net.tortuga.level.map;


import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.util.Log;

import com.porcupine.coord.CoordI;


public class TileGridBuilder {

	private int[][][] map;


	public TileGridBuilder(CoordI size) {
		this(size.x, size.y, size.z);
	}


	public TileGridBuilder(int x, int y, int z) {
		map = new int[z][y][x];
	}


	public void setTile(CoordI coord, int id)
	{
		setTile(coord.x, coord.y, coord.z, id);
	}


	public void setTile(int x, int y, int z, int id)
	{
		try {
			map[z][y][x] = id;
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e("Coord [" + x + ";" + y + ";" + z + "] out of range in TurtleMapBuilder.", e);
		}
	}


	public int[][][] toIdGrid()
	{
		return map;
	}


	public MapTile[][][] toTileGrid()
	{
		return new TileGridDescriptor(map).buildTileGrid();
	}
}
