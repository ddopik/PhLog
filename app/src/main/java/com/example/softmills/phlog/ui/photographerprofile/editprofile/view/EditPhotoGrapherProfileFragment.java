package com.example.softmills.phlog.ui.photographerprofile.editprofile.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter.EditPhotoGrapherProfileFragmentImpl;
import com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter.EditPhotoGrapherProfileFragmentPresenter;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EditPhotoGrapherProfileFragment extends BaseFragment implements EditPhotoGrapherProfileFragmentView {

    private View mainView;
    private EditPhotoGrapherProfileFragmentPresenter presenter;

//    private EditText profileName, profileUserName, profileEmail, ProfilePassWord;
//    private ImageView profileImage;
//    private FrameLayout coverImage;
//    private ImageButton EditProfileBack;
//    private Button editProfileSave;

    private ImageView profileImage, coverImage;
    private EditText nameET, usernameET, phoneET, emailET, passwordET;
    private ProgressBar loading;
    private Button saveButton;
    private Toolbar toolbar;
    private TextView title;
    private ImageButton backButton;

    private Photographer photographer;


    public static EditPhotoGrapherProfileFragment getInstance() {
        EditPhotoGrapherProfileFragment editPhotoGrapherProfileFragment = new EditPhotoGrapherProfileFragment();
        return editPhotoGrapherProfileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_edit_photographer_profile_2, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListeners();
        presenter.getProfileEditData();
    }

    @Override
    protected void initPresenter() {
        presenter = new EditPhotoGrapherProfileFragmentImpl(this, getContext());
    }

    @Override
    protected void initViews() {
        profileImage = mainView.findViewById(R.id.profile_image_image_view);
        coverImage = mainView.findViewById(R.id.cover_image_image_view);
        nameET = mainView.findViewById(R.id.name_edit_text);
        usernameET = mainView.findViewById(R.id.username_edit_text);
        emailET = mainView.findViewById(R.id.email_edit_text);
        phoneET = mainView.findViewById(R.id.phone_edit_text);
        passwordET = mainView.findViewById(R.id.password_edit_text);
        loading = mainView.findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        saveButton = mainView.findViewById(R.id.save_button);
        saveButton.setVisibility(View.INVISIBLE);
        backButton = mainView.findViewById(R.id.back_btn);
        title = mainView.findViewById(R.id.toolbar_title);
        title.setText(R.string.profile);
    }

    private boolean detailsChanged;

    private void initListeners() {
        saveButton.setOnClickListener(v -> {
            presenter.updateProfile(nameET.getText().toString()
                    , usernameET.getText().toString()
                    , emailET.getText().toString()
//                    , phoneET.getText().toString()
                    , passwordET.getText().toString()
                    , profile, cover);
        });
        backButton.setOnClickListener(v -> {
            MainActivity.navigationManger.navigate(Constants.NavigationHelper.PROFILE);
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(photographer.fullName)
                        || s.toString().equals(photographer.userName)
                        || s.toString().equals(photographer.email)
                        || s.toString().equals(photographer.password)
                        || s.toString().isEmpty())
                    return;
                detailsChanged = true;
                saveButton.setVisibility(View.VISIBLE);
            }
        };
        nameET.addTextChangedListener(textWatcher);
        usernameET.addTextChangedListener(textWatcher);
        phoneET.addTextChangedListener(textWatcher);
        emailET.addTextChangedListener(textWatcher);
        passwordET.addTextChangedListener(textWatcher);


        profileImage.setOnClickListener(v -> {
            whichImage = WhichImage.PROFILE;
            openPickerDialog();
        });
        coverImage.setOnClickListener(v -> {
            whichImage = WhichImage.COVER;
            openPickerDialog();
        });
    }

    private void openPickerDialog() {
        CharSequence photoChooserOptions[] = new CharSequence[]{getResources().getString(R.string.general_photo_chooser_camera), getResources().getString(R.string.general_photo_chooser_gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.general_photo_chooser_title));
        builder.setItems(photoChooserOptions, (dialog, option) -> {
            if (option == 0) {
                RequestCameraPermutations();
            } else if (option == 1) {
                requestGalleryPermutations();
            }
        }).show();
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_CAMERA)
    private void RequestCameraPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            ImagePicker.cameraOnly().start(this);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    Constants.REQUEST_CODE_CAMERA, perms);
        }

    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_GALLERY)
    private void requestGalleryPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    .single() // single mode
                    .showCamera(false)
                    .theme(R.style.AppTheme)
                    .start();
        }
        // Already have permission

        else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    Constants.REQUEST_CODE_GALLERY, perms);
        }

    }

    @Override
    public void showPhotoGrapherProfileData(Photographer photographer) {
        this.photographer = photographer;
        nameET.setText(photographer.fullName);
        usernameET.setText(photographer.userName);
        emailET.setText(photographer.email);
        passwordET.setText(photographer.password);
        GlideApp.with(this)
                .load(photographer.imageProfile)
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .into(profileImage);
    }

    @Override
    public void showMessage(int message) {
        showToast(getString(message));
    }

    @Override
    public void viewEditProfileProgress(Boolean State) {
        if (State)
            loading.setVisibility(View.VISIBLE);
        else
            loading.setVisibility(View.GONE);
    }

    String profile;
    String cover;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            detailsChanged = true;
            saveButton.setVisibility(View.VISIBLE);
            switch (whichImage) {
                case COVER:
                    cover = ImagePicker.getFirstImageOrNull(data).getPath();
                    GlideApp.with(this)
                            .load(cover)
                            .into(coverImage);
                    break;
                case PROFILE:
                    profile = ImagePicker.getFirstImageOrNull(data).getPath();
                    GlideApp.with(this)
                            .load(profile)
                            .apply(RequestOptions.circleCropTransform())
                            .into(profileImage);
                    break;
            }
        }
    }

    private WhichImage whichImage;

    private enum WhichImage {
        PROFILE, COVER
    }
}
