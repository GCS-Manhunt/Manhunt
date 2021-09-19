package manhuntgame.app;

import basewindow.*;
import com.badlogic.gdx.Game;

public class App implements IUpdater, IDrawer, IWindowHandler
{
    public static App app;

    public BaseWindow window;
    public BaseFileManager fileManager;
    public Drawer drawer;

    public static String location = "null";

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
           app.window.platformHandler.updateLoc();
    }

    /* This method fires every frame after update().
     * Used to draw the game state while not modifying it. */
    @Override
    public void draw()
    {
        drawer.setColor(255, 0, 0);
        drawer.setFontSize(40);
        drawer.drawText(drawer.width / 2, drawer.height / 2, location);
    }

    /* This method fires when the app is closed */
    @Override
    public void onWindowClose()
    {

    }
}
