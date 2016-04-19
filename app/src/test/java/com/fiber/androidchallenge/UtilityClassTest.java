package com.fiber.androidchallenge;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.fiber.androidchallenge.utils.Utility;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by mahmud on 17/4/2016.
 */
public class UtilityClassTest extends InstrumentationTestCase {
    private Context mContext;
    private Utility mUtilityClass;
    @Test
    public void setUp() throws Exception{
        super.setUp();
    }

    @Test
    public void testGetIPAddress(){
        String expected="192.168.1.1";

        mContext=getInstrumentation().getContext();
        String actual=Utility.getIpAddress(mContext);
        assertEquals("Get ip failed",expected,actual);
    }
    @Test
    public void testMobileIp() throws Exception{

//        mUtilityClass=new Utility();
//        Method method = Utility.class.getDeclaredMethod("getMobileIP", String.class);
//        method.setAccessible(true);
//        String output = (String) method.invoke(mUtilityClass);
//        assertEquals("ddd",output);
        Method method = Utility.class.getMethod("getMobileIP", null);
        method.setAccessible(true);
        Object output = method.invoke(null);
        assertNull("", output);
    }
    @Test
    public void testGetHashFromString(){
        String param="appid=157&device_id=2b6f0cc904d137be2e1730235f5664094b831186&ip=212.45.111.17" +
                "&locale=de&page=2&ps_time=1312211903&pub0=campaign2&timestamp=1312553361&uid=player1&e95a21621a1865bcbae3bee89c4d4f84";
        String expectedHash="7a2b1604c03d46eec1ecd4a686787b75dd693c4d";
        String actual=Utility.getHashString(param);
        assertEquals("Get hash key failed",expectedHash,actual);

    }
    @Test
    public void testGetLocate(){
        String actual=Utility.getLocale();
        String expected="en";
        assertEquals("Get locale failed",expected,actual);
    }
}
