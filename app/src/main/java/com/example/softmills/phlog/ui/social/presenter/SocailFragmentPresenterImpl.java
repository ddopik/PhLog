package com.example.softmills.phlog.ui.social.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.social.view.SocialFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocailFragmentPresenterImpl implements SocialFragmentPresenter {

    private static String TAG = SocailFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    private SocialFragmentView socialFragmentView;

    public SocailFragmentPresenterImpl(Context context, SocialFragmentView socialFragmentView) {
        this.context = context;
        this.socialFragmentView = socialFragmentView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getSocialData() {
        socialFragmentView.viewSocialDataProgress(true);
        BaseNetworkApi.getSocialData(PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialResponse -> {
                    socialFragmentView.viewSocialData(socialResponse.data);
                    socialFragmentView.viewSocialDataProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    socialFragmentView.viewSocialDataProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followUser(String userId) {
        BaseNetworkApi.followUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    socialFragmentView.showMessage(context.getResources().getString(R.string.following_state) + " " + followUserResponse.data);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void followSocialCampaign(String id) {
        BaseNetworkApi.followCampaign(PrefUtils.getUserToken(context), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followCampaignResponse -> {
                    socialFragmentView.showMessage(context.getResources().getString(R.string.campaign_followed));
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void followSocialBrand(String id) {
        BaseNetworkApi.followBrand(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    socialFragmentView.showMessage(context.getResources().getString(R.string.brand_followed));
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void unFollowSocialBrand(String id) {
        BaseNetworkApi.unFollowBrand(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    socialFragmentView.showMessage(context.getResources().getString(R.string.brand_followed));
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
