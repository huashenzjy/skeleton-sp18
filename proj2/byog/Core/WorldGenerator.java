package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

public class WorldGenerator {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 80;
    private static Long seed;
    private static RoomGenerator roomGenerator;
    private static Random RANDOM;
    private static PlayerGenerator playerGenerator;
    // 化繁为简，先创造一个空世界，然后慢慢填充
    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static Long getSeed() {
        return seed;
    }

    public static void setSeed(Long seed) {
        WorldGenerator.seed = seed;
    }

    public static Random getRANDOM() {
        return RANDOM;
    }

    public static void setRANDOM(Random RANDOM) {
        WorldGenerator.RANDOM = RANDOM;
    }

    public static RoomGenerator getRoomGenerator() {
        return roomGenerator;
    }

    public static void setRoomGenerator(RoomGenerator roomGenerator) {
        WorldGenerator.roomGenerator = roomGenerator;
    }

    // 生成世界 ，是该类的核心方法
    public static TETile[][] generateWorld(String input) {
        // 初始化渲染引擎，设置窗口大小为WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // 初始化地图，将所有元素初始化为Tileset.NOTHING
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        innitializeWORLD(world);
        // 随机生成20 个房间
        generateRooms(world, 20, input);

        // 随机生成迷宫
        // generateMaze(world, roomGenerator.getRooms(), getSeed(),1);
        //生成走廊
        HallWayGenerator hallWayGenerator = new HallWayGenerator(roomGenerator, roomGenerator.getRooms());
        hallWayGenerator.generateHallways(world, roomGenerator.getRooms());

        //generateMaze(world, roomGenerator.getRooms(), getSeed(),2);
        playerGenerator = new PlayerGenerator();
        playerGenerator.placePlayer(world,input);
        PlayerControl playerControl = new PlayerControl();
        playerControl.move(world,ter,playerGenerator);

        return world;

    }

    // 初始化世界

    // 初始化世界
    private static void innitializeWORLD(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                    // 生成围墙
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.NOTHING;
                }
            }
        }
    }


    // 随机生成房间
    private static void generateRooms(TETile[][] world, int numRooms, String input) {
        int cnt = 0; // 房间的数量
        long seed = Long.parseLong(input);
        setRANDOM(new Random(seed));
        setSeed(seed);
        // 传入随机数种子，生成房间
        setRoomGenerator(new RoomGenerator(seed));
        while (cnt < numRooms) {
            Room room = getRoomGenerator().generateRoom(WIDTH, HEIGHT);
            if (room != null) {
                room.printRoom(world);
                cnt++;
            } else {
                System.out.println("无法生成房间，跳过...");
            }
        }
    }

    // 生成随机的迷宫
    private static void generateMaze(TETile[][] world, List<Room> rooms, Long seed, int sign) {
        List<Position> emptyArea = MapUtils.findEmptyArea(world, rooms);
        if (!emptyArea.isEmpty()) {
            // 随机选择一个空地
            // 获取任意索引的position
            Position position = emptyArea.get(RANDOM.nextInt(emptyArea.size()));
            MazeGenerator mazeGenerator = new MazeGenerator(seed);
            mazeGenerator.generateMaze(world, rooms, position.getX(), position.getY(), sign);
        }
    }
}