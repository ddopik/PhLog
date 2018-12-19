package com.example.softmills.phlog.ui.campaigns.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.campaigns.CampaignFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public class CampaignPresenterImpl implements CampaignPresenter {

    private static final String TAG = CampaignPresenterImpl.class.getSimpleName();
    private CampaignFragmentView campaignFragmentView;
    private Context context;

    public CampaignPresenterImpl(Context context, CampaignFragmentView campaignFragmentView) {
        this.campaignFragmentView = campaignFragmentView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAllCampaign(int page) {
        campaignFragmentView.showAllCampaignProgress(true);
        BaseNetworkApi.getAllRunningCampaign(PrefUtils.getUserToken(context),page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignResponse -> {
                    campaignFragmentView.showAllCampaignProgress(false);
                        campaignFragmentView.viewAllCampaign(campaignResponse.data.data);
                        campaignFragmentView.showAllCampaignProgress(false);
                }, throwable -> {
                    campaignFragmentView.showAllCampaignProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followCampaign(String campaignID) {
        BaseNetworkApi.followCampaign(PrefUtils.getUserToken(context), campaignID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followCampaignResponse -> {
                    campaignFragmentView.showMessage(context.getResources().getString(R.string.campaign_followed));
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
