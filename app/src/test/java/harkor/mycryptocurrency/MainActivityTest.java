package harkor.mycryptocurrency;

import org.junit.Test;

import harkor.mycryptocurrency.view.MainActivity;

import static org.junit.Assert.*;

public class MainActivityTest {
MainActivity mainActivity=new MainActivity();
    @Test
    public void myFirstTestEver() {
        assertEquals(50,mainActivity.testOfTesting(5));
    }
    @Test
    public void boolTest(){
        assertTrue(mainActivity.isMoreThanZero(50));
        assertTrue(mainActivity.isMoreThanZero(5));
        assertFalse(mainActivity.isMoreThanZero(0));
        assertFalse(mainActivity.isMoreThanZero(-1));
    }
}