package pastMyself;

public class StageTitle
{
    private int x, y;
    Font fontTitle;
    Font fontMessage;

    StageTitle(Font fontTitle, Font fontMessage, int x, int y)
    {
        this.fontTitle = fontTitle;
        this.fontMessage = fontMessage;
        this.x = x;
        this.y = y;
    }

    public void render(int stageNum, int counter)
    {
        fontTitle.setColor(0, 0, 0);
        fontTitle.drawString("stage" + (stageNum + 1), x - fontTitle.getWidth("sta"), y);

        fontMessage.setColor(0, 0, 0);
        fontMessage.setAlpha((float)Math.abs(counter % 20 - 10) / 10);
        fontMessage.drawString("press space key", x - fontMessage.getWidth("press space key") / 2, y + 200);
    }
}
