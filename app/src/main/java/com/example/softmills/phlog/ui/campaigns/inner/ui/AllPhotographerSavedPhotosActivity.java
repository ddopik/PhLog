package com.example.softmills.phlog.ui.campaigns.inner.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.model.Progress;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.AllPhotographerSavedPhotosPresenter;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.AllPhotographerSavedPhotosPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.PhotographerSavedPhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */

public class AllPhotographerSavedPhotosActivity extends BaseActivity implements AllPhotographerSavedPhotosActivityView {

    private CustomRecyclerView savedPhotosRv;
    private ProgressBar savdPhotosProgress;
    private PhotographerSavedPhotoAdapter photographerSavedPhotoAdapter;
    private List<BaseImage> imageList=new ArrayList<>();
    private PagingController pagingController;
    private AllPhotographerSavedPhotosPresenter allPhotographerSavedPhotosPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_photographer_photos);
        initPresenter();
        initView();
        initListener();

        allPhotographerSavedPhotosPresenter.getPhotographerSavedPhotos(0);
    }


    @Override
    public void initView() {
        savedPhotosRv=findViewById(R.id.saved_photos_rv);
        savdPhotosProgress=findViewById(R.id.saved_photos_progress_bar);
        photographerSavedPhotoAdapter = new PhotographerSavedPhotoAdapter(this, imageList);
        savedPhotosRv =findViewById(R.id.saved_photos_rv);
        savedPhotosRv.setAdapter(photographerSavedPhotoAdapter);
    }

    @Override
    public void initPresenter() {
        allPhotographerSavedPhotosPresenter=new AllPhotographerSavedPhotosPresenterImpl(getBaseContext(),this);
    }

    private void initListener() {
        pagingController = new PagingController(savedPhotosRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                allPhotographerSavedPhotosPresenter.getPhotographerSavedPhotos(page);
            }
        };
    }


    @Override
    public void showSavedPhotos(List<BaseImage> photosList) {
        String c=PrefUtils.getUserToken(this);
        this.imageList.addAll(photosList);
        photographerSavedPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showSavedImageProgress(boolean state) {
        if (state){
            savdPhotosProgress.setVisibility(View.VISIBLE);
        }else {
            savdPhotosProgress.setVisibility(View.GONE);
        }
    }
}
