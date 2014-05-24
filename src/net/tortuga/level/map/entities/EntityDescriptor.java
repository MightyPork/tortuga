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
public class EntityDescriptor implements Ionizable {

	private int id;
	private IonCoordI pos;


	/**
	 * Entity descriptor - empty constructor
	 */
	public EntityDescriptor() {}


	/**
	 * Entity descriptor
	 * 
	 * @param id entity ID
	 * @param pos Coord
	 */
	public EntityDescriptor(int id, CoordI pos) {
		this.id = id;
		this.pos = new IonCoordI(pos);
	}


	/**
	 * Entity descriptor
	 * 
	 * @param id entity ID
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public EntityDescriptor(int id, int x, int y, int z) {
		this.id = id;
		this.pos = new IonCoordI(x, y, z);
	}


	/**
	 * Get entity ID
	 * 
	 * @return entity ID
	 */
	public int getId()
	{
		return id;
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


	/**
	 * Get entity at the given pos.
	 * 
	 * @return the entity
	 */
	public Entity getEntity()
	{
		return EntityRegistry.getEntity(id).setPos(getPos());
	}


	@Override
	public void ionRead(InputStream in) throws IOException
	{
		id = (Integer) Ion.fromStream(in);
		pos = (IonCoordI) Ion.fromStream(in);
	}


	@Override
	public void ionWrite(OutputStream out) throws IOException
	{
		Ion.toStream(out, id);
		Ion.toStream(out, pos);
	}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.ENTITY_DESCRIPTOR;
	}

}
