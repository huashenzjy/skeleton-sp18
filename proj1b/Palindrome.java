import static org.junit.Assert.assertEquals;

/**
 * @Author: huashen
 * @CreateTime: 2024-12-11  15:46
 * @Description: TODO 回文运算的类
 * A class for palindrome operations.
 * @Version: 1.0
 **/
public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        if (word.length() == 0) {
            return null;
        }
        ArrayDeque<Character> ca = new ArrayDeque<>();
        char[] charArray = word.toCharArray();
        for (char c : charArray) {
            ca.addLast(c);
        }
        return ca;
    }

    //The isPalindrome method should return true
    // if the given word is a 回文数, and false otherwise
    // 判断是不是回文数的方法
    public boolean isPalindrome(String word) {

        if (word.equals(help(word))) {
            return true;
        }
        return false;
    }

    private String help(String word) {
        String original = word;
        ArrayDeque<Character> cd = (ArrayDeque) wordToDeque(word);
        String back = "";
        //  Deque d = palindrome.wordToDeque("persiflage");
        //        String actual = "";
        //        for (int i = 0; i < "persiflage".length(); i++) {
        //            actual += d.removeFirst();
        //        }
        //        assertEquals("persiflage", actual);
        while (cd.size() > 0) {
            back += cd.removeLast();
        }
        return back;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        //差为1 的 回文数
        String back = help(word);
        char[] charArray = word.toCharArray();
        char[] c2 = back.toCharArray();
        for (int i = 0; i < c2.length; i++, i++) {
            boolean b = cc.equalChars(c2[i], charArray[i]);
            if (b == false) {
                return false;
            }
        }
        return true;
    }
}