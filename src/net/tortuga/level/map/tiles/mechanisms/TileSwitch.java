package net.tortuga.level.map.tiles.mechanisms;


import net.tortuga.level.map.ISwitch;
import net.tortuga.level.map.TurtleMap;
import net.tortuga.level.map.entities.Entity;
import net.tortuga.level.map.entities.mobile.EntityBlock;
import net.tortuga.level.map.tiles.MapTile;
import net.tortuga.level.map.tiles.MapTx;
import net.tortuga.sounds.Effects;
import net.tortuga.textures.TxQuad;
import net.tortuga.util.RenderUtils;

import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;
import com.porcupine.coord.Rect;


/**
 * switch tile
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TileSwitch extends MapTile implements ISwitch {

	/** Is on */
	public boolean activated = false;


	/**
	 * Switch tile
	 */
	public TileSwitch() {}


	/**
	 * Switch tile
	 * 
	 * @param state is activated
	 */
	public TileSwitch(boolean state) {
		activated = state;
	}


	/**
	 * Set activated
	 * 
	 * @param state activated
	 */
	public void setActivated(boolean state)
	{
		activated = state;
	}


	@Override
	public TxQuad getTexture()
	{
		return null;
	}


	@Override
	public boolean canPushBlocksInto(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public boolean isSolidForCollision(TurtleMap map, CoordI tilePos)
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
		return false;
	}


	@Override
	public boolean isGlassy(TurtleMap map, CoordI tilePos)
	{
		return true;
	}


	@Override
	public boolean isShadedAsCube(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public boolean isSlipperly(TurtleMap map, CoordI tilePos)
	{
		return false;
	}


	@Override
	public void render(Coord mapMin, CoordI tilePos, TurtleMap map)
	{
		Coord quadMin = mapMin.add(tilePos.x * 64, (map.getSize().y - 1) * 64 - tilePos.y * 64 + tilePos.z * 32);

		Rect lowQuad = Rect.fromSize(quadMin, 64, 64);

		RenderUtils.quadTextured(lowQuad, activated ? MapTx.L_SWITCH_ON : MapTx.L_SWITCH_OFF);
	}


	@Override
	public void onEntityEnter(TurtleMap map, CoordI tilePos, Entity entity)
	{
		if (entity instanceof EntityBlock) entity.setDead(); // fix for destroying switches - ugly but neccessary
//		if(entity instanceof EntityTurtle) {
//			activated = !activated;
//			Effects.play("switch.toggle", 0.7+rand.nextDouble()*0.4, 1);
//		}
	}


	/**
	 * Toggle this switch by turtle
	 */
	public void switchByTurtle()
	{
		activated = !activated;
		Effects.play("switch.toggle", 0.7 + rand.nextDouble() * 0.4, 1);
	}


	@Override
	public MapTile copyFrom(MapTile copied)
	{
		if (copied instanceof TileSwitch) {
			activated = ((TileSwitch) copied).activated;
		}

		return this;
	}


	@Override
	public void onAddedToMap(TurtleMap map, CoordI tilePos)
	{
		super.onAddedToMap(map, tilePos);
		map.registerSwitch(this);
	}


	@Override
	public boolean isOn()
	{
		return activated;
	}

}
