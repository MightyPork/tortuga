package net.tortuga.level;


import java.util.*;

import net.tortuga.Constants;
import net.tortuga.level.program.tiles.CompiledStone;
import net.tortuga.level.program.tiles.ProgTileStone;
import net.tortuga.level.program.tiles.stones.StoneLabel;


/**
 * Turtle runtime environment<br>
 * Executes program sequence and handles jumps and variables.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TurtleRuntimeEnvironment {

	/** compiled stone sequence */
	public List<CompiledStone> stones = new ArrayList<CompiledStone>();

	/** variable map */
	public Map<Integer, Integer> variables = new HashMap<Integer, Integer>();

	private Stack<Integer> stack = new Stack<Integer>();

	/** Last executed stone */
	public CompiledStone lastStone = null;

	/** Last compare tile TOP grain value */
	public int compareTop = 0;

	/** Last compare tile BOTTOM grain value */
	public int compareBottom = 0;

	/** PC points before the next command (N-1) */
	public int pc = -1;

	private int sleepTimer = 0;

	/** Turtle controller */
	private TurtleController turtle;


	/**
	 * new Turtle environment
	 * 
	 * @param stones program sequence
	 * @param turtle the turtle
	 */
	public TurtleRuntimeEnvironment(List<CompiledStone> stones, TurtleController turtle) {
		this.stones = stones;
		this.turtle = turtle;
	}


	/**
	 * Add sleep time for the turtle
	 * 
	 * @param seconds seconds to sleep
	 */
	public void addSleep(double seconds)
	{
		sleepTimer = (int) (Constants.FPS_GUI * seconds);
	}


	/**
	 * Get program length
	 * 
	 * @return program length
	 */
	public int getLength()
	{
		return stones.size();
	}


	/**
	 * Get compiled stone at address
	 * 
	 * @param address address
	 * @return compiled stone
	 */
	public CompiledStone getStoneAt(int address)
	{
		return stones.get(address);
	}


	/**
	 * Jump to address
	 * 
	 * @param address address
	 */
	public void jumpAbsolute(int address)
	{
		pc = address - 1;
	}


	/**
	 * Jump relative
	 * 
	 * @param add steps to add
	 */
	public void jumpRelative(int add)
	{
		pc += add;
	}


	/**
	 * Goto start
	 */
	public void jumpStart()
	{
		pc = -1;
	}


	/**
	 * Goto end (stop program next cycle)
	 */
	public void jumpEnd()
	{
		pc = getLength() - 1;
	}


	/**
	 * Get varianble
	 * 
	 * @param index variable number
	 * @return it's value
	 */
	public int getVariable(int index)
	{
		Integer val = variables.get(index);
		if (val == null) return 0;
		return val;
	}


	/**
	 * Set variable
	 * 
	 * @param index variable number
	 * @param value it's new value
	 */
	public void setVariable(int index, int value)
	{
		variables.put(index, value);
	}


	/**
	 * Goto a label
	 * 
	 * @param variant label index
	 * @throws TurtleRuntimeException
	 */
	public void jumpToLabel(int variant) throws TurtleRuntimeException
	{
		for (int i = 0; i < stones.size(); i++) {
			CompiledStone cs = getStoneAt(i);
			if (cs == null) continue;

			ProgTileStone stone = cs.getStone();
			if (stone instanceof StoneLabel) {
				if (stone.getVariant() == variant) {
					jumpAbsolute(i + 1);
					break;
				}
			}

		}

		throw new TurtleRuntimeException("Jump to undefined label.");
	}


	/**
	 * Do next command
	 * 
	 * @throws TurtleEndReachedException when program has ended
	 * @throws TurtleRuntimeException other errors
	 */
	public void doNext() throws TurtleEndReachedException, TurtleRuntimeException
	{
		// execution of the sleep command
		if (sleepTimer > 0) {
			sleepTimer--;
			return;
		}

		if (!turtle.isReady()) return; // waiting for animation

		pc++; // increment the PC

		if (pc < 0 || pc > getLength()) {
			throw new TurtleRuntimeException("Jump address out of range.");
		}

		if (pc == getLength()) {
			throw new TurtleEndReachedException("The program has ended.");
		}

		CompiledStone stone = getStoneAt(pc);

		lastStone = stone;

		if (stone == null) {
			pc++;
			doNext();
			return;
		}

		stone.execute(this, turtle);
	}


	/**
	 * Push command address
	 */
	public void pushAddress()
	{
		stack.push(pc);
	}


	/**
	 * Pop command address
	 * 
	 * @throws TurtleRuntimeException when stack is empty
	 */
	public void popAddress() throws TurtleRuntimeException
	{
		try {
			pc = stack.pop();
		} catch (EmptyStackException e) {
			throw new TurtleRuntimeException("Return outside function call.");
		}
	}

}
