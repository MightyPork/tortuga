package net.tortuga.level.map;


import net.tortuga.CustomIonMarks;
import net.tortuga.level.map.entities.EntityDescriptor;

import com.porcupine.ion.AbstractIonList;


public class EntityDescriptorList extends AbstractIonList<EntityDescriptor> {

	public EntityDescriptorList() {}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.ENTITY_LIST;
	}


	public void populateMap(TurtleMap map)
	{
		for (EntityDescriptor ed : this) {
			map.addEntity(ed.getEntity());
		}
	}

}
