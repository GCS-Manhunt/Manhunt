package manhuntgame.app;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.core.app.ActivityCompat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFiles;
import com.badlogic.gdx.backends.android.DefaultAndroidFiles;

public class AndroidLauncher extends AndroidApplication implements LocationListener
{
	public static GeomagneticField geomagneticField;
	public static SensorManager sensorManager;
	public static Sensor accelerometer;
	public static Sensor magnetometer;
	public static double declination;

	public static AndroidLauncher instance;

	public LocationService locationService;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		instance = this;
		super.onCreate(savedInstanceState);

		this.getFilesDir();
		Gdx.files = new DefaultAndroidFiles(this.getAssets(), this, true);
		ManhuntGameApp.appType = ApplicationType.Android;
		ManhuntGameApp.initialize();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.depth = 24;
		config.useImmersiveMode = false;
		config.useAccelerometer = false;
		config.useCompass = true;
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

		while (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{

		}

		permsGranted();

		ManhuntGameApp.pointWidth = displayMetrics.widthPixels / displayMetrics.density;
		ManhuntGameApp.pointHeight = displayMetrics.heightPixels / displayMetrics.density;

		ManhuntGameApp.platformHandler = new AndroidPlatformHandler();

		initialize(new ManhuntGameApp(), config);
	}

	public void permsGranted()
	{
		//LocationManager lm = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		//lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		CompassActivity ca = new CompassActivity();
		sensorManager.registerListener(ca, accelerometer, 100000);
		sensorManager.registerListener(ca, magnetometer, 100000);

		Intent intent = new Intent(this, LocationService.class);
		bindService(intent, new ServiceConnection()
		{
			@Override
			public void onServiceConnected(ComponentName name, IBinder service)
			{
				System.out.println("Connected");
				locationService = ((LocationService.LocalBinder)service).getService();
			}

			@Override
			public void onServiceDisconnected(ComponentName name)
			{
				System.out.println("Disconnected");
			}
		}, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onLocationChanged(Location location)
	{
		manhuntgame.app.Location.latitude = location.getLatitude();
		manhuntgame.app.Location.longitude = location.getLongitude();
		manhuntgame.app.Location.altitude = location.getAltitude();

		/*geomagneticField = new GeomagneticField(
				Double.valueOf(location.getLatitude()).floatValue(),
				Double.valueOf(location.getLongitude()).floatValue(),
				Double.valueOf(location.getAltitude()).floatValue(),
				System.currentTimeMillis()
		);*/
	}
}
