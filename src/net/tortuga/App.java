package net.tortuga;


import static org.lwjgl.opengl.GL11.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileLock;
import java.util.Calendar;
import java.util.Random;

import javax.swing.*;

import net.tortuga.animations.EmptyAnimator;
import net.tortuga.animations.IRenderer;
import net.tortuga.animations.LeavesAnimator;
import net.tortuga.animations.RainAnimator;
import net.tortuga.animations.SnowAnimator;
import net.tortuga.gui.screens.Screen;
import net.tortuga.gui.screens.ScreenMenuMain;
import net.tortuga.gui.screens.ScreenSplash;
import net.tortuga.input.Keys;
import net.tortuga.sounds.Effects;
import net.tortuga.sounds.SoundManager;
import net.tortuga.threads.ThreadSaveScreenshot;
import net.tortuga.util.Log;
import net.tortuga.util.Utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.porcupine.coord.Coord;
import com.porcupine.time.TimerDelta;
import com.porcupine.time.TimerInterpolating;
import com.porcupine.util.FileUtils;


/**
 * SECTOR main class
 * 
 * @author MightyPork
 */
public class App {

	/** instance */
	public static App inst;
	/** Current delta time (secs since last render) */
	public static double currentDelta = 0;

	private static DisplayMode windowDisplayMode = null;

	/** Ambient effect renderer */
	public static IRenderer weatherAnimation;

	/** current screen */
	public static Screen screen = null;

	/** Flag that screenshot is scheduled to be taken next loop */
	public static boolean scheduledScreenshot = false;

	/** Current weather animation */
	public static EWeather weather;


