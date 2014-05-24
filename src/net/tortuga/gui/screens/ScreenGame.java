package net.tortuga.gui.screens;


import net.tortuga.animations.WaterAnimator;
import net.tortuga.gui.panels.PanelGame;
import net.tortuga.level.GameState;
import net.tortuga.level.LevelBundle;
import net.tortuga.level.map.EntityDescriptorList;
import net.tortuga.level.map.TileGridBuilder;
import net.tortuga.level.map.TurtleMap;
import net.tortuga.level.map.TurtleMapDescriptor;
import net.tortuga.level.map.entities.EntityDescriptor;
import net.tortuga.level.map.entities.TurtleDescriptor;
import net.tortuga.sounds.Loops;

import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;
import com.porcupine.coord.Rect;


/**
 * main menu screen
 * 
 * @author MightyPork
 */
public class ScreenGame extends Screen {

	public TurtleMap currentMap = null;
	public Coord mapOffset = new Coord();
	private WaterAnimator wa = new WaterAnimator();


	@Override
	public boolean isWeatherEnabled()
	{
		return true;
	}


	@Override
	public void initScreen()
	{
		Loops.playIngame();

		LevelBundle level = new LevelBundle();

		level.progGrains.add(1001); // jump start
		level.progGrains.add(1102);
		level.progGrains.add(1103);
		level.progGrains.add(1104);
		level.progGrains.add(1105);
		level.progGrains.add(1106);

		level.progStones.add(1);
		level.progStones.add(2);
		level.progStones.add(3);
		level.progStones.add(4);

		level.progStones.add(101);
		level.progStones.add(102);
		level.progStones.add(103);
		level.progStones.add(104);
		level.progStones.add(105);

		level.progStones.add(202);
		level.progStones.add(203);
		level.progStones.add(204);

		level.progStones.add(301);
		level.progStones.add(302);

		level.progStones.add(401);
		level.progStones.add(402);
		level.progStones.add(403);

		level.progStones.add(501);
		level.progStones.add(502);
		level.progStones.add(503);
		level.progStones.add(504);
		level.progStones.add(505);
		level.progStones.add(506);
		level.progStones.add(507);
		level.progStones.add(508);
		level.progStones.add(509);
		level.progStones.add(510);
		level.progStones.add(511);
		level.progStones.add(512);
		level.progStones.add(513);

		level.progLabels = 5;
		level.progVars = 2;
		level.progLength = 16;

		// TODO load map from a TurtleMapDescriptor

// BUILD A LEVEL DESCRIPTOR		
		int w = 12, h = 6, d = 4;
		// x,y,z, ID
		TileGridBuilder builder = new TileGridBuilder(w, h, d);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				builder.setTile(x, y, 0, 1); // stone
			}
		}

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				builder.setTile(x, y, 1, 2); // grass
			}
		}

		// crates
		builder.setTile(2, 2, 2, 200);
		builder.setTile(0, 2, 2, 200);

		// ice
		builder.setTile(3, 4, 1, 5);
		builder.setTile(4, 4, 1, 5);
		builder.setTile(5, 4, 1, 5);
		builder.setTile(6, 4, 1, 5);
		builder.setTile(7, 4, 1, 5);
		builder.setTile(6, 3, 1, 5);
		builder.setTile(6, 2, 1, 5);

		builder.setTile(9, 4, 1, 0); // hole

		builder.setTile(1, 1, 2, 200);

		builder.setTile(5, 2, 2, 201); // elev
		builder.setTile(3, 2, 2, 201); // elev
		builder.setTile(6, 2, 2, 200);

		builder.setTile(9, 2, 2, 202); // switches
		builder.setTile(10, 2, 2, 203);
		builder.setTile(11, 2, 2, 203);

		TurtleDescriptor turtle = new TurtleDescriptor(0, new CoordI(7, 2, 2));
		CoordI goal = new CoordI(8, 4, 2);
		boolean hasGoal = true;

		EntityDescriptorList entities = new EntityDescriptorList();
		// ID, x,y,z
		entities.add(new EntityDescriptor(4, new CoordI(1, 1, 3)));

		entities.add(new EntityDescriptor(4, new CoordI(7, 2, 2)));
		entities.add(new EntityDescriptor(2, new CoordI(8, 2, 2)));
		entities.add(new EntityDescriptor(3, new CoordI(9, 2, 2)));
		entities.add(new EntityDescriptor(1, new CoordI(10, 2, 2)));
		entities.add(new EntityDescriptor(5, new CoordI(11, 2, 2)));

		entities.add(new EntityDescriptor(6, new CoordI(7, 3, 2)));
		entities.add(new EntityDescriptor(7, new CoordI(8, 3, 2)));
		entities.add(new EntityDescriptor(8, new CoordI(9, 3, 2)));
		entities.add(new EntityDescriptor(9, new CoordI(10, 3, 2)));
		entities.add(new EntityDescriptor(10, new CoordI(11, 3, 2)));

		entities.add(new EntityDescriptor(11, new CoordI(11, 4, 2)));

		level.mapDescriptor = new TurtleMapDescriptor(turtle, goal, hasGoal, builder.toIdGrid(), entities);

