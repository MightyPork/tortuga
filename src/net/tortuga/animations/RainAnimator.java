package net.tortuga.animations;


import static org.lwjgl.opengl.GL11.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import net.tortuga.App;
import net.tortuga.textures.TextureManager;
import net.tortuga.textures.Textures;
import net.tortuga.util.RenderUtils;

import com.porcupine.color.HSV;
import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.CoordI;
import com.porcupine.coord.Rect;
import com.porcupine.coord.Vec;
import com.porcupine.math.Calc.Rad;


public class RainAnimator implements IRenderer {

	private static final Vec FALL_VEC = new Vec(-2, 10);
	private static Random rand = new Random();
	private Set<Raindrop> raindrops = new HashSet<Raindrop>();
	private long nsStart;

	private class Raindrop {

		CoordI index = new CoordI(rand.nextInt(4), rand.nextInt(4));
		int width = 64;

		int appW = (int) (App.inst.getSize().x * 1.3);
		Coord pos = new Coord(-appW / 2 + rand.nextInt(appW), -20 - rand.nextInt(100));

		double size = (2 + rand.nextDouble() * 0.3) * 20;
		Rect rect = new Rect().grow_ip(0.5, 0.5);

		Rect txCoord = new Rect(index.x * width, index.y * width, (index.x + 1) * width, (index.y + 1) * width);

		RGB color = new HSV(0.53 + rand.nextDouble() * 0.06, 0.72, 0.7 + 0.1 * rand.nextDouble()).toRGB();

		double time = (System.nanoTime() - nsStart) / 1000000000D;

		double x = Math.sin(time / (30));

		Vec move = (Vec) FALL_VEC.scale(0.7 + rand.nextDouble() * 0.6).mul_ip(x, 1, 1);

		Vec[] translates = { Vec.random(0, 0), Vec.random(200, 400), Vec.random(200, 400), Vec.random(200, 400) };


		public void render(double delta)
		{
			pos.update(delta);

			if (pos.isFinished()) {
				pos.remember();
				pos.add_ip(move);
				pos.animate(0.02);
			}

			glPushMatrix();
			Coord coord = pos.getDelta().mul(1, -1, 1).add(0, App.inst.getSize().y);
			glTranslated(App.inst.getSize().x / 2 + coord.x, coord.y, coord.z);
			glRotated(Rad.toDeg(Math.atan(move.x / move.y)), 0, 0, 1);
			glScaled(size, size, size);
			RenderUtils.setColor(color);
			for (Vec v : translates) {
				glTranslated(v.x, v.y, 0);
				RenderUtils.quadTexturedAbs(rect, txCoord.div(256));
			}
			glPopMatrix();

		}
	}


	public RainAnimator() {
		nsStart = System.nanoTime();
		initFill();
	}


	private void initFill()
	{
		for (int i = 0; i < 250 + (App.isFullscreen() ? 100 : 0); i++) {
			Raindrop sf;
			raindrops.add(sf = new Raindrop());
			int appW = (int) App.inst.getSize().x;
			int appH = (int) App.inst.getSize().y;
			sf.pos.setTo(-appW / 2 + rand.nextInt(appW), rand.nextInt(appH));
		}
	}


	@Override
	public void onFullscreenChange()
	{
		raindrops.clear();
		initFill();
	}


	@Override
	public void updateGui()
	{
		Rect screen = new Rect(App.inst.getSize()).grow_ip(30, 30);

		Iterator<Raindrop> i = raindrops.iterator();
		while (i.hasNext()) {
			Raindrop sf = i.next();
			if (sf.pos.y > screen.getSize().y) {
				i.remove();
			}
		}

	}


	@Override
	public void render(double delta)
	{
		if (raindrops.size() < (App.isFullscreen() ? 350 : 250)) {
			raindrops.add(new Raindrop());
		}

		glPushAttrib(GL_ENABLE_BIT);

		glEnable(GL_TEXTURE_2D);
		RenderUtils.setColor(RGB.WHITE);

		TextureManager.bind(Textures.rain);
		for (Raindrop sf : raindrops) {
			sf.render(delta);
		}

		glPopAttrib();

	}
}
