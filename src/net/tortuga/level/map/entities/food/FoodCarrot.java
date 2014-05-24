package net.tortuga.level.map.entities.food;


import net.tortuga.level.map.entities.Entity;
import net.tortuga.level.map.entities.MetaEntity;


public class FoodCarrot extends MetaEntity {

	@Override
	public Entity createEntity()
	{
		return new EntityFood(EFood.CARROT);
	}
}
