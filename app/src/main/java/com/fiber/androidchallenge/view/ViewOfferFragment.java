package com.fiber.androidchallenge.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fiber.androidchallenge.R;
import com.fiber.androidchallenge.interfaces.OnUiListener;
import com.fiber.androidchallenge.model.Offers;
import com.fiber.androidchallenge.utils.App;
import com.fiber.androidchallenge.utils.Constant;
import com.fiber.androidchallenge.utils.Logger;
import com.fiber.androidchallenge.utils.UiListener;
import com.fiber.androidchallenge.utils.Utility;

import java.util.List;

/**
 * This fragment using to show offer list after server response.
 * It'll show offer list at recycle view when offer list comes otherwise show no offers available.
 * For Ui Notify using UiListener with action value.This action register at onStart() and unregister
 * at onDestroy()
 * Created by Mahmud on 14/4/2016.
 */
public class ViewOfferFragment extends Fragment implements OnUiListener {
    private static final String TAG = ViewOfferFragment.class.getSimpleName();
    private TextView mTextViewNoOffer;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    //this actions variable using to notify ui with several action.Now only 1 action use for list update.
    private int[] actions={Constant.ACTION_OFFER_LIST_UPDATE};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.view_offer_fragment, container, false);
        mTextViewNoOffer=(TextView)view.findViewById(R.id.text_view_no_offer);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.recycle_view_offer);
        mLinearLayoutManager=new LinearLayoutManager(App.getContext());
        return view;
    }

    /**
     * Register the listener with actions to update UI.
     */
    @Override
    public void onStart() {
        super.onStart();
        UiListener.getInstance().registerListener(actions,this);
    }

    /**
     * After getting list of offer it'll show at recycler view adapter.
     * If list is empty it'llonly show "No offers" text.
     * @param offersList
     */

    private void updateView(List<Offers> offersList){
        if(offersList.size()==0){
            mTextViewNoOffer.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else{
            Logger.debugLog(TAG,"updateView:");
            mTextViewNoOffer.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            OfferListRecyclerViewAdapter adapter=new OfferListRecyclerViewAdapter();
            adapter.setAdapterData(offersList);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setAdapter(adapter);
        }

    }

    /**
     * This callback action using to update this view.Now only one action added ACTION_OFFER_LIST_UPDATE,
     * only to notify listview item.This callback received action type and Object.At Object list of offer
     * exist and it's pass to updateView method.
     *
     * @param actionType
     * @param object
     */
    @Override
    public void onReceivedAction(int actionType, Object object) {
        switch (actionType){
            case Constant.ACTION_OFFER_LIST_UPDATE:
                List<Offers> offersList=(List<Offers>)object;
                updateView(offersList);
                break;
            default:
                break;

        }
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
