package com.example.softmills.phlog.ui.allphotos.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.allphotos.presenter.AllPhotographerPhotosPresenter;
import com.example.softmills.phlog.ui.allphotos.presenter.AllPhotographerPhotosPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */

 /***
 *this activity currently view PhotoGrapher Photos  to upload one of them to campaign
 * **/
public class AllPhotographerPhotosActivity extends BaseActivity implements AllPhotographerPhotosActivityView {

    public static String CAMPAIGN_ID="campaign_id";
    private CustomRecyclerView savedPhotosRv;
    private ProgressBar savedPhotosProgress;
    private PhotosListAdapter photographerPhotoAdapter;
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
        photographerPhotoAdapter = new PhotosListAdapter(this, imageList);
        savedPhotosRv = findViewById(R.id.saved_photos_rv);
        savedPhotosRv.setAdapter(photographerPhotoAdapter);
    }

    @Override
    public void initPresenter() {
        allPhotographerPhotosPresenter = new AllPhotographerPhotosPresenterImpl(this, this);
    }

    private void initListener() {
        pagingController = new PagingController(savedPhotosRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                allPhotographerPhotosPresenter.getPhotographerPhotos(page);
            }
        };

        photographerPhotoAdapter.photoAction = photoGrapherSavedPhoto -> {
            if(getIntent().getStringExtra(CAMPAIGN_ID) !=null)
            allPhotographerPhotosPresenter.uploadCampaignExistingPhoto(getIntent().getStringExtra(CAMPAIGN_ID),String.valueOf(photoGrapherSavedPhoto.id));
        };
    }

    @Override
    public void showPhotosList(List<BaseImage> photosList) {
        this.imageList.addAll(photosList);
        photographerPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showImageListProgress(boolean state) {
        if (state) {
            savedPhotosProgress.setVisibility(View.VISIBLE);
        } else {
            savedPhotosProgress.setVisibility(View.GONE);
        }
    }
}
