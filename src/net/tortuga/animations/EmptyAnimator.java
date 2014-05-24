package net.tortuga.animations;


/**
 * Empty animation (no effect)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class EmptyAnimator implements IRenderer {

	/**
	 * New empty animation
	 */
	public EmptyAnimator() {}


	@Override
	public void updateGui()
	{}


	@Override
	public void render(double delta)
	{}


	@Override
	public void onFullscreenChange()
	{}
}
