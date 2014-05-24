package net.tortuga.animations;


import static org.lwjgl.opengl.GL11.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import net.tortuga.App;
import net.tortuga.textures.TextureManager;
import net.tortuga.textures.Textures;
import net.tortuga.util.AnimDouble;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.HSV;
import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;
import com.porcupine.coord.Rect;
import com.porcupine.coord.Vec;


public class SnowAnimator implements IRenderer {

	private static final double FALL_SPEED = 0.3;
	private static Random rand = new Random();
	private Set<Snowflake> snowflakes = new HashSet<Snowflake>();

	private static class Snowflake {

		CoordI index = new CoordI(rand.nextInt(4), rand.nextInt(4));
		int width = 64;
		AnimDouble angle = new AnimDouble(rand.nextInt(360));
		double rotation = (0.2 + rand.nextDouble() * 0.7) * (rand.nextBoolean() ? 1 : -1);
		int appW = (int) App.inst.getSize().x;
		Coord pos = new Coord(-appW / 2 + rand.nextInt(appW), -20);
		Vec move = new Vec(-0.2 + rand.nextDouble() * 0.4, FALL_SPEED);
		double size = (1 + rand.nextDouble() * 0.4) * 22;
		Rect rect = new Rect().grow_ip(0.5, 0.5);

		Rect txCoord = new Rect(index.x * width, index.y * width, (index.x + 1) * width, (index.y + 1) * width);

		RGB color = new HSV(0.597 - 0.05 + rand.nextDouble() * 0.1, 0.14 * rand.nextDouble(), 1).toRGB();


		public void render(double delta)
		{
			pos.update(delta);
			angle.update(delta);

			if (pos.isFinished()) {
				pos.remember();
				pos.add_ip(move);
				pos.animate(0.02);
			}

			if (angle.isFinished()) {
				angle.addValue(rotation, 0.03);
			}

			glPushMatrix();
			Coord coord = pos.getDelta().mul(1, -1, 1).add(0, App.inst.getSize().y);
			glTranslated(App.inst.getSize().x / 2 + coord.x, coord.y, coord.z);
			glRotated(angle.delta(), 0, 0, 1);
			glScaled(size, size, size);
			RenderUtils.setColor(color);
			RenderUtils.quadTexturedAbs(rect, txCoord.div(256));
			glPopMatrix();

		}
	}


	public SnowAnimator() {
		initFill();
	}


	private void initFill()
	{
		for (int i = 0; i < 50 + (App.isFullscreen() ? 50 : 0); i++) {
			Snowflake sf;
			snowflakes.add(sf = new Snowflake());
			int appW = (int) App.inst.getSize().x;
			int appH = (int) App.inst.getSize().y;
			sf.pos.setTo(-appW / 2 + rand.nextInt(appW), rand.nextInt(appH));
		}
	}


	@Override
	public void onFullscreenChange()
	{
		snowflakes.clear();
		initFill();
	}


	@Override
	public void updateGui()
	{
		Rect screen = new Rect(App.inst.getSize()).grow_ip(30, 30);

		Iterator<Snowflake> i = snowflakes.iterator();
		while (i.hasNext()) {
			Snowflake sf = i.next();
			if (sf.pos.y > screen.getSize().y) {
				i.remove();
			}
		}

	}


	@Override
	public void render(double delta)
	{
		if (rand.nextInt(5) == 0 && snowflakes.size() < (App.isFullscreen() ? 100 : 50)) {
			snowflakes.add(new Snowflake());
		}

		glPushAttrib(GL_ENABLE_BIT);

		glEnable(GL_TEXTURE_2D);
		RenderUtils.setColor(RGB.WHITE);

		TextureManager.bind(Textures.snow);
		for (Snowflake sf : snowflakes) {
			sf.render(delta);
		}

		glPopAttrib();

	}
}
