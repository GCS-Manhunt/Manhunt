package manhuntgame.app;

import android.hardware.SensorEvent;
import android.os.Looper;
import basewindow.BasePlatformHandler;
import com.badlogic.gdx.Gdx;

public class AndroidPlatformHandler extends BasePlatformHandler
{
    public static boolean loaded = false;

    @Override
    public void quit()
    {
        System.exit(0);
    }

    @Override
    public void openLink(String url)
    {
        /*Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager pm = ((AndroidApplication) Gdx.app).getContext().getPackageManager();
        pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        ((AndroidApplication)Gdx.app).runOnUiThread(new Runnable() {
            @Override
            public void run () {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                // LiveWallpaper and Daydream applications need this flag
                if (!(((AndroidApplication)Gdx.app).getContext() instanceof Activity))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((AndroidApplication)Gdx.app).startActivity(intent);
            }
        });*/
    }

    @Override
    public void updateLocation()
    {
        if (AndroidLauncher.geomagneticField != null)
        {
            if (loaded)
                return;

            loaded = true;

            Looper.prepare();

            if (AndroidLauncher.instance.locationService != null)
                AndroidLauncher.instance.locationService.requestLocationUpdates();

            AndroidLauncher.declination = AndroidLauncher.geomagneticField.getDeclination();
        }
    }
}
