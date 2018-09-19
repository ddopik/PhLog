package com.example.softmills.phlog.ui.signup.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.signup.view.SignUpView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignUpPresenterImp implements SignUpPresenter {

    private SignUpView signUpView;
    private String TAG =SignUpPresenterImp.class.getSimpleName();

    public SignUpPresenterImp(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void getAllCounters() {
        requestAllCountres();
    }

    @Override
    public void signUpUser() {

    }

    @SuppressLint("CheckResult")
    private void requestAllCountres() {
        BaseNetworkApi.getAllCounters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allCountersRepose -> {
                    signUpView.showCounters(allCountersRepose.countries);
                }, throwable -> {
                    Log.e(TAG, "requestAllCountres() ---->Errot --->" + throwable.getMessage());
                });
    }


}
