package manhuntgame.network.event;

import io.netty.buffer.ByteBuf;
import manhuntgame.app.App;
import manhuntgame.network.NetworkUtils;
import manhuntgame.ui.screen.ScreenConfirmCode;

public class EventCodeConfirmation extends PersonalEvent{

    String confirmationString;

    public EventCodeConfirmation(String confirmationString) {
        this.confirmationString = confirmationString;
    }

    public EventCodeConfirmation()
    {

    }

    @Override
    public void write(ByteBuf b) {
        NetworkUtils.writeString(b, this.confirmationString);
    }

    @Override
    public void read(ByteBuf b) {
        confirmationString = NetworkUtils.readString(b);
    }

    @Override
    public void execute()
    {
        App.app.screen = new ScreenConfirmCode(confirmationString);
    }
}