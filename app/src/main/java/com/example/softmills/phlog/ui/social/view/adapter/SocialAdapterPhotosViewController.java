package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity;
import com.example.softmills.phlog.ui.social.model.SocialData;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.ALL_ALBUM_IMAGES;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.SELECTED_IMG_ID;

public class SocialAdapterPhotosViewController {


    private Context context;

    public SocialAdapterPhotosViewController(Context context) {
        this.context = context;
    }


    public void setPhotosViewType5(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialData, SocialAdapter.OnSocialItemListener onSocialItemListener) {


        socialViewHolder.socialImageName.setText(socialData.title);

        socialViewHolder.socialImageSliderType5.setVisibility(View.VISIBLE);
        SocialImagesAdapter socialImagesAdapter = new SocialImagesAdapter(socialData.photos);

        socialViewHolder.socialImgSlideRv.setAdapter(socialImagesAdapter);


            socialImagesAdapter.onSocialSliderImgClick = img -> {
                Intent intent = new Intent(context, AllAlbumImgActivity.class);

                intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) socialData.photos);
                intent.putExtra(SELECTED_IMG_ID,socialData.photos.get(0).id);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);

            };

    }


}
