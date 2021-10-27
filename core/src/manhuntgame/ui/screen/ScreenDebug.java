package manhuntgame.ui.screen;

import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.app.Location;
import manhuntgame.ui.Button;

public class ScreenDebug extends Screen
{
    Button connect = new Button(540, 1750, 900, 200, "Connect", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenConnect();
        }
    });

    Button testGame = new Button(540, 1500, 900, 200, "Test game", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenGamePreview("Manhunt Game", "CMU - The Fence", "Oct 16, 5:00-7:00PM", new String[]
                    {
                            "Please make sure to follow all campus rules.",
                            "Don't go in buildings that not everyone can access.",
                            "Have fun!"
                    });
        }
    });


    @Override
    public void update()
    {
        connect.update();
        testGame.update();
    }

    @Override
    public void draw()
    {
        connect.draw();
        testGame.draw();

        Drawer drawer = App.app.drawer;
        drawer.setColor(255, 0, 0);
        drawer.setFontSize(120);
        drawer.drawText(drawer.width / 2, drawer.height / 2 - 360, "GPS position:");
        drawer.drawText(drawer.width / 2, drawer.height / 2 - 240, Location.latitude + "");
        drawer.drawText(drawer.width / 2, drawer.height / 2 - 120, Location.longitude + "");
        drawer.drawText(drawer.width / 2, drawer.height / 2 + 0, Location.altitude + "");
        drawer.drawText(drawer.width / 2, drawer.height / 2 + 120, Location.compass + "");
    }
}