package com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.model.Campaign;


import java.util.List;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class photographerCampaignsAdapter extends RecyclerView.Adapter<photographerCampaignsAdapter.CampaignsViewHolder> {


    private List<Campaign> photoGrapherCampaignsList;
    private Context context;
    private photographerCampaignsAdapter.CampignsAction campignsAction;

    public photographerCampaignsAdapter(Context context, List<Campaign> userCampaignsList) {
        this.context = context;
        this.photoGrapherCampaignsList = userCampaignsList;
    }

    @NonNull
    @Override
    public photographerCampaignsAdapter.CampaignsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new photographerCampaignsAdapter.CampaignsViewHolder(layoutInflater.inflate(R.layout.view_holder_campaigns, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull photographerCampaignsAdapter.CampaignsViewHolder campaignsViewHolder, int i) {
//        campaignsViewHolder.campaignImg.setOnClickListener().


        GlideApp.with(context)
                .load(photoGrapherCampaignsList.get(i).campaignUrl)
                .centerCrop()
//                .override(450,450)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(campaignsViewHolder.campaignImg);
        if(campignsAction !=null)
            campaignsViewHolder.campaignImg.setOnClickListener(view ->{ campignsAction.onCampaignsClicked(photoGrapherCampaignsList.get(i));});
    }

    @Override
    public int getItemCount() {
        return photoGrapherCampaignsList.size();
    }

    class CampaignsViewHolder extends RecyclerView.ViewHolder {

        ImageView campaignImg;

        public CampaignsViewHolder(View view) {
            super(view);
            campaignImg=view.findViewById(R.id.campaign_img);
        }
    }

    public interface CampignsAction{
        void onCampaignsClicked(Campaign campaign);
    }
}
