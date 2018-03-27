package pastMyself;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StageDate
{
    private ArrayList<Integer> groundXs, groundYs;
    private int startX, startY;
    private int goalX, goalY;
    private int warpX, warpY;
    private int timeLimit;

    /** 1ステージにあるgroundの最大数 */
    public static final int GROUND_MAX = 100;
    /** ステージの最大数 */
    public static final int STAGE_MAX = 5;

    StageDate()
    {
        groundXs = new ArrayList<>();
        groundYs = new ArrayList<>();
    }

    /**
     * ステージのデータを読み込む
     * @param stageNum ステージ番号
     */
    public void loadStageDate(int stageNum)
    {
        ArrayList<String> stageDate = new ArrayList<>();

        try
        {
            File file = new File("res/stage/stage" + (stageNum + 1) + ".txt");
            if (!file.exists())
            {
                System.out.println("ファイルが存在しません");
                return;
            }

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            int i = 0;
            do
            {
                stageDate.add(br.readLine());
                //System.out.println(stageDate.get(i));
            }
            while (stageDate.get(i++) != null);
            stageDate.remove(null);
            fr.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        for (int i = 0; i < stageDate.size(); i++)
        {
            System.out.println(stageDate.get(i));
        }

        stageDate.remove(0);
        try
        {
            timeLimit = Integer.valueOf(stageDate.get(0));
        }
        catch (NumberFormatException e)
        {
            System.out.println("timeLimitの取得に失敗しました" + stageDate.get(0));
            timeLimit = 100;
        }
        finally
        {
            stageDate.remove(0);
        }
        groundXs.clear();
        groundYs.clear();
        for (int i = 0; i < stageDate.size(); i++)
        {
            for (int j = 0; j < stageDate.get(i).length(); j++)
            {
                switch (stageDate.get(i).charAt(j))
                {
                    case '0':
                        groundXs.add(j * ObjectPool.CUBE_WIDTH);
                        groundYs.add(i * ObjectPool.CUBE_WIDTH);
                        //System.out.println(j + " " + i);
                        break;
                    case 's':
                        startX = j * ObjectPool.CUBE_WIDTH;
                        startY = i * ObjectPool.CUBE_WIDTH;
                        break;
                    case 'g':
                        goalX = j * ObjectPool.CUBE_WIDTH;
                        goalY = i * ObjectPool.CUBE_WIDTH;
                        break;
                    case 'w':
                        warpX = j * ObjectPool.CUBE_WIDTH;
                        warpY = i * ObjectPool.CUBE_WIDTH;
                        break;
                }
            }
        }
    }

    public int getGroundX(int num)
    {
        try
        {
            return groundXs.get(num);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            return 0;
        }
    }

    public int getGroundY(int num)
    {
        try
        {
            return groundYs.get(num);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            return 0;
        }
    }

    public int getGroundNum()
    {
        return groundXs.size();
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getGoalX() {
        return goalX;
    }

    public int getGoalY() {
        return goalY;
    }

    public int getWarpX() {
        return warpX;
    }

    public int getWarpY() {
        return warpY;
    }

    public int getTimeLimit() {
        return timeLimit;
    }
}
