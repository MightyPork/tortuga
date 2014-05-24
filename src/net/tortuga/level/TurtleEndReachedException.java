package net.tortuga.level;


/**
 * Turtle program ended exception
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SuppressWarnings("javadoc")
public class TurtleEndReachedException extends Exception {

	public TurtleEndReachedException() {}


	public TurtleEndReachedException(String message) {
		super(message);
	}


	public TurtleEndReachedException(Throwable cause) {
		super(cause);
	}


	public TurtleEndReachedException(String message, Throwable cause) {
		super(message, cause);
	}

}
