package manhuntgame.app;

import basewindow.InputCodes;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.backends.iosrobovm.IOSFiles;
import org.robovm.apple.corelocation.CLLocationManager;
import org.robovm.apple.foundation.*;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIRectEdge;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.objc.Selector;
import org.robovm.objc.annotation.Method;

public class IOSLauncher extends IOSApplication.Delegate
{
    public static IOSApplicationConfiguration config;
    public static CLLocationManager locationManager = new CLLocationManager();

    @Override
    protected IOSApplication createApplication()
    {
        Gdx.files = new IOSFiles();
        ManhuntGameApp.appType = Application.ApplicationType.iOS;
        ManhuntGameApp.initialize();

        config = new IOSApplicationConfiguration();
        config.hideHomeIndicator = true;
        config.screenEdgesDeferringSystemGestures = UIRectEdge.All;
        config.useAccelerometer = false;
        config.useCompass = false;
        config.allowIpod = true;

        //config.multisample = GLKViewDrawableMultisample._4X;
        //ManhuntGameApp.window.antialiasingEnabled = true;

        try
        {
            ManhuntGameApp.vibrationPlayer = new IOSVibrationPlayer();
        }
        catch (Exception ignored) {}

        ManhuntGameApp.platformHandler = new IOSPlatformHandler();

        ManhuntGameApp.pointWidth = UIScreen.getMainScreen().getBounds().getWidth();
        ManhuntGameApp.pointHeight = UIScreen.getMainScreen().getBounds().getHeight();

        NSNotificationCenter.getDefaultCenter().addObserver(this, Selector.register("keyboardWillShow"), "UIKeyboardWillChangeFrameNotification", null);
        NSNotificationCenter.getDefaultCenter().addObserver(this, Selector.register("keyboardWillHide"), "UIKeyboardWillHideNotification", null);

        locationManager.requestWhenInUseAuthorization();

        return new IOSApplication(new ManhuntGameApp(), config);
    }

    @Method(selector = "keyboardWillShow")
    public void keyboardWillShow(NSNotification n)
    {
        NSDictionary dict = n.getUserInfo();
        double keyboardFrame = ((NSValue) dict.get("UIKeyboardFrameEndUserInfoKey")).rectValue().getHeight();

        ManhuntGameApp.window.keyboardFraction = 1 - (keyboardFrame / UIScreen.getMainScreen().getBounds().getHeight());
    }

    @Method(selector = "keyboardWillHide")
    public void keyboardWillHide(NSNotification n)
    {
        if (ManhuntGameApp.window.showKeyboard)
            ManhuntGameApp.window.validPressedKeys.add(InputCodes.KEY_ESCAPE);
    }

    public static void main(String[] argv)
    {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);

        pool.close();
    }
}