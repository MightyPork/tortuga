package net.tortuga.level;


import net.tortuga.level.map.TurtleMap;
import net.tortuga.level.map.entities.mobile.EntityTurtle;
import net.tortuga.level.map.tiles.MapTile;


/**
 * A turtle controller with access to level map
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TurtleController {

	/** Turtle map */
	private TurtleMap map;

	/** Turtle in the map */
	private EntityTurtle turtle;


	/**
	 * Create turtle controller
	 * 
	 * @param map the map object
	 */
	public TurtleController(TurtleMap map) {
		this.map = map;
		this.turtle = map.getTurtle();
		this.turtle.ctrl = this;
	}


	/**
	 * Get if turtle is ready for next command
	 * 
	 * @return is ready
	 */
	public boolean isReady()
	{
		return map.isMovementFinished();
	}


	/**
	 * Make turtle go forward
	 */
	public void goForward()
	{
		turtle.goForward();
	}


	/**
	 * Make turtle go backward
	 */
	public void goBackward()
	{
		turtle.goBackward();
	}


	/**
	 * Make turtle turn CCW
	 */
	public void turnLeft()
	{
		turtle.turnLeft();
	}


	/**
	 * Make turtle turn CW
	 */
	public void turnRight()
	{
		turtle.turnRight();
	}


	/**
	 * Get tile in frint of turtle
	 * 
	 * @return tile in front of turtle
	 */
	public MapTile getTileFront()
	{
		return turtle.getTileFront();
	}


	/**
	 * Get tile behind turtle
	 * 
	 * @return tile behind
	 */
	public MapTile getTileBack()
	{
		return turtle.getTileBack();
	}


	/**
	 * Get tile front down
	 * 
	 * @return tile front down
	 */
	public MapTile getTileFrontDown()
	{
		return turtle.getTileFrontDown();
	}


	/**
	 * Handler for food eating by turtle
	 */
	public void onFoodEaten()
	{
		// TODO count foods
		System.out.println("Food eaten.");
	}


	/**
	 * Called on turtle death (out of world)
	 */
	public void onTurtleDied()
	{
		// TODO impl onTurtleDied		
	}

}
