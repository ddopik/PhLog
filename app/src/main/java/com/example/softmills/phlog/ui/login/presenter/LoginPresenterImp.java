package com.example.softmills.phlog.ui.login.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.error.ANError;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.commonmodel.BaseErrorResponse;
import com.example.softmills.phlog.base.commonmodel.Device;
import com.example.softmills.phlog.base.commonmodel.ErrorMessageResponse;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.login.view.LoginView;
import com.google.gson.Gson;
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
                    PrefUtils.setLoginState(context, true);
                    PrefUtils.setUserToken(context, loginResponse.loginData.token);
                    PrefUtils.setUserName(context, loginResponse.loginData.userName);
                    PrefUtils.setUserID(context, loginResponse.loginData.id);
                    loginView.navigateToHome();
                    sendFirebaseToken();
                }, throwable -> {
                    if (throwable instanceof ANError) {
                        ANError error = (ANError) throwable;
                        if (error.getErrorCode() == BaseNetworkApi.STATUS_BAD_REQUEST) {
                            ErrorMessageResponse errorMessageResponse = new Gson().fromJson(error.getErrorBody(), ErrorMessageResponse.class);
                            for (BaseErrorResponse e : errorMessageResponse.errors) {
                                if (e.code.equals(BaseNetworkApi.ERROR_VERIFICATION)) {
                                    loginView.showResendVerificationRequest();
                                    break;
                                }
                            }
                        }
                    }
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    loginView.viewLoginProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    private void sendFirebaseToken() {

        BaseNetworkApi.updateFirebaseToken(new Device(Utilities.getDeviceName(), true, PrefUtils.getFirebaseToken(context)), PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    loginView.viewLoginProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    loginView.viewLoginProgress(false);
                });
    }

    @Override
    public void signInWithGoogle() {
        loginView.viewLoginProgress(true);

        com.jaychang.sa.google.SimpleAuth.disconnectGoogle();
        List<String> scopes = new ArrayList<String>();
        scopes.add("email");
        scopes.add("profile");
        scopes.add("openid");


        com.jaychang.sa.google.SimpleAuth.connectGoogle(scopes, new AuthCallback() {
                    @Override
                    public void onSuccess(SocialUser socialUser) {

                        Log.e(TAG, "userId:" + socialUser.toString());

                        HashMap<String, String> parameter = new HashMap<String, String>();
                        parameter.put("full_name", socialUser.fullName);
                        parameter.put("google_id", socialUser.userId);
                        parameter.put("mobile_os", "Android");
                        parameter.put("mobile_model", Utilities.getDeviceName());
                        parameter.put("email", socialUser.email);
                        parameter.put("image_profile", socialUser.profilePictureUrl);
                        processGoogleUser(parameter);
                        loginView.viewLoginProgress(false);

                    }

                    @Override
                    public void onError(Throwable error) {
                        ErrorUtils.Companion.setError(context, TAG, error);
                        loginView.viewLoginProgress(false);
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "Canceled");
                        loginView.viewLoginProgress(false);
                    }
                }

        );

    }

    @Override
    public void signInWithFaceBook() {
        loginView.viewLoginProgress(true);
        List<String> scopes = new ArrayList<>();
        com.jaychang.sa.facebook.SimpleAuth.connectFacebook(scopes, new AuthCallback() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(SocialUser socialUser) {

                HashMap<String, String> parameter = new HashMap<String, String>();

                parameter.put("full_name", socialUser.fullName);
                parameter.put("facebook_id", socialUser.userId);
                parameter.put("mobile_os", "Android");
                parameter.put("mobile_model", Utilities.getDeviceName());
                parameter.put("email", socialUser.email);
                parameter.put("image_profile", socialUser.profilePictureUrl);
                processFaceBookUser(parameter);
                loginView.viewLoginProgress(false);
            }

            @Override
            public void onError(Throwable error) {
                ErrorUtils.Companion.setError(context, TAG, error);
                loginView.viewLoginProgress(false);
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "Canceled");
                loginView.viewLoginProgress(false);
            }

        });
    }


    @SuppressLint("CheckResult")
    private void processFaceBookUser(HashMap<String, String> data) {
        loginView.viewLoginProgress(true);
        BaseNetworkApi.socialLoginFacebook(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialLoginResponse -> {

                    PrefUtils.setLoginState(context, true);
                    PrefUtils.setUserToken(context, socialLoginResponse.data.token);
                    PrefUtils.setUserName(context, socialLoginResponse.data.userName);
                    PrefUtils.setUserID(context, String.valueOf(socialLoginResponse.data.id));
                    loginView.navigateToHome();
                    sendFirebaseToken();
                    loginView.viewLoginProgress(false);

                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    loginView.viewLoginProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    private void processGoogleUser(HashMap<String, String> data) {
        loginView.viewLoginProgress(true);
        BaseNetworkApi.socialLoginGoogle(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialLoginResponse -> {
                    PrefUtils.setLoginState(context, true);
                    PrefUtils.setUserToken(context, socialLoginResponse.data.token);
                    PrefUtils.setUserName(context, socialLoginResponse.data.userName);
                    PrefUtils.setUserID(context, String.valueOf(socialLoginResponse.data.id));
                    loginView.navigateToHome();
                    sendFirebaseToken();
                    loginView.viewLoginProgress(false);

                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    loginView.viewLoginProgress(false);
                });
    }

    @Override
    public Observable<Boolean> forgotPassword(Context context, String email) {
        return BaseNetworkApi.forgotPassword(email)
                .map(response -> response != null);
    }

    @SuppressLint("CheckResult")
    @Override
    public void sendVerificationRequest(String email) {
        BaseNetworkApi.requestVerification(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    loginView.showMessage(context.getString(R.string.veification_request_sent));
                }, throwable -> {
                    if (throwable instanceof ANError) {
                        ANError error = (ANError) throwable;
                        if (error.getErrorCode() == BaseNetworkApi.STATUS_BAD_REQUEST) {
                            ErrorMessageResponse errorMessageResponse = new Gson().fromJson(error.getErrorBody(), ErrorMessageResponse.class);
                            for (BaseErrorResponse e : errorMessageResponse.errors) {
                                switch (e.code) {
                                    case BaseNetworkApi.ERROR_VALIDATION:
                                    case BaseNetworkApi.ERROR_VERIFICATION:
                                        loginView.showMessage(e.message);
                                        break;
                                }
                            }
                        }
                    }
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
