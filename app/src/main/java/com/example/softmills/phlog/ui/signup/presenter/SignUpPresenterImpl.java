package com.example.softmills.phlog.ui.signup.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.signup.model.AllCountersRepose;
import com.example.softmills.phlog.ui.signup.view.SignUpView;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignUpPresenterImpl implements SignUpPresenter {

    private SignUpView signUpView;
    private Context context;
    private String TAG = SignUpPresenterImpl.class.getSimpleName();

    public SignUpPresenterImpl(Context context, SignUpView signUpView) {
        this.signUpView = signUpView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAllCounters() {
        BaseNetworkApi.getAllCounters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allCountersRepose -> {
                    AllCountersRepose countersRepose = allCountersRepose;
                    signUpView.showCounters(allCountersRepose.countries);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void signUpUser(HashMap<String, String> signUpData) {
        signUpView.setProgress(true);
        BaseNetworkApi.signUpUser(signUpData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(signUpResponse -> {
                    signUpView.setProgress(false);
                    PrefUtils.setLoginState(context, true);
                    PrefUtils.setUserToken(context, signUpResponse.token);
//                    PrefUtils.setUserID(context, signUpResponse.id);
                    signUpView.navigateToHome();
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    signUpView.setProgress(false);
                });
    }
}





