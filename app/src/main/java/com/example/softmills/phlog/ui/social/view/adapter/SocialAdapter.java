package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.social.model.Entite;
import com.example.softmills.phlog.ui.social.model.SocialData;

import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.CAMPAIGN_DISPLAY_TYPE_1;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_ALBUM;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_BRAND;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_CAMPAIGN;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_IMAGE;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_PROFILE;
import static com.example.softmills.phlog.Utiltes.Constants.IMGS_DISPLAY_TYPE_5;
import static com.example.softmills.phlog.Utiltes.Constants.PROFILE_DISPLAY_TYPE_3;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.SocialViewHolder> {

    private Context context;
    private List<SocialData> socialDataList;

    @SuppressWarnings("WeakerAccess")
    public  OnSocialProfileListener onSocialProfileListener;


    public SocialAdapter(List<SocialData> socialDataList) {
        this.socialDataList = socialDataList;
    }

    @NonNull
    @Override
    public SocialViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new SocialViewHolder(layoutInflater.inflate(R.layout.view_holder_main_social_item, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull SocialViewHolder socialViewHolder, int i) {

        switch (socialDataList.get(i).entityId) {

            case ENTITY_PROFILE: {
                bindProfileEntity(socialDataList.get(i).entites.get(0), socialViewHolder);
                break;
            }
            case ENTITY_CAMPAIGN: {
                bindCampaignEntity(socialDataList.get(i).entites.get(0), socialViewHolder);
                break;
            }
            case ENTITY_ALBUM: {
                break;
            }
            case ENTITY_IMAGE: {
                bindImageSlider(socialDataList.get(i).entites.get(0),socialDataList.get(i).title, socialViewHolder);
                break;
            }
            case ENTITY_BRAND: {
                break;
            }


        }


    }

    @Override
    public int getItemCount() {
        return socialDataList.size();
    }

    class SocialViewHolder extends RecyclerView.ViewHolder {

        FrameLayout socialProfileType3, socialImageSliderType5, socialCampaignType1;

        ImageView socialProfileIcon, socialProfileImg_1, socialProfileImg_2, socialProfileImg_3, socialProfileImg_4;
        TextView socialProfileFullName, socialProfileUserName;
        Button followSocialProfile;
        /////
        CustomRecyclerView socialImgSlideRv;
        ImageView socialImfSliderIcon;
        TextView socialImageName;
        /////
        ImageView socialCampaignIcon, socialCampaignImg;
        TextView socialCampaignName, socialCampaignTitle, socialCampaignDayLeft;
        Button socialJoinCampaignBtn;


        SocialViewHolder(View view) {
            super(view);


            socialProfileType3 = view.findViewById(R.id.social_profile_type_3);
            socialImageSliderType5 = view.findViewById(R.id.images_slider_type_5);
            socialCampaignType1 = view.findViewById(R.id.social_campaign_type_1);
            ////profileItemView type_1
            socialProfileIcon = view.findViewById(R.id.social_profile_icon_img);
            socialProfileFullName = view.findViewById(R.id.social_profile_full_name);
            socialProfileUserName = view.findViewById(R.id.social_profile_user_name);
            followSocialProfile = view.findViewById(R.id.follow_social_profile);
            socialProfileImg_1 = view.findViewById(R.id.social_profile_img_1);
            socialProfileImg_2 = view.findViewById(R.id.social_profile_img_2);
            socialProfileImg_3 = view.findViewById(R.id.social_profile_img_3);
            socialProfileImg_4 = view.findViewById(R.id.social_profile_img_4);


            ////ImageSlider type_1
            socialImgSlideRv = view.findViewById(R.id.social_img_slider_rv);
            socialImfSliderIcon =view.findViewById(R.id.social_icon_img);
            socialImageName = view.findViewById(R.id.social_image_name);


            ///CampainItemView
            socialCampaignIcon = view.findViewById(R.id.social_campaign_icon);
            socialCampaignName = view.findViewById(R.id.social_campaign_name);
            socialCampaignImg = view.findViewById(R.id.social_campaign_img);
            socialCampaignTitle = view.findViewById(R.id.social_campaign_title);
            socialCampaignDayLeft = view.findViewById(R.id.social_campaign_day_left);
            socialJoinCampaignBtn = view.findViewById(R.id.social_join_campaign_btn);

        }
    }

    public interface OnSocialProfileListener {
        void OnSoilProfileClick(Entite entite);
    }

    private void bindProfileEntity(Entite entite, SocialViewHolder socialViewHolder) {

        switch (entite.displayType) {

            case PROFILE_DISPLAY_TYPE_3:
                socialViewHolder.socialProfileType3.setVisibility(View.VISIBLE);
                GlideApp.with(context)
                        .load(entite.thumbnail)
                        .placeholder(R.drawable.default_user_pic)
                        .error(R.drawable.default_user_pic)
                        .apply(RequestOptions.circleCropTransform())
                        .into(socialViewHolder.socialProfileIcon);
                GlideApp.with(context)
                        .load(entite.imgs.get(0))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .apply(new RequestOptions().centerCrop())
                        .into(socialViewHolder.socialProfileImg_1);
                GlideApp.with(context)
                        .load(entite.imgs.get(1))
                        .placeholder(R.drawable.default_photographer_profile)
                        .centerCrop()
                        .error(R.drawable.default_photographer_profile)
                        .apply(new RequestOptions().centerCrop())
                        .into(socialViewHolder.socialProfileImg_2);
                GlideApp.with(context)
                        .load(entite.imgs.get(2))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .apply(new RequestOptions().centerCrop())
                        .into(socialViewHolder.socialProfileImg_3);
                GlideApp.with(context)
                        .load(entite.imgs.get(3))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .apply(new RequestOptions().centerCrop())
                        .into(socialViewHolder.socialProfileImg_4);

                socialViewHolder.socialProfileFullName.setText(entite.fullName);
                socialViewHolder.socialProfileUserName.setText(entite.userName);

                if (onSocialProfileListener != null)
                    socialViewHolder.followSocialProfile.setOnClickListener(v -> {
                        onSocialProfileListener.OnSoilProfileClick(entite);
                    });
        }
    }

    private void bindImageSlider(Entite entite, String title,SocialViewHolder socialViewHolder) {
        switch (entite.displayType) {

            case IMGS_DISPLAY_TYPE_5: {
                GlideApp.with(context)
                        .load(entite.imgs.get(0))
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .into(socialViewHolder.socialImfSliderIcon);

                socialViewHolder.socialImageName.setText(title);

                socialViewHolder.socialImageSliderType5.setVisibility(View.VISIBLE);
                SocialImagesAdapter socialImagesAdapter = new SocialImagesAdapter(entite);
                socialViewHolder.socialImgSlideRv.setAdapter(socialImagesAdapter);
            }

        }
    }

    private void bindCampaignEntity(Entite entite, SocialViewHolder socialViewHolder) {
        switch (entite.displayType) {

            case CAMPAIGN_DISPLAY_TYPE_1:
                socialViewHolder.socialCampaignType1.setVisibility(View.VISIBLE);


                GlideApp.with(context)
                        .load(entite.thumbnail)
                        .placeholder(R.drawable.default_user_pic)
                        .error(R.drawable.default_user_pic)
                        .apply(RequestOptions.circleCropTransform())
                        .into(socialViewHolder.socialCampaignIcon);

                GlideApp.with(context)
                        .load(entite.imgs.get(0))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .into(socialViewHolder.socialCampaignImg);

                socialViewHolder.socialCampaignTitle.setText(entite.titleEn);
                socialViewHolder.socialCampaignDayLeft.setText("day left here");
                socialViewHolder.socialCampaignName.setText(entite.nameEn);
                socialViewHolder.socialJoinCampaignBtn.setOnClickListener(v -> {

                });
        }

    }
}
