package com.example.softmills.phlog.ui.login.presenter;

import android.content.Context;

import java.util.HashMap;

import io.reactivex.Observable;

public interface LoginPresenter {


    void signInNormal(HashMap<String,String> loginData);
    void signInWithGoogle();

    void signInWithFaceBook();

    Observable<Boolean> forgotPassword(Context context, String email);

    void sendVerificationRequest(String toString);
}
