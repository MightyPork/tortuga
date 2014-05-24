package net.tortuga.level.map.entities;


import com.porcupine.coord.CoordI;


/**
 * Direction of movement
 * 
 * @author MightyPork
 */
@SuppressWarnings("javadoc")
// irrelevant
public enum MoveDir
{
	NORTH, SOUTH, EAST, WEST, UP, DOWN;

	/**
	 * Get the opposite direction
	 * 
	 * @return opposite direction
	 */
	public MoveDir back()
	{
		switch (this) {
			case NORTH:
				return SOUTH;
			case SOUTH:
				return NORTH;
			case EAST:
				return WEST;
			case WEST:
				return EAST;
			case UP:
				return DOWN;
			case DOWN:
				return UP;
		}
		return null;
	}


	/**
	 * Get direction after rotation
	 * 
	 * @param rotateDir direction of rotation
	 * @return processed direction
	 */
	public MoveDir turn(RotateDir rotateDir)
	{
		if (rotateDir == RotateDir.CW) {
			switch (this) {
				case NORTH:
					return EAST;
				case SOUTH:
					return WEST;
				case EAST:
					return SOUTH;
				case WEST:
					return NORTH;
			}
		} else {
			switch (this) {
				case NORTH:
					return WEST;
				case SOUTH:
					return EAST;
				case EAST:
					return NORTH;
				case WEST:
					return SOUTH;
			}
		}
		return this;
	}


	/**
	 * Get rotation ordinal
	 * 
	 * @return ordinal
	 */
	public int getRotateOrdinal()
	{
		switch (this) {
			case NORTH:
				return 1;
			case SOUTH:
				return 3;
			case EAST:
				return 0;
			case WEST:
				return 2;
		}

		return 0;
	}


	/**
	 * Get movement vector
	 * 
	 * @return vector
	 */
	public CoordI getMoveVector()
	{
		switch (this) {
			case NORTH:
				return new CoordI(0, -1, 0);
			case SOUTH:
				return new CoordI(0, 1, 0);
			case EAST:
				return new CoordI(1, 0, 0);
			case WEST:
				return new CoordI(-1, 0, 0);
			case UP:
				return new CoordI(0, 0, 1);
			case DOWN:
				return new CoordI(0, 0, -1);
		}

		return new CoordI(0, 0, 0);
	}


	/**
	 * Get rotation index (frame number, (east) 0 to 127 (CCW), for spritesheet)
	 * 
	 * @return index
	 */
	public int getRotationIndex()
	{
		switch (this) {
			case NORTH:
				return 32;
			case SOUTH:
				return 96;
			case EAST:
				return 0;
			case WEST:
				return 64;
		}

		return 0;
	}


	/**
	 * Get if this movement is vertical = fall / fly
	 * 
	 * @return is vertical
	 */
	public boolean isVertical()
	{
		return this == UP || this == DOWN;
	}
}
