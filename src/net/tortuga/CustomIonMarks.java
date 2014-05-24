package net.tortuga;


import net.tortuga.level.LevelPars;
import net.tortuga.level.map.EntityDescriptorList;
import net.tortuga.level.map.TileGridDescriptor;
import net.tortuga.level.map.TurtleMapDescriptor;
import net.tortuga.level.map.entities.EntityDescriptor;
import net.tortuga.level.map.entities.TurtleDescriptor;
import net.tortuga.level.program.GrainList;
import net.tortuga.level.program.StoneList;
import net.tortuga.util.IonCoordI;

import com.porcupine.ion.Ion;


/**
 * Class adding ION marks for custom ionizable objects
 * 
 * @author MightyPork
 */
@SuppressWarnings("javadoc")
public class CustomIonMarks {

	// low level things
	public static final byte COORD = 50;

	// level, map, turtle, entities
	public static final byte MAP_DESCRIPTOR = 100;
	public static final byte TURTLE_DESCRIPTOR = 101;
	public static final byte ENTITY_DESCRIPTOR = 102;
	public static final byte TILE_LIST = 103;
	public static final byte ENTITY_LIST = 104;
	public static final byte PGM_GRAIN_LIST = 105;
	public static final byte PGM_STONE_LIST = 106;
	public static final byte LEVEL_PARS = 107;


//	public static final byte LEVEL = 105;
//	
//	public static final byte SAVED_PROGRAM = 106;

	/**
	 * Register ion marks
	 */
	public static void init()
	{
		Ion.registerIonizable(COORD, IonCoordI.class);
		Ion.registerIonizable(MAP_DESCRIPTOR, TurtleMapDescriptor.class);
		Ion.registerIonizable(TURTLE_DESCRIPTOR, TurtleDescriptor.class);
		Ion.registerIonizable(ENTITY_DESCRIPTOR, EntityDescriptor.class);
		Ion.registerIonizable(TILE_LIST, TileGridDescriptor.class);
		Ion.registerIonizable(ENTITY_LIST, EntityDescriptorList.class);
		Ion.registerIonizable(PGM_GRAIN_LIST, GrainList.class);
		Ion.registerIonizable(PGM_STONE_LIST, StoneList.class);
		Ion.registerIonizable(LEVEL_PARS, LevelPars.class);
	}

}
