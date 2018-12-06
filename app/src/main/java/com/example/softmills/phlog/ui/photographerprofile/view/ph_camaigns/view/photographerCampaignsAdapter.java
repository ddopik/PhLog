package com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.commonmodel.Campaign;


import java.util.List;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class photographerCampaignsAdapter extends RecyclerView.Adapter<photographerCampaignsAdapter.CampaignViewHolder> {



    private List<Campaign> homeCampaignList;
    private Context context;
    public CampaignLister campaignLister;

    public photographerCampaignsAdapter(Context context, List<Campaign> homeCampaignList) {
        this.context = context;
        this.homeCampaignList = homeCampaignList;

    }


    @NonNull
    @Override
    public CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new CampaignViewHolder(layoutInflater.inflate(R.layout.view_holder_home_campaigns, viewGroup, false));


    }

    @Override
    public void onBindViewHolder(@NonNull CampaignViewHolder campaignViewHolder, int i) {

        Campaign homeCampaign = homeCampaignList.get(i);
        campaignViewHolder.campaignImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campaignLister != null) {
                    campaignLister.onCampaignClicked(homeCampaign.id.toString());
                }
            }
        });
//. todo HASHED UNTIL BACK END FEED BACK
//        GlideApp.with(context).load(homeCampaign.imageCover)
//                .error(R.drawable.splash_screen_background)
//                .into(new SimpleTarget<Drawable>() {
//                    @Override
//                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
//                        campaignViewHolder.campaignImage.setBackground(resource);
//                    }
//                });
//        campaignViewHolder.campaignBusinessName.setText(homeCampaign.business.userName);
//        campaignViewHolder.campaignTitle.setText(homeCampaign.titleEn);
//        campaignViewHolder.campaignDayLeft.setText(homeCampaign.leftDays);
//        campaignViewHolder.campaignDayLeft.append(" "+context.getResources().getString(R.string.days_left));
//        GlideApp.with(context).load(homeCampaign.business.thumbnail).apply(RequestOptions.circleCropTransform()).into(campaignViewHolder.campaignBusinessIcon);
        //////////////////////////////////////////
    }

    @Override
    public int getItemCount() {
        return homeCampaignList.size();
    }

    public class CampaignViewHolder extends RecyclerView.ViewHolder {
        ImageView campaignImage;
        ImageView campaignBusinessIcon;
        TextView campaignBusinessName, campaignTitle, campaignDayLeft;
        Button joinCampaignBtn;

        public CampaignViewHolder(View view) {
            super(view);

            campaignBusinessIcon = view.findViewById(R.id.campaign_icon);
            campaignImage = view.findViewById(R.id.campaign_img);
            campaignBusinessName = view.findViewById(R.id.campaign_name);
            campaignTitle = view.findViewById(R.id.campaign_title);
            campaignDayLeft = view.findViewById(R.id.campaign_day_left);
//            readMeBtn = view.findViewById(R.id.remind_me_btn);
            joinCampaignBtn = view.findViewById(R.id.join_campaign_btn);

        }
    }


    public interface CampaignLister {

        void onCampaignClicked(String campaignID);

    }
}
