package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * 生成由六边形组成的地图世界。
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        // 初始化渲染引擎，设置窗口大小为WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // 初始化地图，将所有元素初始化为Tileset.NOTHING
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // 添加镶嵌的六边形
        tessellateHexagons(world, new Position(5, 5), 3, 3, 3, Tileset.FLOWER);

        // 将绘制的世界显示在屏幕上
        ter.renderFrame(world);
    }

    /**
     * 在地图上以镶嵌模式添加多个六边形。
     *
     * @param world         地图
     * @param startPosition 镶嵌的起始位置
     * @param hexSize       每个六边形的大小
     * @param numHexesWide  水平方向的六边形数量
     * @param numHexesTall  垂直方向的六边形数量
     * @param tile          绘制使用的瓦片
     */
    public static void tessellateHexagons(TETile[][] world, Position startPosition, int hexSize, int numHexesWide, int numHexesTall, TETile tile) {
        int hexHeight = 2 * hexSize; // 每个六边形的高度
        int hexWidth = hexRowWidth(hexSize, hexSize - 1); // 每个六边形的宽度

        // 外层循环：绘制每一列
        for (int col = 0; col < numHexesWide; col++) {
            // 列的横向偏移
            int colXOffset = col * (hexWidth + 1) / 2;

            // 内层循环：绘制每一列中的每个六边形
            for (int row = 0; row < numHexesTall; row++) {
                int rowYOffset = row * hexHeight * 3 / 4; // 行的纵向偏移

                // 计算当前六边形的左下角位置
                Position hexStart = new Position(startPosition.x + colXOffset, startPosition.y + rowYOffset);

                // 检查是否超出边界，跳过无效六边形
                if (hexStart.x + hexWidth > world.length || hexStart.y + hexHeight > world[0].length) {
                    continue;
                }

                // 绘制六边形
                addHexagon(world, hexStart, hexSize, tile);
            }
        }
    }

    /**
     * 计算第i行的宽度，对于大小为s的六边形。
     *
     * @param s 六边形的大小
     * @param i 行号，其中i=0是最下面一行
     * @return 第i行的宽度
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }

        return s + 2 * effectiveI;
    }

    /**
     * 计算第i行中最左边瓦片的相对x坐标，
     * 假设底行为x坐标为零。例如，如果s=3且i=2，则此函数
     * 返回-2，因为从底部向上的第2行从左边开始2个单位
     * 起始位置，例如
     * xxxx
     * xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i=2，从六边形底部左侧的2个点开始
     * xxxxxx
     * xxxx
     *
     * @param s 六边形的大小
     * @param i 六边形的行号，其中i=0是最底部
     * @return 相对x坐标
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /**
     * 添加一行相同的瓦片。
     *
     * @param world 地图
     * @param p     行的最左边位置
     * @param width 行的宽度（瓦片数量）
     * @param t     绘制使用的瓦片
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * 在地图上添加一个六边形。
     *
     * @param world 地图
     * @param p     六边形的左下角坐标
     * @param s     六边形的大小
     * @param t     绘制使用的瓦片
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        if (s < 2) {
            throw new IllegalArgumentException("六边形必须至少为大小2.");
        }

        // 六边形有2*s行。这段代码从底部行开始迭代，
        // 我们称为行0。
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);

        }
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }
}
