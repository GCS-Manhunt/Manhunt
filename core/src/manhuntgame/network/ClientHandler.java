package manhuntgame.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import manhuntgame.app.App;
import manhuntgame.network.event.EventKick;
import manhuntgame.network.event.EventPing;
import manhuntgame.network.event.EventSendClientDetails;
import manhuntgame.network.event.INetworkEvent;

import java.util.UUID;

// CLIENTSIDE
public class ClientHandler extends ChannelInboundHandlerAdapter 
{	
	public String message = "";
	public MessageReader reader = new MessageReader();
	
	public ChannelHandlerContext ctx;

	public static long lastMessage = -1;
	public static long latency = 0;

	public static long latencySum = 0;
	public static int latencyCount = 1;
	public static long lastLatencyTime = 0;
	public static long lastLatencyAverage = 0;

	public boolean open = false;

	public UUID connectionID;

	public ClientHandler(UUID connectionID)
	{
		this.connectionID = connectionID;
	}

	// Client connected
	@Override
    public void channelActive(ChannelHandlerContext ctx)
    {
		this.open = true;

    	if (this.connectionID != Client.connectionID)
		{
			if (ctx != null)
				ctx.close();

			return;
		}

		if (ctx != null)
			this.reader.queue = ctx.channel().alloc().buffer();
		else
			this.reader.queue = Unpooled.buffer();

		this.ctx = ctx;

		this.sendEvent(new EventSendClientDetails(App.network_protocol, App.clientID, App.username));
		this.sendEvent(new EventPing());
    }

    public void close()
	{
		if (Client.handler.ctx != null)
			Client.handler.ctx.close();
	}

	// Send an event
	public synchronized void sendEvent(INetworkEvent e)
	{
		ByteBuf b = ctx.channel().alloc().buffer();
		int i = NetworkEventMap.get(e.getClass());

		if (i == -1)
			throw new RuntimeException("The network event " + e.getClass() + " has not been registered!");

		b.writeInt(i);
		e.write(b);
		
		ByteBuf b2 = ctx.channel().alloc().buffer();
		b2.writeInt(b.readableBytes());
		b2.writeBytes(b);
		ctx.channel().writeAndFlush(b2);
		
		ReferenceCountUtil.release(b);
	}
	
	public synchronized void sendEventAndClose(INetworkEvent e)
	{
		this.sendEvent(e);

		if (ctx != null)
			ctx.close();
	}

	// Client disconnected
	@Override
    public void channelInactive(ChannelHandlerContext ctx)
    {
    	ReferenceCountUtil.release(this.reader.queue);
		Client.connectionID = null;
    	this.open = false;
	}

    // Message received
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
		this.ctx = ctx;
		ByteBuf buffy = (ByteBuf) msg;
		boolean reply = this.reader.queueMessage(buffy, null);
		ReferenceCountUtil.release(msg);

		if (reply)
		{
			if (lastMessage < 0)
				lastMessage = System.currentTimeMillis();

			long time = System.currentTimeMillis();
			latency = time - lastMessage;
			lastMessage = time;

			latencyCount++;
			latencySum += latency;

			if (time / 1000 > lastLatencyTime)
			{
				lastLatencyTime = time / 1000;
				lastLatencyAverage = latencySum / latencyCount;

				latencySum = 0;
				latencyCount = 0;
			}

			this.sendEvent(new EventPing());
		}
    }

    public void reply()
	{
		synchronized (App.eventsOut)
		{
			//EventKeepConnectionAlive k = new EventKeepConnectionAlive();
			//Game.eventsOut.add(k);

			for (int i = 0; i < App.eventsOut.size(); i++)
			{
				INetworkEvent e = App.eventsOut.get(i);
				this.sendEvent(e);
			}

			App.eventsOut.clear();
		}
	}
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
    {
		this.open = false;

		System.err.println("A network exception has occurred: " + e.toString());
		e.printStackTrace();

		EventKick ev = new EventKick("Network exception");
		ev.clientID = null;
		App.eventsIn.add(ev);

        ctx.close();
    }
}