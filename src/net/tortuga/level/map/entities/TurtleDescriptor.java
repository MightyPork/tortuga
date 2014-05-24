package net.tortuga.level.map.entities;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.tortuga.CustomIonMarks;
import net.tortuga.util.IonCoordI;

import com.porcupine.coord.CoordI;
import com.porcupine.ion.Ion;
import com.porcupine.ion.Ionizable;


/**
 * Entity descriptor for loading entities from ION
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TurtleDescriptor implements Ionizable {

	private int dir;
	private IonCoordI pos;


	/**
	 * Entity descriptor - empty constructor
	 */
	public TurtleDescriptor() {}


	/**
	 * Turtle descriptor
	 * 
	 * @param dir look direction: 0 east, 1 north, 2 west, 3 south
	 * @param pos pos
	 */
	public TurtleDescriptor(int dir, CoordI pos) {
		this.dir = dir;
		this.pos = new IonCoordI(pos);
	}


	/**
	 * Get entity pos
	 * 
	 * @return pos
	 */
	public CoordI getPos()
	{
		return pos;
	}


//	/**
//	 * Get entity at the given pos.
//	 * @param theme turtle theme
//	 * @return the entity
//	 */
//	public EntityTurtle getEntity(ETurtle theme) {
//		EntityTurtle e = new EntityTurtle(theme);
//		e.setPos(getPos());
//		e.direction = getDir();
//		return e;
//	}

	public MoveDir getDir()
	{
		switch (dir) {
			case 0:
				return MoveDir.EAST;
			case 1:
				return MoveDir.NORTH;
			case 2:
				return MoveDir.WEST;
			case 3:
				return MoveDir.SOUTH;
		}
		return MoveDir.EAST;
	}


	@Override
	public void ionRead(InputStream in) throws IOException
	{
		dir = (Integer) Ion.fromStream(in);
		pos = (IonCoordI) Ion.fromStream(in);
	}


	@Override
	public void ionWrite(OutputStream out) throws IOException
	{
		Ion.toStream(out, dir);
		Ion.toStream(out, pos);
	}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.TURTLE_DESCRIPTOR;
	}

}
