package com.fiber.androidchallenge.model;

import com.fiber.androidchallenge.utils.Constant;

/**
 * This class set get of user input data.
 * First it load some constant data from constant class.
 * Created by mahmud on 14/4/2016.
 */
public class InputParam {

    private String mApiKey= Constant.API_KEY;
    private int mAppId=Constant.APP_ID;
    private String mUid=Constant.USER_ID;
    private String mDeviceId;
    private String mLocale;
    private String mIpAddress;
    private String mPub0=Constant.PUB0;
    private int mPage=Constant.PAGE;
    private long mPsTime;
    private long mUnixTimeSpan;

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }
    public int getPage() {
        return mPage;
    }
    public void setPage(int page) {
        mPage = page;
    }
    public int getAppId() {
        return mAppId;
    }

    public void setAppId(int appId) {
        mAppId = appId;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public String getLocale() {
        return mLocale;
    }

    public void setLocale(String locale) {
        mLocale = locale;
    }

    public String getIpAddress() {
        return mIpAddress;
    }

    public void setIpAddress(String ipAddress) {mIpAddress = ipAddress;
    }

    public String getPub0() {
        return mPub0;
    }

    public void setPub0(String pub0) {
        mPub0 = pub0;
    }

    public long getPsTime() {
        return mPsTime;
    }

    public void setPsTime(long psTime) {
        mPsTime = psTime;
    }
    public long getUnixTimeSpan() {
        return mUnixTimeSpan;
    }

    public void setUnixTimeSpan(long unixTimeSpan) {
        mUnixTimeSpan = unixTimeSpan;
    }



}
