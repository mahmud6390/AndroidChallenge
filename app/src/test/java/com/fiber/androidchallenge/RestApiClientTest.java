package com.fiber.androidchallenge;

import android.test.InstrumentationTestCase;

import com.fiber.androidchallenge.api.RestApiClient;
import com.fiber.androidchallenge.interfaces.IParamsKey;
import com.fiber.androidchallenge.model.InputParam;
import com.fiber.androidchallenge.utils.Constant;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by USER on 19/4/2016.
 */
public class RestApiClientTest implements IParamsKey {

    /**
     * based on input param inputed value it's sorted the key.Here inputed first key is UId.
     * But expected string first key is appid
     */
    @Test
    public void testSortedConcatUrlTest(){
        RestApiClient apiClient=new RestApiClient();
        InputParam inputParam=new InputParam();
        apiClient.AddParam(KEY_U_ID,inputParam.getUid());
        apiClient.AddParam(KEY_APP_ID,String.valueOf(inputParam.getAppId()));
        apiClient.AddParam(KEY_IP,inputParam.getIpAddress());
        apiClient.AddParam(KEY_LOCALE,inputParam.getLocale());
        apiClient.AddParam(KEY_DEVICE_ID,inputParam.getDeviceId());
        apiClient.AddParam(KEY_PS_TIME,String.valueOf(inputParam.getPsTime()));
        apiClient.AddParam(KEY_PUB0,inputParam.getPub0());
        apiClient.AddParam(KEY_PAGE,String.valueOf(inputParam.getPage()));
        apiClient.AddParam(KEY_TIME_STAMP,String.valueOf(inputParam.getUnixTimeSpan()));
        String actual=apiClient.getSortedConcatUrl();
        Assert.assertTrue(actual.startsWith("appid"));

    }

    /**
     * inputed InputStream is null.so output string should be null
     */
    @Test
    public void testResponseStringTest(){
        int code=200;
        InputStream inputStream=null;
        String apiKey= Constant.API_KEY;
        String headerHash="werwqrewqrewerwerwer";
        RestApiClient apiClient=new RestApiClient();
        Assert.assertNull(apiClient.getResponseString(code, inputStream, apiKey, headerHash));

    }

}
