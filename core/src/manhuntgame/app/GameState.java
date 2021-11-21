package manhuntgame.app;

public class GameState
{
    public static GameState game = new GameState();

    public HeadingTracker headingTracker = new HeadingTracker();
    public String proximity = "";

    public int points = 0;
    public boolean seeker = false;

    public int rank = 2;
    public int scoreAhead = 53;

    public int code = 123456;

    public String getRankText(int rank)
    {
        if (rank % 10 == 1 && rank % 100 != 11)
            return rank + "st place";
        else if (rank % 10 == 2 && rank % 100 != 12)
            return rank + "nd place";
        else if (rank % 10 == 3 && rank % 100 != 13)
            return rank + "rd place";
        else
            return rank + "th place";
    }

    public String getRankText2()
    {
        if (rank <= 1)
            return "";

        return scoreAhead + " points behind " + getRankText(rank - 1);
    }
}