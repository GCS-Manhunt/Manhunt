package manhuntgame.ui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.network.event.EventSendPlayerIdentity;
import manhuntgame.ui.Button;
import manhuntgame.ui.TextBox;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ScreenInfo extends Screen
{

    public Button back = new Button(540, 1750, 900, 200, "Go back", new Runnable()
    {
        @Override
        public void run()
        {
            App.app.screen = new ScreenMain();
        }
    });

    TextBox full = new TextBox(540, 250, 1080, 200, "", new Runnable()
    {
        @Override
        public void run()
        {

        }
    }, "");

    public String instructions = "Manhunt is a hide-and-seek type game with two types " +
            "of players: Hiders and Seekers. \n " +
            "Hiders attempt to evade seekers for as long as possible. Evading for longer " +
            "while staying closer to seekers earns more points! \n " +
            "When you get caught, you become a seeker. Seekers attempt to catch hiders " +
            "as fast as possible. The faster you do, the more points you get! \n " +
            "Try to get as many points as possible. Higher scores are better! \n " +
            "At the start, one seeker is chosen at random. \n " +
            "Good luck, and have fun!";
    public String[] words = instructions.split(" ");

    public ScreenInfo() {

    }

    @Override
    public void update()
    {
        back.update();
        // full.update();
    }

    @Override
    public void draw()
    {
        Drawer d = App.app.drawer;
        d.setColor(255, 0, 0);
        d.setFontSize(135);
        d.drawText(540,140, "Manhunt");

        // d.drawText(540,550, "Game rules and info:");
        int c_limit = 38;

        d.setFontSize(50);
        String cur = "";
        int line = 0;
        for (int i = 0; i < words.length; i++)
        {
            if (words[i].equals("\n")) {
                d.drawText(50, 300 + 55 * line, cur, false);
                line+=2;
                cur = "";
            }
            else if (cur.length()+words[i].length() <= c_limit) {
                cur += words[i]+" ";
            }
            else {
                d.drawText(50, 300 + 55 * line, cur, false);
                line++;
                cur = words[i]+" ";
            }
        }
        d.drawText(50, 300 + 55 * line, cur, false);

        // full.draw();
        back.draw();
    }
}