package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter.PhotoGrapherSavedFragmentPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter.PhotoGrapherSavedFragmentPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdalla_maged on 9/30/2018.
 */
public class PhotoGrapherSavedPhotosFragment extends BaseFragment implements PhotoGrapherPhotosFragmentView {

    private View mainView;
    private List<BaseImage> photoGrapherSavedPhotoList = new ArrayList<BaseImage>();
    private PhotographerSavedPhotoAdapter photographerSavedPhotoAdapter;
    private PhotoGrapherSavedFragmentPresenter photoGrapherSavedFragmentPresenter;

    private CustomRecyclerView savedPhotosRv;
    private PagingController pagingController;


    public static PhotoGrapherSavedPhotosFragment getInstance() {
        return new PhotoGrapherSavedPhotosFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_saved, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        photoGrapherSavedFragmentPresenter.getPhotographerSavedPhotos(0); //initialPage
    }

    @Override
    protected void initPresenter() {
        photoGrapherSavedFragmentPresenter = new PhotoGrapherSavedFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {
        photographerSavedPhotoAdapter = new PhotographerSavedPhotoAdapter(getContext(), photoGrapherSavedPhotoList);
        savedPhotosRv = mainView.findViewById(R.id.saved_photos_rv);
        savedPhotosRv.setAdapter(photographerSavedPhotoAdapter);

    }

    private void initListener() {
        pagingController = new PagingController(savedPhotosRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                photoGrapherSavedFragmentPresenter.getPhotographerSavedPhotos(page);
            }
        };
    }


    @Override
    public void showSavedPhotos(List<BaseImage> photosList) {
        this.photoGrapherSavedPhotoList.addAll(photosList);
        photographerSavedPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }


}
