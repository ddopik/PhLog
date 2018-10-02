package com.example.softmills.phlog.ui.campaigns.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.PermissionUtil;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.campaigns.CampaignFragmentView;

import io.reactivex.Scheduler;
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
    public void getAllCampaign() {
        campaignFragmentView.showAllCampaginProgress(true);
        BaseNetworkApi.getAllCampaign(PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignResponse -> {
                    campaignFragmentView.showAllCampaginProgress(false);
                    if (campaignResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        campaignFragmentView.viewAllCampaign(campaignResponse.data);
                    } else {
                        campaignFragmentView.showAllCampaginProgress(false);
                        Log.e(TAG, "getAllCampaign --->()" + campaignResponse.state);
                    }
                }, throwable -> {
                    campaignFragmentView.showAllCampaginProgress(false);
                    Log.e(TAG, "getAllCampaign --->()" + throwable.getMessage());
                });
    }
}
