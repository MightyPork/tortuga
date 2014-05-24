package net.tortuga.level.map.entities.food;


import net.tortuga.level.map.entities.EntityTx;
import net.tortuga.textures.TxQuad;


/**
 * Enum of food types, with sprite textures and shadows
 * 
 * @author MightyPork
 */
@SuppressWarnings("javadoc")
// irrelevant
public enum EFood
{
	//@formatter:off
	
	APPLE_RED(EntityTx.APPLE_RED, EntityTx.APPLE_RED_SHADOW),
	APPLE_ORANGE(EntityTx.APPLE_ORANGE, EntityTx.APPLE_ORANGE_SHADOW),
	APPLE_YELLOW(EntityTx.APPLE_YELLOW, EntityTx.APPLE_YELLOW_SHADOW),
	APPLE_LIME(EntityTx.APPLE_LIME, EntityTx.APPLE_LIME_SHADOW),
	APPLE_GREEN(EntityTx.APPLE_GREEN, EntityTx.APPLE_GREEN_SHADOW),
	PEAR_YELLOW(EntityTx.PEAR_YELLOW, EntityTx.PEAR_YELLOW_SHADOW),
	PEAR_ORANGE(EntityTx.PEAR_ORANGE, EntityTx.PEAR_ORANGE_SHADOW),
	CARROT(EntityTx.CARROT, EntityTx.CARROT_SHADOW),
	LETTUCE(EntityTx.LETTUCE, EntityTx.LETTUCE_SHADOW),
	TOMATO_PLAIN(EntityTx.TOMATO_PLAIN, EntityTx.TOMATO_PLAIN_SHADOW),
	TOMATO_WAVY(EntityTx.TOMATO_WAVY, EntityTx.TOMATO_WAVY_SHADOW);
	
	//@formatter:on

	private TxQuad sprite, shadow;


	private EFood(TxQuad sprite, TxQuad shadow) {
		this.sprite = sprite;
		this.shadow = shadow;
	}


	/**
	 * Get the sprite frame
	 * 
	 * @return sprite frame
	 */
	public TxQuad getSpriteFrame()
	{
		return sprite;
	}


	/**
	 * Get the shadow frame
	 * 
	 * @return shadow frame
	 */
	public TxQuad getShadowFrame()
	{
		return shadow;
	}
}
