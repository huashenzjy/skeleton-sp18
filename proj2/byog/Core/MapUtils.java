package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huashen
 * @CreateTime: 2024-12-25  16:48
 * @Description: TODO 找出一个空旷的区域，并将其标记为地图边界。
 * @Version: 1.0
 **/
public class MapUtils {
    public static List<Position> findEmptyArea(TETile[][] World, List<Room> rooms) {
//储存空旷区域的坐标
        List<Position> emptyAreas = new ArrayList<>();
        // i 和j 代表空旷区域的坐标
        for (int i = 0; i < World.length; i++) {
            for (int j = 0; j < World[0].length; j++) {
                if (World[i][j] == Tileset.NOTHING && !isInsideRoom(rooms, i, j)) {
                    emptyAreas.add(new Position(i, j));
                }

            }
        }
        return emptyAreas;
    }

    private static boolean isInsideRoom(List<Room> rooms, int x, int y) {
        for (Room room : rooms) {
            if (room.contains(x, y)) {
                return true;
            }
        }
        return false;
    }
}