package com.example.softmills.phlog.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.social.model.SocialData;

public class SocialAdapterAlbumViewController {
    Context context;

    public SocialAdapterAlbumViewController(Context context) {
        this.context = context;
    }


    @SuppressLint("CheckResult")
    public void setAlbumViewType4(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialAlbumData, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.socialAlbumType4.setVisibility(View.VISIBLE);

        if (socialAlbumData.photos.size() >= 3) {

            GlideApp.with(context)
                    .load(socialAlbumData.photos.get(0))
                    .placeholder(R.drawable.default_user_pic)
                    .error(R.drawable.default_user_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(socialViewHolder.socialAlbumIconImg);


            GlideApp.with(context)
                    .load(socialAlbumData.photos.get(0))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum1);


            GlideApp.with(context)
                    .load(socialAlbumData.photos.get(1))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum2);


            GlideApp.with(context)
                    .load(socialAlbumData.photos.get(2))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum3);
        } else {


//            handle if PhotoGrapher has photos less than 3 or has no photos
            GlideApp.with(context)
                    .load(socialAlbumData.albums.get(0).url)
                    .error(R.drawable.default_photographer_profile)
                    .apply(new RequestOptions().centerCrop())
                    .into(socialViewHolder.socialDefaultAlbumImg);




        }


        socialViewHolder.socialAlbumName.setText(socialAlbumData.title);
        if (socialAlbumData.albums != null) {
            socialViewHolder.socialAlbumPhotosNumber.setText(new StringBuilder().append(socialAlbumData.albums.size()).append(" ").append("photo").toString());
        } else {
            socialViewHolder.socialAlbumPhotosNumber.setText(new StringBuilder().append("0").append(" ").append("photo").toString());
        }

    }


}

