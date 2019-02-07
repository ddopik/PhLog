package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.social.model.SocialData;

public class SocialAdapterBrandController {

    private Context context;

    public SocialAdapterBrandController(Context context) {
        this.context = context;
    }

    public void setBrandViewType_1(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialData, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.socialBrandType1.setVisibility(View.VISIBLE);

        GlideApp.with(context)
                .load(socialData.brands.get(0).thumbnail)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialBrandIconImg);

        GlideApp.with(context)
                .load(socialData.brands.get(0).brandImageCover)
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialViewHolder.socialBrandImg);

        socialViewHolder.socialBrandName.setText(socialData.brands.get(0).fullName);
        socialViewHolder.socialBrandFollowing.setText(socialData.brands.get(0).followingsCount);

        if (onSocialItemListener != null) {
            socialViewHolder.socialBrandImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSocialItemListener.onSocialBrandClicked(socialData);
                }
            });
        }
        if (onSocialItemListener != null) {
            socialViewHolder.followBrandBtn.setOnClickListener(v -> {
                onSocialItemListener.onSocialBrandFollowClicked(socialData);
            });
        }
    }
}
