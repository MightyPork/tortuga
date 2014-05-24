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

import org.lwjgl.opengl.GL14;

import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;
import com.porcupine.coord.Vec;
import com.porcupine.math.Calc;


/**
 * Map water animator
 * 
 * @author MightyPork
 */
public class WaterAnimator implements IRenderer {

	private static Random rand = new Random();
	private Set<Circle> circles = new HashSet<Circle>();
	private long nsStart;

	private class Circle {

		int appW = (int) App.inst.getSize().x;
		int appH = (int) App.inst.getSize().y;
		Coord pos;

		AnimDouble size = new AnimDouble(1);

		boolean dead;

		Vec[] translates = { Vec.random(0, 0), Vec.random(200, 400), Vec.random(200, 400), Vec.random(200, 400) };

		Rect rect = new Rect().grow_ip(0.5, 0.5);


		public Circle() {
			pos = new Coord(-appW / 2 + rand.nextInt(appW), -appH / 2 + rand.nextInt(appH));
			pos.sub_ip(0, getCurrentWaterOffset().y);

			double maxS = (0.4 + rand.nextDouble() * 1.2) * 140;
			size.addValue(maxS, maxS / 30);
		}


		//RGB color = new HSV(0.597-0.05+rand.nextDouble()*0.1, 0.14*rand.nextDouble(), 1).toRGB();

		public void render(double delta)
		{
			size.update(delta);

			if (size.isFinished()) dead = true;

			glPushMatrix();
			Coord coord = pos;
			glTranslated(App.inst.getSize().x / 2 + coord.x, App.inst.getSize().y / 2 + coord.y, coord.z);

			RGB color = new RGB(0x123B82, Calc.clampd((1D - size.getRatio()) * 0.8, 0, 0.4));
			double sc = size.delta();
			RenderUtils.setColor(color);

			for (Vec v : translates) {
				glTranslated(v.x, v.y, 0);
				RenderUtils.quadTexturedAbs(rect.mul(sc, sc * 0.8), Rect.ONE);
			}

			glPopMatrix();

		}
	}


	/**
	 * Make new animator
	 */
	public WaterAnimator() {
		nsStart = System.nanoTime();
	}


	@Override
	public void onFullscreenChange()
	{}


	@Override
	public void updateGui()
	{
		Iterator<Circle> i = circles.iterator();
		while (i.hasNext()) {
			Circle sf = i.next();
			if (sf.dead) {
				i.remove();
			}
		}

	}

	/** Map offset (window offset) */
	public Coord offsetPlus = new Coord(0, 0);


	private Coord getCurrentWaterOffset()
	{
		double time = (System.nanoTime() - nsStart) / 1000000000D;

		double x = Math.sin(time / (30)) * 100D;
		double y = (time / (50)) * 256D;
		return offsetPlus.add(x, y);
	}


	@Override
	public void render(double delta)
	{
		if (circles.size() < (App.isFullscreen() ? 35 : 20)) {
			circles.add(new Circle());
		}

		glPushAttrib(GL_ENABLE_BIT);
		glPushMatrix();

		Rect quad = new Rect(App.inst.getSize());
		Rect quadT = new Rect(App.inst.getSize());
		quadT.add_ip(getCurrentWaterOffset().mul(-1, 1, 1));

		RenderUtils.quadTextured(quad, quadT, Textures.water);

		GL14.glBlendFuncSeparate(GL_ONE, GL_SRC_ALPHA, GL_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnable(GL_TEXTURE_2D);

		RenderUtils.translate(getCurrentWaterOffset());

		TextureManager.bind(Textures.circle);
		for (Circle sf : circles) {
			sf.render(delta);
		}

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glPopMatrix();
		glPopAttrib();

	}
}
