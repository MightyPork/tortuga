package net.tortuga.level.program.tiles;


import com.porcupine.coord.CoordI;


public class StoneTx {

	public static final CoordI GO_FORTH = new CoordI(0, 0);
	public static final CoordI GO_BACK = new CoordI(1, 0);
	public static final CoordI TURN_LEFT = new CoordI(2, 0);
	public static final CoordI TURN_RIGHT = new CoordI(3, 0);
	public static final CoordI BOX_PLACE = new CoordI(4, 0);
	public static final CoordI BOX_TAKE = new CoordI(5, 0);
	public static final CoordI SLEEP = new CoordI(6, 0);
	public static final CoordI BELL = new CoordI(7, 0);

	public static final CoordI LABEL = new CoordI(0, 1);
	public static final CoordI GOTO = new CoordI(1, 1);
	public static final CoordI CALL = new CoordI(2, 1);
	public static final CoordI RETURN = new CoordI(3, 1);
	public static final CoordI LABEL_FILL = new CoordI(4, 1);

	public static final CoordI MATH_SET = new CoordI(0, 2);
	public static final CoordI MATH_ADD = new CoordI(1, 2);
	public static final CoordI MATH_SUB = new CoordI(2, 2);
	public static final CoordI MATH_MUL = new CoordI(3, 2);
	public static final CoordI MATH_DIV = new CoordI(4, 2);
	public static final CoordI MATH_MOD = new CoordI(5, 2);

	public static final CoordI MATH_AND = new CoordI(0, 3);
	public static final CoordI MATH_OR = new CoordI(1, 3);
	public static final CoordI MATH_XOR = new CoordI(2, 3);
	public static final CoordI MATH_NOT = new CoordI(3, 3);
	public static final CoordI MATH_INC = new CoordI(4, 3);
	public static final CoordI MATH_DEC_JZ = new CoordI(5, 3);
	public static final CoordI MATH_DEC_JNZ = new CoordI(6, 3);

	public static final CoordI COMPARE = new CoordI(0, 4);
	public static final CoordI COMPARE_RESULT_FULL = new CoordI(1, 4);
	public static final CoordI COMPARE_RESULT_EQUAL_UNEQUAL = new CoordI(2, 4);

	public static final CoordI LOOK_FRONT = new CoordI(3, 4);
	public static final CoordI LOOK_INV = new CoordI(4, 4);
	public static final CoordI LOOK_DOWN = new CoordI(5, 4);

	public static final CoordI GRAIN_LABEL = new CoordI(0, 6);
	public static final CoordI GRAIN_NUMBER = new CoordI(1, 6);
	public static final CoordI GRAIN_JUMP_SKIP_ONE = new CoordI(2, 6);
	public static final CoordI GRAIN_JUMP_RELATIVE_FORTH = new CoordI(3, 6);
	public static final CoordI GRAIN_JUMP_RELATIVE_BACK = new CoordI(4, 6);
	public static final CoordI GRAIN_JUMP_END = new CoordI(5, 6);
	public static final CoordI GRAIN_JUMP_START = new CoordI(6, 6);
	public static final CoordI GRAIN_JUMP_ABSOLUTE = new CoordI(7, 6);
	public static final CoordI GRAIN_LABEL_FILL = new CoordI(8, 6);

	public static final CoordI GRAIN_VAR_A = new CoordI(0, 7);
	public static final CoordI GRAIN_VAR_B = new CoordI(1, 7);
	public static final CoordI GRAIN_VAR_C = new CoordI(2, 7);
	public static final CoordI GRAIN_VAR_D = new CoordI(3, 7);
	public static final CoordI GRAIN_VAR_E = new CoordI(4, 7);
	public static final CoordI GRAIN_VAR_F = new CoordI(5, 7);
	public static final CoordI GRAIN_VAR_G = new CoordI(6, 7);
	public static final CoordI GRAIN_VAR_H = new CoordI(7, 7);
	public static final CoordI GRAIN_VAR_I = new CoordI(8, 7);
	public static final CoordI GRAIN_VAR_J = new CoordI(9, 7);

	public static final CoordI GRAIN_VAR_K = new CoordI(0, 8);
	public static final CoordI GRAIN_VAR_L = new CoordI(1, 8);
	public static final CoordI GRAIN_VAR_M = new CoordI(2, 8);
	public static final CoordI GRAIN_VAR_N = new CoordI(3, 8);
	public static final CoordI GRAIN_VAR_O = new CoordI(4, 8);
	public static final CoordI GRAIN_VAR_P = new CoordI(5, 8);
	public static final CoordI GRAIN_VAR_Q = new CoordI(6, 8);
	public static final CoordI GRAIN_VAR_R = new CoordI(7, 8);
	public static final CoordI GRAIN_VAR_S = new CoordI(8, 8);
	public static final CoordI GRAIN_VAR_T = new CoordI(9, 8);

	public static final CoordI GRAIN_VAR_U = new CoordI(0, 9);
	public static final CoordI GRAIN_VAR_V = new CoordI(1, 9);
	public static final CoordI GRAIN_VAR_W = new CoordI(2, 9);
	public static final CoordI GRAIN_VAR_X = new CoordI(3, 9);
	public static final CoordI GRAIN_VAR_Y = new CoordI(4, 9);
	public static final CoordI GRAIN_VAR_Z = new CoordI(5, 9);

	public static final CoordI[] GRAIN_LETTERS = { GRAIN_VAR_A, GRAIN_VAR_B, GRAIN_VAR_C, GRAIN_VAR_D, GRAIN_VAR_E, GRAIN_VAR_F, GRAIN_VAR_G, GRAIN_VAR_H, GRAIN_VAR_I, GRAIN_VAR_J, GRAIN_VAR_K,
			GRAIN_VAR_L, GRAIN_VAR_M, GRAIN_VAR_N, GRAIN_VAR_O, GRAIN_VAR_P, GRAIN_VAR_Q, GRAIN_VAR_R, GRAIN_VAR_S, GRAIN_VAR_T, GRAIN_VAR_U, GRAIN_VAR_V, GRAIN_VAR_W, GRAIN_VAR_X, GRAIN_VAR_Y,
			GRAIN_VAR_Z };

}
