package com.fiber.androidchallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * This values comes from JSON response.Keys are serialized by GSON
 * Created by Mahmud on 14/4/2016.
 */
public class Information {
    @SerializedName("app_name")
    public String appName;
    @SerializedName("appid")
    public String appId;
    @SerializedName("virtual_currency")
    public String virtualCurrency;
    @SerializedName("country")
    public String country;
    @SerializedName("language")
    public String language;
    @SerializedName("support_url")
    public String supportUrl;


}
