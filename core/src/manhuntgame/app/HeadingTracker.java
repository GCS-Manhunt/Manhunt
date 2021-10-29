package manhuntgame.app;

import java.util.ArrayList;

public class HeadingTracker
{
    public static final long expiry_time = 20000;

    public ArrayList<PlayerHeading> headings = new ArrayList<>();

    public void update()
    {
        for (int i = 0; i < headings.size(); i++)
        {
            if (System.currentTimeMillis() - headings.get(i).lastUpdate > expiry_time)
            {
                System.out.println("remove " + headings.get(i));
                headings.remove(i);
                i--;
            }
        }
    }

    public void addHeading(PlayerHeading ph)
    {
        for (int i = 0; i < headings.size(); i++)
        {
            if (headings.get(i).id.equals(ph.id))
            {
                headings.remove(i);
                i--;
            }
        }

        //System.out.println(ph);
        headings.add(ph);
        //System.out.println(headings.size());
    }
}
