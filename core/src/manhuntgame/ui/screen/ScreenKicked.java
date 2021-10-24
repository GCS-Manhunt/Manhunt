package manhuntgame.ui.screen;

import manhuntgame.app.App;
import manhuntgame.ui.Button;

public class ScreenKicked extends Screen
{
	public String reason;
	
	public ScreenKicked(String reason)
	{
		this.reason = reason;
	}
		
	Button back = new Button(540, 1000, 700, 80, "Ok", new Runnable()
	{
		@Override
		public void run() 
		{
			App.app.screen = new ScreenConnect();
		}
	}
	);

	@Override
	public void update() 
	{
		back.update();
	}

	@Override
	public void draw() 
	{
		App.app.drawer.setColor(255, 0, 0);
		App.app.drawer.setFontSize(48);
		App.app.drawer.drawText(540, 900, this.reason);
	
		back.draw();
	}
	
}
