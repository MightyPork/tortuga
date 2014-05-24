package net.tortuga.gui.panels;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tortuga.fonts.Fonts;
import net.tortuga.gui.screens.ScreenGame;
import net.tortuga.gui.screens.ScreenMenuMain;
import net.tortuga.gui.widgets.ETheme;
import net.tortuga.gui.widgets.IWidgetFactory;
import net.tortuga.gui.widgets.Theme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.composite.*;
import net.tortuga.gui.widgets.display.Text;
import net.tortuga.gui.widgets.input.Button;
import net.tortuga.gui.widgets.input.ButtonIcon;
import net.tortuga.gui.widgets.layout.FullWidthLayout;
import net.tortuga.gui.widgets.layout.LayoutH;
import net.tortuga.gui.widgets.layout.LayoutV;
import net.tortuga.gui.widgets.layout.frame.FrameBelt;
import net.tortuga.gui.widgets.layout.frame.FrameBottom;
import net.tortuga.gui.widgets.layout.frame.FrameTop;
import net.tortuga.level.LevelBundle;
import net.tortuga.level.program.GrainRegistry;
import net.tortuga.level.program.StoneRegistry;
import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.level.program.tiles.ProgTileStone;
import net.tortuga.level.program.tiles.grains.GrainJumpLabel;
import net.tortuga.level.program.tiles.grains.GrainNumber;
import net.tortuga.level.program.tiles.grains.GrainVariable;
import net.tortuga.level.program.tiles.stones.StoneLabel;
import net.tortuga.textures.Tx;
import net.tortuga.util.Align;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.math.Calc;


