package net.tortuga.level;


/**
 * Turtle runtime exception (error in user program)
 * 
 * @author MightyPork
 */
@SuppressWarnings("javadoc")
public class TurtleRuntimeException extends Exception {

	public TurtleRuntimeException() {}


	public TurtleRuntimeException(String message) {
		super(message);
	}


	public TurtleRuntimeException(Throwable cause) {
		super(cause);
	}


	public TurtleRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
