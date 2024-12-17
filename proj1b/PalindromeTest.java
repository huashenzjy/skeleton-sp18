import static org.junit.Assert.*;
import org.junit.Test;

public class PalindromeTest {

    @Test
    public void testEmptyString() {
        Palindrome palindrome = new Palindrome();
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testSingleCharacter() {
        Palindrome palindrome = new Palindrome();
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("A")); // 如果大小写需要忽略
    }

    @Test
    public void testSimplePalindromes() {
        Palindrome palindrome = new Palindrome();
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("121"));
    }

    @Test
    public void testNonPalindromes() {
        Palindrome palindrome = new Palindrome();
        assertFalse(palindrome.isPalindrome("abc"));
        assertFalse(palindrome.isPalindrome("hello"));
    }
}