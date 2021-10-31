package manhuntgame.ui;

import basewindow.InputPoint;
import manhuntgame.app.App;
import manhuntgame.app.Drawer;

public class Button
{
	public Runnable function;
	public double posX;
	public double posY;
	public double sizeX;
	public double sizeY;
	public String text;

	public boolean selected = false;
	public boolean enabled = true;

	public double disabledColR = 20;
	public double disabledColG = 20;
	public double disabledColB = 20;

	public double unselectedColR = 40;
	public double unselectedColG = 40;
	public double unselectedColB = 40;

	public double textColR = 255;
	public double textColG = 255;
	public double textColB = 255;

	public Button(double x, double y, double sX, double sY, String text, Runnable f)
	{
		this.function = f;

		this.posX = x;
		this.posY = y;
		this.sizeX = sX;
		this.sizeY = sY;
		this.text = text;
	}

	public Button(double x, double y, double sX, double sY, String text)
	{
		this.posX = x;
		this.posY = y;
		this.sizeX = sX;
		this.sizeY = sY;
		this.text = text;

		this.enabled = false;
	}

	public void draw()
	{
		Drawer drawing = App.app.drawer;
		drawing.setFontSize(this.sizeY * 0.6);

		if (!enabled)
			drawing.setColor(this.disabledColR, this.disabledColG, this.disabledColB);

		drawing.setColor(this.unselectedColR, this.unselectedColG, this.unselectedColB);

		drawing.fillRect(posX, posY, sizeX - sizeY, sizeY);
		drawing.fillOval(posX - sizeX / 2 + sizeY / 2, posY, sizeY, sizeY);
		drawing.fillOval(posX + sizeX / 2 - sizeY / 2, posY, sizeY, sizeY);

		drawing.setColor(this.textColR, this.textColG, this.textColB);
		drawing.drawText(posX, posY, text);
	}

	public void update()
	{
		for (int i : App.app.window.touchPoints.keySet())
		{
			InputPoint p = App.app.window.touchPoints.get(i);

			if (p.tag.equals(""))
			{
				double mx = App.app.drawer.getInterfacePointerX(p.x);
				double my = App.app.drawer.getInterfacePointerY(p.y);

				boolean handled = checkMouse(mx, my, p.valid);

				if (handled)
					p.tag = "button";
			}
		}
	}

	public boolean checkMouse(double mx, double my, boolean valid)
	{
		boolean handled = false;

		sizeX += 20;
		sizeY += 20;

		selected = (mx > posX - sizeX/2 && mx < posX + sizeX/2 && my > posY - sizeY/2  && my < posY + sizeY/2);

		if (selected && valid)
		{
			if (enabled)
			{
				handled = true;

				function.run();

				App.app.drawer.playVibration("click");
			}
		}

		sizeX -= 20;
		sizeY -= 20;

		return handled;
	}
}
