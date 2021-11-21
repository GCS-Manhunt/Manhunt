package manhuntgame.ui.screen;

import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.app.GameState;
import manhuntgame.app.HeadingTracker;
import manhuntgame.app.Location;
import manhuntgame.app.PlayerHeading;
import manhuntgame.ui.Button;

public class ScreenHider extends Screen
{
    public HeadingTracker headingTracker = GameState.game.headingTracker;
    // public scoreTracker scoreTracker = GameState.game.scoreTracker;

    // use screenHeadings instead, this file is just for personal use

    public String code = "123456";

    /* each player has a code (sent by server) they can provide if they're caught.
    somehow, the screen needs to change upon verification - see
    ScreenEnterCode. no idea how you would do this, however
     */

    public ScreenHider()
    {

    }

    @Override
    public void update()
    {
        headingTracker.update();
        // scoreTracker.update();
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

        d.drawText(540, 1500, "");

        d.drawText(540, 1750, "Code: "+code);

        for (PlayerHeading ph: headingTracker.headings)
        {
            d.setColor(255, 0, 0);
            d.drawImage("compass_arrow.png", posX, posY, size + 20, size + 20, Math.toRadians(ph.angle - Location.compass));
            d.setFontSize(40);
            d.drawText(posX, posY, GameState.game.proximity);
        }
    }
}