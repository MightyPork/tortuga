package net.tortuga.util;


import java.io.File;

import net.tortuga.Constants;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Vec;
import com.porcupine.math.Calc.Rad;
import com.porcupine.math.Polar;
import com.porcupine.util.FileUtils;


/**
 * Sector's utils class
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Utils {

	private static File gameDir;


	/**
	 * 2D angle of observer to point
	 * 
	 * @param observer point of observer
	 * @param point point of target
	 * @param lookVec look vector of observer
	 * @return angle
	 */
	public static double observerAngleToCoord(Coord observer, Coord point, Vec lookVec)
	{
		Vec dist = observer.vecTo(point);

		Polar point_p = Polar.fromCoord(dist.x, dist.z);
		Polar look_p = Polar.fromCoord(lookVec.x, lookVec.z);

		return Rad.toDeg(Rad.diff(point_p.angle, look_p.angle));
	}


	/**
	 * Get working directory ending with slash.
	 * 
	 * @return directory path file
	 */
	public static File getGameFolder()
	{
		if (gameDir == null) {
			gameDir = FileUtils.getAppDir(Constants.APP_DIR);
		}

		return gameDir;
	}


	/**
	 * Get subfolder of game dir
	 * 
	 * @param subfolderName sibfolder name
	 * @return the file object
	 */
	public static File getGameSubfolder(String subfolderName)
	{
		return new File(getGameFolder(), subfolderName);
	}


	public static Object fallback(Object... options)
	{
		for (Object o : options) {
			if (o != null) return o;
		}
		return null; // error
	}
}
