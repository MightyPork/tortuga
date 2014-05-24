package net.tortuga.level.map;


import java.io.InputStream;
import java.io.OutputStream;

import net.tortuga.CustomIonMarks;
import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MapTileRegistry;
import net.tortuga.util.IonCoordI;

import com.porcupine.ion.AbstractIonList;
import com.porcupine.ion.Ion;


/**
 * Bundle of level map tiles, ionizable integer array with dimensions.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TileGridDescriptor extends AbstractIonList<Integer> {

	/** Size of level map */
	public IonCoordI size = new IonCoordI();


	/**
	 * Level tile array, empty constructor
	 */
	public TileGridDescriptor() {}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.TILE_LIST;
	}


	/**
	 * Build from 3D array of IDs
	 * 
	 * @param tiles array of IDs (z,y,x)
	 */
	public TileGridDescriptor(int[][][] tiles) {
		size = new IonCoordI(tiles[0][0].length, tiles[0].length, tiles.length);

		for (int z = 0; z < tiles.length; z++) {
			for (int y = 0; y < tiles[0].length; y++) {
				for (int x = 0; x < tiles[0][0].length; x++) {
					add(tiles[z][y][x]);
				}
			}
		}

	}


	/**
	 * Convert to a 3D array of IDs
	 * 
	 * @return the array
	 */
	public int[][][] buildIdGrid()
	{
		int[][][] map = new int[size.z][size.y][size.x];

		int index = 0;
		for (int z = 0; z < size.z; z++) {
			for (int y = 0; y < size.y; y++) {
				for (int x = 0; x < size.x; x++) {
					map[z][y][x] = get(index);
					index++;
				}
			}
		}

		return map;
	}


	/**
	 * Build map tile array (real tiles and nulls)
	 * 
	 * @return tile array 3D
	 */
	public MapTile[][][] buildTileGrid()
	{
		MapTile[][][] map = new MapTile[size.z][size.y][size.x];

		try {
			int index = 0;
			for (int z = 0; z < size.z; z++) {
				for (int y = 0; y < size.y; y++) {
					for (int x = 0; x < size.x; x++) {
						map[z][y][x] = MapTileRegistry.getTile(get(index));
						index++;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not load turtle map.", e);
		}

		return map;
	}


	@Override
	public void ionWriteCustomData(OutputStream out)
	{
		Ion.toStream(out, size);
	}


	@Override
	public void ionReadCustomData(InputStream in)
	{
		size = (IonCoordI) Ion.fromStream(in);
	}

}
