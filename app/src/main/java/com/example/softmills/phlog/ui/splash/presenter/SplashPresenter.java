package com.example.softmills.phlog.ui.splash.presenter;

import android.content.Context;

import com.example.softmills.phlog.ui.splash.model.CheckVersionData;
import com.example.softmills.phlog.ui.splash.view.SplashView;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface SplashPresenter {
    void setView(SplashView view);

    Observable<Boolean> sendFirebaseToken(Context context);

    Observable<CheckVersionData> checkAppVersion();
}
