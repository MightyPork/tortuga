package net.tortuga.sounds;


import java.util.HashMap;
import java.util.Map;

import net.tortuga.App;
import net.tortuga.EWeather;


/**
 * Audio loop manager
 * 
 * @author MightyPork
 */
public class Loops {

	private static boolean ready;

	private static Map<String, LoopPlayer> loops = new HashMap<String, LoopPlayer>();


	/**
	 * Init the loops
	 */
	public static void init()
	{
		loops.put("ambient.water", new LoopPlayer(SoundManager.amb_water, 1f, 0.05f, SoundManager.volumeAmbients));
		loops.put("weather.rain", new LoopPlayer(SoundManager.amb_rain, 1f, 0.2f, SoundManager.volumeAmbients));
		loops.put("ambient.wilderness", new LoopPlayer(SoundManager.amb_wilderness, 1f, 0.08f, SoundManager.volumeAmbients));

		loops.put("block.move", new LoopPlayer(SoundManager.loop_crate_move, 1f, 0.13f, SoundManager.volumeEffects));
		loops.put("turtle.move", new LoopPlayer(SoundManager.loop_turtle_move, 1f, 0.12f, SoundManager.volumeEffects));

		ready = true;
	}


	private static LoopPlayer getPlayer(String resource)
	{
		LoopPlayer pl = loops.get(resource);
		if (pl == null) {
			throw new IllegalArgumentException("Unknown sound loop: \"" + resource + "\"");
		}
		return pl;
	}


	/**
	 * Start playing a loop with fade-in
	 * 
	 * @param resource resource ID
	 * @param fadeIn fade in time (secs)
	 */
	public static void start(String resource, double fadeIn)
	{
		getPlayer(resource).fadeIn(fadeIn);
	}


	/**
	 * Stop playing a loop with fade-out
	 * 
	 * @param resource resource ID
	 * @param fadeOut fade out time (secs)
	 */
	public static void stop(String resource, double fadeOut)
	{
		getPlayer(resource).fadeOut(fadeOut);
	}


	/**
	 * Play in-game ambient loops
	 */
	public static void playIngame()
	{
		start("ambient.water", 1);
	}


	/**
	 * Play main menu loops
	 */
	public static void playMenu()
	{
		start("ambient.wilderness", 1);
		// TODO stop intro

		stop("ambient.water", 2);
		stop("turtle.move", 0.5);
		stop("block.move", 0.5);

		if (App.weather == EWeather.RAIN) {
			getPlayer("weather.rain").fadeIn(1);
		}
	}


	/**
	 * Play intro effect
	 */
	public static void playIntro()
	{
		// TODO play intro		
	}


	/**
	 * Update players (fading in/out)
	 * 
	 * @param delta delta time
	 */
	public static void update(double delta)
	{
		if (!ready) return;
		for (LoopPlayer player : loops.values()) {
			player.update(delta);
		}
	}

}
