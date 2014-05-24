package net.tortuga.level.map;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import net.tortuga.CustomIonMarks;
import net.tortuga.level.map.entities.EntityDescriptor;
import net.tortuga.level.map.entities.TurtleDescriptor;
import net.tortuga.level.map.entities.mobile.ETurtle;
import net.tortuga.util.IonCoordI;

import com.porcupine.coord.CoordI;
import com.porcupine.ion.Ion;
import com.porcupine.ion.Ionizable;


/**
 * Level map descriptor (including entities, tiles and goal mark)
 * 
 * @author MightyPork
 */
public class TurtleMapDescriptor implements Ionizable {

	private TurtleDescriptor turtle = null;
	private IonCoordI goal = new IonCoordI();
	private boolean hasGoal = false;
	private TileGridDescriptor mapTiles = null;
	private EntityDescriptorList entities = null;


	/**
	 * Level map descriptor - empty constructor
	 */
	public TurtleMapDescriptor() {}


	/**
	 * Level map descriptor
	 * 
	 * @param turtle tirtle coord
	 * @param goal goal coord
	 * @param hasGoal flag if goal tile should be shown
	 * @param mapTiles 3D array of map tiles
	 * @param entities list of entity descriptors
	 */
	public TurtleMapDescriptor(TurtleDescriptor turtle, CoordI goal, boolean hasGoal, int[][][] mapTiles, List<EntityDescriptor> entities) {
		this.turtle = turtle;
		if (goal == null) {
			goal = new IonCoordI();
			hasGoal = false;
		}
		this.goal = new IonCoordI(goal);
		this.hasGoal = hasGoal;
		this.mapTiles = new TileGridDescriptor(mapTiles);
		this.entities = new EntityDescriptorList();
		this.entities.addAll(entities);
	}


	@Override
	public void ionRead(InputStream in) throws IOException
	{
		turtle = (TurtleDescriptor) Ion.fromStream(in);
		goal = (IonCoordI) Ion.fromStream(in);
		hasGoal = (Boolean) Ion.fromStream(in);
		mapTiles = (TileGridDescriptor) Ion.fromStream(in);
		entities = (EntityDescriptorList) Ion.fromStream(in);
	}


	@Override
	public void ionWrite(OutputStream out) throws IOException
	{
		Ion.toStream(out, turtle);
		Ion.toStream(out, goal);
		Ion.toStream(out, hasGoal);
		Ion.toStream(out, mapTiles);
		Ion.toStream(out, entities);
	}


	/**
	 * Convert to a level map
	 * 
	 * @param turtleTheme
	 * @return the map
	 */
	public TurtleMap buildMap(ETurtle turtleTheme)
	{
		TurtleMap map = new TurtleMap(mapTiles.buildTileGrid());

		map.setTurtle(turtle.getDir(), turtleTheme, turtle.getPos());

		map.setGoal(hasGoal ? goal : null);

		for (EntityDescriptor e : entities) {
			map.addEntity(e.getEntity());
		}

		return map;
	}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.MAP_DESCRIPTOR;
	}

}
