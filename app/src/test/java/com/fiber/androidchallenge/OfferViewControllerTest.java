package com.fiber.androidchallenge;

import android.content.Context;
import android.test.InstrumentationTestCase;
import com.fiber.androidchallenge.controller.OfferViewController;
import com.fiber.androidchallenge.interfaces.IHttpResponseListener;
import com.fiber.androidchallenge.model.InputParam;
import com.fiber.androidchallenge.model.ServerResponse;
import com.fiber.androidchallenge.utils.Constant;
import com.fiber.androidchallenge.utils.PreferenceHelper;
import com.fiber.androidchallenge.utils.Utility;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mahmud on 19/4/2016.
 */
public class OfferViewControllerTest extends InstrumentationTestCase {
    private OfferViewController controller;

    public void setUp() throws Exception{
        super.setUp();
        controller=new OfferViewController();
    }

    /**
     * By this test we can submit user data and at callback it's result true if success otherwise false.
     */
    @Test
    public void testSetInputDataTest(){
        Context context= getInstrumentation().getContext();
        InputParam param=new InputParam();
        param.setApiKey(param.getApiKey());
        param.setAppId(param.getAppId());
        param.setUid(param.getUid());
        param.setDeviceId(PreferenceHelper.getString(Constant.PREF_KEY_DEVICE_ID, Utility.getAdvertisingId(context)));
        param.setIpAddress(Utility.getIpAddress(context));
        param.setLocale(Utility.getLocale());
        param.setPage(Constant.PAGE);
        param.setPsTime(Utility.getUnixTimestamp());
        param.setUnixTimeSpan(Utility.getUnixTimestamp());
        OfferViewController controller=new OfferViewController();
        controller.setInputDataForTestCase(param, new IHttpResponseListener() {
            @Override
            public void onResultSuccess(Object object) {
                ServerResponse response=(ServerResponse)object;
                Assert.assertNotNull(response);
            }

            @Override
            public void onResultFail(String failedMessage) {
                Assert.assertFalse("Failed result",true);
            }
        });

    }

}
