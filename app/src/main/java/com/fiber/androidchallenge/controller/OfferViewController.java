package com.fiber.androidchallenge.controller;

import com.fiber.androidchallenge.api.HttpGetRequestAsync;
import com.fiber.androidchallenge.interfaces.IHttpResponseListener;
import com.fiber.androidchallenge.model.InputParam;
import com.fiber.androidchallenge.model.Offers;
import com.fiber.androidchallenge.model.ServerResponse;
import com.fiber.androidchallenge.utils.Constant;
import com.fiber.androidchallenge.utils.Logger;
import com.fiber.androidchallenge.utils.UiListener;

import java.util.List;

/**
 * This controller class send input param to http parser and handle the response to notify UI.
 * Created by Mahmud on 19/4/2016.
 */
public class OfferViewController implements IHttpResponseListener {
    private static final String TAG =OfferViewController.class.getSimpleName() ;

    public void setInputData(InputParam inputParam){
        HttpGetRequestAsync request=new HttpGetRequestAsync(this);
        request.execute(inputParam);
    }
    public void setInputDataForTestCase(InputParam inputParam,IHttpResponseListener listener){
        HttpGetRequestAsync request=new HttpGetRequestAsync(listener);
        request.execute(inputParam);
    }

    /**
     * This call back function called after getting success result from server response.
     * It'll cast the Object as Server response where as list of Offer exist.
     * If the offer list size 0.it'll show no offer available.To update
     * the offer list view.
     * It'll remove this "add info fragment" and notify the "view offer fragment" with action and offerlist.
     * "View offer fragment" register this action at oncreateview and unregister when destroy the view.
     * @param object
     */
    @Override
    public void onResultSuccess(Object object) {
        ServerResponse response=(ServerResponse)object;
        List<Offers> offerList=response.offersList;
        Logger.debugLog(TAG,"onResultSuccess"+response.code+":"+response.message+":"+offerList.size());
        UiListener.getInstance().notifyUi(Constant.ACTION_OFFER_LIST_UPDATE,offerList);

    }

    @Override
    public void onResultFail(String failedMessage) {

        UiListener.getInstance().notifyUi(Constant.ACTION_FAILED,failedMessage);
    }
}
