package com.example.softmills.phlog.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.login.presenter.LoginPresenter;
import com.example.softmills.phlog.ui.login.presenter.LoginPresenterImp;
import com.example.softmills.phlog.ui.signup.view.SignUpActivity;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button signUpBtn;
    private ConstraintLayout facebookSigning, googleSigningBtn;
    private TextView mail, passWord, signUpTxt, forgotPassword;
    private TextInputLayout mailInput, passwordInput;
    private ProgressBar loginProgressBar;
    private LoginPresenter loginPresenter;
    private CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        initPresenter();
        initListener();

    }

    @Override
    public void initView() {
        googleSigningBtn = findViewById(R.id.google_signing_btn);
        facebookSigning = findViewById(R.id.facebook_signing_btn);
        signUpTxt = findViewById(R.id.sign_up_txt);
        signUpBtn = findViewById(R.id.sign_up_btn);
        mail = findViewById(R.id.mail);
        passWord = findViewById(R.id.password);
        mailInput = findViewById(R.id.mail_login_input);
        passwordInput = findViewById(R.id.login_password_input);
        loginProgressBar = findViewById(R.id.login_progress);
        forgotPassword = findViewById(R.id.forgot_password);
    }

    @Override
    public void initPresenter() {

        loginPresenter = new LoginPresenterImp(getBaseContext(), this);

    }

    private void initListener() {

        facebookSigning.setOnClickListener((view -> loginPresenter.signInWithFaceBook()));
        googleSigningBtn.setOnClickListener((view -> loginPresenter.signInWithGoogle()));
        signUpTxt.setOnClickListener((view) -> navigateToSignUp());
        signUpBtn.setOnClickListener(view -> {
            if (isLoginDataValid()) {
                HashMap<String, String> normalLoginData = new HashMap<String, String>();
                normalLoginData.put("email", mail.getText().toString());
                normalLoginData.put("password", passWord.getText().toString());
                loginPresenter.signInNormal(normalLoginData);
            }
        });
        forgotPassword.setOnClickListener(v -> {
            if (mail.getText().toString().isEmpty()) {
                mailInput.setError(getResources().getString(R.string.email_missing));
                return;
            }
            loginProgressBar.setVisibility(View.VISIBLE);
            Disposable disposable = loginPresenter.forgotPassword(getBaseContext(), mail.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> loginProgressBar.setVisibility(View.GONE))
                    .subscribe(success -> {
                        if (success)
                            showToast(getString(R.string.forgot_password_check_your_mail));
                    }, throwable -> {
                        ErrorUtils.Companion.setError(this, TAG, throwable);
                    });
            disposables.add(disposable);
        });
    }


    private boolean isLoginDataValid() {

        if (mail.getText().toString().isEmpty()) {
            mailInput.setError(getResources().getString(R.string.email_missing));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches()) {
            mailInput.setError(getResources().getString(R.string.wrong_email_format));
            return false;
        } else {
            mailInput.setErrorEnabled(false);
        }


        if (passWord.getText().toString().isEmpty()) {
            passwordInput.setError(getResources().getString(R.string.invalid_password));
            return false;
        } else {
            passwordInput.setErrorEnabled(false);
        }


        return true;
    }


    @Override
    public void navigateToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    @Override
    public void showMessage(String msg) {
        super.showToast(msg);
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void viewLoginProgress(Boolean state) {
        if (state) {
            loginProgressBar.setVisibility(View.VISIBLE);
        } else {
            loginProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showResendVerificationRequest() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.email_not_verified)
                .setMessage(R.string.send_verfication_request)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    loginPresenter.sendVerificationRequest(mail.getText().toString());
                    dialog.dismiss();
                }).setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss())
                .show();
    }
}





