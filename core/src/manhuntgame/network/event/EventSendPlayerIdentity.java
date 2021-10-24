package manhuntgame.network.event;

import io.netty.buffer.ByteBuf;
import manhuntgame.network.NetworkUtils;

public class EventSendPlayerIdentity extends PersonalEvent
{
    public String username;

    public EventSendPlayerIdentity(String username)
    {
        this.username = username;
    }

    public EventSendPlayerIdentity()
    {

    }

    @Override
    public void write(ByteBuf b)
    {
        NetworkUtils.writeString(b, username);
    }

    @Override
    public void read(ByteBuf b)
    {
        this.username = NetworkUtils.readString(b);
    }

    @Override
    public void execute()
    {

    }
}
