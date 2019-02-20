package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter.FragmentPhotoGrapherPhotosPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter.FragmentPhotoGrapherPhotosPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.ALL_ALBUM_IMAGES;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.LIST_NAME;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.SELECTED_IMG_ID;

/**
 * Created by Abdalla_maged on 9/30/2018.
 */
public class PhotoGrapherPhotosFragment extends BaseFragment implements FragmentPhotoGrapherPhotosView {

    private static String TAG = PhotoGrapherPhotosFragment.class.getSimpleName();
    private View mainView;
    private List<BaseImage> photoGrapherPhotoList;
    private PhotoGrapherPhotosAdapter photographerSavedPhotoAdapter;
    private FragmentPhotoGrapherPhotosPresenter fragmentPhotoGrapherPhotosPresenter;
    private LinearLayoutManager mLayoutManager;
    private CustomRecyclerView photosRv;
    private ProgressBar photosProgress;
    private PagingController pagingController;


    public static PhotoGrapherPhotosFragment getInstance() {
        return new PhotoGrapherPhotosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_photos, container, false);
        mLayoutManager = new LinearLayoutManager(getContext());
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        fragmentPhotoGrapherPhotosPresenter.getPhotographerPhotos(0); //initialPage
    }

    @Override
    protected void initPresenter() {
        fragmentPhotoGrapherPhotosPresenter = new FragmentPhotoGrapherPhotosPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {
        photoGrapherPhotoList= new ArrayList<BaseImage>();
        photographerSavedPhotoAdapter = new PhotoGrapherPhotosAdapter(getContext(), photoGrapherPhotoList);
        photosRv = mainView.findViewById(R.id.photos_rv);
        photosRv.setAdapter(photographerSavedPhotoAdapter);
        photosProgress = mainView.findViewById(R.id.photos_progress);

    }

    private void initListener() {
        pagingController = new PagingController(photosRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                fragmentPhotoGrapherPhotosPresenter.getPhotographerPhotos(page);
            }
        };

        photographerSavedPhotoAdapter.photoAction= photoGrapherSavedPhoto -> {
            Intent intent = new Intent(getActivity(), AllAlbumImgActivity.class);
            intent.putExtra(SELECTED_IMG_ID, photoGrapherSavedPhoto.id);
            intent.putExtra(LIST_NAME, getActivity().getResources().getString(R.string.saved));
            intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) photoGrapherPhotoList);
            startActivity(intent);
        };

    }



    @Override
    public void showPhotos(List<BaseImage> photosList) {
        this.photoGrapherPhotoList.addAll(photosList);
        photographerSavedPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showPhotosProgress(boolean state) {
        if (state) {
            photosProgress.setVisibility(View.VISIBLE);
        } else {
            photosProgress.setVisibility(View.GONE);
        }
    }


}
