package manhuntgame.network.event;

import io.netty.buffer.ByteBuf;
import manhuntgame.app.App;
import manhuntgame.app.GameState;
import manhuntgame.ui.screen.ScreenConfirmCode;

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
        App.app.screen = new ScreenConfirmCode("You are now a seeker!");
    }
}
