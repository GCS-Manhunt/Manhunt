package manhuntgame.network;

import io.netty.buffer.ByteBuf;
import manhuntgame.app.App;
import manhuntgame.network.event.*;

import java.util.UUID;

public class MessageReader 
{
	public static final int max_event_size = 1048576;

	public boolean isServer = false;

	public boolean useQueue = true;
	public ByteBuf queue;
	protected boolean reading = false;
	protected int endpoint;

	public boolean queueMessage(ByteBuf m, UUID clientID)
	{
		return this.queueMessage(null, m, clientID);
	}

	public synchronized boolean queueMessage(ServerHandler s, ByteBuf m, UUID clientID)
	{
		boolean reply = false;
		
		try
		{
			byte[] bytes = new byte[59];
			m.getBytes(0, bytes);

			if (useQueue)
				queue.writeBytes(m);
			else
				queue = m;

			if (queue.readableBytes() >= 4)
			{
				if (!reading)
				{
					endpoint = queue.readInt();

					if (endpoint > max_event_size)
					{
						if (isServer && s != null)
						{
							s.sendEventAndClose(new EventKick("A network exception has occurred: message size " + endpoint + " is too big!"));
						}
						else
						{
							EventKick ev = new EventKick("A network exception has occurred: message size " + endpoint + " is too big!");
							ev.clientID = null;
							App.eventsIn.add(ev);

							Client.handler.ctx.close();
						}

						return false;
					}
				}
				
				reading = true;

				while (queue.readableBytes() >= endpoint)
				{
					reply = this.readMessage(s, queue, clientID) || reply;
					queue.discardReadBytes();
					
					reading = false;
					
					if (queue.readableBytes() >= 4)
					{
						endpoint = queue.readInt();

						if (endpoint > MessageReader.max_event_size)
						{
							if (isServer && s != null)
							{
								s.sendEventAndClose(new EventKick("A network exception has occurred: message size " + endpoint + " is too big!"));
							}
							else
							{
								EventKick ev = new EventKick("A network exception has occurred: message size " + endpoint + " is too big!");
								ev.clientID = null;
								App.eventsIn.add(ev);

								Client.handler.ctx.close();
							}

							return false;
						}

						reading = true;
					}
				}
			}
		}
		catch (Exception e)
		{
			if (s != null)
				System.err.println("A network exception has occurred: " + e.toString() + " (" + s.username + "/" + s.clientID + ")");
			else
				System.err.println("A network exception has occurred: " + e.toString());

			e.printStackTrace();

			if (isServer && s != null)
			{
				s.sendEventAndClose(new EventKick("A network exception has occurred: " + e.toString()));
			}
			else
			{
				EventKick ev = new EventKick("A network exception has occurred: " + e.toString());
				ev.clientID = null;
				App.eventsIn.add(ev);

				Client.handler.ctx.close();
			}
		}

		return reply;
	}

	public synchronized boolean readMessage(ServerHandler s, ByteBuf m, UUID clientID) throws Exception
	{
		int i = m.readInt();
		Class<? extends INetworkEvent> c = NetworkEventMap.get(i);

		if (c == null)
			throw new Exception("Invalid network event: " + i);

		INetworkEvent e = c.getConstructor().newInstance();
		e.read(m);

		if (e instanceof PersonalEvent)
		{
			((PersonalEvent) e).clientID = clientID;
		}

		if (e instanceof EventPing)
			return true;
		else if (e instanceof IServerThreadEvent)
			((IServerThreadEvent) e).execute(s);
		else
		{
			synchronized (App.eventsIn)
			{
				App.eventsIn.add(e);
			}
		}

		return false;
	}
}
