package manhuntgame.network.event;

import manhuntgame.network.ServerHandler;

public interface IServerThreadEvent 
{
	void execute(ServerHandler s);
}
