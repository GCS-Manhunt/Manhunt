package manhuntgame.app;

import basewindow.BasePlatformHandler;
import org.robovm.apple.corelocation.CLHeading;
import org.robovm.apple.corelocation.CLLocation;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIStatusBarStyle;
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
            Location.latitude = 0;
            Location.longitude = 0;
            Location.altitude = 0;
        }
        else
        {
            Location.latitude = l.getCoordinate().getLatitude();
            Location.longitude = l.getCoordinate().getLongitude();
            Location.altitude = l.getAltitude();
        }

        CLHeading heading = IOSLauncher.locationManager.getHeading();

        if (heading != null)
        {
            Location.compass = heading.getTrueHeading();
        }
        else
            Location.compass = 0;
    }
}
