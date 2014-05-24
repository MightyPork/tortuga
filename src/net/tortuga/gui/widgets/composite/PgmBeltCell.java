package net.tortuga.gui.widgets.composite;


import net.tortuga.fonts.Fonts;
import net.tortuga.fonts.LoadedFont;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.level.program.tiles.EGrainSlotMode;
import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.level.program.tiles.ProgTileStone;
import net.tortuga.level.program.tiles.grains.GrainJumpRelative;
import net.tortuga.textures.Tx;
import net.tortuga.util.Align;
import net.tortuga.util.RenderUtils;

import org.lwjgl.input.Keyboard;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


public class PgmBeltCell extends Widget {

	private static final Coord SIZE = new Coord(64, 64 + 62 + 62);

	public int addressCode;

	protected BeltGrainSlot topGrain = new BeltGrainSlot(this, 0);

	protected BeltGrainSlot bottomGrain = new BeltGrainSlot(this, 1);

	protected BeltStoneSlot mainSlot = new BeltStoneSlot(this);

	private DragDropServer server;


	public PgmBeltCell() {
		setMargins(6, 0, 6, 0);
	}


	public PgmBeltCell(int i) {
		this.addressCode = i;
	}

	private LoadedFont tinyFont = Fonts.tiny;


	@Override
	public void render(Coord mouse)
	{
		Coord center = rect.getCenter().add_ip(0, 6).round_ip();

		Rect centralSlot = new Rect(center, center).grow_ip(32, 32);

		Rect topSlot = centralSlot.add(0, 63);
		Rect bottomSlot = centralSlot.sub(0, 63);

		boolean top = false;
		boolean bottom = false;

		boolean topImp = false;
		boolean bottomImp = false;

		if (mainSlot.stone != null) {
			EGrainSlotMode topMode = mainSlot.stone.getSquareStone().getGrainModeTop();
			EGrainSlotMode bottomMode = mainSlot.stone.getSquareStone().getGrainModeBottom();

			top = (topMode != EGrainSlotMode.UNUSED);
			bottom = (bottomMode != EGrainSlotMode.UNUSED);

			topImp = (topMode == EGrainSlotMode.REQUIRED);
			bottomImp = (bottomMode == EGrainSlotMode.REQUIRED);
		}

		// render slots
		if (top) RenderUtils.quadTextured(topSlot, Tx.SLOT_HEXAGON);
		RenderUtils.quadTextured(centralSlot, Tx.SLOT_SQUARE);
		if (bottom) RenderUtils.quadTextured(bottomSlot, Tx.SLOT_HEXAGON);

		LoadedFont font = Fonts.program_number;
		Coord midTxtPos = center.sub(2.5, font.getHeight() / 2);

		RGB color = new RGB(0, 0.5);
		font.draw(midTxtPos, addressCode + "", color, Align.CENTER);

		if (topImp) RenderUtils.quadTextured(topSlot, Tx.SLOT_WARNING);
		if (bottomImp) RenderUtils.quadTextured(bottomSlot, Tx.SLOT_WARNING);

		if (topGrain.stone != null) topGrain.stone.render(center.add(0, 63));
		if (mainSlot.stone != null) mainSlot.stone.render(center);
		if (bottomGrain.stone != null) bottomGrain.stone.render(center.sub(0, 63));

		Integer topNum = null;
		Integer bottomNum = null;

		ProgTileGrain grainT = null;
		ProgTileGrain grainB = null;

		if (topGrain.hasStone()) {
			grainT = ((DragStone) topGrain.getStoneInSlot()).getHexagonStone();
			if (grainT.hasExtraNumber()) topNum = grainT.getExtraNumber();
		}

		if (bottomGrain.hasStone()) {
			grainB = ((DragStone) bottomGrain.getStoneInSlot()).getHexagonStone();
			if (grainB.hasExtraNumber()) bottomNum = grainB.getExtraNumber();
		}

		if (topNum != null) {
			Coord pos = center.add(-3, 32 + 54).round_ip();

			String txt = topNum + "";

			if (grainT instanceof GrainJumpRelative) {
				if (topNum > 0) txt = "+" + txt;
			}

			if (topGrain.isMouseOver() && server.isInNumberEditMode()) {
				color = new RGB(0x33ddff, 1);
				tinyFont.drawFuzzy(pos, txt, Align.CENTER, color, new RGB(0, 0.12), 2, true);
			} else {
				color = new RGB(0, 0.8);
				tinyFont.draw(pos, txt, color, Align.CENTER);
			}
		}

		if (bottomNum != null) {
			Coord pos = center.add(-3, -(32 + 57) - tinyFont.getHeight()).round_ip();

			String txt = bottomNum + "";

			if (grainB instanceof GrainJumpRelative) {
				if (bottomNum > 0) txt = "+" + txt;
			}

			if (bottomGrain.isMouseOver() && server.isInNumberEditMode()) {
				color = new RGB(0x33ddff, 1);
				tinyFont.drawFuzzy(pos, txt, Align.CENTER, color, new RGB(0, 0.12), 2, true);
			} else {
				color = new RGB(0, 0.8);
				tinyFont.draw(pos, txt, color, Align.CENTER);
			}

		}

		//RenderUtils.quadBorder(rect, 1, RGB.GREEN, null);
		//RenderUtils.quadBorder(rect.getAxisV(), 1, RGB.RED, null);
//mainSlot.stone != null && 
		if ((Keyboard.isKeyDown(Keyboard.KEY_TAB))) {
			color = new RGB(0x3399ff, 1);
			RGB blur = new RGB(0, 0.12);

			// on top
			font.drawFuzzy(midTxtPos, addressCode + "", Align.CENTER, color, blur, 3, true);
		}
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		return null;
	}


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
		setMinSize(SIZE.add(0, 25 /*0tinyFont.getHeight()*2*/));
		rect.setTo(minSize);
	}


	public void setServer(DragDropServer dndServer)
	{
		server = dndServer;
		dndServer.registerSlot(mainSlot);
		dndServer.registerSlot(topGrain);
		dndServer.registerSlot(bottomGrain);
	}

	private boolean moTop = false, moMiddle = false, moBottom = false;


	@Override
	public void handleStaticInputs(Coord pos)
	{
		super.handleStaticInputs(pos);

		moTop = false;
		moBottom = false;
		moMiddle = false;

		if (isMouseOver(pos)) {
			moTop = pos.isInRect(rect.getEdgeTop().growDown(64));
			moBottom = pos.isInRect(rect.getEdgeBottom().growUp(64));
			moMiddle = pos.isInRect(rect.getAxisH().grow(0, 32));
		}
	}


	public boolean isMouseOverGrain(int index)
	{
		return index == 0 ? moTop : moBottom;
	}


	public boolean isMouseOverStone()
	{
		return moMiddle;
	}


	public EGrainSlotMode getGrainSlotMode(int index)
	{
		if (mainSlot.stone == null) return EGrainSlotMode.UNUSED;

		ProgTileStone sq = mainSlot.stone.getSquareStone();
		return index == 0 ? sq.getGrainModeTop() : sq.getGrainModeBottom();
	}


	public boolean doesGrainSlotAcceptStone(DragStone stone, int index)
	{
		if (getGrainSlotMode(index) == EGrainSlotMode.UNUSED) return false;
		if (!stone.isGrain()) return false;

		ProgTileStone sq = mainSlot.stone.getSquareStone();

		if (index == 0) return sq.acceptsGrainTop(stone.getHexagonStone().getGrainType());
		if (index == 1) return sq.acceptsGrainBottom(stone.getHexagonStone().getGrainType());

		return true;
	}

}
