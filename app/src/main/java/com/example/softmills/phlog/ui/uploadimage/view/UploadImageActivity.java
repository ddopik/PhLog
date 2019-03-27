package com.example.softmills.phlog.ui.uploadimage.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.UploadImageData;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class UploadImageActivity extends BaseActivity {


    public static String IMAGE_TYPE = "image_type";
    private UploadImageData imageType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        initView();

    }

    @Override
    public void initView() {

        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        if (bundle.getSerializable(IMAGE_TYPE) != null) {
            imageType = (UploadImageData) bundle.getSerializable(IMAGE_TYPE);
            GalleryImageFragment galleryImageFragment = GalleryImageFragment.getInstance(imageType);
            addFragment(R.id.upload_img_container, galleryImageFragment, GalleryImageFragment.class.getSimpleName(), false);
        }


    }

    @Override
    public void initPresenter() {

    }
}
