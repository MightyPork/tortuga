package net.tortuga.level.map;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.tortuga.App;
import net.tortuga.level.TurtleController;
import net.tortuga.level.map.entities.Entity;
import net.tortuga.level.map.entities.MoveDir;
import net.tortuga.level.map.entities.mobile.ETurtle;
import net.tortuga.level.map.entities.mobile.EntityTurtle;
import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MapTileRegistry;
import net.tortuga.util.Log;

import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;


/**
 * A turtle map (X*Y, Z layers)
 * 
 * @author MightyPork
 */
public class TurtleMap {

	// TODO make Ionizable

	/**
	 * Option to wait for ALL entities to finish movement before letting turtle
	 * to do next move.
	 */
	private static final boolean STRICT_WAITING = false;

	private CoordI size = new CoordI(0, 0, 0);

	/** Tile marked as Goal tile */
	private CoordI goal = new CoordI(0, 0, 0);

	/** Flag that a goal mark is present */
	public boolean hasGoal = false;

	private MapTile[][][] map = null;

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<ISwitch> switches = new ArrayList<ISwitch>();

	/** Map's turtle */
	public EntityTurtle turtle = null;

	/** A turtle controller */
	public TurtleController controller;


	/**
	 * Set map data, call hooks on all tiles
	 * 
	 * @param map the map
	 */
	private void setMapData(MapTile[][][] map)
	{
		this.map = map;
		size.setTo(map[0][0].length, map[0].length, map.length);

		for (int z = 0; z < size.z; z++) {
			for (int y = 0; y < size.y; y++) {
				for (int x = 0; x < size.x; x++) {
					CoordI here = new CoordI(x, y, z);
					MapTile tile = getTile(here);
					if (tile != null) tile.onAddedToMap(this, here);
				}
			}
		}
	}


	/**
	 * Set the turtle
	 * 
	 * @param dir turtle look direction
	 * @param theme turtle spritesheet texture
	 * @param pos turtle coord
	 */
	public void setTurtle(MoveDir dir, ETurtle theme, CoordI pos)
	{
		if (turtle != null) {
			if (entities.contains(turtle)) {
				entities.remove(turtle);
			}
		}
		turtle = new EntityTurtle(theme);
		turtle.direction = dir;
		turtle.pos = pos;
		if (!entities.contains(turtle)) {
			entities.add(turtle);
		}

		turtle.map = this;

		controller = new TurtleController(this);

		turtle.onAddedToMap();
	}


	/**
	 * Set turtle theme
	 * 
	 * @param theme theme to set
	 */
	public void setTurtleTheme(ETurtle theme)
	{
		if (turtle == null) Log.w("Null turtle in TurtleMap!");
		turtle.setTheme(theme);
	}


	/**
	 * Get the turtle
	 * 
	 * @return turtle
	 */
	public EntityTurtle getTurtle()
	{
		return turtle;
	}


	/**
	 * Get the turtle controller
	 * 
	 * @return turtle
	 */
	public TurtleController getTurtleController()
	{
		return controller;
	}


	/**
	 * New turtle map
	 * 
	 * @param width size X
	 * @param height size Y
	 * @param layers size Z (layers)
	 */
	public TurtleMap(int width, int height, int layers) {
		map = new MapTile[layers][height][width];
		size.setTo(width, height, layers);
	}


	/**
	 * New turtle map
	 * 
	 * @param size map size
	 */
	public TurtleMap(CoordI size) {
		map = new MapTile[size.z][size.y][size.x];
		size.setTo(size);
	}


	/**
	 * New turtle map
	 * 
	 * @param levelData level data to be used in this map
	 */
	public TurtleMap(MapTile[][][] levelData) {
		setMapData(levelData);
	}


	/**
	 * Add an entity
	 * 
	 * @param entity entity
	 */
	public void addEntity(Entity entity)
	{
		entities.add(entity);
		entity.setMap(this);
		entity.onAddedToMap();
	}


	/**
	 * Add an entity
	 * 
	 * @param entity entity
	 * @param pos entity position
	 */
	public void addEntity(Entity entity, CoordI pos)
	{
		entity.setPos(pos);
		addEntity(entity);
	}


	/**
	 * Add an entity
	 * 
	 * @param entity entity
	 * @param x pos x
	 * @param y pos y
	 * @param z pos z
	 */
	public void addEntity(Entity entity, int x, int y, int z)
	{
		addEntity(entity, new CoordI(x, y, z));
	}


	/**
	 * Remove entity from list
	 * 
	 * @param entity entity
	 */
	public void removeEntity(Entity entity)
	{
		entities.remove(entity);
	}


	/**
	 * Get if entity is registered.<br>
	 * Note: Dead entities are removed from the list
	 * 
	 * @param entity entity to check
	 * @return is in list
	 */
	public boolean hasEntity(Entity entity)
	{
		return entities.contains(entity);
	}


