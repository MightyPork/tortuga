package net.tortuga.textures;


/**
 * List of texture quads for GUIs
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SuppressWarnings("javadoc")
public class Tx {

	// logo
	public static TxQuad LOGO;
	public static TxQuad TITLE;

	// buttons_small, buttons_menu
	public static TxQuad BTN_SMALL;
	public static TxQuad BTN_MENU;

	// screw
	public static TxQuad SCREW;

	// checkbox
	public static TxQuad CKBOX_ON;
	public static TxQuad CKBOX_OFF;

	// radio button
	public static TxQuad RADIO_ON;
	public static TxQuad RADIO_OFF;

	// slider H
	public static TxQuad SLIDER_TRACK;
	public static TxQuad SLIDER_FILL;
	public static TxQuad SLIDER_FILL2;
	public static TxQuad SLIDER_HANDLE;

	// scrollbar H
	public static TxQuad SCROLL_H_TRACK;
	public static TxQuad SCROLL_H_HANDLE;
	public static TxQuad SCROLL_H_HANDLE_HOVER;
	public static TxQuad SCROLL_H_DOTS;
	public static TxQuad SCROLL_H_DOTS_HOVER;

	// scrollbar V
	public static TxQuad SCROLL_V_TRACK;
	public static TxQuad SCROLL_V_HANDLE;
	public static TxQuad SCROLL_V_HANDLE_HOVER;
	public static TxQuad SCROLL_V_DOTS;
	public static TxQuad SCROLL_V_DOTS_HOVER;

	// icons
	public static TxQuad ICON_QUIT;
	public static TxQuad ICON_CODE;
	public static TxQuad ICON_TURTLE;

	// slots
	public static TxQuad SLOT_SQUARE;
	public static TxQuad SLOT_HEXAGON;
	public static TxQuad SLOT_WARNING;

	public static TxQuad STONE_SQUARE;
	public static TxQuad STONE_HEXAGON;

	public static TxQuad WATER_CIRCLE;


	public static void initForSplash()
	{
		// splash logo
		LOGO = TxQuad.fromSize(Textures.logo, 15, 9, 226, 132);
	}


	public static void init()
	{
		// title image (word art)
		TITLE = TxQuad.fromSize(Textures.logo, 0, 142, 256, 64);

		// buttons
		BTN_SMALL = TxQuad.fromSize(Textures.buttons_small, 0, 0, 256, 72);
		BTN_MENU = TxQuad.fromSize(Textures.buttons_menu, 0, 0, 256, 72);

		// screw
		SCREW = TxQuad.fromSize(Textures.widgets, 25, 240, 16, 16);

		// checkbox
		CKBOX_ON = TxQuad.fromSize(Textures.widgets, 0, 157, 58, 28);
		CKBOX_OFF = TxQuad.fromSize(Textures.widgets, 0, 186, 58, 28);

		// radio button
		RADIO_ON = TxQuad.fromSize(Textures.widgets, 59, 157, 22, 22);
		RADIO_OFF = TxQuad.fromSize(Textures.widgets, 59, 157, 22, 22);

		// scrollbar H
		SCROLL_H_TRACK = TxQuad.fromSize(Textures.widgets, 0, 26, 68, 8);
		SCROLL_H_HANDLE = TxQuad.fromSize(Textures.widgets, 0, 35, 68, 19);
		SCROLL_H_HANDLE_HOVER = TxQuad.fromSize(Textures.widgets, 0, 55, 68, 19);
		SCROLL_H_DOTS = TxQuad.fromSize(Textures.widgets, 69, 35, 25, 19);
		SCROLL_H_DOTS_HOVER = TxQuad.fromSize(Textures.widgets, 69, 55, 25, 19);

		// scrollbar V
		SCROLL_V_TRACK = TxQuad.fromSize(Textures.widgets, 208, 0, 8, 68);
		SCROLL_V_HANDLE = TxQuad.fromSize(Textures.widgets, 217, 0, 19, 68);
		SCROLL_V_HANDLE_HOVER = TxQuad.fromSize(Textures.widgets, 237, 0, 19, 68);
		SCROLL_V_DOTS = TxQuad.fromSize(Textures.widgets, 217, 69, 19, 25);
		SCROLL_V_DOTS_HOVER = TxQuad.fromSize(Textures.widgets, 237, 69, 19, 25);

		SLIDER_TRACK = TxQuad.fromSize(Textures.widgets, 0, 0, 68, 14);
		SLIDER_FILL = TxQuad.fromSize(Textures.widgets, 0, 15, 68, 10);
		SLIDER_FILL2 = TxQuad.fromSize(Textures.widgets, 6, 15, 53, 10);
		SLIDER_HANDLE = TxQuad.fromSize(Textures.widgets, 0, 232, 24, 24);

		ICON_QUIT = TxQuad.fromSize(Textures.icons, 0, 0, 32, 32);
		ICON_CODE = TxQuad.fromSize(Textures.icons, 32, 0, 32, 32);
		ICON_TURTLE = TxQuad.fromSize(Textures.icons, 64, 0, 32, 32);

		SLOT_SQUARE = TxQuad.fromSize(Textures.widgets, 192, 192, 64, 64);
		SLOT_HEXAGON = TxQuad.fromSize(Textures.widgets, 127, 192, 64, 64);
		SLOT_WARNING = TxQuad.fromSize(Textures.widgets, 127, 62, 64, 64);

		STONE_SQUARE = TxQuad.fromSize(Textures.widgets, 192, 127, 64, 64);
		STONE_HEXAGON = TxQuad.fromSize(Textures.widgets, 127, 127, 64, 64);

		WATER_CIRCLE = TxQuad.fromSize(Textures.circle, 0, 0, 256, 256);
	}

}
