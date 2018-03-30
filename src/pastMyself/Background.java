package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Background extends GameObject
{
    Background(int width, int height)
    {
        this.width = width;
        this.height = height;
        active = true;
    }

    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        abX = -cameraX % ObjectPool.CUBE_WIDTH;
        abY = -cameraY % ObjectPool.CUBE_WIDTH;
    }

    public void render(Graphics g, ImageManager im)
    {
        float x = abX;
        float y = abY;
        while (x < Play.DISPLAY_WIDTH)
        {
            while (y < Play.DISPLAY_HEIGHT)
            {
                im.drawBackground(x, y, width, height);
                y += ObjectPool.CUBE_WIDTH;
            }
            y = abY;
            x += ObjectPool.CUBE_WIDTH;
        }
    }
}
