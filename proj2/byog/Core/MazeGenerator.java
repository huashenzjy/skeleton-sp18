package byog.Core;//package byog.Core;
//
//import byog.TileEngine.TETile;
//import byog.TileEngine.Tileset;
//
//import java.util.*;
//
///**
// * @Author: huashen
// * @CreateTime: 2024-12-25  16:30
// * @Description: TODO 负责生成迷宫
// * @Version: 1.0
// **/
//public class MazeGenerator {
//    private Random random;
//    private List<Position> walkAble;
//
//    public MazeGenerator(Long seed) {
//        random = new Random(seed);
//    }
//
//    /**
//     * @description: 生成迷宫
//     * @author: huashen
//     * @date: 2024/12/25 下午5:28
//     * @param: world
//     * @param: rooms
//     * @param: startX 开始x坐标位置
//     * @param: startY  开始y坐标位置
//     * @return: void
//     **/
//    public void generateMaze(TETile[][] world, List<Room> rooms, int startX, int startY, int sign) {
//        // 用深度优先算法生成迷宫
//        Deque<Position> stack = new ArrayDeque<>(); // 使用 ArrayDeque 实现 Deque
//        stack.push(new Position(startX, startY)); // push 方法仍然可用
//        world[startX][startY] = Tileset.FLOOR;
//        // 回溯算法
//        while (!stack.isEmpty()) {
//            Position current = stack.peek();//获取栈顶元素， 不弹出，偷看一下 , 一直递归调用
//            List<Position> walkAbleNeighbors = getWalkableNeighbors(world, rooms, current);
//            if (!walkAbleNeighbors.isEmpty()) {
//                // 任意选择一个可行的位置一直任意走下去，直到走不通为止
//                Position nextPosition = walkAbleNeighbors.get(random.nextInt(walkAbleNeighbors.size()));
//                //已经选择了其中一个方向
//
//                // 雕刻已经走过的路，因为我们的步长 为 2 ，所以需要计算中间值 ,意思是中间和， next 都要设置为 Tileset.FLOOR
//
//                //假设 current 的坐标是 (1, 1)，next 的坐标是 (3, 1)。
//                //如果不计算中间值，直接将 world[3][1] 设置为 Tileset.FLOOR，那么地图上会是这样的：
//                //@.@@
//                //.....
//                //.....
//                //其中 @ 代表 Tileset.FLOOR，. 代表其他瓦片。可以看到，(1, 1) 和 (3, 1) 之间是断开的。
//                //如果计算中间值 midX = (1 + 3) / 2 = 2，并将 world[2][1] 也设置为 Tileset.FLOOR，那么地图上会是这样的：
//                //@@@@
//                //.....
//                //.....
//                //这样就形成了一条连续的路径。
//                int midX = (current.getX() + nextPosition.getX()) / 2;
//                int midY = (current.getY() + nextPosition.getY()) / 2;
//                if (sign == 1) {
//                    world[midX][midY] = Tileset.WALL;
//                    world[nextPosition.getX()][nextPosition.getY()] = Tileset.WALL;
//                } else if (sign== 2) {
//                    world[midX][midY] = Tileset.FLOWER;
//                    world[nextPosition.getX()][nextPosition.getY()] = Tileset.FLOWER;
//                }
//
//                //将这个位置入栈 ，// 进行迭代调用
//                stack.push(nextPosition);
//            } else {
//                stack.pop(); // 回溯，弹出栈顶元素 ， 如果是最后一个就退出循环
//            }
//
//            // 后处理：填充死路
//            fillDeadEnds(world);
//
//        }
//    }
//
//    private void fillDeadEnds(TETile[][] world) {
//        for (int x = 1; x < world.length - 1; x++) {
//            for (int y = 1; y < world[0].length - 1; y++) {
//                if (world[x][y] == Tileset.NOTHING) {
//                    int wallCount = 0;
//                    if (world[x + 1][y] == Tileset.WALL) wallCount++;
//                    if (world[x - 1][y] == Tileset.WALL) wallCount++;
//                    if (world[x][y + 1] == Tileset.WALL) wallCount++;
//                    if (world[x][y - 1] == Tileset.WALL) wallCount++;
//
//                    // 如果是死路（三面是墙），则填充
//                    if (wallCount >= 3) {
//                        world[x][y] = Tileset.GRASS;
//                        System.out.println("sdasd");
//                    }
//                }
//            }
//        }
//    }
////        private List<Position> getWalkableNeighbors(TETile[][] world, List<Room> rooms, Position pos) {
////        // 获得可行的 前后左右 相隔2 的邻居 集合  ，如果为空，就是不能走了，返回空集合
////        ArrayList<Position> neighbors = new ArrayList<>();
////        // 步长为2
////        int[][] directions = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};
////        for (int[] direction : directions) {
////            int x = pos.getX() + direction[0];
////            int y = pos.getY() + direction[1];
////            if (isWalkAble(world, rooms, x, y)) {
////                neighbors.add(new Position(x, y));
////            }
////        }
////        return neighbors;
////    }
//    private List<Position> getWalkableNeighbors(TETile[][] world, List<Room> rooms, Position current) {
//        //        // 获得可行的 前后左右 相隔2 的邻居 集合  ，如果为空，就是不能走了，返回空集合
//        List<Position> neighbors = new ArrayList<>();
//
//        int x = current.getX();
//        int y = current.getY();
//
//        int[][] directions = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}}; // 步长为 2
//
//        for (int[] dir : directions) {
//            int nextX = x + dir[0];
//            int nextY = y + dir[1];
//            int midX = (x + nextX) / 2;
//            int midY = (y + nextY) / 2;
//
//          //   关键的边界检查：
//            if (nextX >= 1 && nextX < world.length - 1 && nextY >= 1 && nextY < world[0].length - 1 &&
//                    midX >= 1 && midX < world.length - 1 && midY >= 1 && midY < world[0].length - 1 &&
//                    world[nextX][nextY] == Tileset.NOTHING && world[midX][midY] == Tileset.NOTHING) {
//                Position next = new Position(nextX, nextY);
//                neighbors.add(next);
//            }
//        }
//        return neighbors;
//    }
////    private boolean isInsideAnyRoom(List<Room> rooms, int x, int y) {
////        for (Room room : rooms) {
////            if (room.contains(x, y)) {
////                return true;
////            }
////        }
////        return false;
////    }
////    private boolean isInBounds(TETile[][] world, int x, int y) {
////        return x >= 1 && x < world.length - 1 && y >= 1 && y < world[0].length - 1;
////    }
//
//}

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

