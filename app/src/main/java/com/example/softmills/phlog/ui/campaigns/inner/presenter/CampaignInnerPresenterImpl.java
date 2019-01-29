package com.example.softmills.phlog.ui.campaigns.inner.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.campaigns.inner.ui.CampaignInnerActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerPresenterImpl implements CampaignInnerPresenter {

    private String TAG = CampaignInnerPresenterImpl.class.getSimpleName();

    private Context context;
    private CampaignInnerActivityView campaignInnerActivityView;


    public CampaignInnerPresenterImpl(Context context, CampaignInnerActivityView campaignInnerActivityView) {
        this.context = context;
        this.campaignInnerActivityView = campaignInnerActivityView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getCampaignDetails(String campaignID) {
        BaseNetworkApi
                .getCampaignDetails(PrefUtils.getUserToken(context), campaignID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignInnerResponse -> {
                            campaignInnerActivityView.viewCampaignTitle(campaignInnerResponse.campaign.titleEn);
                            campaignInnerActivityView.viewCampaignLeftDays(String.valueOf(campaignInnerResponse.campaign.daysLeft));
                            campaignInnerActivityView.viewCampaignHeaderImg(campaignInnerResponse.campaign.imageCover);
                            campaignInnerActivityView.viewCampaignHostedBy(campaignInnerResponse.campaign.business.fullName);
                            campaignInnerActivityView.viewCampaignMissionDescription(campaignInnerResponse.campaign.descrptionEn
                                    , campaignInnerResponse.campaign.status
                                    , campaignInnerResponse.campaign.numberImages);

                        },
                        throwable -> {
                            ErrorUtils.Companion.setError(context, TAG, throwable.toString());
                        });
    }


}
