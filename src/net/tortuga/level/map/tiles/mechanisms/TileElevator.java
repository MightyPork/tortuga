package net.tortuga.level.map.tiles.mechanisms;


import net.tortuga.level.map.TurtleMap;
import net.tortuga.level.map.entities.Entity;
import net.tortuga.level.map.entities.MoveDir;
import net.tortuga.level.map.entities.mobile.EntityTurtle;
import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MapTx;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.AnimDouble;
import net.tortuga.util.RenderUtils;

import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;
import com.porcupine.coord.Rect;


/**
 * Piston / elevator tile for turtle and blocks
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TileElevator extends MapTile {

	private boolean isExtended = false;
	private boolean inMotion = false;
	private AnimDouble moveProgress = new AnimDouble(0);
	private Entity movedEntity;
	private static final double ANIM_TIME_UP = 1;
	private static final double ANIM_TIME_DOWN = 0.3;


	@Override
	public TxQuad getTexture()
	{
		return null;
	}


	@Override
	public boolean isGlassy(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public boolean isSlipperly(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public boolean isSolidForCollision(TurtleMap map, CoordI tilePos)
	{
		return isExtended || inMotion;
	}


	@Override
	public boolean canPushBlocksInto(TurtleMap map, CoordI tilePos)
	{
		return !isSolidForCollision(map, tilePos);
	}


	@Override
	public boolean isShadedAsCube(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public boolean isShadowSide(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public boolean isShadowSurface(TurtleMap map, CoordI tilePos)
	{
		return isExtended;
	}


	@Override
	public boolean canBePickedUp(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public boolean canBePushed(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public void render(Coord mapMin, CoordI tilePos, TurtleMap map)
	{
		Coord quadMin = mapMin.add(tilePos.x * 64, (map.getSize().y - 1) * 64 - tilePos.y * 64 + tilePos.z * 32);

		Rect lowQuad = Rect.fromSize(quadMin, 64, 64);
		Rect platformQuad = lowQuad.copy();
		if (inMotion) {
			double fraction = moveProgress.delta();
			platformQuad.add_ip(0, 32 * (isExtended ? 1 - fraction : fraction));
		} else {
			platformQuad.add_ip(0, isExtended ? 32 : 0);
		}

		TxQuad txBase = MapTx.L_ELEV_BASE;
		TxQuad txBaseOver = MapTx.L_ELEV_BASE_OVER;
		TxQuad txPlatform = MapTx.L_ELEV_PLATFORM;

		RenderUtils.quadTextured(lowQuad, txBase);
		RenderUtils.quadTextured(platformQuad, txPlatform);
		RenderUtils.quadTextured(lowQuad, txBaseOver);
		// has parts that are rendered over "3D" vertical side of platform to simulate "hole"

	}


	@Override
	public void onEntityEnter(TurtleMap map, CoordI tilePos, Entity entity)
	{
		if (entity == null) return;
		if (entity.moveDir.isVertical()) return; // fallen onto = do nothing
		if (!inMotion) {
			if (!isExtended) {
				inMotion = true;
				moveProgress.animIn(ANIM_TIME_UP);

				// let the entity go up
				entity.inMotion = true;
				entity.moveDir = MoveDir.UP;
				entity.moveProgress.animIn(ANIM_TIME_UP);
				entity.setImmediateShadow(true);

				movedEntity = entity;
			}
		}
	}


	@Override
	public void onEntityStepOff(TurtleMap map, CoordI tilePos, Entity entity)
	{
		if (entity == null) return;
		if (!(entity instanceof EntityTurtle)) return;
		if (!inMotion) {
			if (isExtended) {
				inMotion = true; // contract
				moveProgress.animIn(ANIM_TIME_DOWN);
			}
		}
	}


	@Override
	public void update(TurtleMap map, CoordI tilePos, double delta)
	{
		if (inMotion) {
			moveProgress.update(delta);
			if (moveProgress.isFinished()) {
				inMotion = false;
				isExtended = !isExtended;

				if (movedEntity != null) {
					movedEntity.setImmediateShadow(false);
					movedEntity = null;
				}
			}
		}
	}

}
