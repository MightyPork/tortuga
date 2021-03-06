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


public class LeavesAnimator implements IRenderer {

	private static final double FALL_SPEED = 0.3;
	private static Random rand = new Random();
	private Set<Leaf> leaves = new HashSet<Leaf>();

	private static class Leaf {

		CoordI index = new CoordI(rand.nextInt(4), rand.nextInt(4));
		int width = 64;
		AnimDouble angle = new AnimDouble(rand.nextInt(360));
		double rotation = (0.3 + rand.nextDouble() * 0.9) * (rand.nextBoolean() ? 1 : -1);
		int appW = (int) App.inst.getSize().x;
		Coord pos = new Coord(-appW / 2 + rand.nextInt(appW), -20 - rand.nextInt(100));
		Vec move = new Vec(-0.8 + rand.nextDouble() * 1.6, FALL_SPEED - 0.07 + rand.nextDouble() * 0.15);
		double size = (1.7 + rand.nextDouble() * 1) * 22;
		Rect rect = new Rect().grow_ip(0.5, 0.5);

		Rect txCoord = new Rect(index.x * width, index.y * width, (index.x + 1) * width, (index.y + 1) * width);

		RGB color = new HSV(0.05 + rand.nextDouble() * 0.21, 0.95, 0.5 + 0.20 * rand.nextDouble()).toRGB();


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


	public LeavesAnimator() {
		initFill();
	}


	private void initFill()
	{
		for (int i = 0; i < 30 + (App.isFullscreen() ? 30 : 0); i++) {
			Leaf sf;
			leaves.add(sf = new Leaf());
			int appW = (int) App.inst.getSize().x;
			int appH = (int) App.inst.getSize().y;
			sf.pos.setTo(-appW / 2 + rand.nextInt(appW), rand.nextInt(appH));
		}
	}


	@Override
	public void onFullscreenChange()
	{
		leaves.clear();
		initFill();
	}


	@Override
	public void updateGui()
	{
		Rect screen = new Rect(App.inst.getSize()).grow_ip(30, 30);

		Iterator<Leaf> i = leaves.iterator();
		while (i.hasNext()) {
			Leaf sf = i.next();
			if (sf.pos.y > screen.getSize().y) {
				i.remove();
			}
		}

	}


	@Override
	public void render(double delta)
	{
		if (leaves.size() < (App.isFullscreen() ? 66 : 36)) {
			leaves.add(new Leaf());
		}

		glPushAttrib(GL_ENABLE_BIT);

		glEnable(GL_TEXTURE_2D);
		RenderUtils.setColor(RGB.WHITE);

		TextureManager.bind(Textures.leaves);
		for (Leaf sf : leaves) {
			sf.render(delta);
		}

		glPopAttrib();

	}
}
