package manhuntgame.app;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFiles;

public class AndroidLauncher extends AndroidApplication
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.getFilesDir();
		Gdx.files = new AndroidFiles(this.getAssets(), this.getFilesDir().getAbsolutePath());
		ManhuntGameApp.appType = ApplicationType.Android;
		ManhuntGameApp.initialize();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.depth = 24;
		config.useImmersiveMode = true;
		config.useAccelerometer = false;
		config.useCompass = false;
		config.maxSimultaneousSounds = 64;

		//ManhuntGameApp.window.antialiasingEnabled = true;
		//config.numSamples = 4;

		ManhuntGameApp.keyboardHeightListener = new AndroidKeyboardHeightListener(this);
		ManhuntGameApp.vibrationPlayer = new AndroidVibrationPlayer();

		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		if (Build.VERSION.SDK_INT >= 30)
			this.getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;

		ManhuntGameApp.pointWidth = displayMetrics.widthPixels / displayMetrics.density;
		ManhuntGameApp.pointHeight = displayMetrics.heightPixels / displayMetrics.density;

		ManhuntGameApp.platformHandler = new AndroidPlatformHandler();

		initialize(new ManhuntGameApp(), config);
	}
}
