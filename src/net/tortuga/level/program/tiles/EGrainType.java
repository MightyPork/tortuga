package net.tortuga.level.program.tiles;


/**
 * Grain type
 * 
 * @author MightyPork
 */
public enum EGrainType
{
	/** Do-nothing grain */
	NONE,
	/** Jump target, with working "execute" method. */
	LABEL,
	/** Number from user */
	NUMBER,
	/** Variable identifier */
	VAR;
}
