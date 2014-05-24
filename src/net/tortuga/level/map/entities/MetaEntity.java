package net.tortuga.level.map.entities;


import net.tortuga.textures.TxQuad;


/**
 * Meta tile used to build map tiles with special configurations. Is used in
 * MapTileRegistry with separate ID.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class MetaEntity extends Entity {

	/**
	 * Create the right map tile
	 * 
	 * @return the wrapped tile
	 */
	public abstract Entity createEntity();


	@Override
	public boolean canPushBlocks()
	{
		return false;
	}


	@Override
	public TxQuad getSpriteFrame()
	{
		return null;
	}


	@Override
	public TxQuad getSpriteShadowFrame()
	{
		return null;
	}


	@Override
	public boolean hasShadow()
	{
		return false;
	}
}
