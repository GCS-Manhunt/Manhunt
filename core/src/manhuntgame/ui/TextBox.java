package manhuntgame.ui;

import basewindow.InputCodes;
import basewindow.InputPoint;
import manhuntgame.app.App;
import manhuntgame.app.Drawer;

import java.util.ArrayList;

public class TextBox
{
	public Runnable function;
	public double posX;
	public double posY;
	public double sizeX;
	public double sizeY;
	public String labelText;

	public String previousInputText;
	public String inputText;

	public boolean selected = false;
	public boolean clearSelected = false;

	public boolean enableSpaces = true;
	public boolean allowSpaces = true;
	public boolean allowLetters = true;
	public boolean allowNumbers = true;
	public boolean allowColons = false;
	public boolean allowAll = false;
	public boolean allowDots = false;
	public boolean enablePunctuation = false;
	public boolean checkMaxValue = false;
	public boolean checkMinValue = false;
	public boolean allowNegatives = false;
	public boolean allowDoubles = false;

	public boolean lowerCase = false;
	public boolean enableCaps = false;

	public int maxChars = 18;
	public double maxValue = Integer.MAX_VALUE;
	public double minValue = Integer.MIN_VALUE;

	public double colorR = 40;
	public double colorG = 40;
	public double colorB = 40;
	public double bgColorR = 20;
	public double bgColorG = 20;
	public double bgColorB = 20;
	public double selectedColorR = 60;
	public double selectedColorG = 60;
	public double selectedColorB = 60;
	public double selectedFullColorR = 120;
	public double selectedFullColorG = 60;
	public double selectedFullColorB = 60;

	public boolean hover = false;

	public boolean enabled = true;

	public TextBox(double x, double y, double sX, double sY, String text, Runnable f, String defaultText)
	{
		this.posX = x;
		this.posY = y;
		this.function = f;

		this.sizeX = sX;
		this.sizeY = sY;
		this.labelText = text;

		this.inputText = defaultText;
		this.previousInputText = defaultText;
	}

	public void draw()
	{
		Drawer drawing = App.app.drawer;

		drawing.setFontSize(this.sizeY * 0.6);
		
		drawing.setColor(this.bgColorR, this.bgColorG, this.bgColorB);
		drawing.fillRect(posX, posY, sizeX - sizeY, sizeY);
		drawing.fillOval(posX - sizeX / 2 + sizeY / 2, posY, sizeY, sizeY);
		drawing.fillOval(posX + sizeX / 2 - sizeY / 2, posY, sizeY, sizeY);

		drawing.fillRect(posX, posY - sizeY * 3 / 4, sizeX - sizeY, sizeY);
		drawing.fillOval(posX - sizeX / 2 + sizeY / 2, posY - sizeY * 3 / 4, sizeY, sizeY);
		drawing.fillOval(posX + sizeX / 2 - sizeY / 2, posY - sizeY * 3 / 4, sizeY, sizeY);

		drawing.fillRect(posX, posY - 15, sizeX, sizeY * 3 / 4);

		double m = 0.8;

		if (selected)
		{
			if (this.inputText.length() >= this.maxChars)
				drawing.setColor(this.selectedFullColorR, this.selectedFullColorG, this.selectedFullColorB);
			else
				drawing.setColor(this.selectedColorR, this.selectedColorG, this.selectedColorB);
		}
		else
			drawing.setColor(this.colorR, this.colorG, this.colorB);

		drawing.fillRect(posX, posY, sizeX - sizeY, sizeY * m);
		drawing.fillOval(posX - sizeX / 2 + sizeY / 2, posY, sizeY * m, sizeY * m);
		drawing.fillOval(posX + sizeX / 2 - sizeY / 2, posY, sizeY * m, sizeY * m);

		drawing.setColor(255, 255, 255);

		drawing.drawText(posX, posY - sizeY * 13 / 16, labelText);

		this.drawInput();

		if (selected && inputText.length() > 0)
		{
			drawing.setColor(255, 0, 0);

			drawing.fillOval(this.posX - this.sizeX / 2 + this.sizeY / 2, this.posY, this.sizeY * 3 / 4, this.sizeY * 3 / 4);

			drawing.setColor(255, 255, 255);

			drawing.setFontSize(this.sizeY * 0.6);
			drawing.drawText(this.posX + 2 - this.sizeX / 2 + this.sizeY / 2 - 1, this.posY - 1, "x");
		}

		if (selected)
		{
			drawing.setColor(255, 255, 255);
			drawing.fillOval(this.posX + this.sizeX / 2 - this.sizeY / 2, this.posY - sizeY * 13 / 16, this.sizeY * 3 / 4, this.sizeY * 3 / 4);
			drawing.drawImage("paste.png", this.posX + this.sizeX / 2 - this.sizeY / 2, this.posY - sizeY * 13 / 16, this.sizeY * 1 / 2, this.sizeY * 1 / 2);

			drawing.fillOval(this.posX + this.sizeX / 2 - this.sizeY * 3 / 2, this.posY - sizeY * 13 / 16, this.sizeY * 3 / 4, this.sizeY * 3 / 4);
			drawing.drawImage("copy.png", this.posX + this.sizeX / 2 - this.sizeY * 3 / 2, this.posY - sizeY * 13 / 16, this.sizeY * 1 / 2, this.sizeY * 1 / 2);

		}
	}

