package manhuntgame.ui.screen;

import manhuntgame.app.App;
import manhuntgame.app.Drawer;
import manhuntgame.ui.Button;

public class ScreenInputCode extends Screen
{
    public int[] nums = new int[6];
    public int count = 0;

    int size = 300;
    int space = 350;

    public Button n1 = new Button(540 - space, 960 - space, size, size, "1", new Runnable()
    {
        @Override
        public void run()
        {
            input(1);
        }
    });

    public Button n2 = new Button(540, 960 - space, size, size, "2", new Runnable()
    {
        @Override
        public void run()
        {
            input(2);
        }
    });

    public Button n3 = new Button(540 + space, 960 - space, size, size, "3", new Runnable()
    {
        @Override
        public void run()
        {
            input(3);
        }
    });

    public Button n4 = new Button(540 - space, 960, size, size, "4", new Runnable()
    {
        @Override
        public void run()
        {
            input(4);
        }
    });

    public Button n5 = new Button(540, 960, size, size, "5", new Runnable()
    {
        @Override
        public void run()
        {
            input(5);
        }
    });

    public Button n6 = new Button(540 + space, 960, size, size, "6", new Runnable()
    {
        @Override
        public void run()
        {
            input(6);
        }
    });

    public Button n7 = new Button(540 - space, 960 + space, size, size, "7", new Runnable()
    {
        @Override
        public void run()
        {
            input(7);
        }
    });

    public Button n8 = new Button(540, 960 + space, size, size, "8", new Runnable()
    {
        @Override
        public void run()
        {
            input(8);
        }
    });

    public Button n9 = new Button(540 + space, 960 + space, size, size, "9", new Runnable()
    {
        @Override
        public void run()
        {
            input(9);
        }
    });

    public Button n0 = new Button(540, 960 + space * 2, size, size, "0", new Runnable()
    {
        @Override
        public void run()
        {
            input(0);
        }
    });

    public Button ndel = new Button(540 - space, 960 + space * 2, size, size, "<", new Runnable()
    {
        @Override
        public void run()
        {
            input(-1);
        }
    });

    public Button done = new Button(540 + space, 960 + space * 2, size, size, "Ok", new Runnable()
    {
        @Override
        public void run()
        {
            if (count < nums.length)
            {
                App.app.screen = new ScreenHeadings();
            }
        }
    });

    public Button[] buttons = new Button[]{n1, n2, n3, n4, n5, n6, n7, n8, n9, n0, ndel, done};

    public void input(int i)
    {
        if (i >= 0 && count < nums.length)
        {
            nums[count] = i;
            count++;
        }
        else if (i < 0 && count > 0)
        {
            count--;
        }
    }

    public ScreenInputCode()
    {
        done.customFontSize = 80;
    }

    @Override
    public void update()
    {
        for (Button b: buttons)
            b.update();

        if (count < nums.length)
        {
            done.text = "Back";
            done.unselectedColR = 40;
            done.unselectedColG = 40;
            done.unselectedColB = 40;
        }
        else
        {
            done.text = "Submit";
            done.unselectedColR = 100;
            done.unselectedColG = 100;
            done.unselectedColB = 100;
        }
    }

    @Override
    public void draw()
    {
        Drawer d = App.app.drawer;
        d.setFontSize(100);
        d.setColor(255, 0, 0);
        d.drawText(540, 150, "Enter hider's code:");

        for (int i = 0; i < nums.length; i++)
        {
            double x = 180 * i + 90;
            double y = 940 - space * 1.75;

            d.setColor(60, 60, 60);

            if (i == count)
                d.setColor(255, 0, 0);

            d.fillOval(x, y, 150, 150);
            d.setColor(20, 20, 20);

            d.fillOval(x, y, 130, 130);

            if (i < count)
            {
                d.setColor(255, 255, 255);
                d.drawText(x, y, nums[i] + "");
            }

        }

        for (Button b: buttons)
            b.draw();
    }
}
