package com.example.softmills.phlog.ui.campaigns.inner.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.AllPhotographerPhotosPresenter;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.AllPhotographerPhotosPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.PhotographerSavedPhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */

/***
 *
 * **/
public class AllPhotographerPhotosActivity extends BaseActivity implements AllPhotographerPhotosActivityView {

    public String SELECT_MODEL = "select_mode";
    private String SELECT_MODE_NORMAL = "normal";
    private String SELECT_MODE_UPLOAD = "upload";
    private CustomRecyclerView savedPhotosRv;
    private ProgressBar savedPhotosProgress;
    private PhotographerSavedPhotoAdapter photographerSavedPhotoAdapter;
    private List<BaseImage> imageList = new ArrayList<>();
    private PagingController pagingController;
    private AllPhotographerPhotosPresenter allPhotographerPhotosPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_photographer_photos);
        initPresenter();
        initView();
        initListener();
        allPhotographerPhotosPresenter.getPhotographerPhotos(0);
    }


    @Override
    public void initView() {
        savedPhotosRv = findViewById(R.id.saved_photos_rv);
        savedPhotosProgress = findViewById(R.id.saved_photos_progress_bar);
        photographerSavedPhotoAdapter = new PhotographerSavedPhotoAdapter(this, imageList);
        savedPhotosRv = findViewById(R.id.saved_photos_rv);
        savedPhotosRv.setAdapter(photographerSavedPhotoAdapter);
    }

    @Override
    public void initPresenter() {
        allPhotographerPhotosPresenter = new AllPhotographerPhotosPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {
        pagingController = new PagingController(savedPhotosRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                allPhotographerPhotosPresenter.getPhotographerPhotos(page);
            }
        };

        photographerSavedPhotoAdapter.photoAction = photoGrapherSavedPhoto -> {
            if (getIntent().getStringExtra(SELECT_MODEL).equals(SELECT_MODE_NORMAL)) {
            } else if (getIntent().getStringExtra(SELECT_MODEL).equals(SELECT_MODE_UPLOAD)) {
//                allPhotographerPhotosPresenter.uploadCampaignPhoto(); <----- pending from BackEnd
            }
        };
    }

    @Override
    public void showSavedPhotos(List<BaseImage> photosList) {
        this.imageList.addAll(photosList);
        photographerSavedPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showSavedImageProgress(boolean state) {
        if (state) {
            savedPhotosProgress.setVisibility(View.VISIBLE);
        } else {
            savedPhotosProgress.setVisibility(View.GONE);
        }
    }
}
