package com.example.softmills.phlog.ui.welcome.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.welcome.view.WelcomeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomeScreenImpl implements WelcomePresenter {


    private String TAG = WelcomeScreenImpl.class.getSimpleName();
    private WelcomeView welcomeView;
    private Context context;

    public WelcomeScreenImpl(WelcomeView welcomeView, Context context) {
        this.welcomeView = welcomeView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getWelcomeSlidesImages() {

        BaseNetworkApi.getWelcomeSlidesImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(welcomeScreenResponse -> {
                    if (welcomeScreenResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        welcomeView.showWelcomeImageSlider(welcomeScreenResponse.initSlider);
                    } else {
                        ErrorUtils.Companion.setError(context, TAG, welcomeScreenResponse.toString());
                    }
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
