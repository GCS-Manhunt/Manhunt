package manhuntgame.app;

import basewindow.IModel;
import basewindow.Model;
import basewindow.ModelPart;

import java.util.ArrayList;

public class Drawer
{
    public double width = 1080;
    public double height = 1920;
    public double depth = 1000;

    public double fullWidth = 1080;
    public double fullHeight = 1920;

    public double currentColorR;
    public double currentColorG;
    public double currentColorB;
    public double currentColorA;
    public double currentGlow;

    public double fontSize;

    public double scale;

    public double boundLeft;
    public double boundRight;
    public double boundTop;
    public double boundBottom;

    public void updateDimensions()
    {
        double windowWidth = App.app.window.absoluteWidth;
        double windowHeight = App.app.window.absoluteHeight;

        this.scale = Math.min(windowWidth / this.width, windowHeight / this.height);

        this.fullWidth = windowWidth / this.scale;
        this.fullHeight = windowHeight / this.scale;

        double xMargin = (windowWidth / this.scale - this.width) / 2;
        this.boundLeft = -xMargin;
        this.boundRight = this.width + xMargin;

        double yMargin = (windowHeight / this.scale - this.height) / 2;
        this.boundTop = -yMargin;
        this.boundBottom = this.height + yMargin;

        App.app.window.absoluteDepth = this.scale * this.depth;
    }

    public void setColor(double r, double g, double b)
    {
        App.app.window.setColor(r, g, b);
        this.currentColorR = r;
        this.currentColorG = g;
        this.currentColorB = b;
        this.currentColorA = 255;
        this.currentGlow = 0;
    }

    public void setColor(double r, double g, double b, double a)
    {
        App.app.window.setColor(r, g, b, a);
        this.currentColorR = r;
        this.currentColorG = g;
        this.currentColorB = b;
        this.currentColorA = a;
        this.currentGlow = 0;
    }

    public void setColor(double r, double g, double b, double a, double glow)
    {
        App.app.window.setColor(r, g, b, a, glow);
        this.currentColorR = r;
        this.currentColorG = g;
        this.currentColorB = b;
        this.currentColorA = a;
        this.currentGlow = glow;
    }

    public void drawModel(IModel m, double x, double y, double width, double height, double angle)
    {
        double drawX = (scale * x + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * y + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (width * scale);
        double drawSizeY = (height * scale);

        m.draw(drawX, drawY, drawSizeX, drawSizeY, angle);
    }
    
    public void fillOval(double x, double y, double sizeX, double sizeY)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);

