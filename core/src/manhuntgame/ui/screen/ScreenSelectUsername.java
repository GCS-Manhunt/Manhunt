package manhuntgame.ui.screen;

import basewindow.BaseFile;
import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.app.GameState;
import manhuntgame.network.Client;
import manhuntgame.ui.Button;
import manhuntgame.ui.TextBox;

import java.util.UUID;

public class ScreenSelectUsername extends Screen
{
    Thread clientThread;

    Button exit = new Button(540, 1750, 900, 200, "Back", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenMain();
        }
    });

    TextBox username = new TextBox(540, 750, 1080, 200, "Username", new Runnable()
    {
        @Override
        public void run()
        {
            App.username = username.inputText;

            BaseFile file = App.app.fileManager.getFile("username");

            try
            {
                if (!file.exists())
                    file.create();

                file.startWriting();
                file.println(username.inputText);
                file.stopWriting();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    },  App.username);

    public ScreenSelectUsername()
    {
        username.maxChars = 16;
        username.enableCaps = true;
        username.enableSpaces = false;

        BaseFile file = App.app.fileManager.getFile("username");

        if (file.exists())
        {
            try
            {
                file.startReading();
                username.inputText = file.nextLine();
                file.stopReading();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    Button next = new Button(540, 1000, 900, 200, "Next", new Runnable()
    {
        @Override
        public void run()
        {
           App.username = username.inputText;
           App.app.screen = new ScreenConnect();
        }
    }
    );

    @Override
    public void update()
    {
        next.enabled = username.inputText.length() > 0;

        exit.update();
        next.update();
        username.update();
    }

    @Override
    public void draw()
    {
        Drawer drawer = App.app.drawer;
        drawer.setFontSize(100);
        drawer.setColor(255, 0, 0);
        drawer.drawText(540, 100, "Enter username");

        exit.draw();
        next.draw();
        username.draw();
    }
}