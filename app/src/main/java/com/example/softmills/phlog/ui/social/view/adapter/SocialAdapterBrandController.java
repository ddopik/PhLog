package com.example.softmills.phlog.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.brand.view.BrandInnerActivity;
import com.example.softmills.phlog.ui.social.model.SocialData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocialAdapterBrandController {

    private String TAG=SocialAdapterBrandController.class.getSimpleName();
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
                .load(socialData.brands.get(0).imageCover)
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
                    Intent intent = new Intent(context, BrandInnerActivity.class);
                    intent.putExtra(BrandInnerActivity.BRAND_ID, String.valueOf(socialData.brands.get(0).id));
                    context.startActivity(intent);
                }
            });
        }
        if (onSocialItemListener != null) {
            socialViewHolder.followBrandBtn.setOnClickListener(v -> {
             followSocialBrand(socialData.brands.get(0).id,onSocialItemListener);
            });
        }
    }

    @SuppressLint("CheckResult")
    private void followSocialBrand(int brandId,SocialAdapter.OnSocialItemListener onSocialItemListener) {
        BaseNetworkApi.followBrand(String.valueOf(brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    onSocialItemListener.onSocialBrandFollowed(brandId,true);
                 }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
    @SuppressLint("CheckResult")
     public void unFollowSocialBrand(int brandId,SocialAdapter.OnSocialItemListener onSocialItemListener) {
        BaseNetworkApi.unFollowBrand(String.valueOf(brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    onSocialItemListener.onSocialBrandFollowed(brandId,false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
