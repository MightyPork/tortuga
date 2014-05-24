package net.tortuga.gui.widgets.composite;


import net.tortuga.level.program.tiles.ProgTileStone;
import net.tortuga.textures.Tx;
import net.tortuga.util.RenderUtils;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


public class PgmShopSquare extends PgmShopBase {

	public PgmShopSquare(ProgTileStone stone) {
		setShopStone(stone);
	}


	public PgmShopSquare() {}


	@Override
	public void renderSlotBackground(Coord center)
	{
		Rect centralSlot = rect.getAxisH();
		centralSlot.grow_ip(0, 32);
		RenderUtils.quadTextured(centralSlot, Tx.SLOT_SQUARE);
	}
}
