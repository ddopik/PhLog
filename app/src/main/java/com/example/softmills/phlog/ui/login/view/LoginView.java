package com.example.softmills.phlog.ui.login.view;

import android.os.Bundle;

import java.util.HashMap;

public interface LoginView {
    void navigateToSignUp();

    void navigateToHome();
    void showMessage(String msg);
    void viewLoginProgress(Boolean state);

    void showResendVerificationRequest();
 }
