package net.tortuga.level.map.entities.food;


import net.tortuga.level.map.entities.Entity;
import net.tortuga.sounds.Effects;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.AnimDouble;

import com.porcupine.coord.Coord;


/**
 * Food entity
 * 
 * @author MightyPork
 */
public class EntityFood extends Entity {

	private AnimDouble initFadeAnim = new AnimDouble(0);

	private EFood type = EFood.APPLE_RED;
	private double bounceOffset;
	/** Fading progress */
	private AnimDouble fadeProgress = new AnimDouble(0);
	private boolean willEat = false;


	@Override
	public void onAddedToMap()
	{
		super.onAddedToMap();
		initFadeAnim.animIn(1.2);
	}


	/**
	 * Animate food eating (fade out)
	 * 
	 * @param time animation length
	 */
	public void animateEat(double time)
	{
		willEat = true;
		fadeProgress.animIn(time);

		Effects.play("turtle.eat", 0.7 + 0.5 * rand.nextDouble(), 1);
	}


	/**
	 * Animate food destruction (fade out)
	 * 
	 * @param time animation length
	 */
	public void animateDestroy(double time)
	{
		willEat = false;
		fadeProgress.animIn(time);

		Effects.play("fruit.destroy", 0.7 + 0.5 * rand.nextDouble(), 1);
	}


	/**
	 * food type
	 * 
	 * @param type food type
	 */
	public EntityFood(EFood type) {
		this.type = type;
		bounceOffset = rand.nextDouble() * 6.28D;
	}


	/**
	 * Moving block entity
	 */
	public EntityFood() {}


	@Override
	public void updatePos(double delta)
	{
		super.updatePos(delta);

		fadeProgress.update(delta);

		initFadeAnim.update(delta);

		if (fadeProgress.delta() == 1 && fadeProgress.isFinished()) {
			if (willEat) map.controller.onFoodEaten();
			setDead();
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
		double vert = 2 * Math.sin((System.currentTimeMillis() / 3000D) * 6.28D + bounceOffset);
		return new Coord(0, 8 + (moveDir.isVertical() && inMotion ? 0 : 1) * vert);
	}


	@Override
	public TxQuad getSpriteFrame()
	{
		return type.getSpriteFrame();
	}


	@Override
	public TxQuad getSpriteShadowFrame()
	{
		return type.getShadowFrame();
	}


	@Override
	public Entity copyFrom(Entity copied)
	{
		super.copyFrom(copied);

		type = ((EntityFood) copied).type;
		bounceOffset = rand.nextDouble() * 6.28D;

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
		return (1D - fadeProgress.delta()) * super.getAlphaSprite() * initFadeAnim.delta();
	}
}
