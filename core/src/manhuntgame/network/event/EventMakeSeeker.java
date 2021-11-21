package manhuntgame.network.event;

import io.netty.buffer.ByteBuf;
import manhuntgame.app.GameState;

public class EventMakeSeeker extends PersonalEvent{
    @Override
    public void write(ByteBuf b) {
    }

    @Override
    public void read(ByteBuf b) {
    }

    @Override
    public void execute() {
        GameState.game.seeker = true;
    }
}
