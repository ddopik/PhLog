package com.example.softmills.phlog.ui.uploadimage.view;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.esafirm.imagepicker.features.ImagePicker;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.commonmodel.UploadImageType;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.uploadimage.view.adapter.GalleryImageAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.softmills.phlog.Utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.softmills.phlog.Utiltes.Constants.REQUEST_CODE_GALLERY;


/**
 * Created by abdalla_maged on 10/22/2018.
 */
public class GalleryImageFragment extends BaseFragment {

    private View mainView;
    private GalleryImageAdapter galleryImageAdapter;
    private CustomRecyclerView galleryRv;
    private ImageButton openCameraBtn, backBtn;
    private UploadImageType ImageType;

    public static GalleryImageFragment getInstance(UploadImageType imageType) {
        GalleryImageFragment galleryImageFragment = new GalleryImageFragment();
        galleryImageFragment.ImageType = imageType;
        return galleryImageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_gallery_image, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryImageAdapter = new GalleryImageAdapter(getContext(), getAllShownImagesPath());
        initViews();
        initListeners();
        requestGalleryPermutations();
    }

    @AfterPermissionGranted(REQUEST_CODE_GALLERY)
    private void requestGalleryPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (!EasyPermissions.hasPermissions(getContext(), perms)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_CODE_GALLERY, perms);
        }

    }

    @Override
    public void initViews() {
        galleryRv = mainView.findViewById(R.id.gallery_img_rv);
        galleryRv.setAdapter(galleryImageAdapter);
        openCameraBtn = mainView.findViewById(R.id.open_camera_btn);
//        backBtn = mainView.findViewById(R.id.back_btn);

    }

    @Override
    public void initPresenter() {
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).getSupportActionBar().hide();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    private void initListeners() {

        galleryImageAdapter.onGalleryImageClicked = imagePath -> {
            Bundle extras = new Bundle();
            ImageType.setImageUrl(imagePath);
            extras.putSerializable(ImageFilterActivity.IMAGE_TYPE, ImageType);
            Intent intent = new Intent(getContext(), ImageFilterActivity.class);
//            intent.putExtra(ImageFilterActivity.ImageFilter, imagePath);
            intent.putExtras(extras);
            startActivity(intent);
        };
        openCameraBtn.setOnClickListener((view) -> {

            RequestCameraPermutations();
        });
//        backBtn.setOnClickListener((view) -> {
//            getActivity().recreate();
//        });

    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    private ArrayList<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getContext().getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }


    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    private void RequestCameraPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            ImagePicker.cameraOnly().start(this);


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_CODE_CAMERA, perms);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            String image = ImagePicker.getFirstImageOrNull(data).getPath();
            Intent intent = new Intent(getContext(), ImageFilterActivity.class);
            intent.putExtra("image_uri", image);
            startActivity(intent);

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
