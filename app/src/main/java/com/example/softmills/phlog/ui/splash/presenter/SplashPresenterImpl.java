package com.example.softmills.phlog.ui.splash.presenter;

import android.content.Context;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.commonmodel.Device;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.splash.view.SplashView;

import io.reactivex.Observable;

public class SplashPresenterImpl implements SplashPresenter {
    private SplashView view;

    @Override
    public void setView(SplashView view) {
        this.view = view;
    }

    @Override
    public Observable<Boolean> sendFirebaseToken(Context context) {
        return BaseNetworkApi.updateFirebaseToken(new Device(Utilities.getDeviceName(), true, PrefUtils.getFirebaseToken(context)))
                .map(response -> {
                    if (response != null) {
                        PrefUtils.setFirebaseTokenSentToServer(context, true);
                        return true;
                    } else {
                        PrefUtils.setFirebaseTokenSentToServer(context, false);
                        return false;
                    }
                });
    }
}
