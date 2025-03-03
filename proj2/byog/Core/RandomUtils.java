package byog.Core;

import java.util.Random;

/**
 * A library of static methods to generate pseudo-random numbers from
 * different distributions (bernoulli, uniform, gaussian, discrete,
 * and exponential). Also includes methods for shuffling an array and
 * other randomness related stuff you might want to do. Feel free to
 * modify this file.
 * <p>
 * Adapted from https://introcs.cs.princeton.edu/java/22library/StdRandom.java.html
*
 */
public class RandomUtils {

    /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     */
    public static double uniform(Random random) {
        return random.nextDouble();
    }

    /**
     * Returns a random integer uniformly in [0, n).
     *
     * @param n number of possible integers
     * @return a random integer uniformly between 0 (inclusive) and {@code n} (exclusive)
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public static int uniform(Random random, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }
        return random.nextInt(n);
    }


    /**
     * Returns a random long integer uniformly in [0, n).
     *
     * @param n number of possible {@code long} integers
     * @return a random long integer uniformly between 0 (inclusive) and {@code n} (exclusive)
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public static long uniform(Random random, long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }

        // https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#longs-long-long-long-
        long r = random.nextLong();
        long m = n - 1;

        // power of two
        if ((n & m) == 0L) {
            return r & m;
        }

        // reject over-represented candidates
        long u = r >>> 1;
        while (u + m - (r = u % n) < 0L) {
            u = random.nextLong() >>> 1;
        }
        return r;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////


    /**
     * Returns a random integer uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random integer uniformly in [a, b)
     * @throws IllegalArgumentException if {@code b <= a}
     * @throws IllegalArgumentException if {@code b - a >= Integer.MAX_VALUE}
     */
    public static int uniform(Random random, int a, int b) {
        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(random, b - a);
    }

    /**
     * Returns a random real number uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless {@code a < b}
     */
    public static double uniform(Random random, double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(random) * (b - a);
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability <em>p</em>.
     *
     * @param p the probability of returning {@code true}
     * @return {@code true} with probability {@code p} and
     * {@code false} with probability {@code p}
     * @throws IllegalArgumentException unless {@code 0} &le; {@code p} &le; {@code 1.0}
     */
    public static boolean bernoulli(Random random, double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        }
        return uniform(random) < p;
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability 1/2.
     *
     * @return {@code true} with probability 1/2 and
     * {@code false} with probability 1/2
     */
    public static boolean bernoulli(Random random) {
        return bernoulli(random, 0.5);
    }

    /**
     * Returns a random real number from a standard Gaussian distribution.
     *
     * @return a random real number from a standard Gaussian distribution
     * (mean 0 and standard deviation 1).
     */
    public static double gaussian(Random random) {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        do {
            x = uniform(random, -1.0, 1.0);
            y = uniform(random, -1.0, 1.0);
            r = x * x + y * y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random gaussian
    }

    /**
     * Returns a random real number from a Gaussian distribution with mean &mu;
     * and standard deviation &sigma;.
     *
     * @param mu    the mean
     * @param sigma the standard deviation
     * @return a real number distributed according to the Gaussian distribution
     * with mean {@code mu} and standard deviation {@code sigma}
     */
    public static double gaussian(Random random, double mu, double sigma) {
        return mu + sigma * gaussian(random);
    }

    /**
     * Returns a random integer from a geometric distribution with success
     * probability <em>p</em>.
     *
     * @param p the parameter of the geometric distribution
     * @return a random integer from a geometric distribution with success
     * probability {@code p}; or {@code Integer.MAX_VALUE} if
     * {@code p} is (nearly) equal to {@code 1.0}.
     * @throws IllegalArgumentException unless {@code p >= 0.0} and {@code p <= 1.0}
     */
    public static int geometric(Random random, double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        }
        // using algorithm given by Knuth
        return (int) Math.ceil(Math.log(uniform(random)) / Math.log(1.0 - p));
    }

    /**
     * Returns a random integer from a Poisson distribution with mean &lambda;.
     *
     * @param lambda the mean of the Poisson distribution
     * @return a random integer from a Poisson distribution with mean {@code lambda}
     * @throws IllegalArgumentException unless {@code lambda > 0.0} and not infinite
     */
    public static int poisson(Random random, double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        }
        if (Double.isInfinite(lambda)) {
            throw new IllegalArgumentException("lambda must not be infinite: " + lambda);
        }
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        double p = 1.0;
        double expLambda = Math.exp(-lambda);
        do {
            k++;
            p *= uniform(random);
        } while (p >= expLambda);
        return k - 1;
    }

