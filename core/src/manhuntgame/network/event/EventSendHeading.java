package manhuntgame.network.event;

import io.netty.buffer.ByteBuf;
import manhuntgame.app.GameState;
import manhuntgame.app.PlayerHeading;
import manhuntgame.network.NetworkUtils;

import java.util.UUID;

public class EventSendHeading implements INetworkEvent
{

    public double heading;

    public UUID uuid;

    public EventSendHeading()
    {

    }

    public EventSendHeading(double heading, UUID uuid)
    {
        this.heading = heading;
        this.uuid = uuid;
    }

    @Override
    public void write(ByteBuf b)
    {
        b.writeDouble(this.heading);
        NetworkUtils.writeString(b, uuid.toString());
    }

    @Override
    public void read(ByteBuf b)
    {
        this.heading = b.readDouble();
        this.uuid = UUID.fromString(NetworkUtils.readString(b));
    }

    @Override
    public void execute()
    {
        GameState.game.headingTracker.addHeading(new PlayerHeading(uuid, heading));
    }

}

