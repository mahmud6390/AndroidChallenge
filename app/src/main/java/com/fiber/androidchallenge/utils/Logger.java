package com.fiber.androidchallenge.utils;

import android.util.Log;

public class Logger {
	private static boolean mDebug=true;
	public static void debugLog(String tag,String msg){
		if(mDebug){
			Log.d(tag, msg);
		}
	}
	public static void errorLog(String tag,String msg){
		if(mDebug){
			Log.e(tag, msg);
		}
	}
	public static void infoLog(String tag,String msg){
		if(mDebug){
			Log.i(tag, msg);
		}
	}

}
