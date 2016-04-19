package com.fiber.androidchallenge.interfaces;

/**
 * HTTP request all param list
 * Created by Mahmud on 14/4/2016.
 */
public interface IParamsKey {
   String URL="http://api.fyber.com/feed/v1/offers.json";
   int STATUS_CODE =200;
   String HEADER_KEY_CONNECTION="connection";
   String HEADER_KEY_CONNECTION_VALUE="keep-alive";
   String HEADER_KEY_ENCODING="Accept-Encoding";
   String HEADER_KEY_ENCODING_VALUE="gzip";
   String HEADER_KEY_X_SPON="X-Sponsorpay-Response-Signature";
   String KEY_APP_ID="appid";
   String KEY_U_ID="uid";
   String KEY_IP="ip";
   String KEY_LOCALE="locale";
   String KEY_DEVICE_ID="device_id";
   String KEY_PS_TIME="ps_time";
   String KEY_PUB0="pub0";
   String KEY_PAGE="page";
   String KEY_TIME_STAMP="timestamp";
   String KEY_HASH="hashkey";

}
