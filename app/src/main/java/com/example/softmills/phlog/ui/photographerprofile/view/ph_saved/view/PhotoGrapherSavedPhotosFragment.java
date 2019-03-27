package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter.PhotoGrapherSavedFragmentPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter.PhotoGrapherSavedFragmentPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.PhotosListType.CURRENT_PHOTOGRAPHER_SAVED_LIST;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.ALL_ALBUM_IMAGES;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.CURRENT_PAGE;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.LIST_NAME;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.LIST_TYPE;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.SELECTED_IMG_ID;

/**
 * Created by Abdalla_maged on 9/30/2018.
 */
public class PhotoGrapherSavedPhotosFragment extends BaseFragment implements PhotoGrapherPhotosFragmentView {

    private View mainView;
    private List<BaseImage> photoGrapherSavedPhotoList;
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
        if (becameVisible) {
            photoGrapherSavedFragmentPresenter.getPhotographerSavedPhotos(0); //initialPage
        }
    }

    private boolean becameVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !becameVisible) {
            becameVisible = true;
            if (getView() != null)
                photoGrapherSavedFragmentPresenter.getPhotographerSavedPhotos(0); //initialPage
        }
    }

    @Override
    protected void initPresenter() {
        photoGrapherSavedFragmentPresenter = new PhotoGrapherSavedFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {
        photoGrapherSavedPhotoList = new ArrayList<BaseImage>();
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

        photographerSavedPhotoAdapter.photoAction= photoGrapherSavedPhoto -> {
            Intent intent = new Intent(getContext(), AllAlbumImgActivity.class);
            intent.putExtra(SELECTED_IMG_ID, photoGrapherSavedPhoto.id);
            intent.putExtra(LIST_TYPE, CURRENT_PHOTOGRAPHER_SAVED_LIST);
            intent.putExtra(LIST_NAME, getActivity().getResources().getString(R.string.saved));
            intent.putExtra(CURRENT_PAGE, pagingController.getCurrentPage());
            intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) photoGrapherSavedPhotoList);
            startActivity(intent);
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
