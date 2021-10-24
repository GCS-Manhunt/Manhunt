package manhuntgame.app;

import basewindow.*;
import com.badlogic.gdx.Game;
import manhuntgame.network.Client;
import manhuntgame.network.NetworkEventMap;
import manhuntgame.network.SynchronizedList;
import manhuntgame.network.event.*;
import manhuntgame.ui.screen.Screen;
import manhuntgame.ui.screen.ScreenMain;

import java.util.UUID;

public class App implements IUpdater, IDrawer, IWindowHandler
{
    public static App app;

    public BaseWindow window;
    public BaseFileManager fileManager;
    public Drawer drawer;

    public Screen screen = new ScreenMain();

    public static final SynchronizedList<INetworkEvent> eventsOut = new SynchronizedList<>();
    public static final SynchronizedList<INetworkEvent> eventsIn = new SynchronizedList<>();

    public static final int network_protocol = 0;
    public static UUID clientID = UUID.randomUUID();
    public static String username = "test";

    public App(BaseFileManager fileManager)
    {
        this.fileManager = fileManager;
        this.drawer = new Drawer();

        NetworkEventMap.register(EventPing.class);
        NetworkEventMap.register(EventSendClientDetails.class);
        NetworkEventMap.register(EventKick.class);
        NetworkEventMap.register(EventAcceptConnection.class);
        NetworkEventMap.register(EventSendPlayerIdentity.class);
    }

    /* This method fires every frame.
    *  Used to change the game state. */
    @Override
    public void update()
    {
        drawer.updateDimensions();

        if (app.window.platformHandler != null)
           app.window.platformHandler.updateLocation();

        synchronized (eventsIn)
        {
            for (INetworkEvent e: eventsIn)
            {
                e.execute();
            }

            eventsIn.clear();
        }

        screen.update();

        if (Client.handler != null)
        {
            eventsOut.add(new EventSendLocation(Location.longitude, Location.latitude, Location.altitude));
            Client.handler.reply();
        }
    }

    /* This method fires every frame after update().
     * Used to draw the game state while not modifying it. */
    @Override
    public void draw()
    {
        screen.draw();
    }

    /* This method fires when the app is closed */
    @Override
    public void onWindowClose()
    {

    }
}
