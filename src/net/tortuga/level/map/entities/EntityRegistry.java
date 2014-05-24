package net.tortuga.level.map.entities;


import java.util.LinkedHashMap;

import net.tortuga.level.map.entities.food.*;
import net.tortuga.util.Log;


/**
 * Entity registry
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class EntityRegistry {

	private static LinkedHashMap<Integer, Class<? extends Entity>> map = new LinkedHashMap<Integer, Class<? extends Entity>>();


	/**
	 * Init entity registry
	 */
	public static void init()
	{
		register(1, FoodAppleGreen.class);
		register(2, FoodAppleLime.class);
		register(3, FoodAppleOrange.class);
		register(4, FoodAppleRed.class);
		register(5, FoodAppleYellow.class);
		register(6, FoodPearOrange.class);
		register(7, FoodPearYellow.class);
		register(8, FoodCarrot.class);
		register(9, FoodLettuce.class);
		register(10, FoodTomatoPlain.class);
		register(11, FoodTomatoWavy.class);
	}


	/**
	 * Register an entity
	 * 
	 * @param index index
	 * @param entityClass entity class
	 */
	public static void register(int index, Class<? extends Entity> entityClass)
	{
		if (map.containsKey(index)) {
			Log.w("Registered entity " + entityClass.getSimpleName() + " replaces " + map.get(index).getSimpleName() + " at index " + index);
		}

		if (map.containsValue(entityClass)) {
			Log.w("Registering entity " + entityClass.getSimpleName() + " to index " + index + " as duplicate.");
		}

		map.put(index, entityClass);
	}


	/**
	 * Convert meta entity to real entity
	 * 
	 * @param entity checked tile
	 * @return output tile for Map
	 */
	private static Entity processMetaEntity(Entity entity)
	{
		if (entity instanceof MetaEntity) {
			return ((MetaEntity) entity).createEntity();
		}

		return entity;
	}


	/**
	 * Get instance of entity with given index
	 * 
	 * @param index entity index
	 * @return new entity instance
	 */
	public static Entity getEntity(int index)
	{
		if (index == 0) return null;
		if (!map.containsKey(index)) {
			Log.w("There is no entity registered at index " + index + ".");
		}
		try {
			return processMetaEntity(map.get(index).newInstance());
		} catch (Exception e) {
			Log.e("Could not instantiate entity at " + index + ": " + map.get(index).getSimpleName());
			return null;
		}
	}
}