/**
 * Game panel
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class PanelGame extends PanelGui {

	@SuppressWarnings("unused")
	private static final int PG_INFO = 0, PG_PROG = 1, PG_MAP = 2;

	private static final int id_BACK = 0, id_RUN = 1, id_PROGRAM = 2, id_TURTLE = 3;

	private static final int SHOP_ROW = 12;
	public static final int LOW_PANEL_SIZE = 60;
	public static final int TOP_PANEL_SIZE = 60;

	/** Game GUI page: 0 = info, 1 = program, 2 = turtle map */
	private int page = PG_PROG;

	private Button bnProg;

	private Button bnTurtle;

	private static BeltCellFactory beltCellFactory = new BeltCellFactory();
	private List<Widget> beltCells = new ArrayList<Widget>();

	private static LowerShopSlotFactory stoneShopSlotFactory = new LowerShopSlotFactory();
	private List<Widget> stoneShopSlots = new ArrayList<Widget>();

	private static LowerShopRowFactory lowerShopRowFactory = new LowerShopRowFactory();
	private List<Widget> lowerShopRows = new ArrayList<Widget>();

	private static UpperShopSlotFactory grainShopSlotFactory = new UpperShopSlotFactory();
	private List<Widget> grainShopSlots = new ArrayList<Widget>();

	private static UpperShopRowFactory upperShopRowFactory = new UpperShopRowFactory();
	private List<Widget> upperShopRows = new ArrayList<Widget>();

	// the whole programming layout
	private LayoutV programmingLayout;
	private LayoutV ingameLayout;

	// lower shop box
	private CompositeScrollBoxV lowerShopBox;
	// upper shop box
	private CompositeScrollBoxV upperShopBox;

	// belt frame
	private FrameBelt beltFrame;

	// belt box
	private CompositeScrollBoxH beltBox;

	private FullWidthLayout beltLayout;

	private DragDropServer dndServer;

	private LevelBundle bundle;

	/** Screen as ScreenGame */
	private ScreenGame screenGame;

	private static class BeltCellFactory implements IWidgetFactory {

		@Override
		public Widget getWidget()
		{
			return new PgmBeltCell();
		}


		public Widget getWidget(int i)
		{
			return new PgmBeltCell(i);
		}

	}

	private static class LowerShopSlotFactory implements IWidgetFactory {

		@Override
		public Widget getWidget()
		{
			return new PgmShopSquare();
		}


		public Widget getWidget(ProgTileStone stone)
		{
			return new PgmShopSquare(stone);
		}
	}

	private static class UpperShopSlotFactory implements IWidgetFactory {

		@Override
		public Widget getWidget()
		{
			return new PgmShopHexagon();
		}


		public Widget getWidget(ProgTileGrain stone)
		{
			return new PgmShopHexagon(stone);
		}
	}

	private static class LowerShopRowFactory implements IWidgetFactory {

		@Override
		public LayoutH getWidget()
		{
			return getWidget(SHOP_ROW);
		}


		private LayoutH getWidget(int inRow)
		{
			LayoutH lh = new LayoutH();
			for (int j = 0; j < inRow; j++) {
				lh.add(stoneShopSlotFactory.getWidget());
			}

			return lh;
		}


		private LayoutH getWidget(List<Widget> childs)
		{
			LayoutH lh = new LayoutH();

			for (Widget w : childs) {
				lh.add(w);
			}

			return lh;
		}
	}

	private static class UpperShopRowFactory implements IWidgetFactory {

		@Override
		public LayoutH getWidget()
		{
			return getWidget(SHOP_ROW);
		}


		private LayoutH getWidget(int inRow)
		{
			LayoutH lh = new LayoutH();
			for (int j = 0; j < inRow; j++) {
				lh.add(grainShopSlotFactory.getWidget());
			}

			return lh;
		}


		private LayoutH getWidget(List<Widget> childs)
		{
			LayoutH lh = new LayoutH();

			for (Widget w : childs) {
				lh.add(w);
			}

			return lh;
		}
	}


	@Override
	public boolean hasBackgroundLayer()
	{
		return page == PG_PROG;
	}


	@Override
	public RGB getBackgroundColor()
	{
		return new RGB(0, 0.5);
	}


	/**
	 * Game panel
	 * 
	 * @param screen the screen
	 * @param bundle the level bundle
	 */
	public PanelGame(ScreenGame screen, LevelBundle bundle) {
		super(screen);
		this.bundle = bundle;

		screen.setMap(bundle.getBuiltMap().copy());

		this.screenGame = screen;
	}


	@Override
	public void initGui()
	{
		FullWidthLayout fwl;
		FrameTop ft;
		FrameBottom fb;
		LayoutH h;

		// sort the tile lists
		Collections.sort(bundle.progGrains);
		Collections.sort(bundle.progStones);

		int programLength = bundle.progLength;
		int labels = bundle.progLabels;
		int vars = bundle.progVars;

		List<ProgTileGrain> grains = new ArrayList<ProgTileGrain>();
		List<ProgTileStone> stones = new ArrayList<ProgTileStone>();

		// GRAINS

		// add labels (stones and grains in one cycle, to save time)
		for (int i = 0; i < labels; i++) {
			stones.add((ProgTileStone) new StoneLabel().setVariant(i));
			grains.add((ProgTileGrain) new GrainJumpLabel().setVariant(i));
		}

		int grainNumberId = GrainRegistry.getGrainIndex(GrainNumber.class);
		int grainLabelId = GrainRegistry.getGrainIndex(GrainJumpLabel.class);
		int stoneLabelId = StoneRegistry.getStoneIndex(StoneLabel.class);

		// add requested grains
		for (int id : bundle.progGrains) {
			if (id == grainNumberId || id == grainLabelId || id < 1000) continue;
			grains.add(GrainRegistry.getGrain(id));
		}

		// if there are variables, make sure there's also a number grain		
		if (bundle.progVars > 0 || bundle.progStones.contains(grainNumberId)) {
			grains.add(new GrainNumber());
		}

		// add variables
		for (int i = 0; i < vars; i++) {
			grains.add((ProgTileGrain) new GrainVariable().setVariant(i));
		}

		// STONES

		// add the stones
		for (int id : bundle.progStones) {
			if (id == stoneLabelId || id > 1000) continue;
			stones.add(StoneRegistry.getStone(id));
		}

		dndServer = new DragDropServer();

		// build belt
		for (int i = 0; i < programLength; i++) {
			Widget widget = beltCellFactory.getWidget(i + 1);
			beltCells.add(widget);
			((PgmBeltCell) widget).setServer(dndServer);
		}

		// prepare shop cells
		for (ProgTileStone stone : stones) {
			Widget widget = stoneShopSlotFactory.getWidget(stone);
			stoneShopSlots.add(widget);
			((PgmShopBase) widget).setServer(dndServer);
		}

		for (ProgTileGrain grain : grains) {
			Widget widget = grainShopSlotFactory.getWidget(grain);
			grainShopSlots.add(widget);
			((PgmShopBase) widget).setServer(dndServer);
		}

		if (stoneShopSlots.size() == 0) stoneShopSlots.add(stoneShopSlotFactory.getWidget());
		if (grainShopSlots.size() == 0) grainShopSlots.add(grainShopSlotFactory.getWidget());

		// put shop cells to shop
		for (int i = 0; i < Math.ceil(stoneShopSlots.size() / ((double) SHOP_ROW)); i++) {
			int begin = i * SHOP_ROW;
			int end = (i + 1) * SHOP_ROW;

			end = Calc.clampi(end, 0, stoneShopSlots.size());

			List<Widget> row = stoneShopSlots.subList(begin, end);

			while (row.size() < SHOP_ROW) {
				Widget widget = stoneShopSlotFactory.getWidget();
				row.add(widget);
				((PgmShopBase) widget).setServer(dndServer);
			}

			lowerShopRows.add(lowerShopRowFactory.getWidget(row));
		}

		for (int i = 0; i < Math.ceil(grainShopSlots.size() / ((double) SHOP_ROW)); i++) {
			int begin = i * SHOP_ROW;
			int end = (i + 1) * SHOP_ROW;

			end = Calc.clampi(end, 0, grainShopSlots.size());

			List<Widget> row = grainShopSlots.subList(begin, end);

			while (row.size() < SHOP_ROW) {
				Widget widget = grainShopSlotFactory.getWidget();
				row.add(widget);
				((PgmShopBase) widget).setServer(dndServer);
			}

			upperShopRows.add(upperShopRowFactory.getWidget(row));
		}

		//@formatter:off
		
		// top frame with title
		Text title = Theme.mkTitle("Game Screen of Tortuga");		
		ft = new FrameTop(title, Align.CENTER, Align.CENTER);
		ft.setMinHeight(TOP_PANEL_SIZE);
		fwl = new FullWidthLayout(ft, Align.CENTER, Align.CENTER);
		addGui(fwl, Align.CENTER, Align.TOP);	
		
		// left button		
		h = new LayoutH(Align.LEFT, Align.CENTER);
		h.setMargins(10, 0, 0, 0).setMinHeight(TOP_PANEL_SIZE);
			
		ButtonIcon bnQuit = new ButtonIcon(id_BACK, Tx.ICON_QUIT, new RGB(0x990000)); 
		bnQuit.setTooltip("Leave Game", new RGB(0xcc0000));			
		h.add(bnQuit);
		addGui(h, Align.LEFT, Align.TOP);
		
		
		// right buttons
			bnProg = new ButtonIcon(id_PROGRAM, Tx.ICON_CODE, new RGB(0xffff00)); 
				bnProg.setTooltip("Program", new RGB(0xcccc00));
				bnProg.setSelected(page == PG_PROG);
			
			bnTurtle = new ButtonIcon(id_TURTLE, Tx.ICON_TURTLE, new RGB(0x00ff00)); 
				bnTurtle.setTooltip("Level Map", new RGB(0x00cc00));
				bnTurtle.setSelected(page == PG_MAP);
			
			h = new LayoutH(Align.RIGHT, Align.CENTER);
				h.setMargins(0, 0, 10, 0).setMinHeight(TOP_PANEL_SIZE);
				h.add(bnProg);
				h.add(bnTurtle);
		
		addGui(h, Align.RIGHT, Align.TOP);
		
				
		
		
		
		// center layout
		programmingLayout = new LayoutV(Align.CENTER, Align.CENTER);
			
		upperShopBox = new CompositeScrollBoxV(2, upperShopRowFactory, upperShopRows, Align.CENTER, Align.BOTTOM);
		upperShopBox.setMarginsV(10, 0);
		fwl = new FullWidthLayout(upperShopBox, Align.CENTER, Align.CENTER);	
		programmingLayout.add(fwl);
					
		beltBox = new CompositeScrollBoxH(5, beltCellFactory, beltCells, Align.CENTER, Align.CENTER);
		beltFrame = new FrameBelt(beltBox, Align.CENTER, Align.TOP);	
		beltFrame.padding.top += 7;	
		beltFrame.padding.bottom -= 3;
		beltLayout = new FullWidthLayout(beltFrame, Align.CENTER, Align.CENTER);
		beltLayout.setRenderIndex(1);
		beltLayout.setMarginsV(10, 10);
		programmingLayout.add(beltLayout);
		
		lowerShopBox = new CompositeScrollBoxV(2, lowerShopRowFactory, lowerShopRows, Align.CENTER, Align.TOP);
		fwl = new FullWidthLayout(lowerShopBox, Align.CENTER, Align.CENTER);	
		programmingLayout.add(fwl);
		programmingLayout.add(dndServer);
		addGui(programmingLayout, Align.CENTER, Align.CENTER).setRenderIndex(1);		
		
		
		// MAP

		ingameLayout = new LayoutV(Align.CENTER, Align.CENTER);
			
		addGui(ingameLayout, Align.CENTER, Align.CENTER);	

		// BOTTOM GUI
		Button bn = new Button(id_RUN, "Go turtle, go!", Fonts.gui_title);	
		bn.setTheme(ETheme.BUTTON_LAUNCH);
		fb = new FrameBottom(bn, Align.CENTER, Align.CENTER);
		fb.setMinHeight(LOW_PANEL_SIZE);
		fwl = new FullWidthLayout(fb, Align.CENTER, Align.CENTER);
		addGui(fwl, Align.CENTER, Align.BOTTOM);
		
		//@formatter:on
	}


	@Override
	public void onPostInit()
	{
		onWindowChanged(); // adapt belt to window
	}


	@Override
	public void onWindowChanged()
	{
		super.onWindowChanged();

		adaptBeltSize();
		adaptShopSizes();

		updateWidgetPositions();

		screenGame.correctMapOffset();
	}


	private void adaptBeltSize()
	{
		int width = (int) (app.getSize().x);

		Widget b = beltCellFactory.getWidget();
		b.calcChildSizes();
		int one = b.getSize().xi() + b.getMargins().left;

		int canFit = width / one;

		beltBox.adaptForSize(canFit);
	}


	private void adaptShopSizes()
	{
		int height = (int) (app.getSize().y);

		height -= LOW_PANEL_SIZE;
		height -= TOP_PANEL_SIZE;
		height -= beltLayout.getSize().y;
		height -= beltLayout.getMargins().getVetical();
		height -= lowerShopBox.getMargins().getVetical();
		height -= upperShopBox.getMargins().getVetical();
		height -= 8;

		height /= 2;

		Widget b = lowerShopRowFactory.getWidget();
		b.calcChildSizes();
		int one = b.getSize().yi() + b.getMargins().top;

		int canFit = (int) Math.floor(height / one);

		lowerShopBox.adaptForSize(canFit);

		upperShopBox.adaptForSize(canFit);
	}


	@Override
	public void actionPerformed(Widget widget)
	{
		switch (widget.id) {
			case id_BACK:
				app.replaceScreen(new ScreenMenuMain());
				break;

			case id_PROGRAM:
				bnProg.setSelected(true);
				bnTurtle.setSelected(false);
				programmingLayout.setVisible(true);
				page = PG_PROG;
				break;

			case id_TURTLE:
				bnProg.setSelected(false);
				bnTurtle.setSelected(true);
				programmingLayout.setVisible(false);
				page = PG_MAP;
				break;
		}
	}

	private boolean dragging = false;
	private Coord dragStartMapOffset = null;
	private Coord dragStartMouse = null;


	@Override
	public void onMouseButton(int button, boolean down, int wheelDelta, Coord pos, Coord deltaPos)
	{
		super.onMouseButton(button, down, wheelDelta, pos, deltaPos);

		if (page != 2) return;

		Coord mouse = new Coord(Mouse.getX(), Mouse.getY());
		Coord scrSize = app.getSize();

		if (mouse.y < scrSize.y - TOP_PANEL_SIZE && mouse.y > LOW_PANEL_SIZE) {
			if (button == 0 && down) {
				dragging = true;
				dragStartMapOffset = screenGame.mapOffset;
				dragStartMouse = mouse;
			}
		}

		if (button == 0 && !down) {
			dragging = false;
		}

	}


	@Override
	public void onKey(int key, char c, boolean down)
	{
		if (down && key == Keyboard.KEY_R) {
			screenGame.setMap(bundle.getBuiltMap().copy());
		}
	}


	@Override
	public void handleStaticInputs()
	{
		super.handleStaticInputs();

		if (screenGame.currentMap.isMovementFinished()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				screenGame.currentMap.getTurtleController().goForward();

			} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				screenGame.currentMap.getTurtleController().goBackward();

			} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				screenGame.currentMap.getTurtleController().turnLeft();

			} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				screenGame.currentMap.getTurtleController().turnRight();

			}
		}

		if (dragging) {
			Coord mouse = new Coord(Mouse.getX(), Mouse.getY());

			screenGame.mapOffset = dragStartMapOffset.add(dragStartMouse.vecTo(mouse));

			Coord oldOffset = screenGame.mapOffset.copy();

			screenGame.correctMapOffset();

			if (!oldOffset.equals(screenGame.mapOffset)) {
				dragStartMapOffset = screenGame.mapOffset.copy();
				dragStartMouse = mouse.copy();
			}

		}

	}

}
