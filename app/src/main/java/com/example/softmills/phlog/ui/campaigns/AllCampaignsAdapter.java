package com.example.softmills.phlog.ui.campaigns;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class AllCampaignsAdapter extends RecyclerView.Adapter<AllCampaignsAdapter.CampaignViewHolder> {

    private List<Campaign> homeCampaignList;
    private Context context;
    public CampaignLister campaignLister;

    public AllCampaignsAdapter(Context context, List<Campaign> homeCampaignList) {
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

        if(homeCampaign.isJoined){
            campaignViewHolder.joinCampaignBtn.setVisibility(View.INVISIBLE);
        }else {
            campaignViewHolder.joinCampaignBtn.setVisibility(View.VISIBLE);
        }
        campaignViewHolder.joinCampaignBtn.setOnClickListener(v -> {
            if (campaignLister != null) {
                campaignLister.onFollowCampaign(homeCampaign);
            }
        });

        GlideApp.with(context).load(homeCampaign.imageCover)
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        campaignViewHolder.campaignImage.setBackground(resource);
                    }
                });
        campaignViewHolder.campaignBusinessName.setText(homeCampaign.business.fullName);
        campaignViewHolder.campaignTitle.setText(homeCampaign.titleEn);
        campaignViewHolder.campaignDayLeft.setText(String.valueOf(homeCampaign.daysLeft));
        campaignViewHolder.campaignDayLeft.append(" " + context.getResources().getString(R.string.days_left));
        GlideApp.with(context).load(homeCampaign.business.thumbnail)
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .apply(RequestOptions.circleCropTransform())
                .into(campaignViewHolder.campaignBusinessIcon);
    }

    @Override
    public int getItemCount() {
        return homeCampaignList.size();
    }

    public class CampaignViewHolder extends RecyclerView.ViewHolder {
        private ImageView campaignImage;
        private ImageView campaignBusinessIcon;
        private TextView campaignBusinessName, campaignTitle, campaignDayLeft;
        private Button joinCampaignBtn;

        public CampaignViewHolder(View view) {
            super(view);

            campaignBusinessIcon = view.findViewById(R.id.campaign_icon);
            campaignImage = view.findViewById(R.id.campaign_img);
            campaignBusinessName = view.findViewById(R.id.campaign_name);
            campaignTitle = view.findViewById(R.id.campaign_title);
            campaignDayLeft = view.findViewById(R.id.campaign_days_left);
//            readMeBtn = view.findViewById(R.id.remind_me_btn);
            joinCampaignBtn = view.findViewById(R.id.join_campaign_btn);

        }
    }


    public interface CampaignLister {

        void onCampaignClicked(String campaignID);

        void onFollowCampaign(Campaign campaign);

    }
}
