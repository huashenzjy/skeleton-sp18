package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class PlayerGenerator {
    private Random random;
    private static int playerX ;
    private static int playerY ;

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

    // 生成玩家在地图中
    public void placePlayer(TETile[][] world, String input) {
        if (world == null || world.length == 0 || world[0].length == 0) {
            throw new IllegalArgumentException("Invalid world array");
        }

        int maxAttempts = world.length * world[0].length;
        long seed = Long.parseLong(input);
        random = new Random(seed);
        for (int i = 0; i < maxAttempts; i++) {
            int x = random.nextInt(world.length);
            int y = random.nextInt(world[0].length);

            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.PLAYER;
                playerX = x;
                playerY = y;
                System.out.println("DEBUG - Player placed at: " + x + "," + y);
                placeGold(world, input);
                //当执行到`return`时，方法会立即退出，不再执行循环的剩余次数或其他代码
                return;
            }
        }
        throw new RuntimeException("Failed to place player: No valid floor found");
    }

    public void placeGold(TETile[][] world, String input) {
        long seed = Long.parseLong(input + 20);
       Random goldRandom = new Random(seed);
        int maxAttempts = world.length * world[0].length;
        for (int i = 0; i < maxAttempts; i++) {
            int x = goldRandom.nextInt(world.length);
            int y = goldRandom.nextInt(world[0].length);
            if (!(x == 0 || y == 0 || x == world.length -1 ||y == world[0].length -1)) {
                if (world[x][y] == Tileset.WALL) {
                    world[x][y] = Tileset.LOCKED_DOOR;
                    return;
                }
            }
        }
        System.out.println("12312414 221");
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}