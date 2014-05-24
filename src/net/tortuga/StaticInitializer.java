package net.tortuga;


import net.tortuga.gui.widgets.ColorScheme;
import net.tortuga.level.map.entities.EntityRegistry;
import net.tortuga.level.map.tiles.MapTileRegistry;
import net.tortuga.level.program.GrainRegistry;
import net.tortuga.level.program.StoneRegistry;


/**
 * Initialization utility, initializing all the static stuff that is needed
 * before starting main loop.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class StaticInitializer {

	/**
	 * Init static things and start threads.<br>
	 * This is called on startup, even before the splash screen.
	 */
	public static void initOnStartup()
	{
		CustomIonMarks.init();

		ColorScheme.init();
		GrainRegistry.init();
		StoneRegistry.init();
		MapTileRegistry.init();
		EntityRegistry.init();
	}


	/**
	 * Initialize all.
	 */
	public static void initPostLoad()
	{}

}
