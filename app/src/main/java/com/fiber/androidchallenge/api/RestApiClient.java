package com.fiber.androidchallenge.api;



import com.fiber.androidchallenge.interfaces.IParamsKey;
import com.fiber.androidchallenge.model.HttpResponse;
import com.fiber.androidchallenge.utils.Logger;
import com.fiber.androidchallenge.utils.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.GZIPInputStream;

/**
 * This class using to execute the get http request and response.
 * It's received all params and value,sorted the params as key.Then execute the url with combined param value.
 * After received response based on it's response status code it'll get the response message.
 * If response code 200 it'll concated response string and api key.Then hash the string.If hash string match with
 * header response hash.then return the json formated string as a object.
 * Created by mahmud on 14/4/2016.
 */
public class RestApiClient implements IParamsKey {
    private static final String PARAM_EQUAL ="=" ;
    private static final String PARAM_AND ="&" ;
    //http connection timeout value 10 sec
    private static final int TIMEOUT_CONNECTION =10000;
    //socket read timeout value 2 sec
    private static final int READ_TIMEOUT =2000;
    private static final String TAG =RestApiClient.class.getSimpleName() ;
    private ArrayList<NameValuePair> mParams;

    public RestApiClient(){
        mParams = new ArrayList<NameValuePair>();
    }
    public void AddParam(String name, String value) {
        mParams.add(new BasicNameValuePair(name, value));
    }

    /**
     * This method return the sorted string of param name and value.
     * First it sorted the params as the key name.If params value not empty and null it's append
     * key name with "=" and value append with "&" ,concat total string and return
     * @return
     */
    public String getSortedConcatUrl(){
        if(mParams==null){
            return null;
        }
        Collections.sort(mParams, new Comparator<NameValuePair>(){
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs)
            {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        StringBuffer sb = new StringBuffer();
        for(NameValuePair pairs:mParams){
            if (null != pairs.getValue() && !"".equals(pairs.getValue())){
                sb.append(pairs.getName());
                sb.append(PARAM_EQUAL);
                sb.append(pairs.getValue());
                sb.append(PARAM_AND);
            }
        }
        String concatString = sb.toString();
        if (sb.toString().endsWith(PARAM_AND))
        {
            concatString = sb.substring(0, sb.length() - 1);
        }
        Logger.debugLog(TAG,concatString);

        return concatString;
    }

    /**
     * This method mainlly responsible of http connection stabilised,response and close.
     * First it's encode the params string then decode the string and append with main url.
     * It's set the header connection as keep-alive and response encoding type is:gzip.
     * Based on status code,input stream,header hash,api key it's make a response string.
     * It's return the Object with status code and response message.
     * @param url
     * @param combinedParamString
     * @param apiKey
     * @return
     */
    public HttpResponse executeRequest(String url, String combinedParamString, String apiKey) {
        HttpResponse httpResponseObj = new HttpResponse();
        String response = null;
        int statusCode = 0;
        HttpURLConnection connection = null;
        try {
            String paramString = URLEncoder.encode(combinedParamString, "UTF-8");
            String decoderStr = URLDecoder.decode(paramString, "UTF-8");
            url += "?" + decoderStr;
            URL urltoconnect = new URL(url);
            Logger.debugLog(TAG, "url:" + url);
            connection = (HttpURLConnection) urltoconnect
                    .openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(TIMEOUT_CONNECTION);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty(HEADER_KEY_CONNECTION, HEADER_KEY_CONNECTION_VALUE);
            connection.setRequestProperty(HEADER_KEY_ENCODING, HEADER_KEY_ENCODING_VALUE);
            connection.connect();
            statusCode = connection.getResponseCode();
            String responseHeaderHash = connection.getHeaderField(HEADER_KEY_X_SPON);
            InputStream inputStream=connection.getInputStream();
            response=getResponseString(statusCode,inputStream,apiKey,responseHeaderHash);
        } catch (UnsupportedEncodingException e) {

        } catch (Exception e) {

        } finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
        httpResponseObj.setResponse(response);
        httpResponseObj.setStatusCode(statusCode);
        return httpResponseObj;
    }

    /**
     * Based on status code 200 it's makes a response string with following step.
     * Step 1: get raw string from input stream.
     * Step 2: concat string with api key.
     * Step 3: make this string hash.
     * Step 4: compare this hash with response header hash.if it's match it'll return the string.
     * @param statusCode
     * @param inputStream
     * @param apiKey
     * @param headerHash
     * @return
     */
    public String getResponseString(int statusCode,InputStream inputStream,String apiKey,String headerHash){
        String response=null;
        if (statusCode == STATUS_CODE) {
            try {
                String responseString = getStringFromInputStream(inputStream);
                String concatWithAPI = responseString + apiKey;
                String concatStringHash = Utility.getHashString(concatWithAPI);
                Logger.debugLog(TAG, "responseStr:" + concatStringHash + ":" + headerHash);
                if (headerHash.equalsIgnoreCase(concatStringHash)) {
                    response = responseString;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return response;
    }

    /**
     * Make a string from input stream with string builder.Input stream format GZIPInputstream and character set
     * will be "UTF-8"
     * @param stream
     * @return
     * @throws IOException
     */
    private  String getStringFromInputStream(InputStream stream) throws IOException
    {
        try {
            StringBuilder result = new StringBuilder();
            InputStream in = new GZIPInputStream(stream);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            stream.close();
            return result.toString();
        }catch (NullPointerException e){

        }
        return null;
    }

}
