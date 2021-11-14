package manhuntgame.ui.screen;

import manhuntgame.app.*;
import manhuntgame.network.event.EventSendPlayerIdentity;
import manhuntgame.ui.Button;

import java.net.Inet4Address;

public class ScreenSeeker extends Screen
{
    public HeadingTracker headingTracker = GameState.game.headingTracker;
    // public scoreTracker scoreTracker = GameState.game.scoreTracker;
    // ^ make the above whatever is used to track the score

    // call for help button
    public Button call = new Button(540, 1500, 900, 200, "Request help", new Runnable()
    {
        @Override
        public void run()
        {
            // App.app.screen = new ScreenMain();
        }
    });

    // say someone's been found. just to be clear, ive no clue how you would do this
    public Button found = new Button(540, 1750, 900, 200, "Found hider", new Runnable()
    {
        @Override
        public void run()
        {
            // App.app.screen = new ScreenSeeker();
        }
    });

    public ScreenSeeker()
    {

    }

    @Override
    public void update()
    {
        headingTracker.update();
        // scoreTracker.update();

        call.update();
        found.update();
    }

    @Override
    public void draw()
    {
        double posX = 540;
        double posY = 540;
        double size = 800;

        Drawer d = App.app.drawer;
        d.setColor(40, 40, 40);
        d.fillOval(posX, posY, size, size);
        d.setColor(0, 0, 0);
        d.fillOval(posX, posY, size - 20, size - 20);

        d.setColor(255, 255, 255);
        d.drawImage("compass_arrow.png", posX, posY, size + 20, size + 20, -Math.toRadians(Location.compass));

        d.setFontSize(135);
        d.drawText(540,1250, "Score:"); // append score here

        call.draw();
        found.draw();

        for (PlayerHeading ph: headingTracker.headings)
        {
            d.setColor(255, 0, 0);
            d.drawImage("compass_arrow.png", posX, posY, size + 20, size + 20, Math.toRadians(ph.angle - Location.compass));
            d.setFontSize(40);
            d.drawText(posX, posY, GameState.game.proximity);
        }
    }
}