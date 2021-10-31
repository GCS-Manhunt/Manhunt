package manhuntgame.app;

import basewindow.BasePlatformHandler;
import basewindow.BaseVibrationPlayer;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import libgdxwindow.LibGDXFileManager;
import libgdxwindow.LibGDXSoundPlayer;
import libgdxwindow.LibGDXWindow;

public class ManhuntGameApp extends ApplicationAdapter
{
	public static ManhuntGameApp instance;
	public static LibGDXWindow window;

	public static BaseVibrationPlayer vibrationPlayer;
	public static BasePlatformHandler platformHandler;
	public static IKeyboardHeightListener keyboardHeightListener;

	public static double pointWidth = -1;
	public static double pointHeight = -1;

	public static Application.ApplicationType appType;

	public static void initialize()
	{
		App.app = new App(new LibGDXFileManager());
		window = new LibGDXWindow("Manhunt Game", 1080, 1920, 1000, App.app, App.app, App.app, true, true);
		window.appType = appType;
		App.app.window = window;
	}

	@Override
	public void create()
	{
		instance = this;

		window.absoluteWidth = Gdx.graphics.getWidth();
		window.absoluteHeight = Gdx.graphics.getHeight();
		window.absoluteDepth = 1000;

		window.initialize();

		App.app.window.vibrationPlayer = vibrationPlayer;
		App.app.window.vibrationsEnabled = vibrationPlayer != null;
		App.app.window.platformHandler = platformHandler;

		window.touchscreen = true;

		window.pointWidth = pointWidth;
		window.pointHeight = pointHeight;

		if (Gdx.app.getType() == Application.ApplicationType.Android)
			keyboardHeightListener.init();
	}

	@Override
	public void resize(int width, int height)
	{
		window.absoluteWidth = width;
		window.absoluteHeight = height;

		window.updatePerspective();
	}

	@Override
	public void render()
	{
		window.render();
	}

	@Override
	public void dispose()
	{
		App.app.window.windowHandler.onWindowClose();
	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{
		window.lastFrame = System.currentTimeMillis();

		LibGDXSoundPlayer soundPlayer = (LibGDXSoundPlayer) window.soundPlayer;
		if (soundPlayer.musicID != null && soundPlayer.combinedMusicMap.get(soundPlayer.musicID) != null)
		{
			float pos = soundPlayer.currentMusic.getPosition();
			long time = System.currentTimeMillis();

			for (Music m: soundPlayer.combinedMusicMap.get(soundPlayer.musicID))
			{
				m.stop();
			}

			for (Music m: soundPlayer.combinedMusicMap.get(soundPlayer.musicID))
			{
				m.setPosition(pos + (System.currentTimeMillis() - time) / 1000f);
				m.play();
			}
		}
	}
}
