package net.tortuga.level;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.tortuga.CustomIonMarks;

import com.porcupine.ion.AbstractIonMap;
import com.porcupine.ion.Ionizable;


public class LevelPars implements Ionizable {

	public static final String COUNT_STONES = "cnt.length";
	public static final String COUNT_PGM_TICKS = "cnt.ticks";
	public static final String COUNT_MOVES = "cnt.moves";
	public static final String COUNT_FOOD = "cnt.food";
	// FIXME
	private AbstractIonMap<Integer> pars = new AbstractIonMap<Integer>() {};


	@Override
	public void ionRead(InputStream in) throws IOException
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void ionWrite(OutputStream out) throws IOException
	{
		// TODO Auto-generated method stub

	}


	@Override
	public byte ionMark()
	{
		return CustomIonMarks.LEVEL_PARS;
	}

}
