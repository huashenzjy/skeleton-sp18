package byog.Core;

import byog.TileEngine.TETile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
/**
 * @Author: huashen
 * @CreateTime: 2024-12-24  20:20
 * @Description: TODO
 * @Version: 1.0
 **/
public class RoomGenerator {
    List<Room> rooms;
    private Random random; // 随机数生成器

    public RoomGenerator() {
    }

    public RoomGenerator(long seed) {
       setRandom(new Random(seed));
        rooms = new ArrayList<>();
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public  List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @description: 随机生成一个房间并检查是否与已有房间重叠
     * @author: huashen
     * @date: 2024/12/24 下午8:22
     * @param: mapWidth 地图宽度
     * @param: mapHeight 地图高度
     * @return: Room 随机生成的房间
     */
    public Room generateRoom(int mapWidth, int mapHeight) {
        // Room room = new Room();
        //Room room2 = room.randomRoom(mapWidth, mapHeight);
        Room newRoom = Room.randomRoom(mapWidth, mapHeight, random);
        // 检查是否与已有房间重叠 还有和 边界重叠
        if (isOverlaps(newRoom) || isOverlapsCorner(newRoom, mapWidth, mapHeight)) {
            return generateRoom(mapWidth, mapHeight); // 递归调用以重新生成
        }
        rooms.add(newRoom);
        return newRoom;
    }
    private boolean isOverlaps(Room room) {
        for (Room r : rooms) {
            if (r != null && r.overlap(room)) { // 检查 r 是否为 null
                return true;
            }
        }
        return false;
    }
private boolean isOverlapsCorner(Room room, int mapWidth, int mapHeight) {
   int backspace = 2; // 留出边界的距离， 1 为外墙，还有个1 是空格
    int thisLeft = room.getPosition().getX();
    int thisRight = thisLeft + room.getRoomWidth() - 1;
    int thisBottom = room.getPosition().getY();
    int thisTop = thisBottom + room.getRoomHeight() - 1;

    // 检查当前房间是否超出地图边界（至少离边界有一个单位的距离）
    return thisLeft < backspace || thisRight >= mapWidth - backspace || thisBottom < backspace || thisTop >= mapHeight - backspace;
}


    /**
     * @description: 打印房间
     * @author: huashen
     * @date: 2024/12/24 下午8:14
     * @param: world 传入的地图
     * @param: room 传入的房间
     * @return: void
     **/
    public void printRoom(TETile[][] world, Room room) {
        int startX = room.getPosition().getX();
        int startY = room.getPosition().getY();
        int endX = startX + room.getRoomWidth();
        int endY = startY + room.getRoomHeight(); // 修改这里

        for (int x = startX; x < endX; x += 1) {
            for (int y = startY; y < endY; y += 1) {
                Room.helpPrint(world, startX, startY, endX, endY, x, y);
            }
        }
    }

}