    /**
     * Returns a random real number from the standard Pareto distribution.
     *
     * @return a random real number from the standard Pareto distribution
     */
    public static double pareto(Random random) {
        return pareto(random, 1.0);
    }

    /**
     * Returns a random real number from a Pareto distribution with
     * shape parameter &alpha;.
     *
     * @param alpha shape parameter
     * @return a random real number from a Pareto distribution with shape
     * parameter {@code alpha}
     * @throws IllegalArgumentException unless {@code alpha > 0.0}
     */
    public static double pareto(Random random, double alpha) {
        if (!(alpha > 0.0)) {
            throw new IllegalArgumentException("alpha must be positive: " + alpha);
        }
        return Math.pow(1 - uniform(random), -1.0 / alpha) - 1.0;
    }

    /**
     * Returns a random real number from the Cauchy distribution.
     *
     * @return a random real number from the Cauchy distribution.
     */
    public static double cauchy(Random random) {
        return Math.tan(Math.PI * (uniform(random) - 0.5));
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param probabilities the probability of occurrence of each integer
     * @return a random integer from a discrete distribution:
     * {@code i} with probability {@code probabilities[i]}
     * @throws IllegalArgumentException if {@code probabilities} is {@code null}
     * @throws IllegalArgumentException if sum of array entries is not (very nearly) equal to 1.0
     * @throws IllegalArgumentException unless {@code probabilities[i] >= 0.0} for each index i
     */
    public static int discrete(Random random, double[] probabilities) {
        if (probabilities == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        double eps = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            if (!(probabilities[i] >= 0.0)) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: "
                                                   + probabilities[i]);
            }
            sum += probabilities[i];
        }
        if (sum > 1.0 + eps || sum < 1.0 - eps) {
            throw new IllegalArgumentException("sum of array entries does not approximately "
                                               + "equal 1.0: " + sum);
        }

