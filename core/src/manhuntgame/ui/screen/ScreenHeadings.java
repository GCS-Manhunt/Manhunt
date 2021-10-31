package manhuntgame.ui.screen;

import manhuntgame.app.*;

import java.net.Inet4Address;

public class ScreenHeadings extends Screen
{
    public HeadingTracker headingTracker = GameState.game.headingTracker;

    public ScreenHeadings()
    {

    }

    @Override
    public void update()
    {
        headingTracker.update();
    }

    @Override
    public void draw()
    {
        double posX = 540;
        double posY = 960;
        double size = 800;

        Drawer d = App.app.drawer;
        d.setColor(40, 40, 40);
        d.fillOval(posX, posY, size, size);
        d.setColor(0, 0, 0);
        d.fillOval(posX, posY, size - 20, size - 20);

        d.setColor(255, 255, 255);
        d.drawImage("compass_arrow.png", posX, posY, size + 20, size + 20, -Math.toRadians(Location.compass));

        System.out.println("there are: " + headingTracker.headings.size());
        for (PlayerHeading ph: headingTracker.headings)
        {
            d.setColor(255, 0, 0);
            d.drawImage("compass_arrow.png", posX, posY, size + 20, size + 20, Math.toRadians(ph.angle - Location.compass));
            d.setFontSize(40);
            d.drawText(posX, posY, GameState.game.proximity);
        }
    }
}
