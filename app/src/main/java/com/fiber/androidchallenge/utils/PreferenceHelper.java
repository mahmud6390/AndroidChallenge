package com.fiber.androidchallenge.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mahmud on 14/4/2016.
 */
public class PreferenceHelper {

    private static final String PREFS_NAME = "android_pref";

    private static PreferenceHelper sInstance;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private PreferenceHelper(Context context) {
        mPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    private static PreferenceHelper getInstance() {

        if (sInstance == null){
            sInstance = new PreferenceHelper(App.getContext());
        }
        return sInstance;
    }
    private static PreferenceHelper getInstance(Context context) {

        if (sInstance == null){
            sInstance = new PreferenceHelper(context);
        }
        return sInstance;
    }

    public static String getString(String key, String defValue) {
        return getInstance().mPref.getString(key, defValue);
    }

    public static boolean putString(String key, String value) {
        return getInstance().mEditor.putString(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        return getInstance().mPref.getInt(key, defValue);
    }

    public static boolean putInt(String key, int value) {
        return getInstance().mEditor.putInt(key, value).commit();
    }
    public static boolean removeAllData() {
        return getInstance().mEditor.clear().commit();
    }
    //for test cases shared value check
    public static boolean putString(Context con,String key,String value){
        return getInstance(con).mEditor.putString(key, value).commit();
    }
    public static String getString(Context con,String key, String defValue) {
        return getInstance(con).mPref.getString(key, defValue);
    }
    //end of test cases

}