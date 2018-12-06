package com.example.softmills.phlog.ui.signup.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
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

    public SignUpPresenterImpl(Context context,SignUpView signUpView) {
        this.signUpView = signUpView;
        this.context=context;
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
                    Log.e(TAG,"signUpUser() --->"+signUpResponse.toString());
                    if (signUpResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        signUpView.showMessage(signUpResponse.token);
                        signUpView.showMessage("What should we do now");
                    } else if (signUpResponse.state.equals(BaseNetworkApi.STATUS_IN_VALID_RESPONSE)){
                        signUpView.showMessage(signUpResponse.message);
                    }
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    private void requestAllCounters() {
        BaseNetworkApi.getAllCounters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allCountersRepose -> {
                    AllCountersRepose countersRepose=allCountersRepose;
                    signUpView.showCounters(allCountersRepose.countries);
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable);
                });
    }


}

