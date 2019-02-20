package com.example.softmills.phlog.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.brand.view.BrandInnerActivity;
import com.example.softmills.phlog.ui.social.model.SocialData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocialAdapterBrandController {

    private String TAG=SocialAdapterBrandController.class.getSimpleName();
    private Context context;
    private SocialAdapter socialAdapter;
    private List<SocialData> socialDataList;
    public SocialAdapterBrandController(Context context, SocialAdapter socialAdapter, List<SocialData> socialDataList) {
        this.context = context;
        this.socialAdapter = socialAdapter;
        this.socialDataList = socialDataList;

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
                if (!socialData.brands.get(0).isFollow){
                    followSocialBrand(socialData.brands.get(0).id,socialData);
                }else{
                    unFollowSocialBrand(socialData.brands.get(0).id,socialData);
                }

            });
        }
    }

    @SuppressLint("CheckResult")
    private void followSocialBrand(int brandId,SocialData socialData) {
        BaseNetworkApi.followBrand(String.valueOf(brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {

                    socialData.brands.get(0).isFollow = true;
                    getBrandIndex(brandId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(index -> {
                                if (index > 0) {
                                    socialDataList.set(index, socialData);
                                    socialAdapter.notifyDataSetChanged();
                                }

                            }, throwable -> {
                                ErrorUtils.Companion.setError(context, TAG, throwable);
                            });
//                    onSocialItemListener.onSocialBrandFollowed(brandId,true);
                 }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
    @SuppressLint("CheckResult")
     public void unFollowSocialBrand(int brandId,SocialData socialData) {
        BaseNetworkApi.unFollowBrand(String.valueOf(brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    socialData.brands.get(0).isFollow = false;
                    getBrandIndex(brandId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(index -> {
                                if (index > 0) {
                                    socialDataList.set(index, socialData);
                                    socialAdapter.notifyDataSetChanged();
                                }

                            }, throwable -> {
                                ErrorUtils.Companion.setError(context, TAG, throwable);
                            });
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }


    private Observable<Integer> getBrandIndex(int businessId) {
        return Observable.fromCallable(() -> {

            for (int i = 0; i < socialDataList.size(); i++) {
                if (socialDataList.get(i).profiles != null && socialDataList.get(i).profiles.size() > 0)
                    for (Business business : socialDataList.get(i).brands) {
                        if (business.id.equals(businessId)) {
                            return i;
                        }
                    }
            }
            return -1;
        });

    }
}
