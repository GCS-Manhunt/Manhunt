package manhuntgame.ui.screen;

import com.badlogic.gdx.Game;
import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.app.Location;
import manhuntgame.network.Client;
import manhuntgame.ui.Button;
import manhuntgame.ui.TextBox;

import java.util.UUID;

public class ScreenConnect extends Screen
{
  Thread clientThread;

  Button exit = new Button(540, 1750, 900, 200, "Exit", new Runnable()
  {
    @Override
    public void run()
    {
      App.app.screen = new ScreenMain();
    }
  });

  TextBox ip = new TextBox(540, 750, 1080, 200, "IP Address", new Runnable()
  {
    @Override
    public void run()
    {

    }
  }, "");

  public ScreenConnect()
  {
    ip.allowDots = true;
    ip.maxChars = 43;
    ip.allowColons = true;
    ip.lowerCase = true;
  }

  Button join = new Button(540, 1000, 900, 200, "Join", new Runnable()
  {
    @Override
    public void run()
    {
      App.eventsOut.clear();

      clientThread = new Thread(new Runnable()
      {

        @Override
        public void run()
        {
          ScreenConnecting s = new ScreenConnecting(clientThread);
          App.app.screen = s;

          UUID connectionID = UUID.randomUUID();
          Client.connectionID = connectionID;

          try
          {
            String ipaddress = ip.inputText;
            int port = 8080;

            if (ip.inputText.contains(":"))
            {
              int colon = ip.inputText.lastIndexOf(":");
              ipaddress = ip.inputText.substring(0, colon);
              port = Integer.parseInt(ip.inputText.substring(colon + 1));
            }

            if (ip.inputText.equals(""))
              Client.connect("localhost", 8080, connectionID);
            else
              Client.connect(ipaddress, port, connectionID);
          }
          catch (Exception e)
          {
            if (App.app.screen == s && Client.connectionID == connectionID)
            {
              s.text = "Failed to connect";
              s.exception = e.getLocalizedMessage();
              s.finished = true;

              e.printStackTrace();
            }
          }
        }

      });

      clientThread.setDaemon(true);
      clientThread.start();
    }
  }
  );

  @Override
  public void update()
  {
    exit.update();
    join.update();
    ip.update();
  }

  @Override
  public void draw()
  {
    exit.draw();
    join.draw();
    ip.draw();
  }
}