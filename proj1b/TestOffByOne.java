import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
 // 测试这个
  @Test
    public void TestEqualChars() {
      OffByOne a =  new OffByOne();
      assertFalse(offByOne.equalChars('f','b'));
      assertTrue(offByOne.equalChars('a','b'));

  }
}
