package com.example.softmills.phlog.ui.signup.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.signup.view.SignUpView;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignUpPresenterImp implements SignUpPresenter {

    private SignUpView signUpView;
    private String TAG = SignUpPresenterImp.class.getSimpleName();

    public SignUpPresenterImp(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void getAllCounters() {
        requestAllCounters();
    }

    @SuppressLint("CheckResult")
    @Override
    public void signUpUser(HashMap<String, String> signUpData) {
        BaseNetworkApi.signUpUser(signUpData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(signUpResponse -> {
                    if (signUpResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        signUpView.showMessage(signUpResponse.token);
                    } else {
                        signUpView.showMessage(signUpResponse.email.get(0));
                    }
                }, throwable -> {
                    signUpView.showMessage(throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    private void requestAllCounters() {
        BaseNetworkApi.getAllCounters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allCountersRepose -> {
                    signUpView.showCounters(allCountersRepose.countries);
                }, throwable -> {
                    Log.e(TAG, "requestAllCounters() ---->Errot --->" + throwable.getMessage());
                });
    }


}
