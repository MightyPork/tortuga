package net.tortuga.level.map.entities.mobile;


import java.util.Set;

import net.tortuga.level.TurtleController;
import net.tortuga.level.map.entities.Entity;
import net.tortuga.level.map.entities.MoveDir;
import net.tortuga.level.map.entities.RotateDir;
import net.tortuga.level.map.entities.food.EntityFood;
import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.mechanisms.TileSwitch;
import net.tortuga.sounds.Effects;
import net.tortuga.sounds.Loops;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.AnimDouble;

import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;


/**
 * The turtle entity
 * 
 * @author MightyPork
 */
public class EntityTurtle extends Entity {

	private AnimDouble initFadeAnim = new AnimDouble(0);

	/** Turtle controller */
	public TurtleController ctrl;

	/** Active spritesheet texture */
	public ETurtle theme;


//	@Override
//	public double getAlphaSprite() {
//		return 1;
//	}

//	@Override
//	public double getAlphaShadow() {
//		return 1;
//	}

	@Override
	public void onAddedToMap()
	{
		super.onAddedToMap();
		initFadeAnim.animIn(0.6);
	}


	@Override
	public double getAlphaShadow()
	{
		return super.getAlphaShadow() * initFadeAnim.delta();
	}


	@Override
	public double getAlphaSprite()
	{
		return super.getAlphaSprite() * initFadeAnim.delta();
	}


	@Override
	public void updatePos(double delta)
	{
		super.updatePos(delta);
		initFadeAnim.update(delta);
	}


	@Override
	public void onStopMove()
	{
		// TODO check for winning conditions

		super.onStopMove();

		if (isDead()) {
			Loops.stop("turtle.move", 0.2);
			Effects.play("water.splash", 0.6 + 0.5 * rand.nextDouble(), 1);
			ctrl.onTurtleDied();
			return;
		}

		// TODO ask controller for next step.

		if (!inMotion || moveDir.isVertical()) {
			Loops.stop("turtle.move", 0.2);
		}

		if (!inMotion && moveDir == MoveDir.DOWN) {
			Effects.play("turtle.drop", 0.7 + 0.4 * rand.nextDouble(), 1);
		}
	}


	@Override
	public void onStopRotation()
	{
		super.onStopRotation();

		if (!inRotation) {
			Loops.stop("turtle.move", 0.2);
		}
	}


	@Override
	public void onRotationStarted()
	{
		Loops.start("turtle.move", 0.2);
	}


	@Override
	public double[] getMoveTriggerPoints()
	{
		double dist = 0.5;
		if (moveDir == MoveDir.DOWN) dist = 0.3;
		if (moveDir == MoveDir.EAST) dist = 0.2;
		if (moveDir == MoveDir.WEST) dist = 0.2;
		if (moveDir == MoveDir.NORTH) dist = 0.3;
		if (moveDir == MoveDir.SOUTH) dist = 0.3;

		return new double[] { dist, 0.5 };
	}


	@Override
	public void onMoveTrigger(int i)
	{
		switch (i) {
			case 0: // food
				double time = moveProgress.time;
				if (moveDir == MoveDir.DOWN) time *= 0.2;
				if (moveDir == MoveDir.EAST) time *= 0.3;
				if (moveDir == MoveDir.WEST) time *= 0.3;
				if (moveDir == MoveDir.NORTH) time *= 0.2;
				if (moveDir == MoveDir.SOUTH) time *= 0.2;

				Set<Entity> entities = map.getEntitiesAtCoord(getCoordMoveTarget());
				for (Entity e : entities) {
					if (e instanceof EntityFood) {
						((EntityFood) e).animateEat(time);
					}
				}
				break;

			case 1: // switches
				CoordI targetPos = getCoordMoveTarget();
				MapTile tgTile = map.getTile(targetPos);
				if (tgTile != null && tgTile instanceof TileSwitch) {
					((TileSwitch) tgTile).switchByTurtle();
				}
				break;
		}
	}


	/**
	 * Turtle entity
	 */
	public EntityTurtle() {}


	/**
	 * Turtle entity
	 * 
	 * @param theme
	 */
	public EntityTurtle(ETurtle theme) {
		this.theme = theme;
	}


	@Override
	public boolean canPushBlocks()
	{
		return true;
	}


	@Override
	public Entity copyFrom(Entity copied)
	{
		super.copyFrom(copied);

		theme = ((EntityTurtle) copied).theme;

		return this;
	}


	@Override
	public TxQuad getSpriteFrame()
	{
		CoordI index = getSpritesheetIndex();
		return TxQuad.fromSize(theme.getSpriteTexture(), index.x * 64, index.y * 64, 64, 64);
	}


	@Override
	public Coord getSpriteOffset()
	{
		double vert = Math.sin((System.currentTimeMillis() / 1300D) * 6.28D);
		return new Coord(0, 6 + (moveDir.isVertical() && inMotion ? 0 : 1) * vert);
	}


	@Override
	public TxQuad getSpriteShadowFrame()
	{
		CoordI index = getSpritesheetIndex();
		return TxQuad.fromSize(theme.getShadowTexture(), index.x * 64, index.y * 64, 64, 64);
	}


	private CoordI getSpritesheetIndex()
	{
		// no motion or rotation - static turtle
		if (!inMotion && !inRotation) {
			return new CoordI(0, direction.getRotateOrdinal());
		}

		// motion
		if (inMotion) {
			if (moveDir.isVertical()) {
				return new CoordI(0, direction.getRotateOrdinal());
			} else {
				int moveIndex;

				if (!isSliding) {
					moveIndex = (int) (Math.round((moveProgress.delta() * 32)) % 16);
				} else {
					moveIndex = (int) (Math.round((moveProgress.delta() * 16)) % 16);
				}

				return new CoordI(moveIndex, direction.getRotateOrdinal());
			}
		}

		// rotation
		if (inRotation) {
			int rotateIndex = direction.getRotationIndex();

			if (rotateDir == RotateDir.CCW) {
				rotateIndex += Math.round(rotateProgress.delta() * 32);
			}

			if (rotateDir == RotateDir.CW) {
				rotateIndex -= Math.round(rotateProgress.delta() * 32);
			}

			if (rotateIndex < 0) rotateIndex += 128;
			if (rotateIndex > 127) rotateIndex -= 128;

			return new CoordI(rotateIndex % 16, 4 + (int) Math.floor(rotateIndex / 16D));
		}

		return new CoordI(0, 0);
	}


	/**
	 * Go backward
	 */
	public void goBackward()
	{
		goToDir(direction.back());
	}


	/**
	 * Go forward
	 */
	public void goForward()
	{
		goToDir(direction);
	}


	@Override
	public boolean hasShadow()
	{
		return true;
	}


	/**
	 * Set theme.
	 * 
	 * @param theme theme
	 */
	public void setTheme(ETurtle theme)
	{
		this.theme = theme;
	}


	@Override
	public void onMoveStarted()
	{
		if (!moveDir.isVertical()) {
			Loops.start("turtle.move", 0.2);
		}
	}

}
