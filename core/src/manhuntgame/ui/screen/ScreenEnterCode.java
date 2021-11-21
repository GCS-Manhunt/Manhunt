package manhuntgame.ui.screen;

import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.app.GameState;
import manhuntgame.app.HeadingTracker;
import manhuntgame.app.Location;
import manhuntgame.app.PlayerHeading;
import manhuntgame.ui.Button;
import manhuntgame.ui.TextBox;

// DEPRECATED, USE ScreenInputCode INSTEAD

public class ScreenEnterCode extends Screen {
    Thread ClientThread; // use this for sending the text and such

    TextBox enter = new TextBox(540, 750, 1080, 200, "Enter Code", new Runnable()
    {
        @Override
        public void run()
        {

        }
    }, "");

    Button confirm = new Button(540, 1000, 900, 200, "Confirm", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenSeeker();
            /*
            // sample code for sending the text to check
            if it's the same as the hider code. feel free to change as necessary

            clientThread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        ScreenConnecting s = new ScreenVerify(clientThread);
                        App.app.screen = s;
                        etc.
                    }
                }
            */
        }
    });

    public ScreenEnterCode () {
        enter.allowDots = true;
        enter.maxChars = 6;
        enter.allowColons = true;
        enter.lowerCase = true;
    }

    @Override
    public void update()
    {
        enter.update();
        confirm.update();
    }

    @Override
    public void draw()
    {
        enter.draw();
        confirm.draw();
    }
}