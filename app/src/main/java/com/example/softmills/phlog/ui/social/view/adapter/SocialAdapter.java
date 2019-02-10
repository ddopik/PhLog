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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.social.model.SocialData;

import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.ALBUM_DISPLAY_TYPE_4;
import static com.example.softmills.phlog.Utiltes.Constants.BRAND_DISPLAY_TYPE_1;
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
    public OnSocialItemListener onSocialItemListener;
    private SocialAdapterProfileViewController socialAdapterProfileViewController;
    private SocialAdapterCampaignViewController socialAdapterCampaignViewController;
    private SocialAdapterAlbumViewController socialAdapterAlbumViewController;
    private SocialAdapterPhotosViewController socialAdapterPhotosViewController;
    private SocialAdapterBrandController socialAdapterBrandController;


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

        if (socialDataList.size() >0)
         switch (socialDataList.get(i).storyId) {

            case ENTITY_PROFILE: {
                socialAdapterProfileViewController = new SocialAdapterProfileViewController(context);
                bindProfileEntity(socialDataList.get(i), socialViewHolder);
                break;
            }
            case ENTITY_CAMPAIGN: {
                socialAdapterCampaignViewController = new SocialAdapterCampaignViewController(context);
                bindCampaignEntity(socialDataList.get(i), socialViewHolder);
                break;
            }
            case ENTITY_ALBUM: {
                socialAdapterAlbumViewController = new SocialAdapterAlbumViewController(context);
                bindAlbumEntity(socialDataList.get(i), socialViewHolder);
                break;
            }
            case ENTITY_IMAGE: {
                socialAdapterPhotosViewController = new SocialAdapterPhotosViewController(context);
                bindImageSlider(socialDataList.get(i), socialViewHolder);
                break;
            }
            case ENTITY_BRAND: {
                socialAdapterBrandController = new SocialAdapterBrandController(context);
                bindBrandEntity(socialDataList.get(i), socialViewHolder);
                break;
            }


        }


    }

    @Override
    public int getItemCount() {
        return socialDataList.size();
    }

    class SocialViewHolder extends RecyclerView.ViewHolder {

        FrameLayout socialProfileType3, socialImageSliderType5, socialCampaignType1, socialBrandType1,socialAlbumType4;

        ImageView socialProfileIcon, socialProfileImg_1, socialProfileImg_2, socialProfileImg_3, socialProfileImg_4;
        TextView socialProfileFullName, socialProfileUserName;
        Button followSocialProfile;
        LinearLayout socialProfileContainer;
        /////
        CustomRecyclerView socialImgSlideRv;
        ImageView socialItemSliderIcon;
        TextView socialImageName;
        /////
        LinearLayout socailCampaignConatainer;
        ImageView socialCampaignIcon, socialCampaignImg;
        TextView socialCampaignName, socialCampaignTitle, socialCampaignDayLeft;
        Button socialJoinCampaignBtn;
        /////
        ImageView socialBrandIconImg, socialBrandImg;
        TextView socialBrandName, socialBrandFollowing;
        Button followBrandBtn;
        /////
        ImageView socialAlbumIconImg,socialAlbum1,socialAlbum2,socialAlbum3;
        TextView socialAlbumName,socialAlbumPhotosNumber;


        SocialViewHolder(View view) {
            super(view);


            socialProfileType3 = view.findViewById(R.id.social_profile_type_3);
            socialImageSliderType5 = view.findViewById(R.id.images_slider_type_5);
            socialCampaignType1 = view.findViewById(R.id.social_campaign_type_1);
            socialBrandType1 = view.findViewById(R.id.social_brand_type_1);
            socialAlbumType4 = view.findViewById(R.id.social_album_type_4);
            /////profileItemView type_1
            socialProfileIcon = view.findViewById(R.id.social_profile_icon_img);
            socialProfileFullName = view.findViewById(R.id.social_profile_full_name);
            socialProfileUserName = view.findViewById(R.id.social_profile_user_name);
            followSocialProfile = view.findViewById(R.id.follow_social_profile);
            socialProfileImg_1 = view.findViewById(R.id.social_profile_img_1);
            socialProfileImg_2 = view.findViewById(R.id.social_profile_img_2);
            socialProfileImg_3 = view.findViewById(R.id.social_profile_img_3);
            socialProfileImg_4 = view.findViewById(R.id.social_profile_img_4);
            socialProfileContainer = view.findViewById(R.id.social_profile_container);
            /////ImageSlider type_1
            socialImgSlideRv = view.findViewById(R.id.social_img_slider_rv);
            socialItemSliderIcon = view.findViewById(R.id.social_icon_img);
            socialImageName = view.findViewById(R.id.social_image_name);
            /////CampaignItemView
            socailCampaignConatainer = view.findViewById(R.id.social_campaign_container);
            socialCampaignIcon = view.findViewById(R.id.social_campaign_icon);
            socialCampaignName = view.findViewById(R.id.social_campaign_name);
            socialCampaignImg = view.findViewById(R.id.social_campaign_img);
            socialCampaignTitle = view.findViewById(R.id.social_campaign_title);
            socialCampaignDayLeft = view.findViewById(R.id.social_campaign_day_left);
            socialJoinCampaignBtn = view.findViewById(R.id.social_join_campaign_btn);
            /////BrandItemView
            socialBrandIconImg = view.findViewById(R.id.social_brand_icon_img);
            socialBrandImg = view.findViewById(R.id.social_brand_img);
            socialBrandName = view.findViewById(R.id.social_brand_name);
            socialBrandFollowing = view.findViewById(R.id.social_brand_following);
            followBrandBtn = view.findViewById(R.id.follow_brand);
            /////BrandAlbumView
            socialAlbumIconImg=view.findViewById(R.id.social_album_icon_img);
            socialAlbumName=view.findViewById(R.id.social_album_name);
            socialAlbumPhotosNumber=view.findViewById(R.id.social_album_photos_number);
            socialAlbum1=view.findViewById(R.id.social_album_img_1);
            socialAlbum2=view.findViewById(R.id.social_album_img_2);
            socialAlbum3=view.findViewById(R.id.social_album_img_3);






        }
    }

    public interface OnSocialItemListener {


        void onSocialCampaignClicked(SocialData socialData);

        void onSocialFollowCampaignClicked(SocialData socialData);

        void onSocialSlideImageClicked(SocialData socialData);

        void onSocialBrandClicked(SocialData socialData);

        void onSocialBrandFollowClicked(SocialData socialData);
    }

    private void bindProfileEntity(SocialData socialData, SocialViewHolder socialViewHolder) {

        switch (socialData.displayType) {

            case PROFILE_DISPLAY_TYPE_3:
                SocialAdapterProfileViewController.OnSocialAdapterProfileViewListener onSocialAdapterProfileViewListener= state -> {
                    socialData.profiles.get(0).isFollow=state;
                    socialData.profiles.set(0,socialData.profiles.get(0));
                    notifyDataSetChanged();
                };
                socialAdapterProfileViewController.setProfileType3(socialData.profiles.get(0), socialViewHolder,onSocialAdapterProfileViewListener );
                break;
        }


    }

    private void bindImageSlider(SocialData socialData, SocialViewHolder socialViewHolder) {
        switch (socialData.displayType) {

            case IMGS_DISPLAY_TYPE_5: {
                socialAdapterPhotosViewController.setPhotosViewType5(socialViewHolder, socialData, onSocialItemListener);
                break;
            }
        }
    }

    private void bindCampaignEntity(SocialData socialData, SocialViewHolder socialViewHolder) {
        switch (socialData.displayType) {

            case CAMPAIGN_DISPLAY_TYPE_1:
                socialAdapterCampaignViewController.setCampaignType_1(socialViewHolder, socialData, onSocialItemListener);
                break;

        }

    }

    private void bindBrandEntity(SocialData socialData, SocialViewHolder socialViewHolder) {
        switch (socialData.displayType) {

            case BRAND_DISPLAY_TYPE_1: {
                socialAdapterBrandController.setBrandViewType_1(socialViewHolder, socialData, onSocialItemListener);
                break;
            }
        }
    }

    private void bindAlbumEntity(SocialData socialData, SocialViewHolder socialViewHolder) {
        switch (socialData.displayType) {

            case ALBUM_DISPLAY_TYPE_4: {
//                socialAdapterAlbumViewController.setAlbumViewType4(socialViewHolder, socialData, onSocialItemListener);
                break;
            }
        }
    }

}
