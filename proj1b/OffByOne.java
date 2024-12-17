/**
 * @Author: huashen
 * @CreateTime: 2024-12-11  15:46
 * @Description: TODO  : A class for off-by-1 comparators.
 * 差 1 比较器的类
 * @Version: 1.0
 **/
public class OffByOne implements CharacterComparator {

    //equalChars 对于恰好相差 1 的字符返回 true
    @Override
    public boolean equalChars(char x, char y) {
        int i = x - y;
        i = Math.abs(i);
        if (i == 1 || i == 0) {
            return true;
        }
        return false;
    }

}