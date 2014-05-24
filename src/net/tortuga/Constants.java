package net.tortuga;


import com.porcupine.coord.Coord;


/**
 * Sector constants
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SuppressWarnings("javadoc")
public class Constants {

	// STRINGS
	public static final int VERSION_NUMBER = 1;
	public static final String VERSION_NAME = "1.0";
	public static final String TITLEBAR = "Tortuga " + VERSION_NAME + " - MightyPork's programming game";

	// FILES+DIRS
	public static final String APP_DIR = "tortuga";

	public static final String DIR_SCREENSHOTS = "screenshots";

	public static final String FILE_CONFIG = "config.ini";
	public static final String FILE_LOG = "Runtime.log";
	public static final String FILE_LOG_E = "Error.log";

	// AUDIO
	public static final Coord LISTENER_POS = new Coord(0, 0, 0);

	// TIMING
	public static final int FPS_GUI = 40;
	//public static final double SPEED_MUL = (40D / FPS_GUI);

	public static final int FPS_RENDER = 200; // max

	// LOGGING GROUPS
	public static final boolean LOG_FONTS = false;
	public static final boolean LOG_TEXTURES = false;
	public static final boolean LOG_SOUNDS = true;
	public static final boolean LOG_XML_LOADING = false;

	// INITIAL WINDOW SIZE (later loaded from config file)
	public static final int WINDOW_SIZE_X = 1024;
	public static final int WINDOW_SIZE_Y = 768;
}
