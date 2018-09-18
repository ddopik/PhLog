package com.example.softmills.phlog.ui.signup.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends BaseActivity {

    private EditText name, userName, mail, registerPassword, confirmRegister_password, mobile;
    private TextInputLayout nameInput, userNameInput, mailInput, registerPasswordInput, confirmRegister_passwordInput, mobileInput;
    private Button registerCancel, register_signUp;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        initPresenter();
        initView();
        initListener();
    }



    @Override
    public void initView() {
        name = findViewById(R.id.name);
        userName = findViewById(R.id.user_name);
        mail = findViewById(R.id.mail);
        registerPassword = findViewById(R.id.register_password);
        confirmRegister_password = findViewById(R.id.confirm_register_password);
        mobile = findViewById(R.id.mobile);

        registerCancel = findViewById(R.id.register_cancel);
        register_signUp = findViewById(R.id.register_sign_up);


        nameInput = findViewById(R.id.name_input);
        userNameInput = findViewById(R.id.user_name_input);
        mailInput = findViewById(R.id.mail_input);
        registerPasswordInput = findViewById(R.id.register_password_input);
        confirmRegister_passwordInput = findViewById(R.id.confirm_register_password_input);
        mobileInput = findViewById(R.id.mobile_input);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.listValues));

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.country);
        autoCompleteTextView.setAdapter(arrayAdapter);
//        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View arg0) {
////                autoCompleteTextView.showDropDown();
//            }
//        });
        autoCompleteTextView.setOnFocusChangeListener((view, b) -> autoCompleteTextView.showDropDown());



    }

    @Override
    public void initPresenter() {

    }

    public void initListener() {
        register_signUp.setOnClickListener(view -> dataIsValid());
    }

    private boolean dataIsValid() {
        List<Boolean> failedStates = new ArrayList<Boolean>(6);

        if (name.getText() == null || name.getText().toString().equals("")) {
            nameInput.setError(getResources().getText(R.string.invailde_name));
            failedStates.add(0, false);
        } else {
            nameInput.setErrorEnabled(false);
            failedStates.add(0, true);
        }
        if (userName.getText().toString().isEmpty()) {
            userNameInput.setError(getString(R.string.invalid_user_name));
            failedStates.add(1, false);
        } else {
            userNameInput.setErrorEnabled(false);
            failedStates.add(1, true);
        }
        if (mail.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches()) {
            mailInput.setError(getString(R.string.invalid_mail));
            failedStates.add(2, false);
        } else {
            mailInput.setErrorEnabled(false);
            failedStates.add(2, true);
        }
        if (registerPassword.getText().toString().isEmpty()) {
            registerPasswordInput.setError(getString(R.string.invalid_password));
            failedStates.add(3, false);
        } else if (registerPassword.getText().length() < 6) {
            registerPasswordInput.setError(getString(R.string.password_limit));
            failedStates.add(3, false);
        } else {
            registerPasswordInput.setErrorEnabled(false);
            failedStates.add(3, true);
        }
        if (!registerPassword.getText().toString().equals(confirmRegister_password.getText().toString())) {
            confirmRegister_passwordInput.setError(getResources().getString(R.string.password_not_matched));
            failedStates.add(4, false);
        } else {
            confirmRegister_passwordInput.setErrorEnabled(false);
            failedStates.add(4, true);
        }
        if ( mobile.getText().toString().equals("") || ! android.util.Patterns.PHONE.matcher(mobile.getText()).matches()) {
            mobileInput.setError(getString(R.string.invalid_phone_number));
            failedStates.add(5, false);
        } else {
            mobileInput.setErrorEnabled(false);
            failedStates.add(5, true);
        }


        for (int i = 0; i < failedStates.size(); i++) {
            if (!failedStates.get(i)) {
                return false;
            }

        }
        return  true;
    }
}
