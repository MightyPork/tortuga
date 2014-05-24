package net.tortuga.level.program;


import net.tortuga.CustomIonMarks;

import com.porcupine.ion.AbstractIonList;


public class StoneList extends AbstractIonList<Integer> {

	public StoneList() {}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.PGM_STONE_LIST;
	}

}
