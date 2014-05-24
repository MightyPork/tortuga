package net.tortuga.input;


/**
 * Function object with return type
 * 
 * @author MightyPork
 * @param <T> return type
 */
public interface Function<T> {

	/**
	 * Execute this routine
	 * 
	 * @param args optional arguments (not optional for implementations)
	 * @return returned value
	 */
	public T run(Object... args);
}
