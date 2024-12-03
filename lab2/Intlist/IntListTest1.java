import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntListTest1 {

    private IntList emptyList;
    private IntList listA;
    private IntList listB;

    @Before
    public void setUp() {
        emptyList = new IntList();
        listA = new IntList(1, new IntList(2, new IntList(3, null)));
        listB = new IntList(4, new IntList(5, new IntList(6, null)));
    }

    @Test
    public void catenate_NullFirstList_ReturnsSecondList() {
        IntList result = IntList.catenate(null, listB);
        assertEquals(listB, result);
    }

    @Test
    public void catenate_NullSecondList_ReturnsFirstList() {
        IntList result = IntList.catenate(listA, null);
        assertEquals(listA, result);
    }

    @Test
    public void catenate_BothListsNotNull_ReturnsCombinedList() {
        IntList result = IntList.catenate(listA, listB);
        IntList expected = new IntList(1, new IntList(2, new IntList(3, new IntList(4, new IntList(5, new IntList(6, null))))));
        assertEquals(expected, result);
    }

    @Test
    public void catenate_FirstListEmpty_ReturnsSecondList() {
        IntList result = IntList.catenate(emptyList, listB);
        assertEquals(listB, result);
    }

    @Test
    public void catenate_SecondListEmpty_ReturnsFirstList() {
        IntList result = IntList.catenate(listA, emptyList);
        assertEquals(listA, result);
    }

    @Test
    public void catenate_BothListsEmpty_ReturnsEmptyList() {
        IntList result = IntList.catenate(emptyList, emptyList);
        assertEquals(emptyList, result);
    }
}
