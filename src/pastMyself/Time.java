package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.TimerTask;

public class Time
{
    private int x, y;
    private int time;
    private Font font;
    private int timeLimit;

    Time(Font font, int x, int y)
    {
        this.font = font;
        this.x = x;
        this.y = y;
    }

    public void update(GameContainer gc, int counter)
    {
        if (time <= counter / 60)
        {
            time = counter / 60;
        }
    }

    public void render(Graphics g, ImageManager im)
    {
        int timeLeft = timeLimit - time;
        font.setColor(0, 0, 0);
        if (timeLeft % 60 >= 10)
        {
            font.drawString(String.valueOf(timeLeft / 60) + ":" +String.valueOf(timeLeft % 60) , x, y);
        }
        else
        {
            font.drawString(String.valueOf(timeLeft / 60) + ":0" +String.valueOf(timeLeft % 60) , x, y);
        }
    }

    public void setTimeLimit(int timeLimit)
    {
        this.timeLimit = timeLimit;
    }

    public boolean getTiemOver()
    {
        return time > timeLimit;
    }
}
