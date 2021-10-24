package manhuntgame.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.RequiresApi;

public class LocationService extends Service
{
    private static final String TAG = "GPS";

    private LocationManager mLocationManager = null;

    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 1f;

    private final IBinder mBinder = new LocalBinder();

    private static class LocationListener implements android.location.LocationListener
    {
        public LocationListener(String provider)
        {
            if (AndroidLauncher.instance != null)
                AndroidLauncher.instance.onLocationChanged(new Location(provider));
        }

        @Override
        public void onLocationChanged(Location location)
        {
            if (AndroidLauncher.instance != null)
                AndroidLauncher.instance.onLocationChanged(location);
        }

        @Override
        public void onProviderDisabled(String provider)
        {

        }

        @Override
        public void onProviderEnabled(String provider)
        {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0)
    {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate()
    {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel("c01", name, NotificationManager.IMPORTANCE_DEFAULT);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }

        initializeLocationManager();
        try
        {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        }
        catch (java.lang.SecurityException ex)
        {
            Log.i(TAG, "fail to request location update, ignore", ex);
        }
        catch (IllegalArgumentException ex)
        {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

        try
        {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        }
        catch (java.lang.SecurityException ex)
        {
            Log.i(TAG, "fail to request location update, ignore", ex);
        }
        catch (IllegalArgumentException ex)
        {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mLocationManager != null)
        {
            for (int i = 0; i < mLocationListeners.length; i++)
            {
                try
                {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                }
                catch (Exception ex)
                {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager()
    {
        if (mLocationManager == null)
        {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public class LocalBinder extends Binder
    {
        LocationService getService()
        {
            return LocationService.this;
        }
    }

    public void requestLocationUpdates() {
        Log.i(TAG, "Requesting location updates");
        startService(new Intent(getApplicationContext(), LocationService.class));
        try
        {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[1]);
        } catch (SecurityException unlikely)
        {
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }
}