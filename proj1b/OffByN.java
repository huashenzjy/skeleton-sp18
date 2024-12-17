/**
 * @Author: huashen
 * @CreateTime: 2024-12-11  15:46
 * @Description: TODO A class for palindrome operations.
 * 偏差 N 比较器的类
 * @Version: 1.0
 **/
public class OffByN implements CharacterComparator{
   private int N;
    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
            int i = x - y;
            i = Math.abs(i);
            if (i == N) {
                return true;
            }
            return false;
    }
}