package net.tortuga.textures;


import org.newdawn.slick.opengl.Texture;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


/**
 * Texture Quad (describing a part of a texture)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TxQuad {

	/** The texture */
	public Texture tx;
	/** Coords in texture (pixels) */
	public Rect uvs;
	/** Quad size */
	public Coord size;


	/**
	 * Create TxQuad from left top coord and rect size
	 * 
	 * @param tx texture
	 * @param x1 left top X
	 * @param y1 left top Y
	 * @param width area width
	 * @param height area height
	 * @return new TxQuad
	 */
	public static TxQuad fromSize(Texture tx, int x1, int y1, int width, int height)
	{
		return new TxQuad(tx, x1, y1, x1 + width, y1 + height);
	}


	/**
	 * @param tx Texture
	 * @param uvs Rect of texturwe UVs (pixels - from left top)
	 */
	public TxQuad(Texture tx, Rect uvs) {
		this.tx = tx;
		this.uvs = uvs.copy();
		this.size = uvs.getSize();
	}


	/**
	 * Make of coords
	 * 
	 * @param tx texture
	 * @param x1 x1
	 * @param y1 y1
	 * @param x2 x2
	 * @param y2 y2
	 */
	public TxQuad(Texture tx, int x1, int y1, int x2, int y2) {
		this.tx = tx;
		this.uvs = new Rect(x1, y1, x2, y2);
		this.size = uvs.getSize();
	}


	public TxQuad copy()
	{
		return new TxQuad(tx, uvs);
	}
}