        App.app.window.shapeRenderer.fillOval(drawX, drawY, drawSizeX, drawSizeY);
    }

    public void fillOval(double x, double y, double z, double sizeX, double sizeY)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);
        double drawZ = z * scale;

        App.app.window.shapeRenderer.fillOval(drawX, drawY, drawZ, drawSizeX, drawSizeY, false);
    }
    
    public void fillGlow(double x, double y, double sizeX, double sizeY)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);

        App.app.window.shapeRenderer.fillGlow(drawX, drawY, drawSizeX, drawSizeY);
    }

    public void fillGlow(double x, double y, double z, double sizeX, double sizeY)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);
        double drawZ = scale * z;

        App.app.window.shapeRenderer.fillGlow(drawX, drawY, drawZ, drawSizeX, drawSizeY, false);
    }

    public void fillGlow(double x, double y, double sizeX, double sizeY, boolean shade)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);

        App.app.window.shapeRenderer.fillGlow(drawX, drawY, drawSizeX, drawSizeY, shade);
    }

    public void drawOval(double x, double y, double sizeX, double sizeY)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);

        App.app.window.shapeRenderer.drawOval(drawX, drawY, drawSizeX, drawSizeY);
    }

    public void fillRect(double x, double y, double sizeX, double sizeY)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);

        App.app.window.shapeRenderer.fillRect(drawX, drawY, drawSizeX, drawSizeY);
    }

    public void fillProgressRect(double x, double y, double sizeX, double sizeY, double progress)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale * progress);
        double drawSizeY = (sizeY * scale);

        App.app.window.shapeRenderer.fillRect(drawX, drawY, drawSizeX, drawSizeY);
    }

    public void drawImage(String img, double x, double y, double sizeX, double sizeY)
    {
        double drawX = (scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);

        App.app.window.shapeRenderer.drawImage(drawX, drawY, drawSizeX, drawSizeY, "/images/" + img, false);
    }

    public void drawImage(String img, double x, double y, double sizeX, double sizeY, double angle)
    {
        double drawX = (scale * (x) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * (y) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = (sizeX * scale);
        double drawSizeY = (sizeY * scale);

        App.app.window.shapeRenderer.drawImage(drawX, drawY, drawSizeX, drawSizeY, "/images/" + img, angle, false);
    }

    public void drawRect(double x, double y, double sizeX, double sizeY)
    {
        double drawX = Math.round(scale * (x - sizeX / 2) + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = Math.round(scale * (y - sizeY / 2) + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawSizeX = Math.round(sizeX * scale);
        double drawSizeY = Math.round(sizeY * scale);

        App.app.window.shapeRenderer.drawRect(drawX, drawY, drawSizeX, drawSizeY);
    }

    public void drawText(double x, double y, String text)
    {
        double sizeX = App.app.window.fontRenderer.getStringSizeX(this.fontSize, text);
        double sizeY = App.app.window.fontRenderer.getStringSizeY(this.fontSize, text);

        double drawX = (scale * x - sizeX / 2 + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * y - sizeY / 2 + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);

        App.app.window.fontRenderer.drawString(drawX, drawY, this.fontSize, this.fontSize, text);
    }

    public void drawText(double x, double y, String text, boolean rightAligned)
    {
        double sizeX = App.app.window.fontRenderer.getStringSizeX(this.fontSize, text);
        double sizeY = App.app.window.fontRenderer.getStringSizeY(this.fontSize, text);

        double offX = sizeX;

        if (!rightAligned)
            offX = 0;

        double drawX = (scale * x - offX + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * y - sizeY / 2 + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        App.app.window.fontRenderer.drawString(drawX, drawY, this.fontSize, this.fontSize, text);
    }

    public void drawUncenteredText(double x, double y, String text)
    {
        double drawX = (scale * x + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * y + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        App.app.window.fontRenderer.drawString(drawX, drawY, this.fontSize, this.fontSize, text);
    }
    
    public void addVertex(double x, double y, double z)
    {
        double drawX = (scale * x + Math.max(0, App.app.window.absoluteWidth - this.width * scale) / 2);
        double drawY = (scale * y + Math.max(0, App.app.window.absoluteHeight - this.height * scale) / 2);
        double drawZ = z;

        App.app.window.addVertex(drawX, drawY, drawZ);
    }
    
    public void setFontSize(double size)
    {
        this.fontSize = size / 36.0 * scale;
    }

    public ArrayList<String> wrapText(String msg, double max, double fontSize)
    {
        this.setFontSize(fontSize);

        ArrayList<String> lines = new ArrayList<>();
        StringBuilder l = new StringBuilder();

        boolean first = true;
        for (String s : msg.split(" "))
        {
            if (App.app.window.fontRenderer.getStringSizeX(this.fontSize, l + " " + s) / this.scale <= max)
            {
                if (!first)
                    l.append(" ");

                l.append(s);
            }
            else if (App.app.window.fontRenderer.getStringSizeX(this.fontSize, s) / this.scale > max)
            {
                if (!first)
                    l.append(" ");

                for (char c : s.toCharArray())
                {
                    if (App.app.window.fontRenderer.getStringSizeX(this.fontSize, l.toString() + c) / this.scale > max)
                    {
                        lines.add(l.toString());
                        l = new StringBuilder();
                    }

                    l.append(c);
                }
            }
            else
            {
                lines.add(l.toString());
                l = new StringBuilder();
                l.append(s);
            }

            first = false;
        }

        if (l.length() > 0)
            lines.add(l.toString());

        return lines;
    }

    public double getInterfacePointerX(double x)
    {
        return (x - Math.max(0, App.app.window.absoluteWidth - width * scale) / 2) / scale;
    }

    public double getInterfacePointerY(double y)
    {
        return (y - Math.max(0, App.app.window.absoluteHeight - height * scale) / 2) / scale;
    }

    public void playVibration(String vibration)
    {
        if (!App.app.window.vibrationsEnabled)
            return;

        switch (vibration)
        {
            case "click":
                App.app.window.vibrationPlayer.click();
                break;
            case "heavyClick":
                App.app.window.vibrationPlayer.heavyClick();
                break;
            case "selectionChanged":
                App.app.window.vibrationPlayer.selectionChanged();
                break;
        }
    }

    public ModelPart createModelPart()
    {
        return App.app.window.createModelPart();
    }

    public Model createModel(String dir)
    {
        return new Model(App.app.window, App.app.fileManager, dir);
    }
}
