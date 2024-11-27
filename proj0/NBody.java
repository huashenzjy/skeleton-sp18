<<<<<<< Updated upstream
/**
 * @Author: huashen
 * @CreateTime: 2024-11-26  16:22
 * @Description: NBody 是一个实际运行模拟的类。此类将没有构造函数。
 * 此类的目标是模拟在其中一个数据文件中指定的 Universe。
 * @Version: 1.0
 **/
public class NBody {
    //您的第一种方法是 readRadius。
// 给定一个文件名，它应该返回
// 一个对应于该文件中 universe 半径的 double，
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        int theNumberOfPlanets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String fileName) {

        In in = new In(fileName);
        int theNumberOfPlanets = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[theNumberOfPlanets]; // 数组空间创建写实在数量
        for (int i = 0; i < theNumberOfPlanets; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            double Vx = in.readDouble();
            double Vy = in.readDouble();
            double mass = in.readDouble();
            String imgName = in.readString();
            planets[i] = new Planet(x, y, Vx, Vy, mass, imgName);
        }
        return planets;
    }

    public static void main(String[] args) {
        String filename = null;
        double T = 0;
        double dt = 0;
        T = Double.parseDouble(args[0]);
        dt = Double.parseDouble(args[1]);
        filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);

        // set the universe scale
        StdDraw.setScale(-radius, radius);

        // 启用双缓冲
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        //StdDraw.pause(2000);

        int num = planets.length;
        for (double time = 0; time < T; time += dt) {
            double xForces[] = new double[num];
            double yForces[] = new double[num];
// 画完背景，再画星球
            StdDraw.picture(0, 0, "images/starfield.jpg");
            //每次外层循环都要计算每个行星的合力，存到数组中
            for (int i = 0; i < num; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            // 代入合力
            for (int i = 0; i < num; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            // draw all the planets
            for (Planet planet : planets) {
                planet.draw();
            }

            // Draw the background image.
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
=======
/**
 * @Author: huashen
 * @CreateTime: 2024-11-26  16:22
 * @Description: NBody 是一个实际运行模拟的类。此类将没有构造函数。
 * 此类的目标是模拟在其中一个数据文件中指定的 Universe。
 * @Version: 1.0
 **/
public class NBody {
    //您的第一种方法是 readRadius。
// 给定一个文件名，它应该返回
// 一个对应于该文件中 universe 半径的 double，
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        int theNumberOfPlanets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String fileName) {

        In in = new In(fileName);
        int theNumberOfPlanets = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[theNumberOfPlanets]; // 数组空间创建写实在数量
        for (int i = 0; i < theNumberOfPlanets; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            double Vx = in.readDouble();
            double Vy = in.readDouble();
            double mass = in.readDouble();
            String imgName = in.readString();
            planets[i] = new Planet(x, y, Vx, Vy, mass, imgName);
        }
        return planets;
    }

    public static void main(String[] args) {
        String filename = null;
        double T = 0;
        double dt = 0;
        T = Double.parseDouble(args[0]);
        dt = Double.parseDouble(args[1]);
        filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);

        // set the universe scale
        StdDraw.setScale(-radius, radius);

        // 启用双缓冲
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        //StdDraw.pause(2000);

        int num = planets.length;
        for (double time = 0; time < T; time += dt) {
            double xForces[] = new double[num];
            double yForces[] = new double[num];
// 画完背景，再画星球
            StdDraw.picture(0, 0, "images/starfield.jpg");
            //每次外层循环都要计算每个行星的合力，存到数组中
            for (int i = 0; i < num; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            // 代入合力
            for (int i = 0; i < num; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            // draw all the planets
            for (Planet planet : planets) {
                planet.draw();
            }

            // Draw the background image.
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
>>>>>>> Stashed changes
}