package net.tortuga.level;


import net.tortuga.level.map.TurtleMap;
import net.tortuga.level.map.TurtleMapDescriptor;
import net.tortuga.level.map.entities.mobile.ETurtle;
import net.tortuga.level.program.GrainList;
import net.tortuga.level.program.StoneList;


/**
 * Level bundle
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class LevelBundle { // TODO make Ionizable

	/** Program length */
	public int progLength = 0;

	/** Number of labels allowed */
	public int progLabels = 0;

	/** Number of variables allowed */
	public int progVars = 0;

	/** List of grains allowed (except "GotoLabel" and "Var") */
	public GrainList progGrains = new GrainList();
	/** List of stones enabled (except "Label") */
	public StoneList progStones = new StoneList();

	/** Turtle map */
	public TurtleMapDescriptor mapDescriptor;

	/** Built map, used to start a level */
	private TurtleMap builtMap;


	/**
	 * Get the built map
	 * 
	 * @return map
	 */
	public TurtleMap getBuiltMap()
	{
		if (builtMap == null) buildMap();
		return builtMap;
	}


	/**
	 * Build map
	 */
	public void buildMap()
	{
		builtMap = mapDescriptor.buildMap(ETurtle.DEFAULT);
	}

	// TODO add map data and records to beat
}