	public void drawInput()
	{
		if (selected)
			App.app.drawer.drawText(posX, posY, inputText + "\u00a7127127127255_");
		else
			App.app.drawer.drawText(posX, posY, inputText);
	}

	public void update()
	{
		for (int i : App.app.window.touchPoints.keySet())
		{
			InputPoint p = App.app.window.touchPoints.get(i);

			double mx = App.app.drawer.getInterfacePointerX(p.x);
			double my = App.app.drawer.getInterfacePointerY(p.y);

			boolean handled = checkMouse(mx, my, true, p.valid && p.tag.equals(""), p);

			if (handled)
				p.tag = "textbox";

			checkDeselect(mx, my, p.valid);
		}

		if (selected)
		{
			double frac = Math.max(0, Math.round(App.app.drawer.scale * (this.posY + 30) + Math.max(0, App.app.window.absoluteHeight
					- App.app.drawer.height * App.app.drawer.scale) / 2) - App.app.window.absoluteHeight * App.app.window.keyboardFraction)
					/ App.app.window.absoluteHeight;
			App.app.window.keyboardOffset = Math.min(frac, App.app.window.keyboardOffset + 0.04 * App.app.window.frameFrequency * frac);
			App.app.window.showKeyboard = true;
			this.checkKeys();
		}
	}

	public boolean shouldAddEffect()
	{
		return this.hover && !this.selected && this.enabled && !App.app.window.touchscreen;
	}

	public boolean checkMouse(double mx, double my, boolean down, boolean valid, InputPoint p)
	{
		boolean handled = false;

		sizeX += 20;
		sizeY += 20;

		hover = mx > posX - sizeX / 2 && mx < posX + sizeX / 2 && my > posY - sizeY / 2 - sizeY * 3 / 4 && my < posY + sizeY / 2;

		clearSelected = selected && mx < posX - sizeX / 2 + sizeY && mx > posX - sizeX / 2 && my > posY - sizeY / 2 && my < posY + sizeY / 2;

		if (hover && valid && enabled)
		{
			if (!selected)
			{
				handled = true;
				selected = true;
				this.previousInputText = this.inputText;

				App.app.drawer.playVibration("click");
				App.app.window.getRawTextKeys().clear();
			}
		}

		if (clearSelected && valid && inputText.length() > 0)
		{
			handled = true;
			this.clear();
		}

		if (selected && valid && mx > posX + sizeX / 2 - sizeY && mx < posX + sizeX / 2 && my > posY - sizeY / 2 - sizeY * 13 / 16 && my < posY + sizeY / 2 - sizeY * 13 / 16)
		{
			this.paste();
			handled = true;
			App.app.drawer.playVibration("click");
		}

		if (selected && valid && mx > posX + sizeX / 2 - sizeY * 2 && mx < posX + sizeX / 2 - sizeY && my > posY - sizeY / 2 - sizeY * 13 / 16 && my < posY + sizeY / 2 - sizeY * 13 / 16)
		{
			this.copy();
			handled = true;
			App.app.drawer.playVibration("click");
		}

		sizeX -= 20;
		sizeY -= 20;

		return handled;
	}

