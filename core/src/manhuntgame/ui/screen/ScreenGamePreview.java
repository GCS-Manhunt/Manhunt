package manhuntgame.ui.screen;

import com.badlogic.gdx.Game;
import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.network.event.EventSendPlayerIdentity;
import manhuntgame.ui.Button;

public class ScreenGamePreview extends Screen
{
    public String gameName;
    public String location;
    public String duration;

    public Button back = new Button(540, 1800, 1000, 100, "Go back", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenMain();
        }
    });

    public Button agree = new Button(540, 1600, 1000, 100, "Agree and continue", new Runnable()
    {
        @Override
        public void run()
        {
            App.eventsOut.add(new EventSendPlayerIdentity("Test user name"));
            //App.app.screen = new ScreenSelectUsername();
        }
    });

    public String[] rules;

    public ScreenGamePreview(String name, String location, String duration, String[] rules)
    {
        this.gameName = name;
        this.location = location;
        this.duration = duration;
        this.rules = rules;
    }

    @Override
    public void update()
    {
        back.update();
        agree.update();
    }

    @Override
    public void draw()
    {
        Drawer d = App.app.drawer;
        d.setColor(255, 0, 0);
        d.setFontSize(100);
        d.drawText(540,100, this.gameName);

        d.setFontSize(60);
        d.drawText(540,200, this.location);
        d.drawText(540,300, this.duration);

        d.drawText(540,500, "Game rules and information:");

        d.setFontSize(35);
        for (int i = 0; i < rules.length; i++)
        {
            d.drawText(25, 560 + 45 * i, rules[i], false);
        }

        back.draw();
        agree.draw();
    }
}
