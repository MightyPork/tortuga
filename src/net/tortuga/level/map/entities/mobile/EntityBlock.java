package net.tortuga.level.map.entities.mobile;


import java.util.Set;

import net.tortuga.level.map.entities.Entity;
import net.tortuga.level.map.entities.MoveDir;
import net.tortuga.level.map.entities.food.EntityFood;
import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.sounds.Effects;
import net.tortuga.sounds.Loops;
import net.tortuga.textures.Textures;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.AnimDouble;

import com.porcupine.coord.Coord;


/**
 * A block entity
 * 
 * @author MightyPork
 */
public class EntityBlock extends Entity {

	/** The represented map tile */
	public MapTile tile;
	private Entity carriedEntity;


	/**
	 * Moving block
	 * 
	 * @param tile tile
	 */
	public EntityBlock(MapTile tile) {
		this.tile = tile;
	}


	/**
	 * Moving block entity
	 */
	public EntityBlock() {}


	@Override
	public void onStopMove()
	{
		super.onStopMove();

		if (isDead()) {
			Loops.stop("block.move", 0.2);
			Effects.play("water.splash", 0.6 + 0.5 * rand.nextDouble(), 1);
			return;
		}

		if (moveDir.isVertical()) {
			Loops.stop("block.move", 0.2);
		}

		if (!inMotion) {
			Loops.stop("block.move", 0.2);

			// solidify
			map.setTile(getCoord(), tile);
			setDead();

			if (moveDir == MoveDir.DOWN) {
				Effects.play("block.drop", 0.7 + 0.4 * rand.nextDouble(), 1);
			}
		}

	}


	@Override
	public boolean hasShadow()
	{
		return true;
	}


	@Override
	public Coord getSpriteOffset()
	{
		return new Coord(0, 0);
	}


	@Override
	public TxQuad getSpriteFrame()
	{
		return tile.getTexture();
	}


	@Override
	public TxQuad getSpriteShadowFrame()
	{
		return TxQuad.fromSize(Textures.turtle_shadows, 0, 12 * 64, 64, 64);
	}


	@Override
	public Entity copyFrom(Entity copied)
	{
		super.copyFrom(copied);

		tile = ((EntityBlock) copied).tile;

		return this;
	}


	@Override
	public boolean canPushBlocks()
	{
		return false;
	}


	@Override
	public double getAlphaSprite()
	{
		return 0.6;
	}


	@Override
	public double[] getMoveTriggerPoints()
	{
		return new double[] { 0.3 };
	}


	@Override
	public void onMoveTrigger(int i)
	{
		double time = moveProgress.time;
		if (moveDir == MoveDir.DOWN) time *= 0.2;
		if (moveDir == MoveDir.EAST) time *= 0.3;
		if (moveDir == MoveDir.WEST) time *= 0.3;
		if (moveDir == MoveDir.NORTH) time *= 0.2;
		if (moveDir == MoveDir.SOUTH) time *= 0.2;

		Set<Entity> entities = map.getEntitiesAtCoord(getCoordMoveTarget());
		for (Entity e : entities) {
			if (e instanceof EntityFood) {
				((EntityFood) e).animateDestroy(time);
			}
		}

	}


	@Override
	public void onMoveStarted()
	{
		Loops.start("block.move", 0.2);

		// get entity to carry = food
		Set<Entity> entities = map.getEntitiesAtCoord(getCoord().add(0, 0, 1));
		for (Entity e : entities) {
			if (e instanceof EntityFood) {
				carriedEntity = e;
				map.removeEntity(e);
				e.moveDir = moveDir;
				e.inMotion = true;
				e.isCarried = true;
				e.moveProgress.animIn(moveProgress.time);
				e.isSliding = isSliding;
			}
		}
	}


	@Override
	public void updatePos(double delta)
	{
		super.updatePos(delta);

		if (carriedEntity != null) {
			carriedEntity.updatePos(delta);

			if (carriedEntity.isMoveFinished()) {
				if (inMotion) {
					carriedEntity.moveDir = moveDir;
					carriedEntity.inMotion = true;
					carriedEntity.isCarried = true;
					carriedEntity.moveProgress.animIn(moveProgress.time);
					carriedEntity.isSliding = isSliding;
				} else {
					map.addEntity(carriedEntity);
					carriedEntity.isCarried = false;
					carriedEntity.inMotion = false;
					carriedEntity.moveProgress = new AnimDouble(0);
					carriedEntity.isSliding = false;
				}

			}
		}
	}


	@Override
	public void render(Coord mapMin)
	{
		super.render(mapMin);
		if (carriedEntity != null) carriedEntity.renderSprite(mapMin);
	}
}
