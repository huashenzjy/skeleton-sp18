package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

/**
 * @Author: huashen
 * @CreateTime: 2024-12-26  16:28
 * @Description: TODO 连接走廊的类
 * @Version: 1.0
 **/
public class HallWayGenerator {
    private List<Room> rooms;
    private RoomGenerator roomGenerator;
    private Random random ;

    public HallWayGenerator( RoomGenerator roomGenerator, List<Room> rooms) {
        random = new Random();
        this.roomGenerator = roomGenerator;
        this.rooms = rooms;
    }

    public RoomGenerator getRoomGenerator() {
        return roomGenerator;
    }

    public void setRoomGenerator(RoomGenerator roomGenerator) {
        this.roomGenerator = roomGenerator;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

   /**
     * @description: 生成隧道
     * @author: huashen
     * @date: 2024/12/30 下午12:21
            **/
//    public void generateHallways(TETile[][] map, List<Room> rooms) {
//        for (int i = 0; i < rooms.size() - 1; i++) {
//            connectRoom(rooms.get(i), rooms.get(i + 1), map);
//        }
//        drawWalls(map);
//    }

//    private void connectRoom(Room room1, Room room2, TETile[][] map) {
//        int x1, y1;
//        if (room1.getPosition().getX() + room1.getRoomWidth() < room2.getPosition().getX() ||
//                room2.getPosition().getX() + room2.getRoomWidth() < room1.getPosition().getX()) {
//            // 水平方向不相邻，水平方向的两个房间
//            // 在 room1 的右侧或 room2 的左侧墙壁上选择 y 坐标
//            // 选择两个房间的底部相对的y坐标 ，的最大值， 作为隧道的最低点
//            int minY = Math.max(room1.getPosition().getY() + 1, room2.getPosition().getY() + 1);
//            // 选择两个房间的顶部相对的y坐标 ，的最小值， 作为隧道的最高点
//            int maxY = Math.min(room1.getPosition().getY() + room1.getRoomHeight() - 1, room2.getPosition().getY() + room2.getRoomHeight() - 1);
////例如，如果 minY 是 5，maxY 是 10，则 maxY - minY + 1 的值为 6，random.nextInt(6) 会生成 0 到 5 之间的随机数。
//// 然后，加上 minY (5)，就可以得到 5 到 10 之间的随机数。
//            y1 = random.nextInt(maxY - minY + 1) + minY;
//            if (room1.getPosition().getX() < room2.getPosition().getX()) {
//                x1 = room1.getPosition().getX() + room1.getRoomWidth() - 1;
//            } else {
//                x1 = room1.getPosition().getX();
//            }
//        } else { // 垂直方向不相邻 ， 垂直方向的两个房间
//            //在room1的下方或room2的上方墙壁上选择x坐标
//            int minX = Math.max(room1.getPosition().getX() + 1, room2.getPosition().getX() + 1);
//            int maxX = Math.min(room1.getPosition().getX() + room1.getRoomWidth() - 1, room2.getPosition().getX() + room2.getRoomWidth() - 1);
//            x1 = random.nextInt(maxX - minX + 1) + minX;
//            if (room1.getPosition().getY() < room2.getPosition().getY()) {
//                y1 = room1.getPosition().getY() + room1.getRoomHeight() - 1;
//            } else {
//                y1 = room1.getPosition().getY();
//            }
//        }
//
//        int x2, y2; // 挖隧道 的目标位置
//        if (room1.getPosition().getX() + room1.getRoomWidth() < room2.getPosition().getX() ||
//                room2.getPosition().getX() + room2.getRoomWidth() < room1.getPosition().getX()) {
//            // 水平方向不相邻
//            y2 = y1;
//            if (room1.getPosition().getX() < room2.getPosition().getX()) {
//                x2 = room2.getPosition().getX();
//            } else {
//                x2 = room2.getPosition().getX() + room2.getRoomWidth() - 1;
//            }
//        } else { // 垂直方向不相邻
//            x2 = x1;
//            if (room1.getPosition().getY() < room2.getPosition().getY()) {
//                y2 = room2.getPosition().getY();
//            } else {
//                y2 = room2.getPosition().getY() + room2.getRoomHeight() - 1;
//            }
//        }
//        createTunnel(x1, y1, x2, y2, map);
//    }

//    private void connectRoom(Room room1, Room room2, TETile[][] map) {
//        int x1, y1;
//        if (room1.getPosition().getX() + room1.getRoomWidth() < room2.getPosition().getX() ||
//                room2.getPosition().getX() + room2.getRoomWidth() < room1.getPosition().getX()) {
//            // 水平方向不相邻
//            int minY = Math.max(room1.getPosition().getY() + 1, room2.getPosition().getY() + 1);
//            int maxY = Math.min(room1.getPosition().getY() + room1.getRoomHeight() - 1, room2.getPosition().getY() + room2.getRoomHeight() - 1);
//
//            // 关键的检查：确保 maxY - minY + 1 是正数
//            if (maxY - minY + 1 > 0) { // 只在差值大于0的时候才调用nextInt
//                y1 = random.nextInt(maxY - minY + 1) + minY;
//            } else {
//                // 处理重叠的情况，例如：
//                // 1. 直接选择 minY 作为 y1
//                y1 = minY;
//                // 2. 或者不创建这个方向的连接
//                // return;
//            }
//
//            if (room1.getPosition().getX() < room2.getPosition().getX()) {
//                x1 = room1.getPosition().getX() + room1.getRoomWidth() - 1;
//            } else {
//                x1 = room1.getPosition().getX();
//            }
//        } else { // 垂直方向不相邻
//            int minX = Math.max(room1.getPosition().getX() + 1, room2.getPosition().getX() + 1);
//            int maxX = Math.min(room1.getPosition().getX() + room1.getRoomWidth() - 1, room2.getPosition().getX() + room2.getRoomWidth() - 1);
//
//            // 关键的检查：确保 maxX - minX + 1 是正数
//            if (maxX - minX + 1 > 0) { // 只在差值大于0的时候才调用nextInt
//                x1 = random.nextInt(maxX - minX + 1) + minX;
//            } else {
//                // 处理重叠的情况
//                x1 = minX; //选择最小的x
//                //return; // 或者不创建这个方向的连接
//            }
//
//            if (room1.getPosition().getY() < room2.getPosition().getY()) {
//                y1 = room1.getPosition().getY() + room1.getRoomHeight() - 1;
//            } else {
//                y1 = room1.getPosition().getY();
//            }
//        }
//
//        int x2, y2;
//        // ... (这部分代码不需要修改)
//        //挖隧道 的目标位置
//        if (room1.getPosition().getX() + room1.getRoomWidth() < room2.getPosition().getX() ||
//                room2.getPosition().getX() + room2.getRoomWidth() < room1.getPosition().getX()) {
//            // 水平方向不相邻
//            y2 = y1;
//            if (room1.getPosition().getX() < room2.getPosition().getX()) {
//                x2 = room2.getPosition().getX();
//            } else {
//                x2 = room2.getPosition().getX() + room2.getRoomWidth() - 1;
//            }
//        } else { // 垂直方向不相邻
//            x2 = x1;
//            if (room1.getPosition().getY() < room2.getPosition().getY()) {
//                y2 = room2.getPosition().getY();
//            } else {
//                y2 = room2.getPosition().getY() + room2.getRoomHeight() - 1;
//            }
//        }
//        createTunnel(x1, y1, x2, y2, map);
//    }
//private void connectRoom(Room room1, Room room2, TETile[][] map) {
//    int x1, y1, x2, y2;
//
//    if (room1.getPosition().getX() + room1.getRoomWidth() < room2.getPosition().getX()) { // room1 在 room2 左侧
//        x1 = room1.getPosition().getX() + room1.getRoomWidth() - 1;
//        x2 = room2.getPosition().getX();
//        int minY = Math.max(room1.getPosition().getY() + 1, room2.getPosition().getY() + 1);
//        int maxY = Math.min(room1.getPosition().getY() + room1.getRoomHeight() - 2, room2.getPosition().getY() + room2.getRoomHeight() - 2);
//        if (maxY - minY >= 0) {
//            y1 = random.nextInt(maxY - minY + 1) + minY;
//            y2 = y1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room2.getPosition().getX() + room2.getRoomWidth() < room1.getPosition().getX()) { // room2 在 room1 左侧
//        x1 = room1.getPosition().getX();
//        x2 = room2.getPosition().getX() + room2.getRoomWidth() - 1;
//        int minY = Math.max(room1.getPosition().getY() + 1, room2.getPosition().getY() + 1);
//        int maxY = Math.min(room1.getPosition().getY() + room1.getRoomHeight() - 2, room2.getPosition().getY() + room2.getRoomHeight() - 2);
//        if (maxY - minY >= 0) {
//            y1 = random.nextInt(maxY - minY + 1) + minY;
//            y2 = y1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room1.getPosition().getY() + room1.getRoomHeight() < room2.getPosition().getY()) { // room1 在 room2 上方
//        y1 = room1.getPosition().getY() + room1.getRoomHeight() - 1;
//        y2 = room2.getPosition().getY();
//        int minX = Math.max(room1.getPosition().getX() + 1, room2.getPosition().getX() + 1);
//        int maxX = Math.min(room1.getPosition().getX() + room1.getRoomWidth() - 2, room2.getPosition().getX() + room2.getRoomWidth() - 2);
//        if (maxX - minX >= 0) {
//            x1 = random.nextInt(maxX - minX + 1) + minX;
//            x2 = x1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room2.getPosition().getY() + room2.getRoomHeight() < room1.getPosition().getY()) { // room2 在 room1 上方
//        y1 = room1.getPosition().getY();
//        y2 = room2.getPosition().getY() + room2.getRoomHeight() - 1;
//        int minX = Math.max(room1.getPosition().getX() + 1, room2.getPosition().getX() + 1);
//        int maxX = Math.min(room1.getPosition().getX() + room1.getRoomWidth() - 2, room2.getPosition().getX() + room2.getRoomWidth() - 2);
//        if (maxX - minX >= 0) {
//            x1 = random.nextInt(maxX - minX + 1) + minX;
//            x2 = x1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    }
//}
//    /**
//     * @description: 挖隧道的方法
//     * @author: huashen
//     * @date: 2024/12/26 下午5:11
//     * @param: x1 开始的x坐标
//     * @param: y1 开始的y坐标
//     * @param: x2 目标位置的x坐标
//     * @param: y2 目标位置的y坐标
//     * @return: void
//     **/
//    private void createTunnel(int x1, int y1, int x2, int y2, TETile[][] map) {
//        // 这是水平方向的隧道
//        while (x1 != x2) {
//            map[x1][y1] = Tileset.FLOOR;
//             int y3 = y1 -=1;
//             int y4 = y1 +=1;
//             int x3 = x1 ;
//
//
//            x1 += (x2 > x1) ? 1 : -1;
//            x3 += (x2 > x1) ? 1 : -1;
//            // 挖隧道的算法，从x1,y1开始，到x2,y2结束，每次向x方向移动1格或-1格
//              // map[x3][y3] = Tileset.WALL;
//              // map[x3][y4] = Tileset.WALL;
//        }
//        while (y1 != y2) {
//            map[x1][y1] = Tileset.FLOOR;
//
//            y1 += (y2 > y1) ? 1 : -1;
//        }
//    }
//private void connectRoom(Room room1, Room room2, TETile[][] map) {
//    int x1, y1, x2, y2;
//
//    if (room1.getPosition().getX() + room1.getRoomWidth() < room2.getPosition().getX()) { // room1 在 room2 左侧
//        x1 = room1.getPosition().getX() + room1.getRoomWidth() - 1;
//        x2 = room2.getPosition().getX();
//        int minY = Math.max(room1.getPosition().getY() + 1, room2.getPosition().getY() + 1);
//        int maxY = Math.min(room1.getPosition().getY() + room1.getRoomHeight() - 2, room2.getPosition().getY() + room2.getRoomHeight() - 2);
//        if (maxY - minY >= 0) {
//            y1 = random.nextInt(maxY - minY + 1) + minY;
//            y2 = y1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room2.getPosition().getX() + room2.getRoomWidth() < room1.getPosition().getX()) { // room2 在 room1 左侧
//        x1 = room1.getPosition().getX();
//        x2 = room2.getPosition().getX() + room2.getRoomWidth() - 1;
//        int minY = Math.max(room1.getPosition().getY() + 1, room2.getPosition().getY() + 1);
//        int maxY = Math.min(room1.getPosition().getY() + room1.getRoomHeight() - 2, room2.getPosition().getY() + room2.getRoomHeight() - 2);
//        if (maxY - minY >= 0) {
//            y1 = random.nextInt(maxY - minY + 1) + minY;
//            y2 = y1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room1.getPosition().getY() + room1.getRoomHeight() < room2.getPosition().getY()) { // room1 在 room2 上方
//        y1 = room1.getPosition().getY() + room1.getRoomHeight() - 1;
//        y2 = room2.getPosition().getY();
//        int minX = Math.max(room1.getPosition().getX() + 1, room2.getPosition().getX() + 1);
//        int maxX = Math.min(room1.getPosition().getX() + room1.getRoomWidth() - 2, room2.getPosition().getX() + room2.getRoomWidth() - 2);
//        if (maxX - minX >= 0) {
//            x1 = random.nextInt(maxX - minX + 1) + minX;
//            x2 = x1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room2.getPosition().getY() + room2.getRoomHeight() < room1.getPosition().getY()) { // room2 在 room1 上方
//        y1 = room1.getPosition().getY();
//        y2 = room2.getPosition().getY() + room2.getRoomHeight() - 1;
//        int minX = Math.max(room1.getPosition().getX() + 1, room2.getPosition().getX() + 1);
//        int maxX = Math.min(room1.getPosition().getX() + room1.getRoomWidth() - 2, room2.getPosition().getX() + room2.getRoomWidth() - 2);
//        if (maxX - minX >= 0) {
//            x1 = random.nextInt(maxX - minX + 1) + minX;
//            x2 = x1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    }
//}
//
//    private void createTunnel(int x1, int y1, int x2, int y2, TETile[][] map) {
//        while (x1 != x2) {
//            map[x1][y1] = Tileset.FLOOR;
//            x1 += (x2 > x1) ? 1 : -1;
//        }
//        while (y1 != y2) {
//            map[x1][y1] = Tileset.FLOOR;
//            y1 += (y2 > y1) ? 1 : -1;
//        }
//    }
//private void connectRoom(Room room1, Room room2, TETile[][] map) {
//    int x1, y1, x2, y2;
//
//    if (room1.getPosition().getX() + room1.getRoomWidth() < room2.getPosition().getX()) { // room1 在 room2 左侧
//        x1 = room1.getPosition().getX() + room1.getRoomWidth() - 1;
//        x2 = room2.getPosition().getX();
//        int minY = Math.max(room1.getPosition().getY() + 1, room2.getPosition().getY() + 1);
//        int maxY = Math.min(room1.getPosition().getY() + room1.getRoomHeight() - 2, room2.getPosition().getY() + room2.getRoomHeight() - 2);
//        if (maxY - minY >= 0) {
//            y1 = random.nextInt(maxY - minY + 1) + minY;
//            y2 = y1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room2.getPosition().getX() + room2.getRoomWidth() < room1.getPosition().getX()) { // room2 在 room1 左侧
//        x1 = room1.getPosition().getX();
//        x2 = room2.getPosition().getX() + room2.getRoomWidth() - 1;
//        int minY = Math.max(room1.getPosition().getY() + 1, room2.getPosition().getY() + 1);
//        int maxY = Math.min(room1.getPosition().getY() + room1.getRoomHeight() - 2, room2.getPosition().getY() + room2.getRoomHeight() - 2);
//        if (maxY - minY >= 0) {
//            y1 = random.nextInt(maxY - minY + 1) + minY;
//            y2 = y1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room1.getPosition().getY() + room1.getRoomHeight() < room2.getPosition().getY()) { // room1 在 room2 上方
//        y1 = room1.getPosition().getY() + room1.getRoomHeight() - 1;
//        y2 = room2.getPosition().getY();
//        int minX = Math.max(room1.getPosition().getX() + 1, room2.getPosition().getX() + 1);
//        int maxX = Math.min(room1.getPosition().getX() + room1.getRoomWidth() - 2, room2.getPosition().getX() + room2.getRoomWidth() - 2);
//        if (maxX - minX >= 0) {
//            x1 = random.nextInt(maxX - minX + 1) + minX;
//            x2 = x1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    } else if (room2.getPosition().getY() + room2.getRoomHeight() < room1.getPosition().getY()) { // room2 在 room1 上方
//        y1 = room1.getPosition().getY();
//        y2 = room2.getPosition().getY() + room2.getRoomHeight() - 1;
//        int minX = Math.max(room1.getPosition().getX() + 1, room2.getPosition().getX() + 1);
//        int maxX = Math.min(room1.getPosition().getX() + room1.getRoomWidth() - 2, room2.getPosition().getX() + room2.getRoomWidth() - 2);
//        if (maxX - minX >= 0) {
//            x1 = random.nextInt(maxX - minX + 1) + minX;
//            x2 = x1;
//            createTunnel(x1, y1, x2, y2, map);
//        }
//    }
//}

//    // 挖隧道的关键方法
//    private void createTunnel(int x1, int y1, int x2, int y2, TETile[][] map) {
//        while (x1 != x2) {
//            map[x1][y1] = Tileset.FLOOR;
//            x1 += (x2 > x1) ? 1 : -1;
//        }
//        //map[x1][y1] = Tileset.FLOOR; // 关键修改：设置 x2 的位置为 FLOOR
//        while (y1 != y2) {
//            map[x1][y1] = Tileset.FLOOR;
//            y1 += (y2 > y1) ? 1 : -1;
//        }
//        map[x1][y1] = Tileset.FLOOR; // 关键修改：设置 y2 的位置为 FLOOR
//    }

  //  private void connectRoom(Room room1, Room room2, TETile[][] map) {
//        int x1, y1, x2, y2;
//
//        // 获取房间的中心点
//        x1 = room1.getPosition().getX() + room1.getRoomWidth() / 2;
//        y1 = room1.getPosition().getY() + room1.getRoomHeight() / 2;
//        x2 = room2.getPosition().getX() + room2.getRoomWidth() / 2;
//        y2 = room2.getPosition().getY() + room2.getRoomHeight() / 2;
//
//        // L 形连接
//        int cornerX = x1;
//        int cornerY = y2;
//
//        // 创建水平部分
//        createTunnel(x1, y1, cornerX, y1, map);
//        // 创建垂直部分
//        createTunnel(cornerX, y1, cornerX, cornerY, map);
//
//        // 或者先垂直后水平
//        //createTunnel(x1, y1, x1, cornerY, map);
//        //createTunnel(x1, cornerY, x2, cornerY, map);
//    }
//
//    private void createTunnel(int x1, int y1, int x2, int y2, TETile[][] map) {
//        while (x1 != x2) {
//            if (map[x1][y1] != Tileset.FLOOR) { // 避免重叠
//                map[x1][y1] = Tileset.FLOOR;
//            }
//            x1 += (x2 > x1) ? 1 : -1;
//        }
//        if (map[x1][y1] != Tileset.FLOOR) { // 避免重叠
//            map[x1][y1] = Tileset.FLOOR;
//        }
//        while (y1 != y2) {
//            if (map[x1][y1] != Tileset.FLOOR) { // 避免重叠
//                map[x1][y1] = Tileset.FLOOR;
//            }
//            y1 += (y2 > y1) ? 1 : -1;
//        }
//        if (map[x1][y1] != Tileset.FLOOR) { // 避免重叠
//            map[x1][y1] = Tileset.FLOOR;
//        }
//
//    }
//
//    // 挖完隧道就绘制墙在周围
//    private void drawWalls(TETile[][] world) {
//        int w = world.length;
//        int h = world[0].length;
//        for (int x = 0; x < w; x++) {
//            for (int y = 0; y < h; y++) {
//                if (world[x][y] == Tileset.FLOOR) {
//                    // 检查周围八个方向
//                    for (int i = -1; i <= 1; i++) {
//                        for (int j = -1; j <= 1; j++) {
//                            // 排除自身
//                            if (i == 0 && j == 0) continue;
//
//                            int nx = x + i;
//                            int ny = y + j;
//
//                            // 边界检查
//                            if (nx >= 0 && nx < w && ny >= 0 && ny < h && world[nx][ny] == Tileset.NOTHING) {
//                                world[nx][ny] = Tileset.WALL;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
    public void generateHallways(TETile[][] map, List<Room> rooms) {
        for (int i = 0; i < rooms.size() - 1; i++) {
            // 这里第一和第二个连接，以此类推，就不会出现所有的房间都连在一起的情况
            connectRoom(rooms.get(i), rooms.get(i + 1), map);
        }
        drawWalls(map);
    }

    private void connectRoom(Room room1, Room room2, TETile[][] map) {
        // 获得房间的中心点
        //使用房间的中心点可以使连接更自然，避免走廊直接贴着墙壁
        // x2 ，y2 也为目标点
        int x1 = room1.getPosition().getX() + room1.getRoomWidth() / 2;
        int y1 = room1.getPosition().getY() + room1.getRoomHeight() / 2;
        int x2 = room2.getPosition().getX() + room2.getRoomWidth() / 2;
        int y2 = room2.getPosition().getY() + room2.getRoomHeight() / 2;

        //假设最简单的非直线连接方式，就是L型连接
       //L 形有两种方向，可以先画横线再画竖线，也可以先画竖线再画横线。
        // 随机选择 L 形连接的方向（先水平后垂直，或先垂直后水平）
        // 这里使用随机数来决定
        createTunnel(x2, y1, x2, y2, map); // 垂直
        createTunnel(x1, y1, x2, y1, map); // 先画出水平线，再画出垂直线，因为终点是y1


//        if (random.nextBoolean()) {
//        }
//        else {
//            createTunnel(x1, y1, x1, y2, map); // 垂直
//            createTunnel(x1, y2, x2, y2, map); // 水平
//        }
    }


    private void createTunnel(int x1, int y1, int x2, int y2, TETile[][] map) {
        while (x1 != x2) {
            // 检查是否为floor，避免重复设置
            if (map[x1][y1] != Tileset.FLOOR) {
                map[x1][y1] = Tileset.FLOOR;
            }
            //进行迭代
            x1 += (x2 > x1) ? 1 : -1;
        }
        //这里是当x1 = x2时，也要设置一个 FLOOR，因为最后一个坐标点也要设置为 FLOOR
        if (map[x1][y1] != Tileset.FLOOR) {
            map[x1][y1] = Tileset.FLOOR;
        }
        while (y1 != y2) {
            if (map[x1][y1] != Tileset.FLOOR) {
                map[x1][y1] = Tileset.FLOOR;
            }
            y1 += (y2 > y1) ? 1 : -1;
        }
        if (map[x1][y1] != Tileset.FLOOR) {
            map[x1][y1] = Tileset.FLOOR;
        }
    }

    private void drawWalls(TETile[][] world) {
        int w = world.length;
        int h = world[0].length;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if (i == 0 && j == 0) continue;
                            // 检查周围九宫格
                            int nx = x + i;
                            int ny = y + j;

                            if (nx >= 0 && nx < w && ny >= 0 && ny < h && world[nx][ny] == Tileset.NOTHING) {
                                world[nx][ny] = Tileset.WALL;
                            }
                        }
                    }
                }
            }
        }
    }
}