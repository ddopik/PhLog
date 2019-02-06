package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.social.model.Source;

public class SocialAdapterAlbumViewController {
    Context context;

    public SocialAdapterAlbumViewController(Context context) {
        this.context = context;
    }


    public void setAlbumViewType4(SocialAdapter.SocialViewHolder socialViewHolder, Source source, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.socialAlbumType4.setVisibility(View.VISIBLE);

        GlideApp.with(context)
                .load(source.albums.get(0))
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialAlbumIconImg);

        GlideApp.with(context)
                .load(source.albums.get(0))
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialViewHolder.socialAlbum1);

        GlideApp.with(context)
                .load(source.albums.get(1))
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialViewHolder.socialAlbum2);

        GlideApp.with(context)
                .load(source.albums.get(2))
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialViewHolder.socialAlbum3);

        socialViewHolder.socialAlbumName.setText(source.title);
        socialViewHolder.socialAlbumPhotosNumber.setText(new StringBuilder().append(source.albums.size()).append(" ").append("photo").toString());
        if (onSocialItemListener != null) {
            socialViewHolder.followBrandBtn.setOnClickListener(v -> {
                onSocialItemListener.onSocialBrandFollowClicked(source);
            });
        }
    }
}
