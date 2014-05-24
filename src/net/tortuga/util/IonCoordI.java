package net.tortuga.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.tortuga.CustomIonMarks;

import com.porcupine.coord.CoordI;
import com.porcupine.ion.Ion;
import com.porcupine.ion.Ionizable;


/**
 * Ionizable Integer Coord
 * 
 * @author MightyPork
 */
public class IonCoordI extends CoordI implements Ionizable {

	/**
	 * Ionizable Integer Coord
	 * 
	 * @param other copied coord
	 */
	public IonCoordI(CoordI other) {
		super(other);
	}


	/**
	 * Ionizable Integer Coord (empty constructor)
	 */
	public IonCoordI() {
		super();
	}


	/**
	 * Ionizable Integer Coord
	 * 
	 * @param x X coord
	 * @param y Y coord
	 */
	public IonCoordI(int x, int y) {
		super(x, y);
	}


	/**
	 * Ionizable Integer Coord
	 * 
	 * @param x X coord
	 * @param y Y coord
	 * @param z Z coord
	 */
	public IonCoordI(int x, int y, int z) {
		super(x, y, z);
	}


	@Override
	public void ionRead(InputStream in) throws IOException
	{
		x = (Integer) Ion.fromStream(in);
		y = (Integer) Ion.fromStream(in);
		z = (Integer) Ion.fromStream(in);
	}


	@Override
	public void ionWrite(OutputStream out) throws IOException
	{
		Ion.toStream(out, x);
		Ion.toStream(out, y);
		Ion.toStream(out, z);
	}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.COORD;
	}


	/**
	 * Get copy
	 * 
	 * @return copy
	 */
	@Override
	public IonCoordI copy()
	{
		return new IonCoordI(x, y, z);
	}

}
