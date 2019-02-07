package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.social.model.SocialData;

public class SocialAdapterAlbumViewController {
    Context context;

    public SocialAdapterAlbumViewController(Context context) {
        this.context = context;
    }


    public void setAlbumViewType4(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialAlbumData, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.socialAlbumType4.setVisibility(View.VISIBLE);

        if (socialAlbumData.photos.size() >0) {
            GlideApp.with(context)
                    .load(socialAlbumData.photos.get(0))
                    .placeholder(R.drawable.default_user_pic)
                    .error(R.drawable.default_user_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(socialViewHolder.socialAlbumIconImg);
        }else {
            socialViewHolder.socialAlbumIconImg.setVisibility(View.INVISIBLE);
        }


        if (socialAlbumData.photos.size() >0) {
            GlideApp.with(context)
                    .load(socialAlbumData.photos.get(0))
                    .centerCrop()
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum1);
        }else {
            socialViewHolder.socialAlbum1.setVisibility(View.INVISIBLE);
        }

        if (socialAlbumData.photos.size() >=1) {
            GlideApp.with(context)
                    .load(socialAlbumData.photos.get(1))
                    .centerCrop()
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum2);
        }else {
            socialViewHolder.socialAlbum2.setVisibility(View.INVISIBLE);
        }


        if (socialAlbumData.photos.size() >=2) {
            GlideApp.with(context)
                    .load(socialAlbumData.photos.get(2))
                    .centerCrop()
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum3);
        }{
            socialViewHolder.socialAlbum3.setVisibility(View.INVISIBLE);
        }

        socialViewHolder.socialAlbumName.setText(socialAlbumData.title);
        socialViewHolder.socialAlbumPhotosNumber.setText(new StringBuilder().append(socialAlbumData.albums.size()).append(" ").append("photo").toString());
        if (onSocialItemListener != null) {
            socialViewHolder.followBrandBtn.setOnClickListener(v -> {
                onSocialItemListener.onSocialBrandFollowClicked(socialAlbumData);
            });
        }
    }
}
