package com.fiber.androidchallenge.utils;



import com.fiber.androidchallenge.interfaces.OnUiListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This singleton class using to notify view classes like as Observer pattern.
 * It'll add the actions with corresponding class name at onCreate().
 * And based on action type it'll notify the ui.
 * And unregister when view will be destroy.
 * Created by Mahmud on 14/4/2016.
 */
public class UiListener {
    private static final String TAG =UiListener.class.getSimpleName() ;
    private static volatile UiListener instance;
    private Map<Integer, ArrayList<OnUiListener>> mActionReceiverMap;
    private UiListener(){
        mActionReceiverMap = new HashMap<Integer, ArrayList<OnUiListener>>();
    }
    public static synchronized UiListener getInstance(){
        if (instance == null) {
            synchronized (UiListener.class) {
                if (instance == null) {
                    instance = new UiListener();
                }
            }
        }
        return instance;

    }

    /**
     * It's keep the actions with class name as Map key.value format.
     * @param actions
     * @param onReceiveListener
     */

    public void registerListener(int[] actions, OnUiListener onReceiveListener) {
        synchronized (mActionReceiverMap) {
            for (Integer action : actions) {
                if (!mActionReceiverMap.containsKey(action)) {
                    mActionReceiverMap.put(action, new ArrayList<OnUiListener>());
                }
                ArrayList<OnUiListener> onReceiveListeners = mActionReceiverMap.get(action);
                String className = onReceiveListener.getClass().getName();
                for (OnUiListener onListener : onReceiveListeners) {
                    if (onListener.getClass().getName().equals(className)) {
                        onReceiveListeners.remove(onListener);
                        break;
                    }
                }
                Logger.debugLog(TAG, "add actn = " + action + " " + className);
                mActionReceiverMap.get(action).add(onReceiveListener);
            }
        }
    }

    public void unregisterListener(int[] actions, OnUiListener onReceiveListener) {
        synchronized (mActionReceiverMap) {
            for (Integer action : actions) {
                if (mActionReceiverMap.containsKey(action)) {
                    mActionReceiverMap.get(action).remove(onReceiveListener);
                }
            }
        }
    }
    public  void notifyUi(int action, Object object) {
        try {
            synchronized (mActionReceiverMap) {
                if (mActionReceiverMap.containsKey(action)) {
                    ArrayList<OnUiListener> onReceiveListeners = mActionReceiverMap.get(action);
                    for (OnUiListener onListener : onReceiveListeners) {
                        onListener.onReceivedAction(action, object);
                    }
                }
            }
        } catch (Exception e) {
          Logger.debugLog(TAG,"Exception:"+e.toString());
        }

    }

}
