package com.example.softmills.phlog.ui.userprofile.view.campaigns.view;

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

public class UserCampaignsAdapter extends RecyclerView.Adapter<UserCampaignsAdapter.CampaignsViewHolder> {


    private List<Campaign> userCampaignsList;
    private Context context;
    private CampignsAction campignsAction;

    public UserCampaignsAdapter(Context context, List<Campaign> userCampaignsList) {
        this.context = context;
        this.userCampaignsList = userCampaignsList;
    }

    @NonNull
    @Override
    public CampaignsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new CampaignsViewHolder(layoutInflater.inflate(R.layout.view_holder_campaigns, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignsViewHolder campaignsViewHolder, int i) {
//        campaignsViewHolder.campaignImg.setOnClickListener().


        GlideApp.with(context)
                .load(userCampaignsList.get(i).campaignUrl)
                .centerCrop()
//                .override(450,450)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(campaignsViewHolder.campaignImg);
        if(campignsAction !=null)
        campaignsViewHolder.campaignImg.setOnClickListener(view ->{ campignsAction.onCampaignsClicked(userCampaignsList.get(i));});
    }

    @Override
    public int getItemCount() {
        return userCampaignsList.size();
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
