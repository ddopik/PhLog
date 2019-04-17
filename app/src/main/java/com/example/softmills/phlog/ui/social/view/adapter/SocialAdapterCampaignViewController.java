package com.example.softmills.phlog.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.campaigns.inner.ui.CampaignInnerActivity;
import com.example.softmills.phlog.ui.social.model.SocialData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocialAdapterCampaignViewController {

    private Context context;
    private String TAG = SocialAdapterCampaignViewController.class.getSimpleName();
    private List<SocialData> socialDataList;
    private SocialAdapter socialAdapter;

    public SocialAdapterCampaignViewController(Context context, SocialAdapter socialAdapter, List<SocialData> socialDataList) {
        this.context = context;
        this.socialAdapter = socialAdapter;
        this.socialDataList = socialDataList;
    }


    @SuppressLint("CheckResult")
    public void setCampaignType_1(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialData, SocialAdapter.OnSocialItemListener onSocialItemListener) {

        socialViewHolder.socialCampaignType1.setVisibility(View.VISIBLE);
        socialViewHolder.storyTitle.setText(socialData.title);
        socialViewHolder.socialCampaignDayLeft.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(context, R.drawable.ic_time_white), null);
        socialViewHolder.campaignPrize.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(context, R.drawable.ic_prize_white), null);
        GlideApp.with(context)
//                .load(socialData.campaigns.get(0).business.thumbnail)
                .load(socialData.campaigns.get(0).imageCover)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialCampaignIcon);

        GlideApp.with(context)
                .load(socialData.campaigns.get(0).imageCover)
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialViewHolder.socialCampaignImg);

        socialViewHolder.socialCampaignTitle.setText(socialData.title);
        socialViewHolder.socialCampaignDayLeft.setText("days_left here");
        socialViewHolder.socialCampaignName.setText(socialData.campaigns.get(0).titleEn);

        /**
         * join disabled for both state in social screen
         **/
        socialViewHolder.socialJoinCampaignBtn.setVisibility(View.GONE);

//        if (socialData.campaigns.get(0).isJoined) {
//            socialViewHolder.socialJoinCampaignBtn.setVisibility(View.GONE);
//        } else {
//            socialViewHolder.socialJoinCampaignBtn.setVisibility(View.VISIBLE);
//        }

        if (onSocialItemListener != null) {
            socialViewHolder.socialJoinCampaignBtn.setOnClickListener(v -> {
                joinCampaign(socialData.campaigns.get(0).id,socialData);
            });


            GlideApp.with(context)
                    .load(context.getResources().getDrawable(R.drawable.giphy))

                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // log exception

                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            socialViewHolder.socialJoinCampaignBtn.setBackground(context.getResources().getDrawable(R.drawable.giphy));
                            return false;
                        }
                    });

            socialViewHolder.socialCampaignContainer.setOnClickListener(v -> {
                Intent intent = new Intent(context, CampaignInnerActivity.class);
                intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, String.valueOf(socialData.campaigns.get(0).id));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            });

            if (socialData.campaigns.get(0).prize != null)
                socialViewHolder.campaignPrize.setText(socialData.campaigns.get(0).prize);
        }
    }

    @SuppressLint("CheckResult")
    public void joinCampaign(int campaignId,SocialData socialData) {


        BaseNetworkApi.followCampaign(String.valueOf(campaignId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followCampaignResponse -> {
                    getCampaignIndex(campaignId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(index -> {


                                /**
                                 * join disabled for both state in social screen
                                **/
//                                if (index >0){
//                                    socialData.campaigns.get(0).isJoined =true;
//                                    socialDataList.set(index, socialData);
//                                    socialAdapter.notifyDataSetChanged();
//                                }
//                                socialAdapter.notifyDataSetChanged();

                            }, throwable -> {
                                ErrorUtils.Companion.setError(context, TAG, throwable);
                            });


                 }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    private Observable<Integer> getCampaignIndex(int campaign) {
        return Observable.fromCallable(() -> {

            for (int i = 0; i < socialDataList.size(); i++) {
                if (socialDataList.get(i).profiles != null && socialDataList.get(i).profiles.size() > 0)
                    for (Campaign mCampaign : socialDataList.get(i).campaigns) {
                        if (mCampaign.id.equals(campaign)) {
                            return i;
                        }
                    }
            }
            return -1;
        });

    }
}
