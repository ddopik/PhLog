package com.example.softmills.phlog.ui.earning.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.earning.view.EarningInnerView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EarningInnerPresenterImpl implements EarningInnerPresenter {

    private static final String TAG = EarningInnerPresenterImpl.class.getSimpleName();

    private EarningInnerView view;

    @Override
    public void setView(EarningInnerView view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getEarning(Context context, String earningId) {
        BaseNetworkApi.getEarningDetails(earningId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    view.setEarning(response.getData());
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
