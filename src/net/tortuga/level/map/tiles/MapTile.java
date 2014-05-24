package net.tortuga.level.map.tiles;


import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import net.tortuga.annotations.Unimplemented;
import net.tortuga.level.map.TurtleMap;
import net.tortuga.level.map.entities.Entity;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.Log;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;
import com.porcupine.coord.Rect;
import com.porcupine.math.Calc;


/**
 * Base map tile
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class MapTile {

	/** RNG */
	public static Random rand = new Random();


	/**
	 * Hook called when tile was added to map
	 * 
	 * @param map
	 * @param tilePos
	 */
	@Unimplemented
	public void onAddedToMap(TurtleMap map, CoordI tilePos)
	{}


	/**
	 * Get if tile can be picked up by turtle
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return can be picked up
	 */
	public boolean canBePickedUp(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	/**
	 * Get if tile can be pushed by turtle
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return can be pushed
	 */
	public boolean canBePushed(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	/**
	 * Get a copy
	 * 
	 * @return copy
	 */
	public MapTile copy()
	{
		try {
			return getClass().newInstance().copyFrom(this);
		} catch (Exception e) {
			Log.w("Could not copy " + Calc.cname(this));
			return null;
		}
	}


	/**
	 * Copy parameters from another tile
	 * 
	 * @param copied copied tile
	 * @return this
	 */
	public MapTile copyFrom(MapTile copied)
	{
		return this;
	}


	/**
	 * Get tile texture
	 * 
	 * @return texture quad
	 */
	public abstract TxQuad getTexture();


	/**
	 * is ice?
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return is ice
	 */
	public boolean isSlipperly(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	/**
	 * Get if tile can be walked through
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return can be walked through
	 */
	public boolean isSolidForCollision(TurtleMap map, CoordI tilePos)
	{
		return true;
	}


	/**
	 * Get if tile uses cube render shading
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return can be walked through
	 */
	public boolean isShadedAsCube(TurtleMap map, CoordI tilePos)
	{
		return true;
	}


	/**
	 * Get if turtle can push blocks into this tile - used by elevator.
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return can push blocks into
	 */
	public boolean canPushBlocksInto(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	/**
	 * Get if tile can have shadow on top
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return can have shadow on top
	 */
	public boolean isShadowSurface(TurtleMap map, CoordI tilePos)
	{
		return true;
	}


	/**
	 * Get if tile can have shadow rendered on side
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return can have shadow on side
	 */
	public boolean isShadowSide(TurtleMap map, CoordI tilePos)
	{
		return true;
	}


	/**
	 * Get if this tile is transparent = ice - for culling
	 * 
	 * @param map the map
	 * @param tilePos tile pos
	 * @return is transparent
	 */
	public boolean isGlassy(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	/**
	 * Called when entity walks into this tile's area
	 * 
	 * @param map turtle map
	 * @param tilePos this tile pos
	 * @param entity the entity
	 */
	@Unimplemented
	public void onEntityEnter(TurtleMap map, CoordI tilePos, Entity entity)
	{}


	/**
	 * Called when entity leaves this tile's area
	 * 
	 * @param map turtle map
	 * @param tilePos this tile pos
	 * @param entity the entity
	 */
	@Unimplemented
	public void onEntityLeave(TurtleMap map, CoordI tilePos, Entity entity)
	{}


	/**
	 * Called when entity steps onto this tile (is above)
	 * 
	 * @param map turtle map
	 * @param tilePos this tile pos
	 * @param entity the entity
	 */
	@Unimplemented
	public void onEntityStepOn(TurtleMap map, CoordI tilePos, Entity entity)
	{}


	/**
	 * Called when entity steps off this tile (was above, went away)
	 * 
	 * @param map turtle map
	 * @param tilePos this tile pos
	 * @param entity the entity
	 */
	@Unimplemented
	public void onEntityStepOff(TurtleMap map, CoordI tilePos, Entity entity)
	{}


	/**
	 * Perform animation update if needed
	 * 
	 * @param map turtle map
	 * @param tilePos this tile pos
	 * @param delta delta time
	 */
	@Unimplemented
	public void update(TurtleMap map, CoordI tilePos, double delta)
	{}


	/**
	 * Render tile
	 * 
	 * @param mapMin map rect min
	 * @param tilePos tile position in map (counted from top left down)
	 * @param map the map
	 */
	public void render(Coord mapMin, CoordI tilePos, TurtleMap map)
	{
		//, tilePos.z*1+1
		Coord quadMin = mapMin.add(tilePos.x * 64, (map.getSize().y - 1) * 64 - tilePos.y * 64 + tilePos.z * 32);
		Coord topMid = quadMin.add(32, 64);
		Coord sideMid = quadMin.add(32, 16);
		Coord lowFaceMid = quadMin.add(32, 32);

		// rects of face sizes
		Rect topRect = new Rect(-32, -32, 32, 32);
		Rect sideRect = new Rect(-32, -16, 32, 16);
		Rect tileQuad = Rect.fromSize(quadMin, 64, 96);
		Rect topLineQuad = tileQuad.getEdgeTop().add_ip(0, -3).growUp_ip(32);

		/*
		 *   [topLeft]     [top]        [topRight]
		 *   [leftUp]      [tile]       [rightUp]
		 * [downLeft]   [down|Up|Down]  [downRight]
		 * 
		 */

		// north
		boolean iNW = map.isTileFullCube(tilePos.add(-1, -1, 0));
		boolean iN = map.isTileFullCube(tilePos.add(0, -1, 0));
		boolean iNE = map.isTileFullCube(tilePos.add(1, -1, 0));

		boolean iNW_U = map.isTileFullCube(tilePos.add(-1, -1, 1));
		boolean iN_U = map.isTileFullCube(tilePos.add(0, -1, 1));
		boolean iNE_U = map.isTileFullCube(tilePos.add(1, -1, 1));

		boolean iNW_D = map.isTileFullCube(tilePos.add(-1, -1, -1));
		boolean iN_D = map.isTileFullCube(tilePos.add(0, -1, -1));
		boolean iNE_D = map.isTileFullCube(tilePos.add(1, -1, -1));

		boolean iW = map.isTileFullCube(tilePos.add(-1, 0, 0));
		boolean iE = map.isTileFullCube(tilePos.add(1, 0, 0));

		boolean iW_U = map.isTileFullCube(tilePos.add(-1, 0, 1));
		boolean iU = map.isTileFullCube(tilePos.add(0, 0, 1));
		boolean iE_U = map.isTileFullCube(tilePos.add(1, 0, 1));

		boolean iW_D = map.isTileFullCube(tilePos.add(-1, 0, -1));
		boolean iD = map.isTileFullCube(tilePos.add(0, 0, -1));
		boolean iE_D = map.isTileFullCube(tilePos.add(1, 0, -1));

		boolean iSW = map.isTileFullCube(tilePos.add(-1, 1, 0));
		boolean iS = map.isTileFullCube(tilePos.add(0, 1, 0));
		boolean transpS = (iS && map.getTile(tilePos.add(0, 1, 0)).isGlassy(map, tilePos.add(0, 1, 0)));
		boolean iSE = map.isTileFullCube(tilePos.add(1, 1, 0));

		boolean iSW_U = map.isTileFullCube(tilePos.add(-1, 1, 1));
		boolean iS_U = map.isTileFullCube(tilePos.add(0, 1, 1));
		boolean iSE_U = map.isTileFullCube(tilePos.add(1, 1, 1));

		boolean iSW_D = map.isTileFullCube(tilePos.add(-1, 1, -1));
		boolean iS_D = map.isTileFullCube(tilePos.add(0, 1, -1));
		boolean iSE_D = map.isTileFullCube(tilePos.add(1, 1, -1));

		boolean isLowest = !iD;
//		boolean isLowest = true;
//		for (int z = tilePos.z - 1; z >= 0; z--) {
//			if (map.isTileSolid(tilePos.x, tilePos.y, z)) {
//				isLowest = false;
//			}
//		}

		/*
		 * Shadows
		 *       abv
		 * +-------------+
		 * |tLU   tU  tRU|
		 * |             |              
		 * |tL         tR|
		 * |             |  
		 * |tLD   --  tRD|
		 * +-------------+
		 * |             |
		 * |sL         sR|
		 * |      sD     |
		 * +-------------+
		 * 
		 */

		boolean tmpW = (iW || iNW);
		boolean tmpE = (iE || iNE);

		boolean abvInner = !iU && !iN && (tmpW && tmpE);
		boolean abvLeft = !iU && !iN && (!tmpW && tmpE);
		boolean abvRight = !iU && !iN && (tmpW && !tmpE);
		boolean abvBoth = !iU && !iN && (!tmpW && !tmpE);

		boolean tLU = !iU && iNW_U && (iW_U == iN_U);
		boolean tU = !iU && iN_U;
		boolean tRU = !iU && iNE_U && (iE_U == iN_U);
		boolean tL = !iU && iW_U;
		boolean tR = !iU && iE_U;
		boolean tLD = !iU && iSW_U && (iW_U == iS_U);
		boolean tRD = !iU && iSE_U && (iE_U == iS_U);
		boolean sL = !iS && iSW;
		boolean sR = !iS && iSE;
		boolean sD = !iS && (iS_D || isLowest);
		boolean tD = !iU && iS_U;

		// RENDER!

		// "above shadow"
		if (isShadedAsCube(map, tilePos)) {
			if (abvInner) RenderUtils.quadTextured(topLineQuad, MapTx.SH_ABOVE_INNER);
			if (abvLeft) RenderUtils.quadTextured(topLineQuad, MapTx.SH_ABOVE_LEFT);
			if (abvRight) RenderUtils.quadTextured(topLineQuad, MapTx.SH_ABOVE_RIGHT);
			if (abvBoth) RenderUtils.quadTextured(topLineQuad, MapTx.SH_ABOVE_BOTH);
		}

		// the tile
		TxQuad tx = getTexture();

		if (!transpS || !isGlassy(map, tilePos)) {
			RenderUtils.quadTextured(tileQuad, tx);
		} else {
			TxQuad tx2 = tx.copy();
			tx2.size.y -= 32;
			tx2.uvs.growUp_ip(-32);
			RenderUtils.quadTextured(tileQuad.growDown(-32), tx2);
		}

		if (map.hasGoal && map.getGoal().equals(tilePos.add(0, 0, 1))) {
			RenderUtils.quadTextured(tileQuad.growDown(-32), MapTx.L_GOAL);
		}

		if (!isShadedAsCube(map, tilePos)) return;

		// ### TOP FACE ###
		glPushMatrix();
		RenderUtils.translate(topMid);

		// RU, U
		if (tRU) RenderUtils.quadTextured(topRect, MapTx.SH_TOP_CORNER);
		if (tU) RenderUtils.quadTextured(topRect, MapTx.SH_TOP_SIDE);

		glRotated(90, 0, 0, 1);
		// LU, L
		if (tLU) RenderUtils.quadTextured(topRect, MapTx.SH_TOP_CORNER);
		if (tL) RenderUtils.quadTextured(topRect, MapTx.SH_TOP_SIDE);

		glRotated(90, 0, 0, 1);
		// LD, D
		if (tLD) RenderUtils.quadTextured(topRect, MapTx.SH_TOP_CORNER);
		if (tD) RenderUtils.quadTextured(topRect, MapTx.SH_TOP_SIDE);

		glRotated(90, 0, 0, 1);
		// RD, R
		if (tRD) RenderUtils.quadTextured(topRect, MapTx.SH_TOP_CORNER);
		if (tR) RenderUtils.quadTextured(topRect, MapTx.SH_TOP_SIDE);

		glPopMatrix();

		// ### SIDE FACE ###
		glPushMatrix();
		RenderUtils.translate(sideMid);

		// R, D
		if (sR) RenderUtils.quadTextured(sideRect, MapTx.SH_SIDE_SIDE);
		if (sD) RenderUtils.quadTextured(sideRect, MapTx.SH_SIDE_BOTTOM);
		glRotated(180, 0, 0, 1);
		// L
		if (sL) RenderUtils.quadTextured(sideRect, MapTx.SH_SIDE_SIDE);

		glPopMatrix();

		if (isLowest) {
			RGB sideShadow = new RGB(RGB.WHITE, 0.6);

			// S
			if (!iS && !iS_D) {
				glPushMatrix();
				RenderUtils.translate(lowFaceMid.add(0, -64));
				RenderUtils.quadTextured(topRect, MapTx.SH_TOP_SIDE, sideShadow);
				glPopMatrix();
			}

			// W
			if (!iW && !iW_D) {
				glPushMatrix();
				RenderUtils.translate(lowFaceMid.add(-64, 0));
				glRotated(-90, 0, 0, 1);
				RenderUtils.quadTextured(topRect, MapTx.SH_TOP_SIDE, sideShadow);
				glPopMatrix();
			}

			// E
			if (!iE && !iE_D) {
				glPushMatrix();
				RenderUtils.translate(lowFaceMid.add(64, 0));
				glRotated(90, 0, 0, 1);
				RenderUtils.quadTextured(topRect, MapTx.SH_TOP_SIDE, sideShadow);
				glPopMatrix();
			}

			// SW corner
			if (!iSW && !iS && !iW && !iSW_D && !iS_D && !iW_D) {
				glPushMatrix();
				RenderUtils.translate(lowFaceMid.add(-64, -64));
				RenderUtils.quadTextured(topRect, MapTx.SH_TOP_CORNER, sideShadow);
				glPopMatrix();
			}

			// SE corner
			if (!iSE && !iS && !iE && !iSE_D && !iS_D && !iE_D) {
				glPushMatrix();
				RenderUtils.translate(lowFaceMid.add(64, -64));
				glRotated(90, 0, 0, 1);
				RenderUtils.quadTextured(topRect, MapTx.SH_TOP_CORNER, sideShadow);
				glPopMatrix();
			}

			// NW corner
			if (!iNW && !iN && !iW && !iNW_D && !iN_D && !iW_D) {
				glPushMatrix();
				RenderUtils.translate(lowFaceMid.add(-64, 64));
				glRotated(-90, 0, 0, 1);
				RenderUtils.quadTextured(topRect, MapTx.SH_TOP_CORNER, sideShadow);
				glPopMatrix();
			}

			// NE corner
			if (!iNE && !iN && !iE && !iNE_D && !iN_D && !iE_D) {
				glPushMatrix();
				RenderUtils.translate(lowFaceMid.add(64, 64));
				glRotated(180, 0, 0, 1);
				RenderUtils.quadTextured(topRect, MapTx.SH_TOP_CORNER, sideShadow);
				glPopMatrix();
			}
		}

	}

}