public class MazeGenerator {
    private Random random;

    public MazeGenerator(Long seed) {
        random = new Random(seed);
    }

    public void generateMaze(TETile[][] world, List<Room> rooms, int startX, int startY, int wallType) {
        Deque<Position> stack = new ArrayDeque<>();
        stack.push(new Position(startX, startY));
        world[startX][startY] = Tileset.FLOOR;

        int[][] directions = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}}; // 预先计算方向偏移量

        while (!stack.isEmpty()) {
            Position current = stack.peek();
            List<Position> walkableNeighbors = new ArrayList<>();

            for (int[] dir : directions) {
                int nextX = current.getX() + dir[0];
                int nextY = current.getY() + dir[1];
                int midX = (current.getX() + nextX) / 2;
                int midY = (current.getY() + nextY) / 2;

                // 合并边界检查和图块类型检查
                if (nextX > 0 && nextX < world.length - 1 && nextY > 0 && nextY < world[0].length - 1 &&
                        world[nextX][nextY] == Tileset.NOTHING && world[midX][midY] == Tileset.NOTHING) {
                    walkableNeighbors.add(new Position(nextX, nextY));
                }
            }

            if (!walkableNeighbors.isEmpty()) {
                Position nextPosition = walkableNeighbors.get(random.nextInt(walkableNeighbors.size()));
                int midX = (current.getX() + nextPosition.getX()) / 2;
                int midY = (current.getY() + nextPosition.getY()) / 2;

                // 根据 wallType 设置墙壁类型
                if (wallType == 1) {
                    world[midX][midY] = Tileset.WALL;
                    world[nextPosition.getX()][nextPosition.getY()] = Tileset.WALL;
                } else if (wallType == 2) {
                    world[midX][midY] = Tileset.FLOOR;
                    world[nextPosition.getX()][nextPosition.getY()] = Tileset.FLOWER;
                }

                stack.push(nextPosition);
            } else {
                stack.pop();
            }
        }

        // 多次调用 fillDeadEnds 方法
        fillDeadEnds(world);
        fillDeadEnds(world);
    }

    private void fillDeadEnds(TETile[][] world) {
        for (int x = 1; x < world.length - 1; x++) {
            for (int y = 1; y < world[0].length - 1; y++) {
                if (world[x][y] == Tileset.NOTHING) {
                    int wallCount = 0;
                    if (world[x + 1][y] != Tileset.NOTHING) wallCount++;
                    if (world[x - 1][y] != Tileset.NOTHING) wallCount++;
                    if (world[x][y + 1] != Tileset.NOTHING) wallCount++;
                    if (world[x][y - 1] != Tileset.NOTHING) wallCount++;

                    // 填充更多类型的死路
                    if (wallCount >= 3) {
                        world[x][y] = Tileset.FLOWER;
                    }
                }
            }
        }
    }
}