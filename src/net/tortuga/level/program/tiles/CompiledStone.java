package net.tortuga.level.program.tiles;


import net.tortuga.level.TurtleController;
import net.tortuga.level.TurtleRuntimeEnvironment;
import net.tortuga.level.TurtleRuntimeException;


/**
 * Compiled program stone
 * 
 * @author MightyPork
 */
public class CompiledStone {

	private ProgTileStone stone = null;
	private ProgTileGrain grainTop = null;
	private ProgTileGrain grainBottom = null;


	/**
	 * A compiled stone
	 * 
	 * @param stone main stone
	 * @param grainTop top grain if any
	 * @param grainBottom bottom grain if any
	 */
	public CompiledStone(ProgTileStone stone, ProgTileGrain grainTop, ProgTileGrain grainBottom) {
		this.stone = stone;
		this.grainTop = grainTop;
		this.grainBottom = grainBottom;
	}


	/**
	 * Get main stone
	 * 
	 * @return main stone
	 */
	public ProgTileStone getStone()
	{
		return stone;
	}


	/**
	 * Get top grain
	 * 
	 * @return top grain
	 */
	public ProgTileGrain getGrainTop()
	{
		return grainTop;
	}


	/**
	 * Get bottom grain
	 * 
	 * @return bottom grain
	 */
	public ProgTileGrain getGrainBottom()
	{
		return grainBottom;
	}


	/**
	 * Execute this instruction
	 * 
	 * @param environment runtime environment
	 * @param turtle turtle controller
	 * @throws TurtleRuntimeException
	 */
	public void execute(TurtleRuntimeEnvironment environment, TurtleController turtle) throws TurtleRuntimeException
	{
		stone.execute(grainTop, grainBottom, environment, turtle);
	}

}
