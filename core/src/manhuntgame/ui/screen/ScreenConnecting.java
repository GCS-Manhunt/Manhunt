package manhuntgame.ui.screen;

import manhuntgame.app.App;
import manhuntgame.network.Client;
import manhuntgame.ui.Button;

public class ScreenConnecting extends Screen
{
	public String text = "Connecting...";
	public String exception = "";
	public boolean finished = false;
	public Thread thread;

	double time = 0;

	public ScreenConnecting(Thread t)
	{
		this.thread = t;
	}

	Button back = new Button(540, 1150, 760, 80, "Back", new Runnable()
	{
		@Override
		public void run()
		{
			App.app.screen = new ScreenConnect();
			Client.connectionID = null;
			try
			{
				thread.stop();
			}
			catch (Exception ignored)
			{

			}
		}
	}
	);

	@Override
	public void update()
	{
		back.update();
		time += App.app.window.frameFrequency;
	}

	@Override
	public void draw()
	{
		App.app.drawer.setColor(255, 0, 0);
		App.app.drawer.setFontSize(48);

		double size = Math.min(1, time / 50);
		double size2 = Math.min(1, Math.max(0, time / 50 - 0.25));
		double size3 = Math.min(1, Math.max(0, time / 50 - 0.5));

		if (!this.finished)
		{
			App.app.drawer.drawText(540, 800, this.text);

			App.app.drawer.setColor(255, 0, 0);

			drawSpinny(100, 4, 0.3, 2 * 45 * size, 1 * size);
			drawSpinny(99, 3, 0.5, 2 * 30 * size2, 0.75 * size2);
			drawSpinny(100, 2, 0.7, 2 * 15 * size3, 0.5 * size3);
		}
		else
		{
			App.app.drawer.drawText(540, 800, this.text);

			App.app.drawer.setFontSize(20);

			App.app.drawer.drawText(540, 960, this.exception);
		}

		back.draw();
	}

	public void drawSpinny(int max, int parts, double speed, double size, double dotSize)
	{
		for (int i = 0; i < max; i++)
		{
			double frac = (System.currentTimeMillis() / 1000.0 * speed + i / 100.0) % 1;
			double s = (i % (max * 1.0 / parts)) / 10.0 * parts;
			App.app.drawer.fillOval(540 + size * Math.cos(frac * Math.PI * 2),
					960 - 60 * 5 / 12 + size * Math.sin(frac * Math.PI * 2),
					s * dotSize, s * dotSize);
		}
	}

}
