package com.example.softmills.phlog.ui.campaigns.inner.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.androidnetworking.error.ANError;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.commonmodel.BaseErrorResponse;
import com.example.softmills.phlog.base.commonmodel.ErrorMessageResponse;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.campaigns.inner.ui.CampaignInnerActivityView;
import com.google.gson.Gson;

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
                .subscribe(response -> {
                            if (response != null && response.campaign != null)
                                campaignInnerActivityView.setCampaign(response.campaign);
                        }, throwable -> {
                    if (throwable instanceof ANError) {
                        ANError error = (ANError) throwable;
                        if (error.getErrorCode() == BaseNetworkApi.STATUS_BAD_REQUEST) {
                            ErrorMessageResponse errorMessageResponse = new Gson().fromJson(error.getErrorBody(), ErrorMessageResponse.class);
                            for (BaseErrorResponse e : errorMessageResponse.errors) {
                                if (e.code.equals(BaseNetworkApi.ERROR_NOT_FOUND)) {
                                    campaignInnerActivityView.showNotFoundDialog();
                                    break;
                                }
                            }
                        }
                    }
                            ErrorUtils.Companion.setError(context, TAG, throwable);
                        });
    }


}
