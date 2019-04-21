package com.example.softmills.phlog.ui.signup.view;

import com.example.softmills.phlog.ui.signup.model.Country;

import java.util.List;

public interface SignUpView {
    void showCounters(List<Country> countries);
    void showMessage(String msg);
    void setProgress(boolean b);

    void signupSuccess();
 }
