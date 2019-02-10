package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.social.model.SocialData;

public class SocialAdapterCampaignViewController {

    private Context context;

    public SocialAdapterCampaignViewController(Context context) {
        this.context = context;
    }


    public void setCampaignType_1(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialData, SocialAdapter.OnSocialItemListener onSocialItemListener) {

        socialViewHolder.socialCampaignType1.setVisibility(View.VISIBLE);

        GlideApp.with(context)
                .load(socialData.campaigns.get(0).business.thumbnail)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialCampaignIcon);

        GlideApp.with(context)
                .load(socialData.campaigns.get(0).imageCover)
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialViewHolder.socialCampaignImg);

        socialViewHolder.socialCampaignTitle.setText(socialData.title);
        socialViewHolder.socialCampaignDayLeft.setText(socialData.campaigns.get(0).daysLeft);
        socialViewHolder.socialCampaignName.setText(socialData.campaigns.get(0).titleEn);

        if (onSocialItemListener != null) {
            socialViewHolder.socialJoinCampaignBtn.setOnClickListener(v -> {
                onSocialItemListener.onSocialFollowCampaignClicked(socialData);
            });
            socialViewHolder.socailCampaignConatainer.setOnClickListener(v -> onSocialItemListener.onSocialCampaignClicked(socialData));

        }
    }

}
