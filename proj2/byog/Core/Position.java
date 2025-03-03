package byog.Core;

/**
 * @Author: huashen
 * @CreateTime: 2024-12-23  19:52
 * @Description: TODO
 * @Version: 1.0
 **/
public class Position {
   private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}