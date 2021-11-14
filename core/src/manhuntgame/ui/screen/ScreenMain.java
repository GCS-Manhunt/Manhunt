package manhuntgame.ui.screen;

import manhuntgame.ui.Button;
import manhuntgame.app.App;
import manhuntgame.app.Drawer;

public class ScreenMain extends Screen
{
    // main screen. preferably this should replace ScreenMain once everything's done.
    /* we'll need a lot of buttons (subject to change):
    1) play -> opens up the way to multiple buttons:
    a) create game
    b) join game
    c) quit
    this can be on another screen (ScreenBeginGame)

    2) options/settings

    and some less important ones like help, about, quit, etc.
     */

    // button which goes to the old screen for debugging; can be removed once everything's said and done
    Button toDebug = new Button(540, 750, 900, 200, "Debug", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenDebug();
        }
    });


    Button play = new Button(540, 1000, 900, 200, "Play", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenConnect();
        }
    });

    Button settings = new Button(540, 1250, 900, 200, "Settings", new Runnable()
    {
        @Override
        public void run()
        {
            // App.app.screen = new ScreenSettings(); todo later
            App.app.screen = new ScreenBeginGame(); // temp debugging
        }
    });

    Button help = new Button(540, 1500, 900, 200, "About/Help", new Runnable()
    {
        @Override
        public void run()
        {
            // App.app.screen = new ScreenHelp(); just text, can deal with it later
        }
    });

    Button quit = new Button(540, 1750, 900, 200, "Quit", new Runnable()
    {
        @Override
        public void run()
        {
            if (App.app.window.platformHandler != null)
                App.app.window.platformHandler.quit();
            else
                System.exit(0);
        }
    });

    @Override
    public void update()
    {
        // update buttons and not much else
        toDebug.update();
        play.update();
        settings.update();
        help.update();
        quit.update();
    }

    @Override
    public void draw()
    {
        toDebug.draw();
        play.draw();
        settings.draw();
        help.draw();
        quit.draw();

        Drawer drawer = App.app.drawer;
        drawer.setFontSize(120);
        drawer.drawText(60, 100, "Manhunt Game", false);

    }
}
