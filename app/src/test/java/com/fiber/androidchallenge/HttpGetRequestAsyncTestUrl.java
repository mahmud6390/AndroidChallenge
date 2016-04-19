package com.fiber.androidchallenge;

import com.fiber.androidchallenge.api.HttpGetRequestAsync;
import com.fiber.androidchallenge.interfaces.IHttpResponseListener;
import com.fiber.androidchallenge.utils.Constant;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by USER on 19/4/2016.
 */
public class HttpGetRequestAsyncTestUrl {
    /**
     * This test case make the final url with hash key.
     * This is private method so reflection using to access method.
     * Passed parameter of sorted param and api key.
     * It'll return expected url with hashkey
     * @throws Exception
     */
    @Test
    public void testMakeUrl() throws Exception{
        String sortedUrl="appid=157&device_id=2b6f0cc904d137be2e1730235f5664094b831186&ip=212.45.111.17&locale=de&page=2&ps_time=1312211903&pub0=campaign2&timestamp=1312553361&uid=player1";
        String apiKey="e95a21621a1865bcbae3bee89c4d4f84";
        String expected="appid=157&device_id=2b6f0cc904d137be2e1730235f5664094b831186&ip=212.45.111.17&locale=de&page=2&ps_time=1312211903&pub0=campaign2&timestamp=1312553361&uid=player1&hashkey=7a2b1604c03d46eec1ecd4a686787b75dd693c4d";
        HttpGetRequestAsync httpGetRequestAsync=new HttpGetRequestAsync(new IHttpResponseListener() {
            @Override
            public void onResultSuccess(Object object) {

            }

            @Override
            public void onResultFail(String failedMessage) {

            }
        });
        Class myClass = Class.forName(HttpGetRequestAsync.class.getName());
        Method method = myClass.getDeclaredMethod("makeUrl", new Class[] { String.class, String.class });
        method.setAccessible(true);
        Object outPut = method.invoke(httpGetRequestAsync, new Object[] { sortedUrl,apiKey });
        Assert.assertEquals(expected,(String)outPut);
    }
}
