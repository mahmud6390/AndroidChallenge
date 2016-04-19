package com.fiber.androidchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This values comes from JSON response.Keys are serialized by GSON
 * Created by mahmud on 14/4/2016.
 */
public class ServerResponse {
    @SerializedName("code")
    public String code;
    @SerializedName("message")
    public String message;
    @SerializedName("count")
    public int count;
    @SerializedName("pages")
    public int pages;
    @SerializedName("information")
    public Information information=new Information();
    @SerializedName("offers")
    public List<Offers> offersList=new ArrayList<Offers>();
}
