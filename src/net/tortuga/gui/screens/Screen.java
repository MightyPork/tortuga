package net.tortuga.gui.screens;


import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import net.tortuga.App;
import net.tortuga.animations.IRenderer;
import net.tortuga.annotations.Unimplemented;
import net.tortuga.gui.panels.Panel;
import net.tortuga.gui.panels.PanelEmpty;
import net.tortuga.input.Keys;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Vec;


/**
 * Screen class.<br>
 * Screen animates 3D world, while contained panels render 2D overlays, process
 * inputs and run the game logic.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class Screen implements IRenderer {

	/** application instance, for easier calls */
	public App app = App.inst;

	/** root panel */
	public Panel rootPanel = new PanelEmpty(this);

	/** RNG */
	public Random rand = new Random();


	/**
	 * Set screen root panel
	 * 
	 * @param panel root panel
	 */
	public final void setRootPanel(Panel panel)
	{
		this.rootPanel = panel;
	}


	/**
	 * handle fullscreen change
	 */
	@Override
	public final void onFullscreenChange()
	{
		onWindowResize();
		rootPanel.onViewportChanged();
	}


	/**
	 * handle window resize.
	 */
	public void onWindowResize()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		Coord s = app.getSize();

		glViewport(0, 0, s.xi(), s.yi());

		glOrtho(0, s.x, 0, s.y, -1000, 1000);

		glMatrixMode(GL_MODELVIEW);

		glLoadIdentity();

		glEnable(GL_BLEND);
		//glDisable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);

		rootPanel.onViewportChanged();
	}


	/**
	 * Initialize screen
	 */
	public void init()
	{
		onWindowResize();

		initScreen();

		rootPanel.onCreate();
		rootPanel.onFocus();

		// SETUP LIGHTS

		glDisable(GL_LIGHTING);

		// OTHER SETTINGS

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glClearDepth(1f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		glEnable(GL_NORMALIZE);

		glShadeModel(GL_SMOOTH);
		glDisable(GL_TEXTURE_2D);

	}


	/**
	 * Here you can initialize the screen.
	 */
	public abstract void initScreen();


	/**
	 * Update tick
	 */
	@Override
	public final void updateGui()
	{
		Mouse.poll();
		Keyboard.poll();
		checkInputEvents();

		getFocusedPanel().updateGui();

		onGuiUpdate();

		app.weatherAnimation.updateGui();
	}


	@Unimplemented
	protected void onGuiUpdate()
	{}


	/**
	 * Render screen
	 * 
	 * @param delta delta time (position between two update ticks, to allow
	 *            super-smooth animations)
	 */
	@Override
	public final void render(double delta)
	{
		glPushAttrib(GL_ENABLE_BIT);

		// draw the directly rendered 3D stuff
		rootPanel.render3D();

		rootPanel.render(delta);

		glPopAttrib();
	}


	/**
	 * @return topmost panel which can handle inputs
	 */
	protected final Panel getFocusedPanel()
	{
		return rootPanel.getTop();
	}


	/**
	 * Check input events and process them.
	 */
	private final void checkInputEvents()
	{
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			boolean down = Keyboard.getEventKeyState();
			char c = Keyboard.getEventCharacter();
			Keys.onKey(key, down);
			getFocusedPanel().onKey(key, c, down);
		}
		while (Mouse.next()) {
			int button = Mouse.getEventButton();
			boolean down = Mouse.getEventButtonState();
			Coord delta = new Coord(Mouse.getEventDX(), Mouse.getEventDY());
			Coord pos = new Coord(Mouse.getEventX(), Mouse.getEventY());
			int wheeld = Mouse.getEventDWheel();

			getFocusedPanel().onMouseButton(button, down, wheeld, pos, delta);
		}

		int xc = Mouse.getX();
		int yc = Mouse.getY();
		int xd = Mouse.getDX();
		int yd = Mouse.getDY();
		int wd = Mouse.getDWheel();

		if (Math.abs(xd) > 0 || Math.abs(yd) > 0 || Math.abs(wd) > 0) {
			getFocusedPanel().onMouseMove(new Coord(xc, yc), new Vec(xd, yd), wd);
		}

		getFocusedPanel().handleStaticInputs();
	}


	/**
	 * Render background 2D (all is ready for rendering)
	 * 
	 * @param delta delta time
	 */
	@Unimplemented
	public void render2D(double delta)
	{}


	/**
	 * Get if ambient animation is enabled for this screen
	 * 
	 * @return
	 */
	public boolean isWeatherEnabled()
	{
		return true;
	}
}
