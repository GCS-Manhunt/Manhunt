package manhuntgame.app;

import basewindow.*;
import manhuntgame.network.SynchronizedList;
import manhuntgame.network.event.INetworkEvent;

import java.util.UUID;

public class App implements IUpdater, IDrawer, IWindowHandler
{
    public static App app;

    public BaseWindow window;
    public BaseFileManager fileManager;
    public Drawer drawer;

    public static final SynchronizedList<INetworkEvent> eventsOut = new SynchronizedList<>();
    public static final SynchronizedList<INetworkEvent> eventsIn = new SynchronizedList<>();

    public static final int network_protocol = 0;
    public static UUID clientID;
    public static String username;

    public App(BaseFileManager fileManager)
    {
        this.fileManager = fileManager;
        this.drawer = new Drawer();
    }

    /* This method fires every frame.
    *  Used to change the game state. */
    @Override
    public void update()
    {
        drawer.updateDimensions();

        if (app.window.platformHandler != null)
           app.window.platformHandler.updateLocation();
    }

    /* This method fires every frame after update().
     * Used to draw the game state while not modifying it. */
    @Override
    public void draw()
    {
        drawer.setColor(255, 0, 0);
        drawer.setFontSize(60);
        drawer.drawText(drawer.width / 2, drawer.height / 2 - 60, Location.latitiude + "");
        drawer.drawText(drawer.width / 2, drawer.height / 2, Location.longitude + "");
        drawer.drawText(drawer.width / 2, drawer.height / 2 + 60, Location.altitude + "");

    }

    /* This method fires when the app is closed */
    @Override
    public void onWindowClose()
    {

    }
}
