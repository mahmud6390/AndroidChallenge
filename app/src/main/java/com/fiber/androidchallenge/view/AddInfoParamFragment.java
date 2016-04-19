package com.fiber.androidchallenge.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fiber.androidchallenge.R;
import com.fiber.androidchallenge.controller.OfferViewController;
import com.fiber.androidchallenge.interfaces.OnUiListener;
import com.fiber.androidchallenge.model.InputParam;
import com.fiber.androidchallenge.utils.App;
import com.fiber.androidchallenge.utils.Constant;
import com.fiber.androidchallenge.utils.Logger;
import com.fiber.androidchallenge.utils.PreferenceHelper;
import com.fiber.androidchallenge.utils.UiListener;
import com.fiber.androidchallenge.utils.Utility;

/**
 * By this class user add input data and send this data to server with callback.
 * After getting success callback this fragment remove and show the result with other fragment(ViewOfferFragment).
 * At fail callback it's showing the failed message with toast message.
 * It'll handle the internet connectivity check when press "Add" button
 * Created by mahmud on 14/4/2016.
 */
public class AddInfoParamFragment extends Fragment implements View.OnClickListener,OnUiListener {


    private static final String TAG =AddInfoParamFragment.class.getSimpleName() ;
    private Context mContext;
    private EditText mEditTextApiKey,mEditTextAppId,mEditTextUid,mEditTextPub0;
    private EditText mEditTextDeviceId,mEditTextIp,mEditTextLocale;
    private int[] actions={Constant.ACTION_OFFER_LIST_UPDATE,Constant.ACTION_FAILED};

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext= App.getContext();
        View view = (View) inflater.inflate(R.layout.add_info_fragment, container, false);
        initUi(view);
        return view;
    }

    /**
     * At onStart it'll register the ui listener action from controller.
     * Retrive device id,default user data and set them at view.
     */
    @Override
    public void onStart() {
        super.onStart();
        UiListener.getInstance().registerListener(actions,this);
        retrieveDeviceId();
        InputParam inputParam=getDefaultSettingsValue();
        mEditTextApiKey.setText(inputParam.getApiKey());
        mEditTextAppId.setText(inputParam.getAppId()+"");
        mEditTextUid.setText(inputParam.getUid());
        mEditTextPub0.setText(inputParam.getPub0());
        mEditTextDeviceId.setText(inputParam.getDeviceId());
        mEditTextIp.setText(inputParam.getIpAddress());
        mEditTextLocale.setText(inputParam.getLocale());
    }
    private void initUi(View view){
        ((Button)view.findViewById(R.id.button_cancel)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.button_add)).setOnClickListener(this);
        mEditTextApiKey=(EditText)view.findViewById(R.id.edit_text_api_key);
        mEditTextAppId=(EditText)view.findViewById(R.id.edit_text_app_id);
        mEditTextUid=(EditText)view.findViewById(R.id.edit_text_user_id);
        mEditTextPub0=(EditText)view.findViewById(R.id.edit_text_pub0);
        mEditTextDeviceId=(EditText)view.findViewById(R.id.edit_text_device_id);
        mEditTextIp=(EditText)view.findViewById(R.id.edit_text_ip);
        mEditTextLocale=(EditText)view.findViewById(R.id.edit_text_locale);

    }

    /**
     * This method using to retrieve device id from Android advertising identifier.Though this it is unique id
     * so it'll store at device shared preference and first it's check from shared preference if not exist
     * then get this value from background thread.After getting it'll store at share preference and update
     * edittext.
     */
    private void retrieveDeviceId(){
        String deviceId= PreferenceHelper.getString(Constant.PREF_KEY_DEVICE_ID,null);
        Logger.debugLog(TAG,"onStart>>deviceId:"+deviceId);
        if(deviceId==null){
            new Thread(){
                public void run(){
                    final String gId=Utility.getAdvertisingId(mContext);
                    Logger.debugLog(TAG,"did:"+gId);
                    PreferenceHelper.putString(Constant.PREF_KEY_DEVICE_ID,gId);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEditTextDeviceId.setText(gId);
                        }
                    });
                }
            }.start();
        }
    }

    /**
     * It'll set the value of device ipadress and locale from device setting via Utility class.
     * @return
     */
    private InputParam getDefaultSettingsValue(){
        InputParam inputParam=new InputParam();
        inputParam.setIpAddress(Utility.getIpAddress(mContext));
        inputParam.setLocale(Utility.getLocale());
        inputParam.setDeviceId(PreferenceHelper.getString(Constant.PREF_KEY_DEVICE_ID,""));
        return inputParam;
    }

    /**
     * Update user input data from edittext value.set unixtime and pstime from system current time via Utility class.
     * @return
     */
    private InputParam updateUserInputParam(){
        InputParam inputParam=new InputParam();
        inputParam.setApiKey(mEditTextApiKey.getText().toString());
        inputParam.setUid(mEditTextUid.getText().toString());
        String appId=mEditTextAppId.getText().toString();
        inputParam.setAppId(appId.isEmpty()?0:Integer.parseInt(appId));
        inputParam.setPub0(mEditTextPub0.getText().toString());
        inputParam.setLocale(mEditTextLocale.getText().toString());
        inputParam.setDeviceId(mEditTextDeviceId.getText().toString());
        inputParam.setIpAddress(mEditTextIp.getText().toString());
        inputParam.setPsTime(Utility.getUnixTimestamp());
        inputParam.setUnixTimeSpan(Utility.getUnixTimestamp());
        return inputParam;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_cancel:
                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.button_add:
                if(!isEmpty()){
                    if(!Utility.isConnectedToInternet(mContext)){
                        Toast.makeText(App.getContext(),"Internet not available!!!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    InputParam inputParam=updateUserInputParam();
                    OfferViewController controller=new OfferViewController();
                    controller.setInputData(inputParam);
                }

                break;
            default:
                break;
        }
    }

    /**
     * If offer list action comes from controller it'll remove add view.And at failed state it's showing the fail message
     * @param actionType
     * @param object
     */

    @Override
    public void onReceivedAction(int actionType, Object object) {
        switch (actionType){
            case Constant.ACTION_OFFER_LIST_UPDATE:
                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case Constant.ACTION_FAILED:
                if(object instanceof String){
                    String failedMessage=(String)object;
                    Toast.makeText(App.getContext(),failedMessage,Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }

    /**
     * This method check empty validation of edittext.Multiple edittext should be empty.Return true boolean if empty.
     * @return
     */
    private boolean isEmpty(){
        boolean isEmpty=false;
        if(mEditTextApiKey.getText().toString().isEmpty()){
            mEditTextApiKey.setError("API key not empty");
           isEmpty=true;
        }
        if(mEditTextAppId.getText().toString().isEmpty()){
            mEditTextAppId.setError("App id not empty");
           isEmpty=true;
        }
        if(mEditTextUid.getText().toString().isEmpty()){
            mEditTextUid.setError("User id not empty");
            isEmpty=true;
        }
        return isEmpty;
    }
    /**
     * Unregister the actions.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        UiListener.getInstance().unregisterListener(actions,this);
    }
}
