package net.tortuga.sounds;


import java.nio.FloatBuffer;
import java.util.Random;

import net.tortuga.Constants;
import net.tortuga.util.Log;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.SoundStore;

import com.porcupine.coord.Coord;
import com.porcupine.math.Calc.Buffers;
import com.porcupine.mutable.MFloat;


/**
 * Preloaded sounds.
 * 
 * @author MightyPork
 */
@SuppressWarnings({ "javadoc", "unused" })
public class SoundManager {

	/** Volume of GUI buttons and clicks */
	public static MFloat volumeGui = new MFloat(1F);

	/** Volume of ingame sound effects */
	public static MFloat volumeEffects = new MFloat(1F);

	/** Volume of ambients (music & weather) */
	public static MFloat volumeAmbients = new MFloat(1F);

	/** Volume of water */
	public static MFloat volumeWater = new MFloat(1F);

	protected static AudioX click_button1;
	protected static AudioX click_button2;
	protected static AudioX click_typewriter1;
	protected static AudioX click_typewriter2;
	protected static AudioX click_switch;
	protected static AudioX click_open;
	protected static AudioX click_rattle;
	protected static AudioX click_tray;

	protected static AudioX gui_popup;
	protected static AudioX gui_shutter;
	protected static AudioX gui_gear1;
	protected static AudioX gui_gear2;

	protected static AudioX gui_error;
	protected static AudioX turtle_bell;
	protected static AudioX turtle_eat;
	protected static AudioX splash;

	protected static AudioX amb_water;
	protected static AudioX amb_rain;
	protected static AudioX amb_wilderness;

	protected static AudioX loop_crate_move;
	protected static AudioX loop_turtle_move;
	protected static AudioX crate_drop;
	protected static AudioX turtle_drop;
	protected static AudioX break_fruit;

/*	protected static AudioX musIntro;
	protected static AudioX musIngameLoop;
	protected static AudioX musMenuLoop;
	protected static AudioX musDesignerLoop;*/

	private static final String DIR_EFFECTS = "res/sounds/effects/";
	private static final String DIR_MUSIC = "res/sounds/music/";
	private static final String DIR_LOOPS = "res/sounds/loops/";

	public static SoundStore player = SoundStore.get();


	public static void loadForSplash()
	{
		gui_shutter = loadSound(DIR_EFFECTS + "shutter.ogg");
	}


	/**
	 * Load sounds
	 */
	public static void load()
	{
		click_button1 = loadSound(DIR_EFFECTS + "click_button1.ogg");
		click_button2 = loadSound(DIR_EFFECTS + "click_button2.ogg");
		click_typewriter1 = loadSound(DIR_EFFECTS + "click_typewriter1.ogg");
		click_typewriter2 = loadSound(DIR_EFFECTS + "click_typewriter2.ogg");
		click_switch = loadSound(DIR_EFFECTS + "click_switch.ogg");
		click_open = loadSound(DIR_EFFECTS + "click_open.ogg");
		click_rattle = loadSound(DIR_EFFECTS + "click_rattle.ogg");
		click_tray = loadSound(DIR_EFFECTS + "click_tray.ogg");

		gui_popup = loadSound(DIR_EFFECTS + "popup.ogg");
		gui_gear1 = loadSound(DIR_EFFECTS + "gear1.ogg");
		gui_gear2 = loadSound(DIR_EFFECTS + "gear2.ogg");

		turtle_bell = loadSound(DIR_EFFECTS + "bell.ogg");
		gui_error = loadSound(DIR_EFFECTS + "error.ogg");
		turtle_eat = loadSound(DIR_EFFECTS + "eat.ogg");
		splash = loadSound(DIR_EFFECTS + "splash.ogg");

		amb_water = loadSound(DIR_LOOPS + "stream.ogg");
		amb_rain = loadSound(DIR_LOOPS + "rain.ogg");
		amb_wilderness = loadSound(DIR_LOOPS + "wilderness.ogg");

		loop_crate_move = loadSound(DIR_LOOPS + "crate_move.ogg");
		loop_turtle_move = loadSound(DIR_LOOPS + "turtle_walking.ogg");
		crate_drop = loadSound(DIR_EFFECTS + "block_drop.ogg");
		turtle_drop = loadSound(DIR_EFFECTS + "turtle_drop.ogg");
		break_fruit = loadSound(DIR_EFFECTS + "break_fruit.ogg");

		Loops.init();
		Effects.init();
	}

	public static Coord listener = new Coord();
	private static Random rand = new Random();


	/**
	 * Set listener pos
	 * 
	 * @param pos
	 */
	public static void setListener(Coord pos)
	{
		listener.setTo(pos);
		FloatBuffer buf3 = Buffers.alloc(3);
		FloatBuffer buf6 = Buffers.alloc(6);
		buf3.clear();
		Buffers.fill(buf3, (float) pos.x, (float) pos.y, (float) pos.z);
		AL10.alListener(AL10.AL_POSITION, buf3);

		buf3.clear();
		Buffers.fill(buf3, 0, 0, 0);
		AL10.alListener(AL10.AL_VELOCITY, buf3);

		buf6.clear();
		Buffers.fill(buf6, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
		AL10.alListener(AL10.AL_ORIENTATION, buf6);

		buf3 = buf6 = null;
	}


	/**
	 * load one sound
	 * 
	 * @param path file path
	 * @return the sound
	 */
	private static AudioX loadSound(String path)
	{
		try {
			String ext = path.substring(path.length() - 3).toLowerCase();
			AudioX audio = null;
			if (ext.equals("ogg")) {
				audio = new AudioX(player.getOgg(path));
			}
			if (ext.equals("wav")) {
				audio = new AudioX(player.getWAV(path));
			}
			if (ext.equals("aif")) {
				audio = new AudioX(player.getAIF(path));
			}
			if (ext.equals("mod")) {
				audio = new AudioX(player.getMOD(path));
			}
			if (Constants.LOG_SOUNDS) Log.f2("Sound " + path + " loaded.");
			return audio;
		} catch (Exception e) {
			Log.e("ERROR WHILE LOADING: " + path);
			throw new RuntimeException(e);
		}
	}


	public static void update(double delta)
	{
		Loops.update(delta);
	}
}
