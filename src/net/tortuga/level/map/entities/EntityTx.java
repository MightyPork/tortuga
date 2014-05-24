package net.tortuga.level.map.entities;


import net.tortuga.textures.Textures;
import net.tortuga.textures.TxQuad;


/**
 * Textures for entities (food)
 * 
 * @author MightyPork
 */
@SuppressWarnings("javadoc")
public class EntityTx {

	// apples
	public static TxQuad APPLE_RED;
	public static TxQuad APPLE_RED_SHADOW;

	public static TxQuad APPLE_GREEN;
	public static TxQuad APPLE_GREEN_SHADOW;

	public static TxQuad APPLE_YELLOW;
	public static TxQuad APPLE_YELLOW_SHADOW;

	public static TxQuad APPLE_ORANGE;
	public static TxQuad APPLE_ORANGE_SHADOW;

	public static TxQuad APPLE_LIME;
	public static TxQuad APPLE_LIME_SHADOW;

	// pears
	public static TxQuad PEAR_YELLOW;
	public static TxQuad PEAR_YELLOW_SHADOW;

	public static TxQuad PEAR_ORANGE;
	public static TxQuad PEAR_ORANGE_SHADOW;

	// carrot
	public static TxQuad CARROT;
	public static TxQuad CARROT_SHADOW;

	// lettuce
	public static TxQuad LETTUCE;
	public static TxQuad LETTUCE_SHADOW;

	// plain tomato
	public static TxQuad TOMATO_PLAIN;
	public static TxQuad TOMATO_PLAIN_SHADOW;

	// wavy tomato
	public static TxQuad TOMATO_WAVY;
	public static TxQuad TOMATO_WAVY_SHADOW;


	/**
	 * Load
	 */
	public static void init()
	{
		// tiles: row1
		APPLE_RED = TxQuad.fromSize(Textures.food, 0, 0, 64, 64);
		APPLE_RED_SHADOW = TxQuad.fromSize(Textures.food_shadows, 0, 0, 64, 64);

		APPLE_GREEN = TxQuad.fromSize(Textures.food, 64, 0, 64, 64);
		APPLE_GREEN_SHADOW = TxQuad.fromSize(Textures.food_shadows, 64, 0, 64, 64);

		APPLE_YELLOW = TxQuad.fromSize(Textures.food, 64 * 2, 0, 64, 64);
		APPLE_YELLOW_SHADOW = TxQuad.fromSize(Textures.food_shadows, 64 * 2, 0, 64, 64);

		APPLE_ORANGE = TxQuad.fromSize(Textures.food, 64 * 3, 0, 64, 64);
		APPLE_ORANGE_SHADOW = TxQuad.fromSize(Textures.food_shadows, 64 * 3, 0, 64, 64);

		// 2nd row
		APPLE_LIME = TxQuad.fromSize(Textures.food, 0, 64, 64, 64);
		APPLE_LIME_SHADOW = TxQuad.fromSize(Textures.food_shadows, 0, 64, 64, 64);

		PEAR_YELLOW = TxQuad.fromSize(Textures.food, 64, 64, 64, 64);
		PEAR_YELLOW_SHADOW = TxQuad.fromSize(Textures.food_shadows, 64, 64, 64, 64);

		PEAR_ORANGE = TxQuad.fromSize(Textures.food, 64 * 2, 64, 64, 64);
		PEAR_ORANGE_SHADOW = TxQuad.fromSize(Textures.food_shadows, 64 * 2, 64, 64, 64);

		CARROT = TxQuad.fromSize(Textures.food, 64 * 3, 64, 64, 64);
		CARROT_SHADOW = TxQuad.fromSize(Textures.food_shadows, 64 * 3, 64, 64, 64);

		// 3rd row
		LETTUCE = TxQuad.fromSize(Textures.food, 0, 64 * 2, 64, 64);
		LETTUCE_SHADOW = TxQuad.fromSize(Textures.food_shadows, 0, 64 * 2, 64, 64);

		TOMATO_PLAIN = TxQuad.fromSize(Textures.food, 64, 64 * 2, 64, 64);
		TOMATO_PLAIN_SHADOW = TxQuad.fromSize(Textures.food_shadows, 64, 64 * 2, 64, 64);

		TOMATO_WAVY = TxQuad.fromSize(Textures.food, 64 * 2, 64 * 2, 64, 64);
		TOMATO_WAVY_SHADOW = TxQuad.fromSize(Textures.food_shadows, 64 * 2, 64 * 2, 64, 64);
	}

}
