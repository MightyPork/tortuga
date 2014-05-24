package net.tortuga.gui.widgets;


import static net.tortuga.gui.widgets.EColorRole.*;

import java.util.HashMap;
import java.util.Map;

import com.porcupine.color.RGB;


public class ColorScheme {

	public static ColorScheme getForTheme(ETheme theme)
	{
		switch (theme) {
			case WIDGET:
				return widget;

			case TEXT:
				return text;

			case BUTTON:
				return button;

			case BUTTON_LAUNCH:
				return buttonLaunch;

			case MENU_BUTTON:
				return menuButton;

			case MENU_TITLE:
				return menuTitle;
		}

		return widget;
	}

	private static ColorScheme text;
	private static ColorScheme widget;
	private static ColorScheme button;
	private static ColorScheme buttonLaunch;
	private static ColorScheme menuButton;
	private static ColorScheme menuTitle;


	public static void init()
	{
		ColorScheme cs;

		//@formatter:off
		
//		menuButton = cs = new ColorScheme();
//		cs.base.put(FG, new RGB(0x0B74E3));
//		cs.base.put(SHADOW, new RGB(0x003185, 0.2));	
//		
//		cs.hover.put(FG, new RGB(0x02E9FA));
//		cs.hover.put(SHADOW, new RGB(0x00A8FC, 0.05));
		
		
		// remade
		menuButton = cs = new ColorScheme();
		cs.base.put(FG, new RGB(0xd4d4d4));
		cs.base.put(SHADOW, new RGB(0x000000, 0.1));
		
		cs.hover.put(FG, new RGB(0xe4e4e4));
		cs.hover.put(SHADOW, new RGB(0x000000, 0.1));
		
		cs.clicked.put(FG, new RGB(0x989898));
		cs.clicked.put(SHADOW, new RGB(0x000000, 0.1));
		
		
		// remade
		menuTitle = cs = new ColorScheme();
		cs.base.put(FG, new RGB(0xffffff));
		cs.base.put(SHADOW, new RGB(0xffffff, 0.2));
		
		
		text = cs = new ColorScheme();
		cs.base.put(FG, new RGB(0x101010));
		cs.base.put(SHADOW, new RGB(0xffffff, 0.1));
				
		// remade
		widget = cs = new ColorScheme();			
		cs.setBase(
			new RGB(0x2f2f2f, 1), // bdr
			new RGB(0x9f9f9f, 0.8), // bg
			new RGB(0x101010, 1)  // fg
		);
		cs.setHover(
			new RGB(0x2f2f2f, 1), // bdr
			new RGB(0xbfbfbf, 0.8), // bg
			new RGB(0x101010, 1)  // fg
		);
		cs.setClicked(
			new RGB(0x2f2f2f, 1), // bdr
			new RGB(0x8f8f8f, 0.8), // bg
			new RGB(0x101010, 1)  // fg
		);
		cs.setSelected(
			new RGB(0x2f2f2f, 1), // bdr
			new RGB(0xbfbfff, 0.8), // bg
			new RGB(0x101010, 1)  // fg
		);
		

		button = cs = new ColorScheme();
		cs.base.put(FG, new RGB(0xefefef, 1));
		cs.hover.put(FG, new RGB(0xffffff, 1));
		cs.clicked.put(FG, new RGB(0xdfdfdf, 1));
		cs.selected.put(FG, new RGB(0xefefef, 1));
		cs.base.put(SHADOW, new RGB(0, 0.1));
		

		buttonLaunch = cs = new ColorScheme();
		cs.base.put(FG, new RGB(0x33ff33, 1));
		cs.hover.put(FG, new RGB(0x00ff00, 1));
		cs.clicked.put(FG, new RGB(0x00cc00, 1));
		cs.base.put(SHADOW, new RGB(0, 0.1));
				
		//@formatter:on

	}

	private Map<EColorRole, RGB> base = new HashMap<EColorRole, RGB>();
	private Map<EColorRole, RGB> hover = new HashMap<EColorRole, RGB>();
	private Map<EColorRole, RGB> clicked = new HashMap<EColorRole, RGB>();
	private Map<EColorRole, RGB> selected = new HashMap<EColorRole, RGB>();


	// constructor
	public ColorScheme() {}


	public ColorScheme(RGB border, RGB background, RGB foreground) {
		setBase(border, background, foreground);
		setHover(border, background, foreground);
		setClicked(border, background, foreground);
		setSelected(border, background, foreground);
	}


	public ColorScheme(RGB border, RGB background) {
		setBase(border, background, RGB.BLACK);
		setHover(border, background, RGB.BLACK);
		setClicked(border, background, RGB.BLACK);
		setSelected(border, background, RGB.BLACK);
	}


	public ColorScheme setBase(RGB border, RGB background, RGB foreground)
	{
		base.put(BDR, border);
		base.put(BG, background);
		base.put(FG, foreground);
		return this;
	}


	public ColorScheme setHover(RGB border, RGB background, RGB foreground)
	{
		hover.put(BDR, border);
		hover.put(BG, background);
		hover.put(FG, foreground);
		return this;
	}


	public ColorScheme setClicked(RGB border, RGB background, RGB foreground)
	{
		clicked.put(BDR, border);
		clicked.put(BG, background);
		clicked.put(FG, foreground);
		return this;
	}


	public ColorScheme setSelected(RGB border, RGB background, RGB foreground)
	{
		selected.put(BDR, border);
		selected.put(BG, background);
		selected.put(FG, foreground);
		return this;
	}


	private RGB fallback(RGB... options)
	{
		for (RGB color : options) {
			if (color != null) return color;
		}
		System.out.println(base);
		return RGB.RED; // error color
	}


	public RGB getColor(EColorRole role, boolean panelActive, boolean enabled, boolean isHover, boolean isClicked, boolean isSelected)
	{
		if (!enabled) {
			if (isSelected) return fallback(selected.get(role), base.get(role)).mulAlpha(0.3);
			return base.get(role).mulAlpha(0.3);
		}

		if (panelActive) {
			if (isClicked) {
				if (isSelected) return fallback(selected.get(role), clicked.get(role), hover.get(role), base.get(role));
				return fallback(clicked.get(role), hover.get(role), base.get(role));
			}

			if (isHover) {
				if (isSelected) return fallback(selected.get(role), hover.get(role), base.get(role));
				return fallback(hover.get(role), base.get(role));
			}
		}

		if (isSelected) return fallback(selected.get(role), base.get(role));

		return fallback(base.get(role));
	}

}
