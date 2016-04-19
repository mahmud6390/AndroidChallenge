package com.fiber.androidchallenge.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * This class using to get all static field like shared pref key,action type also.
 * Created by mahmud on 14/4/2016.
 */
public class Utility {
    private static final String TAG = Utility.class.getSimpleName();
    //this char array use to byte ro hex at hash key generate
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    /**
     * This method using to check internet connectivity status as boolean format.
     *
     * @param context
     * @return
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable() && conMgr
                .getActiveNetworkInfo().isConnected());
        Logger.debugLog(TAG, "connected:" + connected);
        return connected;
    }

    /**
     * Make SHA1 hask key on parameter string
     *
     * @param str
     * @return
     */

    public static String getHashString(String str) {
        String hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = str.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();
            hash = makeHexFromByte(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash;
    }

    private static String makeHexFromByte(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * This method return device id.First it's check isLimitAdTrackingEnabled of Android Advertising.
     * Only then it return the AdvertisingId.If enable is false or any exception occured then it's return
     * device IMEI id which is unique id.
     *
     * @param mContext
     * @return
     */
    public static String getAdvertisingId(Context mContext) {
        if (mContext == null) {
            return null;
        }
        String advertisingId = null;
        try {
            boolean isEnable = AdvertisingIdClient.getAdvertisingIdInfo(mContext).isLimitAdTrackingEnabled();
            Logger.debugLog(TAG, "isEnable:" + isEnable);
            //advertisingId = AdvertisingIdClient.getAdvertisingIdInfo(mContext).getId();
            //Logger.debugLog("DEVICE_ID","advertisingId:"+advertisingId);
            if (isEnable) {
                advertisingId = AdvertisingIdClient.getAdvertisingIdInfo(mContext).getId();
            } else {
                advertisingId = getDeviceIMEI(mContext);
            }
        } catch (IOException | IllegalStateException | GooglePlayServicesNotAvailableException
                | GooglePlayServicesRepairableException e) {
            advertisingId = getDeviceIMEI(mContext);
        }

        return advertisingId;
    }

    private static String getDeviceIMEI(Context mContext) {
        if (mContext == null) {
            return null;
        }
        return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    /**
     * This method return ip address based on connectivity.Based on connectivity first it check wifi state.
     * If wifi state enable it's return wifi ip address.
     * If mobile data connected it's check inetaddress for IPV4 and return it's ip address
     * @param mContext
     * @return
     */
    public static String getIpAddress(Context mContext) {
        if (mContext == null) {
            return null;
        }
        String deviceIP = "";
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                deviceIP = getWifiIp(mContext);
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                deviceIP = getMobileIP();
            }
        } else {
            // not connected to the internet
        }

        return deviceIP;
    }

    public static long getUnixTimestamp() {
        return (System.currentTimeMillis() / 1000L);
    }

    /**
     * Get device landuage as formate "en"
     * @return
     */
    public static String getLocale() {
        return Locale.getDefault().getLanguage();
    }

    private static String getWifiIp(Context mContext) {
        try {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            Logger.debugLog("DEVICE_ID", "wifi ip:" + ipAddress);
            return String.format(Locale.getDefault(), "%d.%d.%d.%d",
                    (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get IP For mobile
     */
    private static String getMobileIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses(); enumInetAddress.hasMoreElements(); ) {
                    InetAddress inetAddress = enumInetAddress.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }

                }
            }
        } catch (SocketException ex) {
            Logger.debugLog("DEVICE_ID", "exception:" + ex.toString());
        }
        return null;
    }

}
