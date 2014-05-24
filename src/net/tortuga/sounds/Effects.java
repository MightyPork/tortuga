package net.tortuga.sounds;


import java.util.HashMap;
import java.util.Map;

import com.porcupine.mutable.MFloat;


/**
 * Audio effect manager
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Effects {

	private static Map<String, EffectPlayer> efects = new HashMap<String, EffectPlayer>();


	/**
	 * Init players
	 */
	public static void init()
	{
		MFloat gGui = SoundManager.volumeGui;
		MFloat gEff = SoundManager.volumeEffects;

		efects.put("gui.button.menu", new EffectPlayer(SoundManager.click_typewriter1, 1f, 0.3f, gGui));
		efects.put("gui.button.dialog", new EffectPlayer(SoundManager.click_typewriter2, 1f, 0.6f, gGui));
		efects.put("gui.switch", new EffectPlayer(SoundManager.click_switch, 1f, 0.3f, gGui));
		efects.put("gui.default", new EffectPlayer(SoundManager.click_button1, 1.4f, 0.3f, gGui));
		efects.put("gui.popup", new EffectPlayer(SoundManager.gui_popup, 1, 0.55f, gGui));
		efects.put("gui.screenshot", new EffectPlayer(SoundManager.gui_shutter, 1f, 1f, gGui));

		efects.put("gui.error", new EffectPlayer(SoundManager.gui_error, 1f, 0.7f, gGui));
		efects.put("gui.tray", new EffectPlayer(SoundManager.click_tray, 1f, 0.3f, gGui));
		efects.put("gui.open", new EffectPlayer(SoundManager.click_open, 1f, 0.3f, gGui));
		efects.put("gui.rattle", new EffectPlayer(SoundManager.click_rattle, 1f, 0.3f, gGui));
		efects.put("gui.gear1", new EffectPlayer(SoundManager.gui_gear1, 1f, 0.2f, gGui));
		efects.put("gui.gear2", new EffectPlayer(SoundManager.gui_gear2, 1f, 0.2f, gGui));

		efects.put("gui.program.grab", new EffectPlayer(SoundManager.click_button2, 1.2f, 0.3f, gGui));
		efects.put("gui.program.drop", new EffectPlayer(SoundManager.click_button2, 0.8f, 0.3f, gGui));
		efects.put("gui.program.delete", new EffectPlayer(SoundManager.click_button2, 0.4f, 0.3f, gGui));

		efects.put("turtle.bell", new EffectPlayer(SoundManager.turtle_bell, 1f, 0.7f, gEff));
		efects.put("turtle.eat", new EffectPlayer(SoundManager.turtle_eat, 1f, 0.7f, gEff));
		efects.put("block.drop", new EffectPlayer(SoundManager.crate_drop, 1f, 0.4f, gEff));
		efects.put("turtle.drop", new EffectPlayer(SoundManager.turtle_drop, 1f, 0.4f, gEff));
		efects.put("water.splash", new EffectPlayer(SoundManager.splash, 1f, 0.4f, gEff));
		efects.put("fruit.destroy", new EffectPlayer(SoundManager.break_fruit, 1f, 0.8f, gEff));
		efects.put("switch.toggle", new EffectPlayer(SoundManager.click_switch, 1f, 0.4f, gEff));

	}


	private static EffectPlayer getPlayer(String resource)
	{
		EffectPlayer pl = efects.get(resource);
		if (pl == null) {
			throw new IllegalArgumentException("Unknown sound effect: \"" + resource + "\"");
		}
		return pl;
	}


	/**
	 * Play a resource with default settings
	 * 
	 * @param resource resource ID
	 */
	public static void play(String resource)
	{
		getPlayer(resource).play(1);
	}


	/**
	 * Play resource with changed gain
	 * 
	 * @param resource resource ID
	 * @param gain gain
	 */
	public static void play(String resource, double gain)
	{
		getPlayer(resource).play(gain);
	}


	/**
	 * Play resource with changed pitch and gain
	 * 
	 * @param resource resource ID
	 * @param pitch pitch
	 * @param gain gain
	 */
	public static void play(String resource, double pitch, double gain)
	{
		getPlayer(resource).play(pitch, gain);
	}

}
