package manhuntgame.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.core.app.ActivityCompat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFiles;

public class AndroidLauncher extends AndroidApplication implements LocationListener
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

		ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

		ManhuntGameApp.pointWidth = displayMetrics.widthPixels / displayMetrics.density;
		ManhuntGameApp.pointHeight = displayMetrics.heightPixels / displayMetrics.density;

		ManhuntGameApp.platformHandler = new AndroidPlatformHandler();

		LocationManager lm = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

		initialize(new ManhuntGameApp(), config);
	}

	@Override
	public void onLocationChanged(Location location)
	{
		manhuntgame.app.Location.latitiude = location.getLatitude();
		manhuntgame.app.Location.longitude = location.getLongitude();
		manhuntgame.app.Location.altitude = location.getAltitude();
	}
}
