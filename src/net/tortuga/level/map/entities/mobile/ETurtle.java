package net.tortuga.level.map.entities.mobile;


import net.tortuga.textures.Textures;

import org.newdawn.slick.opengl.Texture;


/**
 * Enum of turtle themes
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SuppressWarnings("javadoc")
// irrelevant
public enum ETurtle
{
	//@formatter:off
	DEFAULT(Textures.turtle_brown_lt),
	BROWN_LIGHT(Textures.turtle_brown_lt),
	BROWN_DARK(Textures.turtle_brown_dk),
	BROWN_ORNAMENTAL(Textures.turtle_brown_orn),
	RED(Textures.turtle_red),
	GREEN(Textures.turtle_green),
	PURPLE(Textures.turtle_purple),
	YELLOW(Textures.turtle_yellow),
	BLUE(Textures.turtle_blue);
	//@formatter:on

	private ETurtle(Texture tx) {
		this.tx = tx;
	}

	private Texture tx;


	/**
	 * Get turtle sprite sheet texture
	 * 
	 * @return spritesheet
	 */
	public Texture getSpriteTexture()
	{
		return tx;
	}


	/**
	 * Get shadow sprite sheet texture
	 * 
	 * @return spritesheet
	 */
	public Texture getShadowTexture()
	{
		return Textures.turtle_shadows;
	}
}
