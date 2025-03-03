package byog.lab6;

//import edu.princeton.cs.introcs.StdDraw;

import byog.TileEngine.StdDraw;

import java.awt.*;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn; // 玩家回合
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up byog.TileEngine.StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    /**
     * @author: huashen
     * @date: 2025/2/24 下午9:34
     * @return: String 生成长度为 n 的随机小写字母的字符串
     **/
    public String generateRandomString(int n) {
        // TODO： 生成长度为 n 的随机小写字母的字符串
        StringBuilder randomString = new StringBuilder(n);
        // 生成字符串
        for (int i = 0; i < n; i++) {
            // 获取小写字母其中一个索引
            int index = rand.nextInt(CHARACTERS.length);
            randomString.append(CHARACTERS[index]);
        }
        return randomString.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.text((double) width / 2, (double) height / 2, s);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            drawFrame(c + "");
            StdDraw.pause(500);
            drawFrame(" ");// 清屏
        }
    }

    public String solicitNCharsInput(int n) {
        // n 为位数
        //TODO: Read n letters of player input
        // 读取用户输入的 字符串
        StringBuilder sb = new StringBuilder();
        while (sb.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                sb.append(c);
                drawFrame(sb.toString());
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    // 忽略异常
//                }
            }
        }

        return sb.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts 设置相关的变量
        //TODO: Establish Game loop  建立游戏循环
        gameOver = false;
        round = 1;
        playerTurn = false;
        while (!gameOver) {
            // 开始游戏
            drawFrame("Round" + round);// 显示回合1
            StdDraw.pause(1500);
            playerTurn = true;
            // Generate a random string of length equal to the current round number
            //中文：生成一个随机字符串，字符串的长度等于当前回合数
            String grs = generateRandomString(round);
            flashSequence(grs);
            // 读取 用户输入
            String input = solicitNCharsInput(round);
            //TODO: Check if the input matches the random string
            if (input.equals(grs)) {
                // 玩家输入正确
                drawFrame(ENCOURAGEMENT[round] + " ，You win at " + round);
                round++;
            } else {
                // 玩家输入错误
                drawFrame("Game Over! You made it to round" + round + " but failed.");
                gameOver = true;
                playerTurn = false;
            }
        }

    }
}
