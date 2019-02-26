package com.example.softmills.phlog.ui.login.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.login.view.LoginView;
import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenterImp implements LoginPresenter {

    private LoginView loginView;
    private Context context;

    public LoginPresenterImp(Context context, LoginView loginView) {
        this.loginView = loginView;
        this.context = context;

    }

    private static final String TAG = LoginPresenterImp.class.getSimpleName();

    @SuppressLint("CheckResult")
    @Override
    public void signInNormal(HashMap<String, String> loginData) {
        loginView.viewLoginProgress(true);
        BaseNetworkApi.LoginUserNormal(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    loginView.viewLoginProgress(false);
                    loginView.showMessage(loginResponse.loginData.fullName);
                    PrefUtils.setLoginState(context, true);
                    PrefUtils.setUserToken(context, loginResponse.loginData.token);
                    PrefUtils.setUserName(context, loginResponse.loginData.userName);
                    PrefUtils.setUserID(context, loginResponse.loginData.id);
                    sendFirebaseToken();
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    loginView.viewLoginProgress(false);
                });
    }

    private void sendFirebaseToken() {
        loginView.navigateToHome();
        BaseNetworkApi.updateFirebaseToken(PrefUtils.getUserToken(context), PrefUtils.getFirebaseToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    loginView.viewLoginProgress(false);
                    if (response == null)
                        return;
                    loginView.navigateToHome();
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    loginView.viewLoginProgress(false);
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
                ErrorUtils.Companion.setError(context, TAG, error);
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
//                parameter.put("userId", socialUser.userId);

//                parameter.put("accessToken", socialUser.accessToken);
//                 parameter.put("username", socialUser.username);
//                parameter.put("pageLink", socialUser.pageLink);

                parameter.put("full_name", socialUser.fullName);
                parameter.put("facebook_id", socialUser.userId);
                parameter.put("mobile_os", "Android");
                parameter.put("mobile_model", Utilities.getDeviceName());
                parameter.put("email", socialUser.email);
                parameter.put("image_profile", socialUser.profilePictureUrl);
                processFaceBookUser(parameter);

            }

            @Override
            public void onError(Throwable error) {
                ErrorUtils.Companion.setError(context, TAG, error);
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
                    if (socialLoginResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        PrefUtils.setLoginState(context, true);
                        PrefUtils.setUserToken(context, socialLoginResponse.token);
                        PrefUtils.setUserName(context, socialLoginResponse.userName);
//                        PrefUtils.setUserID(context, socialLoginResponse.id);
                        sendFirebaseToken();
                    }
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @Override
    public Observable<Boolean> forgotPassword(Context context, String email) {
        return BaseNetworkApi.forgotPassword(email)
                .map(response -> {
                    if (response != null)
                        return true;
                    return false;
                });
    }
}
