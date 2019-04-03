package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
    private ProgressBar photographerPhotosProgressBar;
    private PagingController pagingController;
    private String nextPageUrl = "1";
    private boolean isLoading;

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
        photographerPhotosProgressBar = mainView.findViewById(R.id.photographer_photos_progress_bar);
        photoGrapherSavedPhotoList = new ArrayList<BaseImage>();
        photographerSavedPhotoAdapter = new PhotographerSavedPhotoAdapter(getContext(), photoGrapherSavedPhotoList);
        savedPhotosRv = mainView.findViewById(R.id.saved_photos_rv);
        savedPhotosRv.setAdapter(photographerSavedPhotoAdapter);

    }

    private void initListener() {


        ////// initial block works by forcing then next Api for Each ScrollTop
        // cause recycler listener won't work until mainView ported with items
        savedPhotosRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager mLayoutManager = (LinearLayoutManager) savedPhotosRv.getLayoutManager();

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (firstVisibleItemPosition == 0) {
                        if (nextPageUrl != null) {
                            photoGrapherSavedFragmentPresenter.getPhotographerSavedPhotos(Integer.parseInt(nextPageUrl));
                        }

                    }
                }
            }
        });

        ////////////////


        pagingController = new PagingController(savedPhotosRv) {
            @Override
            protected void loadMoreItems() {
                photoGrapherSavedFragmentPresenter.getPhotographerSavedPhotos(Integer.parseInt(nextPageUrl));
            }

            @Override
            public boolean isLastPage() {

                if (nextPageUrl == null) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }


        };


        photographerSavedPhotoAdapter.photoAction = photoGrapherSavedPhoto -> {
            Intent intent = new Intent(getContext(), AllAlbumImgActivity.class);
            intent.putExtra(SELECTED_IMG_ID, photoGrapherSavedPhoto.id);
            intent.putExtra(LIST_TYPE, CURRENT_PHOTOGRAPHER_SAVED_LIST);
            intent.putExtra(LIST_NAME, getActivity().getResources().getString(R.string.saved));

            intent.putExtra(CURRENT_PAGE, nextPageUrl);
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

    @Override
    public void viewPhotosLoading(boolean state) {
        isLoading = state;
        if (state) {
            photographerPhotosProgressBar.setVisibility(View.VISIBLE);
        } else {
            photographerPhotosProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }

}
