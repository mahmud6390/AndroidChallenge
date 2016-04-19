package com.fiber.androidchallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * This values comes from JSON response.Keys are serialized by GSON
 * Created by mahmud on 14/4/2016.
 */
public class Offers {
    @SerializedName("title")
    public String title;
    @SerializedName("teaser")
    public String teaser;
    @SerializedName("thumbnail")
    public Thumbnail thumbnail;
    @SerializedName("payout")
    public int payout;

    public class Thumbnail{
        @SerializedName("hires")
        public String hires;
    }

}
