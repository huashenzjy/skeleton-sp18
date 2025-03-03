package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * @Author: huashen
 * @CreateTime: 2024-12-24  18:22
 * @Description: TODO
 * @Version: 1.0
 **/
public class Room {
    private Position position; // 用来存放坐标
    private int roomWidth; // 宽度
    private int roomHeight;// 高度
    private static final int MIN_ROOM_WIDTH = 5;
    private static final int MIN_ROOM_HEIGHT = 5;
    private static final int defaultRoomSize = 13;
    public Room() {
    }

    public Room(int height, Position position, int width) {
        this.roomHeight = height;
        this.position = position;
        this.roomWidth = width;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getRoomHeight() {
        return roomHeight;
    }

    public void setRoomHeight(int roomHeight) {
        this.roomHeight = roomHeight;
    }

    public int getRoomWidth() {
        return roomWidth;
    }

    public void setRoomWidth(int roomWidth) {
        this.roomWidth = roomWidth;
    }
/**
 * @description:  随机生成一个房间
 * @author: huashen
 * @date: 2024/12/26 下午8:17
 * @param: mapWidth
 * @param: mapHeight
 * @return: Room 房间
        **/
    public static Room randomRoom(int mapWidth, int mapHeight, Random random) {
        if (mapWidth < MIN_ROOM_WIDTH || mapHeight < MIN_ROOM_HEIGHT) {
            throw new IllegalArgumentException("地图尺寸不足以容纳最小房间");
        }

        int roomWidth = random.nextInt(defaultRoomSize) + MIN_ROOM_WIDTH;
        int roomHeight = random.nextInt(defaultRoomSize) + MIN_ROOM_HEIGHT;

        int roomX = random.nextInt(mapWidth - roomWidth);
        int roomY = random.nextInt(mapHeight - roomHeight);

        return new Room(roomHeight, new Position(roomX, roomY), roomWidth);
    }
//public static Room randomRoom(int mapWidth, int mapHeight, Random random) {
//    int width = random.nextInt(MAX_WIDTH - MIN_WIDTH + 1) + MIN_WIDTH;
//    int height = random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1) + MIN_HEIGHT;
//    int backspace = 1;
//    int x = random.nextInt(mapWidth - width - 2 * backspace + 1) + backspace;
//    int y = random.nextInt(mapHeight - height - 2 * backspace + 1) + backspace;
//    return new Room(new Position(x, y), width, height);
//}

    /**
     * @description: 打印房间
     * @author: huashen
     * @date: 2024/12/24 下午8:14
     * @param: world 传入的地图
     * @param: room 传入的房间
     * @return: void
     **/
    public void printRoom(TETile[][] world) {
        int startX = this.position.getX();
        int startY = this.position.getY();
        int endX = startX + this.roomWidth;
        int endY = startY + this.roomHeight;

        // 调试输出
//        System.out.println("Room position: (" + startX + ", " + startY + ")");
//        System.out.println("Room size: " + roomWidth + "x" + roomHeight);
//        System.out.println("World dimensions: " + world.length + "x" + world[0].length);

        // 检查房间是否在世界范围内
        if (startX < 0 || startY < 0 || endX > world.length || endY > world[0].length) {
            System.out.println("房间位置或大小超出世界边界: (" + startX + ", " + startY + ") 宽度：" + roomWidth + " 高度：" + roomHeight);
            return;
        }

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                helpPrint(world, startX, startY, endX, endY, x, y);
            }
        }
    }

    //  把这个辅助方法单独提出来，方便调用
    static void helpPrint(TETile[][] world, int startX, int startY, int endX, int endY, int x, int y) {
        if (x == startX || x == endX - 1) {
            world[x][y] = Tileset.WALL;
        } else if (y == startY || y == endY - 1) {
            world[x][y] = Tileset.WALL;
        } else {
            world[x][y] = Tileset.FLOOR;
        }
    }

  //  版本二 的 helpPrint 方法 ， 全都设置为墙
//    static void helpPrint(TETile[][] world, int startX, int startY, int endX, int endY, int x, int y) {
//        world[x][y] = Tileset.WALL;
//    }

    public boolean overlap(Room other) {
        if (this == null || other == null) { // 处理 null 参数
            return false; // 如果有任一房间为空，则不重叠
        }

        int thisLeft = position.getX();
        int thisRight = thisLeft + roomWidth;
        int thisBottom = position.getY();
        int thisTop = thisBottom + roomHeight;

        int otherLeft = other.position.getX();
        int otherRight = otherLeft + other.roomWidth;
        int otherBottom = other.position.getY();
        int otherTop = otherBottom + other.roomHeight;

        // 检查两个房间是否重叠 (使用更清晰的逻辑)
        return !(thisRight <= otherLeft || thisLeft >= otherRight || thisTop <= otherBottom || thisBottom >= otherTop);
    }

    public boolean contains(int x, int y) {
        // 如果坐标(x,y)在房间里面
        // 在x里面
        return x >= position.getX() && x < position.getX() + roomWidth &&
                y >= position.getY() && y < position.getY() + roomHeight;
        //在y里面
    }
}