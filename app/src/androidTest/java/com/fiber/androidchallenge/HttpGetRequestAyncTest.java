package com.fiber.androidchallenge;

import android.test.ActivityInstrumentationTestCase2;

import com.fiber.androidchallenge.api.HttpGetRequestAsync;
import com.fiber.androidchallenge.interfaces.IHttpResponseListener;
import com.fiber.androidchallenge.model.InputParam;
import com.fiber.androidchallenge.utils.App;
import com.fiber.androidchallenge.utils.Constant;
import com.fiber.androidchallenge.utils.PreferenceHelper;
import com.fiber.androidchallenge.utils.Utility;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
 * Created by Mahmud on 19/4/2016.
 */
public class HttpGetRequestAyncTest  extends ActivityInstrumentationTestCase2{
    public HttpGetRequestAyncTest() {
        super(HttpGetRequestAyncTest.class);
    }
    CountDownLatch signal = null;
    String expected="expected";
    String actual="";

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    /**
     * Input user data via aynctask class and at callback it's check the assert equal.
     * at success callback it's print expected text as output and at failed it's print
     * fail as output
     * @throws Exception
     */
    public void testServerRequest() throws Exception{
        InputParam param=new InputParam();
        param.setApiKey(param.getApiKey());
        param.setAppId(param.getAppId());
        param.setUid(param.getUid());
        param.setDeviceId(PreferenceHelper.getString(Constant.PREF_KEY_DEVICE_ID, Utility.getAdvertisingId(App.getContext())));
        param.setIpAddress(Utility.getIpAddress(App.getContext()));
        param.setLocale(Utility.getLocale());
        param.setPage(Constant.PAGE);
        param.setPsTime(Utility.getUnixTimestamp());
        param.setUnixTimeSpan(Utility.getUnixTimestamp());
        HttpGetRequestAsync ayncTest=new HttpGetRequestAsync(new IHttpResponseListener() {
            @Override
            public void onResultSuccess(Object object) {
                signal.countDown();
                actual="expected";
            }

            @Override
            public void onResultFail(String failedMessage) {
                signal.countDown();
                actual="fail";
            }
        });
        ayncTest.execute(param);
        signal.await(10, TimeUnit.SECONDS);
        assertEquals(expected,actual);
    }
}
