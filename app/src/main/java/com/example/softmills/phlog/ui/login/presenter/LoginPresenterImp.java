package com.example.softmills.phlog.ui.login.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.login.model.LoginResponse;
import com.example.softmills.phlog.ui.login.view.LoginView;
import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenterImp implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
    }

    private static final String TAG = LoginPresenterImp.class.getSimpleName();

    @SuppressLint("CheckResult")
    @Override
    public void signInNormal(HashMap<String, String> loginData) {
        BaseNetworkApi.LoginUserNormal(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    String m = loginResponse.loginData.email;
                    loginView.showMessage(loginResponse.loginData.fullName);
                }, throwable -> {
                    loginView.showMessage(throwable.getMessage());
                });
    }

    @Override
    public void signInWithGoogle() {
//        GoogleSignInOptions.SCOPE_EMAIL
        List<String> scopes = new ArrayList<String>();
        scopes.add("email");
        scopes.add("profile");
        scopes.add("openid");

        com.jaychang.sa.google.SimpleAuth.connectGoogle(scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {

                Log.e(TAG, "userId:" + socialUser.toString());
//                Log.d(TAG, "email:" + socialUser.email);
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "signInWithGoogle()--->" + error.getMessage());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "Canceled");
            }
        });

    }

    @Override
    public void signInWithFaceBook() {
        List<String> scopes = new ArrayList<>();
        com.jaychang.sa.facebook.SimpleAuth.connectFacebook(scopes, new AuthCallback() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(SocialUser socialUser) {

                HashMap<String, String> parameter = new HashMap<String, String>();
                parameter.put("userId", socialUser.userId);
                parameter.put("email", socialUser.email);
                parameter.put("accessToken", socialUser.accessToken);
                parameter.put("profilePictureUrl", socialUser.profilePictureUrl);
                parameter.put("username", socialUser.username);
                parameter.put("fullName", socialUser.fullName);
                parameter.put("pageLink", socialUser.pageLink);
                parameter.put("facebook_id", socialUser.userId);
                processFaceBookUser(parameter);

            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, error.getMessage());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "Canceled");
            }
        });
    }


    @SuppressLint("CheckResult")
    private void processFaceBookUser(HashMap<String, String> data) {
        BaseNetworkApi.socialLoginFacebook(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialLoginResponse -> {
                    if (socialLoginResponse.state.equals(BaseNetworkApi.STATUS_OK) && socialLoginResponse.found.equals(BaseNetworkApi.NEW_FACEBOOK_USER_STATUS)) {
                        //todo--->session data should be set
                        loginView.navigateToHome();
                    }
                }, throwable -> {
                    Log.e(TAG, "processFaceBookUser() Error--->" + throwable.getMessage());
                });
    }
}
