package net.tortuga.gui.widgets.composite;


import net.tortuga.annotations.NeedsOverride;
import net.tortuga.annotations.Unimplemented;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.level.program.tiles.ProgTileBase;

import com.porcupine.coord.Coord;


/**
 * Program shop slot
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class PgmShopBase extends Widget implements IDragSlot {

	private static final Coord SIZE = new Coord(64, 64);

	/** DND server */
	protected DragDropServer server = null;

	/** Stone in slot */
	protected DragStone stone = null;

	protected boolean mouseOver = false;

	protected int createdStones = 0;


	@Override
	public boolean hasStone()
	{
		return stone != null && (createdStones == 0 || !stone.getStoneBase().isSingleInstance());
	}


	/**
	 * Shop slot base
	 */
	public PgmShopBase() {
		setMargins(0, 0, 0, 0);
	}


	@Override
	@Unimplemented
	public void deleteStone()
	{}


	@Override
	public boolean isShopSlot()
	{
		return true;
	}


	public void setShopStone(ProgTileBase stone)
	{
		this.stone = new DragStone(stone);
	}


	/**
	 * Set drag'n'drop server
	 * 
	 * @param server the server
	 * @return this
	 */
	public PgmShopBase setServer(DragDropServer server)
	{
		this.server = server;
		server.registerSlot(this);
		return this;
	}


	@Override
	public final void render(Coord mouse)
	{
		Coord center = rect.getCenter();
		renderSlotBackground(center);

		if (hasStone()) {
			getStoneInSlot().render(center);
		}
	}


	/**
	 * Render slot background texture
	 * 
	 * @param center center coord
	 */
	public abstract void renderSlotBackground(Coord center);


	@Override
	public Widget onScroll(Coord pos, int scroll)
	{
		return null;
	}


	@Override
	public Widget onKey(int key, char chr, boolean down)
	{
		return null;
	}


	@Override
	public void calcChildSizes()
	{
		setMinSize(SIZE);
		rect.setTo(minSize);
	}


	@Override
	public DragStone getStoneInSlot()
	{
		return stone;
	}


	@Override
	public boolean acceptsStone(IDragStone stone)
	{
		return true; // trash for all
	}


	@Override
	public boolean putStone(IDragStone stone)
	{
		// trash function
		server.notifySlotsStoneDestroyed(stone);
		return true;
	}


	@Override
	public void returnStone(IDragStone stone)
	{
		// discard it
		server.notifySlotsStoneDestroyed(stone);
	}


	@Override
	@NeedsOverride
	public void onStoneCreated(DragStone stone)
	{
		if (stone.equals(this.stone)) {
			createdStones++;
		}
	}


	@Override
	@NeedsOverride
	public void onStoneDestroyed(DragStone stone)
	{
		if (stone.equals(this.stone)) {
			createdStones--;
		}
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		return null;
	}


	@Override
	public void handleStaticInputs(Coord pos)
	{
		super.handleStaticInputs(pos);
		mouseOver = isMouseOver(pos);
	}


	@Override
	public DragStone grabStone()
	{
		if (hasStone()) {
			server.notifySlotsStoneCreated(stone);
			return stone.copy();
		}
		return null;
	}


	@Override
	public boolean isMouseOver()
	{
		return mouseOver && isVisible() && isEnabled();
	}

}