	public void clear()
	{
		App.app.drawer.playVibration("click");
		this.inputText = "";
	}

	public void checkDeselect(double mx, double my, boolean valid)
	{
		if (App.app.window.touchscreen)
		{
			sizeX += 20;
			sizeY += 20;
		}

		boolean hover = mx > posX - sizeX / 2 && mx < posX + sizeX / 2 && my > posY - sizeY / 2 - sizeY * 3 / 4 && my < posY + sizeY / 2;

		if (((!hover && valid)) && selected)
		{
			this.submit();
		}

		if (App.app.window.touchscreen)
		{
			sizeX -= 20;
			sizeY -= 20;
		}
	}

	public void submit()
	{
		App.app.window.validPressedKeys.remove((Integer) InputCodes.KEY_ENTER);
		App.app.window.validPressedKeys.remove((Integer) InputCodes.KEY_ESCAPE);

		this.performValueCheck();
		function.run();
		this.previousInputText = this.inputText;
		App.app.drawer.playVibration("click");
		selected = false;
		App.app.window.showKeyboard = false;
	}

	public void checkKeys()
	{
		if (App.app.window.validPressedKeys.contains(InputCodes.KEY_ENTER))
		{
			this.submit();
			return;
		}

		if (App.app.window.validPressedKeys.contains(InputCodes.KEY_ESCAPE) && selected)
		{
			App.app.window.validPressedKeys.remove((Integer) InputCodes.KEY_ESCAPE);
			selected = false;
			this.inputText = this.previousInputText;
			App.app.window.showKeyboard = false;
		}

		App.app.window.pressedKeys.clear();
		App.app.window.validPressedKeys.clear();

		if (App.app.window.textPressedKeys.contains(InputCodes.KEY_LEFT_CONTROL) || App.app.window.textPressedKeys.contains(InputCodes.KEY_RIGHT_CONTROL) || App.app.window.textPressedKeys.contains(InputCodes.KEY_LEFT_SUPER) || App.app.window.textPressedKeys.contains(InputCodes.KEY_RIGHT_SUPER))
		{
			if (App.app.window.textPressedKeys.contains(InputCodes.KEY_C))
			{
				this.copy();
			}

			if (App.app.window.textPressedKeys.contains(InputCodes.KEY_V))
			{
				this.paste();
			}

			if (App.app.window.textPressedKeys.contains(InputCodes.KEY_BACKSPACE) || App.app.window.textPressedKeys.contains(InputCodes.KEY_DELETE))
			{
				App.app.window.textPressedKeys.clear();
				App.app.window.textValidPressedKeys.clear();
				App.app.window.getRawTextKeys().clear();

				this.clear();
			}
		}

		boolean caps = (this.enableCaps && (App.app.window.textPressedKeys.contains(InputCodes.KEY_LEFT_SHIFT) || App.app.window.textPressedKeys.contains(InputCodes.KEY_RIGHT_SHIFT)));

		ArrayList<Integer> texts = App.app.window.getRawTextKeys();

		for (int key : texts)
		{
			String text = App.app.window.getTextKeyText(key);

			if (text == null && key == InputCodes.KEY_SPACE)
				text = " ";

			inputKey(App.app.window.translateTextKey(key), text, caps);
		}

		texts.clear();
	}

	public void performValueCheck()
	{
		try
		{
			if (checkMaxValue)
				if (Integer.parseInt(inputText) > this.maxValue)
					inputText = (int) this.maxValue + "";

			if (checkMinValue)
				if (Integer.parseInt(inputText) < this.minValue)
					inputText = (int) this.minValue + "";
		}
		catch (Exception ignored) {}
	}

