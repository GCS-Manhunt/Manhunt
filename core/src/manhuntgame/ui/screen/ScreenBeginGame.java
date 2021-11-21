package manhuntgame.ui.screen;

import manhuntgame.ui.Button;
import manhuntgame.app.App;
import manhuntgame.app.Drawer;

public class ScreenBeginGame extends Screen
{
    /*
    a) create game
    b) join game
    c) back to main screen
     */

    Button create = new Button(540, 1000, 900, 200, "Create", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenSeeker(); // can be changed later
        }
    });

    Button join = new Button(540, 1250, 900, 200, "Join", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenHeadings(); // can be changed later
        }
    });

    Button back = new Button(540, 1500, 900, 200, "Back", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenMain();
        }
    });

    @Override
    public void update()
    {
        // update buttons and not much else
        create.update();
        join.update();
        back.update();
    }

    @Override
    public void draw()
    {
        create.draw();
        join.draw();
        back.draw();

        Drawer drawer = App.app.drawer;
        drawer.setFontSize(120);
        drawer.drawText(60, 100, "Manhunt Game", false);

    }
}