// BUILD MAP FROM DESCRIPTOR
		level.buildMap();

		setRootPanel(new PanelGame(this, level));
	}


	/**
	 * Set current map.
	 * 
	 * @param map map
	 */
	public void setMap(TurtleMap map)
	{
		this.currentMap = map;
		map.setTurtleTheme(GameState.turtleTheme);
	}


	@Override
	public void render2D(double delta)
	{
		wa.offsetPlus = mapOffset;
		wa.render(delta);

		CoordI mapSize = currentMap.getSize();
		Coord renderSize = new Coord(mapSize.x * 64, mapSize.y * 64 + mapSize.z * 32);
		Coord screenSize = app.getSize();

		Coord min = screenSize.mul(0.5);
		min.sub_ip(renderSize.x / 2, renderSize.y / 2);
		min.add_ip(mapOffset);
		min.round_ip();

		currentMap.render(min);
	}


	/**
	 * Correct map offset (after screen resize)
	 */
	public void correctMapOffset()
	{
		CoordI mapSize = currentMap.getSize();
		Coord renderSize = new Coord(mapSize.x * 64, mapSize.y * 64 + mapSize.z * 32);
		Coord screenSize = app.getSize();
		Coord min = screenSize.div(2).sub_ip(renderSize.x / 2, renderSize.y / 2).add_ip(mapOffset).round_ip();

		Rect rect = Rect.fromSize(min, renderSize);
		Rect disp = new Rect(screenSize);
		disp.growDown_ip(-PanelGame.LOW_PANEL_SIZE);
		disp.growUp_ip(-PanelGame.TOP_PANEL_SIZE);

		boolean xenough = false, yenough = false;
		if (rect.getSize().y <= disp.getSize().y) {
			mapOffset.y = 0;
			yenough = true;
		}

		if (rect.getSize().x <= disp.getSize().x) {
			mapOffset.x = 0;
			xenough = true;
		}

		if (!xenough && rect.x1() > disp.x1() + 32) {
			mapOffset.x -= rect.x1() - (disp.x1() + 32);
		}

		if (!xenough && rect.x2() < disp.x2() - 32) {
			mapOffset.x += (disp.x2()) - (rect.x2() + 32);
		}

		if (!yenough && rect.y1() > disp.y1() + 32) {
			mapOffset.y -= rect.y1() - (disp.y1() + 32);
		}

		if (!yenough && rect.y2() < disp.y2() - 32) {
			mapOffset.y += (disp.y2()) - (rect.y2() + 32);
		}

	}


	@Override
	protected void onGuiUpdate()
	{
		super.onGuiUpdate();
		wa.updateGui();
	}

}
