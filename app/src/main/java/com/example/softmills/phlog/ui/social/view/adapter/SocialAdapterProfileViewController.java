package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.social.model.Source;

import static com.example.softmills.phlog.Utiltes.Constants.PROFILE_DISPLAY_TYPE_3;

public class SocialAdapterProfileViewController {

    private Context context;

    public SocialAdapterProfileViewController(Context context){
        this.context=context;
    }


    public void setProfileType3(Source source, SocialAdapter.SocialViewHolder socialViewHolder, SocialAdapter.OnSocialItemListener onSocialItemListener) {

        socialViewHolder.socialProfileType3.setVisibility(View.VISIBLE);


        ///////////////
        GlideApp.with(context)
                .load(source.profiles.get(0).imageProfile)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialProfileIcon);
        GlideApp.with(context)
                .load(source.photos.get(0))
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialViewHolder.socialProfileImg_1);
        GlideApp.with(context)
                .load(source.photos.get(1))
                .placeholder(R.drawable.default_photographer_profile)
                .centerCrop()
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialViewHolder.socialProfileImg_2);
        GlideApp.with(context)
                .load(source.photos.get(2))
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialViewHolder.socialProfileImg_3);
        GlideApp.with(context)
                .load(source.photos.get(3))
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialViewHolder.socialProfileImg_4);
        ///////////////////////// todo user profile should be called in separate Api

        socialViewHolder.socialProfileFullName.setText(source.profiles.get(0).fullName);
        socialViewHolder.socialProfileUserName.setText(source.profiles.get(0).userName);

        if (onSocialItemListener != null) {

            socialViewHolder.socialProfileContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSocialItemListener.onSocialProfileClick(source);
                }
            });
            socialViewHolder.followSocialProfile.setOnClickListener(v -> {
                onSocialItemListener.OnFollowSocialProfileClick(source);

            });
        }
    }


}
