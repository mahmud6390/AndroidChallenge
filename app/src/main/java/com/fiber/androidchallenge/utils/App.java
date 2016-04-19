package com.fiber.androidchallenge.utils;

import android.app.Application;
import android.content.Context;

/**
 * This application class set the application context at oncreate.So that from run time of this app
 * at any stage we can using this context.
 * Created by USER on 14/4/2016.
 */
public class App extends Application {
    private static Context sContext;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        sContext=getApplicationContext();
    }
    public static Context getContext(){
        return sContext;
    }
}
