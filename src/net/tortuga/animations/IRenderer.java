package net.tortuga.animations;


public interface IRenderer {

	public void updateGui();


	public void render(double delta);


	public void onFullscreenChange();
}
