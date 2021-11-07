package manhuntgame.ui.screen;

import manhuntgame.app.*;
import manhuntgame.ui.Button;

import java.net.Inet4Address;

public class ScreenHeadings extends Screen
{
    public HeadingTracker headingTracker = GameState.game.headingTracker;

    Button input = new Button(540, 1600, 900, 200, "Input code", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenInputCode();
        }
    });

    public ScreenHeadings()
    {

    }

    @Override
    public void update()
    {
        if (GameState.game.seeker)
            input.update();

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

        for (PlayerHeading ph: headingTracker.headings)
        {
            d.setColor(255, 0, 0);
            d.drawImage("compass_arrow.png", posX, posY, size + 20, size + 20, Math.toRadians(ph.angle - Location.compass));
            d.setFontSize(40);
            d.drawText(posX, posY, GameState.game.proximity);
        }

        d.setColor(255, 0, 0);
        d.setFontSize(60);

        if (GameState.game.seeker)
        {
            d.drawText(50, 50, "Role: Seeker", false);
            d.setFontSize(40);
            d.drawText(50, 100, "Use the arrows to find hiders!", false);
            d.drawText(50, 140, "If you find a hider, input their code", false);
        }
        else
        {
            d.drawText(50, 50, "Role: Hider", false);
            d.setFontSize(40);
            d.drawText(50, 100, "Hide from the seekers!", false);
            d.drawText(50, 140, "You get more points if you play it risky", false);
        }

        d.setFontSize(60);
        d.drawText(50, 220, "Points: " + GameState.game.points, false);

        d.setFontSize(40);
        d.drawText(50, 260, GameState.game.getRankText(GameState.game.rank), false);
        d.drawText(50, 300, GameState.game.getRankText2(), false);


        if (GameState.game.seeker)
            input.draw();
        else
        {
            d.setFontSize(60);
            d.drawText(540, 1500, "Your code:");
            d.setFontSize(140);
            d.drawText(540, 1600, String.format("%06d", GameState.game.code));
        }

    }
}
