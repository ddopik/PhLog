package com.example.softmills.phlog.ui.signup.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.login.view.LoginActivity;
import com.example.softmills.phlog.ui.signup.model.Country;
import com.example.softmills.phlog.ui.signup.presenter.SignUpPresenter;
import com.example.softmills.phlog.ui.signup.presenter.SignUpPresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignUpActivity extends BaseActivity implements SignUpView {

    private String TAG = SignUpActivity.class.getSimpleName();
    private EditText full_name, mail, registerPassword, confirmRegister_password, mobile, country;
    private TextInputLayout nameInput, userNameInput, mailInput, registerPasswordInput, confirmRegister_passwordInput, mobileInput, countryInput;
    private Button registerCancel, register_signUp;
    private AutoCompleteTextView autoCompleteTextView;
    private ProgressBar loading;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<Country> countryListObj = new ArrayList<>();
    private ArrayList<String> countryList = new ArrayList<>();
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        initPresenter();
        initView();
        initListener();
        signUpPresenter.getAllCounters();
    }


    @Override
    public void initView() {
        full_name = findViewById(R.id.full_name);
//        userName = findViewById(R.id.user_name);
        mail = findViewById(R.id.mail);
        registerPassword = findViewById(R.id.register_password);
//        confirmRegister_password = findViewById(R.id.confirm_register_password);
        mobile = findViewById(R.id.mobile);
        registerCancel = findViewById(R.id.register_cancel);
        register_signUp = findViewById(R.id.register_sign_up);


        nameInput = findViewById(R.id.full_name_input);
//        userNameInput = findViewById(R.id.user_name_input);
        mailInput = findViewById(R.id.mail_input);
        registerPasswordInput = findViewById(R.id.register_password_input);
        mobileInput = findViewById(R.id.mobile_input);
        countryInput = findViewById(R.id.country_input);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryList);
        autoCompleteTextView = findViewById(R.id.country);
        autoCompleteTextView.setThreshold(0);
        autoCompleteTextView.setAdapter(arrayAdapter);
//        autoCompleteTextView.setDropDownHeight(500);

        loading = findViewById(R.id.loading);
    }

    @Override
    public void initPresenter() {
        signUpPresenter = new SignUpPresenterImpl(getBaseContext(), this);
    }

    public void initListener() {
        register_signUp.setOnClickListener(view -> {
            if (isDataIsValid()) {

                HashMap<String, String> signUpData = new HashMap<>();
                signUpData.put("full_name", full_name.getText().toString());
                signUpData.put("password", registerPassword.getText().toString());

                if (!mail.getText().toString().isEmpty()) {
                    signUpData.put("email", mail.getText().toString());
                } else {
                    signUpData.put("email", "");

                }


                if (android.util.Patterns.PHONE.matcher(mobile.getText()).matches()) {
                    signUpData.put("mobile", mobile.getText().toString());
                } else {
                    signUpData.put("mobile", "");

                }

                if (!String.valueOf(getCountryID()).isEmpty()) {
                    signUpData.put("country_id", String.valueOf(getCountryID()));
                }
                {
                    signUpData.put("country_id", "");
                }

//                signUpData.put("User_name", userName.getText().toString());
                signUpData.put("mobile_os", "Android");
                signUpData.put("mobile_model", Utilities.getDeviceName());

                signUpPresenter.signUpUser(signUpData);

            }
        });
        registerCancel.setOnClickListener(view -> navigateToLogin());
    }

    private boolean isDataIsValid() {
        List<Boolean> failedStates = new ArrayList<Boolean>(6);

        if (full_name.getText() == null || full_name.getText().toString().equals("")) {
            nameInput.setError(getResources().getText(R.string.invailde_name));
            failedStates.add(0, false);
        } else {
            nameInput.setErrorEnabled(false);
            failedStates.add(0, true);
        }

        if (mail.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches()) {
            mailInput.setError(getString(R.string.invalid_mail));
            failedStates.add(1, false);
        } else {
            mailInput.setErrorEnabled(false);
            failedStates.add(1, true);
        }
        if (registerPassword.getText().toString().isEmpty()) {
            registerPasswordInput.setError(getString(R.string.invalid_password));
            failedStates.add(2, false);
        } else if (registerPassword.getText().length() < 6) {
            registerPasswordInput.setError(getString(R.string.password_limit));
            failedStates.add(2, false);
        } else {
            registerPasswordInput.setErrorEnabled(false);
            failedStates.add(2, true);
        }
//        if (!registerPassword.getText().toString().equals(confirmRegister_password.getText().toString())) {
//            confirmRegister_passwordInput.setError(getResources().getString(R.string.password_not_matched));
//            failedStates.add(2, false);
//        }
//        else {
//            confirmRegister_passwordInput.setErrorEnabled(false);
//            failedStates.add(2, true);
//        }
        if (!android.util.Patterns.PHONE.matcher(mobile.getText()).matches()) {
            if (!mobile.getText().toString().isEmpty()) {
                mobileInput.setError(getString(R.string.invalid_phone_number));
                failedStates.add(3, false);
            }
        } else {
            mobileInput.setErrorEnabled(false);
            failedStates.add(3, true);
        }


        if (autoCompleteTextView.getText().toString().isEmpty()) {
            countryInput.setError(getString(R.string.please_select_country));
            countryInput.setError(getString(R.string.please_select_country));
            failedStates.add(6, false);
        } else {


            if (getCountryID() == 0) {
                countryInput.setError(getString(R.string.select_country_not_exist));
                failedStates.add(4, false);
            } else {
                countryInput.setErrorEnabled(false);
                failedStates.add(4, true);
            }


        }


        for (int i = 0; i < failedStates.size(); i++) {
            if (!failedStates.get(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void showCounters(List<Country> countries) {
        this.countryList.clear();
        this.countryListObj.clear();
        this.countryListObj.addAll(countries);
        for (int i = 0; i < countries.size(); i++) {
            countryList.add(countries.get(i).nameEn);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private int getCountryID() {
        for (int i = 0; i < countryListObj.size(); i++) {
            if (countryListObj.get(i).nameEn.equals(autoCompleteTextView.getText().toString())) {
                return countryListObj.get(i).id;
            }
        }
        return 0;
    }


    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void setProgress(boolean b) {
        if (b) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void signupSuccess() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.signup_success)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }).show();
    }
}
