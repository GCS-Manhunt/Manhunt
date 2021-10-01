package manhuntgame.ui.screen;

import com.badlogic.gdx.Game;
import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.app.Location;
import manhuntgame.ui.Button;
import manhuntgame.ui.TextBox;

public class ScreenMain extends Screen
{
    Button connect = new Button(540, 1700, 700, 80, "Connect", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenConnect();
        }
    });


    @Override
    public void update()
    {
        connect.update();
    }

    @Override
    public void draw()
    {
        connect.draw();

        Drawer drawer = App.app.drawer;
        drawer.setColor(255, 0, 0);
        drawer.setFontSize(60);
        drawer.drawText(drawer.width / 2, drawer.height / 2 - 120, "GPS position:");
        drawer.drawText(drawer.width / 2, drawer.height / 2 - 60, Location.latitiude + "");
        drawer.drawText(drawer.width / 2, drawer.height / 2, Location.longitude + "");
        drawer.drawText(drawer.width / 2, drawer.height / 2 + 60, Location.altitude + "");
    }
}
