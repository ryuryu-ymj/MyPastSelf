package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Ground extends GameObject
{
    /**
     * groundがステージ上のどの地面を演じているのか（groundXの番号）
     */
    private int num;
    /**
     * groundの型
     */
    private Type type;
    private static boolean isPlayerWarped;

    /**
     * groundの型
     */
    public enum Type
    {
        /** 普通 */
        NORMAL,
        /** トゲトゲ */
        SPINE,
        /** ワープ後に消える */
        TO_DISAPPEAR,
        /** ワープ後に現れる */
        TO_APPEAR,
    }

    /**
     * コンストラクタ
     *
     * @param width  横幅
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
        if (checkLeaving(0))
        {
            active = false;
            ObjectPool.isGroundDisplay[num] = false;
            //System.out.println("delate " + num + " " + (int)abX / 55 + "," + (int)abY / 55);
        }
        changeToDisplayPoint(cameraX, cameraY);
        //System.out.print((int)abX / 55 + "," + (int)abY / 55 + " ");
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        //g.setColor(Color.green);
        //g.drawRect(displayX - width / 2, displayY - height / 2, width, height);
        switch (type)
        {
            case NORMAL:
                im.drawGround(displayX, displayY, width, height);
                break;
            case SPINE:
                im.drawGroundSpine(displayX, displayY, width, height);
                break;
            case TO_APPEAR:
                if (!isPlayerWarped)
                {
                    im.drawGroundDot(displayX, displayY, width, height);
                }
                else
                {
                    im.drawGroundDotHalf(displayX, displayY, width, height);
                }
                break;
            case TO_DISAPPEAR:
                if (!isPlayerWarped)
                {
                    im.drawGroundDotHalf(displayX, displayY, width, height);
                }
                else
                {
                    im.drawGroundDot(displayX, displayY, width, height);
                }
                break;
        }
    }

    /**
     * 初期化処理
     *
     * @param x
     * @param y
     * @param type 0:ノーマル 1:トゲトゲ
     */
    public void activate(int x, int y, Type type, int num)
    {
        this.abX = x;
        this.abY = y;
        this.type = type;
        this.num = num;
        active = true;
    }

    /*public void changeAppearance()
    {
        if (type == Type.DISAPPEAR)
        {
            type = Type.APPEAR;
            System.out.println(0);
            return;
        }
        else if (type == Type.APPEAR)
        {
            type = Type.DISAPPEAR;
            System.out.println(1);
            return;
        }
    }*/

    public Type getType()
    {
        return type;
    }

    public static void startFirstTrial()
    {
        isPlayerWarped = false;
    }

    public static void startSecondTrial()
    {
        isPlayerWarped = true;
    }
}
