package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view;

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
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter.FragmentPhotoGrapherPhotosPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter.FragmentPhotoGrapherPhotosPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.PhotosListType.CURRENT_PHOTOGRAPHER_PHOTOS_LIST;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.ALL_ALBUM_IMAGES;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.CURRENT_PAGE;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.LIST_NAME;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.LIST_TYPE;
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
    private TextView placeHolder;
    private CustomRecyclerView photosRv;
    private ProgressBar photosProgress;
    private PagingController pagingController;
    private String nextPageUrl = "1";
    private boolean isLoading;


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
        initListener();//initialPage
        if (becameVisible) {
            fragmentPhotoGrapherPhotosPresenter.getPhotographerPhotos(0);
        }
    }

    @Override
    protected void initPresenter() {
        fragmentPhotoGrapherPhotosPresenter = new FragmentPhotoGrapherPhotosPresenterImpl(getContext(), this);
    }

    private boolean becameVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !becameVisible) {
            becameVisible = true;
            if (getView() != null)
                fragmentPhotoGrapherPhotosPresenter.getPhotographerPhotos(0);
        }
    }

    @Override
    protected void initViews() {
        photoGrapherPhotoList = new ArrayList<BaseImage>();
        photographerSavedPhotoAdapter = new PhotoGrapherPhotosAdapter(getContext(), photoGrapherPhotoList);
        photosRv = mainView.findViewById(R.id.photos_rv);
        photosRv.setAdapter(photographerSavedPhotoAdapter);
        photosProgress = mainView.findViewById(R.id.photos_progress);
        placeHolder = mainView.findViewById(R.id.place_holder);
    }

    private void initListener() {


        ////// initial block works by forcing then next Api for Each ScrollTop
        // cause recycler listener won't work until mainView ported with items
        photosRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager mLayoutManager = (LinearLayoutManager) photosRv.getLayoutManager();
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (firstVisibleItemPosition == 0) {
                        if (nextPageUrl != null) {
                            fragmentPhotoGrapherPhotosPresenter.getPhotographerPhotos(Integer.parseInt(nextPageUrl));
                        }

                    }
                }
            }
        });
        ////////////////


        pagingController = new PagingController(photosRv) {


            @Override
            protected void loadMoreItems() {

                fragmentPhotoGrapherPhotosPresenter.getPhotographerPhotos(Integer.parseInt(nextPageUrl));

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
            intent.putExtra(LIST_NAME, getActivity().getResources().getString(R.string.photos));
            intent.putExtra(LIST_TYPE, CURRENT_PHOTOGRAPHER_PHOTOS_LIST);
            intent.putExtra(CURRENT_PAGE, nextPageUrl);
            intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) photoGrapherPhotoList);
            startActivity(intent);
        };

    }


    @Override
    public void showPhotos(List<BaseImage> photosList) {
        this.photoGrapherPhotoList.addAll(photosList);
        photographerSavedPhotoAdapter.notifyDataSetChanged();
        if (this.photoGrapherPhotoList.isEmpty())
            placeHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showPhotosProgress(boolean state) {
        isLoading = state;

        if (state) {
            photosProgress.setVisibility(View.VISIBLE);
        } else {
            photosProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }

}
