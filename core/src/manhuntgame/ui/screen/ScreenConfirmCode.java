package manhuntgame.ui.screen;

import manhuntgame.app.App;
import manhuntgame.ui.Button;
import manhuntgame.ui.screen.Screen;
import manhuntgame.ui.screen.ScreenHeadings;

public class ScreenConfirmCode extends Screen
{
	public String msg;

	public ScreenConfirmCode(String msg)
	{
		this.msg = msg;
	}
		
	Button back = new Button(540, 1250, 900, 200, "Ok", new Runnable()
	{
		@Override
		public void run() 
		{
			App.app.screen = new ScreenHeadings();
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
		App.app.drawer.setFontSize(80);
		App.app.drawer.drawText(540, 900, this.msg);
	
		back.draw();
	}
	
}
