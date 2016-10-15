package ru.innopolis;

import org.junit.Assert;
import org.junit.Test;
import ru.innopolis.utils.Utils;

/**
 * Created by Smith on 14.10.2016.
 */
public class UtilsTest {

    @Test
    public void testGetValuesFromLine(){
        Integer[] expectedRight = new Integer[]{323,43,43};
        Integer[] actualsRight = Utils.<Integer>getValuesFromLine("323 43 43");
        Assert.assertEquals(actualsRight.length,3);
        for(int i=0; i< actualsRight.length; i++){
            Assert.assertEquals(expectedRight[i],actualsRight[i]);
        }
        try {
            Utils.<Integer>getValuesFromLine("323 aaa 43");
            Assert.fail();
        }catch (NumberFormatException e){
            // it's ok
        }catch (Exception e){
            Assert.fail();
        }
    }
}
