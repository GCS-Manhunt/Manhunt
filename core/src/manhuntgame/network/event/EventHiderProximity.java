package manhuntgame.network.event;

import io.netty.buffer.ByteBuf;
import manhuntgame.app.GameState;
import manhuntgame.network.NetworkUtils;

import java.util.UUID;

public class EventHiderProximity extends PersonalEvent{

    public UUID uuid;
    public boolean proximity;

    public EventHiderProximity()
    {

    }

    public EventHiderProximity(UUID uuid, boolean proximity) {
        this.proximity = proximity;
        this.uuid = uuid;
    }


    @Override
    public void write(ByteBuf b) {
        NetworkUtils.writeString(b, this.uuid.toString());
        b.writeBoolean(this.proximity);
    }

    @Override
    public void read(ByteBuf b) {
        this.uuid = UUID.fromString(NetworkUtils.readString(b));
        this.proximity = b.readBoolean();
    }

    @Override
    public void execute() {
        if (proximity)
            GameState.game.proximity = "Seeker in proximity!";
        else
            GameState.game.proximity = "";
    }
}
