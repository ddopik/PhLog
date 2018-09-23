package com.example.softmills.phlog.ui.login.view;

import android.os.Bundle;

import java.util.HashMap;

public interface LoginView {
    void navigateToSignUp();
    void navigateToSignUp(HashMap<String,String> map);
    void showToast(String msg);
}
