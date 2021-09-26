package manhuntgame.app;

import basewindow.BasePlatformHandler;
import org.robovm.apple.corelocation.CLLocation;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.objc.Selector;

public class IOSPlatformHandler extends BasePlatformHandler
{
    @Override
    public void quit()
    {
        try
        {
            UIApplication.getSharedApplication().performSelector(Selector.register("suspend"));
            Thread.sleep(1000);
            System.exit(0);
        }
        catch (Exception e)
        {
            System.exit(0);
        }
    }

    @Override
    public void openLink(String uri)
    {
        NSURL url = new NSURL(uri);
        UIApplication.getSharedApplication().openURL(url, null, null);
    }

    @Override
    public void updateLocation()
    {
        CLLocation l = IOSLauncher.locationManager.getLocation();

        if (l == null)
        {
            Location.latitiude = 0;
            Location.longitude = 0;
            Location.altitude = 0;
        }
        else
        {
            Location.latitiude = l.getCoordinate().getLatitude();
            Location.longitude = l.getCoordinate().getLongitude();
            Location.altitude = l.getAltitude();
        }
    }
}
