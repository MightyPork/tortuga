package net.tortuga.level.program;


import net.tortuga.CustomIonMarks;

import com.porcupine.ion.AbstractIonList;


public class GrainList extends AbstractIonList<Integer> {

	public GrainList() {}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.PGM_GRAIN_LIST;
	}

}