	private static boolean lockInstance()
	{
		final File lockFile = new File(Utils.getGameFolder(), ".lock");
		try {
			final RandomAccessFile randomAccessFile = new RandomAccessFile(lockFile, "rw");
			final FileLock fileLock = randomAccessFile.getChannel().tryLock();
			if (fileLock != null) {
				Runtime.getRuntime().addShutdownHook(new Thread() {

					@Override
					public void run()
					{
						try {
							fileLock.release();
							randomAccessFile.close();
							lockFile.delete();
						} catch (Exception e) {
							System.out.println("Unable to remove lock file.");
							e.printStackTrace();
						}
					}
				});
				return true;
			}
		} catch (Exception e) {
			System.out.println("Unable to create and/or lock file.");
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * Is if FS
	 * 
	 * @return is in fs
	 */
	public static boolean isFullscreen()
	{
		return Display.isFullscreen();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		inst = new App();
		try {
			inst.start();
		} catch (Throwable t) {
			showCrashReport(t);
		}

	}


	/**
	 * Show crash report dialog with error stack trace.
	 * 
	 * @param error
	 */
	public static void showCrashReport(Throwable error)
	{
		Log.e(error);

		try {
			inst.deinit();
		} catch (Throwable t) {}

		JFrame f = new JFrame("Tortuga has crashed!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));

		StringWriter sw = new StringWriter();
		error.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();

		String errorLogAsString = "Not found.";
		String wholeLogAsString = "Not found.";

		try {
			wholeLogAsString = FileUtils.fileToString(Utils.getGameSubfolder(Constants.FILE_LOG));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			errorLogAsString = FileUtils.fileToString(Utils.getGameSubfolder(Constants.FILE_LOG_E));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String txt = "";
		txt = "";
		txt += "TORTUGA GAME HAS CRASHED!\n";
		txt += "\n";
		txt += "Please report it to MightyPork:\n";
		txt += "\tE-mail: ondra@ondrovo.com\n";
		txt += "\tTwitter: #MightyPork (post log via pastebin.com)\n";
		txt += "\n";
		txt += "\n";
		txt += "Version: " + Constants.VERSION_NAME + "\n";
		txt += "\n";
		txt += "\n";
		txt += "### STACK TRACE ###\n";
		txt += "\n";
		txt += exceptionAsString + "\n";
		txt += "\n";
		txt += "\n";
		txt += "### ERROR LOG ###\n";
		txt += "\n";
		txt += errorLogAsString + "\n";
		txt += "\n";
		txt += "\n";
		txt += "### FULL LOG ###\n";
		txt += "\n";
		txt += wholeLogAsString + "\n";

		// Create Scrolling Text Area in Swing
		JTextArea ta = new JTextArea(txt, 20, 70);
		ta.setFont(new Font("Courier", 0, 16));
		ta.setMargin(new Insets(10, 10, 10, 10));
		ta.setEditable(false);
		ta.setLineWrap(false);
		JScrollPane sbrText = new JScrollPane(ta);
		sbrText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		sbrText.setWheelScrollingEnabled(true);
		sbrText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sbrText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Create Quit Button
		JButton btnQuit = new JButton("Quit");
		btnQuit.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(btnQuit);

		f.getContentPane().add(sbrText);
		f.getContentPane().add(buttonPane);

		// Close when the close button is clicked
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Display Frame
		f.pack(); // Adjusts frame to size of components

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation((dim.width - f.getWidth()) / 2, (dim.height - f.getHeight()) / 2);

		f.setVisible(true);

		while (true) {}
	}


	private void deinit()
	{
		Display.destroy();
		Mouse.destroy();
		Keyboard.destroy();
		SoundManager.player.clear();
		AL.destroy();
	}


	/**
	 * Quit to OS
	 */
	public void exit()
	{
		deinit();
		System.exit(0);
	}


	/**
	 * Get current screen
	 * 
	 * @return screen
	 */
	public Screen getScreen()
	{
		return screen;
	}


	/**
	 * Get screen size
	 * 
	 * @return size
	 */
	public Coord getSize()
	{
		return new Coord(Display.getWidth(), Display.getHeight());
	}


// INIT

	private void init() throws LWJGLException
	{
		GameConfig.initLoad();

		Log.enable(GameConfig.logEnabled);
		Log.setPrintToStdout(GameConfig.logStdOut);

		Log.i("Game version: " + Constants.VERSION_NAME);

		// init display
		Display.setDisplayMode(windowDisplayMode = new DisplayMode(Constants.WINDOW_SIZE_X, Constants.WINDOW_SIZE_Y));
		Display.setResizable(GameConfig.enableResize);
		Display.setVSyncEnabled(GameConfig.enableVsync);
		Display.setTitle(Constants.TITLEBAR);

		int samples = GameConfig.antialiasing;
		while (true) {
			try {
				Display.create(new PixelFormat().withSamples(samples).withAlphaBits(4));
				Log.i("Created display with " + samples + "x multisampling.");
				break;
			} catch (LWJGLException e) {
				Log.w("Failed to create display with " + samples + "x multisampling, trying " + samples / 2 + "x.");
				if (samples >= 2) {
					samples /= 2;
				} else if (samples == 1) {
					samples = 0;
				} else if (samples == 0) {
					Log.e("Could not create display.", e);
					exit();
				}
			}
		}

		SoundManager.player.setMaxSources(256);
		SoundManager.player.init();
		SoundManager.setListener(Constants.LISTENER_POS);

		// TODO from config
		SoundManager.volumeGui.set(1f);
		SoundManager.volumeEffects.set(1f);
		SoundManager.volumeWater.set(1f);
		SoundManager.volumeAmbients.set(1f);

		Mouse.create();
		Keyboard.create();
		Keyboard.enableRepeatEvents(false);
		Keys.init();

		LoadingManager.loadForSplash();

		StaticInitializer.initOnStartup();

		//Display.update();
		if (GameConfig.startInFullscreen) {
			switchFullscreen();
			Display.update();
		}

		new ThreadScreenshotTrigger().start();

		Random rand = new Random();

		if (rand.nextInt(3) == 0) {
			// chance to get no weather effect
			weatherAnimation = new EmptyAnimator();
			weather = EWeather.NONE;
		} else {
			// get weather based on month
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);

			int negMonth = month + 6;
			if (negMonth >= 12) negMonth -= 12;

			int auMonth = month - 4;
			if (auMonth < 0) auMonth += 12;

			int suMonth = month + 2;
			if (suMonth < 0) suMonth += 12;

			double snowChance = (0.4 + rand.nextDouble()) * (6 - Math.abs(negMonth - 6)) * 1.5;
			double rainChance = (0.3 + rand.nextDouble()) * (6 - Math.abs(suMonth - 6)) * 1.5;
			double leafChance = (0.3 + rand.nextDouble()) * (6 - Math.abs(auMonth - 6)) * 1.4;

			Log.f3("Weather chance:");
			Log.f3("SNOW = " + snowChance);
			Log.f3("RAIN = " + rainChance);
			Log.f3("LEAF = " + leafChance);

			if (snowChance > leafChance && snowChance >= rainChance) {
				weatherAnimation = new SnowAnimator();
				weather = EWeather.SNOW;

			} else if (rainChance >= snowChance && rainChance > leafChance) {
				weatherAnimation = new RainAnimator();
				weather = EWeather.RAIN;

			} else if (leafChance > rainChance && leafChance >= snowChance) {
				weatherAnimation = new LeavesAnimator();
				weather = EWeather.LEAVES;

			} else {
				weatherAnimation = new RainAnimator();
				weather = EWeather.RAIN;

			}
		}
	}


	private void start() throws LWJGLException
	{
		if (!lockInstance()) {
			System.out.println("No more than 1 instance of this game can be running at a time.");

			JOptionPane.showMessageDialog(null, "The game is already running.", "Instance error", JOptionPane.ERROR_MESSAGE);

			exit();
			return;
		}

		Log.enable(true);
		Log.setPrintToStdout(true);

		init();
		mainLoop();
		deinit();
	}

// INIT END

// UPDATE LOOP

	/** timer */
	private TimerDelta timerRender;
	private TimerInterpolating timerGui;

	private int timerAfterResize = 0;


	private void mainLoop()
	{
		screen = new ScreenSplash();

		screen.init();

		timerRender = new TimerDelta();
		timerGui = new TimerInterpolating(Constants.FPS_GUI);

		while (!Display.isCloseRequested()) {
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			timerGui.sync();

			int ticks = timerGui.getSkipped();

			if (ticks >= 1) {
				screen.updateGui();
				timerGui.startNewFrame();
			}

			double delta = timerRender.getDelta();

			currentDelta = delta;

			// RENDER
			screen.render(delta);
			SoundManager.update(delta);

			Display.update();

			if (scheduledScreenshot) {
				takeScreenshot();
				scheduledScreenshot = false;
			}

			if (Keys.justPressed(Keyboard.KEY_F11)) {
				Log.f2("F11, toggle fullscreen.");
				switchFullscreen();
				screen.onFullscreenChange();
				Keys.destroyChangeState(Keyboard.KEY_F11);
				weatherAnimation.onFullscreenChange();
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
					Log.f2("Ctrl+Q, force quit.");
					Keys.destroyChangeState(Keyboard.KEY_Q);
					exit();
					return;
				}

				if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
					Log.f2("Ctrl+M, go to main menu.");
					Keys.destroyChangeState(Keyboard.KEY_M);
					screen.rootPanel.onClose();
					screen.rootPanel.onBlur();
					replaceScreen(new ScreenMenuMain());
				}

				if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
					Log.f2("Ctrl+F, switch fullscreen.");
					Keys.destroyChangeState(Keyboard.KEY_F);
					switchFullscreen();
					screen.onFullscreenChange();
					weatherAnimation.onFullscreenChange();
				}
			}

			if (Display.wasResized()) {
				screen.onWindowResize();
				timerAfterResize = 0;
			} else {
				timerAfterResize++;
				if (timerAfterResize > Constants.FPS_GUI * 0.3) {
					timerAfterResize = 0;
					int x = Display.getX();
					int y = Display.getY();

					int w = Display.getWidth();
					int h = Display.getHeight();
					if (w % 2 != 0 || h % 2 != 0) {
						try {
							Display.setDisplayMode(windowDisplayMode = new DisplayMode(w - w % 2, h - h % 2));
							screen.onWindowResize();
							Display.setLocation(x, y);
						} catch (LWJGLException e) {
							e.printStackTrace();
						}
					}
				}
			}

			try {
				Display.sync(Constants.FPS_RENDER);
			} catch (Throwable t) {
				Log.e("Your graphics card driver does not support fullscreen properly.", t);

				try {
					Display.setDisplayMode(windowDisplayMode);
				} catch (LWJGLException e) {
					Log.e("Error going back from corrupted fullscreen.");
					showCrashReport(e);
				}
			}
		}
	}


// UPDATE LOOP END