	/**
	 * Get map tile at Coord
	 * 
	 * @param x coord X
	 * @param y coord Y
	 * @param z layer index (Z)
	 * @return the tile (null for empty tile)
	 */
	public MapTile getTile(int x, int y, int z)
	{
		if (x < 0 || y < 0 || z < 0) return null;
		if (x >= size.x || y >= size.y || z >= size.z) return null;
		return map[z][y][x];
	}


	/**
	 * Get if a map tile at Coord is solid and not null
	 * 
	 * @param x coord X
	 * @param y coord Y
	 * @param z layer index (Z)
	 * @return is solid
	 */
	public boolean isTileSolid(int x, int y, int z)
	{
		if (x < 0 || y < 0 || z < 0) return false;
		if (x >= size.x || y >= size.y || z >= size.z) return false;
		return map[z][y][x] != null && map[z][y][x].isSolidForCollision(this, new CoordI(x, y, z));
	}


	/**
	 * Get if a map tile at Coord is opaque and not null
	 * 
	 * @param x coord X
	 * @param y coord Y
	 * @param z layer index (Z)
	 * @return is solid
	 */
	public boolean isTileFullCube(int x, int y, int z)
	{
		if (x < 0 || y < 0 || z < 0) return false;
		if (x >= size.x || y >= size.y || z >= size.z) return false;
		return map[z][y][x] != null && map[z][y][x].isShadedAsCube(this, new CoordI(x, y, z));
	}


	/**
	 * Get map tile at Coord
	 * 
	 * @param pos coord
	 * @return the tile (null for empty tile)
	 */
	public MapTile getTile(CoordI pos)
	{
		return getTile(pos.x, pos.y, pos.z);
	}


	/**
	 * Get if a map tile at Coord is solid and not null
	 * 
	 * @param pos coord
	 * @return is solid
	 */
	public boolean isTileSolid(CoordI pos)
	{
		return isTileSolid(pos.x, pos.y, pos.z);
	}


	/**
	 * Get if a map tile at Coord is opaque and not null
	 * 
	 * @param pos coord
	 * @return is solid
	 */
	public boolean isTileFullCube(CoordI pos)
	{
		return isTileFullCube(pos.x, pos.y, pos.z);
	}


	/**
	 * Set map tile at Coord
	 * 
	 * @param x coord X
	 * @param y coord Y
	 * @param z layer index (Z)
	 * @param tile the tile (null for empty tile)
	 * @return true on success (false if coords are outside the map)
	 */
	public boolean setTile(int x, int y, int z, MapTile tile)
	{
		if (x < 0 || y < 0 || z < 0) return false;
		if (x >= size.x || y >= size.y || z >= size.z) return false;

		map[z][y][x] = tile;

		if (tile != null) tile.onAddedToMap(this, new CoordI(x, y, z));
		return true;
	}


	/**
	 * Set map tile at Coord
	 * 
	 * @param pos coord
	 * @param tile the tile (null for empty tile)
	 * @return true on success (false if coords are outside the map)
	 */
	public boolean setTile(CoordI pos, MapTile tile)
	{
		return setTile(pos.x, pos.y, pos.z, tile);
	}


	/**
	 * Set map tile at Coord
	 * 
	 * @param x coord X
	 * @param y coord Y
	 * @param z layer index (Z)
	 * @param id the tile ID (0 for empty tile)
	 * @return true on success (false if coords are outside the map)
	 */
	public boolean setTile(int x, int y, int z, int id)
	{
		return setTile(x, y, z, MapTileRegistry.getTile(id));
	}


	/**
	 * Set map tile at Coord
	 * 
	 * @param pos coord
	 * @param id the tile id (0 for empty tile)
	 * @return true on success (false if coords are outside the map)
	 */
	public boolean setTile(CoordI pos, int id)
	{
		return setTile(pos.x, pos.y, pos.z, id);
	}


	/**
	 * Set map goal tile coord
	 * 
	 * @param pos goal coord
	 */
	public void setGoal(CoordI pos)
	{
		if (pos == null) {
			hasGoal = false;
		} else {
			setGoal(pos.x, pos.y, pos.z);
		}
	}


	/**
	 * Set map goal tile coord
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param z z coord (layer)
	 */
	public void setGoal(int x, int y, int z)
	{
		goal = new CoordI(x, y, z);
		hasGoal = true;
	}


	/**
	 * Get goal coord
	 * 
	 * @return goal coord
	 */
	public CoordI getGoal()
	{
		if (!hasGoal) return null;

		return goal;
	}


	/**
	 * Get map size
	 * 
	 * @return size
	 */
	public CoordI getSize()
	{
		return size;
	}


	/**
	 * Get living entities at given coord
	 * 
	 * @param pos cord to check
	 * @return entities there
	 */
	public Set<Entity> getEntitiesAtCoord(CoordI pos)
	{
		Set<Entity> found = new HashSet<Entity>();

		for (Entity ent : entities) {
			if (!ent.isDead()) {
				if (ent.getCoord().equals(pos)) {
					found.add(ent);
				}
			}
		}

		return found;
	}


