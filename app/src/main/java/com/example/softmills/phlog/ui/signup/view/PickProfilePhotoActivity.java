package com.example.softmills.phlog.ui.signup.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.signup.presenter.PickProfilePhotoPresenter;
import com.example.softmills.phlog.ui.signup.presenter.PickProfilePhotoPresenterImpl;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.softmills.phlog.Utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.softmills.phlog.Utiltes.Constants.REQUEST_CODE_GALLERY;

public class PickProfilePhotoActivity extends BaseActivity implements PickProfilePhotoActivityView {

    private String TAG = PickProfilePhotoActivity.class.getSimpleName();
    private ImageView pickImage;
    private Button uploadProfileBtn;
    private Button skipUploadProfileBtn;
    private ProgressBar uploadProfileImgProgress;
    private PickProfilePhotoPresenter pickProfilePhotoPresenter;
    private File imgPath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_pic);
        initPresenter();
        initView();
        initListener();
    }

    @Override
    public void initView() {
        pickImage = findViewById(R.id.pick_image);
        uploadProfileBtn = findViewById(R.id.upload_profile_btn);
        skipUploadProfileBtn = findViewById(R.id.skip_upload_btn);
        uploadProfileImgProgress = findViewById(R.id.upload_profile_img_progress);


    }

    private void initListener() {
        pickImage.setOnClickListener(view -> {
            openPickerDialog();
        });
        uploadProfileBtn.setOnClickListener(view -> {
            if (imgPath != null)
                pickProfilePhotoPresenter.uploadPhoto(imgPath);

        });
        skipUploadProfileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void initPresenter() {
        pickProfilePhotoPresenter = new PickProfilePhotoPresenterImpl(getBaseContext(), this);
    }


    private void openPickerDialog() {
        CharSequence photoChooserOptions[] = new CharSequence[]{getResources().getString(R.string.general_photo_chooser_camera), getResources().getString(R.string.general_photo_chooser_gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.general_photo_chooser_title));
        builder.setItems(photoChooserOptions, (dialog, option) -> {
            if (option == 0) {
                RequestCameraPermutations();
//                EasyImage.openCamera(this, 0);
            } else if (option == 1) {
                requestGalleryPermutations();
//                EasyImage.openGallery(this, 0);
            }
        }).show();
    }


    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    private void RequestCameraPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getBaseContext(), perms)) {

            ImagePicker.cameraOnly().start(this);


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_CODE_CAMERA, perms);
        }

    }

    @AfterPermissionGranted(REQUEST_CODE_GALLERY)
    private void requestGalleryPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getBaseContext(), perms)) {
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
                    REQUEST_CODE_GALLERY, perms);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            this.imgPath = new File(ImagePicker.getFirstImageOrNull(data).getPath());
            //Header Img
            GlideApp.with(getBaseContext())
                    .load(ImagePicker.getFirstImageOrNull(data).getPath())
                    .error(R.drawable.ic_launcher_foreground)
                    .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    .apply(RequestOptions.circleCropTransform())
                    .into(pickImage);


        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void viewUploadImageProgress(Boolean state) {
        if (state) {
            uploadProfileImgProgress.setVisibility(View.VISIBLE);
        } else {
            uploadProfileImgProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void UploadProfileImgFinished(Boolean state) {
        Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
