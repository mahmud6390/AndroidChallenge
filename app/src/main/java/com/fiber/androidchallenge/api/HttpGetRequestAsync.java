package com.fiber.androidchallenge.api;


import android.os.AsyncTask;

import com.fiber.androidchallenge.interfaces.IHttpResponseListener;
import com.fiber.androidchallenge.interfaces.IParamsKey;
import com.fiber.androidchallenge.model.HttpResponse;
import com.fiber.androidchallenge.model.InputParam;
import com.fiber.androidchallenge.model.ServerResponse;
import com.fiber.androidchallenge.utils.Logger;
import com.fiber.androidchallenge.utils.Utility;
import com.google.gson.Gson;

/**
 * This Async task class handle http request on background and response on ui thread.
 * It's received a callback to send the response as success or failed.
 *
 * Created by Mahmud on 14/4/2016.
 *
 */
public class HttpGetRequestAsync extends AsyncTask<InputParam,String,Object> implements IParamsKey {
    private final String TAG=HttpGetRequestAsync.class.getSimpleName();
    private IHttpResponseListener responseListener;
    public HttpGetRequestAsync(IHttpResponseListener responseListener){
        this.responseListener=responseListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * At do in background
     * @param params
     * @return
     */
    @Override
    protected Object doInBackground(InputParam... params) {
        InputParam inputParam=params[0];
        RestApiClient apiClient=new RestApiClient();
        apiClient.AddParam(KEY_APP_ID,String.valueOf(inputParam.getAppId()));
        apiClient.AddParam(KEY_U_ID,inputParam.getUid());
        apiClient.AddParam(KEY_IP,inputParam.getIpAddress());
        apiClient.AddParam(KEY_LOCALE,inputParam.getLocale());
        apiClient.AddParam(KEY_DEVICE_ID,inputParam.getDeviceId());
        apiClient.AddParam(KEY_PS_TIME,String.valueOf(inputParam.getPsTime()));
        apiClient.AddParam(KEY_PUB0,inputParam.getPub0());
        apiClient.AddParam(KEY_PAGE,String.valueOf(inputParam.getPage()));
        apiClient.AddParam(KEY_TIME_STAMP,String.valueOf(inputParam.getUnixTimeSpan()));


        String sortedUrl=apiClient.getSortedConcatUrl();
        String finalUrl=makeUrl(sortedUrl,inputParam.getApiKey());

        HttpResponse response=apiClient.executeRequest(URL,finalUrl,inputParam.getApiKey());
        if(response.getResponse()!=null && response.getStatusCode()==STATUS_CODE){
            ServerResponse serverResponse=null;
            try{
                Gson gson=new Gson();
                serverResponse=gson.fromJson(response.getResponse(),ServerResponse.class);
               Logger.debugLog(TAG, serverResponse.code+":mess:"+serverResponse.message);
            }catch (Exception e){
                Logger.debugLog(TAG,"Exception"+e.toString());
            }

            return serverResponse;
        }
        else{
            return response.getResponse();
        }

    }

    /**
     * Make url from sorted url and api key
     * Step 1: concat the sorted param with api key
     * Step 2: Make hash key from url
     * Step 3: Append hash key with sorted url
     * Created string is the final string with param key and value
     *
     * @param sortedUrl
     * @param apiKey
     * @return
     */

    private String makeUrl(String sortedUrl, String apiKey) {
        Logger.debugLog(TAG,"sortedUrl:"+sortedUrl);
        String concatUrlWithApiKey=sortedUrl+"&"+apiKey;
        String hashKey= Utility.getHashString(concatUrlWithApiKey);
        String finalUrl=sortedUrl+"&"+KEY_HASH+"="+hashKey;
        Logger.debugLog(TAG,"finalString:"+finalUrl);
        return finalUrl;
    }

    /**
     * At object parameter it'll pass ServerResponse object or string.
     * case 1: If it's instance of ServerResponse then it'll get the json data.
     * case 2: If it's passing string with (status code+message) as failed.
     * case 3: Otherwise it response was null or any other exception it'll send failed message
     * @param serverResponse
     */
    @Override
    protected void onPostExecute(Object serverResponse) {
        if(serverResponse instanceof ServerResponse && serverResponse!=null){
            responseListener.onResultSuccess(serverResponse);
        }
        else if(serverResponse instanceof String){
            String failedMsg=(String)serverResponse;
            Logger.debugLog(TAG,"failedMsg:"+failedMsg);
            responseListener.onResultFail(failedMsg);
        }
        else{
            responseListener.onResultFail("Failed to data retrieve!!!!");
        }

    }

}
