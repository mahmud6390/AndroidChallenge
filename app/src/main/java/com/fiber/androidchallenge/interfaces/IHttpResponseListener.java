package com.fiber.androidchallenge.interfaces;

/**
 * HTTP request response listener with success and fail.
 * Created by Mahmud on 14/4/2016.
 */
public interface IHttpResponseListener {
    void onResultSuccess(Object object);
    void onResultFail(String failedMessage);
}
