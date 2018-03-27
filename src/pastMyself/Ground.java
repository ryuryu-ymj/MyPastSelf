package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Ground extends GameObject
{
    /**
     * groundがステージ上のどの地面を演じているのか（groundXの番号）
     */
    int num;
    /**
     * 0:ノーマル 1:トゲトゲ
     */
    int type;
    /**
     * トゲトゲの幅
     */
    int side = 20;

    /**
     * コンストラクタ
     * @param width 横幅
     * @param height 縦幅
     */
    Ground(int width, int height)
    {
        active = false;
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        //checkLeaving(0);
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        //g.setColor(Color.green);
        //g.drawRect(displayX - width / 2, displayY - height / 2, width, height);
        im.drawGround(displayX, displayY, width, height);
    }

    /**
     * 初期化処理
     * @param x
     * @param y
     * @param type 0:ノーマル 1:トゲトゲ
     */
    public void activate(int x, int y, int type, int num)
    {
        this.abX = x;
        this.abY = y;
        this.type = type;
        this.num = num;
        active = true;
    }
}
