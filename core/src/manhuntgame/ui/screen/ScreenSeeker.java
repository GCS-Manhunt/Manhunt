package manhuntgame.ui.screen;

import manhuntgame.app.*;
import manhuntgame.ui.Button;

import java.net.Inet4Address;

public class ScreenSeeker extends Screen
{
    public HeadingTracker headingTracker = GameState.game.headingTracker;

    Button input = new Button(540, 1530, 900, 200, "Input code", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenInputCode();
        }
    });

    // button to call for help if/when relevant
    Button call = new Button(540, 1770, 900, 200, "Call for help", new Runnable()
    {
        @Override
        public void run()
        {
            // App.app.screen = new ScreenInputCode();
        }
    });

    public ScreenSeeker()
    {

    }

    @Override
    public void update()
    {
        if (true) {
            input.update();
            call.update();
        }

        headingTracker.update();
    }

    @Override
    public void draw() {
        double posX = 540;
        double posY = 930;
        double size = 800;

        Drawer d = App.app.drawer;
        d.setColor(40, 40, 40);
        d.fillOval(posX, posY, size, size);
        d.setColor(0, 0, 0);
        d.fillOval(posX, posY, size - 20, size - 20);

        d.setColor(255, 255, 255);
        d.drawImage("compass_arrow.png", posX, posY, size + 20, size + 20, -Math.toRadians(Location.compass));

        for (PlayerHeading ph : headingTracker.headings) {
            d.setColor(255, 0, 0);
            d.drawImage("compass_arrow.png", posX, posY, size + 20, size + 20, Math.toRadians(ph.angle - Location.compass));
            d.setFontSize(40);
            d.drawText(posX, posY, GameState.game.proximity);
        }

        d.setColor(255, 0, 0);
        d.setFontSize(70);
        d.drawText(50, 50, "Role: ", false);

        // did some manual coloring here
        // this is probably the only time it's needed, so it's not worth
        // making a general method for this, though i might try later
        if (true) {
            d.setColor(255, 255, 0);
            d.drawText(260, 50, "Seeker", false);
            d.setFontSize(60);
            d.setColor(0, 160, 255);
            d.drawText(210, 120, "hiders", false);
            d.setColor(255, 0, 0);
            d.drawText(50, 120, "Find          with the arrows!", false);
            d.drawText(50, 180, "When you do, input their code", false);
        } else {
            d.setColor(0, 160, 255);
            d.drawText(260, 50, "Hider", false);
            d.setFontSize(60);
            d.setColor(255, 255, 0);
            d.drawText(525, 120, "seekers!", false);
            d.setColor(255, 0, 0);
            d.drawText(50, 120, "Hide from the ", false);
            d.drawText(50, 180, "Play risky for more points!", false);
        }

        d.setFontSize(70);
        d.setColor(0, 255, 0);
        d.drawText(50, 270, "Points: " + GameState.game.points, false);

        d.setFontSize(60);
        d.setColor(255, 0, 0);
        d.drawText(50, 340, GameState.game.getRankText(GameState.game.rank), false);
        d.drawText(50, 400, GameState.game.getRankText2(), false);


        if (true) {
            input.draw();
            call.draw();
        }
        else
        {
            d.setFontSize(70);
            d.drawText(540, 1550, "Your code:");
            d.setFontSize(140);
            d.drawText(540, 1685, String.format("%06d", GameState.game.code));
        }

    }
}