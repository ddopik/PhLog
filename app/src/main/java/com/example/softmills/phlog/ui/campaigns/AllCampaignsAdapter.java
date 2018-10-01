package com.example.softmills.phlog.ui.campaigns;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.model.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class AllCampaignsAdapter extends RecyclerView.Adapter<AllCampaignsAdapter.CampaignViewHolder> {

    private List<Campaign> campaignList;
    private Context context;
    public  AllCampaignsAdapter(Context context,List<Campaign> campaignList){
        this.context=context;
        this.campaignList=campaignList;

    }


    @NonNull
    @Override
    public CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        return new CampaignViewHolder(layoutInflater.inflate(R.layout.view_holder_home_campaigns,viewGroup,false));


    }

    @Override
    public void onBindViewHolder(@NonNull CampaignViewHolder campaignViewHolder, int i) {


//        GlideApp.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(imageView); //set campaign rounded icon
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public class CampaignViewHolder extends RecyclerView.ViewHolder {
        public CampaignViewHolder(View view) {
            super(view);
        }
    }
}
