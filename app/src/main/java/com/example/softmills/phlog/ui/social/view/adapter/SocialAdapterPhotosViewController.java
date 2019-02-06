package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.social.model.Source;

public class SocialAdapterPhotosViewController {


    private Context context;

    public SocialAdapterPhotosViewController(Context context) {
        this.context = context;
    }


    public void setPhotosViewType5(SocialAdapter.SocialViewHolder socialViewHolder, Source source, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        GlideApp.with(context)
                .load(source.photos.get(0))
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(socialViewHolder.socialItemSliderIcon);

        socialViewHolder.socialImageName.setText(source.title);

        socialViewHolder.socialImageSliderType5.setVisibility(View.VISIBLE);
        SocialImagesAdapter socialImagesAdapter = new SocialImagesAdapter(source);///todo img should be obj

        socialViewHolder.socialImgSlideRv.setAdapter(socialImagesAdapter);

        if (onSocialItemListener != null) {
            socialImagesAdapter.onSocialSliderImgClick = img -> {
                onSocialItemListener.onSocialSlideImageClicked(source);

            };
        }
    }


}
