package manhuntgame.network;

import java.util.UUID;

public class ConnectedPlayer
{
	public String username;
	public final UUID clientId;
	
	public ConnectedPlayer(UUID id, String name)
	{
		this.clientId = id;
		this.username = name;
	}
}
