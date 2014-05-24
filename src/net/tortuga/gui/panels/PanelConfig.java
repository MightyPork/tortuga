package net.tortuga.gui.panels;


import net.tortuga.GameConfig;
import net.tortuga.gui.screens.Screen;
import net.tortuga.gui.widgets.Theme;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.display.Text;
import net.tortuga.gui.widgets.input.Button;
import net.tortuga.gui.widgets.input.Checkbox;
import net.tortuga.gui.widgets.input.Slider;
import net.tortuga.gui.widgets.layout.Gap;
import net.tortuga.gui.widgets.layout.LayoutH;
import net.tortuga.gui.widgets.layout.LayoutV;
import net.tortuga.gui.widgets.layout.frame.FrameWindow;
import net.tortuga.sounds.SoundManager;
import net.tortuga.util.Align;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.porcupine.color.RGB;


/**
 * Overlay panel for paused game.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class PanelConfig extends PanelGui {

	// FIXME add sliders for volume, add "toggle fullscreen" button

	private static final int SAVE = 0;
	private static final int CANCEL = 1;
	private Checkbox ckInitFullscreen;
	private Checkbox ckVsync;

	private int soundVol = GameConfig.audioVolumeSound;
	private int musicVol = GameConfig.audioVolumeMusic;
	private Slider sliderSound;
	private Slider sliderMusic;
	private Button bnCancel;
	private Button bnSave;
	private Checkbox ckResizable;
	private int origin;


	/**
	 * @param screen
	 * @param origin 0 = menu, 1 = pause screen
	 */
	public PanelConfig(Screen screen, int origin) {
		super(screen);
		this.origin = origin;
	}


	@Override
	public void initGui()
	{
		FrameWindow frame = Theme.mkWindow();

		addGui(frame);

		LayoutV v = new LayoutV(Align.CENTER, Align.CENTER);
		v.add(Theme.mkTitle("Settings"));

		LayoutV v2 = new LayoutV(Align.LEFT, Align.TOP);

		v2.add(ckInitFullscreen = new Checkbox(-1, "Start in fullscreen"));
		v2.add(ckVsync = new Checkbox(-1, "Enable v-sync"));
		v2.add(ckResizable = new Checkbox(-1, "Resizable window"));

		ckInitFullscreen.setChecked(GameConfig.startInFullscreen);
		ckVsync.setChecked(GameConfig.enableVsync);
		ckResizable.setChecked(GameConfig.enableResize);

		v2.add(new Gap(0, 15));

		LayoutH h2;

		h2 = new LayoutH(Align.LEFT, Align.CENTER);
		h2.add(new Text("Sound").setTextAlign(Align.RIGHT).setMinWidth(100));
		h2.add(new Gap(5, 0));
		h2.add(sliderSound = new Slider(250, soundVol / 100D));
		v2.add(h2);

		h2 = new LayoutH(Align.LEFT, Align.CENTER);
		h2.add(new Text("Music").setTextAlign(Align.RIGHT).setMinWidth(100));
		h2.add(new Gap(5, 0));
		h2.add(sliderMusic = new Slider(250, musicVol / 100D));
		v2.add(h2);
		v2.add(new Gap(0, 20));

		v.add(v2);

		LayoutH h = new LayoutH(Align.CENTER, Align.CENTER);
		h.add(bnSave = new Button(SAVE, "Save"));
		h.add(new Gap(10, 0));
		h.add(bnCancel = new Button(CANCEL, "Cancel"));
		v.add(h);

		frame.add(v);
	}


	@Override
	public void actionPerformed(Widget widget)
	{
		if (widget.id == CANCEL) {
			closePanel();
			return;
		}

		if (widget.id == SAVE) {
			GameConfig.setNewProp(GameConfig.pk_win_fs, ckInitFullscreen.isChecked());
			GameConfig.setNewProp(GameConfig.pk_vsync, ckVsync.isChecked());
			GameConfig.setNewProp(GameConfig.pk_win_resize, ckResizable.isChecked());

			GameConfig.setNewProp(GameConfig.pk_music_volume, Math.round(sliderMusic.getValue() * 100));
			GameConfig.setNewProp(GameConfig.pk_sound_volume, Math.round(sliderSound.getValue() * 100));

			GameConfig.saveLoad();
			GameConfig.useLoaded();

			// TODO from config
			SoundManager.volumeGui.set(1f);
			SoundManager.volumeEffects.set(1f);
			SoundManager.volumeWater.set(1f);
			SoundManager.volumeAmbients.set(1f);

			Display.setResizable(GameConfig.enableResize);

			closePanel();
			return;
		}

	}


	@Override
	public void onKey(int key, char c, boolean down)
	{
		super.onKey(key, c, down);

		if (key == Keyboard.KEY_ESCAPE && down) {
			actionPerformed(bnCancel);
		}

		if (key == Keyboard.KEY_RETURN && down) {
			actionPerformed(bnSave);
		}
	}


	@Override
	public void onFocus()
	{
		Keyboard.enableRepeatEvents(true);
	}


	@Override
	public void onBlur()
	{
		Keyboard.enableRepeatEvents(false);
	}


	@Override
	public boolean hasBackgroundLayer()
	{
		return true;
	}


	@Override
	public RGB getBackgroundColor()
	{
		return new RGB(0, 0.4);
	}

}
