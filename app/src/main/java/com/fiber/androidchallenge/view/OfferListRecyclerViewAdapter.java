package com.fiber.androidchallenge.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fiber.androidchallenge.R;
import com.fiber.androidchallenge.model.Offers;
import com.fiber.androidchallenge.utils.App;
import com.fiber.androidchallenge.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter show the offer item as list.It's received List<Offers> value.
 * Created by mahmud on 14/4/2016.
 */
public class OfferListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private static final int VIEW_TYPE_DEFAULT=1;
    private static final int VIEW_TYPE_MORE_OFFER=2;
    private static final String TAG =OfferListRecyclerViewAdapter.class.getSimpleName() ;
    private List<Offers> mOffersList;
    public OfferListRecyclerViewAdapter(){
        mOffersList=new ArrayList<Offers>();
    }
    public void setAdapterData(List<Offers> offersList){
        mOffersList=offersList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEW_TYPE_DEFAULT:
                viewHolder = new OfferViewHolder(inflater.inflate(R.layout.offer_list_adapter_item, parent, false));
                break;
            case VIEW_TYPE_MORE_OFFER:
                viewHolder = new MoreOfferViewHolder(inflater.inflate(R.layout.more_offer_adapter_item, parent, false));
                break;
            default:
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case VIEW_TYPE_DEFAULT:
                OfferViewHolder mHolder=(OfferViewHolder)holder;
                Offers offerDto=mOffersList.get(position);
                String title=offerDto.title;
                String teaser=offerDto.teaser;
                int payout=offerDto.payout;
                String thumbUrl=offerDto.thumbnail.hires;
                Logger.debugLog(TAG,"title:"+title+"teaser:"+teaser+":payout:"+payout+"thumbUrl:"+thumbUrl);
                mHolder.textViewTitle.setText(title);
                mHolder.textViewPayout.setText(payout+"");
                mHolder.textViewTeaser.setText(teaser);
                Glide.with(App.getContext())
                        .load(thumbUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(mHolder.imageViewThumb);
                break;
            case VIEW_TYPE_MORE_OFFER:
                MoreOfferViewHolder moreOfferViewHolder=(MoreOfferViewHolder)holder;
                moreOfferViewHolder.buttonMore.setOnClickListener(this);
                break;

            default:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return mOffersList.size();
    }


    @Override
    public int getItemViewType(int position) {
        Logger.debugLog(TAG,"position:"+position+":getItemCount():"+getItemCount());
        return (position==(getItemCount()-1)?VIEW_TYPE_MORE_OFFER:VIEW_TYPE_DEFAULT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_more:
                //TODO
                //need to work here after click more
                Toast.makeText(App.getContext(),"More data will be load!!",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    public static class OfferViewHolder extends RecyclerView.ViewHolder{
        public final TextView textViewTitle,textViewPayout,textViewTeaser;
        public final ImageView imageViewThumb;
        public OfferViewHolder(View itemView) {
            super(itemView);
            textViewTitle=(TextView) itemView.findViewById(R.id.text_view_title);
            imageViewThumb=(ImageView)itemView.findViewById(R.id.image_view_thumb);
            textViewPayout=(TextView) itemView.findViewById(R.id.text_view_payout);
            textViewTeaser=(TextView)itemView.findViewById(R.id.text_view_teaser);
        }
    }
    public static class MoreOfferViewHolder extends RecyclerView.ViewHolder{
        public final Button buttonMore;
        public MoreOfferViewHolder(View itemView) {
            super(itemView);
            buttonMore=(Button)itemView.findViewById(R.id.button_more);
        }
    }
}
