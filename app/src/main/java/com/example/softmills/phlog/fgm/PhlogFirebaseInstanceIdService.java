package com.example.softmills.phlog.fgm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.commonmodel.Device;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhlogFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
//        Log.e("Firebase token", token);
        PrefUtils.saveFirebaseToken(getApplicationContext(), token);
        if (PrefUtils.isLoginProvided(getApplicationContext()))
            BaseNetworkApi.updateFirebaseToken(new Device(Utilities.getDeviceName(), true, PrefUtils.getFirebaseToken(getApplicationContext())), PrefUtils.getUserToken(getApplicationContext()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        PrefUtils.setFirebaseTokenSentToServer(getApplicationContext(), true);
                    }, throwable -> {
                        PrefUtils.setFirebaseTokenSentToServer(getApplicationContext(), false);
                    });
    }
}