        // the for loop may not return a value when both r is (nearly) 1.0 and when the
        // cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
        while (true) {
            double r = uniform(random);
            sum = 0.0;
            for (int i = 0; i < probabilities.length; i++) {
                sum = sum + probabilities[i];
                if (sum > r) {
                    return i;
                }
            }
        }
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param frequencies the frequency of occurrence of each integer
     * @return a random integer from a discrete distribution:
     * i with probability proportional to frequencies[i]
     * @throws IllegalArgumentException if frequencies is null
     * @throws IllegalArgumentException if all array entries are 0
     * @throws IllegalArgumentException if frequencies[i] is negative for any index i
     * @throws IllegalArgumentException if sum of frequencies exceeds Integer.MAX_VALUE (2^31 - 1)
     */
    public static int discrete(Random random, int[] frequencies) {
        if (frequencies == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        long sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] < 0) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: "
                                                   + frequencies[i]);
            }
            sum += frequencies[i];
        }
        if (sum == 0) {
            throw new IllegalArgumentException("at least one array entry must be positive");
        }
        if (sum >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("sum of frequencies overflows an int");
        }

        // pick index i with probabilitity proportional to frequency
        double r = uniform(random, (int) sum);
        sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            sum += frequencies[i];
            if (sum > r) {
                return i;
            }
        }

        // can't reach here
        assert false;
        return -1;
    }

    /**
     * Returns a random real number from an exponential distribution
     * with rate &lambda;.
     *
     * @param lambda the rate of the exponential distribution
     * @return a random real number from an exponential distribution with
     * rate {@code lambda}
     * @throws IllegalArgumentException unless {@code lambda > 0.0}
     */
    public static double exp(Random random, double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        }
        return -Math.log(1 - uniform(random)) / lambda;
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws IllegalArgumentException if {@code a} is {@code null}
     */
    public static void shuffle(Random random, Object[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(random, n - i);     // between i and n-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws IllegalArgumentException if {@code a} is {@code null}
     */
    public static void shuffle(Random random, double[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(random, n - i);     // between i and n-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws IllegalArgumentException if {@code a} is {@code null}
     */
    public static void shuffle(Random random, int[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(random, n - i);     // between i and n-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws IllegalArgumentException if {@code a} is {@code null}
     */
    public static void shuffle(Random random, char[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(random, n - i);     // between i and n-1
            char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param a  the array to shuffle
     * @param lo the left endpoint (inclusive)
     * @param hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static void shuffle(Random random, Object[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(random, hi - i);     // between i and hi-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param a  the array to shuffle
     * @param lo the left endpoint (inclusive)
     * @param hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static void shuffle(Random random, double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(random, hi - i);     // between i and hi-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param a  the array to shuffle
     * @param lo the left endpoint (inclusive)
     * @param hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static void shuffle(Random random, int[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(random, hi - i);     // between i and hi-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Returns a uniformly random permutation of <em>n</em> elements.
     *
     * @param n number of elements
     * @return an array of length {@code n} that is a uniformly random permutation
     * of {@code 0}, {@code 1}, ..., {@code n-1}
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int[] permutation(Random random, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("argument is negative");
        }
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }
        shuffle(random, perm);
        return perm;
    }

    /**
     * Returns a uniformly random permutation of <em>k</em> of <em>n</em> elements.
     *
     * @param n number of elements
     * @param k number of elements to select
     * @return an array of length {@code k} that is a uniformly random permutation
     * of {@code k} of the elements from {@code 0}, {@code 1}, ..., {@code n-1}
     * @throws IllegalArgumentException if {@code n} is negative
     * @throws IllegalArgumentException unless {@code 0 <= k <= n}
     */
    public static int[] permutation(Random random, int n, int k) {
        if (n < 0) {
            throw new IllegalArgumentException("argument is negative");
        }
        if (k < 0 || k > n) {
            throw new IllegalArgumentException("k must be between 0 and n");
        }
        int[] perm = new int[k];
        for (int i = 0; i < k; i++) {
            int r = uniform(random, i + 1);    // between 0 and i
            perm[i] = perm[r];
            perm[r] = i;
        }
        for (int i = k; i < n; i++) {
            int r = uniform(random, i + 1);    // between 0 and i
            if (r < k) {
                perm[r] = i;
            }
        }
        return perm;
    }

    // throw an IllegalArgumentException if x is null
    // (x can be of type Object[], double[], int[], ...)
    private static void validateNotNull(Object x) {
        if (x == null) {
            throw new IllegalArgumentException("argument is null");
        }
    }

    // throw an exception unless 0 <= lo <= hi <= length
    private static void validateSubarrayIndices(int lo, int hi, int length) {
        if (lo < 0 || hi > length || lo > hi) {
            throw new IllegalArgumentException("subarray indices out of bounds: [" + lo + ", "
                                               + hi + ")");
        }
    }
}
//package byog.Core;
//
//import java.util.Random;
//
///**
// * 一个用于生成不同分布（伯努利、均匀、高斯、离散和指数）的伪随机数的静态方法库。
// * 还包括用于洗牌数组和其他与随机性相关操作的方法。可以自由修改此文件。
// * <p>
// * 参考自 https://introcs.cs.princeton.edu/java/22library/StdRandom.java.html
// */
//public class RandomUtils {
//
//    /**
//     * 返回 [0, 1) 区间内的一个随机实数。
//     *
//     * @return [0, 1) 区间内的一个随机实数
//     */
//    public static double uniform(Random random) {
//        return random.nextDouble();
//    }
//
//    /**
//     * 返回 [0, n) 区间内的一个随机整数。
//     *
//     * @param n 可能的整数个数
//     * @return [0, n) 区间内的一个随机整数
//     * @throws IllegalArgumentException 如果 n <= 0
//     */
//    public static int uniform(Random random, int n) {
//        if (n <= 0) {
//            throw new IllegalArgumentException("参数必须为正数: " + n);
//        }
//        return random.nextInt(n);
//    }
//
//    /**
//     * 返回 [0, n) 区间内的一个随机长整数。
//     *
//     * @param n 可能的长整数个数
//     * @return [0, n) 区间内的一个随机长整数
//     * @throws IllegalArgumentException 如果 n <= 0
//     */
//    public static long uniform(Random random, long n) {
//        if (n <= 0L) {
//            throw new IllegalArgumentException("参数必须为正数: " + n);
//        }
//
//        // https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#longs-long-long-long-
//        long r = random.nextLong();
//        long m = n - 1;
//
//        // 如果 n 是 2 的幂
//        if ((n & m) == 0L) {
//            return r & m;
//        }
//
//        // 拒绝过量表示的候选值
//        long u = r >>> 1;
//        while (u + m - (r = u % n) < 0L) {
//            u = random.nextLong() >>> 1;
//        }
//        return r;
//    }
//
//    ///////////////////////////////////////////////////////////////////////////
//    // 以下静态方法间接依赖于通过上述静态方法调用的 java.util.Random。
//    ///////////////////////////////////////////////////////////////////////////
//
//    /**
//     * 返回 [a, b) 区间内的一个随机整数。
//     *
//     * @param a 左端点
//     * @param b 右端点
//     * @return [a, b) 区间内的一个随机整数
//     * @throws IllegalArgumentException 如果 b <= a 或 b - a >= Integer.MAX_VALUE
//     */
//    public static int uniform(Random random, int a, int b) {
//        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
//            throw new IllegalArgumentException("无效范围: [" + a + ", " + b + ")");
//        }
//        return a + uniform(random, b - a);
//    }
//
//    /**
//     * 返回 [a, b) 区间内的一个随机实数。
//     *
//     * @param a 左端点
//     * @param b 右端点
//     * @return [a, b) 区间内的一个随机实数
//     * @throws IllegalArgumentException 如果 a >= b
//     */
//    public static double uniform(Random random, double a, double b) {
//        if (!(a < b)) {
//            throw new IllegalArgumentException("无效范围: [" + a + ", " + b + ")");
//        }
//        return a + uniform(random) * (b - a);
//    }
//
//    /**
//     * 从成功概率为 p 的伯努利分布返回一个随机布尔值。
//     *
//     * @param p 返回 true 的概率
//     * @return 以概率 p 返回 true，以概率 1-p 返回 false
//     * @throws IllegalArgumentException 如果 p 不在 [0.0, 1.0] 范围内
//     */
//    public static boolean bernoulli(Random random, double p) {
//        if (!(p >= 0.0 && p <= 1.0)) {
//            throw new IllegalArgumentException("概率 p 必须在 0.0 和 1.0 之间: " + p);
//        }
//        return uniform(random) < p;
//    }
//
//    /**
//     * 从成功概率为 1/2 的伯努利分布返回一个随机布尔值。
//     *
//     * @return 以概率 1/2 返回 true，以概率 1/2 返回 false
//     */
//    public static boolean bernoulli(Random random) {
//        return bernoulli(random, 0.5);
//    }
//
//    /**
//     * 返回一个来自标准高斯分布（均值 0 和标准差 1）的随机实数。
//     *
//     * @return 一个来自标准高斯分布的随机实数
//     */
//    public static double gaussian(Random random) {
//        // 使用 Box-Muller 变换的极坐标形式
//        double r, x, y;
//        do {
//            x = uniform(random, -1.0, 1.0);
//            y = uniform(random, -1.0, 1.0);
//            r = x * x + y * y;
//        } while (r >= 1 || r == 0);
//        return x * Math.sqrt(-2 * Math.log(r) / r);
//
//        // 备注：y * Math.sqrt(-2 * Math.log(r) / r)
//        // 是一个独立的标准高斯随机数
//    }
//
//    /**
//     * 返回一个来自均值为 μ 和标准差为 σ 的高斯分布的随机实数。
//     *
//     * @param mu    均值
//     * @param sigma 标准差
//     * @return 一个来自均值为 μ 和标准差为 σ 的高斯分布的随机实数
//     */
//    public static double gaussian(Random random, double mu, double sigma) {
//        return mu + sigma * gaussian(random);
//    }
//
//    /**
//     * 返回一个来自成功概率为 p 的几何分布的随机整数。
//     *
//     * @param p 几何分布的成功概率
//     * @return 一个来自成功概率为 p 的几何分布的随机整数；如果 p 接近 1.0，则返回 Integer.MAX_VALUE。
//     * @throws IllegalArgumentException 如果 p 不在 [0.0, 1.0] 范围内
//     */
//    public static int geometric(Random random, double p) {
//        if (!(p >= 0.0 && p <= 1.0)) {
//            throw new IllegalArgumentException("概率 p 必须在 0.0 和 1.0 之间: " + p);
//        }
//        // 使用 Knuth 提供的算法
//        return (int) Math.ceil(Math.log(uniform(random)) / Math.log(1.0 - p));
//    }
//
//    /**
//     * 返回一个来自均值为 λ 的泊松分布的随机整数。
//     *
//     * @param lambda 泊松分布的均值
//     * @return 一个来自均值为 λ 的泊松分布的随机整数
//     * @throws IllegalArgumentException 如果 λ <= 0.0 或 λ 为无穷大
//     */
//    public static int poisson(Random random, double lambda) {
//        if (!(lambda > 0.0)) {
//            throw new IllegalArgumentException("lambda 必须为正数: " + lambda);
//        }
//        if (Double.isInfinite(lambda)) {
//            throw new IllegalArgumentException("lambda 不能为无穷大: " + lambda);
//        }
//        // 使用 Knuth 提供的算法
//        // 参见 http://en.wikipedia.org/wiki/Poisson_distribution
//        int k = 0;
//        double p = 1.0;
//        double expLambda = Math.exp(-lambda);
//        do {
//            k++;
//            p *= uniform(random);
//        } while (p >= expLambda);
//        return k - 1;
//    }
//
//    /**
//     * 返回一个来自标准帕累托分布的随机实数。
//     *
//     * @return 一个来自标准帕累托分布的随机实数
//     */
//    public static double pareto(Random random) {
//        return pareto(random, 1.0);
//    }
//
//    /**
//     * 返回一个来自形状参数为 α 的帕累托分布的随机实数。
//     *
//     * @param alpha 形状参数
//     * @return 一个来自形状参数为 α 的帕累托分布的随机实数
//     * @throws IllegalArgumentException 如果 α <= 0.0
//     */
//    public static double pareto(Random random, double alpha) {
//        if (!(alpha > 0.0)) {
//            throw new IllegalArgumentException("alpha 必须为正数: " + alpha);
//        }
//        return Math.pow(1 - uniform(random), -1.0 / alpha) - 1.0;
//    }
//
//    /**
//     * 返回一个来自柯西分布的随机实数。
//     *
//     * @return 一个来自柯西分布的随机实数
//     */
//    public static double cauchy(Random random) {
//        return Math.tan(Math.PI * (uniform(random) - 0.5));
//    }
//
//    /**
//     * 返回一个来自指定离散分布的随机整数。
//     *
//     * @param probabilities 每个整数出现的概率
//     * @return 一个来自离散分布的随机整数：i 以概率 probabilities[i] 出现
//     * @throws IllegalArgumentException 如果 probabilities 为 null
//     * @throws IllegalArgumentException 如果数组元素之和不接近 1.0
//     * @throws IllegalArgumentException 如果 probabilities[i] < 0.0 对于任何索引 i
//     */
//    public static int discrete(Random random, double[] probabilities) {
//        if (probabilities == null) {
//            throw new IllegalArgumentException("参数数组为空");
//        }
//        double eps = 1E-14;
//        double sum = 0.0;
//        for (int i = 0; i < probabilities.length; i++) {
//            if (!(probabilities[i] >= 0.0)) {
//                throw new IllegalArgumentException("数组条目 " + i + " 必须非负: " + probabilities[i]);
//            }
//            sum += probabilities[i];
//        }
//        if (sum > 1.0 + eps || sum < 1.0 - eps) {
//            throw new IllegalArgumentException("数组条目之和不接近 1.0: " + sum);
//        }
//
//        // 当 r 接近 1.0 且累积和小于 1.0（由于浮点舍入误差）时，for 循环可能不会返回值
//        while (true) {
//            double r = uniform(random);
//            sum = 0.0;
//            for (int i = 0; i < probabilities.length; i++) {
//                sum = sum + probabilities[i];
//                if (sum > r) {
//                    return i;
//                }
//            }
//        }
//    }
//
//    /**
//     * 返回一个来自指定离散分布的随机整数。
//     *
//     * @param frequencies 每个整数出现的频率
//     * @return 一个来自离散分布的随机整数：i 以频率 proportional to frequencies[i] 出现
//     * @throws IllegalArgumentException 如果 frequencies 为 null
//     * @throws IllegalArgumentException 如果所有数组条目均为 0
//     * @throws IllegalArgumentException 如果 frequencies[i] 为负数 对于任何索引 i
//     * @throws IllegalArgumentException 如果频率总和超过 Integer.MAX_VALUE (2^31 - 1)
//     */
//    public static int discrete(Random random, int[] frequencies) {
//        if (frequencies == null) {
//            throw new IllegalArgumentException("参数数组为空");
//        }
//        long sum = 0;
//        for (int i = 0; i < frequencies.length; i++) {
//            if (frequencies[i] < 0) {
//                throw new IllegalArgumentException("数组条目 " + i + " 必须非负: " + frequencies[i]);
//            }
//            sum += frequencies[i];
//        }
//        if (sum == 0) {
//            throw new IllegalArgumentException("至少有一个数组条目必须为正数");
//        }
//        if (sum >= Integer.MAX_VALUE) {
//            throw new IllegalArgumentException("频率总和超出 int 范围");
//        }
//
//        // 以与频率成比例的概率选择索引
//        double r = uniform(random, (int) sum);
//        sum = 0;
//        for (int i = 0; i < frequencies.length; i++) {
//            sum += frequencies[i];
//            if (sum > r) {
//                return i;
//            }
//        }
//
//        // 不应该到达这里
//        assert false;
//        return -1;
//    }
//
//    /**
//     * 返回一个来自速率为 λ 的指数分布的随机实数。
//     *
//     * @param lambda 指数分布的速率
//     * @return 一个来自速率为 λ 的指数分布的随机实数
//     * @throws IllegalArgumentException 如果 λ <= 0.0
//     */
//    public static double exp(Random random, double lambda) {
//        if (!(lambda > 0.0)) {
//            throw new IllegalArgumentException("lambda 必须为正数: " + lambda);
//        }
//        return -Math.log(1 - uniform(random)) / lambda;
//    }
//
//    public static void shuffle(Random random, Object[] a, int lo, int hi) {
//        validateNotNull(a);
//        validateSubarrayIndices(lo, hi, a.length);
//
//        for (int i = lo; i < hi; i++) {
//            int r = i + uniform(random, hi - i);     // 在 i 和 hi-1 之间
//            Object temp = a[i];
//            a[i] = a[r];
//            a[r] = temp;
//        }
//    }
//
//    /**
//     * 将指定子数组的元素重新排列为均匀随机顺序。
//     *
//     * @param a  要洗牌的数组
//     * @param lo 左端点（包含）
//     * @param hi 右端点（不包含）
//     * @throws IllegalArgumentException 如果 {@code a} 是 {@code null}
//     * @throws IllegalArgumentException 除非 {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
//     */
//    public static void shuffle(Random random, double[] a, int lo, int hi) {
//        validateNotNull(a);
//        validateSubarrayIndices(lo, hi, a.length);
//
//        for (int i = lo; i < hi; i++) {
//            int r = i + uniform(random, hi - i);     // 在 i 和 hi-1 之间
//            double temp = a[i];
//            a[i] = a[r];
//            a[r] = temp;
//        }
//    }
//
//    /**
//     * 将指定子数组的元素重新排列为均匀随机顺序。
//     *
//     * @param a  要洗牌的数组
//     * @param lo 左端点（包含）
//     * @param hi 右端点（不包含）
//     * @throws IllegalArgumentException 如果 {@code a} 是 {@code null}
//     * @throws IllegalArgumentException 除非 {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
//     */
//    public static void shuffle(Random random, int[] a, int lo, int hi) {
//        validateNotNull(a);
//        validateSubarrayIndices(lo, hi, a.length);
//
//        for (int i = lo; i < hi; i++) {
//            int r = i + uniform(random, hi - i);     // 在 i 和 hi-1 之间
//            int temp = a[i];
//            a[i] = a[r];
//            a[r] = temp;
//        }
//    }
//
//    /**
//     * 返回一个长度为 <em>n</em> 的均匀随机排列。
//     *
//     * @param n 元素数量
//     * @return 一个长度为 {@code n} 的数组，是 {@code 0}, {@code 1}, ..., {@code n-1} 的均匀随机排列
//     * @throws IllegalArgumentException 如果 {@code n} 是负数
//     */
//    public static int[] permutation(Random random, int n) {
//        if (n < 0) {
//            throw new IllegalArgumentException("参数为负数");
//        }
//        int[] perm = new int[n];
//        for (int i = 0; i < n; i++) {
//            perm[i] = i;
//        }
//        shuffle(random, perm);
//        return perm;
//    }
//
//    /**
//     * 返回一个长度为 <em>k</em> 的均匀随机排列，从 <em>n</em> 个元素中选择。
//     *
//     * @param n 元素总数
//     * @param k 选择的元素数量
//     * @return 一个长度为 {@code k} 的数组，是 {@code k} 个元素从 {@code 0}, {@code 1}, ..., {@code n-1} 中的均匀随机排列
//     * @throws IllegalArgumentException 如果 {@code n} 是负数
//     * @throws IllegalArgumentException 除非 {@code 0 <= k <= n}
//     */
//    public static int[] permutation(Random random, int n, int k) {
//        if (n < 0) {
//            throw new IllegalArgumentException("参数为负数");
//        }
//        if (k < 0 || k > n) {
//            throw new IllegalArgumentException("k 必须在 0 和 n 之间");
//        }
//        int[] perm = new int[k];
//        for (int i = 0; i < k; i++) {
//            int r = uniform(random, i + 1);    // 在 0 和 i 之间
//            perm[i] = perm[r];
//            perm[r] = i;
//        }
//        for (int i = k; i < n; i++) {
//            int r = uniform(random, i + 1);    // 在 0 和 i 之间
//            if (r < k) {
//                perm[r] = i;
//            }
//        }
//        return perm;
//    }
//
//    // 如果 x 为 null，则抛出 IllegalArgumentException
//    // (x 可以是 Object[], double[], int[], ...)
//    private static void validateNotNull(Object x) {
//        if (x == null) {
//            throw new IllegalArgumentException("参数为 null");
//        }
//    }
//
//    // 抛出异常，除非 0 <= lo <= hi <= length
//    private static void validateSubarrayIndices(int lo, int hi, int length) {
//        if (lo < 0 || hi > length || lo > hi) {
//            throw new IllegalArgumentException("子数组索引超出范围: [" + lo + ", " + hi + ")");
//        }
//    }
//}
//