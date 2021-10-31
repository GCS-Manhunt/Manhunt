package manhuntgame.app.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import manhuntgame.app.ManhuntGameApp;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		Gdx.files = new LwjglFiles();
		ManhuntGameApp.appType = Application.ApplicationType.Desktop;
		ManhuntGameApp.initialize();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.depth = 24;

		//config.samples = 4;
		//ManhuntGameApp.window.antialiasingEnabled = true;

		new LwjglApplication(new ManhuntGameApp(), config);
	}
}
