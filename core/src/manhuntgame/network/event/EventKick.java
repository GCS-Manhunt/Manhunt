package manhuntgame.network.event;

import com.badlogic.gdx.Game;
import io.netty.buffer.ByteBuf;
import manhuntgame.app.App;
import manhuntgame.network.NetworkUtils;
import manhuntgame.ui.screen.ScreenKicked;

public class EventKick extends PersonalEvent
{	
	public String reason;
	
	public EventKick()
	{
		
	}
	
	public EventKick(String reason)
	{
		this.reason = reason;
	}

	@Override
	public void execute() 
	{
		if (this.clientID == null)
		{
			App.app.screen = new ScreenKicked(reason);
		}
	}

	@Override
	public void write(ByteBuf b)
	{
		NetworkUtils.writeString(b, this.reason);
	}

	@Override
	public void read(ByteBuf b) 
	{
		this.reason = NetworkUtils.readString(b);
	}
}