	public void inputKey(int key, String text, boolean caps)
	{
		if (this.enableCaps && (key == InputCodes.KEY_LEFT_SHIFT || key == InputCodes.KEY_RIGHT_SHIFT))
			return;

		if (key == InputCodes.KEY_SPACE)
			text = " ";

		text = text.replace("Keypad ", "");

		if (key == InputCodes.KEY_BACKSPACE || key == '\b')
			inputText = inputText.substring(0, Math.max(0, inputText.length() - 1));

		else if (text != null && inputText.length() + text.length() <= maxChars)
		{
			if (text.equals(" "))
			{
				if (allowSpaces)
				{
					if (enableSpaces)
						inputText += " ";
					else
						inputText += "_";
				}
			}
			else
			{
				if (allowAll)
				{
					inputText += text;
					return;
				}

				if (allowDots)
				{
					if (".".contains(text))
						inputText += text;
				}

				if (allowNegatives && inputText.length() == 0)
				{
					if ("-".contains(text))
						inputText += text;
				}

				if (allowDoubles && !inputText.contains("."))
				{
					if (".".contains(text))
						inputText += text;
				}

				if (enablePunctuation)
				{
					if (enableCaps && caps && "1234567890-=[]\\;',./`".contains(text))
					{
						if ("1".contains(text))
							inputText += "!";
						else if ("2".contains(text))
							inputText += "@";
						else if ("3".contains(text))
							inputText += "#";
						else if ("4".contains(text))
							inputText += "$";
						else if ("5".contains(text))
							inputText += "%";
						else if ("6".contains(text))
							inputText += "^";
						else if ("7".contains(text))
							inputText += "&";
						else if ("8".contains(text))
							inputText += "*";
						else if ("9".contains(text))
							inputText += "(";
						else if ("0".contains(text))
							inputText += ")";
						else if ("-".contains(text))
							inputText += "_";
						else if ("=".contains(text))
							inputText += "+";
						else if ("`".contains(text))
							inputText += "~";
						else if ("[".contains(text))
							inputText += "{";
						else if ("]".contains(text))
							inputText += "}";
						else if ("\\".contains(text))
							inputText += "|";
						else if (";".contains(text))
							inputText += ":";
						else if ("'".contains(text))
							inputText += "\"";
						else if (",".contains(text))
							inputText += "<";
						else if (".".contains(text))
							inputText += ">";
						else if ("/".contains(text))
							inputText += "?";

						return;
					}
					else if ("-=[]\\;',./`!@#$%^&*()_+~{}|:\"<>?".contains(text))
						inputText += text;
				}
				else if (allowColons)
				{
					if (";".contains(text) || ":".contains(text))
						inputText += ":";
				}

				if (allowLetters)
				{
					if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(text))
					{
						if (enableCaps)
						{
							if (caps)
								inputText += text.toUpperCase();
							else
								inputText += text;
						}
						else if (lowerCase)
							inputText += text.toLowerCase();
						else
							inputText += text.toUpperCase();
					}
				}

				if (allowNumbers)
				{
					if ("1234567890".contains(text))
					{
						inputText += text;
					}
				}
			}
		}
	}

	public void copy()
	{
		App.app.window.textPressedKeys.clear();
		App.app.window.textValidPressedKeys.clear();
		App.app.window.getRawTextKeys().clear();

		App.app.window.setClipboard(this.inputText);
	}

	public void paste()
	{
		App.app.window.textPressedKeys.clear();
		App.app.window.textValidPressedKeys.clear();
		App.app.window.getRawTextKeys().clear();

		String s = App.app.window.getClipboard();

		for (int i = 0; i < s.length(); i++)
		{
			this.inputKey(0, s.substring(i, i + 1).toLowerCase(), Character.isUpperCase(s.charAt(i)));
		}
	}
}
