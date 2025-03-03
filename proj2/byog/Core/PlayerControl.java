package byog.Core;

import byog.TileEngine.StdDraw;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * @Author: huashen
 * @CreateTime: 2025-02-26  16:05
 * @Description: TODO 控制玩家移动
 * @Version: 1.0
 **/
public class PlayerControl {
    private PlayerGenerator playerGenerator;
    private int playerX;
    private int playerY;

    public PlayerGenerator getPlayerGenerator() {
        return playerGenerator;
    }

    public void setPlayerGenerator(PlayerGenerator playerGenerator) {
        this.playerGenerator = playerGenerator;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    private void draw(int playerX, int playerY, char c, TETile[][] world, TERenderer ter) {
        switch (c) {
            case 'w':
                if (world[playerX][playerY + 1] == Tileset.FLOOR) {
                    world[playerX][playerY] = Tileset.FLOOR;
                    world[playerX][playerY + 1] = Tileset.PLAYER;
                    updatePosition(playerX, playerY + 1); // 新增同步方法
                    ter.renderFrame(world);
                }
                break;
            case 'a':
                if (world[playerX - 1][playerY] == Tileset.FLOOR) { // 检测左侧地板
                    world[playerX][playerY] = Tileset.FLOOR;
                    world[playerX - 1][playerY] = Tileset.PLAYER;
                    updatePosition(playerX -1, playerY ); // 新增同步方法
                    ter.renderFrame(world);
                }
                break;
            case 's':
                if (world[playerX][playerY - 1] == Tileset.FLOOR) { // 检测下方地板
                    world[playerX][playerY] = Tileset.FLOOR;
                    world[playerX][playerY - 1] = Tileset.PLAYER;
                    updatePosition(playerX,playerY-1 ); // 新增同步方法
                    ter.renderFrame(world);
                }
                break;
            case 'd':
                if (world[playerX + 1][playerY] == Tileset.FLOOR) { // 检测右侧地板
                    world[playerX][playerY] = Tileset.FLOOR;
                    world[playerX + 1][playerY] = Tileset.PLAYER;
                    updatePosition(playerX+1, playerY ); // 新增同步方法
                    ter.renderFrame(world);
                }
                break;
        }
    }

    private void updatePosition(int playerX, int playerY) {
        setPlayerX(playerX);
        setPlayerY(playerY);
    }
//接下来，观察solicitNCharsInput方法，它在一个while循环中读取输入。
    // 但如果没有持续的主循环，该方法可能只执行一次，无法持续监听输入。
    // 在WorldGenerator的generateWorld方法中，
    // 调用了playerControl.move后立即渲染，
    // 但move方法可能没有持续循环，导致程序结束，无法接收后续输入

    // 获取用户输出
    private void solicitNCharsInput(int playerX, int playerY, TETile[][] world, TERenderer ter) {
        // n 为位数
        //TODO: Read n letters of player input
        // 读取用户输入的 字符串
        while (StdDraw.hasNextKeyTyped()) {
            char c = StdDraw.nextKeyTyped();
           // System.out.println(c);
            // 进行画动
            draw(playerX, playerY, c, world, ter);
        }
    }

    /**
     * @description: 主要的move方法
     * @author: huashen
     * @date: 2025/3/3 下午4:16
     * @param: world
     * @return: null
     **/
    public void move(TETile[][] world, TERenderer ter, PlayerGenerator playerGenerator) {
        playerX = playerGenerator.getPlayerX();
        playerY = playerGenerator.getPlayerY();
        // 先渲染框架
        ter.renderFrame(world);
        while (true) {
            // 一直监听
            if (StdDraw.hasNextKeyTyped()) {
                solicitNCharsInput(playerX, playerY, world, ter);
            }
            StdDraw.pause(10); // 控制循环频率
        }

    }
}

