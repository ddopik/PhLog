package com.example.softmills.phlog.ui.welcome.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.welcome.view.WelcomeView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomeScreenImpl implements WelcomePresenter {


    private String TAG = WelcomeScreenImpl.class.getSimpleName();
    private WelcomeView welcomeView;

    public WelcomeScreenImpl(WelcomeView welcomeView) {
        this.welcomeView = welcomeView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getWelcomeSlidesImages() {

        BaseNetworkApi.getWelcomeSlidesImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(welcomeScreenResponse -> {
//                    List<String> imagesList = new ArrayList<String>();

//                    for (int i = 0; i < welcomeScreenResponse.initSlider.size(); i++) {
//                        imagesList.add(welcomeScreenResponse.initSlider.get(i).image);
//                    }
                    welcomeView.showWelcomeImageSlider(welcomeScreenResponse.initSlider);
                }, throwable -> {
                    welcomeView.navigateToHome();
                    Log.e(TAG, "getWelcomeSlidesImages() -----> Error :" + throwable.getMessage());
                });

    }
}
