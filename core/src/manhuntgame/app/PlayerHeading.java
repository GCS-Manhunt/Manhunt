package manhuntgame.app;

import java.util.UUID;

public class PlayerHeading
{
    public UUID id;
    public long lastUpdate;
    public double angle;

    public PlayerHeading(UUID id, double angle)
    {
        this.id = id;
        this.angle = angle;
        this.lastUpdate = System.currentTimeMillis();
    }
}
