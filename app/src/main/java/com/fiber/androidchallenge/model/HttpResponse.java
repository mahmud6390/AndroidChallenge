package com.fiber.androidchallenge.model;

/**
 * This class using for http response.by this,we received only code and response message for further usages.
 * Like response code 200 and message.we send this as getResponse and getStatusCode
 * Created by mahmud on 15/4/2016
 */
public class HttpResponse {
    private String mResponse;
    private int mStatusCode;
    public int getStatusCode() {
        return mStatusCode;
    }
    public void setStatusCode(int mStatusCode) {
        this.mStatusCode = mStatusCode;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String mResponse) {
        this.mResponse = mResponse;
    }



}
