package com.example.softmills.phlog.ui.login.view;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;
import com.jaychang.sa.facebook.SimpleAuth;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends BaseActivity implements LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    Button googleSigningBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
        initListener();
        printHashKey(this);
    }

    private void initView() {
        googleSigningBtn = findViewById(R.id.google_signing);
    }

    private void initListener() {

        googleSigningBtn.setOnClickListener((view -> connectFacebook()));
    }

    void signInWithGoogle() {
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

    private void connectFacebook() {
//        List<String> scopes = Arrays.asList("user_birthday", "user_friends");
        List<String> scopes = new ArrayList<>();


        com.jaychang.sa.facebook.SimpleAuth.connectFacebook(scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {
                Log.d(TAG, "userId:" + socialUser.userId);
                Log.d(TAG, "email:" + socialUser.email);
                Log.d(TAG, "accessToken:" + socialUser.accessToken);
                Log.d(TAG, "profilePictureUrl:" + socialUser.profilePictureUrl);
                Log.d(TAG, "username:" + socialUser.username);
                Log.d(TAG, "fullName:" + socialUser.fullName);
                Log.d(TAG, "pageLink:" + socialUser.pageLink);
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

    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }
}





