package net.tortuga;


import net.tortuga.fonts.Fonts;
import net.tortuga.sounds.SoundManager;
import net.tortuga.textures.Textures;
import net.tortuga.util.Log;


/**
 * Class responsible for resource loading.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class LoadingManager {

	private static final int groups = 3;

	private static int lastloaded = -1;
	private static long beginTime;


	private static void timerStart()
	{
		beginTime = System.currentTimeMillis();
	}


	private static double timerGet()
	{
		return (System.currentTimeMillis() - beginTime) / 1000D;
	}


	/**
	 * Load resources needed to animate splash
	 */
	public static void loadForSplash()
	{
		Log.f1("Loading resources needed for Splash screen.");

		timerStart();
		Fonts.loadForSplash();
		Log.i("LOADING: Fonts for Splash loaded in " + timerGet() + "s");

		timerStart();
		SoundManager.loadForSplash();
		Log.i("LOADING: Sounds for Splash loaded in " + timerGet() + "s");

		timerStart();
		Textures.loadForSplash();
		Log.i("LOADING: Textures for Splash loaded in " + timerGet() + "s");
	}


	/**
	 * Get info text for resource group (eg. Loading sounds...)
	 * 
	 * @return text
	 */
	public static String getSplashInfo()
	{
		switch (lastloaded + 1) {
			case 0:
				return "Loading fonts...";
			case 1:
				return "Loading textures...";
			case 2:
				return "Loading models...";
			case 3:
				return "Loading sounds...";
		}
		return "Loading...";
	}


	/**
	 * Load next resource group
	 */
	public static void loadGroup()
	{
		switch (lastloaded + 1) {
			case 0:
				timerStart();
				Fonts.load();
				Log.i("LOADING: Fonts loaded in " + timerGet() + "s");
				break;

			case 1:
				timerStart();
				Textures.load();
				Log.i("LOADING: Textures loaded in " + timerGet() + "s");
				break;

			case 2:
				timerStart();
				SoundManager.load();
				Log.i("LOADING: Sounds loaded in " + timerGet() + "s");
				break;
		}

		lastloaded++;
	}


	/**
	 * Check if has more resource groups to load
	 * 
	 * @return has more
	 */
	public static boolean hasMoreGroups()
	{
		return lastloaded < groups;
	}


	/**
	 * Called after all resources have been loaded.
	 */
	public static void onResourcesLoaded()
	{
		Log.i("LOADING: All resources loaded.");

		StaticInitializer.initPostLoad();
	}
}
