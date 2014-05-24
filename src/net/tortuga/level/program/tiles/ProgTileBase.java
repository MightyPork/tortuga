package net.tortuga.level.program.tiles;


import net.tortuga.textures.Textures;
import net.tortuga.textures.Tx;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;
import com.porcupine.coord.Rect;


public abstract class ProgTileBase {

	public int variant = 0;


	public int getVariant()
	{
		return variant;
	}


	public ProgTileBase setVariant(int i)
	{
		this.variant = i;
		return this;
	}


	public abstract boolean isSingleInstance();


	public abstract CoordI getOverlayIndex();


	public abstract CoordI getSubOverlayIndex();


	public abstract RGB getSubOverlayColor();


	public abstract EProgTileType getProgTileType();


	public abstract RGB getStoneColor();


	public void render(Coord posCenter)
	{
		Rect tileRect = new Rect(posCenter, posCenter).grow_ip(32, 32);
		Rect overlayRect = new Rect(posCenter, posCenter).grow_ip(24, 24);

		TxQuad tx = (getProgTileType() == EProgTileType.STONE ? Tx.STONE_SQUARE : Tx.STONE_HEXAGON);

		RGB color = getStoneColor();
		RGB color0 = getSubOverlayColor();

		RenderUtils.quadTextured(tileRect, tx, color);

		CoordI index0 = getSubOverlayIndex();
		if (index0 != null) {
			Rect overlayUvs0 = new Rect(index0.toCoord(), index0.toCoord().add_ip(1, 1)).mul_ip(48);
			RenderUtils.quadTextured(overlayRect, overlayUvs0, Textures.program, color0);
		}

		CoordI index = getOverlayIndex();
		Rect overlayUvs = new Rect(index.toCoord(), index.toCoord().add_ip(1, 1)).mul_ip(48);
		RenderUtils.quadTextured(overlayRect, overlayUvs, Textures.program, color);
	}


	public final ProgTileBase copy()
	{
		try {
			ProgTileBase stone = getClass().newInstance();
			stone.copyFrom(this);
			return stone;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}


	public void copyFrom(ProgTileBase other)
	{
		variant = other.variant;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof ProgTileBase)) return false;
		return obj.getClass() == getClass() && ((ProgTileBase) obj).getVariant() == getVariant();
	}

}
