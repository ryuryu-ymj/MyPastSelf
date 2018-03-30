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
    public static final int GROUND_MAX = 200;
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
            String line;
            int lineCnt = 0;
            groundXs.clear();
            groundYs.clear();
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
                if(lineCnt == 0)
                {
                    try
                    {
                        timeLimit = Integer.valueOf(line);
                    }
                    catch (NumberFormatException e)
                    {
                        System.err.println("timeLimitの取得に失敗しました" + line);
                        timeLimit = 100;
                    }
                }
                else
                {
                    for (int letterCnt = 0; letterCnt < line.length(); letterCnt++)
                    {
                        switch (line.charAt(letterCnt))
                        {
                            case '0':
                                groundXs.add(letterCnt * ObjectPool.CUBE_WIDTH);
                                groundYs.add(lineCnt * ObjectPool.CUBE_WIDTH);
                                //System.out.println(j + " " + i);
                                break;
                            case 's':
                                startX = letterCnt * ObjectPool.CUBE_WIDTH;
                                startY = lineCnt * ObjectPool.CUBE_WIDTH;
                                break;
                            case 'g':
                                goalX = letterCnt * ObjectPool.CUBE_WIDTH;
                                goalY = lineCnt * ObjectPool.CUBE_WIDTH;
                                break;
                            case 'w':
                                warpX = letterCnt * ObjectPool.CUBE_WIDTH;
                                warpY = lineCnt * ObjectPool.CUBE_WIDTH;
                                break;
                        }
                    }
                }

                lineCnt++;
            }
            fr.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getGroundXs()
    {
        return groundXs;
    }

    public ArrayList<Integer> getGroundY()
    {
        return groundYs;
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