	/**
	 * Do take a screenshot
	 */
	public void takeScreenshot()
	{
		Effects.play("gui.screenshot");

		glReadBuffer(GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		new ThreadSaveScreenshot(buffer, width, height, bpp).start();
	}


	/**
	 * Replace screen
	 * 
	 * @param newScreen new screen
	 */
	public void replaceScreen(Screen newScreen)
	{
		screen = newScreen;
		screen.init();
	}


	/**
	 * Replace screen, don't init it
	 * 
	 * @param newScreen new screen
	 */
	public void replaceScreenNoInit(Screen newScreen)
	{
		screen = newScreen;
	}


	/**
	 * Toggle FS if possible
	 */
	public void switchFullscreen()
	{
		try {
			if (!Display.isFullscreen()) {
				Log.f3("Entering fullscreen.");
				// save window resize
				windowDisplayMode = new DisplayMode(Display.getWidth(), Display.getHeight());

				Display.setDisplayMode(Display.getDesktopDisplayMode());
				Display.setFullscreen(true);
				Display.update();
//				
//				
//				DisplayMode mode = Display.getDesktopDisplayMode(); //findDisplayMode(WIDTH, HEIGHT);
//				Display.setDisplayModeAndFullscreen(mode);
			} else {
				Log.f3("Leaving fullscreen.");
				Display.setDisplayMode(windowDisplayMode);
				Display.update();
			}
		} catch (Throwable t) {
			Log.e("Failed to toggle fullscreen mode.", t);
			try {
				Display.setDisplayMode(windowDisplayMode);
				Display.update();
			} catch (Throwable t1) {
				showCrashReport(t1);
			}
		}
	}
}