	/**
	 * Render this map
	 * 
	 * @param mapMin map widget min coord
	 */
	public void render(Coord mapMin)
	{
		// update entities, delete dead
		for (int i = 0; i < entities.size(); i++) {
			Entity ent = entities.get(i);
			if (!(ent instanceof EntityTurtle)) {
				ent.updatePos(App.currentDelta);
				if (ent.isDead()) {
					entities.remove(i);
					i--;
				}
			}
		}

		for (int y = 0; y < size.y; y++) {
			for (int z = 0; z < size.z; z++) {
				for (int x = 0; x < size.x; x++) {
					CoordI here = new CoordI(x, y, z);
					MapTile tile = getTile(here);
					if (tile != null) tile.update(this, here, App.currentDelta);
				}
			}
		}

		turtle.updatePos(App.currentDelta);

		int toRender = entities.size();
		// render entities out of map: left, top, below
		// not rendered entities: rendered = false;
		for (Entity ent : entities) {
			if (ent.getRenderCoord().x < -1 || ent.getRenderCoord().y < -1 || ent.getRenderCoord().z < 0) {
				ent.render(mapMin);
				ent.rendered = true;
				toRender--;
			} else {
				ent.rendered = false;
			}
		}

		for (int y = -1; y < size.y + 1; y++) {
			for (int z = 0; z < size.z + 4; z++) {
				for (int x = -1; x < size.x + 1; x++) {
					CoordI here = new CoordI(x, y, z);
					MapTile tile = getTile(here);

					// flag if entities should be hidden
					boolean isOpaque = false;

					//  render non-transparent tile before entities
					if (tile != null) {
						tile.render(mapMin, here, this);
						isOpaque = tile.isShadedAsCube(this, here) && !tile.isGlassy(this, here);
					}

					if (toRender <= 0) continue; // all rendered, no more work.

					// if turtle heading south, render before entities 
					if (!turtle.rendered && turtle.inMotion && turtle.moveDir == MoveDir.SOUTH) {
						if (turtle.getRenderCoord().equals(here)) {
							if (!isOpaque) turtle.render(mapMin);
							turtle.rendered = true;
							toRender--;
						}

					}

					// render entities
					for (Entity ent : entities) {
						if (!ent.rendered && ent != turtle && ent.getRenderCoord().equals(here)) {
							if (!isOpaque) ent.render(mapMin);
							ent.rendered = true;
							toRender--;
						}
					}

					// if turtle not going south, render after entities
					if (!turtle.rendered && turtle.getRenderCoord().equals(here)) {
						if (!isOpaque) turtle.render(mapMin);
						turtle.rendered = true;
						toRender--;

					}

				}
			}
		}

		// render entities on the other side of the map
		for (Entity ent : entities) {
			if (!ent.rendered) {
				if (ent.getRenderCoord().x > size.x || ent.getRenderCoord().y > size.y || ent.getRenderCoord().z > size.z + 3) {
					ent.render(mapMin);
					ent.rendered = true;
					toRender--;
				}
			}
		}
	}


//	/**
//	 * Update entities
//	 */
//	public void update() {
//		for (EntityBase ent : entities) {
//			ent.update();
//		}
//	}

	/**
	 * Get a copy
	 * 
	 * @return copy
	 */
	public TurtleMap copy()
	{
		TurtleMap tm = new TurtleMap(size.x, size.y, size.z);
		tm.setGoal(goal);

		for (int z = 0; z < size.z; z++) {
			for (int y = 0; y < size.y; y++) {
				for (int x = 0; x < size.x; x++) {
					MapTile tile = getTile(x, y, z);
					if (tile != null) tm.setTile(x, y, z, tile.copy());
				}
			}
		}

		tm.setTurtle(turtle.direction, turtle.theme, turtle.pos.copy());

		for (Entity ent : entities) {
			if (!(ent instanceof EntityTurtle)) tm.addEntity(ent.copy());
		}

		return tm;
	}


	/**
	 * Get if all moving entities in the map are stopped or dead
	 * 
	 * @return is ready for next turtle step
	 */
	public boolean isMovementFinished()
	{
		if (!turtle.isMoveFinished()) return false;

		if (STRICT_WAITING) {
			for (Entity e : entities) {
				if (!e.isMoveFinished()) return false;
			}
		}

		return true;
	}


	public boolean isTurtleDead()
	{
		return turtle.isDead();
	}


	public boolean areAllSwitchesOn()
	{
		for (ISwitch sw : switches) {
			if (!sw.isOn()) return false;
		}
		return true;
	}


	public boolean areAllSwitchesOff()
	{
		for (ISwitch sw : switches) {
			if (sw.isOn()) return false;
		}
		return true;
	}


	public boolean isTurtleAtGoal()
	{
		return turtle.getCoord().equals(goal) && hasGoal;
	}


	/**
	 * Register a switch to this map
	 * 
	 * @param tileSwitch registered switch device
	 */
	public void registerSwitch(ISwitch tileSwitch)
	{
		if (!switches.contains(tileSwitch)) switches.add(tileSwitch);
	}

}
