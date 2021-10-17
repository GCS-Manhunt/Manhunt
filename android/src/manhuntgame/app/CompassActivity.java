package manhuntgame.app;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CompassActivity extends Activity implements SensorEventListener
{
    public float[] mGravity;
    public float[] mGeomagnetic;
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null)
        {
            float[] r = new float[9];
            float[] i = new float[9];
            boolean success = SensorManager.getRotationMatrix(r, i, mGravity, mGeomagnetic);
            if (success)
            {
                float[] orientation = new float[3];
                SensorManager.getOrientation(r, orientation);
                manhuntgame.app.Location.compass = (360 + 180 * (orientation[0] + AndroidLauncher.declination) / Math.PI) % 360;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
